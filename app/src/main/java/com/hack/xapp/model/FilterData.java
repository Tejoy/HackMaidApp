package com.hack.xapp.model;

import com.hack.xapp.util.Util;

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

    public long salaryFrom;
    public long salaryTo;

    private static FilterData mFilterData = null;

    public static FilterData getInstance() {
        if (mFilterData == null) {
            mFilterData = new FilterData();

        }
        return mFilterData;
    }

    public static void resetInstance() {
        mFilterData = new FilterData();
    }

    public FilterData() {
        gender = Util.FILTER_GENDER_MALE;
        timeFrom = "00:00";
        timeTo = "00:00";
        isPartTime = false;
        salaryFrom = 0;
        salaryTo = 2000;
        services = new ArrayList<>();
    }

    public void setFilterData(String g, String tf, String tt, boolean p, List<String> svc, long sf, long st) {
        gender = g;
        timeFrom = tf;
        timeTo = tt;
        isPartTime = p;
        services = svc;
        salaryFrom = sf;
        salaryTo = st;
    }

    public boolean hasChanged(FilterData newFilterData) {

        if (!gender.equals(newFilterData.gender)) {
            return true;
        }
        if (!timeFrom.equals(newFilterData.timeFrom)) {
            return true;
        }
        if (!timeTo.equals(newFilterData.timeTo)) {
            return true;
        }
        if (isPartTime != newFilterData.isPartTime) {
            return true;
        }
        if (salaryFrom != newFilterData.salaryFrom) {
            return true;
        }
        if (salaryTo != newFilterData.salaryTo) {
            return true;
        }

        if (services.size() != newFilterData.services.size()) {
            return true;
        }

        for (String s : services) {
            if (!newFilterData.services.contains(s)) {
                return true;
            }
        }
        return false;
    }


}
