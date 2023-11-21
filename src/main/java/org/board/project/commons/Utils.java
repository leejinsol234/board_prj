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
    private static ResourceBundle errorsBundle; //MessageSource를 참조함
    private static ResourceBundle commonsBundle;

    private final HttpServletRequest request;
    private final HttpSession session;

    //처음 로드 시 초기화
    static {
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
        commonsBundle = ResourceBundle.getBundle("messages.commons"); //팀플 코드에 추가하기!
    }

    public static String getMessage(String code, String bundleType) {
        //메세지 코드 가져오기
        bundleType = Objects.requireNonNullElse(bundleType, "validation");
        ResourceBundle bundle = null;
        if(bundleType.equals("common")){
            bundle = commonsBundle;
        } else if (bundleType.equals("error")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }

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

    /**
     * 단일 요청 데이터 조회
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 복수개 요청 데이터 조회
     *
     */
    public String[] getParams(String name) { //name값이 여러 개인 경우
        return request.getParameterValues(name);
    }


    public static int getNumber(int num, int defaultValue) {
        //페이징 처리 시 기본값 설정
        return num <= 0 ? defaultValue : num;
    }

    /**
     * 비회원 구분 UID
     * 비회원 구분은 IP + 브라우저 종류
     *
     */
    public int guestUid() {
        String ip = request.getRemoteAddr(); //ip 주소
        String ua = request.getHeader("User-Agent"); //브라우저 정보

        return Objects.hash(ip, ua); //비회원 구분과 비회원들의 조회수 카운트 통제를 위해(회원은 회원 정보로)
    }
}
