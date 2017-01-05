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
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/6 上午4:04
 * 描述:RecyclerView 分隔线
 */
public class BGADivider extends RecyclerView.ItemDecoration {
    private Drawable mDividerDrawable;
    private int mLeftMargin;
    private int mRightMargin;

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

    // 如果等于分隔线的宽度或高度的话可以不用重写该方法
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == parent.getChildCount() - 1) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, mDividerDrawable.getIntrinsicHeight());
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft() + mLeftMargin;
        int right = parent.getWidth() - parent.getPaddingRight() - mRightMargin;
        View child;
        RecyclerView.LayoutParams layoutParams;
        int top;
        int bottom;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            child = parent.getChildAt(i);
            layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + layoutParams.bottomMargin;
            bottom = top + mDividerDrawable.getIntrinsicHeight();
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, BGAAdapterApp.getApp().getResources().getDisplayMetrics());
    }
}