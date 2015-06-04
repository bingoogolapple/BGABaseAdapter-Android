package cn.bingoogolapple.androidcommon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * @param <M> 适配的数据类型
 */
public abstract class BGARecyclerViewAdapter<M> extends RecyclerView.Adapter<BGARecyclerViewHolder> {
    protected final int mItemLayoutId;
    protected Context mContext;
    protected List<M> mDatas;
    protected BGAOnItemChildClickListener mOnItemChildClickListener;
    protected BGAOnItemChildLongClickListener mOnItemChildLongClickListener;
    private BGAOnRVItemClickListener mOnRVItemClickListener;
    private BGAOnRVItemLongClickListener mOnRVItemLongClickListener;

    public BGARecyclerViewAdapter(Context context, int itemLayoutId) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BGARecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BGARecyclerViewHolder viewHolder = new BGARecyclerViewHolder(LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false), mOnRVItemClickListener, mOnRVItemLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildClickListener(mOnItemChildClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(mOnItemChildLongClickListener);
        setItemChildListener(viewHolder.getViewHolderHelper());
        return viewHolder;
    }

    /**
     * 为item的孩子节点设置监听器，并不是每一个数据列表都要为item的子控件添加事件监听器，所以这里采用了空实现，需要设置事件监听器时重写该方法即可
     *
     * @param viewHolderHelper
     */
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    @Override
    public void onBindViewHolder(BGARecyclerViewHolder viewHolder, int position) {
        fillData(viewHolder.getViewHolderHelper(), position, getItem(position));
    }

    /**
     * 填充item数据
     *
     * @param viewHolderHelper
     * @param position
     * @param model
     */
    protected abstract void fillData(BGAViewHolderHelper viewHolderHelper, int position, M model);

    /**
     * 设置item的点击事件监听器
     *
     * @param onRVItemClickListener
     */
    public void setOnRVItemClickListener(BGAOnRVItemClickListener onRVItemClickListener) {
        mOnRVItemClickListener = onRVItemClickListener;
    }

    /**
     * 设置item的长按事件监听器
     *
     * @param onRVItemLongClickListener
     */
    public void setOnRVItemLongClickListener(BGAOnRVItemLongClickListener onRVItemLongClickListener) {
        mOnRVItemLongClickListener = onRVItemLongClickListener;
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(BGAOnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 设置item中的子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(BGAOnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    public M getItem(int position) {
        return mDatas.get(position);
    }

    public void setDatas(List<M> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<M> datas) {
        if (mDatas == null) {
            mDatas = datas;
        } else {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(M model) {
        removeItem(mDatas.indexOf(model));
    }

    public void addItem(int position, M model) {
        mDatas.add(position, model);
        notifyItemInserted(position);
    }

    public void setItem(int location, M newModel) {
        mDatas.set(location, newModel);
        notifyItemChanged(location);
    }

    public void setItem(M oldModel, M newModel) {
        setItem(mDatas.indexOf(oldModel), newModel);
    }
}