## ----------------------------------
##      DataBinding 相关
## ----------------------------------
-keepclasseswithmembers class * extends androidx.databinding.ViewDataBinding {
    <methods>;
}

-dontwarn androidx.databinding.DataBindingUtil
-dontwarn androidx.databinding.ViewDataBinding

-dontwarn cn.bingoogolapple.baseadapter.BGABindingRecyclerViewAdapter
-dontwarn cn.bingoogolapple.baseadapter.BGABindingViewHolder
-dontwarn cn.bingoogolapple.baseadapter.BGAViewBindingAdapter