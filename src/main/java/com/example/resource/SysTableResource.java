package com.example.resource;

import com.alibaba.fastjson.JSON;
import com.example.redisUtils.ReloadRedisUtil;
import com.example.model.SysTable;
import com.example.service.SysTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/table")
public class SysTableResource {
    @Resource
    private SysTableService service;

    @PostMapping(value = "")
    public @ResponseBody String insertTable(@RequestBody SysTable sysTable) {
        int id = service.insert(sysTable);
        if (id > 0 && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "table", service.selectMax(), JSON.toJSONString(sysTable))) {
            return "success";
        } else {
            return "failure";
        }
    }

    @GetMapping(value = "")
    public  @ResponseBody List<SysTable> getTable() {
        return service.selectAll();
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody String deleteTable(@PathVariable("id") Integer id) {
        if (service.delete(id) > 0 && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "table", id, null)) {
            return "success";
        } else {
            return "failure";
        }
    }
}
