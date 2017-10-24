package cn.bingoogolapple.baseadapter.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.adapter.ListIndexAdapter;
import cn.bingoogolapple.baseadapter.demo.engine.DataEngine;
import cn.bingoogolapple.baseadapter.demo.model.IndexModel;
import cn.bingoogolapple.baseadapter.demo.ui.widget.IndexView;
import cn.bingoogolapple.baseadapter.demo.util.ToastUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class LvSuspensionFragment extends MvcFragment implements BGAOnItemChildClickListener {
    private List<IndexModel> mData;
    private ListView mDataLv;
    private IndexView mIndexView;
    private TextView mTipTv;
    private TextView mTopcTv;

    private ListIndexAdapter mAdapter;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_listindexview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDataLv = getViewById(R.id.lv_listindexview_data);
        mTopcTv = getViewById(R.id.tv_listindexview_category);

        mIndexView = getViewById(R.id.indexview);
        mTipTv = getViewById(R.id.tv_listindexview_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new ListIndexAdapter(mActivity);
        mAdapter.setOnItemChildClickListener(this);

        mIndexView.setDelegate(new IndexView.Delegate() {
            @Override
            public void onIndexViewSelectedChanged(IndexView indexView, String text) {
                int position = mAdapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到ListView的第一个可见条目
                    mDataLv.setSelection(position);
                }
            }
        });

        mDataLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mAdapter.getCount() > 0) {
                    mTopcTv.setText(mAdapter.getItem(firstVisibleItem).topc);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mIndexView.setTipTv(mTipTv);

        mData = DataEngine.loadIndexModelData();
        mAdapter.setData(mData);
        mDataLv.setAdapter(mAdapter);

        mTopcTv.setText(mAdapter.getItem(0).topc);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_index_catalog) {
            ToastUtil.show("点击了分类 " + mAdapter.getItem(position).topc);
        } else if (childView.getId() == R.id.tv_item_index_city) {
            ToastUtil.show("选择了城市 " + mAdapter.getItem(position).name);
        }
    }
}