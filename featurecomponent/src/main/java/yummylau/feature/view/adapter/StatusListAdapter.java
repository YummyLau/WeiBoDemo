package yummylau.feature.view.adapter;


import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import javax.inject.Inject;

import yummylau.common.util.imageloader.ImageLoader;
import yummylau.feature.bean.Pic;
import yummylau.feature.data.DataHelper;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.R;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * 微博adapter
 * Created by g8931 on 2017/12/5.
 */

public class StatusListAdapter extends BaseQuickAdapter<StatusEntity, BaseViewHolder> {

    private NineGridImageView<Pic> mStringNineGridImageView;

    public StatusListAdapter(int layoutResId, @Nullable List<StatusEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StatusEntity item) {
        UserEntity userEntity = item.user;
        ImageLoader.getInstance().load(mContext, userEntity.avatarLarge, (ImageView) helper.getView(R.id.avatar));
        helper.setText(R.id.nick, userEntity.name);
        //创建时间
        helper.setText(R.id.create_time, DataHelper.transformTime(item.created_at));
        //来源
        Elements aTag = Jsoup.parse(item.source).select("a");
        if (aTag != null) {
            if (aTag.first() == null) {
                helper.getView(R.id.source).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.source).setVisibility(View.VISIBLE);
                helper.setText(R.id.source, aTag.text());
            }
        }

        mStringNineGridImageView = helper.getView(R.id.image_layout);
        //图片列表
        if (item.pics != null && !item.pics.isEmpty()) {
            mStringNineGridImageView.setVisibility(View.VISIBLE);
            mStringNineGridImageView.setAdapter(new NineGridAdapter());
            mStringNineGridImageView.setImagesData(item.pics);
        } else {
            mStringNineGridImageView.setVisibility(View.GONE);
        }
        helper.setText(R.id.content, item.text);
        helper.setText(R.id.relay_count, String.valueOf(item.repostsCount));
        helper.setText(R.id.comment_count, String.valueOf(item.commentsCount));
        helper.setText(R.id.like_count, String.valueOf(item.attitudesCount));
    }

    private class NineGridAdapter extends NineGridImageViewAdapter<Pic> {

        @Override
        protected void onDisplayImage(Context context, ImageView imageView, Pic pic) {
            ImageLoader.getInstance().load(context, pic.getLargePic(), imageView);
        }
    }
}
