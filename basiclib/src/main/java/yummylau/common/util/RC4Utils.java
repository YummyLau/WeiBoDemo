package yummylau.common.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author g8165
 *
 */

public class RC4Utils {
	
	private final static String TAG = "RC4Utils";
	
	/**
	 * 加密算法
	 */
	public static final String ALGORITHM = "RC4";

    /**
     * RC4加密
     * @param content  明文
     * @param key      密钥
     * @return         密文
     */
    public static String encryptData(String content, String key){
        if (content == null || key == null) return null;
        byte[] encryptedData = encryptData(content.getBytes(), key);
        return Base64Utils.encode(encryptedData);
    }

    /**
     * RC4解密
     * @param content  密文
     * @param key      密钥
     * @return         明文
     */
    public static String decryptData(String content, String key){
        if (content == null || key == null) return null;
        byte[] decodedData = Base64Utils.decode(content);
        byte[] decryptedData = decryptData(decodedData, key);
        try {
            return new String(decryptedData, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RC4加密
     * @param data    明文
     * @param rc4Key  密钥
     * @return        密文
     */
    @SuppressLint("TrulyRandom")
    public static byte[] encryptData(byte[] data, String rc4Key){

        byte[] encryptedData = null;

        if (data != null) {
            try {
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                //根据给定的字节数组构造一个密钥, ALGORITHM:与给定的密钥内容相关联的密钥算法的名称
                SecretKeySpec keySpec = new SecretKeySpec( rc4Key.getBytes("UTF-8"), ALGORITHM );

                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                encryptedData = cipher.doFinal(data);

            } catch (NoSuchAlgorithmException e) {
                Log.e(TAG, e.toString());
            } catch (NoSuchPaddingException e) {
                Log.e(TAG, e.toString());
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, e.toString());
            } catch (InvalidKeyException e) {
                Log.e(TAG, e.toString());
            } catch (IllegalBlockSizeException e) {
                Log.e(TAG, e.toString());
            } catch (BadPaddingException e) {
                Log.e(TAG, e.toString());
            }
        }

        return encryptedData;
    }
	
	/**
	 * 对数据进行RC4解密
	 * @param encryptedData  密文
	 * @param rc4Key         密钥
	 * @return               明文
	 */
	public static byte[] decryptData(byte[] encryptedData, String rc4Key){
		
		byte[] decryptedData = null;
		
		if (encryptedData != null) {
			try {
				Cipher cipher = Cipher.getInstance(ALGORITHM);
				SecretKeySpec keySpec = new SecretKeySpec( rc4Key.getBytes("UTF-8"), ALGORITHM );
				
				cipher.init(Cipher.DECRYPT_MODE, keySpec);
				decryptedData = cipher.doFinal(encryptedData);
				
			} catch (NoSuchAlgorithmException e) {
				Log.e(TAG, e.toString());
			} catch (NoSuchPaddingException e) {
				Log.e(TAG, e.toString());
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, e.toString());
			} catch (InvalidKeyException e) {
				Log.e(TAG, e.toString());
			} catch (IllegalBlockSizeException e) {
				Log.e(TAG, e.toString());
			} catch (BadPaddingException e) {
				Log.e(TAG, e.toString());
			}
		}
		
		return decryptedData;
	}
}
