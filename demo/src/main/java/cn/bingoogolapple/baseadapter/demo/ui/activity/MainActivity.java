package cn.bingoogolapple.baseadapter.demo.ui.activity;

import static cn.bingoogolapple.baseadapter.demo.R.id.tabLayout;
import static cn.bingoogolapple.baseadapter.demo.R.id.viewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.bingoogolapple.baseadapter.demo.R;
import cn.bingoogolapple.baseadapter.demo.ui.fragment.GvFragment;
import cn.bingoogolapple.baseadapter.demo.ui.fragment.LvFragment;
import cn.bingoogolapple.baseadapter.demo.ui.fragment.MvcFragment;
import cn.bingoogolapple.baseadapter.demo.ui.fragment.RvBindingFragment;
import cn.bingoogolapple.baseadapter.demo.ui.fragment.RvCascadeFragment;
import cn.bingoogolapple.baseadapter.demo.ui.fragment.RvChatFragment;
import cn.bingoogolapple.baseadapter.demo.ui.fragment.RvFragment;
import cn.bingoogolapple.baseadapter.demo.ui.fragment.RvStickyFragment;

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
                new RvCascadeFragment(),
                new RvStickyFragment(),
                new RvFragment(),
                new RvChatFragment(),
                new RvBindingFragment(),
                new LvFragment(),
                new GvFragment()
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