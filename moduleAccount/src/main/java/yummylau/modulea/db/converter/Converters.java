package yummylau.modulea.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * 类型转化，兼容room存储
 * Created by g8931 on 2017/11/22.
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
