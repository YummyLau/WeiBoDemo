package yummylau.feature.view.adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import yummylau.common.activity.BaseActivity;
import yummylau.common.util.imageloader.ImageLoader;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.R;

/**
 * 微博adapter
 * Created by g8931 on 2017/12/5.
 */

public class StatusListAdapter extends BaseQuickAdapter<StatusEntity, BaseViewHolder> {

    public StatusListAdapter(int layoutResId, @Nullable List<StatusEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StatusEntity item) {
        ImageLoader.getInstance().load(mContext, "https://img1.doubanio.com/img/musician/large/22817.jpg", (ImageView) helper.getView(R.id.avatar));
        helper.setText(R.id.nick, "周杰伦");
        helper.setText(R.id.create_time, item.created_at);
        helper.setText(R.id.content, item.text);
        helper.setText(R.id.device_info, item.text);
        helper.setText(R.id.relay_count, String.valueOf(item.repostsCount));
        helper.setText(R.id.comment_count, String.valueOf(item.commentsCount));
        helper.setText(R.id.like_count, String.valueOf(item.attitudesCount));
    }
}
