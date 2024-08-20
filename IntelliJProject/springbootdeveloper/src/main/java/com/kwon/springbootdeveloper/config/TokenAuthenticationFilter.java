package com.kwon.springbootdeveloper.config;

import com.kwon.springbootdeveloper.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    // JWT 토큰을 검증하고 인증 정보를 제공하는 TokenProvider 객체
    private final TokenProvider tokenProvider;

    // HTTP 요청 헤더에서 Authorization 헤더의 키 값
    private final static String HEADER_AUTHORIZATION = "Authorization";

    // Authorization 헤더 값의 접두사
    private final static String TOKEN_PREFIX = "Bearer";

    /**
     * 요청이 필터를 통과할 때마다 실행되는 메서드
     * JWT 토큰의 유효성을 검사하고, 유효한 경우 인증 정보를 설정합니다.
     *
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @param filterChain FilterChain 객체
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        // 요청 헤더에서 Authorization 키 값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        // 가져온 값에서 "Bearer " 접두사를 제거하고 실제 토큰 값만 추출
        String token = getAccessToken(authorizationHeader);

        // 가져온 토큰이 유효한지 확인하고, 유효한 경우 인증 정보를 설정
        if (tokenProvider.validToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 요청과 응답을 전달
        filterChain.doFilter(request, response);
    }

    /**
     * Authorization 헤더에서 JWT 토큰 값을 추출합니다.
     *
     * @param authorizationHeader Authorization 헤더 값
     * @return 접두사 "Bearer "를 제외한 JWT 토큰 값
     */
    private String getAccessToken(String authorizationHeader) {
        // Authorization 헤더가 null이 아니고 "Bearer "로 시작하는 경우에만 토큰 값을 추출
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            // "Bearer "를 제거하고 실제 토큰 값만 반환
            return authorizationHeader.substring(TOKEN_PREFIX.length()).trim();
        }
        return null; // Authorization 헤더가 없거나 "Bearer "로 시작하지 않으면 null 반환
    }
}
