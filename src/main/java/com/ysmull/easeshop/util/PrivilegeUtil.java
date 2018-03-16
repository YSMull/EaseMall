package com.ysmull.easeshop.util;

import com.alibaba.fastjson.JSON;
import com.ysmull.easeshop.model.entity.User;
import com.ysmull.easeshop.model.vo.WebResponse;
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

    private static final long EXTERIOR = 180000000;
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
        log.debug("md5Cookie:{0}", md5Cookie);
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
        if (roles.length > 1) {
            webResponse.setCode(WebResponse.NEED_LOGIN);
        } else if (roles[0].equals(User.ROLE.BUYER)) {
            webResponse.setCode(WebResponse.NEED_BUYER);
        } else if (roles[0].equals(User.ROLE.SELLER)) {
            webResponse.setCode(WebResponse.NEED_SELLER);
        }
        webResponse.setMsg("no auth!");
        httpResponse.getWriter().write(JSON.toJSONString(webResponse));
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        return null;
    }

}
