package org.board.project.models.board;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.board.project.entities.BoardData;
import org.board.project.entities.QBoardData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardListService {
    //복잡한 쿼리가 필요할 경우

    @PersistenceContext
    private EntityManager em;

    //게시글 목록 조회(처음부터 조인)
    public List<BoardData> getList(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QBoardData boardData = QBoardData.boardData;
        JPAQuery<BoardData> query = queryFactory.selectFrom(boardData)
                .leftJoin(boardData.member)
                .fetchJoin();

        List<BoardData> items = query.fetch();

        return items;
    }

}
