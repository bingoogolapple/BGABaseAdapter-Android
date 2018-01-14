## ----------------------------------
##      DataBinding 相关
## ----------------------------------
-keepclasseswithmembers class * extends android.databinding.ViewDataBinding{
    <methods>;
}

-dontwarn android.databinding.DataBindingUtil
-dontwarn android.databinding.ViewDataBinding

-dontwarn cn.bingoogolapple.baseadapter.BGABindingRecyclerViewAdapter
-dontwarn cn.bingoogolapple.baseadapter.BGABindingViewHolder
-dontwarn cn.bingoogolapple.baseadapter.BGAViewBindingAdapter