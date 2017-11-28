package yummylau.common.util;

import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {

    /**
     * 天转小时
     */
    public static final long TIME_DAY_TO_HOUR = 24;
    /**
     * 时间小时到分钟转换
     */
    public static final long TIME_HOUR_TO_MINUTE = 60;
    /**
     * 时间分钟到秒转换
     */
    public static final long TIME_MINUTE_TO_SECOND = 60;
    /**
     * 时间1秒钟
     */
    public static final long TIME_ONE_SECOND = 1000;
    /**
     * 时间1分钟
     */
    public static final long TIME_ONE_MINUTE = TIME_MINUTE_TO_SECOND * TIME_ONE_SECOND;
    /**
     * 时间1小时
     */
    public static final long TIME_ONE_HOUR = TIME_HOUR_TO_MINUTE * TIME_MINUTE_TO_SECOND * TIME_ONE_SECOND;
    /**
     * 时间1天
     */
    public static final long TIME_ONE_DAY = TIME_DAY_TO_HOUR * TIME_HOUR_TO_MINUTE * TIME_MINUTE_TO_SECOND * TIME_ONE_SECOND;

    public static final String HTML_SPACE = "&nbsp;";

    private final static long MILLSECONDS_OF_THREE_DAYS = 259200000;

    private final static SimpleDateFormat sSecondFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private final static SimpleDateFormat sMinuteFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    private final static SimpleDateFormat sDateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final String TAG = TimeUtils.class.getSimpleName();

    public static long getCurrentDateTimestamp() {
        long currentTimestamp = new Date().getTime();
        return currentTimestamp / TimeUtils.TIME_ONE_DAY * TimeUtils.TIME_ONE_DAY;
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
            Log.e(TAG, e.getMessage());
        }
        return timestamp;
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
     * 论坛帖子列表时间转换
     *
     * @param time 时间字符串
     * @return 转换后的结果
     */
    public static String convertTime(String time, boolean showYear) {

        if (null == time) return "";

        String originTime = time.replace(HTML_SPACE, "");

        time = matchDate(null, time);

        if (null == time) return originTime;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        Date date;
//        String timeDate=time;
        try {
            date = format.parse(time);
//            timeDate=format.format(date);
            time = format.format(date);


        } catch (ParseException e) {
            return originTime;
        } catch (Exception e) {
            return originTime;
        }


        Date curDate = new Date();

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(curDate);

        long time1 = date.getTime();
        long time2 = curDate.getTime();

        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
            int diffHour = calendar2.get(Calendar.HOUR_OF_DAY) - calendar1.get(Calendar.HOUR_OF_DAY);

            if (diffHour >= 0) {
                if (((time2 - time1) / 3600000) > 0) {
                    time = Integer.toString(diffHour) + "小时前";
                } else {
                    long diffMin = (time2 - time1) / 60000;
                    if (diffMin <= 0) diffMin = 1;
                    time = Long.toString(diffMin) + "分钟前";
                }
            } else {
                //用Lin检查工具 此处需判断time是否为空 而times有指向的地址，总是不为空不用判断
                String[] times = time.split(" ");
                //if (times != null && times.length > 0) {
                if (times.length > 0) {
                    time = times[0];
                }
//                int diffMin = calendar2.get(Calendar.MINUTE) - calendar1.get(Calendar.MINUTE);
//                if (diffMin > 0) {
//                    time = Integer.toString(diffMin) + "分钟前";
//                } else {
//                    time = "1分钟前";
//                }
            }
        } else {
            String[] timeArr = time.split(" ");
            if (timeArr != null && timeArr.length > 0) {
                time = timeArr[0];
            }
        }

//        if (!showYear) {
//            int i = time.indexOf("-");
//            if (i > 0 && i + 1 < time.length()) {
//                time = time.substring(i + 1, time.length());
//            }
//        }

        if (!showYear) {
            int i = time.indexOf("-");
            if (i > 0 && i + 1 < time.length()) {
                time = time.substring(i + 1, time.length());
            }
        }

        return time;
    }

    /**
     * CC视频时间转换，时间间隔至年
     *
     * @param time 时间字符串
     * @return 转换后结果
     */
    public static String convertTimeWithInterval(String time) {

        if (null == time) return "";

        String originTime = time.replace(HTML_SPACE, "");

        time = matchDate(null, time);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            return originTime;
        } catch (Exception e) {
            return originTime;
        }

        Date curDate = new Date();

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(curDate);

        long time1 = date.getTime();
        long time2 = curDate.getTime();


        long diffDay = (time2 - time1) / (3600000 * 24);
        if (diffDay > 0 && diffDay < 30) {
            time = Integer.toString((int) diffDay) + "天前";
        } else if (diffDay >= 30 && diffDay < 360) {
            long diffMonth = diffDay / 30;
            time = Integer.toString((int) diffMonth) + "个月前";
        } else if (diffDay >= 360 && diffDay < 3600) {
            long diffYeah = diffDay / 360;
            time = Integer.toString((int) diffYeah) + "年前";
        } else if (diffDay == 0 && time2 > time1) {
            long diffHour = (time2 - time1) / 3600000;
            if (diffHour > 0) {
                time = Integer.toString((int) diffHour) + "小时前";
            } else if (diffHour == 0) {
                long diffMin = (time2 - time1) / 60000 + 1;
                time = Integer.toString((int) diffMin) + "分钟前";
            }
        } else {
            String[] times = time.split(" ");
            if (times.length > 0) {
                time = times[0];
            }
        }
        return time;
    }

    /**
     * 转换视频时间
     *
     * @param time
     * @return
     */
    public static String convertVideoTime(String time) {
        if (null == time) return "";

        String originTime = time.replace(HTML_SPACE, "");

        time = matchDate(null, time);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            return originTime;
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
            return Integer.toString(diffMonth / 12) + "年前";
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
                    if (diffMin > 0)
                        return Integer.toString((int) diffMin) + "分钟前";
                }
            }

        }
