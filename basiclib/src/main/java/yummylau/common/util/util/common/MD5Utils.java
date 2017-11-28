package yummylau.common.util.util.common;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5转化
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 */

public class MD5Utils {

    private final static String TAG = MD5Utils.class.getSimpleName();
    private static final String MD5 = "MD5";

    /**
     * 生产32位MD5码
     * @param str  字符串
     * @return     MD5
     */
    public static String generateMD5(String str){
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.toString());
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++){
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
