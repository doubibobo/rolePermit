package com.example.authorization.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.model.SysPermit;
import com.example.model.SysPermitRole;
import com.example.model.SysRoleUser;
import com.example.redisUtils.MainRedisUtil;
import com.example.redisUtils.ReloadRedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class PermitInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(PermitInterceptor.class);

    /**
     * 正则表达式匹配
     * @param pattern 模式串
     * @param content 带匹配串
     * @return 匹配则返回true，不匹配则返回false
     */
    private boolean regex(String pattern, String content) {
        return Pattern.matches(pattern, content);
    }

    /**
     * 根据用户Id获得用户所拥有角色的Id
     * @param id 用户的Id
     * @return 角色Id
     */
    private Set<Integer> getRoleIdByUserId(Integer id) {
        Set<Integer> roleIds = new HashSet<>();
        String number = MainRedisUtil.getValue(ReloadRedisUtil.PREFIX + ReloadRedisUtil.SYS_ROLE_USER_RELOADING);
        assert number != null;
        for (int i = 1; i <= Integer.valueOf(number); ) {
            String value = MainRedisUtil.getValue(ReloadRedisUtil.SYS_ROLE_USER_RELOADING + ReloadRedisUtil.SUFFIX + i);
            if (value != null) {
                SysRoleUser roleUser = JSON.parseObject(value, SysRoleUser.class);
                if (roleUser.getUserId().equals(id)) {
                    roleIds.add(roleUser.getRoleId());
                }
                i++;
            }
        }
        return roleIds;
    }

    /**
     * 根据角色的Id获取权限的Id
     * @param roleIds 角色的Id列表
     * @return 权限的Id列表
     */
    private Set<Integer> getPermitIdByRoleIds(Set<Integer> roleIds) {
        Set<Integer> permitIds = new HashSet<>();
        String number = MainRedisUtil.getValue(ReloadRedisUtil.PREFIX + ReloadRedisUtil.SYS_PERMIT_ROLE_RELOADING);
        assert number != null;

        for (Integer roleId : roleIds) {
            for (int i = 1; i <= Integer.valueOf(number); ) {
                String value = MainRedisUtil.getValue(ReloadRedisUtil.SYS_PERMIT_ROLE_RELOADING + ReloadRedisUtil.SUFFIX + i);
                if (value != null) {
                    SysPermitRole permitRole = JSON.parseObject(value, SysPermitRole.class);
                    if (permitRole.getRoleId().equals(roleId)) {
                        roleIds.add(permitRole.getPermitId());
                    }
                    i++;
                }
            }
        }
        return permitIds;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入到权限拦截器中", request.getPathInfo(), response.getStatus());
        // 排除不需要拦截的情况
        if (request.getMethod().equals("OPTIONS")) {
            logger.info("OPTIONS请求，不需要进行拦截");
            return true;
        }
        if (request.getRequestURI().equals("/login")) {
            logger.info("无权限页面，不需要拦截器进行拦截");
            return true;
        }
        if (! (handler instanceof HandlerMethod)) {
            logger.info("特殊情况，不需要拦截器进行拦截");
            return true;
        }

        // 记录所访问的路径
        logger.info("URI: " + request.getRequestURI());
        logger.info("URL: " + request.getRequestURL());
        logger.info("Method : " + request.getMethod());

        Integer userId = 1;
        // 获取用户所具有的权限
        // 首先根据用户的Id获取角色Id
        Set<Integer> roleIds = getRoleIdByUserId(userId);
        // 根据角色的Id获取权限Id
        Set<Integer> permitIds = getPermitIdByRoleIds(roleIds);

        for (Integer permitId : permitIds) {
            String value = MainRedisUtil.getValue(ReloadRedisUtil.SYS_PERMIT_RELOADING + ReloadRedisUtil.SUFFIX + permitId);
            SysPermit permit = JSON.parseObject(value, SysPermit.class);
            assert permit != null;
            if (regex(permit.getPermitUrl(), request.getRequestURI())) {
                return true;
            }
        }

//        // 从请求头中获取用户的ID
//        String authorization = request.getHeader(HttpConstant.AUTHORIZATION);
//        if (authorization == null) {
//            logger.info("该用户没有登录或者登录信息错误");
//            response.setStatus(403);
//            return false;
//        }

        return false;
    }
}
