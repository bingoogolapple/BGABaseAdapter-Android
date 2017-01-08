package cn.bingoogolapple.androidcommon.adapter.demo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.GvFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.LvFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.LvSuspensionFragment;
import cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment.MvcFragment;
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

    private class ContentPagerAdapter extends FragmentPagerAdapter {

        private List<MvcFragment> mFragmentList = Arrays.asList(
                new GvFragment(),
                new LvFragment(),
                new RvFragment(),
                new RvBindingFragment(),
                new RvChatFragment(),
                new LvSuspensionFragment(),
                new RvSuspensionFragment(),
                new RvSuspensionDividerOneFragment(),
                new RvSuspensionDividerTwoFragment()
        );

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentList.get(position).getClass().getSimpleName().replace("Fragment", "");
        }
    }

}