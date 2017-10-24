package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.model.IndexModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class RecyclerIndexDemoTwoAdapter extends BGARecyclerViewAdapter<IndexModel> {

    public RecyclerIndexDemoTwoAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_index_city);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
        helper.setText(R.id.tv_item_index_city, model.name);
    }

    /**
     * 是否为该分类下的第一个条目
     *
     * @param position
     * @return
     */
    public boolean isCategoryFistItem(int position) {
        // 第一条数据是该分类下的第一个条目
        if (position == 0) {
            return true;
        }

        String currentTopc = getItem(position).topc;
        String preTopc = getItem(position - 1).topc;
        // 当前条目的分类和上一个条目的分类不相等时，当前条目为该分类下的第一个条目
        if (!TextUtils.equals(currentTopc, preTopc)) {
            return true;
        }

        return false;
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