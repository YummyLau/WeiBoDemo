package com.netease.hearthstone.biz.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jakewharton.disklrucache.DiskLruCache;
import com.netease.hearthstone.biz.LogPrinter;
import com.netease.hearthstone.utils.FileUtils;
import com.netease.hearthstone.utils.common.MD5Utils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 磁盘缓存管理类
 *
 * @author g8164
 */
public class ImageDiskCache {

    public static final String TAG = ImageDiskCache.class.getSimpleName();

    private DiskLruCache mDiskLruCache;

    private int mDiskCacheSize = 1024 * 1024 * 100;     // 100MB                ？磁盘缓存100MB是否太小了

    /**
     * 初始化函数
     *
     * @param context
     */
    ImageDiskCache(Context context) {
        try {
            File imageCacheDir = new File(FileUtils.getImageCacheDir(context));

            //如果文件夹不存在则创建
            if (!imageCacheDir.exists()) {
                imageCacheDir.mkdirs();
            }

            // 第一个参数指定的是数据的缓存地址
            // 第二个参数指定当前应用程序的版本号，每当版本号改变，缓存路径下存储的所有数据都会被清除掉
            // 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1
            // 第四个参数指定最多可以缓存多少字节的数据
            mDiskLruCache = DiskLruCache.open(imageCacheDir, 1, 1, mDiskCacheSize);

        } catch (IOException e) {
            LogPrinter.i(TAG, "fail to open cache");
        }

    }

    /**
     * 添加图片到缓存
     * @param data
     * @param bitmap
     */
    public void addBitmapToDiskCache(final String data, final Bitmap bitmap) {
        if (data == null || bitmap == null) {
            return;
        }

        if (mDiskLruCache != null) {
            final String key = MD5Utils.generateMD5(data);
            OutputStream out = null;
            try {
                final DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                if (snapshot == null) {
                    final DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        out = editor.newOutputStream(0);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        editor.commit();
                        out.close();
                        mDiskLruCache.flush();
                    }
                } else {
                    snapshot.getInputStream(0).close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 从缓存中取出图片
     * @param data
     * @return
     */
    public final Bitmap getBitmapFromDiskCache(final String data) {
        if (data == null) {
            return null;
        }
        final String key = MD5Utils.generateMD5(data);//md5生成key
        if (mDiskLruCache != null) {
            InputStream inputStream = null;
            try {
                final DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                if (snapshot != null) {
                    inputStream = snapshot.getInputStream(0);
                    if (inputStream != null) {
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        if (bitmap != null) {
                            return bitmap;
                        }
                    }
                }
            } catch (final IOException e) {
                LogPrinter.e(TAG, "fail to ge cache in disk (IOException)");
            } catch (OutOfMemoryError e) {
                LogPrinter.e(TAG, "fail to ge cache in disk (OutOfMemoryError)");
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 删除缓存文件
     * @param data
     * @return
     */
    public boolean removeImageFromDiskCache(final String data) {
        if (data == null) {
            return false;
        }

        final String key = MD5Utils.generateMD5(data);  //md5生成key
        if (mDiskLruCache != null) {
            try {
                return mDiskLruCache.remove(key);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
