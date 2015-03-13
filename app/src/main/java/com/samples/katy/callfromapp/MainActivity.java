package com.samples.katy.callfromapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static Activity mainActivity;
    private SearchView searchView;
    private Menu menu;
    private ContactsListFragment contactsListFragment;
    private RecentCallsListFragment recentCallsListFragment;
    private NavigationDrawerFragment navigationDrawerFragment;
    private CharSequence lastScreenTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        setContentView(R.layout.activity_main);
//        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);

        setContentView(R.layout.activity_main);

        navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        lastScreenTitle = getTitle();

        // Set up the drawer.
        navigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

//        deleteDatabase("contactManager");
//        deleteDatabase("callsManager");

        mainActivity = this;
        contactsListFragment = new ContactsListFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, contactsListFragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearch();
                return true;

            case R.id.action_refresh:
                onRefresh();
                return true;

            case R.id.action_help:
                onHelp();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void onHelp() {

    }

    private void onRefresh() {

    }

    private void onSearch() {


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        contactsListFragment.filterContacts(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        contactsListFragment.filterContacts(newText);
        return true;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (position == 0 && contactsListFragment != null) {
            if (recentCallsListFragment != null) {
                getSupportFragmentManager().beginTransaction().hide(recentCallsListFragment).commit();
            }

            getSupportFragmentManager().beginTransaction().show(contactsListFragment).commit();
        }

        if (position == 1) {
            if (recentCallsListFragment != null) {
                getSupportFragmentManager().beginTransaction().show(recentCallsListFragment).commit();
            }
            else {
                recentCallsListFragment = new RecentCallsListFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, recentCallsListFragment)
                        .commit();
            }
            getSupportFragmentManager().beginTransaction().hide(contactsListFragment).commit();

        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                lastScreenTitle = getString(R.string.app_name);
                break;
            case 2:
                lastScreenTitle = getString(R.string.title_section2);
                break;
            case 3:
                lastScreenTitle = getString(R.string.title_section3);
                break;
        }
    }
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(lastScreenTitle);
    }
}
