package cn.bingoogolapple.androidcommon.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/11/10 下午9:34
 * 描述:
 */
public class BGABindingViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private B mBinding;

    public BGABindingViewHolder(B binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public B getBinding() {
        return mBinding;
    }
}