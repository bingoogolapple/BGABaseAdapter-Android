/**
 * Copyright 2015 tyzlmjj
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

package cn.bingoogolapple.baseadapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/9 下午9:27
 * 描述:RecyclerView 滚动到指定位置帮助类。参考的 http://blog.csdn.net/tyzlmjj/article/details/49227601
 */
public class BGARVVerticalScrollHelper extends RecyclerView.OnScrollListener {
    private RecyclerView mDataRv;
    private Delegate mDelegate;
    private LinearLayoutManager mLinearLayoutManager;
    private int mNewPosition = 0;
    private boolean mIsScrolling = false;
    private boolean mIsSmoothScroll = false;
    private int mState = RecyclerView.SCROLL_STATE_IDLE;

    public static BGARVVerticalScrollHelper newInstance(RecyclerView recyclerView) {
        return new BGARVVerticalScrollHelper(recyclerView, null);
    }

    public static BGARVVerticalScrollHelper newInstance(RecyclerView recyclerView, Delegate delegate) {
        return new BGARVVerticalScrollHelper(recyclerView, delegate);
    }

    private BGARVVerticalScrollHelper(RecyclerView recyclerView, Delegate delegate) {
        mDataRv = recyclerView;
        mDataRv.addOnScrollListener(this);
        mDelegate = delegate;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        try {
            mState = newState;

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mIsScrolling && mIsSmoothScroll) {
                    mIsScrolling = false;
                    mIsSmoothScroll = false;

                    int diffItemCount = mNewPosition - findFirstVisibleItemPosition();
                    if (0 <= diffItemCount && diffItemCount < recyclerView.getChildCount()) {
                        int top = recyclerView.getChildAt(diffItemCount).getTop() - getCategoryHeight();
                        recyclerView.scrollBy(0, top);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        try {
            if (mState == RecyclerView.SCROLL_STATE_DRAGGING) {
                mIsScrolling = false;
                mIsSmoothScroll = false;

                if (mDelegate != null) {
                    mDelegate.dragging(findFirstVisibleItemPosition());
                }
            }

            if (!mIsScrolling && !mIsSmoothScroll && mState == RecyclerView.SCROLL_STATE_SETTLING && mDelegate != null) {
                mDelegate.settling(findFirstVisibleItemPosition());
            }

            if (mIsScrolling && !mIsSmoothScroll) {
                mIsScrolling = false;
                int diffItemCount = mNewPosition - findFirstVisibleItemPosition();
                if (0 <= diffItemCount && diffItemCount < mDataRv.getChildCount()) {
                    int top = mDataRv.getChildAt(diffItemCount).getTop() - getCategoryHeight();
                    mDataRv.scrollBy(0, top);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getCategoryHeight() {
        return mDelegate == null ? 0 : mDelegate.getCategoryHeight();
    }

    public void smoothScrollToPosition(int newPosition) {
        try {
            if (newPosition < 0 || newPosition >= mDataRv.getAdapter().getItemCount()) {
                return;
            }

            mNewPosition = newPosition;
            mDataRv.stopScroll();
            mIsSmoothScroll = true;

            int firstItem = findFirstVisibleItemPosition();
            int lastItem = findLastVisibleItemPosition();
            if (mNewPosition <= firstItem) {
                mDataRv.smoothScrollToPosition(mNewPosition);
            } else if (mNewPosition <= lastItem) {
                int top = mDataRv.getChildAt(mNewPosition - firstItem).getTop() - getCategoryHeight();
                if (top <= 0) {
                    // top 小于等于0时先往上滚动1再滚动「item 复用导致的」
                    mDataRv.scrollBy(0, 2);
                    smoothScrollToPosition(mNewPosition);
                } else {
                    mDataRv.smoothScrollBy(0, top);
                    mIsScrolling = true;
                }
            } else {
                mDataRv.smoothScrollToPosition(mNewPosition);
                mIsScrolling = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollToPosition(int newPosition) {
        try {
            if (newPosition < 0 || newPosition >= mDataRv.getAdapter().getItemCount()) {
                return;
            }

            mNewPosition = newPosition;
            mDataRv.stopScroll();
            mIsSmoothScroll = false;

            int firstItem = findFirstVisibleItemPosition();
            int lastItem = findLastVisibleItemPosition();
            if (newPosition <= firstItem) {
                mDataRv.scrollToPosition(newPosition);
            } else if (newPosition <= lastItem) {
                int top = mDataRv.getChildAt(newPosition - firstItem).getTop() - getCategoryHeight();
                mDataRv.scrollBy(0, top);
            } else {
                mDataRv.scrollToPosition(newPosition);
                mIsScrolling = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取第一个可见条目索引
     *
     * @return
     */
    public int findFirstVisibleItemPosition() {
        return getLinearLayoutManager().findFirstVisibleItemPosition();
    }

    /**
     * 获取最后一个可见条目索引
     *
     * @return
     */
    public int findLastVisibleItemPosition() {
        return getLinearLayoutManager().findLastVisibleItemPosition();
    }

    /**
     * 获取布局管理器
     *
     * @return
     */
    public LinearLayoutManager getLinearLayoutManager() {
        if (mLinearLayoutManager == null) {
            mLinearLayoutManager = (LinearLayoutManager) mDataRv.getLayoutManager();
        }
        return mLinearLayoutManager;
    }

    public interface Delegate {
        /**
         * 获取分类高度
         *
         * @return
         */
        int getCategoryHeight();

        /**
         * 用户手指拖拽、惯性运动、停止滚动时被调用
         *
         * @param position
         */
        void dragging(int position);

        /**
         * 惯性运动时被调用
         *
         * @param position
         */
        void settling(int position);
    }

    public static class SimpleDelegate implements Delegate {
        @Override
        public int getCategoryHeight() {
            return 0;
        }

        @Override
        public void dragging(int position) {
        }

        @Override
        public void settling(int position) {
        }
    }
}