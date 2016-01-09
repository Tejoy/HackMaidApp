package com.hack.xapp.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.hack.xapp.R;
import com.hack.xapp.adapter.ListDialogAdapter;
import com.hack.xapp.model.ServiceItem;
import com.hack.xapp.util.DialogHelper;

import java.util.ArrayList;
import java.util.List;

public class ListDialog extends ListActivity {

    public String[] names;
    private TypedArray imgs;
    private List<DialogHelper> list;

    Button btn_ok;
    Button btn_cancel;
    String services;
    ArrayList<String> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateList();
        setContentView(R.layout.dialog_list);
        btn_ok = (Button) findViewById(R.id.title_maid_timingTo);

        btn_cancel = (Button) findViewById(R.id.title_maid_timingFrom);
        ArrayAdapter<DialogHelper> adapter = new ListDialogAdapter(this, list);

        setListAdapter(adapter);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("selector", serviceList);

// TODO Add extras or a data URI to this intent as appropriate.
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent();
                // resultIntent.putExtra("selector", serviceList);

// TODO Add extras or a data URI to this intent as appropriate.
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "valeu" + position, Toast.LENGTH_LONG).show();
                Log.i("Rajesh", "rarea");
                if (!serviceList.contains(ServiceItem.getServicesList().get(position)))
                    serviceList.add(ServiceItem.getServicesList().get(position));
                else
                    serviceList.remove(ServiceItem.getServicesList().get(position));

            }
        });
    }


    private void populateList() {
        list = new ArrayList<>();
        names = ServiceItem.getServicesList().toArray(new String[ServiceItem.getServicesList().size()]);
        // imgs = ServiceItem.getServicesList() getResources().obtainTypedArray(R.array.flags);
        for (int i = 0; i < names.length; i++)
            list.add(new DialogHelper(names[i], getApplicationContext().getDrawable(ServiceItem.getServiceResource(names[i]))));
    }

}