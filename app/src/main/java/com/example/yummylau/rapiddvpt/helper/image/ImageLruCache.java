package com.example.yummylau.rapiddvpt.helper.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.example.yummylau.rapiddvpt.util.common.MD5Utils;


/**
 * 图片缓存的存取接口类
 *
 * @author g8164
 */
public class ImageLruCache implements ImageCache {

    public static final String TAG = ImageLruCache.class.getSimpleName();

    private LruCache<String, Bitmap> mMemoryCache;      //内存缓存

    private ImageDiskCache mImageDiskLruCache;          //磁盘缓存

    public static ImageLruCache sImageLruCache=null;

    /**
     * 构造函数
     *
     * @param context
     */
    @SuppressLint("NewApi")
    public ImageLruCache(Context context) {

        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // 使用最大可用内存值的1/16作为缓存的大小。
        int cacheSize = maxMemory / 16;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    return bitmap.getByteCount() / 1024;
                } else {
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            }
        };
        mImageDiskLruCache = new ImageDiskCache(context);
    }

    /**
     * 添加图片进缓存
     *
     * @param url
     * @param bitmap
     */
    @Override
    public void putBitmap(final String url, final Bitmap bitmap) {
        String key = MD5Utils.generateMD5(url);
        mMemoryCache.put(key, bitmap);
        ImageDiskCachePutter.getInstance().putImageToDiskCache(mImageDiskLruCache, url, bitmap);
    }

    /**
     * 从缓存中取出图片
     *
     * @param url
     * @return
     */
    @Override
    public Bitmap getBitmap(String url) {

        if (url == null || url.isEmpty()) return null;

        String key = MD5Utils.generateMD5(url);

        Bitmap bitmap = mMemoryCache.get(key);

        if (bitmap != null) {
//            LogPrinter.i(TAG, "image was found in MemoryCache:" + url);
            return bitmap;
        } else {
            bitmap = mImageDiskLruCache.getBitmapFromDiskCache(url);
            if (bitmap != null) {
//                LogPrinter.i(TAG, "image was found in DiskCache:" + url);
                mMemoryCache.put(key, bitmap);
            }
            return bitmap;
        }
    }


    /**
     * 删除缓存图片，内存中和磁盘中
     *
     * @param url
     * @return
     */
    public boolean removeBitmap(String url) {
        String key = MD5Utils.generateMD5(url);
        Bitmap bitmap = mMemoryCache.remove(key);
        boolean result = mImageDiskLruCache.removeImageFromDiskCache(url);
        return bitmap != null && result;        //这里返回结果有点问题：图片不一定存在内存缓存中
    }

//    public static ImageLruCache getInstance(Context context) {
//        return ((GameServiceApplication) context.getApplicationContext()).getImageCache();
//    }

    public static ImageLruCache getInstance(Context context){
        if(sImageLruCache==null){
            synchronized (ImageLruCache.class){
                if(sImageLruCache==null){
                    sImageLruCache=new ImageLruCache(context);
                }
            }
        }
        return sImageLruCache;
    }


}

