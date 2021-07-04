package com.example.myfolk.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myfolk.activity.MainActivity;
import com.example.myfolk.fragment.ClassifyFragment;
import com.example.myfolk.fragment.RecommendFragment;
import com.example.myfolk.fragment.SearchFragment;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;

    private RecommendFragment recommendFragment;
    private ClassifyFragment classifyFragment;
    private SearchFragment searchFragment;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        recommendFragment = new RecommendFragment();
        classifyFragment = new ClassifyFragment();
        searchFragment = new SearchFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = recommendFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = classifyFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = searchFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}
