/**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.bingoogolapple.androidcommon.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/6 上午4:04
 * 描述:RecyclerView 分隔线
 */
public class BGADivider extends RecyclerView.ItemDecoration {
    private Drawable mDividerDrawable;
    private int mMarginLeft;
    private int mMarginRight;
    private int mOrientation = LinearLayout.VERTICAL;
    private int mStartSkipCount = 1;
    private int mEndSkipCount = 0;
    private int mSize = 1;
    private Delegate mDelegate;

    private BGADivider(@DrawableRes int drawableResId) {
        mDividerDrawable = ContextCompat.getDrawable(BGAAdapterUtil.getApp(), drawableResId);
        mSize = Math.min(mDividerDrawable.getIntrinsicHeight(), mDividerDrawable.getIntrinsicWidth());
    }

    /**
     * 自定义 drawable 资源分隔线
     *
     * @param drawableResId
     * @return
     */
    public static BGADivider newDrawableDivider(@DrawableRes int drawableResId) {
        return new BGADivider(drawableResId);
    }

    /**
     * 默认的 shape 资源分隔线
     *
     * @return
     */
    public static BGADivider newShapeDivider() {
        return new BGADivider(R.drawable.bga_adapter_divider_shape);
    }

    /**
     * 默认的图片分隔线
     *
     * @return
     */
    public static BGADivider newBitmapDivider() {
        return new BGADivider(R.mipmap.bga_adapter_divider_bitmap);
    }

    /**
     * 设置代理
     *
     * @param delegate
     * @return
     */
    public BGADivider setDelegate(Delegate delegate) {
        mDelegate = delegate;
        return this;
    }

    /**
     * 设置左边距和右边距资源 id
     *
     * @param resId
     * @return
     */
    public BGADivider setBothMarginResource(@DimenRes int resId) {
        mMarginLeft = BGAAdapterUtil.getDimensionPixelOffset(resId);
        mMarginRight = mMarginLeft;
        return this;
    }

    /**
     * 设置左边距和右边距
     *
     * @param bothMarginDp 单位为 dp
     * @return
     */
    public BGADivider setBothMarginDp(int bothMarginDp) {
        mMarginLeft = BGAAdapterUtil.dp2px(bothMarginDp);
        mMarginRight = mMarginLeft;
        return this;
    }

    /**
     * 设置左边距资源 id
     *
     * @param resId
     * @return
     */
    public BGADivider setMarginLeftResource(@DimenRes int resId) {
        mMarginLeft = BGAAdapterUtil.getDimensionPixelOffset(resId);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeftDp 单位为 dp
     * @return
     */
    public BGADivider setMarginLeftDp(int marginLeftDp) {
        mMarginLeft = BGAAdapterUtil.dp2px(marginLeftDp);
        return this;
    }

    /**
     * 设置右边距资源 id
     *
     * @param resId
     * @return
     */
    public BGADivider setMarginRightResource(@DimenRes int resId) {
        mMarginRight = BGAAdapterUtil.getDimensionPixelOffset(resId);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param marginRightDp 单位为 dp
     * @return
     */
    public BGADivider setMarginRightDp(int marginRightDp) {
        mMarginRight = BGAAdapterUtil.dp2px(marginRightDp);
        return this;
    }

    /**
     * 设置分隔线颜色资源 id
     *
     * @param resId
     * @param isSrcTop
     * @return
     */
    public BGADivider setColorResource(@ColorRes int resId, boolean isSrcTop) {
        return setColor(BGAAdapterUtil.getColor(resId), isSrcTop);
    }

    /**
     * 设置分隔线颜色
     *
     * @param color
     * @param isSrcTop
     * @return
     */
    public BGADivider setColor(@ColorInt int color, boolean isSrcTop) {
        mDividerDrawable.setColorFilter(color, isSrcTop ? PorterDuff.Mode.SRC_ATOP : PorterDuff.Mode.SRC);
        return this;
    }

    /**
     * 设置为水平方向
     *
     * @return
     */
    public BGADivider setHorizontal() {
        mOrientation = LinearLayout.HORIZONTAL;
        return this;
    }

    /**
     * 旋转分隔线，仅用于分隔线为 Bitmap 时。应用场景：UI 给了一个水平分隔线，恰巧项目里需要一条一模一样的竖直分隔线
     *
     * @return
     */
    public BGADivider rotateDivider() {
        if (mDividerDrawable != null && mDividerDrawable instanceof BitmapDrawable) {
            mDividerDrawable = BGAAdapterUtil.rotateBitmap(((BitmapDrawable) mDividerDrawable).getBitmap());
        }
        return this;
    }

    /**
     * 跳过开始的条数。默认值为 1，不绘制第一个 item 顶部的分隔线
     *
     * @param startSkipCount
     * @return
     */
    public BGADivider setStartSkipCount(@IntRange(from = 0) int startSkipCount) {
        mStartSkipCount = startSkipCount;
        if (mStartSkipCount < 0) {
            mStartSkipCount = 0;
        }
        return this;
    }

    /**
     * 跳过末尾的条数
     *
     * @param endSkipCount
     * @return
     */
    public BGADivider setEndSkipCount(@IntRange(from = 0) int endSkipCount) {
        mEndSkipCount = endSkipCount;
        if (mEndSkipCount < 0) {
            mEndSkipCount = 0;
        }
        return this;
    }

    /**
     * 设置分割线尺寸资源 id
     *
     * @param resId
     * @return
     */
    public BGADivider setSizeResource(@DimenRes int resId) {
        mSize = BGAAdapterUtil.getDimensionPixelOffset(resId);
        return this;
    }

    /**
     * 设置分割线尺寸
     *
     * @param sizeDp 单位为 dp
     * @return
     */
    public BGADivider setSizeDp(int sizeDp) {
        mSize = BGAAdapterUtil.dp2px(sizeDp);
        return this;
    }

    /**
     * 获取左边距
     *
     * @return
     */
    public int getMarginLeft() {
        return mMarginLeft;
    }

    /**
     * 获取右边距
     *
     * @return
     */
    public int getMarginRight() {
        return mMarginRight;
    }

    private BGAHeaderAndFooterAdapter getHeaderAndFooterAdapter(RecyclerView parent) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof BGAHeaderAndFooterAdapter) {
            return (BGAHeaderAndFooterAdapter) adapter;
        } else {
            return null;
        }
    }

