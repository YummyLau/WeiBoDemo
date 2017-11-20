package yummylau.common.util;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by g8931 on 2017/11/20.
 */

public class Base64Utils {

    private final static String TAG = Base64Utils.class.getSimpleName();
    private static final int CACHE_SIZE = 1024;

    public static byte[] decode(String base64) {
        byte[] decodedData = null;
        try {
            decodedData = Base64.decode(base64.getBytes(), Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return decodedData;
    }

    public static String encode(byte[] bytes) {
        String encodedData = null;
        if (bytes != null) {
            try {
                encodedData = new String(Base64.encode(bytes, Base64.DEFAULT));
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        return encodedData;
    }

    public static String encodeFile(String filePath) {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    public static byte[] fileToByte(String filePath) {
        byte[] data = new byte[0];
        File file = new File(filePath);

        if (file.exists()) {
            try {
                FileInputStream fin = new FileInputStream(file);
                ByteArrayOutputStream bout = new ByteArrayOutputStream(2018);
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
                e.printStackTrace();
            }
        }
        return data;
    }

}
