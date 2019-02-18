package com.example.service;

import com.example.infra.persistence.sql.UserMapper;
import com.example.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper mapper;

    public int insert(User user) {
        return mapper.insert(user);
    }

    public int delete(int id) {
        return mapper.delete(id);
    }

    public List<User> select() {
        return mapper.selectAll();
    }

    public int selectMax() {
        return mapper.selectMax();
    }
}
