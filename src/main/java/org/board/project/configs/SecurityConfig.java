package org.board.project.configs;


import jakarta.servlet.http.HttpServletResponse;
import org.board.project.models.member.LoginFailureHandler;
import org.board.project.models.member.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableConfigurationProperties(FileUploadConfig.class)
public class SecurityConfig {
    //파일 경로는 바뀔 수 있음
    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //인증 설정 - 로그인 S
        http.formLogin(f -> {
            f.loginPage("/member/login") //post로 넘어갈 실제 페이지
                    .usernameParameter("email") // 변경사항(username으로 사용할 속성)
                    .passwordParameter("password") // password로 사용할 속성
                    .successHandler(new LoginSuccessHandler()) //로그인 성공 시 처리
                    .failureHandler(new LoginFailureHandler()); //로그인 실패 시 처리
        }); //DSL (Domain-Specific language) : 기능별로 나눠서 설정하는 데에 람다식이 효율적
        //인증 설정 - 로그인 E

        //logout 구현하기 S
        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");
        });
        //logout 구현하기 E

        //iframe 차단 해제 S //iframe은 보안 이슈로 막혀있으므로
        http.headers(c ->{
                            //같은 출처일 경우 허용
           c.frameOptions(o -> o.sameOrigin());
        });
        //iframe 차단 해제 E

        /* 인가 설정 - 접근 통제 S */
        http.authorizeHttpRequests(c->{
            c.requestMatchers("/mypage/**").authenticated() //회원 전용(로그인한 회원만 접근 가능한 주소 설정)
             //.requestMatchers("/admin/**").hasAuthority("ADMIN") //hasAuthority() :한 개의 권한만(관리자)
                    .requestMatchers("front/images/**", //권장사항 이슈로 WebSecurityCustomizer에서 이동함.
                            "/front/css/**",
                            "/front/js/**",

                            "/mobile/images/**",
                            "/mobile/css/**",
                            "/mobile/js/**",

                            "/admin/images/**",
                            "/admin/css/**",
                            "/admin/js/**",

                            "/common/css/**",
                            "/common/js/**",
                            "/common/images/**",
                            fileUploadConfig.getUrl()+"**").permitAll()
                    .anyRequest().permitAll(); //나머지 페이지는 권한 필요 없음
        });

        //회원 전용 서비스일 경우 로그인 페이지로 이동하고, 관리자 페이지일 경우 에러 메세지가 보이도록
        http.exceptionHandling(c ->{ //requestMatchers에서 인증 실패시 유입
            c.authenticationEntryPoint((req,resp,e)->{
                String URI = req.getRequestURI(); // 현재 주소를 확인
                if(URI.indexOf("/admin") != -1){ // 현재 주소가 관리자 페이지 일 때 401 응답 코드
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED"); //SC_UNAUTHORIZED 상수로 에러 메세지 전송
                } else { //회원 전용 페이지 일때(예. /mypage) -> 로그인 페이지로 이동
                    String url = req.getContextPath() + "/member/login";
                    resp.sendRedirect(url);
                }
            });
        });
        /* 인가 설정 - 접근 통제 E */

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //BCrypt로 비밀번호 인코딩
    }
}
