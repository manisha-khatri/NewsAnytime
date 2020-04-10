package manisha.khatri.newsanytime.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import manisha.khatri.newsanytime.view.fragment.InternationalFragment;
import manisha.khatri.newsanytime.view.fragment.NationalNewsFragment;
import manisha.khatri.newsanytime.view.fragment.TopStoriesFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    NationalNewsFragment nationalNewsFragment;
    TopStoriesFragment topStoriesFragment;
    InternationalFragment internationalFragment;

    public TabsAdapter(FragmentManager fragmentManager, int tabCount){
        super(fragmentManager);
        this.tabCount = tabCount;
        nationalNewsFragment = new NationalNewsFragment();
        topStoriesFragment = new TopStoriesFragment();
        internationalFragment = new InternationalFragment();
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int position){

        switch (position){
            case 0:  return nationalNewsFragment;

            case 1: return topStoriesFragment;

            case 2: return internationalFragment;

            default: return null;
        }
    }
}