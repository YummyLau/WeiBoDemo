package yummylau.common.view.imageviewer;

import android.content.Context;
import android.os.Build;

import com.github.chrisbanes.photoview.PhotoView;

import yummylau.common.util.imageloader.ImageLoader;

/**
 * 构建pagerview查看项
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2018/01/02.
 */

class PhotoViewCreator {

    public static PhotoView createPhotoView(Context context, String url) {
        PhotoView photoView = new PhotoView(context);
        ImageLoader.getInstance().load(context, url, photoView);
        return photoView;
    }
}
