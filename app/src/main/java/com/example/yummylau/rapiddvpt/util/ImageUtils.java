package com.example.yummylau.rapiddvpt.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.yummylau.rapiddvpt.util.common.NetUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  图片utils
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class ImageUtils {

    public final static int CONNECT_TIME_OUT_MILLIS_NORMAL = 10 * 1000;                             //10秒连接超时
    public final static int READ_TIME_OUT_MILLIS_NORMAL = 30 * 1000;                                //30秒读取超时

    public final static int IMAGE_TYPE_HIGH = 1;                                                    // 高质量图片
    public final static int IMAGE_TYPE_LOW = 2;                                                     // 低质量图片

    // 论坛图片清晰度类型
    public final static int BIG_IMAGE_WIFI_ONLY = 1;
    public final static int BIG_IMAGE = 2;
    public final static int SMALL_IMAGE = 3;

    public static final int REQUEST_CODE_CAPTURE_CAMERA = 0;

    public static final String PATTERN_ADJUST_PARAM = "_\\d*?x\\d*?x0.jpg";                         // 参数正则


    /**
     * 通过Glide加载图片
     * @param context
     * @param url
     * @param imageView
     * @param defaultImageId
     */
    public static void loadImage(Context context, String url, ImageView imageView, int defaultImageId) {
        if (imageView == null) {
            return;
        }
        if(context!=null && context instanceof Activity){
            loadImage((Activity)context,url,imageView,defaultImageId);
            return;
        }
        if (TextUtils.isEmpty(url) && defaultImageId > 0) {
            imageView.setImageResource(defaultImageId);
        } else if (context != null && imageView != null && defaultImageId > 0) {
            // TODO: 17-4-30  加载图片
        }
    }

    /**
     * 通过Glide加载图片,与Activity生命周期绑定
     *
     * @param activity
     * @param url
     * @param imageView
     * @param defaultImageId
     */
    public static void loadImage(Activity activity, String url, ImageView imageView, int defaultImageId) {
        if (activity != null && imageView != null && defaultImageId > 0) {
            // TODO: 17-4-30  加载图片
        }
    }

    /**
     * 通过Glide加载图片,与Fragment生命周期绑定
     *
     * @param fragment
     * @param url
     * @param imageView
     * @param defaultImageId
     */
    public static void loadImage(Fragment fragment, String url, ImageView imageView, int defaultImageId) {
        if (fragment != null && imageView != null && defaultImageId > 0) {
//            Glide.with(fragment).
//                    load(url).
//                    dontAnimate().
//                    placeholder(defaultImageId).
//                    into(imageView);
            // TODO: 17-4-30  加载图片
        }
    }

    /**
     * 使用HttpURLConnection下载图片
     *
     * @param url 图片地址
     * @return 图片位图
     */
    public static Bitmap downloadImage(String url) {
        URL imgUrl;
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) imgUrl.openConnection();
            urlConnection.setUseCaches(false);              //不使用http缓存，使用内存磁盘二级缓存
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(CONNECT_TIME_OUT_MILLIS_NORMAL);
            urlConnection.setReadTimeout(READ_TIME_OUT_MILLIS_NORMAL);
            urlConnection.connect();

            is = urlConnection.getInputStream();

            // 将InputStream转换成Bitmap
            if (is != null) {
//                bitmap = BitmapFactory.decodeStream(is);                                          // 4.3系统有bug
                byte[] data = getBytes(is);
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
        } catch (OutOfMemoryError | Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    private static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // 用数据装
        int len;
        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        return outstream.toByteArray();
    }

    /**
     * 对nos图片进行内缩略，结果图片宽和高都小于或等于请求尺寸宽高
     * 默认图片质量为75
     * Widthx0          限定宽度，高度自适应
     * 0xHeight         限定高度，宽度自适应
     *
     * @param context 为空则不进行图片质量的调整
     * @param url     图片地址
     * @param width   图片限定宽度
     * @param height  图片限定高度
     * @return 符合要求的图片地址
     */
    public static String convertToScaledImgUrl(Context context, String url, int width, int height) {
        return convertToScaledImgUrl(context, url, width, height, 75, true, true);                  // 默认图片质量为75、判断网络类型、使用WebP
    }

    /**
     * 对nos图片进行内缩略，结果图片宽和高都小于或等于请求尺寸宽高
     * Widthx0          限定宽度，高度自适应
     * 0xHeight         限定高度，宽度自适应
     *
     * @param context     为空则不进行图片质量的调整
     * @param url         图片地址
     * @param width       图片限定宽度
     * @param height      图片限定高度
     * @param quality     图片质量0-100,不传默认取值75
     * @param doImageType 是否执行图片类型判断
     * @return 符合要求的图片地址
     */
    public static String convertToScaledImgUrl(Context context, String url, int width, int height, int quality, boolean doImageType, boolean ifWebP) {
        if (url == null) return null;
        if (context != null && doImageType && getImageType(context) == IMAGE_TYPE_LOW) {
            width = (int) (width / 2.0);
            height = (int) (height / 2.0);
        }
        StringBuilder sb = new StringBuilder();
        int subIndex = url.indexOf("?");
        if (subIndex != -1) url = url.substring(0, subIndex);
        //sb.append(url).append("?imageView&thumbnail=").append(width).append("x").append(height).append("&quality=").append(quality).append("&type=webp");
        sb.append(url).append("?imageView&thumbnail=").append(width).append("x").append(height).append("&quality=").append(quality);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ifWebP) {
            sb.append("&type=webp");
        }
        return sb.toString();
    }

