package com.example.jwt.repository;

import com.example.jwt.entity.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryResourceRepository {

    private static final List<Resource> resources = new ArrayList<>();

    public MemoryResourceRepository() {
        resources.add(
                Resource.builder()
                        .id(1L)
                        .method(HttpMethod.GET.name())
                        .pattern("/api/token/required")
                        .required(true)
                        .build()
        );
        resources.add(
                Resource.builder()
                        .id(2L)
                        .method(HttpMethod.GET.name())
                        .pattern("/api/token/optional")
                        .required(false)
                        .build()
        );
    }

    public List<Resource> findAll() {
        return resources;
    }
}
