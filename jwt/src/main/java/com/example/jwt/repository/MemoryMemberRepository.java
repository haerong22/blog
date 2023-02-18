package com.example.jwt.repository;

import com.example.jwt.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryMemberRepository {

    private static final Map<Long, Member> members = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    public MemoryMemberRepository() {
        long id = MemoryMemberRepository.sequence.addAndGet(1);
        members.put(
                id,
                Member.builder()
                        .id(id)
                        .username("bobby")
                        .password("1234")
                        .build()
        );
    }

    public Member findById(Long id) {
        return members.get(id);
    }

    public Member findByUsername(String username) {
        return members.entrySet().stream()
                .filter(m -> m.getValue().getUsername().equals(username))
                .findFirst()
                .orElseThrow()
                .getValue();
    }
}