    private boolean isNeedSkip(int childAdapterPosition, BGAHeaderAndFooterAdapter headerAndFooterAdapter, int realChildAdapterPosition, int realItemCount) {
        if (headerAndFooterAdapter != null && headerAndFooterAdapter.isHeaderViewOrFooterView(childAdapterPosition)) {
            // 是 header 和 footer 时跳过
            return true;
        }

        int lastPosition = realItemCount - 1;
        // 跳过最后 mEndSkipCount 个
        if (realChildAdapterPosition > lastPosition - mEndSkipCount) {
            return true;
        }

        // 跳过前 mStartSkipCount 个
        // 1
        if (realChildAdapterPosition < mStartSkipCount) {
            return true;
        }

        if (mDelegate != null) {
            return mDelegate.isNeedSkip(realChildAdapterPosition, realItemCount);
        }

        // 默认不跳过
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        int realChildAdapterPosition = childAdapterPosition;
        int realItemCount = itemCount;

        BGAHeaderAndFooterAdapter headerAndFooterAdapter = getHeaderAndFooterAdapter(parent);
        if (headerAndFooterAdapter != null) {
            // 转换成真实 item 的索引
            realChildAdapterPosition = headerAndFooterAdapter.getRealItemPosition(childAdapterPosition);
            // 转换成真实 item 的总数
            realItemCount = headerAndFooterAdapter.getRealItemCount();
        }

        if (isNeedSkip(childAdapterPosition, headerAndFooterAdapter, realChildAdapterPosition, realItemCount)) {
            outRect.set(0, 0, 0, 0);
        } else {
            if (mDelegate != null && mDelegate.isNeedCustom(realChildAdapterPosition, realItemCount)) {
                mDelegate.getItemOffsets(this, realChildAdapterPosition, realItemCount, outRect);
            } else {
                if (mOrientation == LinearLayout.VERTICAL) {
                    getVerticalItemOffsets(outRect);
                } else {
                    outRect.set(mSize, 0, 0, 0);
                }
            }
        }
    }

