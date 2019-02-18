package com.example.infra.persistence.sql;

import com.example.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    int insert(SysRole record);

    int delete(int id);

    int update(SysRole record);

    List<SysRole> selectAll();

    SysRole selectOne(int id);

    int selectMax();
}