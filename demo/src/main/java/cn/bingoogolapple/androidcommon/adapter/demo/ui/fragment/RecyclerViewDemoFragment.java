package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.NormalRecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.NormalModel;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.widget.Divider;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/28 下午1:30
 * 描述:
 */
public class RecyclerViewDemoFragment extends BaseFragment implements BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {
    private static final String TAG = RecyclerViewDemoFragment.class.getSimpleName();
    private NormalRecyclerViewAdapter mAdapter;
    private RecyclerView mDataRv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerview);
        mDataRv = getViewById(R.id.rv_recyclerview_data);
    }

    @Override
    protected void setListener() {
        mAdapter = new NormalRecyclerViewAdapter(mActivity);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDataRv.addItemDecoration(new Divider(mActivity));
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void onUserVisible() {
        DataEngine.loadNormalModelDatas(mActivity, new DataEngine.NormalModelResponseHandler() {
            @Override
            public void onSuccess(List<NormalModel> normalModels) {
                mAdapter.setDatas(normalModels);
            }
        });
    }

    @Override
    public void onRVItemClick(View v, int position) {
        showSnackbar("点击了条目 " + mAdapter.getItem(position).title);
    }

    @Override
    public boolean onRVItemLongClick(View v, int position) {
        showSnackbar("长按了条目 " + mAdapter.getItem(position).title);
        return true;
    }

    @Override
    public void onItemChildClick(View v, int position) {
        if (v.getId() == R.id.tv_item_normal_delete) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(View v, int position) {
        if (v.getId() == R.id.tv_item_normal_delete) {
            showSnackbar("长按了删除 " + mAdapter.getItem(position).title);
            return true;
        }
        return false;
    }
}
