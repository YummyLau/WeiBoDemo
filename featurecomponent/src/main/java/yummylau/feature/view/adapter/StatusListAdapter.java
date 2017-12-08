package yummylau.feature.view.adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

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
        Glide.with(mContext)
//                .load(item.user.avatarLarge)
                .load("https://img1.doubanio.com/img/musician/large/22817.jpg")
                .into((ImageView) helper.getView(R.id.avatar));
        helper.setText(R.id.nick, "xxxxx");
        helper.setText(R.id.create_time, item.created_at);
        helper.setText(R.id.content, item.text);
        helper.setText(R.id.device_info, item.text);
    }
}
