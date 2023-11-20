package org.board.project.japEx;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.board.project.commons.constants.MemberType;
import org.board.project.entities.BoardData;
import org.board.project.entities.Member;
import org.board.project.models.board.BoardListService;
import org.board.project.repositories.BoardDataRepository;
import org.board.project.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "spring.properties.active=test")
public class Ex02 {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardDataRepository boardDataRepository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private BoardListService listService;

    @BeforeEach
    void init(){
        Member member = Member.builder()
                .email("user01@test.org")
                .password("12345678")
                .userNm("사용자01")
                .mtype(MemberType.USER)
                .mobile("01011111111")
                .build();

        memberRepository.saveAndFlush(member);
        //게시글 10개 추가
        List<BoardData> items = new ArrayList<>();
        for(int i=1;i<=10;i++){
            BoardData item = BoardData.builder()
                    .subject("제목"+i)
                    .content("내용"+i)
                    .member(member)
                    .build();
            items.add(item);

            boardDataRepository.saveAllAndFlush(items);
            em.clear();
        }
    }


        @Test
        void test1(){
        List<BoardData> items = boardDataRepository.findAll(); //게시글 10개 모두 조회.1차 쿼리
        for(BoardData item : items){
            Member member = item.getMember();
            String email = member.getEmail(); //2차 쿼리
            System.out.println(email);
        }

    }

    @Test
    void test2(){
        List<BoardData> items = boardDataRepository.getList2();

    }

    @Test
    void test3(){
        List<BoardData> items = listService.getList();
    }

    @Test
    void test4(){}
    List<BoardData> items = boardDataRepository.findBySubjectContaining("목");

    @Test
    void test5(){
        Member member = memberRepository.findByEmail("user01@test.org").orElse(null);
        List<BoardData> items = member.getItems(); //실제 데이터를 사용할 때 가져오게 됨
        items.stream().forEach(System.out::println);

        memberRepository.delete(member);
        memberRepository.flush();
        //참조 무결성 제약 조건에 위배됨(

    }

}


