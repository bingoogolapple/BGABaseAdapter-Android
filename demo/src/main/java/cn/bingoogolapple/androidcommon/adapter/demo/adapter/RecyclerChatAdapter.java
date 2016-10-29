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
        super(recyclerView, R.layout.item_chat);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper helper) {
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, ChatModel model) {
        if (model.mUserType == ChatModel.UserType.From) {
            helper.setVisibility(R.id.rl_item_chat_to, View.GONE);
            helper.setVisibility(R.id.rl_item_chat_from, View.VISIBLE);
            String htmlMsg = String.format(mContext.getString(R.string.color_msg_from), model.mMsg);
            helper.setHtml(R.id.tv_item_chat_from_msg, htmlMsg);
        } else {
            helper.setVisibility(R.id.rl_item_chat_from, View.GONE);
            helper.setVisibility(R.id.rl_item_chat_to, View.VISIBLE);
            String htmlMsg = String.format(mContext.getString(R.string.color_msg_to), model.mMsg);
            helper.setHtml(R.id.tv_item_chat_to_msg, htmlMsg);
        }
    }

}