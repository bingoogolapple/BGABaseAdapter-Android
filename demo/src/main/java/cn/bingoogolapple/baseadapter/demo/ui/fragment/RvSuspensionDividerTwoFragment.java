package cn.bingoogolapple.baseadapter.demo.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bingoogolapple.baseadapter.BGADivider;
import cn.bingoogolapple.baseadapter.BGARecyclerViewScrollHelper;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.adapter.RecyclerIndexDemoTwoAdapter;
import cn.bingoogolapple.baseadapter.demo.engine.DataEngine;
import cn.bingoogolapple.baseadapter.demo.ui.widget.IndexView;
import cn.bingoogolapple.baseadapter.demo.util.ToastUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:通过 BGADivider 加两个 SimpleDelegate 来实现悬浮分类列表
 */
public class RvSuspensionDividerTwoFragment extends MvcFragment implements BGAOnRVItemClickListener {
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

        initSuspensionCategory();

        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);


        mAdapter.setData(DataEngine.loadIndexModelData());
        mDataRv.setAdapter(mAdapter);
    }

    private void initSuspensionCategory() {
        // 添加普通分类
        mDataRv.addItemDecoration(BGADivider.newDrawableDivider(R.drawable.shape_divider)
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level9)
                .setDelegate(new BaseSuspensionCategoryDelegate() {
                    @Override
                    public boolean isNeedCustom(int position, int itemCount) {
                        return mAdapter.isCategoryFistItem(position);
                    }

                    @Override
                    public void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect) {
                        outRect.set(0, mCategoryHeight, 0, 0);
                    }

                    @Override
                    public void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int
                            itemCount) {
                        drawCategory(divider, canvas, dividerLeft, dividerRight, dividerBottom, mAdapter.getItem(position).topc);
                    }
                }));
        // 添加悬浮分类
        mDataRv.addItemDecoration(BGADivider.newBitmapDivider()
                .setStartSkipCount(0)
                .setDelegate(new BaseSuspensionCategoryDelegate() {
                    @Override
                    public boolean isNeedCustom(int position, int itemCount) {
                        // 悬浮 Category 全部都需要返回 true 来自定义
                        return true;
                    }

                    @Override
                    public void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int
                            itemCount) {
                        if (position == mLayoutManager.findFirstVisibleItemPosition() + 1) {
                            // 绘制悬浮分类
                            int suspensionBottom = mCategoryHeight;
                            int offset = mCategoryHeight * 2 - dividerBottom;
                            if (offset > 0 && mAdapter.isCategoryFistItem(position)) {
                                suspensionBottom -= offset;
                            }
                            drawCategory(divider, canvas, dividerLeft, dividerRight, suspensionBottom, mAdapter.getItem(mLayoutManager
                                    .findFirstVisibleItemPosition()).topc);
                        }
                    }
                }));
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        ToastUtil.show("选择了城市 " + mAdapter.getItem(position).name);
    }

    private class BaseSuspensionCategoryDelegate extends BGADivider.SimpleDelegate {
        protected int mCategoryBackgroundColor;
        protected int mCategoryTextColor;
        protected int mCategoryPaddingLeft;
        protected int mTextHeight;
        protected int mCategoryHeight;
        protected int mCategoryTextSize;
        protected float mCategoryTextOffset;

        @Override
        protected void initAttr() {
            mCategoryBackgroundColor = getResources().getColor(R.color.category_backgroundColor);
            mCategoryTextColor = getResources().getColor(R.color.category_textColor);
            mCategoryTextSize = getResources().getDimensionPixelOffset(R.dimen.textSize_16);
            mCategoryPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.size_level4);
            mCategoryHeight = getResources().getDimensionPixelOffset(R.dimen.size_level8);

            mPaint.setTextSize(mCategoryTextSize);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setStyle(Paint.Style.FILL);

            Rect rect = new Rect();
            mPaint.getTextBounds("王浩", 0, 2, rect);
            mTextHeight = rect.height();
            mCategoryTextOffset = (mCategoryHeight - mTextHeight) / 2.0f;
        }

        protected void drawCategory(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, String category) {
            // 绘制背景
            mPaint.setColor(mCategoryBackgroundColor);
            canvas.drawRect(dividerLeft - divider.getMarginLeft(), dividerBottom - mCategoryHeight, dividerRight + divider.getMarginRight(), dividerBottom,
                    mPaint);

            // 绘制文字
            mPaint.setColor(mCategoryTextColor);
            canvas.drawText(category, 0, category.length(), mCategoryPaddingLeft, dividerBottom - mCategoryTextOffset, mPaint);
        }
    }
}