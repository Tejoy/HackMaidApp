package com.hack.xapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.hack.xapp.model.Maid;
import com.hack.xapp.model.ServiceItem;
import com.hack.xapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterMaid extends Activity {

    public String TAG = "RegisterMaid";

    Button checkavail;
    Button chooseLoc;
    AlertDialog mDialog;
    private Maid mMaid = null;
    EditText mName;
    EditText mPhoneNumber;
    EditText mGender;
    EditText mPhone;
    Button mService;
    Button mTimingFrom;
    Button mTimingTo;
    EditText mSalaryFrom;
    EditText mSalaryTo;
    Spinner mDuration;
    ImageView mPhoto;
    Context mContext;
    String vServices;
    static final int TIME_DIALOG_FROM = 998;
    static final int TIME_DIALOG_TO = 999;
    static final int SERVICE_PICKER = 1000;
    int PLACE_PICKER_REQUEST = 2;
    private int hour;
    private int minute;
    String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mName = (EditText) this.findViewById(R.id.title_maid_name);
        mPhoto = (ImageView) this.findViewById(R.id.maid_photo);
        mPhoneNumber = (EditText) this.findViewById(R.id.title_maid_age);
        mGender = (EditText) this.findViewById(R.id.title_maid_gender);
        mService = (Button) this.findViewById(R.id.title_maid_services);
        mTimingFrom = (Button) this.findViewById(R.id.title_maid_timingFrom);
        mTimingTo = (Button) this.findViewById(R.id.title_maid_timingTo);
        mSalaryFrom = (EditText) this.findViewById(R.id.title_maid_SalaryFrom);
        mSalaryTo = (EditText) this.findViewById(R.id.title_maid_salaryTo);
        mDuration = (Spinner) this.findViewById(R.id.title_maid_duration);
        checkavail = (Button) findViewById(R.id.button_check_avail);
        chooseLoc = (Button) findViewById(R.id.button_choose_location);


        //showBookDialog();
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

        mPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        mTimingFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Intent photoPickerIntent = new Intent(RegisterMaid.this, TimeRangeSelector.class);
                //startActivity(photoPickerIntent);
                showDialog(TIME_DIALOG_FROM);
            }
        });

        mTimingTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Intent photoPickerIntent = new Intent(RegisterMaid.this, TimeRangeSelector.class);
                //startActivity(photoPickerIntent);
                showDialog(TIME_DIALOG_TO);
            }
        });

        mService.setOnClickListener(new View.OnClickListener() {
            String mServices = "";

            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), ListDialog.class);
                startActivityForResult(in, 5);

               /* AlertDialog.Builder builder = new AlertDialog.Builder(
                        RegisterMaid.this);

                LayoutInflater inflater = RegisterMaid.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.service_item, null);
                builder.setView(dialogView);
                builder.setTitle("Select Service");

                builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id)
                                    {
                                        vServices = mServices;
                                        Toast.makeText(getApplicationContext(), vServices, Toast.LENGTH_LONG).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                    }
                                });
                mDialog = builder.create();
                *///dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, ServiceItem.getServiceResource("cook"));

                //mDialog.show();
            }
        });
        chooseLoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(RegisterMaid.this), PLACE_PICKER_REQUEST);
                } catch (Exception e) {
                }
            }
        });
        checkavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO : populate service list
                List<String> services = new ArrayList<String>();

                //add each service to Maid object one by one like maid.add(serviceString)

                SharedPreferences pref = getSharedPreferences(Util.PREF_MAID_REGISTER, MODE_PRIVATE);
                //TODO: pass the salary input
                Maid maid = new Maid(mName.getText().toString(), mGender.getText().toString(), mPhoneNumber.getText().toString(), 0, 0);

                //TODO : set the photo below as Bitmap
                maid.setIsPartTime(mDuration.getSelectedItem().toString().equals(Util.MAID_ATTR_ISPART_TIME) ? true : false);
                maid.setPhoto(null);

                SharedPreferences.Editor prefsEditor = pref.edit();
                //String json = gson.toJson(myObject); // myObject - instance of MyObject
                prefsEditor.putString(Util.PREF_MAID_REGISTER, maid.toString());
                prefsEditor.commit();
                /*prefsEditor.putString("mName", mName.getText().toString());
                prefsEditor.putLong("mPhone", Long.parseLong(mPhone.getText().toString()));
                prefsEditor.putString("mEmailId", mEmailId.getText().toString());
                prefsEditor.apply();*/


                makeServerRequest(maid);

            }
        });

    }

    private void makeServerRequest(Maid mMaid) {

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

/*        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", user.name);
            jsonObject.put("email", user.email);
            jsonObject.put("mob", user.mob);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        MyJsonArrayRequest jsArrayRequest = new MyJsonArrayRequest(Util.ServerURL + Util.EVENT_REGISTER_MAID, new Response.Listener<JSONArray>() {

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
        }, mMaid);


        queue.add(jsArrayRequest);


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_FROM:
                return new TimePickerDialog(this,
                        timePickerListenerFrom, hour, minute, false);

            case TIME_DIALOG_TO:

                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListenerTo, hour, minute, false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListenerFrom =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    // set current time into textview
                    mTimingFrom.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));

                    // set current time into timepicker
                    //timePicker1.setCurrentHour(hour);
                    //timePicker1.setCurrentMinute(minute);

                }
            };

    private TimePickerDialog.OnTimeSetListener timePickerListenerTo =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    // set current time into textview
                    mTimingTo.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));

                    // set current time into timepicker
                    //timePicker1.setCurrentHour(hour);
                    //timePicker1.setCurrentMinute(minute);

                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        mPhoto.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(imageReturnedIntent, this);
                    loc = place.getLatLng().toString();
                    String toastMsg = String.format("Place: %s", place.getName());

                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                }
            case 5:
                if (resultCode == Activity.RESULT_OK) {

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
            params.put("name", mMaid.name);
            params.put("phone", mMaid.phone);
            params.put("gender", mMaid.gender);
            params.put("salaryTo", String.valueOf(mMaid.salaryTo));
            params.put("salaryFrom", String.valueOf(mMaid.salaryFrom));
            params.put("isPartTime", mMaid.isPartTime ? String.valueOf(1) : String.valueOf(0));
            params.put("isAvailable", mMaid.isAvailable ? String.valueOf(1) : String.valueOf(0));
            params.put("idNum", mMaid.idNum);
            params.put("idType", mMaid.idType);

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

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}