//        String[] times = time.split(" ");
//        if (times.length > 0) {
//            time = times[0];
//        }
        return time;

    }

    public static String timeStampToFullDate(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return format.format(Long.parseLong(timeStamp) * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static String timeStampToDate(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            return format.format(Long.parseLong(timeStamp) * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static String timeStampToDate(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm", Locale.getDefault());
        return format.format(timeStamp);
    }

    public static String timeStampToDate(long timeStamp, String formate) {
        String date = String.valueOf(timeStamp);
        try {
            SimpleDateFormat format = new SimpleDateFormat(formate, Locale.getDefault());
            date = format.format(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String timeStampToOnlyDate(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        try {
            return format.format(Long.parseLong(timeStamp) * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static long dateToTimestamp(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return format.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long dateToTimestamp2(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return format.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 聊天实现显示规则
     *
     * @param time 时间字符串，如“2016-3-25 11：51”
     * @return 转换后的结果
     */
    public static String convertChatTime(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }
        if (time.contains("秒") || time.contains("分钟") || time.contains("小时") || time.contains("天")) {
            return time.replace(HTML_SPACE, "");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm", Locale.CHINA);
        Date pastDate;
        try {
            pastDate = format.parse(time);
        } catch (ParseException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

        Date curDate = new Date();

        Calendar pastCalendar = Calendar.getInstance();
        Calendar curCalendar = Calendar.getInstance();
        pastCalendar.setTime(pastDate);
        curCalendar.setTime(curDate);

        String newTime = null;

        if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && pastCalendar.get(Calendar.MONTH) == curCalendar.get(Calendar.MONTH)
                && pastCalendar.get(Calendar.DATE) == curCalendar.get(Calendar.DATE)) {
            //当天

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, "");

        } else if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && curCalendar.get(Calendar.DAY_OF_YEAR) - pastCalendar.get(Calendar.DAY_OF_YEAR) == 1) {
            //昨天

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, "昨天 ");

        } else if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && pastCalendar.get(Calendar.WEEK_OF_YEAR) == curCalendar.get(Calendar.WEEK_OF_YEAR)) {
            //本周

            String day = "";
            int dayOfWeek = pastCalendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.MONDAY) {
                day = "周一 ";
            } else if (dayOfWeek == Calendar.TUESDAY) {
                day = "周二 ";
            } else if (dayOfWeek == Calendar.WEDNESDAY) {
                day = "周三 ";
            } else if (dayOfWeek == Calendar.THURSDAY) {
                day = "周四 ";
            } else if (dayOfWeek == Calendar.FRIDAY) {
                day = "周五 ";
            } else if (dayOfWeek == Calendar.SATURDAY) {
                day = "周六 ";
            } else if (dayOfWeek == Calendar.SUNDAY) {
                day = "周日 ";
            }

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, day);
        } else {
            int index = time.indexOf(" ");
            if (index > 5 && time.length() > 5) {
                String day = time.substring(5, index);
                newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, day + " ");
            }
        }

        return newTime;
    }

    /**
     * 聊天实现显示规则
     *
     * @param timeStamp 时间戳字符串
     * @return 转换后的结果
     */

    public static String convertChatTimeStamp(String timeStamp) {
        if (timeStamp == null || timeStamp.isEmpty()) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm", Locale.CHINA);

        Date pastDate;
        String time;
        try {
            if (timeStamp.length() == 10) {
                timeStamp = timeStamp + "000";
            }
            time = format.format(Long.valueOf(timeStamp));
            pastDate = format.parse(time);


        } catch (ParseException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

        Date curDate = new Date();

        Calendar pastCalendar = Calendar.getInstance();
        Calendar curCalendar = Calendar.getInstance();
        pastCalendar.setTime(pastDate);
        curCalendar.setTime(curDate);

        String newTime = null;

        if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && pastCalendar.get(Calendar.MONTH) == curCalendar.get(Calendar.MONTH)
                && pastCalendar.get(Calendar.DATE) == curCalendar.get(Calendar.DATE)) {
            //当天

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, "");

        } else if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && curCalendar.get(Calendar.DAY_OF_YEAR) - pastCalendar.get(Calendar.DAY_OF_YEAR) == 1) {
            //昨天

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, "昨天 ");

        } else if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && pastCalendar.get(Calendar.WEEK_OF_YEAR) == curCalendar.get(Calendar.WEEK_OF_YEAR)) {
            //本周

            String day = "";
            int dayOfWeek = pastCalendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.MONDAY) {
                day = "周一 ";
            } else if (dayOfWeek == Calendar.TUESDAY) {
                day = "周二 ";
            } else if (dayOfWeek == Calendar.WEDNESDAY) {
                day = "周三 ";
            } else if (dayOfWeek == Calendar.THURSDAY) {
                day = "周四 ";
            } else if (dayOfWeek == Calendar.FRIDAY) {
                day = "周五 ";
            } else if (dayOfWeek == Calendar.SATURDAY) {
                day = "周六 ";
            } else if (dayOfWeek == Calendar.SUNDAY) {
                day = "周日 ";
            }

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, day);
        } else {
            int index = time.indexOf(" ");
            if (index > 5 && time.length() > 5) {
                String day = time.substring(5, index);
                newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, day + " ");
            }
        }

        return newTime;
    }

    /**
     * 是否显示聊天时间
     *
     * @param lastTime 上一条聊天记录的时间
     * @param nowTime  本条聊天记录的时间
     * @return 本条聊天记录是否显示时间
     */
    public static boolean showChatTime(String nowTime, String lastTime) {
        if (nowTime == null || lastTime == null || nowTime.length() < 2 || lastTime.length() < 2)
            return false;
        if (nowTime.contains("秒")) {
            if (lastTime.contains("秒"))
                return false;
        } else if (nowTime.contains("分钟")) {
            int nowIndex = nowTime.indexOf("分钟");
            int lastIndex = lastTime.indexOf("分钟");
            if (lastIndex >= 0) {
                String timeNow = nowTime.substring(0, nowIndex);
                String timeLast = lastTime.substring(0, lastIndex);
                try {
                    int time1 = Integer.valueOf(timeNow);
                    int time2 = Integer.valueOf(timeLast);
                    if (time2 - time1 < 10)
                        return false;
                } catch (Exception e) {
                }
            }
        } else if (nowTime.contains("小时")) {
            if (nowTime.equals(lastTime))
                return false;
        } else if (nowTime.contains("天")) {
            if (nowTime.equals(lastTime))
                return false;
            if (nowTime.substring(0, nowTime.length() - 1).equals(lastTime.substring(0, lastTime.length() - 1)))
                return false;
        }
        return true;
    }

    /**
     * 时间戳转换为时间
     *
     * @param timeStamp 时间戳
     * @return 转换后的时间
     */

    public static String convertTimeStamp(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm", Locale.CHINA);
        String time = format.format(timeStamp);
        Date pastDate;
        try {
            pastDate = format.parse(time);

        } catch (ParseException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        Date curDate = new Date();

        Calendar pastCalendar = Calendar.getInstance();
        Calendar curCalendar = Calendar.getInstance();
        pastCalendar.setTime(pastDate);
        curCalendar.setTime(curDate);
        String newTime = null;

        if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && pastCalendar.get(Calendar.MONTH) == curCalendar.get(Calendar.MONTH)
                && pastCalendar.get(Calendar.DATE) == curCalendar.get(Calendar.DATE)) {
            //当天

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, "");

        } else if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && curCalendar.get(Calendar.DAY_OF_YEAR) - pastCalendar.get(Calendar.DAY_OF_YEAR) == 1) {
            //昨天

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, "昨天 ");

        } else if (pastCalendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                && pastCalendar.get(Calendar.WEEK_OF_YEAR) == curCalendar.get(Calendar.WEEK_OF_YEAR)) {
            //本周

            String day = "";
            int dayOfWeek = pastCalendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.MONDAY) {
                day = "周一 ";
            } else if (dayOfWeek == Calendar.TUESDAY) {
                day = "周二 ";
            } else if (dayOfWeek == Calendar.WEDNESDAY) {
                day = "周三 ";
            } else if (dayOfWeek == Calendar.THURSDAY) {
                day = "周四 ";
            } else if (dayOfWeek == Calendar.FRIDAY) {
                day = "周五 ";
            } else if (dayOfWeek == Calendar.SATURDAY) {
                day = "周六 ";
            } else if (dayOfWeek == Calendar.SUNDAY) {
                day = "周日 ";
            }

            newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, day);
        } else {
            int index = time.indexOf(" ");
            if (index > 5 && time.length() > 5) {
                String day = time.substring(5, index);
                newTime = timeFormat(pastCalendar.get(Calendar.HOUR_OF_DAY), time, day + " ");
            }
        }

        return newTime;

    }

    public static String timeFormat(int hours, String time, String tips) {
        int index = time.indexOf(" ");
        String newTime = time.substring(index + 1);

        if (0 <= hours && hours < 6) {
            newTime = tips + "凌晨" + newTime;
        } else if (6 <= hours && hours < 12) {
            newTime = tips + "早上" + newTime;
        } else if (12 <= hours && hours < 13) {
            newTime = tips + "中午" + newTime;
        } else if (13 <= hours && hours < 18) {
            newTime = tips + "下午" + newTime;
        } else if (18 <= hours && hours < 23) {
            newTime = tips + " 晚上" + newTime;
        }
        return newTime;
    }

    /**
     * 从论坛返回的时间格式中提取正确的时间
     *
     * @param time
     * @return
     */
    public static String getTime(String time) {
        String newTime = time;
        if (time.contains("span")) {
            int lIndex = time.indexOf("=\"");
            int rIndex = time.indexOf("\">");
            if (lIndex >= 0 && rIndex >= 0) {
                newTime = time.substring(lIndex + 2, rIndex);
            }
        }
        return newTime;
    }

    public static boolean isBirthDateInvalid(String birth) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(format.parse(birth));
            if (cal.before(now)) {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    public static boolean isToday(String time) {
        boolean result = false;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = format.format(new Date());
            if (time != null && time.startsWith(date)) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static abstract class WaitTill {
        public abstract boolean checkIfDone();

        public abstract void onDone();

        public void onCancel() {
        }

        private long mCheckInterval;
        private long mStartTime;
        private long mMaxWaitTime;

        public WaitTill() {
            this(10);
        }

        public WaitTill(final long checkInterval) {
            this(checkInterval, 10000L);
        }

        public WaitTill(long checkInterval, long maxWaitTime) {
            final Handler h = new Handler();
            final Runnable[] r = {null};
            mStartTime = SystemClock.elapsedRealtime();
            mCheckInterval = checkInterval;
            mMaxWaitTime = maxWaitTime;
            r[0] = new Runnable() {
                @Override
                public void run() {
                    if (checkIfDone()) {
                        onDone();
                        return;
                    }
                    if (mStartTime + mMaxWaitTime < SystemClock.elapsedRealtime()) {
                        onCancel();
                        return;
                    }
                    h.postDelayed(r[0], mCheckInterval);
                }
            };
            r[0].run();
        }

        protected void setInterval(long interval) {
            mCheckInterval = interval;
        }

        protected long getPassedTime() {
            return SystemClock.elapsedRealtime() - mStartTime;
        }

        protected long getRemainingTime() {
            return Math.max(mStartTime + mMaxWaitTime - SystemClock.elapsedRealtime(), 0);
        }
    }

    public static abstract class DoUtil {
        abstract protected long getDelay();

        abstract protected void doOnce();

        abstract protected boolean isDone();

        public DoUtil() {
            final Handler h = new Handler();
            final Runnable[] r = new Runnable[1];
            r[0] = new Runnable() {
                @Override
                public void run() {
                    if (isDone()) return;
                    doOnce();
                    h.postDelayed(r[0], getDelay());
                }
            };
            h.post(r[0]);
        }
    }

    public static class TimedValue<T> implements Serializable {
        private static final long serialVersionUID = 7692599207683087778L;

        private T mValue;
        private Long mExpiresAt;
        private Long mDefaultDurMillis;

        public TimedValue(long defaultDurMillis) {
            mDefaultDurMillis = defaultDurMillis;
        }

        /**
         * setting a value with default timeout
         */
        public TimedValue<T> set(T value) {
            return set(value, mDefaultDurMillis);
        }

        /**
         * setting a value with a specific timeout
         */
        public TimedValue<T> set(T value, long durMillis) {
            mValue = value;
            mExpiresAt = SystemClock.elapsedRealtime() + durMillis;
            return this;
        }

        public void clear() {
            mValue = null;
            mExpiresAt = null;
        }

        public T get() {
            if (mExpiresAt != null && SystemClock.elapsedRealtime() > mExpiresAt) {
                mExpiresAt = null;
                mValue = null;
                return null;
            }
            return mValue;
        }
    }

    public static boolean whitin3Days(long time) {
        long unixTimeGMT = time * 1000 - TimeZone.getDefault().getRawOffset();
        long curTime = System.currentTimeMillis();
        return curTime - unixTimeGMT < MILLSECONDS_OF_THREE_DAYS;
    }

    /**
     * @param time unix时间戳
     * @return
     */
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

    public static boolean within48Hours(String time) {

        if (null == time) return false;

        time = matchDate(null, time);
        if (null == time) return false;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

        Date curDate = new Date();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(curDate);

        long time1 = date.getTime();
        long time2 = curDate.getTime();

        return ((time2 - time1) <= 48 * 3600 * 1000);
    }

    //判断选择的日期是否是本周
    public static boolean isThisWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek == currentWeek;
    }
}
