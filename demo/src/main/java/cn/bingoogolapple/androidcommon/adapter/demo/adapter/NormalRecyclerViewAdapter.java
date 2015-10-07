package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.net.Uri;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.NormalModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class NormalRecyclerViewAdapter extends BGARecyclerViewAdapter<NormalModel> {
    private ItemTouchHelper mItemTouchHelper;

    public NormalRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_normal);
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        mItemTouchHelper = itemTouchHelper;
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_delete);
        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
        viewHolderHelper.setItemChildCheckedChangeListener(R.id.cb_item_normal_status);
        viewHolderHelper.getView(R.id.sdv_item_normal_avator).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mItemTouchHelper.startDrag(viewHolderHelper.getRecyclerViewHolder());
                }
                return false;
            }
        });
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, NormalModel model) {
        viewHolderHelper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);
        SimpleDraweeView avatorSdv = viewHolderHelper.getView(R.id.sdv_item_normal_avator);
        avatorSdv.setImageURI(Uri.parse(model.avatorPath));
    }
}