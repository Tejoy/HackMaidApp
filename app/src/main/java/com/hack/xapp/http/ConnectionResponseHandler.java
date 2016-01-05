package com.hack.xapp.http;

import java.util.HashMap;
import java.util.Observer;

/**
 * Created by tejom_000 on 1/5/2016.
 */
public class ConnectionResponseHandler {

    HashMap<String, Observer> observersList = new HashMap<String, Observer>();

    private static ConnectionResponseHandler mConnectionResponseHandler = null;

    private ConnectionResponseHandler() {

    }

    public static ConnectionResponseHandler getInstance() {
        if (mConnectionResponseHandler == null) {
            mConnectionResponseHandler = new ConnectionResponseHandler();
        }
        return mConnectionResponseHandler;
    }

    public void registerObserver(String name, Observer observer) {
        if (!observersList.containsKey(name)) {
            observersList.put(name, observer);
        }
    }

    public void unRegisterObserver(String name) {
        if (observersList.containsKey(name)) {
            observersList.remove(name);
        }
    }

    public Observer getObserver(String key) {
        return observersList.get(key);
    }

}
