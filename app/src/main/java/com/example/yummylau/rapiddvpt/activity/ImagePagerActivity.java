package com.example.yummylau.rapiddvpt.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yummylau.rapiddvpt.R;
import com.example.yummylau.rapiddvpt.databinding.ActivityImagePagerLayoutBinding;
import com.example.yummylau.rapiddvpt.helper.ImageSelectHelper;
import com.example.yummylau.rapiddvpt.helper.image.ImageDetailFragment;
import com.example.yummylau.rapiddvpt.util.ImageUtils;
import com.example.yummylau.rapiddvpt.util.common.DisplayUtils;
import com.example.yummylau.rapiddvpt.util.common.FileUtils;
import com.example.yummylau.rapiddvpt.util.common.SdcardUtils;
import com.example.yummylau.rapiddvpt.util.common.URLUtils;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yummyLau on 17-6-10
 * Email: yummyl.lau@gmail.com
 * 用于查看图片的activity
 */

public class ImagePagerActivity extends BaseActivity implements View.OnClickListener{


    private ActivityImagePagerLayoutBinding binding;

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IMAGE_PATHS_SELECT = "image_paths_select";
    public static final String EXTRA_IMAGE_PATHS_VIEW = "image_paths_view";
    public static final String EXTRA_MAX_IMAGE_COUNT = "max_image_count";

    //增加传url和path两种类型，分别用于网络图片和本地图片
    public static final int TYPE_URL = 1;
    public static final int TYPE_PATH_SELECT = 2;
    public static final int TYPE_PATH_VIEW = 3;
    private int mType;

