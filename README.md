:running:BGAAdapter-Android v1.0.2:running:
============
在AdapterView和RecyclerView中通用的Adapter和ViewHolder，使AdapterView和RecyclerView适配器的使用方式基本一致。

#### 效果图
![Image of 基本使用](https://raw.githubusercontent.com/bingoogolapple/BGAAdapter-Android/master/screenshots/1-normal.gif)
![Image of 城市列表索引](https://raw.githubusercontent.com/bingoogolapple/BGAAdapter-Android/master/screenshots/2-index.gif)
![Image of 聊天布局](https://raw.githubusercontent.com/bingoogolapple/BGAAdapter-Android/master/screenshots/3-chat.gif)

>Gradle

```groovy
dependencies {
    compile 'cn.bingoogolapple:bga-adapter:1.0.0@aar'
}
```

##### 使用非常简单，这里展示一下ListView和RecyclerView实现qq聊天界面的适配器

> ListView实现qq聊天界面的适配器

```Java
public class ListChatAdapter extends BGAAdapterViewAdapter<ChatModel> {

    public ListChatAdapter(Context context) {
        super(context, R.layout.item_chat);
    }

    /**
     * 为item的孩子节点设置监听器，并不是每一个数据列表都要为item的子控件添加事件监听器，所以在父类中采用了空实现，需要设置事件监听器时重写该方法即可
     *
     * @param viewHolderHelper
     */
    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ChatModel model) {
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
```

> RecyclerView实现qq聊天界面的适配器

```Java
public class RecyclerChatAdapter extends BGARecyclerViewAdapter<ChatModel> {
    public RecyclerChatAdapter(Context context) {
        super(context, R.layout.item_chat);
    }

    /**
     * 为item的孩子节点设置监听器，并不是每一个数据列表都要为item的子控件添加事件监听器，所以在父类中采用了空实现，需要设置事件监听器时重写该方法即可
     *
     * @param viewHolderHelper
     */
    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ChatModel model) {
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
```

##### 详细用法请查看[Demo](https://github.com/bingoogolapple/BGAAdapter-Android/tree/master/demo):feet:

## License

    Copyright 2015 bingoogolapple

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
