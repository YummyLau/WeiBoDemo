package yummylau.common.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class MD5Utils {
	
	private final static String TAG = "MD5Utils";
	
	/**
	 * 生产32位MD5码
	 * @param str  字符串
	 * @return     MD5
	 */
	public static String generateMD5(String str){
		
		MessageDigest md5;
		
		try {
			md5 = MessageDigest.getInstance( "MD5" );
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
