package com.example.jwt.service;

import com.example.jwt.entity.Resource;
import com.example.jwt.repository.MemoryResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Component
public class CustomAntPathMatcher extends AntPathMatcher {

    private final List<Resource> resources;

    public CustomAntPathMatcher(MemoryResourceRepository memoryResourceRepository) {
        this.resources = memoryResourceRepository.findAll();
    }

    public boolean match(String method, String path) {
        return resources.stream()
                .anyMatch(r -> r.getMethod().equals(method) && super.match(r.getPattern(), path));
    }

    public boolean required(String method, String path) {
        return resources.stream()
                .filter(r -> r.getMethod().equals(method) && super.match(r.getPattern(), path))
                .findAny()
                .orElseThrow()
                .isRequired();
    }
}
