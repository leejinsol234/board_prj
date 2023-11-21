package org.board.project.models.member;

import lombok.RequiredArgsConstructor;
import org.board.project.commons.constants.MemberType;
import org.board.project.controllers.members.JoinValidator;
import org.board.project.controllers.members.RequestJoin;
import org.board.project.entities.Member;
import org.board.project.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository repository;
    private final PasswordEncoder encoder;
    private final JoinValidator validator;

    /**
     * 회원가입 처리
     * @param form
     * @param errors
     */
    public Member join(RequestJoin form, Errors errors) { //RequestJoin: 커맨드 객체
        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return null; //로그인 실패 시 아래 코드로 이동하지 못함
        }

        String hash = encoder.encode(form.getPassword());

        Member member = Member.builder()
                .email(form.getEmail())
                .userNm(form.getUserNm())
                .mobile(form.getMobile())
                .mtype(MemberType.USER)
                .password(hash)
                .build();

        save(member);

        member = repository.findByEmail(form.getEmail()).get();
        return member;
    }

    public void save(Member member) {
        String mobile = member.getMobile();
        if (mobile != null) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        repository.saveAndFlush(member);
    }
}
