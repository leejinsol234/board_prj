package org.board.project.japEx;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.board.project.commons.constants.MemberType;
import org.board.project.entities.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class Ex02 {
    @PersistenceContext
    private EntityManager em;
    @Test
    void test1(){
        Member member = Member.builder()
                .email("user01@test.org")
                .password("123456")
                .userNm("사용자01")
                .mtype(MemberType.USER)
                .mobile("010")
                .build();

        em.persist(member); //영속성 컨텍스트에 추가
        em.flush();

        Member member2 = em.find(Member.class, member.getUserNo()); //기본키로 조회
        System.out.println(member2); //수정 전

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        member2.setUserNm("(수정)사용자01");
        em.flush(); //update 쿼리 실행

        member2 = em.find(Member.class, member.getUserNo());
        System.out.println(member2);
    }

    @Test
    void test2(){}

}
