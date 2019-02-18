package com.example.role.CommandLineRunner;

import com.alibaba.fastjson.JSON;
import com.example.redisUtils.MainRedisUtil;
import com.example.model.*;
import com.example.response.RolePermitResponse;
import com.example.response.UserRoleResponse;
import com.example.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public final class RedisCommandLineRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(RedisCommandLineRunner.class);

    @Resource
    private UserService userService;

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysPermitService permitService;

    @Resource
    private SysTableService tableService;

    @Resource
    private SysPermitRoleService permitRoleService;

    @Resource
    private SysRoleUserService roleUserService;

    private synchronized Boolean tablePreloading() {
        List<SysTable> lists;
        lists = tableService.selectAll();

        for (SysTable list : lists) {
            String listTransfer = JSON.toJSONString(list);
            MainRedisUtil.setValue("sys_table_preloading_" + list.getTableId(), listTransfer);
        }
        MainRedisUtil.setValue("main_sys_table_preloading", String.valueOf(lists.size()));

        logger.info("waiting for table preloading");

        logger.info("checking for table preloading");

        if (MainRedisUtil.getValue("main_sys_table_preloading") != null) {
            logger.info("table preloading success");
            return true;
        } else {
            logger.info("table preloading failure");
            return false;
        }
    }

    private synchronized Boolean permitPreloading() {
        List<SysPermit> lists;
        lists = permitService.selectAll();

        for (SysPermit list : lists) {
            String listTransfer = JSON.toJSONString(list);
            MainRedisUtil.setValue("sys_permit_preloading_" + list.getPermitId(), listTransfer);
        }
        MainRedisUtil.setValue("main_sys_permit_preloading", String.valueOf(lists.size()));

        logger.info("waiting for permit preloading");

        logger.info("checking for permit preloading");

        if (MainRedisUtil.getValue("main_sys_permit_preloading") != null) {
            logger.info("permit preloading success");
            return true;
        } else {
            logger.info("permit preloading failure");
            return false;
        }
    }

    private synchronized Boolean rolePreloading() {
        List<SysRole> lists;
        lists = roleService.selectAll();

        for (SysRole list : lists) {
            String listTransfer = JSON.toJSONString(list);
            MainRedisUtil.setValue("sys_role_preloading_" + list.getRoleId(), listTransfer);
        }

        MainRedisUtil.setValue("main_sys_role_preloading", String.valueOf(lists.size()));

        logger.info("waiting for role preloading");

        logger.info("checking for role preloading");

        if (MainRedisUtil.getValue("main_sys_role_preloading") != null) {
            logger.info("role preloading success");
            return true;
        } else {
            logger.info("role preloading failure");
            return false;
        }
    }

    private synchronized Boolean userPreloading() {
        List<User> lists;
        lists = userService.select();

        for (User list : lists) {
            String listTransfer = JSON.toJSONString(list);
            MainRedisUtil.setValue("sys_user_preloading_" + list.getUserId(), listTransfer);
        }

        MainRedisUtil.setValue("main_sys_user_preloading", String.valueOf(lists.size()));

        logger.info("waiting for user preloading");

        logger.info("checking for user preloading");

        if (MainRedisUtil.getValue("main_sys_user_preloading") != null) {
            logger.info("user preloading success");
            return true;
        } else {
            logger.info("user preloading failure");
            return false;
        }
    }

    private synchronized Boolean permitRolePreloading() {
        List<SysPermitRole> lists;
        lists = permitRoleService.selectAll();

        for (SysPermitRole list : lists) {
            String listTransfer = JSON.toJSONString(list);
            MainRedisUtil.setValue("sys_permit_role_preloading_" + list.getPermitRoleId(), listTransfer);
        }
        MainRedisUtil.setValue("main_sys_permit_role_preloading", String.valueOf(lists.size()));

        logger.info("waiting for permitRole preloading");

        logger.info("checking for permitRole preloading");

        List<RolePermitResponse> responsesLists;
        responsesLists = permitRoleService.selectAllByRoleId();

        for (RolePermitResponse list : responsesLists) {
            String responsesListTransfer = JSON.toJSONString(list);
            MainRedisUtil.setValue("sys_extend_permit_role_preloading_" + list.getPermitRoleId(), responsesListTransfer);
        }

        MainRedisUtil.setValue("main_sys_extend_permit_role_preloading", String.valueOf(responsesLists.size()));

        logger.info("waiting for extend permitRole preloading");

        logger.info("checking for extend permitRole preloading");

        if (MainRedisUtil.getValue("main_sys_permit_role_preloading") != null && MainRedisUtil.getValue("main_sys_extend_permit_role_preloading") != null) {
            logger.info("permitRole preloading success");
            return true;
        } else {
            logger.info("permitRole preloading failure");
            return false;
        }
    }

    private synchronized Boolean roleUserPreloading() {
        List<SysRoleUser> lists;
        lists = roleUserService.select();

        for (SysRoleUser list : lists) {
            String listTransfer = JSON.toJSONString(list);
            MainRedisUtil.setValue("sys_role_user_preloading_" + list.getRoleUserId(), listTransfer);
        }

        MainRedisUtil.setValue("main_sys_role_user_preloading", String.valueOf(lists.size()));

        logger.info("waiting for roleUser preloading");

        logger.info("checking for roleUser preloading");

        List<UserRoleResponse> responsesLists;
        responsesLists = roleUserService.selectAllByUserId();

        for (UserRoleResponse list : responsesLists) {
            String responseListsTransfer = JSON.toJSONString(list);
            MainRedisUtil.setValue("sys_extend_role_user_preloading_" + list.getRoleUserId(), responseListsTransfer);
        }
        MainRedisUtil.setValue("main_sys_extend_role_user_preloading", String.valueOf(responsesLists.size()));

        logger.info("waiting for extend roleUser preloading");

        logger.info("checking for extend roleUser preloading");

        if (MainRedisUtil.getValue("main_sys_role_user_preloading") != null && MainRedisUtil.getValue("main_sys_extend_role_user_preloading") != null) {
            logger.info("roleUser preloading success");
            return true;
        } else {
            logger.info("roleUser preloading failure");
            return false;
        }
    }

    @Override
    public void run(String... args) throws Exception {
        while (!tablePreloading()) {
            logger.info("table preloading failure, continue to preloading");
        }
        while (!permitPreloading()) {
            logger.info("permit preloading failure, continue to preloading");
        }
        while (!rolePreloading()) {
            logger.info("role preloading failure, continue to preloading");
        }
        while (!userPreloading()) {
            logger.info("user preloading failure, continue to preloading");
        }
        while (!permitRolePreloading()) {
            logger.info("permitRole preloading failure, continue to preloading");
        }
        while (!roleUserPreloading()) {
            logger.info("roleUser preloading failure, continue to preloading");
        }
        logger.info("all things preloading success");
    }
}
