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
    private int mStartSkipCount = 0;
    private int mEndSkipCount = 0;

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
     * 跳过开始的条数
     *
     * @param startSkipCount
     * @return
     */
    public BGADivider setStartSkipCount(int startSkipCount) {
        mStartSkipCount = startSkipCount;
        return this;
    }

    /**
     * 跳过末尾的条数
     *
     * @param endSkipCount
     * @return
     */
    public BGADivider setEndSkipCount(int endSkipCount) {
        mEndSkipCount = endSkipCount;
        return this;
    }

    private boolean isNeedSkip(int position, RecyclerView parent) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        int itemCount = parent.getAdapter().getItemCount();

        if (adapter instanceof BGAHeaderAndFooterAdapter) {
            BGAHeaderAndFooterAdapter headerAndFooterAdapter = (BGAHeaderAndFooterAdapter) adapter;
            if (headerAndFooterAdapter.isHeaderViewOrFooterView(position)) {
                // 是 header 和 footer 时跳过
                return true;
            }

            // 转换成真实 item 的索引
            position = headerAndFooterAdapter.getRealItemPosition(position);
            // 转换成真实 item 的总数
            itemCount = headerAndFooterAdapter.getRealItemCount();
        }

        // 跳过最后一个
        if (position == itemCount - 1) {
            return true;
        }

        // 默认不跳过
        return false;
    }

    // 如果等于分隔线的宽度或高度的话可以不用重写该方法
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        int position = parent.getChildAdapterPosition(view);

        if (mOrientation == LinearLayout.VERTICAL) {
            if (isNeedSkip(position, parent)) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDividerDrawable.getIntrinsicHeight());
            }
        } else {
            if (isNeedSkip(position, parent)) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, mDividerDrawable.getIntrinsicWidth(), 0);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        if (mOrientation == LinearLayout.VERTICAL) {
            drawVertical(canvas, parent);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft() + mLeftMargin;
        int right = parent.getWidth() - parent.getPaddingRight() - mRightMargin;
        View child;
        RecyclerView.LayoutParams layoutParams;
        int top;
        int bottom;
        int itemCount = parent.getAdapter().getItemCount();
        for (int childPosition = 0; childPosition < itemCount; childPosition++) {
            child = parent.getChildAt(childPosition);
            if (child == null || child.getLayoutParams() == null) {
                continue;
            }

            int childAdapterPosition = parent.getChildAdapterPosition(child);
            if (isNeedSkip(childAdapterPosition, parent)) {
                debug("是 header 和 footer 时跳过");
                continue;
            }

            layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + layoutParams.bottomMargin;
            bottom = top + mDividerDrawable.getIntrinsicHeight();
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(canvas);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {

    }

    private static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, BGAAdapterApp.getApp().getResources().getDisplayMetrics());
    }

    private void debug(String msg) {
        Log.i(BGADivider.class.getSimpleName(), msg);
    }
}