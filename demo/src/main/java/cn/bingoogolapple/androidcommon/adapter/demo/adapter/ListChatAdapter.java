package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.content.Context;
import android.view.View;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.model.ChatModel;

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
    protected void setItemChildListener(BGAViewHolderHelper helper) {
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, ChatModel model) {
        if (model.mUserType == ChatModel.UserType.Other) {
            helper.setVisibility(R.id.rl_item_chat_me, View.GONE);
            helper.setVisibility(R.id.rl_item_chat_from, View.VISIBLE);
            String htmlMsg = String.format(mContext.getString(R.string.color_msg_from), model.mMsg);
            helper.setHtml(R.id.tv_item_chat_other_msg, htmlMsg);
        } else {
            helper.setVisibility(R.id.rl_item_chat_from, View.GONE);
            helper.setVisibility(R.id.rl_item_chat_me, View.VISIBLE);
            String htmlMsg = String.format(mContext.getString(R.string.color_msg_to), model.mMsg);
            helper.setHtml(R.id.tv_item_chat_me_msg, htmlMsg);
        }
    }

}