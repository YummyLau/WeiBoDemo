package com.netease.hearthstone.biz.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.netease.hearthstone.R;
import com.netease.hearthstone.biz.ImageNativeLoader;
import com.netease.hearthstone.biz.LogPrinter;
import com.netease.hearthstone.third.imageviewer.PhotoViewAttacher;
import com.netease.hearthstone.utils.ImageUtils;
import com.netease.hearthstone.utils.ToastUtils;


public class ImageDetailFragment extends Fragment {
    private final static int TYPE_NONE = 0;
    private final static int TYPE_URL = 1;
    private final static int TYPE_PATH_SELECT = 2;
    private final static int TYPE_PATH_VIEW = 3;                                                     // 查看一张本地图片

    private String mImageUrl;
    private String mImagePath;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;
    private int mType = TYPE_NONE;

    private static final String TAG = ImageDetailFragment.class.getSimpleName();

    public static ImageDetailFragment newInstanceForUrl(String imageUrl) {
        LogPrinter.i(TAG, "newInstanceForUrl");
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        args.putInt("type", TYPE_URL);
        f.setArguments(args);
        return f;
    }

    public static ImageDetailFragment newInstanceForPath(String imagePath, boolean clickToFinish) {
        LogPrinter.i(TAG, "newInstanceForPath");
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putString("path", imagePath);
        args.putInt("type", clickToFinish ? TYPE_PATH_VIEW : TYPE_PATH_SELECT);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogPrinter.i(TAG, "onCreate");
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        mImagePath = getArguments() != null ? getArguments().getString("path") : null;
        mType = getArguments() != null ? getArguments().getInt("type") : TYPE_NONE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogPrinter.i(TAG, "onCreateView");
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                if (mType == TYPE_PATH_SELECT) {
                    return;
                }
                try {                                                                               // getActivity()可能为null
                    getActivity().finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mImageUrl != null) {
            loadImage(mImageUrl);
        }

        if (mImagePath != null) {
            ImageNativeLoader.getInstance(3, ImageNativeLoader.Type.LIFO).loadPageImage(mImagePath, mImageView, new ImageNativeLoader.OnLoadNativeImageListener() {
                @Override
                public void onLoadComplete() {
                    mAttacher.update();
                }
            });
        }
    }

    public void loadImage(String url) {
        mImageUrl = url;
        LogPrinter.i("loadImage",url);
        Glide.with(getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new GlideDrawableImageViewTarget(mImageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                progressBar.setVisibility(View.GONE);
                mAttacher.update();
                resource.getMinimumHeight();
                //图片过大关闭硬件加速 且不做缓存
                if (resource.getIntrinsicHeight() > ImageUtils.getOpenGLBitmapMaxSize()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    }
                    return;
                }

                //用于判断是否加载过大图
                Drawable drawable = resource.getCurrent();
                Bitmap bitmap = null;
                if (drawable instanceof GlideBitmapDrawable) {
                    bitmap = ((GlideBitmapDrawable) drawable).getBitmap();
                }
                if (bitmap != null && getContext() != null && getContext().getApplicationContext() != null) {
                    ImageLruCache imageCache = ImageLruCache.getInstance(getContext().getApplicationContext());
                    imageCache.putBitmap(mImageUrl, bitmap);
                }
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                if (getActivity() != null) {
                    ToastUtils.showShort(getActivity(), getActivity().getString(R.string.load_image_fail));
                }
                try {
                    getView().setImageResource(R.drawable.default_img_400x400);
                } catch (OutOfMemoryError oom) {
                    oom.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                mAttacher.update();
            }
        });

//        ForumHelper.setImageWithUrl(getActivity(), mImageView, mImageUrl, new ForumHelper.OnImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(ImageView imageView, String url) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onLoadingFailed(ImageView imageView, String url, String failReason) {
//                ToastUtils.showShort(getActivity(), failReason);
//                try {
//                    mImageView.setImageResource(R.drawable.default_forum_image);
//                } catch (OutOfMemoryError e) {
//                    e.printStackTrace();
//                }
//                progressBar.setVisibility(View.GONE);
//                mAttacher.update();
//            }
//
//            @Override
//            public void onLoadingComplete(ImageView imageView, String url, Bitmap loadedImage) {
//                progressBar.setVisibility(View.GONE);
//                mAttacher.update();
//            }
//        });
    }

    /**
     * 释放Image图片资源 todo 注意回收后还要用造成崩溃的情况
     *
     * @param imageView
     */
    private void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                LogPrinter.i(TAG, "recycle");
                mImageView.setImageBitmap(null);
                bitmap.recycle();
            }
        }

    }


    @Override
    public void onDestroy() {
        LogPrinter.i(TAG, "onDestroy");
        // releaseImageViewResouce(mImageView);
        super.onDestroy();
        ImageNativeLoader.imagePageRecycle();
    }

    @Override
    public void onDetach() {
        LogPrinter.i(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogPrinter.i(TAG, "onHiddenChanged");
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onStop() {
        LogPrinter.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onAttach(Activity activity) {
        LogPrinter.i(TAG, "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onLowMemory() {
        LogPrinter.i(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        LogPrinter.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        LogPrinter.i(TAG, "onDestroyView");
        super.onDestroyView();
    }
}
