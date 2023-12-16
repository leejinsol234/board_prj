package org.koreait.models.comment;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.AlertBackException;
import org.koreait.controllers.comments.CommentForm;
import org.koreait.entities.BoardData;
import org.koreait.entities.CommentData;
import org.koreait.entities.Member;
import org.koreait.entities.QCommentData;
import org.koreait.models.board.BoardDataNotFoundException;
import org.koreait.models.board.RequiredPasswordCheckException;
import org.koreait.repositories.BoardDataRepository;
import org.koreait.repositories.CommentDataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentInfoService {

    private final CommentDataRepository commentDataRepository;
    private final EntityManager em;
    private final BoardDataRepository boardDataRepository;
    private final MemberUtil memberUtil;
    private final HttpSession session;
    private final PasswordEncoder encoder;

    public CommentData get(Long seq){
        CommentData comment = commentDataRepository.findById(seq).orElseThrow(CommentNotFoundException::new);

        return comment;
    }
    public CommentForm getForm(Long seq){
        CommentData comment = get(seq);
        CommentForm form = new ModelMapper().map(comment, CommentForm.class);
        form.setBoardDataSeq(comment.getBoardData().getSeq());

        return form;
    }

    //댓글 목록
    //boardDataSeq : 게시글 번호
    public List<CommentData> getList(Long boardDataSeq){
        QCommentData commentData = QCommentData.commentData;

        PathBuilder<CommentData> pathBuilder = new PathBuilder<>(CommentData.class, "commentData");

        List<CommentData> items = new JPAQueryFactory(em)
                .selectFrom(commentData)
                .where(commentData.boardData.seq.eq(boardDataSeq))
                .leftJoin(commentData.member)
                .fetchJoin()
                .orderBy(new OrderSpecifier(Order.ASC, pathBuilder.get("createdAt")))
                .fetch();

        return items;
    }

    //댓글 수 업데이트
    public void updateCommentCnt(Long seq){
       BoardData boardData = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);
       boardData.setCommentCnt(commentDataRepository.getTotal(seq));

        boardDataRepository.flush();
    }

    public void isMine(Long seq){

        if(memberUtil.isAdmin()){
            return;
        }

        CommentData data = get(seq);
        Member commentMember = data.getMember();

        if(commentMember == null) { //비회원 작성
            String key = "chk_comment" + seq;
            if(session.getAttribute(key) == null){ //비회원 비밀번호 확인 전
                session.setAttribute("comment_seq",seq);
                throw new RequiredPasswordCheckException();
            }

        } else {
            if(!memberUtil.isLogin() || commentMember.getUserNo().longValue() != memberUtil.getMember().getUserNo().longValue()){
                throw new AlertBackException(Utils.getMessage("작성한_댓글만_수정_삭제가 가능합니다.", "error"));
            }
        }
    }
    //수정, 삭제 가능 여부
    public boolean isEditable(CommentData comment){
        Member commentMember = comment.getMember();
        if(memberUtil.isAdmin() || commentMember == null){ //관리자이거나 비회원 댓글이면 무조건 노출
            return true;
        }
        if(memberUtil.isLogin() && commentMember != null && commentMember.getUserNo().longValue() == memberUtil.getMember().getUserNo().longValue()){
            return false;
        }
        return false;
    }

    //비회원 댓글 비밀번호 체크
    public boolean checkGuestPassword(Long seq, String password){
        CommentData comment = get(seq);

        return encoder.matches(password, comment.getGuestPw());
    }

}
