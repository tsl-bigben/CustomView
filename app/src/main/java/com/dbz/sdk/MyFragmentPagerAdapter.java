package com.dbz.sdk;

import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
