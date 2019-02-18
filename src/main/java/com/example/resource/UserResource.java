package com.example.resource;

import com.alibaba.fastjson.JSON;
import com.example.redisUtils.ReloadRedisUtil;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserResource {
    @Resource
    private UserService service;

    @PostMapping(value = "")
    public @ResponseBody String insert(@RequestBody User user) {
        if (service.insert(user) > 0
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.INSERT, "user", service.selectMax(), JSON.toJSONString(user))) {
            return "success";
        } else {
            return "failure";
        }
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody String delete(@PathVariable("id") Integer id) {
        if (service.delete(id) > 0
                && ReloadRedisUtil.reloadingMainTables(ReloadRedisUtil.DELETE, "user", id, null)) {
            return "success";
        } else {
            return "failure";
        }
    }

    @GetMapping(value = "")
    public @ResponseBody List<User> select() {
        return service.select();
    }
}
