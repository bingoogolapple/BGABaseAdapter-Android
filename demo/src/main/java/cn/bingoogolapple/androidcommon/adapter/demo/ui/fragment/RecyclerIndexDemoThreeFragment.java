package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

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

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.RecyclerIndexDemoTwoAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.widget.IndexView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:通过 BGADivider 加两个 SimpleDelegate 来实现悬浮分类列表
 */
public class RecyclerIndexDemoThreeFragment extends BaseFragment implements BGAOnRVItemClickListener {
    private RecyclerIndexDemoTwoAdapter mAdapter;
    private RecyclerView mDataRv;
    private LinearLayoutManager mLayoutManager;
    private IndexView mIndexView;
    private TextView mTipTv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerindexview_divider);
        mDataRv = getViewById(R.id.rv_recyclerindexview_data);

        mIndexView = getViewById(R.id.indexview);
        mTipTv = getViewById(R.id.tv_recyclerindexview_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new RecyclerIndexDemoTwoAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);

        mIndexView.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    mLayoutManager.scrollToPosition(position);
                }
            }
        });
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
                        return mAdapter.isCategory(position);
                    }

                    @Override
                    public void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect) {
                        outRect.set(0, mCategoryHeight, 0, 0);
                    }

                    @Override
                    public void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
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
                    public void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
                        // 将 bottom 设置成分类的高度
                        dividerBottom = mCategoryHeight;

                        drawCategory(divider, canvas, dividerLeft, dividerRight, dividerBottom, mAdapter.getItem(mLayoutManager.findFirstVisibleItemPosition()).topc);
                    }
                }));
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        showSnackbar("选择了城市 " + mAdapter.getItem(position).name);
    }

    private class BaseSuspensionCategoryDelegate extends BGADivider.SimpleDelegate {
        protected int mCategoryBackgroundColor;
        protected int mCategoryTextColor;
        protected int mCategoryPaddingLeft;
        protected int mTextHeight;
        protected int mCategoryHeight;
        protected float mCategoryTextOffset;

        @Override
        protected void initAttr() {
            mCategoryBackgroundColor = getResources().getColor(R.color.category_backgroundColor);
            mCategoryTextColor = getResources().getColor(R.color.category_textColor);
            mCategoryPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.size_level4);

            mPaint.setTextSize(getResources().getDimensionPixelOffset(R.dimen.textSize_16));
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setStyle(Paint.Style.FILL);

            mCategoryHeight = getResources().getDimensionPixelOffset(R.dimen.size_level8);

            Rect rect = new Rect();
            mPaint.getTextBounds("王浩", 0, 2, rect);
            mTextHeight = rect.height();
            mCategoryTextOffset = (mCategoryHeight - mTextHeight) / 2.0f;
        }

        protected void drawCategory(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, String category) {
            // 绘制背景
            mPaint.setColor(mCategoryBackgroundColor);
            canvas.drawRect(dividerLeft - divider.getMarginLeft(), dividerBottom - mCategoryHeight, dividerRight + divider.getMarginRight(), dividerBottom, mPaint);

            // 绘制文字
            mPaint.setColor(mCategoryTextColor);
            canvas.drawText(category, 0, category.length(), mCategoryPaddingLeft, dividerBottom - mCategoryTextOffset, mPaint);
        }
    }
}