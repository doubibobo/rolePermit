package com.example.infra.persistence.sql;

import com.example.model.SysPermitRole;
import com.example.response.RolePermitResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysPermitRoleMapper {
    int insert(SysPermitRole record);

    int delete(int id);

    List<SysPermitRole> selectAll();

    SysPermitRole selectAllByPermitRoleId(Integer id);

    List<RolePermitResponse> selectByRoleId(Integer id);

    List<RolePermitResponse> selectAllByRoleId();

    List<SysPermitRole> selectPermitIdOneRole(Integer id);

    int selectMax();
}