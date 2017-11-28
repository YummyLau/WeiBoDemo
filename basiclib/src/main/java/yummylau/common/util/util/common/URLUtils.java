package yummylau.common.util.util.common;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class URLUtils {

    /**
     * 对URL进行编码
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
     * 判断url是否合法
     */
    public static boolean verifyURL(String urlString) {
        if (urlString == null)
            return false;
        try {
            new URL(urlString);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
}
