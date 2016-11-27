package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.model.NormalModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class NormalRecyclerViewAdapter extends BGARecyclerViewAdapter<NormalModel> {

    public NormalRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_normal);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildCheckedChangeListener(R.id.cb_item_normal_status);
        helper.setRVItemChildTouchListener(R.id.iv_item_normal_avatar);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, NormalModel model) {
        Glide.with(mContext).load(model.avatorPath).placeholder(R.mipmap.holder).error(R.mipmap.holder).into(helper.getImageView(R.id.iv_item_normal_avatar));
        helper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);

        helper.setChecked(R.id.cb_item_normal_status, model.selected);
    }

}