package org.board.project.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.board.project.entities.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JSONTest {

    private ObjectMapper om;
    @BeforeEach
    void init(){
        om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
    }

    @Test
    void test1() throws JsonProcessingException {
        Member member = Member.builder()
                .email("user01@test.org")
                .password("12345678")
                .userNm("사용자")
                .build();
        member.setCreatedAt(LocalDateTime.now());
        // 여기까지는 자바 객체

        // JSON 형태로 바꾸기
        String json = om.writeValueAsString(member);
        System.out.println(json);

        //단일 객체일 때는 Java객체로 바꾸려고 하는 클래스를 두 번째 매개변수에 넣는다.
        Member member2 = om.readValue(json, Member.class);
        System.out.println(member2);
    }

    @Test
    void test2() throws JsonProcessingException {

        List<Member> members = new ArrayList<>();
        for(int i =1; i<=3; i++) {
            Member member = Member.builder()
                    .email("user01@test.org")
                    .password("12345678")
                    .userNm("사용자")
                    .build();

            members.add(member);
        }

        String json = om.writeValueAsString(members);
        System.out.println(json);
        //객체가 여러 개거나 입체적인 형태일 때는 TypeReference로 변환시킬 수 있다.
        List<Member> members2 = om.readValue(json, new TypeReference<List<Member>>() {});
        members2.stream().forEach(System.out::println);
    }
}
