package com.hack.xapp.http;

import android.app.Activity;

import com.hack.xapp.model.BookedItem;
import com.hack.xapp.model.ConnectionRequest;
import com.hack.xapp.model.Location;
import com.hack.xapp.model.Maid;
import com.hack.xapp.model.UserObject;

import java.util.Observer;

public class ConnectionClient {

    ConnectionTask mConnectionTask = null;

    public ConnectionClient() {

    }

    public void loginRequest(Activity ctx, Observer observer, String key, String user, String phone, String email) {

        ConnectionResponseHandler.getInstance().registerObserver(key, observer);

        mConnectionTask = new ConnectionTask(ctx);
        ConnectionRequest req = new ConnectionRequest();

        //TODO: set all request data

        mConnectionTask.execute(req);

    }

    public void getAuth(Activity ctx, Observer observer, String key, String token) {

        ConnectionResponseHandler.getInstance().registerObserver(key, observer);

        mConnectionTask = new ConnectionTask(ctx);
        ConnectionRequest req = new ConnectionRequest();

        //TODO : set all request data

        mConnectionTask.execute(req);

    }

    public void getMaidsList(Activity ctx, Observer observer, String key, Location loc) {

        ConnectionResponseHandler.getInstance().registerObserver(key, observer);

        mConnectionTask = new ConnectionTask(ctx);
        ConnectionRequest req = new ConnectionRequest();

        //req.setLoc(loc);

        req.setKey(key);

        //set all request data

        mConnectionTask.execute(req);

    }

    public void registerMaid(Activity ctx, Observer observer, String key, Location loc, Maid mMaid) {

        ConnectionResponseHandler.getInstance().registerObserver(key, observer);

        mConnectionTask = new ConnectionTask(ctx);
        ConnectionRequest req = new ConnectionRequest();


        //TODO : set all request data

        mConnectionTask.execute(req);

    }

    public void registerUser(Activity ctx, Observer observer, String key, UserObject mUserObject) {

        ConnectionResponseHandler.getInstance().registerObserver(key, observer);

        mConnectionTask = new ConnectionTask(ctx);
        ConnectionRequest req = new ConnectionRequest();


        //TODO : set all request data

        mConnectionTask.execute(req);

    }

    public void bookMaid(Activity ctx, Observer observer, String key, BookedItem item) {

        ConnectionResponseHandler.getInstance().registerObserver(key, observer);

        mConnectionTask = new ConnectionTask(ctx);
        ConnectionRequest req = new ConnectionRequest();


        //TODO : set all request data

        mConnectionTask.execute(req);

    }

    public void getBookingHistory(Activity ctx, Observer observer, String key) {

        ConnectionResponseHandler.getInstance().registerObserver(key, observer);

        mConnectionTask = new ConnectionTask(ctx);
        ConnectionRequest req = new ConnectionRequest();


        //TODO : set all request data

        mConnectionTask.execute(req);

    }


}