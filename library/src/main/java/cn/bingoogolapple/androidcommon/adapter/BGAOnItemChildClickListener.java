package cn.bingoogolapple.androidcommon.adapter;

import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/28 上午7:28
 * 描述:AdapterView和RecyclerView的item中子控件点击事件监听器
 */
public interface BGAOnItemChildClickListener {
    void onItemChildClick(View v, int position);
}