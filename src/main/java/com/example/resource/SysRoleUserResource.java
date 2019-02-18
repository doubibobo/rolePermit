package com.example.resource;

import com.alibaba.fastjson.JSON;
import com.example.redisUtils.ReloadRedisUtil;
import com.example.model.SysRoleUser;
import com.example.response.UserRoleResponse;
import com.example.service.SysRoleUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "role_user")
public class SysRoleUserResource {
    @Resource
    private SysRoleUserService service;

    @PostMapping(value = "")
    public @ResponseBody String insert(@RequestBody SysRoleUser roleUser) {
        int id = service.insert(roleUser);
        int max = service.selectMax();
        if (id > 0
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "role_user", max, JSON.toJSONString(roleUser))
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "extend_role_user", max, JSON.toJSONString(service.selectByRoleUserId(max)))) {
            return "success";
        } else {
            return "failure";
        }
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody String delete(@PathVariable("id") Integer id) {
        if (service.delete(id) > 0
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "role_user", id, null)
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "extend_role_user", id, null)) {
            return "success";
        } else {
            return "failure";
        }
    }

    @GetMapping(value = "")
    public @ResponseBody List<SysRoleUser> selectAll() {
        return service.select();
    }

    @GetMapping(value = "/extend")
    public @ResponseBody List<UserRoleResponse> selectAllByUserId() {
        return service.selectAllByUserId();
    }

    @GetMapping(value = "/extend/{id}")
    public @ResponseBody List<UserRoleResponse> selectByUserId(@PathVariable("id") Integer id) {
        return service.selectByUserId(id);
    }
}
