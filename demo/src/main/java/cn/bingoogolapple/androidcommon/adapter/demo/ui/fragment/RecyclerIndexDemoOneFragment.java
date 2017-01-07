package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.RecyclerIndexDemoOneAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.widget.IndexView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RecyclerIndexDemoOneFragment extends BaseFragment implements BGAOnItemChildClickListener {
    private RecyclerIndexDemoOneAdapter mAdapter;
    private RecyclerView mDataRv;
    private LinearLayoutManager mLayoutManager;
    private IndexView mIndexView;
    private TextView mTipTv;
    private TextView mTopcTv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerindexview);
        mDataRv = getViewById(R.id.rv_recyclerindexview_data);
        mTopcTv = getViewById(R.id.tv_recyclerindexview_topc);

        mIndexView = getViewById(R.id.indexview);
        mTipTv = getViewById(R.id.tv_recyclerindexview_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new RecyclerIndexDemoOneAdapter(mDataRv);
        mAdapter.setOnItemChildClickListener(this);

        mIndexView.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForSection(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    mLayoutManager.scrollToPosition(position);
                }
            }
        });
        mDataRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mAdapter.getItemCount() > 0) {
                    mTopcTv.setText(mAdapter.getItem(mLayoutManager.findFirstVisibleItemPosition()).topc);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mIndexView.setTipTv(mTipTv);

        mDataRv.addItemDecoration(BGADivider.newShapeDivider()
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level9)
                .setDelegate(new BGADivider.SimpleDelegate() {
                    @Override
                    public boolean isNeedSkip(int position, int itemCount) {
                        // 如果分类的话就跳过，顶部不绘制
                        return mAdapter.isCategory(position);
                    }
                }));

        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);


        mAdapter.setData(DataEngine.loadIndexModelData());
        mDataRv.setAdapter(mAdapter);

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