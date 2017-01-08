package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.content.Context;
import android.view.View;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.model.IndexModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 上午12:39
 * 描述:
 */
public class ListIndexAdapter extends BGAAdapterViewAdapter<IndexModel> {

    public ListIndexAdapter(Context context) {
        super(context, R.layout.item_index_all);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper) {
        helper.setItemChildClickListener(R.id.tv_item_index_catalog);
        helper.setItemChildClickListener(R.id.tv_item_index_city);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
        if (isCategory(position)) {
            helper.setVisibility(R.id.tv_item_index_catalog, View.VISIBLE);
            helper.setText(R.id.tv_item_index_catalog, model.topc);
        } else {
            helper.setVisibility(R.id.tv_item_index_catalog, View.GONE);
        }
        helper.setText(R.id.tv_item_index_city, model.name);
    }

    public boolean isCategory(int position) {
        int category = getItem(position).topc.charAt(0);
        return position == getPositionForCategory(category);
    }

    public int getPositionForCategory(int category) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = getItem(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == category) {
                return i;
            }
        }
        return -1;
    }
}