//    /**
//     * 根据链接自动使用图片的链接的方式
//     */
//    public static String convertToScaledImgUrlAuto(Context context, String url, int width, int height) {
//        if (ifNosImageUrl(url)) {
//            return convertToScaledImgUrl(context, url, width, height);
//        } else {
//            return convertToAdjustSizeUrl(context, url, width, height);
//        }
//    }

//    /**
//     * 使用外链图片处理接口进行图片尺寸压缩
//     * http://imgsize.ph.126.net/?imgurl=url_512x512x0.jpg
//     * 若为gif图片，后缀改为gif
//     *
//     * @param url 图片源url
//     * @return 处理后的图片链接地址
//     */
//    public static String convertToAdjustSizeUrl(Context context, String url, int width, int height) {
//        if (url == null || url.isEmpty()) return url;
//        String urlPre = AppConfig.getThirdPartyImageUrl(context);
//        if (urlPre == null || urlPre.isEmpty()) return url;
//        url = convertBackAdjustSizeUrl(context, url);
//        if (context != null && getImageType(context) == IMAGE_TYPE_LOW) {
//            width = (int) (width / 2.0);
//            height = (int) (height / 2.0);
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append(urlPre).append(url).append("_").append(width).append("x").append(height).append("x0.jpg");
//        return sb.toString();
//    }
//    /**
//     * 还原外链图片处理接口
//     *
//     * @param url 外链图片处理接口对应的url
//     * @return 还原后的url
//     */
//    public static String convertBackAdjustSizeUrl(Context context, String url) {
//        if (url == null || url.isEmpty()) return url;
//        String urlPre = AppConfig.getThirdPartyImageUrl(context);
//        if (urlPre == null || urlPre.isEmpty()) return url;
//        return url.replace(urlPre, "").replaceAll(PATTERN_ADJUST_PARAM, "");
//    }
    /**
     * 是否为nos图片链接
     */
    public static boolean ifNosImageUrl(String url) {
        return url != null && !url.isEmpty() && url.contains("gameyw-gbox");
    }

    /**
     * 是否为原图地址
     */
    public static boolean ifOriginImageUrl(String url) {
        if (url == null || url.isEmpty()) return false;
        if (ifNosImageUrl(url)) {
            return !url.contains("?imageView");
        } else {
            Pattern pattern = Pattern.compile(PATTERN_ADJUST_PARAM);
            Matcher matcher = pattern.matcher(url);
            return !matcher.find();
        }
    }
    /**
     * 加载Nos原图的url
     */
    public static String convertBackNosUrl(String url) {
        if (url != null) {
            int subIndex = url.indexOf("?");
            if (subIndex != -1) url = url.substring(0, subIndex);
        }
        return url;
    }


//    /**
//     * 是否在缓存中
//     */
//    public static boolean isUrlInImageCache(Context context, String url) {
//        ImageLruCache imageCache = ImageLruCache.getInstance(context);
////        // 从缓存中获取图片，不为空则显示
//        Bitmap bitmap = imageCache.getBitmap(url);
//        if (null != bitmap) {
//            return true;
//
//        }
//        return false;
//    }

