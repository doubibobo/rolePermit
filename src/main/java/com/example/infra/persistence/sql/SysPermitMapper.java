package com.example.infra.persistence.sql;

import com.example.model.SysPermit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermitMapper {
    int insert(SysPermit record);

    int delete(Integer id);

    int update(SysPermit record);

    List<SysPermit> selectAll();

    int selectMax();
}