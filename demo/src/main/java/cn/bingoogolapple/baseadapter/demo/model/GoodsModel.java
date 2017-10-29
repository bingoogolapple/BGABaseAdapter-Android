package cn.bingoogolapple.baseadapter.demo.model;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 14:53
 * 描述:
 */
public class GoodsModel {
    public int id;
    public String name;
    public int categoryId;

    public GoodsModel() {
    }

    public GoodsModel(int id, String name, int categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }
}