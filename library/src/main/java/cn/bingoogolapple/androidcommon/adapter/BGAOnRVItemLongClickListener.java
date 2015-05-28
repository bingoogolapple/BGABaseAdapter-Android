package cn.bingoogolapple.androidcommon.adapter;

import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/28 上午7:28
 * 描述:RecyclerView的item长按事件监听器
 */
public interface BGAOnRVItemLongClickListener {
    boolean onRVItemLongClick(View v, int position);
}