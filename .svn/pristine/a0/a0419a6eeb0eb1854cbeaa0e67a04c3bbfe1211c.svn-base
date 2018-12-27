package com.dwms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * MD5签名工具类
 * @author windy
 * @date 2018-11-08
 */
public class MD5Utils {

    /**
     * MD5签名
     * @param str  字符串
     * @param salt 盐
     */
    public static String md5(String str, String salt) {
        String result;
        try {
            String target = salt == null ? str : str + salt;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(target.getBytes(Charset.forName("utf-8")));
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            result = sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5签名错误，错误信息：", e);
        }
        return result;
    }

    /**
     * MD5文件签名
     * @param filePath 文件路径
     */
    public static String md5File(String filePath) {
        String result;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            //读取文件
            int length = 0;
            byte[] buffer = new byte[1024];
            InputStream is = new FileInputStream(new File(filePath));
            while ((length = is.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            is.close();

            //签名，转换
            byte[] array = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            result = sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5文件签名错误，错误信息：", e);
        }
        return result;
    }
}
