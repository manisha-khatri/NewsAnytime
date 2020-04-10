package manisha.khatri.newsanytime.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.view.adapter.*;

public class HomeActivity extends AppCompatActivity {
    SearchView searchView;
    TextView appTitle;
    Button bkmrkBtn;
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setTabs();
        onBookmarkNewsListBtnClick();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar_home);
        appTitle = findViewById(R.id.app_title);
        searchView = findViewById(R.id.search_view);
        bkmrkBtn =  findViewById(R.id.bookmark_news_list);
        tabLayout =  findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        setToolbar();
        setupSearchView();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void onBookmarkNewsListBtnClick() {
        bkmrkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SavedNewsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("National"));
        tabLayout.addTab(tabLayout.newTab().setText("Top Stories"));
        tabLayout.addTab(tabLayout.newTab().setText("International"));

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

    public void callSearchNewsActivity(String searchedKeyword){
        Intent intent = new Intent(HomeActivity.this, SearchNewsNewsActivity.class);
        intent.putExtra("SEARCHED_KEYWORD",searchedKeyword);
        startActivity(intent);
    }

    private void setupSearchView() {
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                appTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View view) {
                appTitle.setVisibility(View.GONE);
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
