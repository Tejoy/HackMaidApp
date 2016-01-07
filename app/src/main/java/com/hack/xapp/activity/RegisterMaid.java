package com.hack.xapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
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
    EditText mAge;
    EditText mGender;
    EditText mPhone;
    Button mService;
    Button mTiming;
    Spinner mDuration;
    ImageView mPhoto;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mName = (EditText) this.findViewById(R.id.title_maid_name);
        mPhoto = (ImageView) this.findViewById(R.id.maid_photo);


        mAge = (EditText) this.findViewById(R.id.title_maid_age);
        mGender = (EditText) this.findViewById(R.id.title_maid_gender);
        mService = (Button) this.findViewById(R.id.title_maid_services);
        mTiming = (Button) this.findViewById(R.id.title_maid_timing);
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
        checkavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences(Util.PREF_MAID_REGISTER, MODE_PRIVATE);
                Maid maid = new Maid(mName.getText().toString(), Integer.parseInt(mAge.getText().toString()), mGender.getText().toString(), mPhone.getText().toString(), mService.getText().toString(), mTiming.getText().toString(), mDuration.getSelectedItem().toString(), mPhoto.getDrawable());

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

        public Maid(String vName, int vAge, String vGender, String vPhone, String vService, String vTiming, String vDuration, Drawable vPhoto) {
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
}