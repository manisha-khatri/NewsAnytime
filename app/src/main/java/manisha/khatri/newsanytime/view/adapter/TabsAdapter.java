package manisha.khatri.newsanytime.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import manisha.khatri.newsanytime.util._enum.GenericStrings;
import manisha.khatri.newsanytime.util._enum.NewsType;
import manisha.khatri.newsanytime.view.fragment.NewsFragment;
import manisha.khatri.newsanytime.view.fragment.TopStoriesNewsFragment;


public class TabsAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    NewsFragment nationalNewsFragment;
    TopStoriesNewsFragment topStoriesNewsFragment;
    NewsFragment internationalNewsFragment;

    public TabsAdapter(FragmentManager fragmentManager, int tabCount){
        super(fragmentManager);
        this.tabCount = tabCount;
        setNationalNewsFragment();
        setInternationalNewsFragment();
        setTopStoriesNewsFragment();
    }

    void setNationalNewsFragment(){
        nationalNewsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GenericStrings.NEWS_TYPE.toString(), NewsType.NATIONAL.toString());
        nationalNewsFragment.setArguments(bundle);
    }

    void setInternationalNewsFragment(){
        internationalNewsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GenericStrings.NEWS_TYPE.toString(), NewsType.INTERNATIONAL.toString());
        internationalNewsFragment.setArguments(bundle);
    }

    void setTopStoriesNewsFragment(){
        topStoriesNewsFragment = new TopStoriesNewsFragment();
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int position){

        switch (position){
            case 0:  return nationalNewsFragment;

            case 1: return topStoriesNewsFragment;

            case 2: return internationalNewsFragment;

            default: return null;
        }
    }
}