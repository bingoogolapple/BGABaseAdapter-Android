package cn.bingoogolapple.androidcommon.adapter.demo.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/11/27 下午6:21
 * 描述:
 */
public class ImageViewBindingAdapter {

    private static String getPath(String path) {
        if (path == null) {
            path = "";
        }

        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }
        return path;
    }

    @BindingAdapter({"path", "placeholder"})
    public static void displayImage(ImageView imageView, String path, Drawable placeholder) {
        Glide.with(imageView.getContext()).load(getPath(path)).dontAnimate().placeholder(placeholder).into(imageView);
    }

}