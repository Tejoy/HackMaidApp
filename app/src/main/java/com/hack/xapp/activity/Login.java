package com.hack.xapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hack.xapp.R;
import com.hack.xapp.model.UserObject;
import com.hack.xapp.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Activity {

    private String TAG = "Login";

    Button checkavail;
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
        mContext = getBaseContext();
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
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

        checkavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(mName.getText() != null && !("".equals(mPhone.getText().toString())))) {
                    Toast.makeText(mContext, "Please Enter the valid Name", Toast.LENGTH_LONG).show();

                }

                if (!(mPhone.getText() != null && isValidPhone(mPhone.getText().toString()))) {
                    Toast.makeText(mContext, "Please Enter the valid Phone number", Toast.LENGTH_LONG).show();

                }
                if (!(mEmailId.getText() != null && isValidEmail(mEmailId.getText().toString()))) {
                    Toast.makeText(mContext, "Please Enter the valid email id", Toast.LENGTH_LONG).show();

                }

                if (!(mName.getText() != null && !("".equals(mPhone.getText().toString()))) && (mEmailId.getText() != null && isValidEmail(mEmailId.getText().toString()) && ((mPhone.getText() != null && isValidPhone(mPhone.getText().toString()))))) {
                    SharedPreferences pref = getSharedPreferences(Util.PREF_LOGIN, MODE_PRIVATE);
                    UserObject user = new UserObject(mName.getText().toString(), mPhone.getText().toString(), mEmailId.getText().toString());

                    SharedPreferences.Editor prefsEditor = pref.edit();
                    prefsEditor.putString("UserObject", user.toString());
                    Log.i(TAG, "user data : " + user.toString());
                    prefsEditor.commit();

                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", user.name);
                        jsonObject.put("email", user.email);
                        jsonObject.put("mob", user.mob);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Util.ServerURL + Util.EVENT_USER_LOGIN, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // TODO Auto-generated method stub
                            Log.i(TAG, "Response => " + response.toString());
                            //if success

                            Intent intent = new Intent();
                            intent.setClassName(getBaseContext(), MainActivity.class.getName());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(TAG, "onErrorResponse ");
                            error.printStackTrace();

                        }
                    });

                    queue.add(jsObjRequest);

                }
            }
        });
    }

    public final boolean isValidPhone(String target) {
        if (target == null) {
            return false;
        } else if (!(target).matches("[0-9]{10}")) {
            return false;
        }
        return true;
    }

    public final boolean isValidEmail(String target) {
        if (target == null) {
            Toast.makeText(mContext, "Please Enter the empty email id", Toast.LENGTH_LONG).show();

            return false;//    else if (email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))

        } /*else if (!(target).matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            Toast.makeText(mContext, "Please Enter the invalid email id", Toast.LENGTH_LONG).show();

            return false;*/
        // }
        return true;
    }
}