package cn.bingoogolapple.baseadapter.demo.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.model.ChatModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:RecyclerView 多 item 类型适配器
 */
public class RvChatAdapter extends BGARecyclerViewAdapter<ChatModel> {

    public RvChatAdapter(RecyclerView recyclerView) {
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