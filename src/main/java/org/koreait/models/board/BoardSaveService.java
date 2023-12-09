package org.koreait.models.board;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.controllers.boards.BoardForm;
import org.koreait.controllers.boards.BoardFormValidator;
import org.koreait.entities.Board;
import org.koreait.entities.BoardData;
import org.koreait.models.board.config.BoardConfigInfoService;
import org.koreait.repositories.BoardDataRepository;
import org.koreait.repositories.FileInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardSaveService {
    private final BoardDataRepository boardDataRepository;
    private final BoardConfigInfoService infoService;
    private final MemberUtil memberUtil;
    private final PasswordEncoder encoder;
    private final FileInfoRepository fileInfoRepository;
    private final BoardFormValidator validator;

    public void save(BoardForm form, Errors errors){
        validator.validate(form,errors); //비회원 게시글일 때 비밀번호의 복잡성 체크
        if(errors.hasErrors()){
            return;
        }
        save(form);
    }

    public void save(BoardForm form) {
        Long seq = form.getSeq();
        String mode = Objects.requireNonNullElse(form.getMode(), "write"); //기본값 write

        //게시판 설정 조회 + 글쓰기 권한 체크
        Board board = infoService.get(form.getBId(),true);

        String gid = form.getGid();

        BoardData data = null;
        if (mode.equals("update") && seq != null) {
            data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);
        } else {
            data = new BoardData();
            data.setBoard(board); //게시판 bId 최초 글 등록시 한번만 등록
            data.setGid(gid); //그룹Id(gId)는 최초 글 입력시 한 번만 db에 등록됨
            data.setMember(memberUtil.getMember()); //관리자가 글을 수정하더라도 작성자가 관리자로 바뀌면 안되므로 최초에 한 번만 등록되도록 함
        }

        data.setSubject(form.getSubject());
        data.setContent(form.getContent());
        data.setPoster(form.getPoster());
        data.setCategory(form.getCategory()); //카테고리는 수정될 수 있으므로

        //관리자 일때만 공지사항 등록 및 수정이 가능하도록
        if(memberUtil.isAdmin()){
            data.setNotice(form.isNotice());
        }

        //비회원 비밀번호 처리
        String guestPw = form.getGuestPw();
        if(StringUtils.hasText(guestPw)){
            data.setGuestPw(encoder.encode(guestPw)); //비회원이 비밀번호를 입력한 경우 암호화 처리
        }

        boardDataRepository.saveAndFlush(data);

        //파일 업로드 완료 처리
        fileInfoRepository.processDone(gid);
    }
}
