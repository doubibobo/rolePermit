package com.example.service;

import com.example.infra.persistence.sql.SysRoleUserMapper;
import com.example.model.SysRoleUser;
import com.example.response.UserRoleResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleUserService {
    @Resource
    private SysRoleUserMapper mapper;

    public int insert(SysRoleUser record) {
        return mapper.insert(record);
    }

    public int delete(int id) {
        return mapper.delete(id);
    }

    public List<SysRoleUser> select() {
        return mapper.selectAll();
    }

    public SysRoleUser selectByRoleUserId(Integer id) {
        return mapper.selectByRoleUserId(id);
    }

    public List<UserRoleResponse> selectByUserId(Integer id){
        return mapper.selectByUserId(id);
    }

    public List<UserRoleResponse> selectAllByUserId() {
        return mapper.selectAllByUserId();
    }

    public int selectMax() {
        return mapper.selectMax();
    }
}
