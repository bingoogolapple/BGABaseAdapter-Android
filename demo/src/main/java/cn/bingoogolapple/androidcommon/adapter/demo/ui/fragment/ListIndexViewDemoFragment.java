package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.ListIndexAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.model.IndexModel;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.widget.IndexView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class ListIndexViewDemoFragment extends BaseFragment implements BGAOnItemChildClickListener {
    private static final String TAG = ListIndexViewDemoFragment.class.getSimpleName();
    private List<IndexModel> mData;
    private ListView mDataLv;
    private IndexView mIndexView;
    private TextView mTipTv;
    private TextView mTopcTv;

    private ListIndexAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_listindexview);
        mDataLv = getViewById(R.id.lv_listindexview_data);
        mTopcTv = getViewById(R.id.tv_listindexview_topc);

        mIndexView = getViewById(R.id.indexview);
        mTipTv = getViewById(R.id.tv_listindexview_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new ListIndexAdapter(mActivity);
        mAdapter.setOnItemChildClickListener(this);

        mIndexView.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForSection(text.charAt(0));
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
    protected void onUserVisible() {
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_indexview_name) {
            showSnackbar("选择了城市 " + mAdapter.getItem(position).name);
        }
    }
}