package com.example.resource;

import com.alibaba.fastjson.JSON;
import com.example.redisUtils.ReloadRedisUtil;
import com.example.model.SysPermit;
import com.example.service.SysPermitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/permit")
public class SysPermitResource {
    @Resource
    private SysPermitService service;

//    {
//        "permitName" :"查询",
//        "permitDescription" :"权限控制",
//        "permitUrl" :"permit",
//        "permitTable" :1,
//        "permitRank" :10,
//        "permitMethod" : "GET"
//    }
    @PostMapping(value = "")
    public @ResponseBody String insertPermit(@RequestBody SysPermit sysPermit) {
        int id = service.insert(sysPermit);
        if (id > 0 && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "permit", service.selectMax(), JSON.toJSONString(sysPermit))) {
            return "success";
        } else {
            return "failure";
        }
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody String deletePermit(@PathVariable("id") Integer id) {
        if (service.delete(id) > 0 && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "permit", id, null)) {
            return "success";
        } else {
            return "failure";
        }
    }

    @PutMapping(value = "")
    public @ResponseBody String updatePermit(@RequestBody SysPermit sysPermit) {
        if (service.update(sysPermit) > 0 && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.UPDATE, "permit", sysPermit.getPermitId(), JSON.toJSONString(sysPermit))) {
            return "success";
        } else {
            return "failure";
        }
    }

    @GetMapping(value = "")
    public @ResponseBody List<SysPermit> selectPermit() {
        return service.selectAll();
    }
}
