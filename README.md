:running:BGAAdapter-Android:running:
============

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-adapter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-adapter)

在AdapterView和RecyclerView中通用的Adapter和ViewHolder，使AdapterView和RecyclerView适配器的使用方式基本一致。
其中拖拽排序是参考的[Android-ItemTouchHelper-Demo](https://github.com/iPaulPro/Android-ItemTouchHelper-Demo)

#### 效果图
![Image of 聊天布局](http://7xk9dj.com1.z0.glb.clouddn.com/adapter/screenshots/bga_adapter1.gif)
![Image of 城市列表索引](http://7xk9dj.com1.z0.glb.clouddn.com/adapter/screenshots/bga_adapter2.gif)
![Image of 滑动删除+拖拽排序](http://7xk9dj.com1.z0.glb.clouddn.com/adapter/screenshots/bga_adapter3.gif)
![Image of 基本使用](http://7xk9dj.com1.z0.glb.clouddn.com/adapter/screenshots/bga_adapter4.gif)

>Gradle

```groovy
dependencies {
    compile 'com.android.support:recyclerview-v7:latestVersion'
    compile 'cn.bingoogolapple:bga-adapter:latestVersion@aar'
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
    public RecyclerChatAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_chat);
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

### 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群 |
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

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
