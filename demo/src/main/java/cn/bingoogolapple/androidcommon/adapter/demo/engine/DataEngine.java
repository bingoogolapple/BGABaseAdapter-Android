package cn.bingoogolapple.androidcommon.adapter.demo.engine;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.demo.mode.RefreshModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/26 上午1:03
 * 描述:
 */
public class DataEngine {

    public static List<RefreshModel> loadInitDatas() {
        List<RefreshModel> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new RefreshModel("title" + i, "detail" + i));
        }
        return datas;
    }

}