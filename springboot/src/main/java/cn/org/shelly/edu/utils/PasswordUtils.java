package cn.org.shelly.edu.utils;

import cn.hutool.crypto.digest.MD5;

public class PasswordUtils {
    /**
     * 加密盐
     */
    static final byte[] SLAT = {'s','w','p','u'};

    private PasswordUtils() {
    }

    /**
     * 加密
     */
    public static String encrypt(String password) {
        MD5 md5 = new MD5(SLAT);
        return md5.digestHex16(md5.digestHex(password));
    }

    /**
     * 密码配对
     */
    public static boolean match(String password, String encryptedPassword) {
        return encryptedPassword.equals(encrypt(password));
    }
}
