package com.hack.xapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hack.xapp.R;
import com.hack.xapp.model.FilterData;
import com.hack.xapp.model.ServiceItem;
import com.hack.xapp.model.TagObject;
import com.hack.xapp.util.Util;

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

    RadioGroup radioGrpGender;
    RadioGroup radioGrpPartTime;
    Button timeFrom;
    Button timeTo;
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
        Log.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        radioGrpGender = (RadioGroup) view.findViewById(R.id.gender_radio_group);
        radioGrpPartTime = (RadioGroup) view.findViewById(R.id.part_time_radio_group);
        timeFrom = (Button) view.findViewById(R.id.timing_from);
        timeTo = (Button) view.findViewById(R.id.timing_to);
        salaryFrom = (EditText) view.findViewById(R.id.salary_from);
        salaryTo = (EditText) view.findViewById(R.id.salary_to);

        RadioButton btm = (RadioButton) view.findViewById(R.id.gender_male);
        RadioButton btfm = (RadioButton) view.findViewById(R.id.gender_female);
        RadioButton btp = (RadioButton) view.findViewById(R.id.part_time);
        RadioButton btf = (RadioButton) view.findViewById(R.id.full_time);

        btm.setTag(Util.FILTER_GENDER_MALE);
        btfm.setTag(Util.FILTER_GENDER_FEMALE);

        btp.setTag(Util.MAID_ATTR_ISPART_TIME);
        btf.setTag(Util.MAID_ATTR_FULL_TIME);

        radioGrpGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idd = group.getCheckedRadioButtonId();
                Log.i(TAG, "onCheckedChanged checkedId " + checkedId);
                Log.i(TAG, "onCheckedChanged checkedId " + group.getTag(checkedId));
                Log.i(TAG, "onCheckedChanged checkedId " + group.getChildAt(1).getTag());
                Log.i(TAG, "onCheckedChanged checkedId " + group.getChildAt(0).getId());
                Log.i(TAG, "onCheckedChanged checkedId " + group.getChildAt(1).getId());
            }
        });

        radioGrpPartTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idd = group.getCheckedRadioButtonId();
                Log.i(TAG, "onCheckedChanged checkedId " + checkedId);
                Log.i(TAG, "onCheckedChanged checkedId " + group.getTag(checkedId));
                Log.i(TAG, "onCheckedChanged checkedId " + group.getChildAt(0).getTag());
                Log.i(TAG, "onCheckedChanged checkedId " + group.getChildAt(1).getTag());
                Log.i(TAG, "onCheckedChanged checkedId " + group.getChildAt(0).getId());
                Log.i(TAG, "onCheckedChanged checkedId " + group.getChildAt(1).getId());
            }
        });

        salaryFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    FilterData.getInstance().salaryFrom = Long.valueOf(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        salaryTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    FilterData.getInstance().salaryTo = Long.valueOf(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        timeFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new TimePickerDialog(getActivity(),
                        timePickerListenerFrom, 0, 0, false).show();
            }
        });

        timeTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new TimePickerDialog(getActivity(),
                        timePickerListenerTo, 0, 0, false).show();
            }
        });

        Log.i(TAG, "radio grp child 0 id " + radioGrpGender.getChildAt(0).getId());
        radioGrpGender.check(radioGrpGender.getChildAt(0).getId());
        radioGrpPartTime.check(radioGrpPartTime.getChildAt(0).getId());

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

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach");
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

        /*int idd = radioGrp.getCheckedRadioButtonId();
        Log.i(TAG, "onPause radio button id " + idd);
        Log.i(TAG, "onPause radio button one " + R.id.gender_female);
        Log.i(TAG, "onPause radio button one " + R.id.gender_male);
        String salf = salaryFrom.getText().toString().trim();
        String salt = salaryTo.getText().toString().trim();
        long salFrom = salf.length() > 0 ? Long.valueOf(salf) : 0;
        long salTo = salt.length() > 0 ? Long.valueOf(salt) : 2000;
        FilterData.getInstance().salaryFrom = salFrom;
        FilterData.getInstance().salaryTo = salTo;*/

        super.onPause();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach");
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

    private TimePickerDialog.OnTimeSetListener timePickerListenerFrom =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    int hour = selectedHour;
                    int minute = selectedMinute;
                    FilterData.getInstance().timeFrom = new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)).toString();

                    timeFrom.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));
                }
            };

    private TimePickerDialog.OnTimeSetListener timePickerListenerTo =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    int hour = selectedHour;
                    int minute = selectedMinute;
                    FilterData.getInstance().timeTo = new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)).toString();

                    timeTo.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
