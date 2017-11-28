package yummylau.common.util.util.common;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 字符串处理工具
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class StringUtils {

    private final static String TAG = StringUtils.class.getSimpleName();
    private static final String UTF8 = "UTF-8";
    private static final String GBK = "gbk";
    private static final String EMPTY = "";

    private static Calendar sCalendar = Calendar.getInstance(Locale.getDefault());


    /**
     * 字符串是否相等
     *
     * @param actual
     * @param expected
     * @return
     */
    public static boolean isEquals(String actual, String expected) {
        return actual != null && actual.equals(expected);
    }

    /**
     * 预防空指针
     *
     * @param str
     * @return
     */
    public static String proStrToEmpty(String str) {
        return (str == null ? "" : str);
    }

    /**
     * 首字母变大写
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                : new StringBuilder(str.length())
                .append(Character.toUpperCase(c))
                .append(str.substring(1)).toString();
    }

    /**
     * 转UTF8编码
     *
     * @param str
     * @return
     */
    public static String utf8Encode(String str) {
        if (!TextUtils.isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, UTF8);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * 转UTF8编码，带默认返回值
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!TextUtils.isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, UTF8);
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }


    /**
     * html转字符串
     *
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return TextUtils.isEmpty(source) ? source :
                source.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * 全角转半角
     *
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (TextUtils.isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 半角转全角
     *
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (TextUtils.isEmpty(s)) {
            return s;
        }
        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }


    /**
     * 拼接字符串集，默认逗号
     *
     * @param array
     * @return
     */
    public static String join(List<String> array) {
        if (array == null || array.size() == 0) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.size(); i++) {
                if (i != 0) {
                    sb.append(',');
                    sb.append(array.get(i));
                } else {
                    sb.append(array.get(i));
                }
            }
            return sb.toString();
        }
    }

    /**
     * 用ch拼接字符串集
     *
     * @param array
     * @param ch
     * @return
     */
    public static String join(List<String> array, char ch) {
        if (array == null || array.size() == 0) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.size(); i++) {
                if (i != 0) {
                    sb.append(ch);
                    sb.append(array.get(i));
                } else {
                    sb.append(array.get(i));
                }
            }
            return sb.toString();
        }
    }

    /**
     * 按字节长度截串
     *
     * @param str
     * @param len
     * @return
     */
    public static String getSub(String str, int len) {
        return getSub(str, len, EMPTY);
    }

    /**
     * 截取子串并拼接
     *
     * @param str
     * @param len
     * @param symbol
     * @return
     */
    public static String getSub(String str, int len, String symbol) {
        if (str == null)
            return "";
        try {
            int counterOfDoubleByte = 0;
            byte[] b = str.getBytes(GBK);
            if (b.length <= len)
                return str;
            for (int i = 0; i < len; i++) {
                if (b[i] < 0)
                    counterOfDoubleByte++; // 通过判断字符的类型来进行截取
            }
            if (counterOfDoubleByte % 2 == 0)
                str = new String(b, 0, len, GBK) + symbol;
            else
                str = new String(b, 0, len - 1, GBK) + symbol;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }


    /**
     * 获取字符串的字节长度
     *
     * @param str
     * @return
     */
    public static int getLen(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return str.getBytes(GBK).length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 替换html特殊字符
     *
     * @param str
     * @return
     */
    public static String filterHTMLTag(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        str = HtmlUtils.decode(str);
        return str;
    }

    /**
     * 对double进行格式化
     *
     * @param value
     * @param format
     * @return
     */
    public static String format(double value, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }

    /**
     * 格式化double，保留小数点3位
     *
     * @param value
     * @return
     */
    public static String format(double value) {
        String format = "#,###";
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }


    /**
     * 字节转十六进制字符串
     *
     * @param bcd
     * @return
     */
    public static String bytesToHexStr(byte[] bcd) {
        char[] bcdLookup = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        String s = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bcd.length; i++) {
            sb.append(bcdLookup[((bcd[i]) >> 4) & 0x0f]);
            sb.append(bcdLookup[bcd[i] & 0x0f]);
        }
        return sb.toString();
    }


    public static String escapeString(String input) {
        return input.replaceAll("\"", "&quot;")
                .replaceAll("\'", " &apos;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

    public static String getFormattedNumber(double value) {
        return getFormattedNumber(value, false);
    }

    public static String getFormattedNumber(double value, boolean groupingUsed) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(groupingUsed);
        return nf.format(value);
    }

    public static String getFormattedCCurrency(double value) {
        String formatted = getFormattedNumber(value, false);
        if (formatted.contains(".")) {
            formatted = formatted.substring(0, formatted.indexOf('.'));
        }
        return formatted;
    }

    public static String handleNick(String nick, int num) {
        int baseCount = num;
        if (getLen(nick) > baseCount) {
            nick = nick.substring(0, num);
            while (getLen(nick) > baseCount) {
                num--;
                nick = nick.substring(0, num);
            }
        }
        return nick + "...";
    }

    /**
     * 在数字型字符串千分位加逗号
     *
     * @param str
     * @return
     */
    public static String addComma(String str) {
        boolean neg = false;
        if (str.startsWith("-")) { // 处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1) { // 处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        return sb.toString();
    }

    /**
     * 在数字型字符串千分位删除逗号
     *
     * @param str
     * @return
     */
    public static String deleteComma(String str) {
        if (str != null) {
            str = str.replace(",", "");
        }
        return str;
    }

    public static String trim(String str) {
        if (str != null) {
            return str.trim();
        }
        return str;
    }

    /**
     * 获取bytes大小的格式化
     *
     * @param remainSize
     * @return
     */
    public static String getFormattedStorageSize(long remainSize) {
        double remain = 0;
        String[] unit = new String[]{"B", "K", "M", "G", "T"};
        if (remainSize > -1) {
            for (int i = 0; i < unit.length; i++) {
                if (remainSize / 1024 > 0) {
                    remain = remainSize / 1024d;
                    remainSize = remainSize / 1024;
                } else {
                    return String.format(Locale.getDefault(), "%.2f", remain) + unit[i];
                }
            }
        }
        return "未知";
    }

    /***
     * 检查是否含有非Ascii字符
     *
     * @param value
     */
    public static void checkNonAscii(String value) {
        if (value == null) throw new NullPointerException("value == null");
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                throw new IllegalArgumentException(String.format(Locale.US,
                        "Unexpected char %#04x at %d value: %s", (int) c, i, value));
            }
        }
    }

    /**
     * 把所有的 white space 换成空格
     *
     * @param s
     * @return
     */
    public static String compactWhite(String s) {
        int maxPos = s.length();
        StringBuffer sb = new StringBuffer(maxPos);
        int curPos = 0;
        while (curPos < maxPos) {
            char c = s.charAt(curPos++);
            if(HtmlUtils.isWhitespace(c)) {
                while ((curPos < maxPos) && HtmlUtils.isWhitespace(s.charAt(curPos))) {
                    curPos++;
                }
                c = '\u0020';
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
