package com.zhang.mobiledemo.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Android_develop on 2017/3/10.
 */

public class MD5Utils {
    /**
     * MD5加密
     * @param password
     * @return
     */
    public static String encode(String password){
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest=instance.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b:digest){
                int i=b&0xff;//获取字节的低八位有效值
                String hexString = Integer.toHexString(i);//转为16进制
                if (hexString.length()<2){//如果是一位补零
                    hexString="0"+hexString;
                }
                sb.append(hexString);
            }
            return  sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }
}
