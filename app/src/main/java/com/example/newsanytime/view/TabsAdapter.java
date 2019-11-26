package com.example.newsanytime.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public TabsAdapter(FragmentManager fragmentManager, int tabCount){
        super(fragmentManager);
        this.tabCount = tabCount;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int position){

        switch (position){
            case 0: NationalNewsFragment nationalNewsFragment = new NationalNewsFragment();
                    return nationalNewsFragment;

            case 1: TopStoriesFragment topStoriesFragment = new TopStoriesFragment();
                    return topStoriesFragment;

            case 2: InternationalFragment internationalFragment = new InternationalFragment();
                    return internationalFragment;

            default: return null;
        }
    }
}