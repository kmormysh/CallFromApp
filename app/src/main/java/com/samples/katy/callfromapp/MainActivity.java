package com.samples.katy.callfromapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    public static Activity mainActivity;
    private DatabaseHandler db;
    private SearchView searchView;
    private Menu menu;
    private ContactsListFragment contactsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);

        setContentView(R.layout.activity_main);

//        deleteDatabase("contactManager");

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

        // Associate searchable configuration with the SearchView
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
}
