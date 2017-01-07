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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/6 上午4:04
 * 描述:RecyclerView 分隔线
 */
public class BGADivider extends RecyclerView.ItemDecoration {
    private Drawable mDividerDrawable;
    private int mLeftMargin;
    private int mRightMargin;
    private int mOrientation = LinearLayout.VERTICAL;
    private int mStartSkipCount = 1;
    private int mEndSkipCount = 0;
    private Delegate mDelegate;

    private BGADivider(@DrawableRes int drawableResId) {
        mDividerDrawable = ContextCompat.getDrawable(BGAAdapterApp.getApp(), drawableResId);
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
        mLeftMargin = BGAAdapterApp.getApp().getResources().getDimensionPixelOffset(resId);
        mRightMargin = mLeftMargin;
        return this;
    }

    /**
     * 设置左边距和右边距
     *
     * @param dpValue 单位为 dp
     * @return
     */
    public BGADivider setBothMargin(int dpValue) {
        mLeftMargin = dp2px(dpValue);
        mRightMargin = mLeftMargin;
        return this;
    }

    /**
     * 设置左边距资源 id
     *
     * @param resId
     * @return
     */
    public BGADivider setLeftMarginResource(@DimenRes int resId) {
        mLeftMargin = BGAAdapterApp.getApp().getResources().getDimensionPixelOffset(resId);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param dpValue 单位为 dp
     * @return
     */
    public BGADivider setLeftMargin(int dpValue) {
        mLeftMargin = dp2px(dpValue);
        return this;
    }

    /**
     * 设置右边距资源 id
     *
     * @param resId
     * @return
     */
    public BGADivider setRightMarginResource(@DimenRes int resId) {
        mRightMargin = BGAAdapterApp.getApp().getResources().getDimensionPixelOffset(resId);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param dpValue 单位为 dp
     * @return
     */
    public BGADivider setRightMargin(int dpValue) {
        mRightMargin = dp2px(dpValue);
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
        return setColor(BGAAdapterApp.getApp().getResources().getColor(resId), isSrcTop);
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
            Bitmap inputBitmap = ((BitmapDrawable) mDividerDrawable).getBitmap();
            Matrix matrix = new Matrix();
            matrix.setRotate(90, (float) inputBitmap.getWidth() / 2, (float) inputBitmap.getHeight() / 2);

            float outputX = inputBitmap.getHeight();
            float outputY = 0;

            final float[] values = new float[9];
            matrix.getValues(values);
            float x1 = values[Matrix.MTRANS_X];
            float y1 = values[Matrix.MTRANS_Y];
            matrix.postTranslate(outputX - x1, outputY - y1);
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap.getHeight(), inputBitmap.getWidth(), Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            Canvas canvas = new Canvas(outputBitmap);
            canvas.drawBitmap(inputBitmap, matrix, paint);
            mDividerDrawable = new BitmapDrawable(null, outputBitmap);
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
                mDelegate.getItemOffsets(realChildAdapterPosition, realItemCount, outRect);
            } else {
                if (mOrientation == LinearLayout.VERTICAL) {
                    outRect.set(0, mDividerDrawable.getIntrinsicHeight(), 0, 0);
                } else {
                    outRect.set(mDividerDrawable.getIntrinsicWidth(), 0, 0, 0);
                }
            }
        }
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
        int dividerLeft = parent.getPaddingLeft() + mLeftMargin;
        int dividerRight = parent.getWidth() - parent.getPaddingRight() - mRightMargin;
        View itemView;
        RecyclerView.LayoutParams itemLayoutParams;
        int dividerTop;
        int dividerBottom;
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

            int itemTop = itemView.getTop() - itemLayoutParams.topMargin;
            if (mDelegate != null && mDelegate.isNeedCustom(realChildAdapterPosition, realItemCount)) {
                mDelegate.drawVertical(canvas, parent.getPaddingLeft(), parent.getWidth() - parent.getPaddingRight(), itemTop, realChildAdapterPosition, realItemCount);
            } else {
                dividerBottom = itemTop;
                dividerTop = dividerBottom - mDividerDrawable.getIntrinsicHeight();
                mDividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
                mDividerDrawable.draw(canvas);
            }
        }
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

        void getItemOffsets(int position, int itemCount, Rect outRect);

        void drawVertical(Canvas canvas, int left, int right, int itemTop, int position, int itemCount);
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
        public void getItemOffsets(int position, int itemCount, Rect outRect) {
        }

        @Override
        public void drawVertical(Canvas canvas, int left, int right, int itemTop, int position, int itemCount) {
        }
    }

    private static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, BGAAdapterApp.getApp().getResources().getDisplayMetrics());
    }

    private void debug(String msg) {
        Log.i(BGADivider.class.getSimpleName(), msg);
    }
}