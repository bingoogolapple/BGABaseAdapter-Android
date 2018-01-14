## ----------------------------------
##      DataBinding 相关
## ----------------------------------
-keepclasseswithmembers class * extends android.databinding.ViewDataBinding{
    <methods>;
}

-dontwarn cn.bingoogolapple.baseadapter.BGABindingRecyclerViewAdapter
-dontwarn cn.bingoogolapple.baseadapter.BGABindingViewHolder
-dontwarn cn.bingoogolapple.baseadapter.BGAViewBindingAdapter