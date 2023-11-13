package org.board.project.configs;

import org.board.project.models.member.LoginFailureHandler;
import org.board.project.models.member.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(f -> {
            f.loginPage("/member/login") //post로 넘어갈 실제 페이지
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler()) //로그인 성공 시 처리
                    .failureHandler(new LoginFailureHandler()); //로그인 실패 시 처리
        }); //DSL (Domain-Specific language)
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //BCrypt로 비밀번호 인코딩
    }
}
