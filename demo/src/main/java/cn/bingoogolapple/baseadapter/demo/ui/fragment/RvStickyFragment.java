package cn.bingoogolapple.baseadapter.demo.ui.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bingoogolapple.baseadapter.BGADivider;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARVVerticalScrollHelper;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.adapter.RvStickyAdapter;
import cn.bingoogolapple.baseadapter.demo.engine.DataEngine;
import cn.bingoogolapple.baseadapter.demo.ui.widget.IndexView;
import cn.bingoogolapple.baseadapter.demo.util.ToastUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:BGADivider 用于简化 RecyclerView 分割线的编写，以及轻松实现基于 RecyclerView 的悬浮分类索引
 */
public class RvStickyFragment extends MvcFragment implements BGAOnRVItemClickListener {
    private RvStickyAdapter mAdapter;
    private RecyclerView mDataRv;
    private IndexView mIndexView;
    private TextView mTipTv;
    private BGARVVerticalScrollHelper mRecyclerViewScrollHelper;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_rv_sticky;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDataRv = getViewById(R.id.rv_sticky_data);

        mIndexView = getViewById(R.id.iv_sticky_index);
        mTipTv = getViewById(R.id.tv_sticky_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new RvStickyAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);

        initStickyDivider();

        mIndexView.setDelegate(new IndexView.Delegate() {
            @Override
            public void onIndexViewSelectedChanged(IndexView indexView, String text) {
                int position = mAdapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
//                    mLayoutManager.scrollToPosition(position);

                    mRecyclerViewScrollHelper.smoothScrollToPosition(position);
//                    mRecyclerViewScrollHelper.scrollToPosition(position);
                }
            }
        });
    }

    private void initStickyDivider() {
        final BGADivider.StickyDelegate stickyDelegate = new BGADivider.StickyDelegate() {
            @Override
            public void initCategoryAttr() {
//                mCategoryBackgroundColor = getResources().getColor(R.color.category_backgroundColor);
//                mCategoryTextColor = getResources().getColor(R.color.category_textColor);
//                mCategoryTextSize = getResources().getDimensionPixelOffset(R.dimen.textSize_16);
//                mCategoryPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.size_level4);
//                mCategoryHeight = getResources().getDimensionPixelOffset(R.dimen.size_level10);
            }

            @Override
            protected boolean isCategoryFistItem(int position) {
                return mAdapter.isCategoryFistItem(position);
            }

            @Override
            protected String getCategoryName(int position) {
                return mAdapter.getItem(position).topc;
            }

            @Override
            protected int getFirstVisibleItemPosition() {
                return mRecyclerViewScrollHelper.findFirstVisibleItemPosition();
            }
        };

        mDataRv.addItemDecoration(BGADivider.newDrawableDivider(R.drawable.shape_divider)
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level9)
                .setDelegate(stickyDelegate));

        mRecyclerViewScrollHelper = BGARVVerticalScrollHelper.newInstance(mDataRv, new BGARVVerticalScrollHelper.SimpleDelegate() {
            @Override
            public int getCategoryHeight() {
                return stickyDelegate.getCategoryHeight();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mIndexView.setTipTv(mTipTv);

        mAdapter.setData(DataEngine.loadIndexModelData());
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        ToastUtil.show("选择了城市 " + mAdapter.getItem(position).name);
    }
}