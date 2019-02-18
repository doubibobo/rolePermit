package com.example.service;

import com.example.infra.persistence.sql.SysPermitMapper;
import com.example.model.SysPermit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysPermitService {
    @Resource
    private SysPermitMapper mapper;

    public int insert(SysPermit sysPermit) {
        return mapper.insert(sysPermit);
    }

    public int delete(Integer id) { return mapper.delete(id); }

    public int update(SysPermit sysPermit) { return mapper.update(sysPermit); }

    public List<SysPermit> selectAll() {
        return mapper.selectAll();
    }

    public int selectMax() { return mapper.selectMax(); }
}
