:running:BGABaseAdapter-Android:running:
============

## 目录
* [功能介绍](#功能介绍)
* [效果图与示例 apk](#效果图与示例-apk)
* [使用](#使用)
* [感谢](#感谢)
* [关于我](#关于我)
* [打赏支持](#打赏支持)

## 功能介绍
在 AdapterView 和 RecyclerView 中通用的 Adapter 和 ViewHolder。

- [x] BGAAdapterViewAdapter 和 BGAViewHolderHelper 用于简化 AdapterView 的子类（如 ListView、GridView）的适配器的编写
- [x] BGARecyclerViewAdapter 和 BGAViewHolderHelper 用于简化 RecyclerView 的适配器的编写，支持多 Item 类型，支持添加多个 Header 和 Footer，回调接口里的索引位置已经在库里处理了，不需要开发者自己减去 Header 个数
- [x] BGADivider 用于简化 RecyclerView 分割线的编写，以及轻松实现基于 RecyclerView 的悬浮分类索引
- [x] BGABindingRecyclerViewAdapter 和 BGABindingViewHolder 用于 RecyclerView 结合 DataBinding 使用时简化 RecyclerView 的适配器的编写，支持多 Item 类型，支持添加多个 Header 和 Footer，回调接口里的索引位置已经在库里处理了，不需要开发者自己减去 Header 个数

## 效果图与示例 apk
![bga_adapter1](https://cloud.githubusercontent.com/assets/8949716/17476073/bf819e04-5d90-11e6-9c21-193ba3c426ed.gif)
![bga_adapter2](https://cloud.githubusercontent.com/assets/8949716/17476074/bfb571ca-5d90-11e6-95b3-c5c345377a8d.gif)
![bgaadapter-recyclerview](https://cloud.githubusercontent.com/assets/8949716/20648733/0b405938-b4ea-11e6-9a37-9fad31f72ec8.gif)
![bgaadapter-recyclerviewbinding](https://cloud.githubusercontent.com/assets/8949716/20648734/0b816fd6-b4ea-11e6-9716-e869f7af0564.gif)
![bga_adapter4](https://cloud.githubusercontent.com/assets/8949716/17476076/bfef7082-5d90-11e6-9403-ba526a10d58f.gif)

[点击下载 BGABaseAdapterDemo.apk](http://fir.im/BGABaseAdapterDemo) 或扫描下面的二维码安装

![BGABaseAdapterDemo apk文件二维码](https://user-images.githubusercontent.com/8949716/31925781-946d8cfc-b850-11e7-9a75-2edda1033a3d.png)

## 使用

### Gradle 依赖

[![Download](https://api.bintray.com/packages/bingoogolapple/maven/bga-baseadapter/images/download.svg)](https://bintray.com/bingoogolapple/maven/bga-baseadapter/_latestVersion) bga-baseadapter 后面的「latestVersion」指的是左边这个 Download 徽章后面的「数字」，请自行替换。

```groovy
dependencies {
    compile 'com.android.support:recyclerview-v7:latestVersion'
    compile 'cn.bingoogolapple:bga-baseadapter:latestVersion@aar'
}
```

### 简化 AdapterView 的子类（如 ListView、GridView）的适配器的编写

[NormalAdapterViewAdapter.java](https://github.com/bingoogolapple/BGABaseAdapter-Android/tree/master/demo/src/main/java/cn/bingoogolapple/baseadapter/demo/adapter/NormalAdapterViewAdapter.java)

### 简化 RecyclerView 的适配器的编写

[NormalRecyclerViewAdapter.java](https://github.com/bingoogolapple/BGABaseAdapter-Android/tree/master/demo/src/main/java/cn/bingoogolapple/baseadapter/demo/adapter/NormalRecyclerViewAdapter.java)

### RecyclerView 结合 DataBinding 使用时简化 RecyclerView 的适配器的编写

[RvBindingFragment.java](https://github.com/bingoogolapple/BGABaseAdapter-Android/tree/master/demo/src/main/java/cn/bingoogolapple/baseadapter/demo/ui/fragment/RvBindingFragment.java)

### BGADivider 用于简化 RecyclerView 分割线的编写，以及轻松实现基于 RecyclerView 的悬浮分类索引

[RvSuspensionDividerOneFragment.java](https://github.com/bingoogolapple/BGABaseAdapter-Android/tree/master/demo/src/main/java/cn/bingoogolapple/baseadapter/demo/ui/fragment/RvSuspensionDividerOneFragment.java)

### 代码是最好的老师，详细用法请查看 [demo](https://github.com/bingoogolapple/BGABaseAdapter-Android/tree/master/demo):feet:

## 感谢

* [https://github.com/hongyangAndroid/baseAdapter](https://github.com/hongyangAndroid/baseAdapter)
参考该库的为 RecyclerView 添加 Header 和 Footer
* [https://github.com/iPaulPro/Android-ItemTouchHelper-Demo](https://github.com/iPaulPro/Android-ItemTouchHelper-Demo)
参考该库的拖拽排序功能

## 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

## 打赏支持

如果觉得 BGA 系列开源库对您有用，请随意打赏。如果猿友有打算购买 [Lantern](https://github.com/getlantern/forum)，可以使用我的邀请码「YFQ9Q3B」购买，双方都赠送三个月的专业版使用时间。

<p align="center">
  <img src="http://7xk9dj.com1.z0.glb.clouddn.com/bga_pay.png" width="450">
</p>

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
