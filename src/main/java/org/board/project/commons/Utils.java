package org.board.project.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {
    private static ResourceBundle validationsBundle;
    private static ResourceBundle errorsBundle;
    private final HttpServletRequest request;
    private final HttpSession session;


    //처음 로드 시 초기화
    static {
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

    public static String getMessage(String code, String bundleType) {
        bundleType = Objects.requireNonNullElse(bundleType, "validation");
        ResourceBundle bundle = bundleType.equals("errors") ? errorsBundle : validationsBundle;
        try{
            return bundle.getString(code);
        } catch (Exception e){
            return null;
        }
    }

    public boolean isMobile(){

        String device = (String) session.getAttribute("device");
        if(device!=null) {
            return device.equals("mobile");
        }

        // 요청 헤더의 user-agent로 사용자의 기기 정보를 알 수 있다.
        // 모바일 기기인지 확인하는 정규 표현식
        boolean isMobile = request.getHeader("User-Agent").matches(".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*");

        return isMobile;
    }

    public String tpl(String tplPath){
        return String.format("%s/"+tplPath, isMobile()? "mobile":"front");
    }

    public static void loginInit(HttpSession session){
        //공유할 수 있도록 Utils에서 정의
        session.removeAttribute("email");
        session.removeAttribute("NotBlank_email");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("globalError");
    }
}
