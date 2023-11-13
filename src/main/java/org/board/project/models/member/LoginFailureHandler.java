package org.board.project.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.board.project.commons.Utils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        //값을 유지하는 범위가 더 넓어야 하므로 session을 사용한다.
        HttpSession session = request.getSession();

        //session 비우기 위한 초기화
        Utils.loginInit(session);

        String email =request.getParameter("email");
        String password = request.getParameter("password");
        //email 또는 password가
        boolean isRequiredFieldCheck = false;

        session.setAttribute("email",email);

        //필수 항목 검증(email,password) S
        if(email == null || email.isBlank()){
            session.setAttribute("NotBlank_email", Utils.getMessage("NotBlank.email","validation"));
            isRequiredFieldCheck = true;
        }

        if(password == null || password.isBlank()){
            session.setAttribute("NotBlank_password", Utils.getMessage("NotBlank.password","validation"));
            isRequiredFieldCheck = true;
        }
        //필수 항목 검증(email,password) E


        if(!isRequiredFieldCheck) { //email 또는 password가 잘못된 경우
            session.setAttribute("globalError", Utils.getMessage("Login.fail", "validation"));
        }



        response.sendRedirect(request.getContextPath()+"/member/login"); //로그인 실패 시 이동할 경로
    }


}
