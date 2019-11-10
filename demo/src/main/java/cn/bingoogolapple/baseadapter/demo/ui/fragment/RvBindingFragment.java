package cn.bingoogolapple.baseadapter.demo.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGABindingRecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGABindingViewHolder;
import cn.bingoogolapple.baseadapter.BGADivider;
import cn.bingoogolapple.baseadapter.BGAOnNoDoubleClickListener;
import cn.bingoogolapple.baseadapter.demo.App;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.databinding.ItemBindingNormalBinding;
import cn.bingoogolapple.baseadapter.demo.engine.ApiEngine;
import cn.bingoogolapple.baseadapter.demo.model.BannerModel;
import cn.bingoogolapple.baseadapter.demo.model.NormalModel;
import cn.bingoogolapple.baseadapter.demo.util.ToastUtil;
import cn.bingoogolapple.bgabanner.BGABanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/28 下午1:30
 * 描述:RecyclerView 结合 DataBinding 使用时简化 RecyclerView 的适配器的编写
 */
public class RvBindingFragment extends MvcFragment {
    private static final String TAG = RvBindingFragment.class.getSimpleName();
    private BGABindingRecyclerViewAdapter<NormalModel, ItemBindingNormalBinding> mAdapter;
    private RecyclerView mDataRv;
    private ItemTouchHelper mItemTouchHelper;
    private BGABanner mBanner;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_rv;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDataRv = getViewById(R.id.rv_data);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 初始化 Adapter
        mAdapter = new BGABindingRecyclerViewAdapter<>(R.layout.item_binding_normal);
        mAdapter.setUiHandler(this);
        mAdapter.setLifecycleOwner(this);

        // 设置分割线
        mDataRv.addItemDecoration(BGADivider.newShapeDivider()
                .setStartSkipCount(1)
                .setEndSkipCount(1)
                .setSizeDp(5)
                .setDelegate(new BGADivider.SimpleDelegate() {
                    @Override
                    public boolean isNeedSkip(int position, int itemCount) {
                        return position == 3;
                    }
                }));


        // 初始化拖拽排序和滑动删除
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mDataRv);


        // 测试 GridLayoutManager
//        mDataRv.setLayoutManager(getGridLayoutManager());
        // 测试 LinearLayoutManager
        mDataRv.setLayoutManager(getLinearLayoutManager());


        // 测试没有 Header 和 Footer 的情况
