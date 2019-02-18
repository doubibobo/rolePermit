package com.example.service;

import com.example.infra.persistence.sql.SysTableMapper;
import com.example.model.SysTable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysTableService {
    @Resource
    private SysTableMapper mapper;

    public int insert(SysTable sysTable) {
        return mapper.insert(sysTable);
    }

    public List<SysTable> selectAll() {
        return mapper.selectAll();
    }

    public int delete(Integer id) {
        return mapper.delete(id);
    }

    public int selectMax() { return mapper.selectMax(); }
}
