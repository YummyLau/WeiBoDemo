package yummylau.common.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class UrlUtils {
    /**
     * 对URL进行编码(UTF-8)
     *
     * @param par    url字符串
     * @param encode 编码方式
     * @return 编码后的字符串
     */
    public static String getURLCode(String par, String encode) {
        String revalue = null;
        if (par != null) {
            try {
                revalue = URLEncoder.encode(par, encode);
            } catch (UnsupportedEncodingException e) {
                revalue = null;
            }
        }
        return revalue;
    }

    /**
     * 对URL进行编码(UTF-8)
     *
     * @param url url字符串
     * @return 编码后的字符串
     */
    public static String getURLCode(String url) {
        return getURLCode(url, "UTF-8");
    }

    /**
     * 对字符串进行URL解码
     *
     * @param text   文本
     * @param encode 编码
     * @return 编码后的字符串
     */
    public static String getURLDecode(String text, String encode) {
        String revalue = null;
        if (null != text) {
            try {
                revalue = URLDecoder.decode(text, encode);
            } catch (UnsupportedEncodingException e) {
                revalue = null;
            }
        }
        return revalue;
    }

    /**
     * 对字符串进行URL解码(UTF-8)
     *
     * @param text 文本
     * @return 编码后的字符串
     */
    public static String getURLDecode(String text) {
        return getURLDecode(text, "UTF-8");
    }

    /**
     * 判断url是否合法
     */
    public static boolean verifyURL(String urlString) {
        if (TextUtils.isEmpty(urlString))
            return false;
        try {
            new URL(urlString);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
}
