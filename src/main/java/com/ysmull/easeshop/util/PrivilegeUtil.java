package com.ysmull.easeshop.util;

import com.ysmull.easeshop.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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

    public static Boolean needLogin(List<String> needLoginUrls, String target) {
        for (String api : needLoginUrls) {
            if (target.equals(api) || target.startsWith(api)) {
                return true;
            }
        }
        return false;
    }

}
