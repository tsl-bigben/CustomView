package com.dbz.sdk;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbz.view.indicator.SmartTabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private MyFragmentPagerAdapter mPagerAdapter;
    private SparseArray<Fragment> fragments = new SparseArray<>();
    private SmartTabLayout mOrderTabLayout;
    private ViewPager mViewPager;
    private SparseArray<Integer> imgs = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOrderTabLayout = findViewById(R.id.smart_layout);
        mViewPager = findViewById(R.id.viewPager_main);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.order_tab_img);
        for (int i = 0; i < typedArray.length(); i++){
            imgs.put(i, typedArray.getResourceId(i, 0));
        }
        typedArray.recycle();
        String[] orders = getResources().getStringArray(R.array.order_tab_title);
        fragments.put(0, new MyFragment());
        fragments.put(1, new MyFragment());
        fragments.put(2, new MyFragment());
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, orders);
        mViewPager.setAdapter(mPagerAdapter);
        mOrderTabLayout.setViewPager(mViewPager, new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View tabView = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, container, false);
                tabView.findViewById(R.id.iv_icon).setBackgroundResource(imgs.get(position));
                return tabView;
            }

            @Override
            public void selectedTab(View view, boolean flag) {
                view.findViewById(R.id.custom_text).setSelected(flag);
            }
        });
    }
}
