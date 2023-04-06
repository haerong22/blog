package com.example.auth.repository;

import com.example.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    int countByUsernameAndPassword(String username, String password);

}
