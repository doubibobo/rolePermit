package com.example.resource;

import com.alibaba.fastjson.JSON;
import com.example.redisUtils.ReloadRedisUtil;
import com.example.model.SysPermitRole;
import com.example.response.RolePermitResponse;
import com.example.service.SysPermitRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "permit_role")
public class SysPermitRoleResource {
    private final Logger logger = LoggerFactory.getLogger(SysPermitRoleResource.class);
    @Resource
    private SysPermitRoleService service;

    @PostMapping(value = "")
    public @ResponseBody String insert(@RequestBody SysPermitRole role) {
        int id = service.insert(role);
        int max = service.selectMax();
        if (id > 0
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "permit_role", max, JSON.toJSONString(role))
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "extend_permit_role", max, JSON.toJSONString(service.selectAllByPermitRoleId(max)))) {
            return "success";
        } else {
            return "failure";
        }
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody String delete(@PathVariable("id") Integer id) {
        if (service.delete(id) > 0
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "permit_role", id, null)
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "extend_permit_role", id, null)) {
            return "success";
        } else {
            return "failure";
        }
    }

    /**
     * 更改角色的权限
     * @param id 角色的Id
     * @param newPermitOfRoles 角色新权限
     * @return 更改是否成功的标志
     */
    @PutMapping(value = "/{id}")
    public @ResponseBody String update(@PathVariable("id") Integer id, @RequestBody List<Integer> newPermitOfRoles) {
        // 首先根据角色的Id查找当前所具有的权限列表序号
        List<SysPermitRole> currentRolePermits = service.selectPermitIdOneRole(id);
        List<Integer> oldPermitIds = new ArrayList<>();
        // 对newPermitOfRole进行验证
        // 删除取消的权限
        for (SysPermitRole currentRolePermit : currentRolePermits) {
            oldPermitIds.add(currentRolePermit.getPermitId());

            if (! newPermitOfRoles.contains(currentRolePermit.getPermitId())) {
                if (service.delete(currentRolePermit.getPermitRoleId()) > 0
                        && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "permit_role", currentRolePermit.getPermitRoleId(), null)
                        && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "extend_permit_role", currentRolePermit.getPermitRoleId(), null)) {
                    logger.info("permit_role and extend_permit_role has update!, the id is " + currentRolePermit.getPermitRoleId());
                }
            }
        }
        // 增加新的权限
        for (Integer newPermitOfRole : newPermitOfRoles) {
            if (! oldPermitIds.contains(newPermitOfRole)) {
                SysPermitRole record = new SysPermitRole();
                record.setRoleId(id);
                record.setPermitId(newPermitOfRole);
                int currentId = service.insert(record);
                int max = service.selectMax();
                if (currentId > 0
                        && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "permit_role", max, JSON.toJSONString(record))
                        && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "extend_permit_role", max, JSON.toJSONString(service.selectAllByPermitRoleId(max)))) {
                    logger.info("permit_role and extend_permit_role has update!, the id is " + max);
                }
            }
        }
        return "success";
    }

    @GetMapping(value = "")
    public @ResponseBody
    List<SysPermitRole> selectAll() {
        return service.selectAll();
    }

    @GetMapping(value = "/extend")
    public @ResponseBody
    List<RolePermitResponse> selectAllByRoleId() {
        return service.selectAllByRoleId();
    }

    @GetMapping(value = "/extend/{id}")
    public @ResponseBody
    List<RolePermitResponse> selectByRoleId(@PathVariable("id") Integer id) {
        return service.selectByRoleId(id);
    }
}
