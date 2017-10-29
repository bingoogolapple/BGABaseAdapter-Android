package cn.bingoogolapple.baseadapter.demo.model;

import java.util.List;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 14:53
 * 描述:
 */
public class CategoryModel {
    public int id;
    public String name;
    public List<GoodsModel> goodsModelList;

    public CategoryModel() {
    }

    public CategoryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
}