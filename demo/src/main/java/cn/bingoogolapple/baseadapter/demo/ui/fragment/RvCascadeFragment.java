package cn.bingoogolapple.baseadapter.demo.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGADivider;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARVVerticalScrollHelper;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.adapter.RvCategoryAdapter;
import cn.bingoogolapple.baseadapter.demo.adapter.RvGoodsAdapter;
import cn.bingoogolapple.baseadapter.demo.engine.DataEngine;
import cn.bingoogolapple.baseadapter.demo.model.CategoryModel;
import cn.bingoogolapple.baseadapter.demo.model.GoodsModel;
import cn.bingoogolapple.baseadapter.demo.util.ToastUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:仿美团外卖点餐界面左右联动
 */
public class RvCascadeFragment extends MvcFragment {
    private RecyclerView mCategoryRv;
    private RecyclerView mGoodsRv;
    private RvCategoryAdapter mCategoryAdapter;
    private RvGoodsAdapter mGoodsAdapter;
    private BGARVVerticalScrollHelper mGoodsScrollHelper; // 商品列表滚动帮助类

    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_rv_cascade;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mCategoryRv = getViewById(R.id.rv_cascade_category);
        mGoodsRv = getViewById(R.id.rv_cascade_goods);
    }

    @Override
    protected void setListener() {
        // 分类
        mCategoryAdapter = new RvCategoryAdapter(mCategoryRv);
        mCategoryAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                mCategoryAdapter.setCheckedPosition(position);
                long categoryId = mCategoryAdapter.getItem(position).id;
                int goodsPosition = mGoodsAdapter.getFirstPositionByCategoryId(categoryId);

                mGoodsScrollHelper.smoothScrollToPosition(goodsPosition);
//                mGoodsScrollHelper.scrollToPosition(goodsPosition);
            }
        });
        mCategoryRv.addItemDecoration(BGADivider.newShapeDivider()
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level3)
                .setDelegate(new BGADivider.SimpleDelegate() {
                    @Override
                    public boolean isNeedCustom(int position, int itemCount) {
                        // 选中的条目和下一个条目自定义，但不绘制分割线
                        return position == mCategoryAdapter.getCheckedPosition() || position == mCategoryAdapter.getCheckedPosition() + 1;
                    }

                    @Override
                    public void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect) {
                        // 选中的条目和下一个条目自定义占用分割线高度，但不绘制分割线
                        divider.getVerticalItemOffsets(outRect);
                    }
                }));
        mCategoryRv.setAdapter(mCategoryAdapter);


        // 商品
        final BGADivider.StickyDelegate stickyDelegate = new BGADivider.StickyDelegate() {
            @Override
            protected boolean isCategoryFistItem(int position) {
                return mGoodsAdapter.isCategoryFistItem(position);
            }

            @Override
            protected String getCategoryName(int position) {
                int categoryId = mGoodsAdapter.getItem(position).categoryId;
                int categoryPosition = mCategoryAdapter.getPositionByCategoryId(categoryId);
                return mCategoryAdapter.getItem(categoryPosition).name;
            }

            @Override
            protected int getFirstVisibleItemPosition() {
                return mGoodsScrollHelper.findFirstVisibleItemPosition();
            }
        };
        mGoodsRv.addItemDecoration(BGADivider.newDrawableDivider(R.drawable.shape_divider)
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level3)
                .setDelegate(stickyDelegate));
        mGoodsScrollHelper = BGARVVerticalScrollHelper.newInstance(mGoodsRv, new BGARVVerticalScrollHelper.SimpleDelegate() {
            @Override
            public int getCategoryHeight() {
                return stickyDelegate.getCategoryHeight();
            }

            @Override
            public void cascade(int position) {
                int categoryId = mGoodsAdapter.getItem(position).categoryId;
                int categoryPosition = mCategoryAdapter.getPositionByCategoryId(categoryId);
                mCategoryAdapter.setCheckedPosition(categoryPosition);

                mCategoryRv.smoothScrollToPosition(mCategoryAdapter.getCheckedPosition());
            }
        });
        mGoodsAdapter = new RvGoodsAdapter(mGoodsRv);
        mGoodsAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                ToastUtil.show("点击了" + mGoodsAdapter.getItem(position).name);
            }
        });
        mGoodsRv.setAdapter(mGoodsAdapter);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        List<CategoryModel> categoryModelList = DataEngine.loadCategoryData();
        List<GoodsModel> goodsModelList = new ArrayList<>();
        for (CategoryModel categoryModel : categoryModelList) {
            goodsModelList.addAll(categoryModel.goodsModelList);
        }
        mCategoryAdapter.setData(categoryModelList);
        mGoodsAdapter.setData(goodsModelList);

    }
}