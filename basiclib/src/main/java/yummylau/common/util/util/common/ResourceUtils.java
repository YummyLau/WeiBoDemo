package yummylau.common.util.util.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 解析资源
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class ResourceUtils {

    private final static String TAG = ResourceUtils.class.getSimpleName();
    private final static String UTF8 = "UTF-8";

    /**
     * 根据名称获取对应的资源id
     * @param context  上下文
     * @param name     名称
     * @param defType  类型
     * @return         返回id
     */
    public static int getResId(@NonNull Context context, @NonNull String name, @NonNull String defType) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(defType)){
            return -1;
        }
        try {
            String pkgName = context.getPackageName();
            Resources resources = context.getResources();
            return resources.getIdentifier(name, defType, pkgName);
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }
        return -1;
    }

    /**
     * 加载assets中图片
     * @param context   context
     * @param name      图片的名称
     * @return          图片bitmap
     */
    public static Bitmap getAssetsImage(@NonNull Context context, @NonNull String name) {
        if (null == context || null == name) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            InputStream is = context.getResources().getAssets().open(name);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG,e.toString());
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        } catch (OutOfMemoryError e) {
            Log.e(TAG,e.toString());
        }
        return bitmap;
    }

    /**
     * 从assets文件夹中获取文件并读取数据
     * @param context   上下文
     * @param fileName  文件名
     * @return          返回去读的内容
     */
    public static String getAssetsFileString(Context context, String fileName) {
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int length = in.available();
            // 创建byte数组
            byte[] buffer = new byte[length];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
