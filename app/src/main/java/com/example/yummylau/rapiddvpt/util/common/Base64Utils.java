package com.example.yummylau.rapiddvpt.util.common;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Base64转化
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 */

public class Base64Utils {

    private final static String TAG = Base64Utils.class.getSimpleName();

    private static final int CACHE_SIZE = 1024;
    private static final int BYTE_ARRAY_SIZE = 2048;

    /**
     * Base64字符串解码为二进制数组
     *
     * @param base64 base64编码后的字符串
     * @return 解码后的字符串
     */
    public static byte[] decode(String base64) {
        byte[] decodedData = null;
        try {
            decodedData = Base64.decode(base64.getBytes(),Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return decodedData;
    }

    /**
     * 二进制数组编码为Base64字符串
     *
     * @param bytes 字符串字节数组
     * @return 编码后的字符串
     */
    public static String encode(byte[] bytes) {
        String encodedData = null;
        if (bytes != null) {
            try {
                encodedData = new String(Base64.encode(bytes,Base64.DEFAULT));
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        return encodedData;
    }

    /**
     * 将文件编码为Base64字符串
     *
     * @param filePath 文件路径
     * @return 编码后的文本
     */
    public static String encodeFile(String filePath) {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    /**
     * 文件转换成二进制数组
     *
     * @param filePath 文件路径
     * @return 二进制数组
     */
    public static byte[] fileToByte(String filePath) {
        byte[] data = new byte[0];
        File file = new File(filePath);

        if (file.exists()) {
            try {
                FileInputStream fin = new FileInputStream(file);
                ByteArrayOutputStream bout = new ByteArrayOutputStream(BYTE_ARRAY_SIZE);
                byte[] cache = new byte[CACHE_SIZE];
                int read;
                while ((read = fin.read(cache)) != -1) {
                    bout.write(cache, 0, read);
                    bout.flush();
                }

                bout.close();
                fin.close();
                data = bout.toByteArray();

            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }

        return data;
    }

}
