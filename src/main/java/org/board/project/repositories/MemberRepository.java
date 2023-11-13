package org.board.project.repositories;

import org.board.project.entities.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    //null값으로 인한 오류를 방지하기 위해 Optional로 감싼다.
}
