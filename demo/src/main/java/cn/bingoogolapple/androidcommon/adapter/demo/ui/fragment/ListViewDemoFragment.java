package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.NormalAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.NormalModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/28 下午12:34
 * 描述:
 */
public class ListViewDemoFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {
    private static final String TAG = ListViewDemoFragment.class.getSimpleName();
    private ListView mDataLv;
    private NormalAdapterViewAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_listview);
        mDataLv = getViewById(R.id.lv_listview_data);
    }

    @Override
    protected void setListener() {
        mDataLv.setOnItemClickListener(this);
        mDataLv.setOnItemLongClickListener(this);

        mAdapter = new NormalAdapterViewAdapter(mDataLv);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDataLv.setAdapter(mAdapter);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showSnackbar("点击了条目 " + mAdapter.getItem(position).title);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showSnackbar("长按了" + mAdapter.getItem(position).title);
        return true;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_normal_delete) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_normal_delete) {
            showSnackbar("长按了删除 " + mAdapter.getItem(position).title);
            return true;
        }
        return false;
    }
}