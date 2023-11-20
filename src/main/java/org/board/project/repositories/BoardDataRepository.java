package org.board.project.repositories;

import org.board.project.entities.BoardData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardDataRepository extends JpaRepository<BoardData,Long> , QuerydslPredicateExecutor<BoardData> {
    @EntityGraph(attributePaths = "member") //직접 즉시 로딩할 부분을 정의해서도 n+1오류를 해결할 수 있다.
    List<BoardData> findBySubjectContaining(String key);

    List<BoardData> findByCreatedAtBetween(LocalDateTime sdate, LocalDateTime edate, Pageable pageable);
    //Pageable로 쿼리 메서드에서 페이징하기


    //쿼리 메서드로 패턴 만들기
    List<BoardData> findBySubjectContainingOrContentContainingOrderBySeqDesc(String subject,String content);

    //@Query
    @Query("SELECT b FROM BoardData b WHERE b.subject LIKE :key1 OR b.content LIKE :key2 ORDER BY b.seq DESC") //key1,key2는 파라미터
    List<BoardData> getList(@Param("key1")String subject, @Param("key2") String content);

    //JPQL로 FETCH JOIN을 통해 처음부터 테이블을 조인하도록 해서 n+1오류를 해결할 수 있다.
    @Query("SELECT b FROM BoardData b JOIN FETCH b.member")
    List<BoardData> getList2();
    //query building으로 직접 sql문을 작성하지 않을 수도 있다.
}
