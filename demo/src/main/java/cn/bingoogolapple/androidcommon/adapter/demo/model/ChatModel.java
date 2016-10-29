package cn.bingoogolapple.androidcommon.adapter.demo.model;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/28 14:39
 * 描述:
 */
public class ChatModel {
    public String mMsg;
    public UserType mUserType;
    public ChatModel(String msg, UserType userType) {
        mMsg = msg;
        mUserType = userType;
    }

    public enum UserType {
        From, To
    }
}