//    /**
//     * 加载网络图片，使用本地二级缓存
//     *
//     * @param context 上下文
//     * @param url     图片的url
//     * @return 图片bitmap
//     */
//    public static Bitmap getNetworkImageWithCache(Context context, String url) {
//
//        if (null == context || !URLUtils.verifyURL(url)) {
//            return null;
//        }
////        ImageLruCache imageCache = ((GameServiceApplication) context.getApplicationContext()).getImageCache();
//        ImageLruCache imageCache = ImageLruCache.getInstance(context.getApplicationContext());
//
//        Bitmap bitmap = null;
//        try {
//            bitmap = imageCache.getBitmap(url);
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//        }
//        if (null != bitmap) return bitmap;
//
//        try {
//            bitmap = downloadImage(url);
//        } catch (OutOfMemoryError e) {
//            e.printStackTrace();
//        }
//
//        if (null != bitmap) imageCache.putBitmap(url, bitmap);
//
//        return bitmap;
//    }

    /**
     * 根据配置与网络情况获取图片质量
     */
    public static int getImageType(Context context) {
        if (context == null) return IMAGE_TYPE_HIGH;
        int type;
        //// TODO: 2017/3/17 此处写死默认为BIG_IMAGE_WIFI_ONLY
        int imageType =BIG_IMAGE_WIFI_ONLY;
        if (imageType == BIG_IMAGE) {                                               // 大图模式
            type = IMAGE_TYPE_HIGH;
        } else if (imageType == SMALL_IMAGE) {                                      // 小图模式
            type = IMAGE_TYPE_LOW;
        } else {                                                                                    // wifi大图，其他小图
            int networkType = NetUtils.getNetworkType(context);
            if (networkType == NetUtils.NETWORK_TYPE_WIFI) {
                type = IMAGE_TYPE_HIGH;
            } else {
                type = IMAGE_TYPE_LOW;
            }
        }
        return type;
    }


    /**
     * 图片尺寸是否超过OpenGLRenderer的限制 获取应当缩放的比率
     *
     * @param w
     * @param h
     * @return
     */

    public static float getLargeBitMapScaleRatio(int w, int h) {
        int maxSize = getOpenGLBitmapMaxSize();
        if (maxSize < h || maxSize < w) {
            return h >= w ? maxSize / (h + 1.f) : maxSize / (w + 1.f);
        }
        return 1.f;
    }

    public static int getOpenGLBitmapMaxSize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            EGLDisplay dpy = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
            int[] vers = new int[2];
            EGL14.eglInitialize(dpy, vers, 0, vers, 1);
            int[] configAttr = {
                    EGL14.EGL_COLOR_BUFFER_TYPE, EGL14.EGL_RGB_BUFFER,
                    EGL14.EGL_LEVEL, 0,
                    EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                    EGL14.EGL_SURFACE_TYPE, EGL14.EGL_PBUFFER_BIT,
                    EGL14.EGL_NONE
            };
            EGLConfig[] configs = new EGLConfig[1];
            int[] numConfig = new int[1];
            EGL14.eglChooseConfig(dpy, configAttr, 0,
                    configs, 0, 1, numConfig, 0);
            if (numConfig[0] == 0) {
                // TROUBLE! No config found.
            }
            EGLConfig config = configs[0];
            int[] surfAttr = {
                    EGL14.EGL_WIDTH, 64,
                    EGL14.EGL_HEIGHT, 64,
                    EGL14.EGL_NONE
            };
            EGLSurface surf = EGL14.eglCreatePbufferSurface(dpy, config, surfAttr, 0);

            int[] ctxAttrib = {
                    EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                    EGL14.EGL_NONE
            };
            EGLContext ctx = EGL14.eglCreateContext(dpy, config, EGL14.EGL_NO_CONTEXT, ctxAttrib, 0);
            EGL14.eglMakeCurrent(dpy, surf, surf, ctx);
            int[] maxSize = new int[1];
            GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, maxSize, 0);
            return maxSize[0];
        } else {
            return 4096;
        }
    }

//    /**
//     * 拍摄照片
//     *
//     * @param name 照片名称
//     */
//    public static void pickByCamera(Activity context, String name) {
//        // 调用手机摄像头
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//            File cameraImageFile = new File(FileUtils.getForumCameraImageFileWithName(name));
//            if (cameraImageFile.exists()) {
//                cameraImageFile.delete();
//            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraImageFile));                // 拍照保存大图，否则不清晰
//            try {
//                context.startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMERA);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
////            new GSToastBottom(context, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
//            ToastUtils.showShort(context,"请确认已经插入SD卡");
//        }
//    }

//    /**
//     * 获取Glide图片缓存大小
//     * @param context
//     * @return
//     */
//    public static long getGlideDiskCacheSize(Context context) {
//        return FileUtils.getFolderSizeMB(Glide.getPhotoCacheDir(context));
//    }
//
//    public static void clearGlideDiskCache(final Context context, final Action1<String> func) {
//        Observable<String> observable = Observable.just(context).map(new Func1<Context, String>() {
//            @Override
//            public String call(Context context) {
//                Glide.get(context).clearDiskCache();
//                return "";
//            }
//        });
//        observable.subscribeOn(RxUtils.workScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(func, RxUtils.emptyThrowable(ImageUtils.class));
//    }
}
