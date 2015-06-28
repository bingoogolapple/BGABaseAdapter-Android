package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.ListChatAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.ChatModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class ListChatDemoFragment extends BaseFragment {
    private static final String TAG = ListChatDemoFragment.class.getSimpleName();
    private List<ChatModel> mDatas;
    private ListView mDataLv;
    private ListChatAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_listview);
        mDataLv = getViewById(R.id.lv_listview_data);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDataLv.setSelector(android.R.color.transparent);
        mDataLv.setDivider(null);
        mAdapter = new ListChatAdapter(mActivity);
        mDatas = DataEngine.loadChatModelDatas();
        mAdapter.setDatas(mDatas);
        mDataLv.setAdapter(mAdapter);
    }

    @Override
    protected void onUserVisible() {
    }
}