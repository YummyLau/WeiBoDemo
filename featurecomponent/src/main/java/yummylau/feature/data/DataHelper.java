package yummylau.feature.data;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.view.animation.Transformation;

import com.alibaba.android.arouter.launcher.ARouter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Singleton;

import yummylau.common.util.making.DateUtils;
import yummylau.feature.AbsentLiveData;
import yummylau.feature.data.local.db.entity.TimeZoneEntity;

/**
 * 存放一些全局变量
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class DataHelper {

//    public FeatureRepository featureRepository;
//
//    private final LiveData<Resource<List<TimeZoneEntity>>> timeZoneData;
//    private final MutableLiveData<Boolean> fetchTimeZone = new MutableLiveData<>();

    public DataHelper() {
//        timeZoneData = Transformations.switchMap(fetchTimeZone, new Function<Boolean, LiveData<Resource<List<TimeZoneEntity>>>>() {
//            @Override
//            public LiveData<Resource<List<TimeZoneEntity>>> apply(Boolean input) {
//                if (input) {
//                    return featureRepository.initTimeZones();
//                } else {
//                    return AbsentLiveData.create();
//                }
//            }
//        });
    }

//    public void fetchTimeZone() {
//        fetchTimeZone.setValue(true);
//    }
//
//    public LiveData<Resource<List<TimeZoneEntity>>> getTimeZoneData() {
//        return timeZoneData;
//    }

    public static String transformTime(String createTime) {
        //Mon Dec 25 23:38:25 +0800 2017
        String datePart = "EEE MMM dd HH:mm:ss Z yyyy";
        String timeZonePart = "Z";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePart, Locale.ENGLISH);
        SimpleDateFormat timeZoneFormat = new SimpleDateFormat(timeZonePart, Locale.ENGLISH);
        try {
            //微博创建时date
            Date date = dateFormat.parse(createTime);
            //取出创建时时区
            String dateTimeZone = timeZoneFormat.format(date);
            //换算当前时区与创建时区
            date = DateUtils.changeTimeZone(date, TimeZone.getTimeZone(dateTimeZone), TimeZone.getDefault());
            return DateUtils.getDiffTime(date.getTime());
        } catch (Exception e) {
            return createTime;
        }
    }
}
