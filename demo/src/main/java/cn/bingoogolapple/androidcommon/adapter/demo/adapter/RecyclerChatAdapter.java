package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.model.ChatModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class RecyclerChatAdapter extends BGARecyclerViewAdapter<ChatModel> {

    public RecyclerChatAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.iv_item_chat_me_status);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).mUserType == ChatModel.UserType.Other) {
            return R.layout.item_chat_other;
        } else {
            return R.layout.item_chat_me;
        }
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, ChatModel model) {
        if (model.mUserType == ChatModel.UserType.Other) {
            helper.setHtml(R.id.tv_item_chat_other_msg, String.format(mContext.getString(R.string.color_msg_from), model.mMsg));
        } else {
            helper.setHtml(R.id.tv_item_chat_me_msg, String.format(mContext.getString(R.string.color_msg_to), model.mMsg));
            helper.setVisibility(R.id.iv_item_chat_me_status, model.mSendStatus == ChatModel.SendStatus.Failure ? View.VISIBLE : View.GONE);
        }
    }

}