package cn.bingoogolapple.androidcommon.adapter;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/11/13 下午3:56
 * 描述:
 */
public class BGAViewBindingAdapter {

    @BindingAdapter({"onNoDoubleClick"})
    public static void onNoDoubleClick(View view, final View.OnClickListener onClickListener) {
        view.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                onClickListener.onClick(v);
            }
        });
    }
}
