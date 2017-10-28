package cn.bingoogolapple.baseadapter.demo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.adapter.RvChatAdapter;
import cn.bingoogolapple.baseadapter.demo.engine.DataEngine;
import cn.bingoogolapple.baseadapter.demo.model.ChatModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:RecyclerView 多 item 类型
 */
public class RvChatFragment extends MvcFragment implements BGAOnItemChildClickListener {
    private RvChatAdapter mAdapter;
    private RecyclerView mDataRv;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_rv;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDataRv = getViewById(R.id.rv_data);
    }

    @Override
    protected void setListener() {
        mAdapter = new RvChatAdapter(mDataRv);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mAdapter.setData(DataEngine.loadChatModelData());
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        mAdapter.getItem(position).mSendStatus = ChatModel.SendStatus.Success;

        mAdapter.moveItem(position, mAdapter.getItemCount() - 1);
    }
}