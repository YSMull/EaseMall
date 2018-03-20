package com.ysmull.easemall.aspect;

import com.ysmull.easemall.annotation.Privilege;
import com.ysmull.easemall.model.entity.User;
import com.ysmull.easemall.util.PrivilegeUtil;
import com.ysmull.easemall.util.UserContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * @author maoyusu
 */
@Aspect
@Component
public class PrivilegeAspcet {

    @Around("@annotation(privilege)")
    public Object privilege(ProceedingJoinPoint jp, Privilege privilege) throws Throwable {
        User user = UserContext.getCurrentUser();
        User.ROLE[] roles = privilege.role();
        boolean login = privilege.login();
        if (roles.length > 0) {
            // 指定了角色(隐含必须登陆)
            if (user != null && Arrays.asList(privilege.role()).contains(user.getRole())) {
                return jp.proceed();
            } else {
                return PrivilegeUtil.noAuth(roles);
            }
        } else if (login) {
            // 只需要登陆，未指定角色
            if (user != null) {
                return jp.proceed();
            } else {
                return PrivilegeUtil.noAuth(roles);
            }
        } else {
            // 注解没有指定任何属性，不需要登陆
            return jp.proceed();
        }
    }
}
