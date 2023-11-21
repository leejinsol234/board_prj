package org.board.project.repositories;

import org.board.project.entities.Member;
import org.board.project.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>, QuerydslPredicateExecutor<Member> {
    Optional<Member> findByEmail(String email);
    //null값으로 인한 오류를 방지하기 위해 Optional로 감싼다.

    default boolean exists(String email) {
        return exists(QMember.member.email.eq(email)); //이메일 동일 여부만 확인
        //간단한 조건식일 때는 Q객체로 접근해도 됨.
    }
}
