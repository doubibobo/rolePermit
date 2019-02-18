package com.example.service;

import com.example.infra.persistence.sql.SysRoleMapper;
import com.example.model.SysRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleService {
    @Resource
    private SysRoleMapper mapper;

    public int insert(SysRole role) {
        return mapper.insert(role);
    }

    public int delete(int id) {
        return mapper.delete(id);
    }

    public int update(SysRole role) {
        return mapper.update(role);
    }

    public List<SysRole> selectAll() {
        return mapper.selectAll();
    }

    public SysRole selectOne(int id) {
        return mapper.selectOne(id);
    }

    public int selectMax() { return mapper.selectMax(); }
}
