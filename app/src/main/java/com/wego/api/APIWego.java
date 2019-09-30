package com.wego.api;

import com.wego.util.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIWego {

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {

        /*if (Config.LOG_ENABLE) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder().addInterceptor(logging)
                    .readTimeout(Config.NETWORK_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(Config.NETWORK_TIME_OUT, TimeUnit.SECONDS)
                    .build();
        } else {

            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(Config.NETWORK_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(Config.NETWORK_TIME_OUT, TimeUnit.SECONDS)
                    .build();
        }*/

        if (retrofit==null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(Config.NETWORK_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(Config.NETWORK_TIME_OUT, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }
}
