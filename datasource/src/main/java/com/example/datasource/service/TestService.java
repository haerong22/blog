package com.example.datasource.service;

import com.example.datasource.dto.TestDto;
import com.example.datasource.dto.TestResponse;
import com.example.datasource.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService {

    private final TestMapper testMapper;

    public void test() {
        final List<Integer> list = List.of(1, 2, 3, 1);
        list.forEach(id -> {
            System.out.println(greetings(id));
        });
    }

    private TestResponse greetings(final int id) {
        final String prefix = "http://test.com/";

        final TestDto url = testMapper.getUrl(id);
//        url.setUrl(prefix + url.getUrl());
//        return url;

        return new TestResponse(prefix + url.getUrl());
    }
}
