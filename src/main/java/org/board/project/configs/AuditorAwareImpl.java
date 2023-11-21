package org.board.project.configs;

import org.board.project.models.member.MemberInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> { //AuditorAware: 로그인한 회원정보가 디비에 저장되도록

    @Override
    public Optional<String> getCurrentAuditor() {
        //로그인 정보 확인
        String email = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() instanceof MemberInfo){
            MemberInfo memberInfo = (MemberInfo)auth.getPrincipal();
            email = memberInfo.getEmail();
        }

        return Optional.ofNullable(email);
    }
}
