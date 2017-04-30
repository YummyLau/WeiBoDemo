package com.example.yummylau.rapiddvpt.util.common;

import android.content.Context;
import android.content.res.Resources;
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
    public static int getResId(Context context, String name, String defType) {
        String pkgName = context.getPackageName();
        Resources resources = context.getResources();
        return resources.getIdentifier(name, defType, pkgName);
    }

//    /**
//     * 加载assets中图片，使用本地二级缓存
//     * @param context   context
//     * @param name      图片的名称
//     * @return          图片bitmap
//     */
//    public static Bitmap getAssetsImageWithCache(Context context, String name) {
//        if (null == context || null == name) {
//            return null;
//        }
////        ImageLruCache imageCache = ((GameServiceApplication)context.getApplicationContext()).getImageCache();
//        ImageLruCache imageCache = ImageLruCache.getInstance(context.getApplicationContext());
//        Bitmap bitmap = imageCache.getBitmap(name);
//        if (null != bitmap) {
//            return bitmap;
//        }
//
//        AssetManager assetManager = context.getAssets();
//        try {
//            InputStream is = assetManager.open(name);
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//
//            if (null != bitmap) {
////                imageCache.putBitmap(name, bitmap);
//                return bitmap;
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//        }
//
//        return bitmap;
//    }

    /**
     * 从assets文件夹中获取文件并读取数据
     * @param context   上下文
     * @param fileName  文件名
     * @return          返回去读的内容
     */
    public static String getFromAssets(Context context, String fileName) {
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
