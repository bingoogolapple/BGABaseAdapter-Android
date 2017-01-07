package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        super(recyclerView, R.layout.item_indexview);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_item_indexview_name);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
        if (isSection(position)) {
            helper.setVisibility(R.id.tv_item_indexview_catalog, View.VISIBLE);
            helper.setText(R.id.tv_item_indexview_catalog, model.topc);
        } else {
            helper.setVisibility(R.id.tv_item_indexview_catalog, View.GONE);
        }
        helper.setText(R.id.tv_item_indexview_name, model.name);
    }

    public boolean isSection(int position) {
        int section = mData.get(position).topc.charAt(0);
        return position == getPositionForSection(section);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}