/**
 * Copyright 2015 bingoogolapple
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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/11/10 下午9:36
 * 描述:在子类的 getItemViewType 方法中，把 item 的布局文件资源 id 作为返回值
 */
public class BGABindingRecyclerViewAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter<BGABindingViewHolder<B>> {
    private LayoutInflater mLayoutInflater;
    protected List<M> mData = new ArrayList<>();
    protected Object mUiHandler;
    protected Object mStatusModel;
    protected BGAHeaderAndFooterAdapter mHeaderAndFooterAdapter;
    protected int mDefaultItemLayoutId;
    protected LifecycleOwner mLifecycleOwner;
    private boolean mIsIgnoreCheckedChanged = true;

    /**
     * 使用该构造方法时需要重写 getItemViewType 方法来返回布局文件资源 id
     */
    public BGABindingRecyclerViewAdapter() {
    }

    /**
     * 默认的布局文件资源 id
     */
    public BGABindingRecyclerViewAdapter(@LayoutRes int defaultItemLayoutId) {
        mDefaultItemLayoutId = defaultItemLayoutId;
    }

    protected LayoutInflater getLayoutInflater(View view) {
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mLayoutInflater;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDefaultItemLayoutId == 0) {
            throw new RuntimeException("请在 " + this.getClass().getSimpleName()
                    + " 中重写 getItemViewType 方法返回布局资源 id，或者使用 BGABindingRecyclerViewAdapter 一个参数的构造方法 BGABindingRecyclerViewAdapter(int defaultItemLayoutId)");
        }
        return mDefaultItemLayoutId;
    }

    @Override
    public BGABindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BGABindingViewHolder(this, DataBindingUtil.inflate(getLayoutInflater(parent), viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(BGABindingViewHolder<B> viewHolder, int position) {
        // 在设置值的过程中忽略选中状态变化
        mIsIgnoreCheckedChanged = true;

        M model = getItem(position);
        B binding = viewHolder.getBinding();
        binding.setLifecycleOwner(mLifecycleOwner);
        binding.setVariable(cn.bingoogolapple.baseadapter.BR.viewHolder, viewHolder);
        binding.setVariable(cn.bingoogolapple.baseadapter.BR.uiHandler, mUiHandler);
        binding.setVariable(cn.bingoogolapple.baseadapter.BR.statusModel, mStatusModel);
        binding.setVariable(cn.bingoogolapple.baseadapter.BR.model, model);
        bindSpecialModel(binding, position, model);
        binding.executePendingBindings();

        mIsIgnoreCheckedChanged = false;
    }

    /**
     * 绑定特殊的 item 数据模型，比如滑动删除之类的
     */
    protected void bindSpecialModel(B binding, int position, M model) {
    }

    public boolean isIgnoreCheckedChanged() {
        return mIsIgnoreCheckedChanged;
    }

    /**
     * 获取指定索引位置的数据模型
     */
    public M getItem(int position) {
        return mData.get(position);
    }

    /**
     * 获取数据集合
     */
    public List<M> getData() {
        return mData;
    }

    public void setStatusModel(Object statusModel) {
        mStatusModel = statusModel;
    }

    /**
     * 设置 item 事件处理器
     */
    public void setUiHandler(Object uiHandler) {
        mUiHandler = uiHandler;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }

    public final void notifyItemRangeInsertedWrapper(int positionStart, int itemCount) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            mHeaderAndFooterAdapter.notifyItemRangeInserted(mHeaderAndFooterAdapter.getHeadersCount() + positionStart, itemCount);
        }
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     */
    public void addNewData(List<M> data) {
        if (BGABaseAdapterUtil.isListNotEmpty(data)) {
            mData.addAll(0, data);
            notifyItemRangeInsertedWrapper(0, data.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     */
    public void addMoreData(List<M> data) {
        if (BGABaseAdapterUtil.isListNotEmpty(data)) {
            int positionStart = mData.size();
            mData.addAll(mData.size(), data);
            notifyItemRangeInsertedWrapper(positionStart, data.size());
        }
    }

    public final void notifyDataSetChangedWrapper() {
        if (mHeaderAndFooterAdapter == null) {
            notifyDataSetChanged();
        } else {
            mHeaderAndFooterAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     */
    public void setData(List<M> data) {
        if (mData == data) {
            return;
        }
        if (data == null) {
            // DataBinding 和 RecyclerView 同时使用时不要用集合的 clear 方法，如果传入的集合为空，直接 new 一个新的
            mData = new ArrayList<>();
        } else {
            mData = data;
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mData.clear();
        notifyDataSetChangedWrapper();
    }

    public final void notifyItemRemovedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemRemoved(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemRemoved(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 删除指定索引数据条目
     */
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemovedWrapper(position);
    }

    /**
     * 删除指定数据条目。该方法在 ItemTouchHelper.Callback 的 onSwiped 方法中调用
     */
    public void removeItem(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        if (mHeaderAndFooterAdapter != null) {
            mData.remove(position - mHeaderAndFooterAdapter.getHeadersCount());
            mHeaderAndFooterAdapter.notifyItemRemoved(position);
        } else {
            removeItem(position);
        }
    }

    /**
     * 删除指定数据条目
     */
    public void removeItem(M model) {
        removeItem(mData.indexOf(model));
    }

    public final void notifyItemInsertedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemInserted(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemInserted(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 在指定位置添加数据条目
     */
    public void addItem(int position, M model) {
        mData.add(position, model);
        notifyItemInsertedWrapper(position);
    }

    /**
     * 在集合头部添加数据条目
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     */
    public void addLastItem(M model) {
        addItem(mData.size(), model);
    }

    public final void notifyItemChangedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemChanged(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemChanged(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 替换指定索引的数据条目
     */
    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
        notifyItemChangedWrapper(location);
    }

    /**
     * 替换指定数据条目
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    public final void notifyItemMovedWrapper(int fromPosition, int toPosition) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemMoved(fromPosition, toPosition);
        } else {
            mHeaderAndFooterAdapter.notifyItemMoved(mHeaderAndFooterAdapter.getHeadersCount() + fromPosition,
                    mHeaderAndFooterAdapter.getHeadersCount() + toPosition);
        }
    }

    /**
     * 移动数据条目的位置
     */
    public void moveItem(int fromPosition, int toPosition) {
        notifyItemChangedWrapper(fromPosition);
        notifyItemChangedWrapper(toPosition);

        // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作

        mData.add(toPosition, mData.remove(fromPosition));
        notifyItemMovedWrapper(fromPosition, toPosition);
    }

    /**
     * 移动数据条目的位置。该方法在 ItemTouchHelper.Callback 的 onMove 方法中调用
     */
    public void moveItem(RecyclerView.ViewHolder from, RecyclerView.ViewHolder to) {
        int fromPosition = from.getAdapterPosition();
        int toPosition = to.getAdapterPosition();
        if (mHeaderAndFooterAdapter != null) {
            mHeaderAndFooterAdapter.notifyItemChanged(fromPosition);
            mHeaderAndFooterAdapter.notifyItemChanged(toPosition);

            // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作

            mData.add(toPosition - mHeaderAndFooterAdapter.getHeadersCount(), mData.remove(fromPosition - mHeaderAndFooterAdapter.getHeadersCount()));
            mHeaderAndFooterAdapter.notifyItemMoved(fromPosition, toPosition);
        } else {
            moveItem(fromPosition, toPosition);
        }
    }

    /**
     * @return 获取第一个数据模型
     */
    public
    @Nullable
    M getFirstItem() {
        return getItemCount() > 0 ? getItem(0) : null;
    }

    /**
     * @return 获取最后一个数据模型
     */
    public
    @Nullable
    M getLastItem() {
        return getItemCount() > 0 ? getItem(getItemCount() - 1) : null;
    }


    public void addHeaderView(View headerView) {
        getHeaderAndFooterAdapter().addHeaderView(headerView);
    }

    public void addFooterView(View footerView) {
        getHeaderAndFooterAdapter().addFooterView(footerView);
    }

    public void removeHeaderView(View view) {
        getHeaderAndFooterAdapter().removeHeaderView(view);
    }

    public void removeFooterView(View view) {
        getHeaderAndFooterAdapter().removeFooterView(view);
    }

    public int getHeadersCount() {
        return mHeaderAndFooterAdapter == null ? 0 : mHeaderAndFooterAdapter.getHeadersCount();
    }

    public int getFootersCount() {
        return mHeaderAndFooterAdapter == null ? 0 : mHeaderAndFooterAdapter.getFootersCount();
    }

    public BGAHeaderAndFooterAdapter getHeaderAndFooterAdapter() {
        if (mHeaderAndFooterAdapter == null) {
            synchronized (BGABindingRecyclerViewAdapter.this) {
                if (mHeaderAndFooterAdapter == null) {
                    mHeaderAndFooterAdapter = new BGAHeaderAndFooterAdapter(this);
                }
            }
        }
        return mHeaderAndFooterAdapter;
    }

    /**
     * 是否是头部或尾部
     */
    public boolean isHeaderOrFooter(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() < getHeadersCount() || viewHolder.getAdapterPosition() >= getHeadersCount() + getItemCount();
    }
}
