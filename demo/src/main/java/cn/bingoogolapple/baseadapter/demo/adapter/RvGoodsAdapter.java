package cn.bingoogolapple.baseadapter.demo.adapter;

import androidx.recyclerview.widget.RecyclerView;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.model.GoodsModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:仿美团外卖点餐界面左右联动-商品列表适配器
 */
public class RvGoodsAdapter extends BGARecyclerViewAdapter<GoodsModel> {

    public RvGoodsAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_cascade_goods);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, GoodsModel model) {
        helper.setText(R.id.tv_item_cascade_goods_name, model.name);
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

        long currentCategoryId = getItem(position).categoryId;
        long preCategoryId = getItem(position - 1).categoryId;
        // 当前条目的分类 id 和上一个条目的分类 id 不相等时，当前条目为该分类下的第一个条目
        if (currentCategoryId != preCategoryId) {
            return true;
        }

        return false;
    }

    /**
     * 根据分类id获取该分类下的第一个商品在商品列表中的位置
     *
     * @param categoryId
     * @return
     */
    public int getFirstPositionByCategoryId(long categoryId) {
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            if (getItem(i).categoryId == categoryId) {
                return i;
            }
        }
        return -1;
    }
}