package com.hack.xapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tejom_000 on 1/6/2016.
 */
public class FilterData {

    public String gender;
    public String timeFrom;
    public String timeTo;
    public List<String> services;
    public boolean isPartTime;

    public String salaryFrom;
    public String salaryTo;

    private static FilterData mFilterData = null;

    public static FilterData getInstance() {
        if (mFilterData == null) {
            mFilterData = new FilterData();

        }
        return mFilterData;
    }

    private FilterData() {
        services = new ArrayList<>();
    }

    public void setFilterData(String g, String tf, String tt, boolean p, List<String> svc, String sf, String st) {
        gender = g;
        timeFrom = tf;
        timeTo = tt;
        isPartTime = p;
        services = svc;
        salaryFrom = sf;
        salaryTo = st;
    }


}
