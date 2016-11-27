package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGABindingRecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAOnNoDoubleClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.App;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.databinding.ItemBindingNormalBinding;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.ApiEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.model.NormalModel;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.widget.Divider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/28 下午1:30
 * 描述:
 */
public class RecyclerViewBindingDemoFragment extends BaseFragment {
    private static final String TAG = RecyclerViewBindingDemoFragment.class.getSimpleName();
    private BGABindingRecyclerViewAdapter<NormalModel, ItemBindingNormalBinding> mAdapter;
    private RecyclerView mDataRv;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerview);
        mDataRv = getViewById(R.id.rv_recyclerview_data);
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

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // 初始化 Adapter
        mAdapter = new BGABindingRecyclerViewAdapter<>(R.layout.item_binding_normal);
        mAdapter.setItemEventHandler(this);

        // 设置分割线
        mDataRv.addItemDecoration(new Divider(mActivity));


        // 初始化拖拽排序和滑动删除
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mDataRv);


        // 测试 GridLayoutManager
        mDataRv.setLayoutManager(getGridLayoutManager());
        // 测试 LinearLayoutManager
//        mDataRv.setLayoutManager(getLinearLayoutManager());


        // 测试没有 Header 和 Footer 的情况
//        mDataRv.setAdapter(mAdapter);

        // 测试有 Header 或 Footer 的情况
        testHaveHeaderAndFooterAdapter();
    }

    private void testHaveHeaderAndFooterAdapter() {
        TextView header1Tv = new TextView(mActivity);
        header1Tv.setBackgroundColor(Color.parseColor("#E15B5A"));
        header1Tv.setTextColor(Color.WHITE);
        header1Tv.setGravity(Gravity.CENTER);
        header1Tv.setPadding(30, 30, 30, 30);
        header1Tv.setText("头部1");
        header1Tv.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSnackbar("点击了头部1");
            }
        });
        // 当时 LinearLayoutManager 时，需要设置一下布局参数的宽度为填充父窗体，否则 header 和 footer 的宽度会是包裹内容
        header1Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(header1Tv);

        TextView header2Tv = new TextView(mActivity);
        header2Tv.setBackgroundColor(Color.parseColor("#71CE7E"));
        header2Tv.setTextColor(Color.WHITE);
        header2Tv.setGravity(Gravity.CENTER);
        header2Tv.setPadding(50, 50, 50, 50);
        header2Tv.setText("头部2");
        header2Tv.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSnackbar("点击了头部2");
            }
        });
        header2Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(header2Tv);

        TextView footer1Tv = new TextView(mActivity);
        footer1Tv.setBackgroundColor(Color.parseColor("#6C9FFC"));
        footer1Tv.setTextColor(Color.WHITE);
        footer1Tv.setGravity(Gravity.CENTER);
        footer1Tv.setPadding(30, 30, 30, 30);
        footer1Tv.setText("底部1");
        footer1Tv.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSnackbar("点击了底部1");
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
                showSnackbar("点击了底部2");
            }
        });
        footer2Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addFooterView(footer2Tv);

        // 当有 Header 或者 Footer 时，需要传入 mAdapter.getHeaderAndFooterAdapter()
        mDataRv.setAdapter(mAdapter.getHeaderAndFooterAdapter());
    }

    @Override
    protected void onUserVisible() {
        App.getInstance().getRetrofit().create(ApiEngine.class).getNormalModels().enqueue(new Callback<List<NormalModel>>() {
            @Override
            public void onResponse(Call<List<NormalModel>> call, Response<List<NormalModel>> response) {
                mAdapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<NormalModel>> call, Throwable t) {
                showSnackbar("数据加载失败");
            }
        });
    }

    public void onClickDelete(BGABindingViewHolder holder, NormalModel model) {
        showSnackbar("删除了 " + model.title);
        mAdapter.removeItem(holder.getAdapterPositionWrapper());
    }

    public boolean onLongClickDelete(BGABindingViewHolder holder, NormalModel model) {
        showSnackbar("长按了删除 " + model.title);
        return true;
    }

    public void onClickItem(BGABindingViewHolder holder, NormalModel model) {
        showSnackbar("点击了条目 " + model.title);
    }

    public boolean onLongClickItem(BGABindingViewHolder holder, NormalModel model) {
        showSnackbar("长按了条目 " + model.title);
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

            showSnackbar((isChecked ? "选中 " : "取消选中") + model.title);
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
