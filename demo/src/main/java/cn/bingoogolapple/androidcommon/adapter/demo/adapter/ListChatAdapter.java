package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.content.Context;
import android.view.View;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.ChatModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 上午12:39
 * 描述:
 */
public class ListChatAdapter extends BGAAdapterViewAdapter<ChatModel> {

    public ListChatAdapter(Context context) {
        super(context, R.layout.item_chat);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, ChatModel model, int position) {
        if (model.mUserType == ChatModel.UserType.From) {
            viewHolderHelper.setVisibility(R.id.rl_item_chat_to, View.GONE);
            viewHolderHelper.setVisibility(R.id.rl_item_chat_from, View.VISIBLE);
            String htmlMsg = String.format(mContext.getString(R.string.color_msg_from), model.mMsg);
            viewHolderHelper.setHtml(R.id.tv_item_chat_from_msg, htmlMsg);
        } else {
            viewHolderHelper.setVisibility(R.id.rl_item_chat_from, View.GONE);
            viewHolderHelper.setVisibility(R.id.rl_item_chat_to, View.VISIBLE);
            String htmlMsg = String.format(mContext.getString(R.string.color_msg_to), model.mMsg);
            viewHolderHelper.setHtml(R.id.tv_item_chat_to_msg, htmlMsg);
        }
    }

}