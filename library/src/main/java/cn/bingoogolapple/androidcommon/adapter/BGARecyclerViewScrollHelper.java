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

package cn.bingoogolapple.androidcommon.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/9 下午9:27
 * 描述:RecyclerView 滚动到指定位置帮助类。参考的 http://blog.csdn.net/tyzlmjj/article/details/49227601
 */
public class BGARecyclerViewScrollHelper extends RecyclerView.OnScrollListener {
    private RecyclerView mDataRv;
    private LinearLayoutManager mLinearLayoutManager;
    private int mNewPosition = 0;
    private boolean mIsScrolling = false;
    private boolean mIsSmoothScroll = false;

    public static BGARecyclerViewScrollHelper newInstance(RecyclerView recyclerView) {
        return new BGARecyclerViewScrollHelper(recyclerView);
    }

    private BGARecyclerViewScrollHelper(RecyclerView recyclerView) {
        mDataRv = recyclerView;
        mDataRv.addOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        try {
            if (mIsScrolling && newState == RecyclerView.SCROLL_STATE_IDLE && mIsSmoothScroll) {
                mIsScrolling = false;
                int diffItemCount = mNewPosition - getLinearLayoutManager().findFirstVisibleItemPosition();
                if (0 <= diffItemCount && diffItemCount < recyclerView.getChildCount()) {
                    int top = recyclerView.getChildAt(diffItemCount).getTop();
                    recyclerView.smoothScrollBy(0, top);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        try {
            if (mIsScrolling && !mIsSmoothScroll) {
                mIsScrolling = false;
                int diffItemCount = mNewPosition - getLinearLayoutManager().findFirstVisibleItemPosition();
                if (0 <= diffItemCount && diffItemCount < mDataRv.getChildCount()) {
                    int top = mDataRv.getChildAt(diffItemCount).getTop();
                    mDataRv.scrollBy(0, top);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void smoothScrollToPosition(int newPosition) {
        try {
            if (newPosition < 0 || newPosition >= mDataRv.getAdapter().getItemCount()) {
                return;
            }

            mNewPosition = newPosition;
            mDataRv.stopScroll();
            mIsSmoothScroll = true;

            int firstItem = getLinearLayoutManager().findFirstVisibleItemPosition();
            int lastItem = getLinearLayoutManager().findLastVisibleItemPosition();
            if (newPosition <= firstItem) {
                mDataRv.smoothScrollToPosition(newPosition);
            } else if (newPosition <= lastItem) {
                int top = mDataRv.getChildAt(newPosition - firstItem).getTop();
                mDataRv.smoothScrollBy(0, top);
            } else {
                mDataRv.smoothScrollToPosition(newPosition);
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

            int firstItem = getLinearLayoutManager().findFirstVisibleItemPosition();
            int lastItem = getLinearLayoutManager().findLastVisibleItemPosition();
            if (newPosition <= firstItem) {
                mDataRv.scrollToPosition(newPosition);
            } else if (newPosition <= lastItem) {
                int top = mDataRv.getChildAt(newPosition - firstItem).getTop();
                mDataRv.scrollBy(0, top);
            } else {
                mDataRv.scrollToPosition(newPosition);
                mIsScrolling = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LinearLayoutManager getLinearLayoutManager() {
        if (mLinearLayoutManager == null) {
            mLinearLayoutManager = (LinearLayoutManager) mDataRv.getLayoutManager();
        }
        return mLinearLayoutManager;
    }
}