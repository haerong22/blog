package com.example.jwt.filter;

import com.example.jwt.jwt.JwtContext;
import com.example.jwt.jwt.JwtProvider;
import com.example.jwt.jwt.MemberInfo;
import com.example.jwt.service.CustomAntPathMatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final CustomAntPathMatcher customAntPathMatcher;

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtContext.clear();

        log.info("auth filter");
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (customAntPathMatcher.match(method, requestURI)) { // 토큰 검증 필요한 uri 인지 확인

            String token = request.getHeader("Authorization");

            if (token != null) { // 헤더의 토큰 확인
                if (jwtProvider.validateToken(token)) { // 토큰 검증
                    JwtContext.setMemberInfo(jwtProvider.getClaim(token)); // 토큰 payload 값을 쓰레드 변수에 저장
                    filterChain.doFilter(request, response);
                }
            } else { // 헤더에 토큰 없을 경우
                if (!customAntPathMatcher.required(method, requestURI)){ // 토큰이 필수 인지 확인
                    JwtContext.setMemberInfo(new MemberInfo()); // 기본 객체 쓰레드 변수에 저장
                    filterChain.doFilter(request, response);
                } else { // 토큰이 없고 토큰이 필수라면 에러
                    throw new RuntimeException("권한 없음");
                }
            }
        } else { // 토큰 검증 필요 없다면 통과
            filterChain.doFilter(request, response);
        }
    }
}
