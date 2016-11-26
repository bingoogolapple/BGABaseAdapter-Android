package cn.bingoogolapple.androidcommon.adapter.demo.model;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/28 14:39
 * 描述:
 */
public class ChatModel {
    public String mMsg;
    public SendStatus mSendStatus;
    public UserType mUserType;
    public ChatModel(String msg, UserType userType, SendStatus sendStatus) {
        mMsg = msg;
        mUserType = userType;
        mSendStatus = sendStatus;
    }

    public enum UserType {
        Other, Me
    }

    public enum SendStatus {
        Success, Failure
    }
}