package com.example.infra.persistence.sql;

import com.example.model.SysTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysTableMapper {
    int insert(SysTable record);

    List<SysTable> selectAll();

    int delete(Integer id);

    int selectMax();
}