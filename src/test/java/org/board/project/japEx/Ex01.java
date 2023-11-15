package org.board.project.japEx;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.board.project.commons.constants.MemberType;
import org.board.project.entities.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@TestPropertySource //테스트용 프로필 설정(locations = "classpath:application-test.yml")
@Transactional //트랜잭션 기능이 적용된 프록시 객체가 생성되며, 트랜잭션 성공 여부에 따라 Commit 또는 Rollback 작업이 이루어진다.
public class Ex01 {
    @PersistenceContext //@Autowired와 동일한 기능.
    private EntityManager em; //EntityManager: 엔티티의 영속성 관리 -> 트랜잭션 수동 관리
    @BeforeEach
    void init(){
        Member member= new Member();
        //member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setPassword("123456");
        member.setUserNm("사용자01");
        member.setMobile("01011111111");
        member.setMtype(MemberType.USER);

        em.persist(member); //변화 감지 상태
        em.flush(); //insert 쿼리 실행
        em.clear(); //영속성 컨텍스트 모두 비우기(db에만 있음)

    }
    @Test
    void test2(){
        Member member = em.find(Member.class, 1L); //db에서 영속성 컨텍스트로 추가
        System.out.println(member);

        Member member2 = em.find(Member.class, 1L); //영속성 컨텍스트에서 조회. 쿼리 실행X
        System.out.println(member2);

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Users AS m WHERE m.email LIKE :key", Member.class); //entity기준으로 쿼리 수행(Member는 entity클래스명)
        query.setParameter("key","%user%"); //영속성 상태에 있다.
        Member member3 = query.getSingleResult();
        member3.setUserNm("(수정)사용자01");
        em.flush();
    }
    @Test
    void test1(){
        Member member= new Member();
        //member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setPassword("123456");
        member.setUserNm("사용자01");
        member.setMobile("01011111111");
        member.setMtype(MemberType.USER);

        em.persist(member); //변화 감지 상태
        em.flush(); //insert 쿼리 실행

        em.detach(member); //영속성 분리, 변화 감지X

        member.setUserNm("(수정)사용자01");
        em.flush(); //update 쿼리 실행

        em.merge(member); //분리된 영속성을 영속 상태로, 변화 감지O
        em.flush(); //조회(select) 후 변화 감지해서 insert 또는 update 쿼리 실행
        
        //em.remove(member);
        //em.flush(); //delete 쿼리 실행
    }

    @Test
    void test3(){

    }
}
