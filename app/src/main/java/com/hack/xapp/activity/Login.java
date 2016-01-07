package com.hack.xapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hack.xapp.R;
import com.hack.xapp.model.Maid;
import com.hack.xapp.util.Util;

import org.json.JSONObject;

public class Login extends Activity {

    Button checkavail;
    AlertDialog mDialog;
    private Maid mMaid = null;
    EditText mName;
    EditText mPhone;
    EditText mEmailId;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mName = (EditText) this.findViewById(R.id.title_user_name);
        mPhone = (EditText) this.findViewById(R.id.title_phone_number);
        mEmailId = (EditText) this.findViewById(R.id.user_mail_id);
        checkavail = (Button) findViewById(R.id.button_check_avail);
        //showBookDialog();
        mContext = getBaseContext();
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        /*mMaid = intent.getParcelableExtra(Util.EXTRA_MAID);
        if (mMaid == null) {
            finish();
        }*/
    }

    public void showBookDialog() {
        LayoutInflater inflater = getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View v = inflater.inflate(R.layout.booking_feedback, null);



/*
        name.setText(mMaid.name);
        timing.setText(mMaid.phone);
        phone.setText(mMaid.phone);*/

/*        WindowManager.LayoutParams params = new WindowManager.LayoutParams(60, 60, 0, 0, 0);

        View serviceView;
        for (String st : mMaid.services) {
            serviceView = LayoutInflater.from(mContext).inflate(R.layout.image_view_layout, null, false);
            serviceView.setBackground(mContext.getResources().getDrawable(ServiceItem.getServiceResource(st)));
            servicesLayout.addView(serviceView, params);
        }*/

      /*  btBook.setOnClickListener(new View.OnClickListener() {
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
        mDialog.show();*/
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

       /* SharedPreferences pref = getSharedPreferences(Util.PREF_NAME, MODE_PRIVATE);
        boolean res = pref.getBoolean(Util.PREF_KEY, false);
        boolean from_history = false;
        Intent i = getIntent();
        if (i != null) {
            from_history = i.getBooleanExtra(Util.FROM_HISTORY, false);
        }*/


        checkavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences(Util.PREF_LOGIN, MODE_PRIVATE);
                User user = new User(mName.getText().toString(), Long.parseLong(mPhone.getText().toString()), mEmailId.getText().toString());

                // pref.edit().putStringSet().commit();
                SharedPreferences.Editor prefsEditor = pref.edit();
                //String json = gson.toJson(myObject); // myObject - instance of MyObject
                prefsEditor.putString("UserObject", user.toString());
                Log.i("Rajesh", "user data : " + user.toString());
                prefsEditor.commit();
                /*prefsEditor.putString("mName", mName.getText().toString());
                prefsEditor.putLong("mPhone", Long.parseLong(mPhone.getText().toString()));
                prefsEditor.putString("mEmailId", mEmailId.getText().toString());
                prefsEditor.apply();*/

                //showBookDialog();
            }
        });

/*        if (res) {
            showBookDialog();
        }*/
    }

    public class User {
        String name = "";
        String mailId = "";
        Long number = 0L;

        public User(String name, Long number, String mailId) {
            this.name = name;
            this.mailId = mailId;
            this.number = number;
        }
    }
}