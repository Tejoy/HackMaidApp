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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.hack.xapp.R;
import com.hack.xapp.adapter.MaidsListAdapter;
import com.hack.xapp.fragment.NavigationDrawerFragment;
import com.hack.xapp.model.FilterData;
import com.hack.xapp.model.Maid;
import com.hack.xapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static SearchView searchView;
    private static MenuItem searchMenuItem;


    String LEFT_DRAWER_TAG = "left";
    String RIGHT_DRAWER_TAG = "right";
    int PLACE_PICKER_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.i(TAG, "onCreate ");
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
        leftitems.add(Util.LEFT_DRAWER_MAIN_LOCATION);

        leftDrawer.setOnItemClickListener(new LeftDrawerClickListner());

        ArrayAdapter leftadpater = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, leftitems);

        leftDrawer.setAdapter(leftadpater);

        leftDrawer.setTag(LEFT_DRAWER_TAG);
        rightDrawer.setTag(RIGHT_DRAWER_TAG);

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);
        //TODO: fetch and pass maid list items, test below
        //rvAdapter = new MaidsListAdapter(getBaseContext(), DummyContent.MAID_ITEMS);
        rvAdapter = new MaidsListAdapter(getBaseContext(), new ArrayList<Maid>());
        rv.setAdapter(rvAdapter);

        Log.i(TAG, "made request for list ");

        makeServerRequest();


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
                if (tag == RIGHT_DRAWER_TAG) {
                    boolean hasChanged = (Util.mFilterData == null || Util.mFilterData.hasChanged(FilterData.getInstance()));
                    if (hasChanged) {
                        Util.mFilterData = FilterData.getInstance();
                        //TODO: filter has changed, hence update the list view items
                        ((MaidsListAdapter.CustomFilter) rvAdapter.getFilter()).filteringWithData();
                        rv.scrollToPosition(0);
                    }
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                String tag = drawerView.getTag().toString();
                Log.i(TAG, "onDrawerOpened " + tag);

                if (tag == RIGHT_DRAWER_TAG) {
                    FilterData.resetInstance();
                }

                supportInvalidateOptionsMenu();
            }
        };

        myDrawer.setDrawerListener(mDrawerToggle);
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (Exception e) {
        }
    }

    private void makeServerRequest() {

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Util.ServerURL + Util.EVENT_MAID_LIST, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub
                Log.i(TAG, "Response => " + response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse ");
                error.printStackTrace();

            }
        });

        queue.add(jsArrayRequest);


    }

    public static SearchView getSearchView() {
        return searchView;
    }

    public static MenuItem getSearchMenuItem() {
        return searchMenuItem;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                //dataAdapter.getFilter().filter(newText);
                Log.i(TAG, "on text chnge text: " + newText);


                rvAdapter.getFilter().filter(newText);
                rv.scrollToPosition(0);

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
            rvAdapter = new MaidsListAdapter(getBaseContext(), list);
            rv.setAdapter(rvAdapter);
        }

        public void onError(String msg) {

        }

    }

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

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {

            case 2:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(imageReturnedIntent, this);
                    String toastMsg = String.format("Place: %s", place.getName());
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                }
        }


    }

    private class MyJsonArrayRequest extends JsonArrayRequest {

        Maid mMaid = null;

        /**
         * Creates a new request.
         *
         * @param url           URL to fetch the JSON from
         * @param listener      Listener to receive the JSON response
         * @param errorListener Error listener, or null to ignore errors.
         */
        public MyJsonArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener, Maid mMaid) {
            super(url, listener, errorListener);
            this.mMaid = mMaid;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();

            //TODO : pass search maid params here


            return super.getParams();
        }

        @Override
        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            Log.i(TAG, "Response => " + response.toString());

            JSONObject jo = new JSONObject();

            try {
                jo.put("lastName", "Doe");
                jo.put("firstName", "John");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray ja = new JSONArray();
            ja.put(jo);
            /*String jsonString = new String(response.data,
                    HttpHeaderParser
                            .parseCharset(response.headers));*/
            return Response.success(ja,
                    HttpHeaderParser
                            .parseCacheHeaders(response));

        }
    }
}













