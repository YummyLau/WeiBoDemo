package yummylau.networkmodule;

import android.util.ArrayMap;

import retrofit2.Converter;

/**
 * Created by g8931 on 2017/11/14.
 */

public class RetrofitParam {

    public String BaseUrl;
    public ArrayMap<String, String> propertyMap;
    public boolean supportCache;
    public Converter.Factory converterFactory;

    private RetrofitParam(String baseUrl, ArrayMap<String, String> propertyMap, boolean supportCache, Converter.Factory converterFactory) {
        BaseUrl = baseUrl;
        this.propertyMap = propertyMap;
        this.supportCache = supportCache;
        this.converterFactory = converterFactory;
    }

    private RetrofitParam(String baseUrl, ArrayMap<String, String> propertyMap, Converter.Factory converterFactory) {
        BaseUrl = baseUrl;
        this.propertyMap = propertyMap;
        this.supportCache = false;
        this.converterFactory = converterFactory;
    }

    public static class Builder {

        private String mBaseUrl;
        private boolean mSupportCache;
        private ArrayMap<String, String> mPropertyMap;
        private Converter.Factory mConverterFactory;

        public Builder baseUrl(String baseUrl) {
            this.mBaseUrl = baseUrl;
            return this;
        }

        public Builder supportCache(boolean support) {
            this.mSupportCache = support;
            return this;
        }

        public Builder converterFactory(Converter.Factory converterFactory) {
            this.mConverterFactory = converterFactory;
            return this;
        }
    }
}
