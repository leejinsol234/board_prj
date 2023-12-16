package org.koreait.models.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ListData;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.Pagination;
import org.koreait.commons.Utils;
import org.koreait.controllers.admins.BoardSearch;
import org.koreait.controllers.boards.BoardDataSearch;
import org.koreait.controllers.boards.BoardForm;
import org.koreait.entities.*;
import org.koreait.models.comment.CommentInfoService;
import org.koreait.models.file.FileInfoService;
import org.koreait.repositories.BoardDataRepository;
import org.koreait.repositories.BoardViewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final BoardDataRepository boardDataRepository;
    private final BoardViewRepository boardViewRepository;
    private final FileInfoService fileInfoService;
    private final EntityManager em;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;
    private final HttpSession session;
    private final PasswordEncoder encoder;
    private final Utils utils;
    private final CommentInfoService commentInfoService;

    /*조회수 Uid
    *   비회원 - Utils::guestUid() : ip + User-Agent(브라우저 종류)
    *   회원 - 회원 번호
    * */
    public int viewUid(){
        return memberUtil.isLogin() ? memberUtil.getMember().getUserNo().intValue() : utils.guestUid();
    }

    /*게시글별 조회수 업데이트*/
    public void updateView(Long seq){
        //조회 기록 추가
        try {
            BoardView boardView = new BoardView();
            boardView.setSeq(seq);
            boardView.setUid(viewUid());

            boardViewRepository.saveAndFlush(boardView); //기본키가 두 번 추가되므로 오류 발생을 try-catch로 잡아준다.
        } catch (Exception e){
            e.printStackTrace();
        }

        //게시글 별 총 조회수 산출
        QBoardView boardView = QBoardView.boardView;
        long cnt = boardViewRepository.count(boardView.seq.eq(seq));

        //게시글 데이터에 업데이트(viewCnt)
        BoardData data = boardDataRepository.findById(seq).orElse(null);
        if(data == null) return;
        data.setViewCnt((int)cnt);
        boardDataRepository.flush();
    }
    public BoardData get(Long seq) {

        BoardData data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);
        data.setComments(commentInfoService.getList(data.getSeq()));
        addFileInfo(data);

        return data;
    }

    public BoardForm getForm(Long seq){ //양식의 완성을 위해 BoardForm으로 가져오기
        BoardData data = get(seq);
        BoardForm form = new ModelMapper().map(data, BoardForm.class);
        form.setMode("update");
        form.setBId(data.getBoard().getBId()); //게시판 아이디를 양식에 입력
        return form;
    }

    public ListData<BoardData> getList(BoardDataSearch search){
        QBoardData boardData = QBoardData.boardData;
        int page = Utils.getNumber(search.getPage(),1);
        int limit = Utils.getNumber(search.getLimit(),20);
        int offset = (page-1) * limit; //현재 페이지 시작 위치

        String bId = search.getBId(); //게시판 아이디
        String sopt = Objects.requireNonNullElse(search.getSopt(),"subject_content"); //검색 옵션
        String skey = search.getSkey(); //검색 키워드
        String category = search.getCategory();

        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(boardData.board.bId.eq(bId));

        //게시판 분류 검색 처리
        if(StringUtils.hasText(category)){
            category = category.trim();
            andBuilder.and(boardData.category.eq(category));
        }

        //키워드 검색 처리
        if(StringUtils.hasText(skey)){
            skey = skey.trim();

            if(sopt.equals("subject")) { //제목 검색
                andBuilder.and(boardData.subject.contains(skey));
            } else if (sopt.equals("content")){ //내용 검색
                andBuilder.and(boardData.content.contains(skey));
            } else if (sopt.equals("subject_content")) { //제목+내용 검색
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(boardData.subject.contains(skey))
                        .or(boardData.content.contains(skey));
            } else if (sopt.equals("poster")) { //작성자 (+아이디) 검색
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(boardData.poster.contains(skey))
                        .or(boardData.member.email.contains(skey))
                        .or(boardData.member.userNm.contains(skey));

                andBuilder.and(orBuilder);
            }
        }

        PathBuilder pathBuilder = new PathBuilder(BoardData.class,"boardData");
        List<BoardData> items = new JPAQueryFactory(em)
                .selectFrom(boardData)
                .leftJoin(boardData.board)
                .leftJoin(boardData.member)
                .where(andBuilder)
                .offset(offset)
                .limit(limit)
                .fetchJoin() //목록 조회 시(n+1 오류를 예방하기 위해) fetchJoin을 사용함
                .orderBy(new OrderSpecifier(Order.valueOf("DESC"), pathBuilder.get("createdAt")))
                .fetch();
        //위처럼 복잡한 조건이 아닌 간단한 조건일 때는 스프링 데이터로 사용할 수도 있음

        int total = (int)boardDataRepository.count(andBuilder); //현재 게시판 전체 개수 조회
        Pagination pagination = new Pagination(page, total, 10, limit, request);

        // 파일 정보 추가
        items.stream().forEach(this::addFileInfo);

        ListData<BoardData> data = new ListData<>();
        data.setContent(items);
        data.setPagination(pagination);

        return data;
    }

    private void addFileInfo(BoardData data){
        String gid = data.getGid();
        List<FileInfo> editorImages = fileInfoService.getListDone(gid, "editor");
        List<FileInfo> attachFiles = fileInfoService.getListDone(gid,"attach");

        data.setEditorImages(editorImages);
        data.setAttachFiles(attachFiles);
    }

    public boolean isMine(Long seq){ //회원의 경우 자신이 작성한 글만 수정할 수 있도록
        if(memberUtil.isAdmin()) return true; //관리자는 다른 회원이 작성한 글도 수정,삭제 가능
        BoardData data = get(seq);
        if(data.getMember() != null){
            Member boardMember = data.getMember();
            Member member = memberUtil.getMember();
              return memberUtil.isLogin() && boardMember.getUserNo().longValue() == member.getUserNo().longValue();
        } else { //비회원 게시글
            //세션에 chk_게시글번호 항목이 있으면 비밀번호 검증 완료
            String key = "chk_" + seq;
            if(session.getAttribute(key) == null){ //비회원 비밀번호 검증 X -> 검증 화면으로 이동
                session.setAttribute("guest_seq",seq);
                throw new RequiredPasswordCheckException();
            } else { //비회원 게시글 검증 성공 시
                return true;
            }
        }

    }

    public boolean checkGuestPassword(Long seq, String password){
        BoardData data = get(seq);
        String guestPw = data.getGuestPw();
        if(!StringUtils.hasText(guestPw)){
            return false;
        }
        return encoder.matches(password, guestPw); //사용자가 입력한 비밀번호, db에 저장되어있는 비밀번호w
    }

    public List<BoardData> getList(String bId, int num){

        QBoardData boardData = QBoardData.boardData;
        num = Utils.getNumber(num, 10);
        Pageable pageable = PageRequest.of(0, num, Sort.by(desc("createdAt")));

        Page<BoardData> data = boardDataRepository.findAll(boardData.board.bId.eq(bId), pageable);

        return data.getContent();
    }

}
