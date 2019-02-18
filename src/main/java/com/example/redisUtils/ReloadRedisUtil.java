package com.example.redisUtils;

import java.util.Objects;

public final class ReloadRedisUtil {
    public static String SYS_TABLE_RELOADING = "sys_table_preloading";
    public static String SYS_PERMIT_RELOADING = "sys_permit_preloading";
    public static String SYS_ROLE_RELOADING = "sys_role_preloading";
    public static String SYS_USER_RELOADING = "sys_user_preloading";
    public static String SYS_PERMIT_ROLE_RELOADING = "sys_permit_role_preloading";
    public static String SYS_EXTEND_PERMIT_ROLE_RELOADING = "sys_extend_permit_role_preloading";
    public static String SYS_ROLE_USER_RELOADING = "sys_role_user_preloading";
    public static String SYS_EXTEND_ROLE_USER_RELOADING = "sys_extend_role_user_preloading";

    public static String PREFIX = "main_";
    public static String SUFFIX = "_";

    public static String INSERT = "INSERT";
    public static String DELETE = "DELETE";
    public static String UPDATE = "UPDATE";

    private static String selectRedisKeyOfTableWithSuffix(String table) {
        switch (table) {
            case "table" : return SYS_TABLE_RELOADING + SUFFIX;
            case "permit" : return SYS_PERMIT_RELOADING + SUFFIX;
            case "role" : return SYS_ROLE_RELOADING + SUFFIX;
            case "user" : return SYS_USER_RELOADING + SUFFIX;
            case "permit_role" : return SYS_PERMIT_ROLE_RELOADING + SUFFIX;
            case "extend_permit_role" : return SYS_EXTEND_PERMIT_ROLE_RELOADING + SUFFIX;
            case "role_user" : return SYS_ROLE_USER_RELOADING + SUFFIX;
            case "extend_role_user" : return SYS_EXTEND_ROLE_USER_RELOADING + SUFFIX;
        }
        return null;
    }

    private static String selectRedisKeyOfTableWithPrefix(String table) {
        switch (table) {
            case "permit_role" : return PREFIX + SYS_PERMIT_ROLE_RELOADING;
            case "extend_permit_role" : return PREFIX + SYS_EXTEND_PERMIT_ROLE_RELOADING;
            case "role_user" : return PREFIX + SYS_ROLE_USER_RELOADING;
            case "extend_role_user" : return PREFIX + SYS_EXTEND_ROLE_USER_RELOADING;
            case "table" : return PREFIX + SYS_TABLE_RELOADING;
            case "permit" : return PREFIX + SYS_PERMIT_RELOADING;
            case "role" : return PREFIX + SYS_ROLE_RELOADING;
            case "user" : return PREFIX + SYS_USER_RELOADING;
        }
        return null;
    }

    public synchronized static Boolean reloadingMainTables(String method, String table, Integer id, String value) {
        if (method.equals(INSERT)) {

            MainRedisUtil.setValue(selectRedisKeyOfTableWithSuffix(table) + id, value);

            Integer preValue = Integer.valueOf(Objects.requireNonNull(MainRedisUtil.getValue(selectRedisKeyOfTableWithPrefix(table))));
            MainRedisUtil.setValue(selectRedisKeyOfTableWithPrefix(table), String.valueOf(preValue + 1));

            return MainRedisUtil.getValue(selectRedisKeyOfTableWithSuffix(table) + id) != null;
        } else if (method.equals(DELETE)) {

            Integer preValue = Integer.valueOf(Objects.requireNonNull(MainRedisUtil.getValue(selectRedisKeyOfTableWithPrefix(table))));
            MainRedisUtil.setValue(selectRedisKeyOfTableWithPrefix(table), String.valueOf(preValue - 1));

            return MainRedisUtil.doDelete(selectRedisKeyOfTableWithSuffix(table) + id);
        } else {
            if (method.equals(UPDATE)) {
                MainRedisUtil.setValue(selectRedisKeyOfTableWithSuffix(table) + id, value);
                return MainRedisUtil.getValue(selectRedisKeyOfTableWithSuffix(table) + id) != null;
            }
            return false;
        }
    }
}
