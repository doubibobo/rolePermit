package com.example.resource;

import com.alibaba.fastjson.JSON;
import com.example.redisUtils.ReloadRedisUtil;
import com.example.model.SysRole;
import com.example.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class SysRoleResource {
    @Resource
    private SysRoleService service;

//    {
//        "roleName" : "系统超级管理员",
//        "roleDescription" : "系统最顶级的管理员，拥有除编码者的最高权限",
//        "roleRank" : 10
//    }
    @PostMapping(value = "")
    public @ResponseBody String insert(@RequestBody SysRole role) {
        if (service.insert(role) > 0 && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "role", service.selectMax(), JSON.toJSONString(role))) {
            return "success";
        } else {
            return "failure";
        }
    }

    /**
     * TODO: 判断角色等级，只有等级高于要删除角色的时候才可以进行删除
     * @param id 角色Id
     * @return 删除是否成功操作
     */
    @DeleteMapping(value = "/{id}")
    public @ResponseBody String delete(@PathVariable("id") Integer id) {
        if (service.delete(id) > 0 && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "role", id, null)) {
            return "success";
        } else {
            return "failure";
        }
    }

//    {
//        "roleName" : "系统超级管理员",
//        "roleDescription" : "系统最顶级的管理员，拥有除编码者的最高权限",
//        "roleRank" : 10,
//        "roleId" : 1
//    }
    /**
     * TODO: 判断角色等级，只有等级高于要删除角色的时候才可以进行修改
     * @param role 角色Id
     * @return 删除是否成功操作
     */
    @PutMapping(value = "")
    public @ResponseBody String update(@RequestBody SysRole role) {
        if (service.update(role) > 0 && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.UPDATE, "role", role.getRoleId(), JSON.toJSONString(role))) {
            return "success";
        } else {
            return "failure";
        }
    }

    @GetMapping(value = "")
    public @ResponseBody List<SysRole> selectAll() {
        return service.selectAll();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody SysRole selectOne(@PathVariable("id") Integer id) {
        return service.selectOne(id);
    }
}
