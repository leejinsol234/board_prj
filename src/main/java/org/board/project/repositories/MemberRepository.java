package org.board.project.repositories;

import org.board.project.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    //null값으로 인한 오류를 방지하기 위해 Optional로 감싼다.
}
