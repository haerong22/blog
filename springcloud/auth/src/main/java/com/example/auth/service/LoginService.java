package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public boolean login(LoginRequest request) {
        log.info("login process!! ==> {}", request.getUsername());
        return memberRepository.countByUsernameAndPassword(request.getUsername(), request.getPassword()) == 1;
    }
}
