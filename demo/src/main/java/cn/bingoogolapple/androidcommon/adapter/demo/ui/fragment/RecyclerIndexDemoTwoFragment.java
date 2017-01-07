package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.RecyclerIndexDemoTwoAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.model.IndexModel;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.widget.IndexView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RecyclerIndexDemoTwoFragment extends BaseFragment implements BGAOnRVItemClickListener {
    private RecyclerIndexDemoTwoAdapter mAdapter;
    private List<IndexModel> mData;
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
        mAdapter = new RecyclerIndexDemoTwoAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);

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

        mDataRv.addItemDecoration(BGADivider.newBitmapDivider().setStartSkipCount(0).setDelegate(new BGADivider.SimpleDelegate() {
            private int mCategoryBackgroundColor;
            private int mCategoryTextColor;
            private int mPaddingLeft;
            private int mTextHeight;
            private int mCategoryHeight;
            private float mCategoryTextOffset;

            @Override
            protected void initAttr() {
                mCategoryBackgroundColor = getResources().getColor(R.color.category_backgroundColor);
                mCategoryTextColor = getResources().getColor(R.color.category_textColor);
                mPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.size_level4);

                mPaint.setTextSize(getResources().getDimensionPixelOffset(R.dimen.textSize_16));
                mPaint.setFakeBoldText(true);
                mPaint.setStyle(Paint.Style.FILL);

                mCategoryHeight = getResources().getDimensionPixelOffset(R.dimen.size_level12);

                Rect rect = new Rect();
                mPaint.getTextBounds("A", 0, 1, rect);
                mTextHeight = rect.height();
                mCategoryTextOffset = (mCategoryHeight - mTextHeight) / 2.0f;
            }

            @Override
            public boolean isNeedCustom(int position, int itemCount) {
                return mAdapter.isSection(position);
            }

            @Override
            public void getItemOffsets(int position, int itemCount, Rect outRect) {
                outRect.set(0, mCategoryHeight, 0, 0);
            }

            @Override
            public void drawVertical(Canvas canvas, int left, int right, int bottom, int position, int itemCount) {
                mPaint.setColor(mCategoryBackgroundColor);
                canvas.drawRect(left, bottom - mCategoryHeight, right, bottom, mPaint);

                mPaint.setColor(mCategoryTextColor);
                String topc = mAdapter.getItem(position).topc;
                canvas.drawText(topc, 0, topc.length(), mPaddingLeft, bottom - mCategoryTextOffset, mPaint);
            }
        }));
        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);


        mData = DataEngine.loadIndexModelData();
        mAdapter.setData(mData);
        mDataRv.setAdapter(mAdapter);

        mTopcTv.setText(mAdapter.getItem(0).topc);
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        showSnackbar("选择了城市 " + mAdapter.getItem(position).name);
    }
}