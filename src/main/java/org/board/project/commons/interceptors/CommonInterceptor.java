package org.board.project.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.board.project.commons.Utils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        init(request);
        //모든 주소에서 들어올 수 있으므로
        return true;
    }

    private void init(HttpServletRequest request){
        HttpSession session = request.getSession(); //초기화 시 세션을 가져오기
        /* PC, Mobile 수동 변경 처리 S */
        String device = request.getParameter("device");
        // 주소에 device값이 들어오면
        if(device != null && !device.isBlank()){
            session.setAttribute("device", device.toLowerCase().equals("mobile")?"mobile":"pc");
        }
        /* PC, Mobile 수동 변경 처리 E */

        /* 로그인 페이지가 아닐 경우 로그인 유효성 검사 세션 삭제 처리 */
        //getRequestURI로 주소 가져오기
        String URI = request.getRequestURI();
        if(URI.indexOf("/member/login") == -1){ // /member/login이 아닌 다른 페이지에서 이동할 경우
            Utils.loginInit(session);
        }

    }
}
