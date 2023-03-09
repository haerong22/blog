package com.example.datasource.mapper;

import com.example.datasource.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Mapper
public interface TestMapper {

    TestDto getUrl(int id);
}
