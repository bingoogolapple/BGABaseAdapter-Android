package cn.bingoogolapple.androidcommon.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/11/10 下午9:36
 * 描述:在子类的 getItemViewType 方法中，把 item 的布局文件资源 id 作为返回值
 */
public abstract class BGABindingRecyclerViewAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter<BGABindingViewHolder<B>> {
    private LayoutInflater mLayoutInflater;
    protected List<M> mData = new ArrayList<>();
    protected Object mItemEventHandler;

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
    public BGABindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BGABindingViewHolder(DataBindingUtil.inflate(getLayoutInflater(parent), viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(BGABindingViewHolder<B> viewHolder, int position) {
        M model = getItem(position);
        B binding = viewHolder.getBinding();
        binding.setVariable(cn.bingoogolapple.androidcommon.adapter.BR.viewHolder, viewHolder);
        binding.setVariable(cn.bingoogolapple.androidcommon.adapter.BR.model, model);
        binding.setVariable(cn.bingoogolapple.androidcommon.adapter.BR.itemEventHandler, mItemEventHandler);
        binding.executePendingBindings();

        bindSpecialModel(binding, position, model);
    }

    /**
     * 绑定特殊的 item 数据模型，比如滑动删除之类的
     *
     * @param binding
     * @param position
     * @param model
     */
    protected void bindSpecialModel(B binding, int position, M model) {
    }

    /**
     * 获取指定索引位置的数据模型
     *
     * @param position
     * @return
     */
    public M getItem(int position) {
        return mData.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<M> getData() {
        return mData;
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param data
     */
    public void addNewData(List<M> data) {
        if (data != null) {
            mData.addAll(0, data);
            notifyItemRangeInserted(0, data.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param data
     */
    public void addMoreData(List<M> data) {
        if (data != null) {
            mData.addAll(mData.size(), data);
            notifyItemRangeInserted(mData.size(), data.size());
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(List<M> data) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(M model) {
        removeItem(mData.indexOf(model));
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, M model) {
        mData.add(position, model);
        notifyItemInserted(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(M model) {
        addItem(mData.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
        notifyItemChanged(location);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        mData.add(toPosition, mData.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 设置 item 时间处理器
     *
     * @param itemEventHandler
     */
    public void setItemEventHandler(Object itemEventHandler) {
        mItemEventHandler = itemEventHandler;
    }
}
