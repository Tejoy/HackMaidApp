package com.hack.xapp.http;

import com.hack.xapp.model.ConnectionRequest;

import java.util.Observer;

public class ConnectionClient {

    ConnectionTask mConnectionTask = null;

    public ConnectionClient() {

    }

    public void getMaidsList(Observer observer, String key) {

        ConnectionResponseHandler.getInstance().registerObserver(key, observer);

        mConnectionTask = new ConnectionTask();
        ConnectionRequest req = new ConnectionRequest();

        //set all request data

        mConnectionTask.execute(req);

    }


}