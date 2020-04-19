package com.dbz.sdk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

public final class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private SparseArray<Fragment> mFragments;

    public MyFragmentPagerAdapter(FragmentManager fm, SparseArray<Fragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
