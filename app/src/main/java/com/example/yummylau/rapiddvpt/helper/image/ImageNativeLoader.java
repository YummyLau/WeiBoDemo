package com.netease.hearthstone.biz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ImageNativeLoader {
    private LruCache<String, Bitmap> mLruCache;     //图片缓存的核心类

    private ExecutorService mThreadPool;            //线程池
    private static int mThreadCount = 5;            //线程池的线程数量，默认为5
    private Type mType = Type.LIFO;                 //队列的调度方式
    private LinkedList<Runnable> mTasks;            //任务队列
    private Thread mPoolThread;                     //轮询的线程
    private Handler mPoolThreadHander;
    private Handler mHandler;                       //运行在UI线程的handler，用于给ImageView设置图片

    private volatile Semaphore mSemaphore = new Semaphore(0);  //引入一个值为1的信号量，防止mPoolThreadHander未初始化完成

    private volatile Semaphore mPoolSemaphore;      //引入一个值为1的信号量，由于线程池内部也有一个阻塞线程，防止加入任务的速度过快，使LIFO效果不明显
    private static ImageNativeLoader mInstance;

    private static LinkedHashMap<String, Bitmap> mImageCacheForImagePage;

    /**
     * 队列的调度方式
     *
     * @author g8164
     */
    public enum Type {
        FIFO, LIFO
    }


    /**
     * 单例获得该实例对象
     *
     * @return
     */
    public static ImageNativeLoader getInstance() {

        if (mInstance == null) {
            synchronized (ImageNativeLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageNativeLoader(mThreadCount, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    private ImageNativeLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    private void init(int threadCount, Type type) {
        // loop thread
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                mPoolThreadHander = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());
                        try {
                            mPoolSemaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 释放一个信号量
                mSemaphore.release();
                Looper.loop();
            }
        };
        mPoolThread.start();

        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 16;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }

        };

        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mPoolSemaphore = new Semaphore(threadCount);
        mTasks = new LinkedList<>();
        mType = type == null ? Type.LIFO : type;

    }

    /**
     * 加载图片
     *
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView) {
        if (null == path) {
            return;
        }
        // set tag
        imageView.setTag(path);
        // UI线程
        if (mHandler == null) {
            mHandler = new ImageMessageHandler();
        }

        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null) {
            ImgBeanHolder holder = new ImgBeanHolder();
            holder.bitmap = bm;
            holder.imageView = imageView;
            holder.path = path;
            Message message = Message.obtain();
            message.obj = holder;
            mHandler.sendMessage(message);
        } else {
            addTask(new Runnable() {
                @Override
                public void run() {
                    ImageSize imageSize = getImageViewWidth(imageView);

                    //对取出来的图像大小进行尺寸压缩
                    int reqWidth;
                    int reqHeight;
                    if (imageSize.width > 2048 || imageSize.height > 2048) {
                        reqWidth = (int) (imageSize.width / 8.0f);
                        reqHeight = (int) (imageSize.height / 8.0f);
                    } else if (imageSize.width > 1024 || imageSize.height > 1024) {
                        reqWidth = (int) (imageSize.width / 4.0f);
                        reqHeight = (int) (imageSize.height / 4.0f);
                    } else {
                        reqWidth = (int) (imageSize.width / 2.0f);
                        reqHeight = (int) (imageSize.height / 2.0f);
                    }

                    Bitmap bm = null;
                    try {
                        bm = decodeSampledBitmapFromResource(path, reqWidth, reqHeight);
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                    if (bm == null) {
                        LogPrinter.i("ImageLoaderNative", path);
                    }
                    addBitmapToLruCache(path, bm);
                    ImgBeanHolder holder = new ImgBeanHolder();
                    holder.bitmap = getBitmapFromLruCache(path);
                    holder.imageView = imageView;
                    holder.path = path;
                    Message message = Message.obtain();
                    message.obj = holder;
                    mHandler.sendMessage(message);
                    mPoolSemaphore.release();
                }
            });
        }

    }

    /**
     * 加载图片查看器中的本地图像
     *
     * @param path
     * @param imageView
     * @param onLoadNativeImageListener
     */

    public void loadPageImage(final String path, final ImageView imageView, final OnLoadNativeImageListener onLoadNativeImageListener) {
        if (null == path) {
            return;
        }
        final String pathKey = path + "2";

        //LogPrinter.i("ImageLoaderNative1", pathKey);

        // set tag
        imageView.setTag(pathKey);
        // UI线程
        if (mHandler == null) {
            mHandler = new ImageMessageHandler();
        }
        if (mImageCacheForImagePage == null) {
            mImageCacheForImagePage = new LinkedHashMap<>();
        }
        Bitmap bm = mImageCacheForImagePage.get(pathKey);
        if (bm != null) {
            ImgBeanHolder holder = new ImgBeanHolder();
            holder.bitmap = bm;
            holder.imageView = imageView;
            holder.path = pathKey;
            holder.onLoadNativeImageListener = onLoadNativeImageListener;
            Message message = Message.obtain();
            message.obj = holder;
            mHandler.sendMessage(message);
        } else {
            addTask(new Runnable() {
                @Override
                public void run() {
                    ImageSize imageSize = getImageViewWidth(imageView);
                    Bitmap bm = null;
                    try {
                        bm = decodeSampledBitmapFromResource(path, imageSize.width, imageSize.height);

                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                    if (bm == null) {
                        LogPrinter.i("ImageLoaderNative", pathKey);
                    } else {
                        //维护缓存并动态回收
                        mImageCacheForImagePage.put(pathKey, bm);
                        //LogPrinter.i("ImageLoaderNative2", pathKey);
                    }
                    ImgBeanHolder holder = new ImgBeanHolder();
                    holder.bitmap = bm;
                    holder.imageView = imageView;
                    holder.path = pathKey;
                    holder.onLoadNativeImageListener = onLoadNativeImageListener;
                    Message message = Message.obtain();
                    message.obj = holder;
                    mHandler.sendMessage(message);
                    mPoolSemaphore.release();
                }
            });
        }

    }

    /**
     * 添加一个任务
     *
     * @param runnable
     */
    private synchronized void addTask(Runnable runnable) {
        try {
            // 请求信号量，防止mPoolThreadHander为null
            if (mPoolThreadHander == null)
                mSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mTasks.add(runnable);

        mPoolThreadHander.sendEmptyMessage(0x110);
    }

    /**
     * 取出一个任务
     *
     * @return
     */
    private synchronized Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTasks.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTasks.removeLast();
        }
        return null;
    }

    /**
     * 单例获得该实例对象
     *
     * @return
     */
    public static ImageNativeLoader getInstance(int threadCount, Type type) {

        if (mInstance == null) {
            synchronized (ImageNativeLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageNativeLoader(threadCount, type);
                }
            }
        }
        return mInstance;
    }


    /**
     * 根据ImageView获得适当的压缩的宽和高
     *
     * @param imageView
     * @return
     */
    private ImageSize getImageViewWidth(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        final DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        final LayoutParams params = imageView.getLayoutParams();

        int width = params.width == LayoutParams.WRAP_CONTENT ? 0 : imageView.getWidth(); // Get actual image width
        if (width <= 0)
            width = params.width; // Get layout width parameter
        if (width <= 0)
            width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check
        // maxWidth
        // parameter
        if (width <= 0)
            width = displayMetrics.widthPixels;
        int height = params.height == LayoutParams.WRAP_CONTENT ? 0 : imageView.getHeight(); // Get actual image height
        if (height <= 0)
            height = params.height; // Get layout height parameter
        if (height <= 0)
            height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check
        // maxHeight
        // parameter
        if (height <= 0)
            height = displayMetrics.heightPixels;
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;

    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     */
    private Bitmap getBitmapFromLruCache(String key) {
        if (null == key) {
            return null;
        }
        return mLruCache.get(key);
    }

    /**
     * 往LruCache中添加一张图片
     *
     * @param key
     * @param bitmap
     */
    private void addBitmapToLruCache(String key, Bitmap bitmap) {
        if (getBitmapFromLruCache(key) == null) {
            if (bitmap != null)
                mLruCache.put(key, bitmap);
        }
    }

    /**
     * 计算inSampleSize，用于压缩图片
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的宽度
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth && height > reqHeight) {
            // 计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 根据计算的inSampleSize，得到压缩后图片
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);

        return bitmap;
    }

    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
        OnLoadNativeImageListener onLoadNativeImageListener;

    }

    private class ImageSize {
        int width;
        int height;
    }

    /**
     * 反射获得ImageView设置的最大宽度和高度
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;

                Log.e("TAG", value + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private static class ImageMessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
            ImageView imageView = holder.imageView;
            Bitmap bm = holder.bitmap;
            String path = holder.path;
            if (imageView.getTag().toString().equals(path) && bm != null) {
                imageView.setImageBitmap(bm);
                if (holder.onLoadNativeImageListener != null) {
                    holder.onLoadNativeImageListener.onLoadComplete();
                }
            }
        }
    }


    public static void imagePageRecycle() {
        if (mImageCacheForImagePage!=null && mImageCacheForImagePage.size() > 5) {
            synchronized (mImageCacheForImagePage) {
                Iterator<String> it = mImageCacheForImagePage.keySet().iterator();
                String key = it.next();
                // LogPrinter.i("ImageLoaderNative3", key);
                //释放第一个
                Bitmap bitmap = mImageCacheForImagePage.get(key);
                if (bitmap != null && !bitmap.isRecycled()) {
                    mImageCacheForImagePage.remove(key);
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            System.gc();
        }

    }

    public interface OnLoadNativeImageListener {
        void onLoadComplete();
    }
}
