package org.board.project.configs;

import org.board.project.models.member.MemberInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    String email = null;
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //getAuthentication(): 로그인한 회원정보를 담고 있다.
        //Object principal = auth.getPrincipal();
        //System.out.printf("principal: "+ principal);
        //비회원- String(문자열): anonymousUser
        //회원- Datails 구현 객체

        //객체의 출처를 확인
        if(auth != null && auth.getPrincipal() instanceof MemberInfo){
          MemberInfo member = (MemberInfo)auth.getPrincipal();
          email = member.getEmail();
        }

        return Optional.ofNullable(email);
    }
}
