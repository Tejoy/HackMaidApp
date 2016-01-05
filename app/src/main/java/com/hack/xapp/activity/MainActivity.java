package com.hack.xapp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.hack.xapp.R;
import com.hack.xapp.adapter.MaidsListAdapter;
import com.hack.xapp.fragment.NavigationDrawerFragment;
import com.hack.xapp.fragment.dummy.DummyContent;
import com.hack.xapp.model.Maid;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.OnFragmentInteractionListener {

    TextView tv;
    MaidsListAdapter rvAdapter;
    RecyclerView rv;
    DrawerLayout myDrawer;
    RelativeLayout rightDrawer;
    ListView leftDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maids_list_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tv = (TextView) findViewById(R.id.no_maids_message);
        rv = (RecyclerView) findViewById(R.id.maids_list_fragment);
        myDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        rightDrawer = (RelativeLayout) findViewById(R.id.right_drawer);
        leftDrawer = (ListView) findViewById(R.id.left_drawer);

        List<String> leftitems = new ArrayList<String>();
        leftitems.add("Login");
        leftitems.add("History");
        leftitems.add("Settings");

 /*       List<String> rightitems = new ArrayList<String>();
        rightitems.add("Ravi");
        rightitems.add("Tejo");
        rightitems.add("Rajjo");

        ArrayAdapter rightadpater = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1,rightitems);

        rightDrawer.setAdapter(rightadpater);*/

        //populateRightDrawer();

        ArrayAdapter leftadpater = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, leftitems);

        leftDrawer.setAdapter(leftadpater);

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);
        rvAdapter = new MaidsListAdapter(getBaseContext(), DummyContent.MAID_ITEMS);
        rv.setAdapter(rvAdapter);
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
                System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                //dataAdapter.getFilter().filter(query);
                System.out.println("on query submit: " + query);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            if (myDrawer.isDrawerOpen(rightDrawer)) {
                myDrawer.closeDrawer(rightDrawer);
            } else {
                myDrawer.openDrawer(rightDrawer);
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


}
