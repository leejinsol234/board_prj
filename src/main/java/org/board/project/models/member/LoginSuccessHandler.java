package org.board.project.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.board.project.commons.Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Objects;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //로그인 성공 시 이동할 특정 페이지(로그인 직전 머물렀던) 설정
        /*
        * 요청 데이터(request에 있음) redirectUrl값이 있으면 이동, 없으면 main페이지로 이동.
        * */
        String redirectUrl = Objects.requireNonNullElse(request.getParameter("redirectUrl"),"/");
        response.sendRedirect(request.getContextPath()+redirectUrl);

        //session 초기화
        HttpSession session = request.getSession();
        Utils.loginInit(session);

    }
}
