package com.hack.xapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.hack.xapp.R;
import com.hack.xapp.adapter.BookedHistoryListAdapter;
import com.hack.xapp.fragment.dummy.DummyContent;

public class History extends AppCompatActivity {

    BookedHistoryListAdapter rvAdapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rv = (RecyclerView) findViewById(R.id.booked_history_list_fragment);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new BookedHistoryListAdapter(getBaseContext(), DummyContent.BOOKED_HISTORY_ITEMS);
        rv.setAdapter(rvAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
