package org.koreait.repositories;

import org.koreait.entities.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardDataRepository extends JpaRepository<BoardData, Long>, QuerydslPredicateExecutor<BoardData> {

    @Scheduled(cron = "0 0 1 * * *")
    @Query("SELECT b.seq, b.subject, b.content, b.poster FROM BoardData b where createdAt = SYSDATE-1 group by ")
    List<BoardData> findByCreatedAtBetween(LocalDateTime yesterday, LocalDateTime today, Pageable pageable);



    //쿼리 메서드로 패턴 만들기
    List<BoardData> findBySubjectContainingOrContentContainingOrderBySeqDesc(String subject,String content);

    //@Query
    @Query("SELECT b FROM BoardData b WHERE b.subject LIKE :key1 OR b.content LIKE :key2 ORDER BY b.seq DESC") //key1,key2는 파라미터
    List<BoardData> getList(@Param("key1")String subject, @Param("key2") String content);
}
