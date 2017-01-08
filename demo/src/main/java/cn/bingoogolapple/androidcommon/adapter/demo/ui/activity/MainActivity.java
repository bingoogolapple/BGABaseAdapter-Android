package cn.bingoogolapple.androidcommon.adapter.demo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.GvFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.LvFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.LvSuspensionFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.RvBindingFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.RvChatFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.RvFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.RvSuspensionDividerOneFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.RvSuspensionDividerTwoFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.RvSuspensionFragment;

import static cn.bingoogolapple.androidcommon.adapter.demo.R.id.tabLayout;
import static cn.bingoogolapple.androidcommon.adapter.demo.R.id.viewPager;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/28 10:23
 * 描述:
 */
public class MainActivity extends MvcActivity {
    private Class[] mFragmentClasses = new Class[]{GvFragment.class, LvFragment.class, RvFragment.class, RvBindingFragment.class, RvChatFragment.class, LvSuspensionFragment.class, RvSuspensionFragment.class, RvSuspensionDividerOneFragment.class, RvSuspensionDividerTwoFragment.class};
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected int getRootLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = getViewById(R.id.toolbar);
        mTabLayout = getViewById(tabLayout);
        mViewPager = getViewById(viewPager);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);

        ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(contentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class ContentPagerAdapter extends FragmentStatePagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(MainActivity.this, mFragmentClasses[position].getName());
        }

        @Override
        public int getCount() {
            return mFragmentClasses.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentClasses[position].getSimpleName().replace("Fragment", "");
        }
    }

}