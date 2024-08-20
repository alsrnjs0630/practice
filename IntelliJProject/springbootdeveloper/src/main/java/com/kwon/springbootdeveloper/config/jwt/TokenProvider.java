package com.kwon.springbootdeveloper.config.jwt;

import com.kwon.springbootdeveloper.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    /**
     * 주어진 사용자와 만료 시간을 기준으로 JWT 토큰을 생성합니다.
     *
     * @param user 토큰을 생성할 사용자
     * @param expiredAt 토큰의 만료 시간
     * @return JWT 토큰 문자열
     */
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();  // 현재 시간
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user); // 만료 시간과 사용자 정보를 기반으로 토큰 생성
    }

    /**
     * 만료일과 사용자 정보를 이용하여 JWT 토큰을 생성합니다.
     *
     * @param expiry 토큰의 만료일
     * @param user 사용자 정보
     * @return JWT 토큰 문자열
     */
    private String makeToken(Date expiry, User user) {
        Date now = new Date();  // 현재 시간

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // JWT 타입으로 헤더 설정
                .setIssuer(jwtProperties.getIssuer()) // 발급자(iss) 설정
                .setIssuedAt(now) // 발급일(iat) 설정
                .setExpiration(expiry) // 만료일(exp) 설정
                .setSubject(user.getEmail()) // 주제(sub)로 사용자 이메일 설정
                .claim("id", user.getId()) // 사용자 ID를 클레임으로 추가
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // HS256 알고리즘과 비밀 키로 서명
                .compact(); // JWT 토큰 문자열 생성
    }

    /**
     * JWT 토큰의 유효성을 검증합니다.
     *
     * @param token 검증할 JWT 토큰
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 서명 검증을 위한 비밀 키 설정
                    .parseClaimsJws(token); // 토큰을 파싱하여 검증
            return true; // 예외가 발생하지 않으면 토큰이 유효함
        } catch (Exception e) { // 예외가 발생하면 토큰이 유효하지 않음
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails
                .User(claims.getSubject(), "", authorities), token, authorities);
    }

    /**
     * JWT 토큰을 기반으로 인증 정보를 가져옵니다.
     *
     * @param token JWT 토큰
     * @return 인증 정보(Authentication)
     */
    public Long getUserId(String token) {
        Claims claims = getClaims(token); // 토큰에서 클레임 추출
        return claims.get("id", Long.class); // 클레임에서 사용자 ID를 가져와서 Authentication 객체 생성
    }

    /**
     * JWT 토큰에서 클레임을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 클레임(Claims)
     */
    private Claims getClaims(String token) {
        return Jwts.parser() // 클레임 파싱
                .setSigningKey(jwtProperties.getSecretKey()) // 서명 검증을 위한 비밀 키 설정
                .parseClaimsJws(token) // 토큰을 파싱하여 클레임 추출
                .getBody(); // 클레임의 본문(body) 반환
    }
}
