package cn.bingoogolapple.baseadapter.demo.adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.model.CategoryModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:仿美团外卖点餐界面左右联动-商品分类列表适配器
 */
public class RvCategoryAdapter extends BGARecyclerViewAdapter<CategoryModel> {

    public RvCategoryAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_cascade_category);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, CategoryModel model) {
        // 设置选中和未选中的背景
        if (mCheckedPosition == position) {
            helper.setBackgroundColorRes(R.id.tv_item_cascade_category_name, android.R.color.white);
        } else {
            helper.setBackgroundRes(R.id.tv_item_cascade_category_name, R.color.bga_adapter_item_pressed);
        }

        helper.setText(R.id.tv_item_cascade_category_name, model.name);
    }

    /**
     * 根据分类id获取分类索引
     *
     * @param categoryId
     * @return
     */
    public int getPositionByCategoryId(int categoryId) {
        if (categoryId < 0) {
            return 0;
        }

        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getItem(i).id == categoryId) {
                return i;
            }
        }
        return 0;
    }
}