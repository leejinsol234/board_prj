package org.board.project.models.member;

import lombok.RequiredArgsConstructor;
import org.board.project.entities.Member;
import org.board.project.repositories.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;

    private Object member;


    //db에서 조회하기 위해
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //회원 정보 조회
        //매개변수는 MemberInfo의 username(email)

        Member member = repository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
        // username이 null일 때는 UsernameNotFoundException로 던져진다.

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getMtype().name()));
        System.out.println(member);
        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(authorities)
                .member(member)
                .build();
    }
}
