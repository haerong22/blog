package com.example.jwt.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtAuthorization.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("JwtAuthorizationArgumentResolver 동작!!");

        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        if (httpServletRequest != null) {
            String token = httpServletRequest.getHeader("Authorization");

            if (token != null && !token.trim().equals("")) {

                if (jwtProvider.validateToken(token)) {
                    return jwtProvider.getClaim(token);
                }
            }

            JwtAuthorization annotation = parameter.getParameterAnnotation(JwtAuthorization.class);
            if (annotation != null && !annotation.required()) {
                return new MemberInfo();
            }
        }

        throw new RuntimeException("권한 없음.");
    }
}