package com.ysmull.easemall.util;

import com.alibaba.fastjson.JSON;
import com.ysmull.easemall.model.entity.User;
import com.ysmull.easemall.model.vo.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maoyusu
 */
public class PrivilegeUtil {

    private static final Logger log = LoggerFactory.getLogger(PrivilegeUtil.class);

    /**
     * 五分钟登陆过期
     */
    private static final long EXTERIOR = 5 * 60 * 1000;
    private static final String SECRET = "MYS&^e%!";

    private PrivilegeUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateCookie(User user) {
        Long uid = user.getId();
        String password = user.getPassword();
        Long t = System.currentTimeMillis() + EXTERIOR;

        String joinedStr = Stream.of(uid.toString(), password, t.toString(), SECRET)
                .collect(Collectors.joining(":"));
        log.debug(joinedStr);

        String md5 = "";
        try {
            md5 = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5")
                    .digest(joinedStr.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.error("md5 generate failed");
            log.error(e.toString());
        }
        String md5Cookie = Stream.of(uid.toString(), t.toString(), md5)
                .collect(Collectors.joining(":"));
        log.debug("md5Cookie:{}", md5Cookie);
        return md5Cookie;
    }

    public static String generateHash(User user, Long expiredTime) {
        Long uid = user.getId();
        String password = user.getPassword();

        String joinedStr = Stream.of(uid.toString(), password, expiredTime.toString(), SECRET)
                .collect(Collectors.joining(":"));
        String md5 = "";
        try {
            md5 = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5")
                    .digest(joinedStr.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.error("md5 generate failed");
            log.error(e.toString());
        }
        return md5;
    }

    public static Object noAuth(User.ROLE[] roles) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpResponse = ((ServletRequestAttributes) requestAttributes).getResponse();
        WebResponse<String> webResponse = new WebResponse<>();
        webResponse.setCode(WebResponse.NO_AUTH);

        if (roles.length == 0) {
            webResponse.setMsg("need login");
        } else {
            String msg = Stream.of(roles).map(Enum::toString).collect(Collectors.joining(","));
            webResponse.setMsg(String.format("need %s", msg));
        }
        httpResponse.getWriter().write(JSON.toJSONString(webResponse));
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        return null;
    }

}
