package org.board.project.repositories;

import org.board.project.entities.BoardView;
import org.board.project.entities.BoardViewId;
import org.board.project.entities.QBoardView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardViewRepository extends JpaRepository<BoardView, BoardViewId>, QuerydslPredicateExecutor {

    //게시글별 조회수 조회
    default long getHit(Long id){
        QBoardView boardView = QBoardView.boardView;
        return count(boardView.id.eq(id));
    }
}
