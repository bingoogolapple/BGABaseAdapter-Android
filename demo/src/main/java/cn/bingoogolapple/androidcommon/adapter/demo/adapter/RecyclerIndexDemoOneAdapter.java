package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.model.IndexModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class RecyclerIndexDemoOneAdapter extends BGARecyclerViewAdapter<IndexModel> {

    public RecyclerIndexDemoOneAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_item_index_catalog);
        helper.setItemChildClickListener(R.id.tv_item_index_city);
    }

    @Override
    public int getItemViewType(int position) {
        if (isCategory(position)) {
            return R.layout.item_index_all;
        } else {
            return R.layout.item_index_city;
        }
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
        if (isCategory(position)) {
            helper.setText(R.id.tv_item_index_catalog, model.topc);
        }
        helper.setText(R.id.tv_item_index_city, model.name);
    }

    public boolean isCategory(int position) {
        int category = getItem(position).topc.charAt(0);
        return position == getPositionForCategory(category);
    }

    public int getPositionForCategory(int category) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = getItem(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == category) {
                return i;
            }
        }
        return -1;
    }
}