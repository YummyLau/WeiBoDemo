package yummylau.common.util.util.common;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class RSAUtils {

    private final static String TAG = RSAUtils.class.getSimpleName();

    public static final String KEY_ALGORITHM = "RSA";       //加密算法
    private static final int MAX_ENCRYPT_BLOCK = 117;       //RSA最大加密明文大小,若超过,则需分段加密
    private static final int MAX_DECRYPT_BLOCK = 128;       //RSA最大解密密文大小

    /**
     * RSA加密
     *
     * @param content 明文
     * @param key     公钥
     * @return 密文
     */
    public static String encryptByPublicKey(@NonNull String content, @NonNull String key) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(key)) return null;
        byte[] encryptedData = encryptByPublicKey(content.getBytes(), key);
        return Base64Utils.encode(encryptedData);
    }

    /**
     * 私钥加密
     *
     * @param content 明文
     * @param key     公钥
     * @return 密文
     */
    public static String encryptByPrivateKey(@NonNull String content, @NonNull String key) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(key)) return null;
        byte[] encryptedData = encryptByPrivateKey(content.getBytes(), key);
        return Base64Utils.encode(encryptedData);
    }


    /**
     * RSA加密
     *
     * @param data      明文
     * @param keyString 公钥
     * @return 密文
     */
    @SuppressLint("TrulyRandom")
    public static byte[] encryptByPublicKey(byte[] data, String keyString) {
        byte[] encryptedData = null;
        byte[] keyBytes = Base64Utils.decode(keyString);
        try {
            // get the public key
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

//			CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
//			Certificate certificate = certificateFactory.generateCertificate( new ByteArrayInputStream(keyBytes) );
//			PublicKey publicKey = certificate.getPublicKey();

            // init
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");    //默认为no padding, 服务器为PKCS1Padding,要一致
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
            out.close();

        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.toString());
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, e.toString());
        } catch (InvalidKeyException e) {
            Log.e(TAG, e.toString());
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, e.toString());
        } catch (BadPaddingException e) {
            Log.e(TAG, e.toString());
        } catch (InvalidKeySpecException e) {
            Log.e(TAG, e.toString());
        } catch (NoSuchProviderException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return encryptedData;
    }

    /**
     * 私钥加密
     *
     * @param data      明文
     * @param keyString 私钥
     * @return 密文
     */
    public static byte[] encryptByPrivateKey(byte[] data, String keyString) {
        byte[] encryptedData = null;
        byte[] keyBytes = Base64.decode(keyString.getBytes(), Base64.DEFAULT);
        try {
            // get the public key
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // init
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");    //默认为no padding, 服务器为PKCS1Padding,要一致
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
            out.close();

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        } catch (NoSuchPaddingException e) {
            System.out.println(e.toString());
        } catch (InvalidKeyException e) {
            System.out.println(e.toString());
        } catch (IllegalBlockSizeException e) {
            System.out.println(e.toString());
        } catch (BadPaddingException e) {
            System.out.println(e.toString());
        } catch (InvalidKeySpecException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return encryptedData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, @NonNull String publicKey) {
        try{
            byte[] keyBytes = Base64Utils.decode(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  //默认为no padding, 服务器为PKCS1Padding,要一致
            cipher.init(Cipher.DECRYPT_MODE, publicK);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }
        return null;
    }
}
