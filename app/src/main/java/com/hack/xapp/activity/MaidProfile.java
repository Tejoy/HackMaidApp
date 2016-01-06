package com.hack.xapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hack.xapp.R;
import com.hack.xapp.model.Maid;
import com.hack.xapp.model.ServiceItem;
import com.hack.xapp.util.Util;

public class MaidProfile extends Activity {

    Button checkavail;
    AlertDialog mDialog;
    private Maid mMaid = null;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_profile);
        checkavail = (Button) findViewById(R.id.button_check_avail);
        mContext = getBaseContext();
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        mMaid = intent.getParcelableExtra(Util.EXTRA_MAID);
        if (mMaid == null) {
            finish();
        }
    }

    public void showBookDialog() {
        LayoutInflater inflater = getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(MaidProfile.this);
        builder.setCancelable(false);
        View v = inflater.inflate(R.layout.booking_feedback, null);

        TextView name = (TextView) v.findViewById(R.id.maid_name);
        TextView timing = (TextView) v.findViewById(R.id.maid_timing);
        LinearLayout servicesLayout = (LinearLayout) v.findViewById(R.id.maid_services);
        TextView phone = (TextView) v.findViewById(R.id.maid_phone);
        Button btBook = (Button) v.findViewById(R.id.button_booked);
        Button btNotBooked = (Button) v.findViewById(R.id.button_not_booked);

        name.setText(mMaid.name);
        timing.setText(mMaid.phone);
        phone.setText(mMaid.phone);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(60, 60, 0, 0, 0);

        View serviceView;
        for (String st : mMaid.services) {
            serviceView = LayoutInflater.from(mContext).inflate(R.layout.image_view_layout, null, false);
            serviceView.setBackground(mContext.getResources().getDrawable(ServiceItem.getServiceResource(st)));
            servicesLayout.addView(serviceView, params);
        }

        btBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: send booked status to server
                SharedPreferences pref = getSharedPreferences(Util.PREF_NAME, MODE_PRIVATE);
                pref.edit().putBoolean(Util.PREF_KEY, false).commit();
                mDialog.dismiss();
            }
        });

        btNotBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences(Util.PREF_NAME, MODE_PRIVATE);
                pref.edit().putBoolean(Util.PREF_KEY, false).commit();
                mDialog.dismiss();
            }
        });

        builder.setView(v);
        mDialog = builder.create();
        mDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maid_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
