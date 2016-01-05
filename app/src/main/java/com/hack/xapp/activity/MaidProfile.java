package com.hack.xapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hack.xapp.R;
import com.hack.xapp.util.Util;

public class MaidProfile extends Activity {

    Button checkavail;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_profile);
        checkavail = (Button) findViewById(R.id.button_check_avail);

    }
/*
    @Override
    public void onBackPressed() {
        //do nothing
    }*/

    public void showBookDialog() {
        LayoutInflater inflater = getLayoutInflater();
        // View dialogView = inf.inflate()
        AlertDialog.Builder builder = new AlertDialog.Builder(MaidProfile.this);
        builder.setCancelable(false);
        View v = inflater.inflate(R.layout.booking_feedback, null);

        builder.setView(v);

        builder.setPositiveButton("Book", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences pref = getSharedPreferences(Util.PREF_NAME, MODE_PRIVATE);
                pref.edit().putBoolean(Util.PREF_KEY, false).commit();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences pref = getSharedPreferences(Util.PREF_NAME, MODE_PRIVATE);
                pref.edit().putBoolean(Util.PREF_KEY, false).commit();
                dialog.dismiss();
            }
        });

        mDialog = builder.create();
        mDialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maid_profile, menu);
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

        SharedPreferences pref = getSharedPreferences(Util.PREF_NAME, MODE_PRIVATE);
        boolean res = pref.getBoolean(Util.PREF_KEY, false);
        boolean from_history = false;
        Intent i = getIntent();
        if (i != null) {
            from_history = i.getBooleanExtra(Util.FROM_HISTORY, false);
        }

        if (from_history) {
            checkavail.setVisibility(View.GONE);
            res = false;
        } else {
            checkavail.setVisibility(View.VISIBLE);
        }

        checkavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences(Util.PREF_NAME, MODE_PRIVATE);
                pref.edit().putBoolean(Util.PREF_KEY, true).commit();

                showBookDialog();


            }
        });

        if (res) {
            showBookDialog();
        }
    }
}
