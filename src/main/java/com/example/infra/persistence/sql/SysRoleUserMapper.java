package com.example.infra.persistence.sql;

import com.example.model.SysRoleUser;
import com.example.response.UserRoleResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {
    int insert(SysRoleUser record);

    int delete(int id);

    List<SysRoleUser> selectAll();

    SysRoleUser selectByRoleUserId(Integer id);

    List<UserRoleResponse> selectByUserId(Integer id);

    List<UserRoleResponse> selectAllByUserId();

    int selectMax();
}