package org.board.project.models.member;

import lombok.Builder;
import lombok.Data;
import org.board.project.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data @Builder
public class MemberInfo implements UserDetails { //관리자가 통제할 수 있도록

    private String email;
    private String password;

    private Member member; //(email,password,authorities)를 제외한 나머지 추가적인 정보를 담기 위함.

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //권한
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { //계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정 잠금 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() { //계정 활성화 여부(예. 탈퇴 시 비활성화->false)
        return true;
    }
}
