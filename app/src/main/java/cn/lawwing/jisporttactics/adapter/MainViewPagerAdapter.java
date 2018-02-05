package cn.lawwing.jisporttactics.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.lawwing.jisporttactics.beans.MainMenuBean;

/**
 * Created by lawwing on 2018/2/5.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private ArrayList<MainMenuBean> names;

    public MainViewPagerAdapter(FragmentManager fm, ArrayList<MainMenuBean> names, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.names = names;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return names != null ? names.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position).getName();
    }
}
