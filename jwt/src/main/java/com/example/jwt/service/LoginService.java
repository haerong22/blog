package com.example.jwt.service;

import com.example.jwt.dto.LoginRequest;
import com.example.jwt.entity.Member;
import com.example.jwt.jwt.JwtProvider;
import com.example.jwt.repository.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemoryMemberRepository memoryMemberRepository;
    private final JwtProvider jwtProvider;

    public String login(LoginRequest loginRequest) {
        Member member = memoryMemberRepository.findByUsername(loginRequest.getUsername());

        if (member.checkPassword(loginRequest.getPassword())) {
            return jwtProvider.createToken(member);
        }

        throw new RuntimeException("로그인 실패");
    }

}
