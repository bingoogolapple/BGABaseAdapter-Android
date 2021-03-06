package cn.bingoogolapple.baseadapter.demo.util;

import androidx.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/9/2 下午5:17
 * 描述:
 */
public class ToastUtil {

    private ToastUtil() {
    }

    public static void show(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            Toast toast;
            if (text.length() < 10) {
                toast = Toast.makeText(BGABaseAdapterUtil.getApp(), text, Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(BGABaseAdapterUtil.getApp(), text, Toast.LENGTH_LONG);
            }
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, BGABaseAdapterUtil.dp2px(2));
            toast.show();
        }
    }

    public static void show(@StringRes int resId) {
        show(BGABaseAdapterUtil.getApp().getResources().getString(resId));
    }
}