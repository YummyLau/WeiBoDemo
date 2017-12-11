package yummylau.account.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * 类型转化，兼容room存储
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
