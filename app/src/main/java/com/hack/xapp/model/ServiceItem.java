package com.hack.xapp.model;

import java.util.List;

/**
 * Created by tejom_000 on 1/5/2016.
 */
public class ServiceItem {

    private static List<String> serviceList;

    private static ServiceItem mServiceItem = null;

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

    public static boolean supportsService(String svc) {
        return serviceList.contains(svc);
    }

}
