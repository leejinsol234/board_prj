package org.board.project.commons;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.board.project.commons.constants.MemberType;
import org.board.project.entities.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    public boolean isLogin(){
        return getMember() != null;  //세션에 값이 있으면 로그인한 상태이다.
    }

    /**
     * 관리자 여부 체크
     * return true -> 관리자
     */
    public boolean isAdmin() {
        //관리자 여부 확인(관리자 페이지 접근 통제를 위해)
        return isLogin() && getMember().getMtype() == MemberType.ADMIN;
    }

    //세션 처리한 멤버 정보 가져오기
    public Member getMember(){
        return (Member) session.getAttribute("loginMember");
    }
}