    private int pagerPosition;
    private int mCurPosition;
    private ArrayList<String> mSelectPathList;                                                      // 本地图片地址列表
    private ArrayList<String> mViewPathList;
    private int mMaxSelectImageCount = ImageSelectHelper.DEFAULT_MAX_COUNT;                         // 最大可选图片个数
    //    private LinearLayout mOriginImageLayout;
    private ArrayList<String> mImageUrlList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_pager_layout);
        overridePendingTransition(R.anim.imageviewer_fade_in_anim, R.anim.no_anim);

        //读取传递过来的信息
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urlList = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        mSelectPathList = getIntent().getStringArrayListExtra(EXTRA_IMAGE_PATHS_SELECT);
        mMaxSelectImageCount = getIntent().getIntExtra(EXTRA_MAX_IMAGE_COUNT, ImageSelectHelper.DEFAULT_MAX_COUNT);
        mViewPathList = getIntent().getStringArrayListExtra(EXTRA_IMAGE_PATHS_VIEW);

        if (urlList != null) {                                                                      // 查看多张网络图片
            mType = TYPE_URL;
            // 非法图片地址过滤
            mImageUrlList = new ArrayList<>();
            for (String url : urlList) {
                if (URLUtils.verifyURL(url)) mImageUrlList.add(url);
            }
            final ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mImageUrlList, TYPE_URL);
            binding.pager.setAdapter(mAdapter);
            binding.download.setOnClickListener(
                    new ImageButton.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            if (SdcardUtils.isSDCardExist()) {
                                String url = mAdapter.getUrl(binding.pager.getCurrentItem());
                                new SaveImageTask(ImagePagerActivity.this, url).execute();
                            } else {
                                Toast.makeText(ImagePagerActivity.this, "没有检测到存储卡，请确认已经插入存储卡",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

            );
            binding.titleBar.setVisibility(View.GONE);
        } else if (mSelectPathList != null) {                                                             // 查看多张本地图片
            mType = TYPE_PATH_SELECT;
            final ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mSelectPathList, TYPE_PATH_SELECT);
            binding.pager.setAdapter(mAdapter);
            binding.layoutBottom.setVisibility(View.GONE);
            //标题栏 返回， 索引， 完成
            binding.titleBar.setTitle("返回");
            binding.topIndicator.setVisibility(View.VISIBLE);

            binding.tvTitlebarRight.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.tvTitlebarRight.getLayoutParams();
            layoutParams.height = DisplayUtils.dip2px(this,30);
            layoutParams.width = DisplayUtils.dip2px(this,80);
            layoutParams.rightMargin = DisplayUtils.dip2px(this,10);
            binding.tvTitlebarRight.setLayoutParams(layoutParams);
            binding.tvTitlebarRight.setPadding(0, 0, 0, 0);
//            mDoneButton.setBackgroundResource(R.drawable.forum_publish_button_selector);
            binding.tvTitlebarRight.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            binding.tvTitlebarRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            binding.tvTitlebarRight.setGravity(Gravity.CENTER);
            setDoneCount();

            //底部选择框
            binding.layoutOptionImage.setVisibility(View.VISIBLE);
            binding.layoutOriginalImage.setOnClickListener(this);
            binding.layoutChooseImage.setOnClickListener(this);
            binding.tvTitlebarRight.setOnClickListener(this);
            if (ImageSelectHelper.contains(mSelectPathList.get(pagerPosition))) {
                binding.cbChooseImage.setChecked(true);
            } else {
                binding.cbChooseImage.setChecked(false);
            }
            binding.cbOriginalImage.setChecked(ImageSelectHelper.sOriginalImage);
            mCurPosition = pagerPosition;
            setImageSizeTv();

            binding.cbChooseImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        if (ImageSelectHelper.contains(mSelectPathList.get(mCurPosition))) {                                         // 已经选择过该图片,取消选择
                            ImageSelectHelper.removeReadySelected(mSelectPathList.get(mCurPosition));
                        }
                    } else {
                        if (ImageSelectHelper.contains(mSelectPathList.get(mCurPosition)))
                            return;
                        int remainCount = mMaxSelectImageCount - ImageSelectHelper.getSelectedCount();
                        if (ImageSelectHelper.getReadySelectedCount() >= remainCount) {
                            binding.cbChooseImage.setChecked(false);
                            Toast.makeText(ImagePagerActivity.this, "最多只能选取" + remainCount + "张",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ImageSelectHelper.addReadySelected(mSelectPathList.get(mCurPosition));
                    }
                    setDoneCount();
                }
            });

            binding.cbOriginalImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ImageSelectHelper.sOriginalImage = !ImageSelectHelper.sOriginalImage;
                    setImageSizeTv();
                }
            });
        } else if (mViewPathList != null) {                                                                 // 查看一张本地图片
            mType = TYPE_PATH_VIEW;
            ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mViewPathList, TYPE_PATH_VIEW);
            binding.pager.setAdapter(mAdapter);
            binding.tvTitlebarRight.setVisibility(View.GONE);
            binding.download.setVisibility(View.GONE);
        }


        int count = binding.pager.getAdapter().getCount();
        CharSequence text = getString(R.string.viewpager_indicator, count > 0 ? 1 : 0, count);
        binding.bottomIndicator.setText(text);
        binding.topIndicator.setText(text);
        // 更新下标
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(final int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, binding.pager.getAdapter().getCount());
                binding.bottomIndicator.setText(text);
                binding.topIndicator.setText(text);
                if (mSelectPathList != null) {
                    String path = mSelectPathList.get(arg0);
                    mCurPosition = arg0;
                    if (ImageSelectHelper.contains(path)) {
                        binding.cbChooseImage.setChecked(true);
                    } else {
                        binding.cbChooseImage.setChecked(false);
                    }
                    binding.cbOriginalImage.setChecked(ImageSelectHelper.sOriginalImage);
                    setImageSizeTv();
                }

//                setOriginTextView(arg0);
            }

        });

        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        binding.pager.setCurrentItem(pagerPosition);
//        setOriginTextView(pagerPosition);                                                           // 当setCurrentItem为0,不触发onPageSelected,需手动设置
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_original_image:
                binding.cbOriginalImage.setChecked(!binding.cbOriginalImage.isChecked());
                break;
            case R.id.layout_choose_image:
                binding.cbChooseImage.setChecked(!binding.cbChooseImage.isChecked());
                break;
            case R.id.tv_titlebar_right:
                setResult(Activity.RESULT_OK);
                ImageSelectHelper.addSelectedList();
                ImageSelectHelper.clearReadySelectedList();
                finish();
                break;
            case R.id.ll_titlebar_back_btn:
            case R.id.tv_titlebar_title:
                onBackPressed();
                break;
            default:
                break;
        }
    }

