package yummylau.feature.data.remote;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yummylau.common.net.HttpParam;
import yummylau.feature.data.remote.api.WeiboApis;

/**
 * Created by g8931 on 2017/12/7.
 */

public class HttpParamCreator {

    public static HttpParam create() {
        return new HttpParam.Builder()
                .baseUrl(WeiboApis.BASE_URL)
                .callAdatperFactory(RxJava2CallAdapterFactory.create())
                .converterFactory(GsonConverterFactory.create())
                .build();
    }
}
