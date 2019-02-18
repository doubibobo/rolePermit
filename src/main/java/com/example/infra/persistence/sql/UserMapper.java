package com.example.infra.persistence.sql;

import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    int insert(User record);

    int delete(int id);

    List<User> selectAll();

    int selectMax();
}