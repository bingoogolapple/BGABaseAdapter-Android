package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewScrollHelper;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.RecyclerIndexDemoTwoAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.widget.IndexView;
import cn.bingoogolapple.androidcommon.adapter.demo.util.ToastUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:通过 BGADivider 加一个 SuspensionDelegate 来实现悬浮分类列表
 */
public class RvSuspensionDividerOneFragment extends MvcFragment implements BGAOnRVItemClickListener {
    private RecyclerIndexDemoTwoAdapter mAdapter;
    private RecyclerView mDataRv;
    private LinearLayoutManager mLayoutManager;
    private IndexView mIndexView;
    private TextView mTipTv;
    private BGARecyclerViewScrollHelper mRecyclerViewScrollHelper;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_recyclerindexview_divider;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDataRv = getViewById(R.id.rv_recyclerindexview_data);

        mIndexView = getViewById(R.id.indexview);
        mTipTv = getViewById(R.id.tv_recyclerindexview_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new RecyclerIndexDemoTwoAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);

        mIndexView.setDelegate(new IndexView.Delegate() {
            @Override
            public void onIndexViewSelectedChanged(IndexView indexView, String text) {
                int position = mAdapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
//                    mLayoutManager.scrollToPosition(position);

                    mRecyclerViewScrollHelper.smoothScrollToPosition(position);
                }
            }
        });
        mRecyclerViewScrollHelper = BGARecyclerViewScrollHelper.newInstance(mDataRv);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mIndexView.setTipTv(mTipTv);

        mDataRv.addItemDecoration(BGADivider.newBitmapDivider()
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level9)
                .setDelegate(new BGADivider.SuspensionCategoryDelegate() {
                    @Override
                    public void initCategoryAttr() {
//                        mCategoryBackgroundColor = getResources().getColor(R.color.category_backgroundColor);
//                        mCategoryTextColor = getResources().getColor(R.color.category_textColor);
//                        mCategoryTextSize = getResources().getDimensionPixelOffset(R.dimen.textSize_16);
//                        mCategoryPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.size_level4);
//                        mCategoryHeight = getResources().getDimensionPixelOffset(R.dimen.size_level10);
                    }

                    @Override
                    protected boolean isCategory(int position) {
                        return mAdapter.isCategory(position);
                    }

                    @Override
                    protected String getCategoryName(int position) {
                        return mAdapter.getItem(position).topc;
                    }

                    @Override
                    protected int getFirstVisibleItemPosition() {
                        return mLayoutManager.findFirstVisibleItemPosition();
                    }
                }));

        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);

        mAdapter.setData(DataEngine.loadIndexModelData());
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        ToastUtil.show("选择了城市 " + mAdapter.getItem(position).name);
    }
}