package com.kwon.springbootdeveloper.config;

import com.kwon.springbootdeveloper.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;
    
    // 스프링 시큐리티 기능 비활성화
    /*
     Spring Security는 기본적으로 모든 요청에 대해 보안 필터를 적용하지만
     이 설정을 통해 특정 경로나 리소스에 대해 보안 검사를 무시할 수 있음.
     */
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console()) // H2 데이터베이스 콘솔에 대한 요청을 보안 설정에서 제외
                .requestMatchers(new AntPathRequestMatcher("/static/**")); // /static/** 경로에 있는 모든 리소스에 대해 보안 필터 무시
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    // HttpSecurity : Spring Security에서 웹 기반 보안 구성을 설정하는 핵심 클래스
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth // 인증, 인가 설정
                        .requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll() // "/login", "/signup", "/user" 에 대해 모든 사용자가 접근할 수 있도록 허용
                        .anyRequest().authenticated()) // 그 외의 모든 요청은 인증이 필요함 ('authenticated()')
                .formLogin(formLogin -> formLogin // 폼 기반 로그인 설정
                        .loginPage("/login") // /login 경로로 접속하면 사용자 정의 로그인 페이지가 나타남
                        .defaultSuccessUrl("/articles") // 로그인 성공 후 리다이렉트 될 URL
                )
                .logout(logout -> logout // 로그아웃 설정
                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 리다이렉트 될 URL
                        .invalidateHttpSession(true) // 로그아웃 시 세션이 무효화되도록 설정. 사용자 세션 데이터 제거
                )
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                /*
                CSRF(Cross-Site Request Forgery)
                웹 애플리케이션에서 사용자의 의도와 무관하게 악의적인 요청을 보내는 공격 기법.
                사용자가 이미 로그인된 상태라는 점을 악용해, 사용자가 모르는 사이에 특정 웹 애플리케이션에서 의도하지 않은 동작을 수행하도록 만든다.
                (예 : 데이터 수정, 비밀번호 변경, 계정 삭제 등)
                !! GET 요청, API 서버 개발 시, 간단한 테스트 및 개발 환경 등 에선 CSRF 보안 기능이 필요 없다.
                그래도 앵간하면 활성화하자.
                 */
                .build();
    }

    // 인증 관리자 관련 설정
    @Bean
    // AuthenticationManager : 인증 로직을 담당. 사용자가 로그인할 때 입력한 자격 증명을 검사하여, 올바른 경우 인증 성공, 그렇지 않으면 예외 발생
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
        throws Exception {
        // DaoAuthenticationProvider : 기본 인증 제공자. 데이터베이스에서 사용자 정보를 조회하고 인증 처리
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // 사용자 정보 서비스 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