    public void getVerticalItemOffsets(Rect outRect) {
        outRect.set(0, mSize, 0, 0);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        int itemCount = parent.getAdapter().getItemCount();
        BGAHeaderAndFooterAdapter headerAndFooterAdapter = getHeaderAndFooterAdapter(parent);
        // 除去 header 和 footer 后中间部分真实 item 的总数
        int realItemCount = itemCount;
        if (headerAndFooterAdapter != null) {
            // 转换成真实 item 的总数
            realItemCount = headerAndFooterAdapter.getRealItemCount();
        }

        if (mOrientation == LinearLayout.VERTICAL) {
            drawVertical(canvas, parent, headerAndFooterAdapter, itemCount, realItemCount);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent, BGAHeaderAndFooterAdapter headerAndFooterAdapter, int itemCount, int realItemCount) {
        int dividerLeft = parent.getPaddingLeft() + mMarginLeft;
        int dividerRight = parent.getWidth() - parent.getPaddingRight() - mMarginRight;
        View itemView;
        RecyclerView.LayoutParams itemLayoutParams;
        int realChildAdapterPosition;

        for (int childPosition = 0; childPosition < itemCount; childPosition++) {
            itemView = parent.getChildAt(childPosition);
            if (itemView == null || itemView.getLayoutParams() == null) {
                continue;
            }

            int childAdapterPosition = parent.getChildAdapterPosition(itemView);
            realChildAdapterPosition = getRealChildAdapterPosition(childAdapterPosition, headerAndFooterAdapter);

            if (isNeedSkip(childAdapterPosition, headerAndFooterAdapter, realChildAdapterPosition, realItemCount)) {
                continue;
            }

            itemLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            int dividerBottom = itemView.getTop() - itemLayoutParams.topMargin;

            if (mDelegate != null && mDelegate.isNeedCustom(realChildAdapterPosition, realItemCount)) {
                mDelegate.drawVertical(this, canvas, dividerLeft, dividerRight, dividerBottom, realChildAdapterPosition, realItemCount);
            } else {
                drawVertical(canvas, dividerLeft, dividerRight, dividerBottom);
            }
        }
    }

    public void drawVertical(Canvas canvas, int dividerLeft, int dividerRight, int itemTop) {
        int dividerBottom = itemTop;
        int dividerTop = dividerBottom - mSize;
        mDividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
        mDividerDrawable.draw(canvas);
    }

    private int getRealChildAdapterPosition(int childAdapterPosition, BGAHeaderAndFooterAdapter headerAndFooterAdapter) {
        if (headerAndFooterAdapter != null) {
            // 转换成真实 item 的索引
            return headerAndFooterAdapter.getRealItemPosition(childAdapterPosition);
        }
        return childAdapterPosition;
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {

    }

    public interface Delegate {
        boolean isNeedSkip(int position, int itemCount);

        boolean isNeedCustom(int position, int itemCount);

        void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect);

        void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount);
    }

    public static class SimpleDelegate implements Delegate {
        protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public SimpleDelegate() {
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
            initAttr();
        }

        protected void initAttr() {
        }

        @Override
        public boolean isNeedSkip(int position, int itemCount) {
            return false;
        }

        @Override
        public boolean isNeedCustom(int position, int itemCount) {
            return false;
        }

        @Override
        public void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect) {
        }

        @Override
        public void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
        }
    }

    /**
     * 继承该类实现自定义悬浮分类样式
     */
    public static abstract class SuspensionCategoryDelegate extends SimpleDelegate {
        protected int mCategoryBackgroundColor;
        protected int mCategoryTextColor;
        protected int mCategoryPaddingLeft;
        protected int mCategoryTextSize;
        protected int mCategoryHeight;
        protected float mCategoryTextOffset;

        @Override
        protected void initAttr() {
            mCategoryBackgroundColor = Color.parseColor("#F2F2F2");
            mCategoryTextColor = Color.parseColor("#848484");
            mCategoryPaddingLeft = BGAAdapterUtil.dp2px(16);
            mCategoryTextSize = BGAAdapterUtil.sp2px(14);
            mCategoryHeight = BGAAdapterUtil.dp2px(32);

            initCategoryAttr();

            mPaint.setStyle(Paint.Style.FILL);

            calculateCategoryTextOffset();
        }

        /**
         * 计算文字底部偏移量
         */
        public void calculateCategoryTextOffset() {
            mPaint.setTextSize(mCategoryTextSize);

            Rect rect = new Rect();
            mPaint.getTextBounds("王浩", 0, 2, rect);
            int textHeight = rect.height();
            mCategoryTextOffset = (mCategoryHeight - textHeight) / 2.0f;
        }

        /**
         * 需要自定义分类属性时重写该方法
         */
        protected void initCategoryAttr() {
        }

        @Override
        public boolean isNeedCustom(int position, int itemCount) {
            // 每一项都自定义
            return true;
        }

        @Override
        public void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect) {
            if (isCategory(position)) {
                // 如果是分类则设置高度为分类高度
                outRect.set(0, mCategoryHeight, 0, 0);
            } else {
                // 如果不是分类则调用 BGADivider 的 getVerticalItemOffsets 来设置 item 高度
                divider.getVerticalItemOffsets(outRect);
            }
        }

        @Override
        public void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
            if (isCategory(position)) {
                drawCategory(divider, canvas, dividerLeft, dividerRight, dividerBottom, getCategoryName(position));
            } else {
                divider.drawVertical(canvas, dividerLeft, dividerRight, dividerBottom);
            }

            // 悬浮分类每次都要绘制，mCategoryHeight 赋值给 dividerBottom，保证始终绘制在最顶部
            drawCategory(divider, canvas, dividerLeft, dividerRight, mCategoryHeight, getCategoryName(getFirstVisibleItemPosition()));
        }

        /**
         * 指定索引位置是否是分类
         *
         * @param position
         * @return
         */
        protected abstract boolean isCategory(int position);

        /**
         * 获取指定索引位置的分类名称
         *
         * @param position
         * @return
         */
        protected abstract String getCategoryName(int position);

        /**
         * 获取第一个可见条目的索引位置
         *
         * @return
         */
        protected abstract int getFirstVisibleItemPosition();

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