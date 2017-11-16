package com.netease.hearthstone.biz.image;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by g8164 on 2015/8/3.
 * 磁盘缓存保存队列
 */
public class ImageDiskCachePutter {

    private LinkedList<Runnable> mTaskList;                     // 任务队列
    private ExecutorService mThreadPool;                        // 线程池
    private Thread mPollingThread;                              // 轮询线程
    private Handler mPollingHandler;                            // 轮询线程中的Handler
    private final static int mThreadCount = 5;                  // 线程池的线程数量，默认为5
    private Type mType = Type.LIFO;                             // 队列的调度方式，默认为LIFO
    private volatile Semaphore mPollingSemaphore;               // 信号量，由于线程池内部也有一个阻塞线程，若加入任务的速度过快，LIFO效果不明显
    private volatile Semaphore mSemaphore = new Semaphore(0);   // 信号量，防止mPoolThreadHander未初始化完成

    private static ImageDiskCachePutter mInstance;

    public enum Type {FIFO, LIFO}

    /**
     * 单例获得实例对象
     *
     * @return 实例对象
     */
    public static ImageDiskCachePutter getInstance() {
        if (mInstance == null) {
            synchronized (ImageDiskCachePutter.class) {
                if (mInstance == null) {
                    mInstance = new ImageDiskCachePutter(mThreadCount, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    /**
     * 单例获得实例对象
     *
     * @param threadCount 线程池的线程数量
     * @param type        队列的调度方式
     * @return 实例对象
     */
    public static ImageDiskCachePutter getInstance(int threadCount, Type type) {
        if (mInstance == null) {
            synchronized (ImageDiskCachePutter.class) {
                if (mInstance == null) {
                    mInstance = new ImageDiskCachePutter(threadCount, type);
                }
            }
        }
        return mInstance;
    }

    /**
     * 构造函数
     *
     * @param threadCount 线程池的线程数量
     * @param type        队列的调度方式
     */
    private ImageDiskCachePutter(int threadCount, Type type) {
        init(threadCount, type);
    }

    /**
     * 初始化
     *
     * @param threadCount 线程池的线程数量
     * @param type        队列的调度方式
     */
    private void init(int threadCount, Type type) {

        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mPollingSemaphore = new Semaphore(threadCount);
        mTaskList = new LinkedList<>();
        mType = type == null ? Type.LIFO : type;

        // 开启轮询线程
        mPollingThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPollingHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());
                        try {
                            mPollingSemaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                mSemaphore.release();   // 释放一个信号量
                Looper.loop();
            }
        };
        mPollingThread.start();
    }

    /**
     * 添加一个任务
     *
     * @param task 任务
     */
    private synchronized void addTask(Runnable task) {
        try {
            // mPollingHanler为空时，请求信号量，因为mPollingHanler创建完成会释放一个信号量
            if (mPollingHandler == null) mSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mTaskList.add(task);

        mPollingHandler.sendEmptyMessage(0x110);
    }

    /**
     * 取出一个任务
     *
     * @return 需要执行的任务
     */
    private synchronized Runnable getTask() {
        if (mType == Type.LIFO) {
            return mTaskList.removeLast();
        } else if (mType == Type.FIFO) {
            return mTaskList.removeFirst();
        }
        return null;
    }

    /**
     * 图片保存到磁盘缓存
     *
     * @param diskCache 磁盘缓存
     * @param url       图片地址
     * @param bitmap    位图
     */
    public void putImageToDiskCache(final ImageDiskCache diskCache, final String url, final Bitmap bitmap) {
        addTask(new Runnable() {
            @Override
            public void run() {
                diskCache.addBitmapToDiskCache(url, bitmap);
                mPollingSemaphore.release();
            }
        });
    }
}
