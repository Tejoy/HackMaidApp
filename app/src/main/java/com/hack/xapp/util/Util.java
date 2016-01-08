package com.hack.xapp.util;

import com.hack.xapp.model.FilterData;
import com.hack.xapp.model.Location;

import java.util.Date;

/**
 * Created by tejom_000 on 1/5/2016.
 */
public class Util {

    public static String EVENT_USER_LOGIN = "login";
    public static String EVENT_AUTH = "auth";
    public static String EVENT_MAID_LIST = "main_activity";
    public static String EVENT_REGISTER_MAID = "register_maid";
    public static String EVENT_REGISTER_USER = "register_user";
    public static String EVENT_BOOK_MAID = "book_maid";
    public static String EVENT_BOOKING_HISTORY = "booking_history";

    public static String PREF_NAME = "MaidBookPref";
    public static String PREF_LOGIN = "LoginPref";
    public static String PREF_MAID_REGISTER = "maidRegister";

    public static String PREF_KEY = "isbooking";

    public static String FILTER_GENDER_FEMALE = "female";
    public static String FILTER_GENDER_MALE = "male";

    public static final String INTENT_EXTRA_MAID = "maid";
    public static String INTENT_EXTRA_FROM_HISTORY = "from_history";

    public static String LEFT_DRAWER_MAIN_SETTINGS = "Settings";
    public static String LEFT_DRAWER_MAIN_LOGIN = "Login";
    public static String LEFT_DRAWER_MAIN_HISTORY = "History";
    public static String LEFT_DRAWER_MAIN_REGISTER = "Register";
    public static String LEFT_DRAWER_MAIN_UNREGISTER = "Unregister";
    public static String LEFT_DRAWER_MAIN_LOCATION = "Location";

    public static FilterData mFilterData = null;
    public static Location currentSearchLoc = null;

    public static String ServerURL = "http://192.168.1.11:8888/MaidService/";


    public static Date getDate(String str) {
        Date d = new Date();
        String hh = str.substring(0, str.indexOf(":"));
        String mm = str.substring(str.indexOf(":") + 1);
        d.setHours(Integer.valueOf(hh));
        d.setMinutes(Integer.valueOf(mm));
        return d;
    }

    public static boolean isgreater(String str1, String str2) {
        return (getDate(str1).compareTo(getDate(str2)) > 0);
    }

    public static boolean isEqual(String str1, String str2) {
        return (getDate(str1).compareTo(getDate(str2)) == 0);
    }

    public static FilterData getFilterData() {
        return mFilterData;
    }

    public static void setFilterData(FilterData data) {
        mFilterData = data;
    }

}
