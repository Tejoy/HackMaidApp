package com.hack.xapp.model;

import java.util.List;

/**
 * Created by tejom_000 on 1/6/2016.
 */
public class FilterData {

    String gender;
    String timeFrom;
    String timeTo;
    List<String> services;
    boolean isPartTime;

    String partTimeFrom;
    String partTimeTo;

    public FilterData(String g, String tf, String tt, boolean p, List<String> svc) {
        gender = g;
        timeFrom = tf;
        timeTo = tt;
        isPartTime = p;
        services = svc;


    }

    public void setPartTime(String ptf, String ptt) {
        partTimeFrom = ptf;
        partTimeTo = ptt;
    }


}
