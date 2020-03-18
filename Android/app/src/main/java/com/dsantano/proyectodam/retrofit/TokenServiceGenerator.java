package com.dsantano.proyectodam.retrofit;

import android.content.Context;

import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.common.MyApp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenServiceGenerator {

    public static final String API_BASE_URL = Constants.NODE_API_URL;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        if(!httpClient.interceptors().contains(logging)){
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    String token = MyApp.getContext().getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_AUTH_TOKEN, null);
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer " + token);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            httpClientBuilder.addInterceptor(logging);
            builder.client(httpClientBuilder.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

}
