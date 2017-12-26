package yummylau.common.net.interceptor;

import android.nfc.Tag;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import yummylau.common.Constants;
import yummylau.common.R;
import yummylau.common.net.CodeGetter;
import yummylau.common.net.HttpCode;
import yummylau.common.net.exception.WeiboApiException;

/**
 * 实现微博后端接口的统一拦截
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class WeiboResultInterceptor implements Interceptor {

    private static final String TAG = WeiboResultInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        MediaType contentType = response.body().contentType();

        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        HttpCode httpCode = null;
        if (contentType.type().equals("application") && contentType.subtype().equals("json")) {
            String result = buffer.clone().readString(Constants.UTF8);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int errorCode = jsonObject.optInt("error_code");
                httpCode = CodeGetter.CODE_SPARSE_ARRAY.get(errorCode);
                //http://open.weibo.com/wiki/Error_code 该文档不全
                if (errorCode != 0 && httpCode == null) {
                    String errorMsg = jsonObject.optString("error");
                    httpCode = new HttpCode(errorCode, errorMsg);
                    CodeGetter.CODE_SPARSE_ARRAY.append(errorCode, httpCode);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (httpCode != null) {
            throw new WeiboApiException(httpCode);
        }
        return response;
    }
}