//    private void setOriginTextView(final int index) {
//        //2016.10.11 更新: 当缓存中已有高清的原图时,直接显示原图   不显示"查看原图按钮"
//
//        if (mImageUrlList != null && index < mImageUrlList.size() && index >= 0) {
//            //LogPrinter.i("setOriginTextView", mImageUrlList.get(index));
//            if (ImageUtils.ifOriginImageUrl(mImageUrlList.get(index))) {
//                mOriginImageLayout.setVisibility(View.GONE);
//            } else {
//                String url = mImageUrlList.get(index);
//                final String originalUrl = ImageUtils.ifNosImageUrl(url) ? ImageUtils.convertBackNosUrl(url) : ImageUtils.convertBackAdjustSizeUrl(getApplicationContext(), url);
//                //如果原图在缓存中，直接显示原图
//                if (ImageUtils.isUrlInImageCache(this, originalUrl)) {
//                    mImageUrlList.set(index, originalUrl);
//                    ImagePagerAdapter adapter = (ImagePagerAdapter) mPager.getAdapter();
//                    adapter.notifyDataSetChanged();
//                    mOriginImageLayout.setVisibility(View.GONE);
//                } else {
//                    mOriginImageLayout.setVisibility(View.VISIBLE);
//                    mOriginImageLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            mImageUrlList.set(index, originalUrl);
//                            ImagePagerAdapter adapter = (ImagePagerAdapter) mPager.getAdapter();
//                            adapter.notifyDataSetChanged();
//                            mOriginImageLayout.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            }
//        }
//    }

    private void setDoneCount() {
        int count = ImageSelectHelper.getReadySelectedCount();
        if (count == 0) {
            binding.tvTitlebarRight.setText("完成");
        } else {
            StringBuilder sb = new StringBuilder();
            int remainSelectCount = mMaxSelectImageCount - ImageSelectHelper.getSelectedCount();
            sb.append("完成(").append(count).append("/").append(remainSelectCount).append(")");
//            sb.append("完成(").append(SelectImageHelper.getSelectedCount()).append("/").append(mMaxSelectImageCount).append(")");
            binding.tvTitlebarRight.setText(sb.toString());
        }
    }

    private void setImageSizeTv() {
        if (ImageSelectHelper.sOriginalImage) {
            binding.tvOriginalSize.setText(new StringBuilder().append("原图").append("(")
                    .append(FileUtils.getFileSize(mSelectPathList.get(mCurPosition)))
                    .append(")").toString());
        } else {
            binding.tvOriginalSize.setText("原图");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, binding.pager.getCurrentItem());
    }

    @Override
    protected void onPause() {
        this.overridePendingTransition(R.anim.no_anim, R.anim.imageviewer_fade_out_anim);
        super.onPause();
    }

    /**
     * PageView的适配器，回收没显示的Fragment
     */
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;
        private int imageType;
        private ImageDetailFragment mCurrentFragment;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList, int type) {
            super(fm);
            this.fileList = fileList;
            this.imageType = type;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            if (imageType == TYPE_PATH_SELECT) {
                String path = fileList.get(position);
                return ImageDetailFragment.newInstanceForPath(path, false);
            } else if (imageType == TYPE_PATH_VIEW) {
                String path = fileList.get(position);
                return ImageDetailFragment.newInstanceForPath(path, true);
            } else {
                String url = fileList.get(position);
                return ImageDetailFragment.newInstanceForUrl(url);
            }
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentFragment = (ImageDetailFragment) object;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;                                                                   // notifyDataSetChanged可以刷新当前页面
        }

        public String getUrl(int positon) {
            return fileList == null || positon < 0 || positon >= fileList.size() ? null : fileList.get(positon);
        }

        public ImageDetailFragment getCurrentFragment() {
            return mCurrentFragment;
        }
    }

    /**
     * 保存图片异步任务
     */
    private class SaveImageTask extends AsyncTask<Void, Void, Bitmap> {

        private WeakReference<Context> mContextReference;
        private String mUrl;

        public SaveImageTask(Context context, String url) {
            mContextReference = new WeakReference<>(context);
            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            if (mContextReference.get() != null) {
                // TODO: 17-6-11
//                return ImageUtils.getNetworkImageWithCache(mContextReference.get(), mUrl);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            saveBitmap(bitmap);
        }
    }

    /**
     * 保存图片到手机
     *
     * @param bitmap 图片
     */
    private void saveBitmap(Bitmap bitmap) {

        if (bitmap == null) {
            Toast.makeText(this, "保存图片失败", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);//中国时间
            String name = dateFormat.format(new Date());
            String uriString = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, name, "");
          Toast.makeText(this, "成功保存图片到手机", Toast.LENGTH_SHORT).show();

            //广播通知相册刷新
            if (uriString != null) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.parse(uriString);
                intent.setData(uri);
                sendBroadcast(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "保存图片失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
