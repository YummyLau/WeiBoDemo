package yummylau.common.util;

/**
 * Created by g8165 on 2016/6/16.
 *
 * 各种类型之间的转换公共函数，不用每次都进行判断
 */
public class ConvertUtils {

    /**
     * 将Object转换成int
     * @param value
     * @param defaultValue
     * @return
     */
    public final static int convertToInt(Object value, int defaultValue) {
        if(null == value || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return defaultValue;
    }

    public final static long convertToLong(Object value, long defaultValue) {
        if(null == value || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Long.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).longValue();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return defaultValue;
    }

    public final static float convertToFloat(Object value, float defaultValue) {
        if(null == value || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Float.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Float.valueOf(value.toString());
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return defaultValue;
    }

    public final static double convertToDouble(Object value, float defaultValue) {
        if(null == value || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Double.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString());
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 字符串转整型
     *
     * @param intString    字符串
     * @param defaultValue 默认值
     * @return 整型
     */
    public static int parseInt(String intString, int defaultValue) {

        if (intString == null || "".equals(intString.trim())) return defaultValue;

        try {
            return Integer.parseInt(intString.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }
}
