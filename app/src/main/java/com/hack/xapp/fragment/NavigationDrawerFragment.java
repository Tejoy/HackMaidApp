package com.hack.xapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.hack.xapp.R;
import com.hack.xapp.model.FilterData;
import com.hack.xapp.model.ServiceItem;
import com.hack.xapp.model.TagObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class NavigationDrawerFragment extends Fragment implements AbsListView.OnItemClickListener {

    private final String TAG = "NavigationDrawerFrag";

    private static final String TAG_SELECTED = "selected";
    private static final String TAG_UNSELECTED = "unselected";
    private static final int TAG_SERVICE_KEY = 1;

    RadioGroup radioGrp;
    EditText timeFrom;
    EditText timeTo;
    EditText salaryFrom;
    EditText salaryTo;


    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;


    // TODO: Rename and change types of parameters
    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        return fragment;
    }

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.duration_spinner);
        radioGrp = (RadioGroup) view.findViewById(R.id.gender_radio_group);
        timeFrom = (EditText) view.findViewById(R.id.timing_from);
        timeTo = (EditText) view.findViewById(R.id.timing_to);
        salaryFrom = (EditText) view.findViewById(R.id.salary_from);
        salaryTo = (EditText) view.findViewById(R.id.salary_to);

        LinearLayout servicesLayout = (LinearLayout) view.findViewById(R.id.maid_services);

        List<String> serviceList = ServiceItem.getServicesList();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(80, 80, 0, 0, 0);
        View v;
        for (String s : serviceList) {
            v = LayoutInflater.from(getActivity()).inflate(R.layout.image_view_layout, null, false);
            final LinearLayout imageLayout = (LinearLayout) v.findViewById(R.id.image_layout);
            ImageView img = (ImageView) v.findViewById(R.id.image_view);
            img.setImageResource(ServiceItem.getServiceResource(s));
            v.setTag(new TagObject(s, TAG_UNSELECTED));
            imageLayout.setBackgroundColor(getResources().getColor(R.color.red));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TagObject tag = (TagObject) v.getTag();
                    Log.i(TAG, "onDrawerClosed " + tag.selection);
                    Log.i(TAG, "onDrawerClosed " + tag.serviceName);
                    if (tag.selection == TAG_SELECTED) {
                        tag.selection = TAG_UNSELECTED;
                        v.setTag(tag);
                        imageLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        FilterData.getInstance().services.remove(tag.serviceName);
                    } else {
                        tag.selection = TAG_SELECTED;
                        v.setTag(tag);
                        imageLayout.setBackgroundColor(getResources().getColor(R.color.green));
                        FilterData.getInstance().services.add(tag.serviceName);
                    }
                }
            });
            servicesLayout.addView(v, params);
        }



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    FilterData.getInstance().isPartTime = true;
                } else {
                    FilterData.getInstance().isPartTime = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Parttime");
        categories.add("Fulltime");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause called ");

        int idd = radioGrp.getCheckedRadioButtonId();
        Log.i(TAG, "onPause radio button id " + idd);
        Log.i(TAG, "onPause radio button one " + R.id.gender_female);
        Log.i(TAG, "onPause radio button one " + R.id.gender_male);

        //FilterData.getInstance().timeFrom = ;
        //FilterData.getInstance().timeTo = ;

        String salFrom = salaryFrom.getText().toString();
        String salTo = salaryTo.getText().toString();
        FilterData.getInstance().salaryFrom = salFrom;
        FilterData.getInstance().salaryTo = salTo;

        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
