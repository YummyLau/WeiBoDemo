package com.example.yummylau.rapiddvpt.NET;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by n3207 on 2016/12/9.
 */
public class RetrofitParam {

    public static final String DEFAULT_BASE_URL = "http://xm.gameyw.netease.com:8080/xiaomei/";

    public String baseUrl;
    public boolean cache;
    public Converter.Factory converterFactory;

    public String getKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(cache).append(baseUrl).append(converterFactory.getClass().getName());
        return sb.toString();
    }

    public RetrofitParam(String baseUrl, boolean cache, Converter.Factory converterFactory) {
        this.baseUrl = baseUrl;
        this.cache = cache;
        this.converterFactory = converterFactory;
    }

    public static class Builder {

        String baseUrl = DEFAULT_BASE_URL;
        boolean cache = false;
        Converter.Factory converterFactory = GsonConverterFactory.create();

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder cache(boolean cache) {
            this.cache = cache;
            return this;
        }

        public Builder converterFactory(Converter.Factory converterFactory) {
            this.converterFactory = converterFactory;
            return this;
        }

        public RetrofitParam build() {
            return new RetrofitParam(this.baseUrl, this.cache, this.converterFactory);
        }
    }
}
