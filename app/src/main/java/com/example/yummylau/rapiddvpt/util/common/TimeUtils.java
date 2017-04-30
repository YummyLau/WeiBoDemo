package com.example.yummylau.rapiddvpt.util.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.yummylau.rapiddvpt.R;
import com.example.yummylau.rapiddvpt.util.common.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期转换
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 */

public class TimeUtils {

    private final static String TAG = StringUtils.class.getSimpleName();

    public static final String HTML_SPACE = "&nbsp;";
    public static final String HOUR_MINUTE_FORMAT = "HH:mm";
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    public static final long TIME_DAY_TO_HOUR = 24;         //天转小时
    public static final long TIME_HOUR_TO_MINUTE = 60;      //时间小时到分钟转换
    public static final long TIME_MINUTE_TO_SECOND = 60;    //时间分钟到秒转换
    public static final long TIME_ONE_SECOND = 1000;        //时间1秒钟
    public static final long TIME_ONE_MINUTE = TIME_MINUTE_TO_SECOND * TIME_ONE_SECOND;                         //时间1分钟
    public static final long TIME_ONE_HOUR = TIME_HOUR_TO_MINUTE * TIME_MINUTE_TO_SECOND * TIME_ONE_SECOND;     //时间1小时
    public static final long TIME_ONE_DAY = TIME_DAY_TO_HOUR *
            TIME_HOUR_TO_MINUTE * TIME_MINUTE_TO_SECOND * TIME_ONE_SECOND;                                      //时间1天
    public static final long TIME_30_DAY = 30 * TIME_ONE_DAY;                                                   //时间30天
    private final static SimpleDateFormat sSecondFormatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());                                   //标准格式，精确秒
    private final static SimpleDateFormat sMinuteFormatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());                                      //标准格式，精确分
    private final static SimpleDateFormat sDateFormatter =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());                                            //标准格式，精确天

    /**
     * 提取时间
     *
     * @param regex 正则表达式
     * @param input 文本
     * @return 匹配结果
     */
    public static String matchDate(String regex, String input) {
        if (regex == null) {
            regex = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}";
        }
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 转换时间
     *
     * @param time
     * @return
     */
    public static String convertCommonTime(long time) {

        String timeString = String.valueOf(time);

        Date date;
        try {
            date = new Timestamp(time);
        } catch (Exception e) {
            return timeString;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int calYear = cal.get(Calendar.YEAR);
        int calMonth = cal.get(Calendar.MONTH);

        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH);

        int diffMonth = (nowYear - calYear) * 12 + nowMonth - calMonth;
        if (diffMonth >= 12) {
            return sDateFormatter.format(date);
        } else if (diffMonth > 1) {
            return Integer.toString(diffMonth) + "个月前";
        } else {
            long time1 = cal.getTime().getTime();
            long time2 = now.getTime().getTime();
            long diffDay = (time2 - time1) / (3600000 * 24);
            if (diffDay > 30 && diffDay < 62) {
                return "1个月前";
            } else if (diffDay >= 1) {
                return Integer.toString((int) diffDay) + "天前";
            } else {
                long diffHour = (time2 - time1) / 3600000;
                if (diffHour > 0) {
                    return Integer.toString((int) diffHour) + "小时前";
                } else if (diffHour == 0) {
                    long diffMin = (time2 - time1) / 60000 + 1;
                    if (diffMin > 0 && diffMin <=1) {
                        return "刚刚";
                    } else if (diffMin > 0)
                        return Integer.toString((int) diffMin) + "分钟前";
                }
            }

        }
        return timeString;
    }

    /**
     * 转换时间
     *
     * @param time
     * @return
     */
    public static String convertCommonTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        String originTime = time.replace(HTML_SPACE, "");
        time = matchDate(null, time);

        Date date;
        try {
            date = sMinuteFormatter.parse(time);
        } catch (Exception e) {
            return originTime;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int calYear = cal.get(Calendar.YEAR);
        int calMonth = cal.get(Calendar.MONTH);

        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH);

        int diffMonth = (nowYear - calYear) * 12 + nowMonth - calMonth;
        if (diffMonth >= 12) {
            return sDateFormatter.format(date);
        } else if (diffMonth > 1) {
            return Integer.toString(diffMonth) + "个月前";
        } else {
            long time1 = cal.getTime().getTime();
            long time2 = now.getTime().getTime();
            long diffDay = (time2 - time1) / (3600000 * 24);
            if (diffDay > 30 && diffDay < 62) {
                return "1个月前";
            } else if (diffDay >= 1) {
                return Integer.toString((int) diffDay) + "天前";
            } else {
                long diffHour = (time2 - time1) / 3600000;
                if (diffHour > 0) {
                    return Integer.toString((int) diffHour) + "小时前";
                } else if (diffHour == 0) {
                    long diffMin = (time2 - time1) / 60000 + 1;
                    if (diffMin > 0 && diffMin <=1) {
                        return "刚刚";
                    } else if (diffMin > 0)
                        return Integer.toString((int) diffMin) + "分钟前";
                }
            }

        }
        return time;
    }

    /**
     * 资讯转换时间 :30天以上数据不显示。
     *
     * @param time
     * @return
     */
    public static String convertNewsTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        String originTime = time.replace(HTML_SPACE, "");
        time = matchDate(null, time);

        Date date;
        try {
            date = sMinuteFormatter.parse(time);
        } catch (Exception e) {
            return originTime;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int calYear = cal.get(Calendar.YEAR);
        int calMonth = cal.get(Calendar.MONTH);

        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH);

        int diffMonth = (nowYear - calYear) * 12 + nowMonth - calMonth;
        if (diffMonth >= 12) {
            return "";
        } else if (diffMonth > 1) {
            return null;
        } else {
            long time1 = cal.getTime().getTime();
            long time2 = now.getTime().getTime();
            long diffDay = (time2 - time1) / (3600000 * 24);
            if (diffDay > 30 && diffDay < 62) {
                return "";
            } else if (diffDay >= 1) {
                return Integer.toString((int) diffDay) + "天前";
            } else {
                long diffHour = (time2 - time1) / 3600000;
                if (diffHour > 0) {
                    return Integer.toString((int) diffHour) + "小时前";
                } else if (diffHour == 0) {
                    long diffMin = (time2 - time1) / 60000 + 1;
                    if (diffMin == 1) {
                        return "刚刚";
                    } else if (diffMin > 0)
                        return Integer.toString((int) diffMin) + "分钟前";
                }
            }

        }
        return time;
    }

    public static String topicTimeConvert(long time) {
        //后台返回的时间戳已经是UTC + 时区差
//        long unixTimeGMT = time * 1000 - TimeZone.getDefault().getRawOffset();
        long unixTimeGMT = time * 1000;
        Date date = new Date(unixTimeGMT);
        Date curDate = new Date();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(curDate);
        calendar2.setTime(date);
        int type = 0;
        int secDiff = (int) (curDate.getTime() / 1000 - time);
        if (secDiff <= 0) {
            secDiff = 1;
        }
        int minDiff = secDiff / 60;
        int hourDiff = minDiff / 60;
        int dayDiff = hourDiff / 24;
        if (dayDiff < 7) {
            if (0 == dayDiff) {
                if (hourDiff > 0) {
                    type = 1;
                } else {
                    if (minDiff > 0) {
                        type = 2;
                    } else {
                        type = 3;
                    }
                }
            } else {
                type = 4;
            }
        }

        String timeStr = null;
        switch (type) {
            case 0:
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                timeStr = format.format(date);
                break;

            case 1:
                timeStr = "" + hourDiff + "小时前";
                break;

            case 2:
                timeStr = "" + minDiff + "分钟前";
                break;

            case 3:
                timeStr = "" + secDiff + "秒前";
                break;

            case 4:
                timeStr = "" + dayDiff + "天前";
                break;
        }

        return timeStr;
    }

    public static String convertCardLibTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        String originTime = time.replace(HTML_SPACE, "");
        time = matchDate(null, time);

        Date date;
        try {
            date = sMinuteFormatter.parse(time);
        } catch (Exception e) {
            return originTime;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar now = Calendar.getInstance();

        long calTime = cal.getTime().getTime();
        long nowTime = now.getTime().getTime();

        final long diff = nowTime - calTime;

        // 一个月按30天算
        if (diff <= TIME_ONE_MINUTE) {
            return "刚刚";
        } else if (diff <= TIME_ONE_HOUR) {
            return Integer.toString((int) (diff / TIME_ONE_MINUTE)) + "分钟前";
        } else if (diff <= TIME_ONE_DAY) {
            return Integer.toString((int) (diff / TIME_ONE_HOUR)) + "小时前";
        } else if (diff <= TIME_30_DAY) {
            return Integer.toString((int) (diff / TIME_ONE_DAY)) + "天前";
        } else {
            int month = (int) (diff / TIME_30_DAY);
            if (month == 0) {
                month = 1;
            }
            if (month <= 12) {
                return month + "个月前";
            } else {
                return Integer.toString(month / 12) + "年前";
            }
        }
    }

    public static String convertFavorCardLibTime(long timestamp) {
        Date date;
        try {
            date = new Date(timestamp);
        } catch (Exception e) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar now = Calendar.getInstance();

        long calTime = cal.getTime().getTime();
        long nowTime = now.getTime().getTime();

        final long diff = nowTime - calTime;

        // 一个月按30天算
        if (diff <= TIME_ONE_MINUTE) {
            return "刚刚";
        } else if (diff <= TIME_ONE_HOUR) {
            return Integer.toString((int) (diff / TIME_ONE_MINUTE)) + "分钟前";
        } else if (diff <= TIME_ONE_DAY) {
            return Integer.toString((int) (diff / TIME_ONE_HOUR)) + "小时前";
        } else if (diff <= TIME_30_DAY) {
            return Integer.toString((int) (diff / TIME_ONE_DAY)) + "天前";
        } else {
            return sDateFormatter.format(date);
        }
    }

    public static long getTimestamp(String time) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        }
        long timestamp = 0;
        try {
            if (time.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}")) {
                timestamp = sSecondFormatter.parse(time).getTime();
            } else if (time.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}")) {
                timestamp = sMinuteFormatter.parse(time).getTime();
            }
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        return timestamp;
    }

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String battleTimeConvert(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = format.parse(time);
            SimpleDateFormat newformat = new SimpleDateFormat("MM-dd HH:mm");
            String newTime = newformat.format(date);
            return newTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return formatter.format(currentTime);
    }


    /**
     * 默认使用　yyyy年MM月dd日
     *
     * @param time    long类型
     * @param formate
     * @return
     */
    public static String getDate(long time, String formate) {
        try {
            if (TextUtils.isEmpty(formate)) {
                formate = "yyyy年MM月dd日";
            }
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 默认使用　yyyy年MM月dd日
     *
     * @param date    Data类型
     * @param formate
     * @return
     */
    public static String getDate(Date date, String formate) {
        try {
            if (date == null) {
                return "";
            }
            if (TextUtils.isEmpty(formate)) {
                formate = "yyyy年MM月dd日";
            }
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 距离当前时间多久,最大单位为天
     *
     * @param context
     * @param duration
     * @return
     */
    public static String getDurationMs(Context context,long duration) {
        if (duration <= 0) {
            return "";
        }
        String dayStr = context.getString(R.string.day);
        String hourStr = context.getString(R.string.hour);
        String minuteStr = context.getString(R.string.minute);
        String secondStr = context.getString(R.string.second);
        try {
            duration = duration / 1000;
            long hour = duration / 3600;
            long min = (duration % 3600) / 60;
            long s = (duration % 3600) % 60;
            if (hour >= 48) {
                return (hour / 24) + dayStr;
            }
            if (hour == 0) {
                if (min == 0) {
                    return s + secondStr;
                } else {
                    return min + minuteStr + s + secondStr;
                }
            } else {
                return hour + hourStr + min + minuteStr + s + secondStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 根据格式格式化时间
     *
     * @param time
     * @param formate
     * @return
     */
    public static String getFormateTime(long time, String formate) {
        try {
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1970/12/21";

    }

    /**
     * 当前是否是圣诞节
     *
     * @return
     */
    public static boolean isChristmas() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (month == Calendar.DECEMBER && day >= 23 && day < 26) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是昨天
     *
     * @param time
     * @return
     */
    public static boolean isYesterday(long time) {

        Date oldTime = new Date(time);
        Date newTime = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today;
        try {
            today = format.parse(todayStr);
            if ((today.getTime() - oldTime.getTime()) > 0 && (today.getTime() - oldTime.getTime()) <= 86400000) {
                return true;
            } else if ((today.getTime() - oldTime.getTime()) <= 0) {
                return false;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断是否是今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(long time) {

        Date oldTime = new Date(time);
        Date newTime = new Date(System.currentTimeMillis());
        return isSameDate(oldTime, newTime);
    }

    /**
     * 是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 聊天时显示的时间格式
     *
     * @param time
     * @return
     */
    public static String getTimeForChatMessage(long time) {

        if (isToday(time)) {
            return getFormateTime(time, "HH:mm");
        }

        if (isYesterday(time)) {
            return getFormateTime(time, "昨天 HH:mm");
        }

        return getFormateTime(time, "yyyy-MM-dd HH:mm");

    }

    /**
     * time UNIX时间
     * 表示规则：
     * １个小时内,使用ｘｘ分钟前
     * ５个小时内，使用ｘｘ小时前
     * 大于５个小时，使用时间规则
     *
     * @param context
     * @param time
     * @return
     */
    public static String getDurationForDiy(Context context, long time) {
        long duration = System.currentTimeMillis() - time;
        if (duration <= 0) {
            return "";
        }
        String minuteStr = context.getResources().getString(R.string.minute);
        String hourStr = context.getResources().getString(R.string.hour);
        String beforeStr = context.getResources().getString(R.string.before);
        String justStr = context.getResources().getString(R.string.just);
        try {
            duration = duration / 1000;
            long hour = duration / 3600;
            long min = (duration % 3600) / 60;
            if (hour == 0) {
                if (min > 0)
                    return min + minuteStr + beforeStr;
            } else {
                if (hour >= 1 && hour <= 5) {
                    return hour + hourStr + beforeStr;
                } else {
                    return getFormateTime(time, "yyyy/MM/dd");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return justStr;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();

        if (time2 >= time1) {
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } else {
            return -1;
        }
    }

    /**
     * 获取当前时间前N天的0点时刻
     *
     * @param days
     * @return
     */
    public static long getPastDate0Time(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        try {
            date = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time = cal.getTimeInMillis();
        return time - days * 24 * 60 * 60 * 1000;
    }

}
