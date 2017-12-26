package yummylau.common.net.exception;

import java.io.IOException;

import yummylau.common.net.HttpCode;

/**
 * 微博接口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class WeiboApiException extends IOException {

    public HttpCode mHttpCode;


    public WeiboApiException(HttpCode httpCode) {
        mHttpCode = httpCode;
    }

    @Override
    public String getMessage() {
        if (mHttpCode != null) {
            return mHttpCode.msg;
        }
        return super.getMessage();
    }
}
