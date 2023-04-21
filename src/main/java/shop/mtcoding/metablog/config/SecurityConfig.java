package shop.mtcoding.metablog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import shop.mtcoding.metablog.core.auth.MyUserDetails;
import shop.mtcoding.metablog.core.exception.ssr.Exception400;
import shop.mtcoding.metablog.core.util.Script;
import shop.mtcoding.metablog.model.user.User;

@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CSRF 해제
        http.csrf().disable(); // postman 접근해야 함!! - CSR 할때!!

        // 2. frame option 해제
        http.headers().frameOptions().disable();

        // 3. Form 로그인 설정
        http.formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")
                .successHandler((req, resp, authentication) -> {
                    // 템플릿엔진에서 꺼내쓰기 위해 적용함
                    MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
                    req.getSession().setAttribute("sessionUser", myUserDetails.getUser());
                    System.out.println("디버그 : 로그인이 완료되었습니다");
                    resp.sendRedirect("/");
                })
                .failureHandler((req, resp, ex) -> {
                    resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                    resp.setContentType("text/html; charset=utf-8");
                    resp.getWriter().println(Script.back("유저네임 혹은 패스워드가 잘못입력되었습니다"));
                })
                .and()
                .logout()
                .logoutSuccessUrl("/");

        // 3. 인증, 권한 필터 설정
        http.authorizeRequests(
                authorize -> authorize.antMatchers("/api/s/**").authenticated()
                        .antMatchers("/s/**").authenticated()
                        .anyRequest().permitAll()
        );

        return http.build();
    }
}
