package org.koreait.repositories;

import org.koreait.entities.CommentData;
import org.koreait.entities.QCommentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentDataRepository extends JpaRepository<CommentData, Long>, QuerydslPredicateExecutor<CommentData> {

    //게시글 별 댓글 수
    default int getTotal(Long boardDataseq){
        QCommentData commentData = QCommentData.commentData;

        return (int)count(commentData.boardData.seq.eq(boardDataseq));
    }
}
