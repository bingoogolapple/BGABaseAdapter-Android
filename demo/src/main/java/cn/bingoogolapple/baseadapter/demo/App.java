package cn.bingoogolapple.baseadapter.demo;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/21 下午10:13
 * 描述:
 */
public class App extends Application {
    private static App sInstance;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://bgashare.bingoogolapple.cn/adapter/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static App getInstance() {
        return sInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}