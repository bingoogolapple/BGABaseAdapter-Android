package cn.bingoogolapple.androidcommon.adapter.demo.engine;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.demo.model.BannerModel;
import cn.bingoogolapple.androidcommon.adapter.demo.model.NormalModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/9/17 上午10:19
 * 描述:
 */
public interface ApiEngine {
    @GET("normalModels.json")
    Call<List<NormalModel>> getNormalModels();

    @GET
    Call<BannerModel> loadBannerData(@Url String url);
}