package com.hack.xapp.http;


import android.os.AsyncTask;
import android.util.Log;

import com.hack.xapp.activity.MainActivity;
import com.hack.xapp.model.ConnectionRequest;
import com.hack.xapp.model.ConnectionResponse;
import com.hack.xapp.model.Maid;
import com.hack.xapp.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ConnectionTask extends AsyncTask<ConnectionRequest, Void, ConnectionResponse> {

    private final String TAG = "ConnectionTask";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //show progress dialog
    }

    @Override
    protected ConnectionResponse doInBackground(ConnectionRequest... params) {

        String data = "";
        String strUrl = params[0].getURL();
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d(TAG, "Exception while fetching data " + e.toString());
        } finally {
            if (iStream != null) {
                try {
                    iStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            urlConnection.disconnect();
        }
        ConnectionResponse connectionResponse = new ConnectionResponse();
        connectionResponse.setData(data);
        return connectionResponse;
    }

    List<Maid> parseMaidsList(String data) {


        return null;
    }

    @Override
    protected void onPostExecute(ConnectionResponse connectionResponse) {
        super.onPostExecute(connectionResponse);

        //send back data via callback
        if (connectionResponse.getKey() == Util.MAIN_ACTIVITY) {
            ((MainActivity.MainResponseObserver) ConnectionResponseHandler.getInstance().getObserver(connectionResponse.getKey())).maidsList(parseMaidsList(connectionResponse.getData()));

        }

    }


}