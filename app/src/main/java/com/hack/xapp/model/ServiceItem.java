package com.hack.xapp.model;

import com.hack.xapp.R;

import java.util.List;

/**
 * Created by tejom_000 on 1/5/2016.
 */
public class ServiceItem {

    private static List<String> serviceList;

    private static ServiceItem mServiceItem = null;

    public static final String cleaning = "cleaning";
    public static final String wash = "clean";
    public static final String childcare = "childcare";
    public static final String elderlycare = "elderlycare";
    public static final String cook = "cook";

    private ServiceItem() {
    }

    public static ServiceItem getInstance() {
        if (mServiceItem == null) {
            mServiceItem = new ServiceItem();
        }
        return mServiceItem;
    }

    public static void addService(String svc) {
        serviceList.add(svc);
    }

    public static int getServiceResource(final String svc) {
        switch (svc) {
            case cook:
                return R.drawable.cook;
            case cleaning:
                return R.drawable.clean;
            case childcare:
                return R.drawable.child;
            case wash:
                return R.drawable.wash;
            case elderlycare:
                return R.drawable.elder;

        }
        return -1;
    }

    public static boolean supportsService(String svc) {
        return serviceList.contains(svc);
    }

}