//        mDataRv.setAdapter(mAdapter);

        // 测试有 Header 或 Footer 的情况
        testHaveHeaderAndFooterAdapter();
    }

    private RecyclerView.LayoutManager getGridLayoutManager() {
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 || position % 2 == 0) ? 1 : layoutManager.getSpanCount();
            }
        });
        return layoutManager;
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
    }

    private void testHaveHeaderAndFooterAdapter() {
        addBannerHeader();

        TextView header1Tv = new TextView(mActivity);
        header1Tv.setBackgroundColor(Color.parseColor("#E15B5A"));
        header1Tv.setTextColor(Color.WHITE);
        header1Tv.setGravity(Gravity.CENTER);
        header1Tv.setPadding(30, 30, 30, 30);
        header1Tv.setText("头部1");
        header1Tv.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ToastUtil.show("点击了头部1");
                List<NormalModel> newData = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    newData.add(new NormalModel("NewTitle" + i, "NewDetail" + i, "https://avatars2.githubusercontent.com/u/8949716?v=3&s=460"));
                }
                mAdapter.addNewData(newData);
            }
        });
        // 当时 LinearLayoutManager 时，需要设置一下布局参数的宽度为填充父窗体，否则 header 和 footer 的宽度会是包裹内容
        header1Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(header1Tv);

        TextView footer1Tv = new TextView(mActivity);
        footer1Tv.setBackgroundColor(Color.parseColor("#6C9FFC"));
        footer1Tv.setTextColor(Color.WHITE);
        footer1Tv.setGravity(Gravity.CENTER);
        footer1Tv.setPadding(30, 30, 30, 30);
        footer1Tv.setText("底部1");
        footer1Tv.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ToastUtil.show("点击了底部1");
                List<NormalModel> moreData = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    moreData.add(new NormalModel("MoreTitle" + i, "MoreDetail" + i, "https://avatars2.githubusercontent.com/u/8949716?v=3&s=460"));
                }
                mAdapter.addMoreData(moreData);
            }
        });
        footer1Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addFooterView(footer1Tv);

        TextView footer2Tv = new TextView(mActivity);
        footer2Tv.setBackgroundColor(Color.parseColor("#51535B"));
        footer2Tv.setTextColor(Color.WHITE);
        footer2Tv.setGravity(Gravity.CENTER);
        footer2Tv.setPadding(50, 50, 50, 50);
        footer2Tv.setText("底部2");
        footer2Tv.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ToastUtil.show("点击了底部2");
            }
        });
        footer2Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addFooterView(footer2Tv);

        // 当有 Header 或者 Footer 时，需要传入 mAdapter.getHeaderAndFooterAdapter()
        mDataRv.setAdapter(mAdapter.getHeaderAndFooterAdapter());
    }

    private void addBannerHeader() {
        // 初始化HeaderView
        View headerView = View.inflate(mActivity, R.layout.layout_header_banner, null);
        mBanner = headerView.findViewById(R.id.banner);
        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(banner.getContext()).load(model).apply(new RequestOptions().placeholder(R.drawable.holder_banner).error(R.drawable.holder_banner).dontAnimate()).thumbnail(0.1f).into(itemView);
            }
        });
        mBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                ToastUtil.show("点击了第" + (position + 1) + "页");
            }
        });
        mAdapter.addHeaderView(headerView);
    }

    /**
     * 加载广告条数据
     */
    private void loadBannerModels() {
        if (mBanner == null) {
            return;
        }

        App.getInstance().getRetrofit().create(ApiEngine.class).loadBannerData("http://bgashare.bingoogolapple.cn/banner/api/4item.json").enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                mBanner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "加载广告条数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载列表数据
     */
    private void loadNormalModels() {
        showLoadingDialog();
        App.getInstance().getRetrofit().create(ApiEngine.class).getNormalModels().enqueue(new Callback<List<NormalModel>>() {
            @Override
            public void onResponse(Call<List<NormalModel>> call, Response<List<NormalModel>> response) {
                dismissLoadingDialog();
                mAdapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<NormalModel>> call, Throwable t) {
                dismissLoadingDialog();
                ToastUtil.show("数据加载失败");
            }
        });
    }

    @Override
    protected void onLazyLoadOnce() {
        loadBannerModels();
        loadNormalModels();
    }

    public void onClickDelete(BGABindingViewHolder holder, NormalModel model) {
        ToastUtil.show("删除了 " + model.title);
        mAdapter.removeItem(holder.getAdapterPositionWrapper());
    }

    public boolean onLongClickDelete(BGABindingViewHolder holder, NormalModel model) {
        ToastUtil.show("长按了删除 " + model.title);
        return true;
    }

    public void onClickItem(BGABindingViewHolder holder, NormalModel model) {
        ToastUtil.show(holder.getAdapterPositionWrapper() + " 点击了条目 " + model.title);
    }

    public boolean onLongClickItem(BGABindingViewHolder holder, NormalModel model) {
        ToastUtil.show("长按了条目 " + model.title);
        return true;
    }

    public boolean onTouchAvatar(BGABindingViewHolder holder, MotionEvent event) {
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            mItemTouchHelper.startDrag(holder);
        }
        return false;
    }

    public void onItemCheckedChanged(NormalModel model, boolean isChecked) {
        // 在填充数据列表时，忽略选中状态变化
        if (!mAdapter.isIgnoreCheckedChanged()) {
            model.selected = isChecked;

            ToastUtil.show((isChecked ? "选中 " : "取消选中") + model.title);
        }
    }

    /**
     * 该类参考：https://github.com/iPaulPro/Android-ItemTouchHelper-Demo
     */
    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
        public static final float ALPHA_FULL = 1.0f;

        @Override
        public boolean isLongPressDragEnabled() {
//            return true;
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

            // 当加了 HeaderAndFooterAdapter 时需要加上下面的判断
            if (mAdapter.isHeaderOrFooter(viewHolder)) {
                dragFlags = swipeFlags = ItemTouchHelper.ACTION_STATE_IDLE;
            }

            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            }

            mAdapter.moveItem(source, target);

            for (NormalModel normalModel : mAdapter.getData()) {
                Log.i(TAG, normalModel.title);
            }

            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.removeItem(viewHolder);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
                ViewCompat.setAlpha(viewHolder.itemView, alpha);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setSelected(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
            viewHolder.itemView.setSelected(false);
        }
    }
}
