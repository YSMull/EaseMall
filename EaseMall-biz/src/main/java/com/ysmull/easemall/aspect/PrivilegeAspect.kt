package com.ysmull.easemall.aspect

import com.ysmull.easemall.annotation.Privilege
import com.ysmull.easemall.util.PrivilegeUtil
import com.ysmull.easemall.util.UserContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component


/**
 * @author maoyusu
 */
@Aspect
@Component
class PrivilegeAspect {

    @Around("@annotation(privilege)")
    @Throws(Throwable::class)
    fun privilege(jp: ProceedingJoinPoint, privilege: Privilege): Any? {
        val user = UserContext.currentUser
        val roles = privilege.role
        val login = privilege.login
        return if (roles.isNotEmpty()) {
            // 指定了角色(隐含必须登陆)
            if (privilege.role.contains(user.role)) {
                jp.proceed()
            } else {
                PrivilegeUtil.noAuth(roles)
            }
        } else if (login) {
            // 只需要登陆，未指定角色
            jp.proceed()
        } else {
            // 注解没有指定任何属性，不需要登陆
            jp.proceed()
        }
    }
}
