package com.hack.xapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.hack.xapp.R;
import com.hack.xapp.model.Maid;
import com.hack.xapp.util.Util;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterMaid extends Activity {

    Button checkavail;
    AlertDialog mDialog;
    private Maid mMaid = null;
    EditText mName;
    EditText mPhoneNumber;
    EditText mGender;
    EditText mPhone;
    Button mService;
    Button mTimingFrom;
    Button mTimingTo;
    Spinner mDuration;
    ImageView mPhoto;
    Context mContext;
    static final int TIME_DIALOG_FROM = 998;
    static final int TIME_DIALOG_TO = 999;
    int PLACE_PICKER_REQUEST = 2;
    private int hour;
    private int minute;

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
        mDuration = (Spinner) this.findViewById(R.id.title_maid_duration);
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

        checkavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences(Util.PREF_MAID_REGISTER, MODE_PRIVATE);
                Maid maid = new Maid(mName.getText().toString(), mGender.getText().toString(), mPhoneNumber.getText().toString(), mService.getText().toString(), mTimingFrom.getText().toString() + "-" + mTimingTo.getText().toString(), mDuration.getSelectedItem().toString(), mPhoto.getDrawable());

                // pref.edit().putStringSet().commit();
                SharedPreferences.Editor prefsEditor = pref.edit();
                //String json = gson.toJson(myObject); // myObject - instance of MyObject
                prefsEditor.putString(Util.PREF_MAID_REGISTER, maid.toString());
                prefsEditor.commit();
                /*prefsEditor.putString("mName", mName.getText().toString());
                prefsEditor.putLong("mPhone", Long.parseLong(mPhone.getText().toString()));
                prefsEditor.putString("mEmailId", mEmailId.getText().toString());
                prefsEditor.apply();*/

                //showBookDialog();
            }
        });

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
        }

    }


    /*        if (res) {
                showBookDialog();
            }*/
    //}
    public class Maid {
        String vName;
        int vAge;
        String vGender;
        String vPhone;
        String vService;
        String vTiming;
        String vDuration;
        Drawable vPhoto;

        public Maid(String vName, String vGender, String vPhone, String vService, String vTiming, String vDuration, Drawable vPhoto) {
            this.vName = vName;
            this.vAge = vAge;
            this.vGender = vGender;
            this.vPhone = vPhone;
            this.vService = vService;
            this.vTiming = vTiming;
            this.vDuration = vDuration;
            this.vPhoto = vPhoto;
        }
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}