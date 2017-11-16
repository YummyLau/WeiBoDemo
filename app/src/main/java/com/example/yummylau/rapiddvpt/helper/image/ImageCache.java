package com.example.yummylau.rapiddvpt.helper.image;

import android.graphics.Bitmap;

/**
 * Created by g8165 on 2016/12/7.
 */

public interface ImageCache {
    Bitmap getBitmap(String url);
    void putBitmap(String url, Bitmap bitmap);
}
