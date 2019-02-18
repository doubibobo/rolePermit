package com.example.service;

import com.example.infra.persistence.sql.SysPermitRoleMapper;
import com.example.model.SysPermitRole;
import com.example.response.RolePermitResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class SysPermitRoleService {
    @Resource
    private SysPermitRoleMapper mapper;

    public int insert(SysPermitRole record) {
        return mapper.insert(record);
    }

    public int delete(int id) {
        return mapper.delete(id);
    }

    public List<SysPermitRole> selectPermitIdOneRole(int id) { return mapper.selectPermitIdOneRole(id); }

    public List<SysPermitRole> selectAll() {
        return mapper.selectAll();
    }

    public SysPermitRole selectAllByPermitRoleId(Integer id) {
        return mapper.selectAllByPermitRoleId(id);
    }

    public List<RolePermitResponse> selectAllByRoleId() {
        return mapper.selectAllByRoleId();
    }

    public List<RolePermitResponse> selectByRoleId(Integer id) {
        return mapper.selectByRoleId(id);
    }

    public int selectMax() {
        return mapper.selectMax();
    }
}
