package cn.bingoogolapple.androidcommon.adapter.demo.engine;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.demo.mode.NormalModel;
import retrofit.Call;
import retrofit.http.GET;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/9/17 上午10:19
 * 描述:
 */
public interface ApiEngine {
    @GET("normalModels.json")
    Call<List<NormalModel>> getNormalModels();
}