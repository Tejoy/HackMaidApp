package com.hack.xapp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.hack.xapp.R;
import com.hack.xapp.adapter.MaidsListAdapter;
import com.hack.xapp.fragment.NavigationDrawerFragment;
import com.hack.xapp.util.dummy.DummyContent;
import com.hack.xapp.model.Maid;
import com.hack.xapp.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    TextView tv;
    MaidsListAdapter rvAdapter;
    RecyclerView rv;
    DrawerLayout myDrawer;
    RelativeLayout rightDrawer;
    ListView leftDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    String LEFT_DRAWER_TAG = "left";
    String RIGHT_DRAWER_TAG = "right";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maids_list_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.left_drawer);
        actionBar.setHomeButtonEnabled(true);

        tv = (TextView) findViewById(R.id.no_maids_message);
        rv = (RecyclerView) findViewById(R.id.maids_list_fragment);
        myDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        rightDrawer = (RelativeLayout) findViewById(R.id.right_drawer);
        leftDrawer = (ListView) findViewById(R.id.left_drawer);

        List<String> leftitems = new ArrayList<String>();
        leftitems.add(Util.LEFT_DRAWER_MAIN_LOGIN);
        leftitems.add(Util.LEFT_DRAWER_MAIN_REGISTER);
        leftitems.add(Util.LEFT_DRAWER_MAIN_SETTINGS);
        leftitems.add(Util.LEFT_DRAWER_MAIN_HISTORY);
        leftitems.add(Util.LEFT_DRAWER_MAIN_UNREGISTER);

        leftDrawer.setOnItemClickListener(new LeftDrawerClickListner());

 /*       List<String> rightitems = new ArrayList<String>();
        rightitems.add("Ravi");
        rightitems.add("Tejo");
        rightitems.add("Rajjo");

        ArrayAdapter rightadpater = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1,rightitems);

        rightDrawer.setAdapter(rightadpater);*/

        //populateRightDrawer();

        ArrayAdapter leftadpater = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, leftitems);

        leftDrawer.setAdapter(leftadpater);

        leftDrawer.setTag(LEFT_DRAWER_TAG);
        rightDrawer.setTag(RIGHT_DRAWER_TAG);

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);
        rvAdapter = new MaidsListAdapter(getBaseContext(), DummyContent.MAID_ITEMS);
        rv.setAdapter(rvAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                    /* host Activity */
                myDrawer,                    /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                String tag = drawerView.getTag().toString();
                Log.i(TAG, "onDrawerClosed " + tag);
                supportInvalidateOptionsMenu();


            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                String tag = drawerView.getTag().toString();
                Log.i(TAG, "onDrawerOpened " + tag);

                supportInvalidateOptionsMenu();
            }
        };

        myDrawer.setDrawerListener(mDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                //dataAdapter.getFilter().filter(newText);
                Log.i(TAG, "on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                //dataAdapter.getFilter().filter(query);
                Log.i(TAG, "on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.i(TAG, "lalala " + id);

        //Log.i(TAG,"lalala tt "+android.R.id.homeAsUp);

        Log.i(TAG, "lalala hh " + android.R.id.home);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            if (myDrawer.isDrawerOpen(rightDrawer)) {
                myDrawer.closeDrawer(rightDrawer);
            } else {
                myDrawer.openDrawer(rightDrawer);
                if (myDrawer.isDrawerOpen(leftDrawer)) {
                    myDrawer.closeDrawer(leftDrawer);
                }
            }
            return true;
        } else if (id == android.R.id.home) {
            if (myDrawer.isDrawerOpen(leftDrawer)) {
                myDrawer.closeDrawer(leftDrawer);
            } else {
                myDrawer.openDrawer(leftDrawer);
                if (myDrawer.isDrawerOpen(rightDrawer)) {
                    myDrawer.closeDrawer(rightDrawer);
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    public class MainResponseObserver implements Observer {
        @Override
        public void update(Observable observable, Object data) {

        }


        public void maidsList(List<Maid> list) {

        }

        public void onError(String msg) {

        }

    }

    ;

    public class LeftDrawerClickListner implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "onItemClick " + position);
            Intent a;
            switch (position) {
                case 0:
                    //Login case
                    a = new Intent(getBaseContext(), Login.class);
                    startActivity(a);
                    break;

                case 1:
                    //Register case
                    a = new Intent(getBaseContext(), RegisterMaid.class);
                    startActivity(a);
                    break;
                case 2:
                    //Settings case
                    /* Intent a = new Intent(getBaseContext(), Login.class);
                    startActivity(a);*/

                    break;

                case 3:
                    //Unregister case
                    a = new Intent(getBaseContext(), History.class);
                    startActivity(a);
                    break;
                case 4:
                    //Unregister case
                    /*a = new Intent(getBaseContext(), History.class);
                    startActivity(a);*/
                    break;

            }
        }
    }


}
