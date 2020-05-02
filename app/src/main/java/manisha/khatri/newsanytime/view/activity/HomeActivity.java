package manisha.khatri.newsanytime.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.util._enum.Fragments;
import manisha.khatri.newsanytime.util._enum.GenericStrings;
import manisha.khatri.newsanytime.view.adapter.*;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setTabs();
        onBookmarkNewsListBtnClick();
    }

    public void callSearchNewsActivity(String searchedKeyword){
        Intent intent = new Intent(HomeActivity.this, SearchNewsNewsActivity.class);
        intent.putExtra(GenericStrings.SEARCHED_KEYWORD.toString(),searchedKeyword);
        startActivity(intent);
    }

    private void initViews() {
        setToolbar();
        setupSearchView();
    }

    private void setToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_home));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void onBookmarkNewsListBtnClick() {
        findViewById(R.id.bookmark_news_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SavedNewsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTabs() {
        TabLayout tabLayout =  findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText(Fragments.NATIONAL.toString()));
        tabLayout.addTab(tabLayout.newTab().setText(Fragments.TOP_STORIES.toString()));
        tabLayout.addTab(tabLayout.newTab().setText(Fragments.INTERNATIONAL.toString()));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        int limit = (tabsAdapter.getCount() > 1 ? tabsAdapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                findViewById(R.id.app_title).setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.app_title).setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearchNewsActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
