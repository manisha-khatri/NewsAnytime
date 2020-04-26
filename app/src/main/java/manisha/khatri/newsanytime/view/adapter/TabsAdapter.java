package manisha.khatri.newsanytime.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import manisha.khatri.newsanytime.util._enum.Country;
import manisha.khatri.newsanytime.view.fragment.NewsFragment;
import manisha.khatri.newsanytime.view.fragment.TopStoriesNewsFragment;

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
            case 0:  return NewsFragment.getNewsInstance(Country.INDIA.toString());

            case 1: return new TopStoriesNewsFragment();

            case 2: return NewsFragment.getNewsInstance(Country.ALL.toString());

            default: return null;
        }
    }
}