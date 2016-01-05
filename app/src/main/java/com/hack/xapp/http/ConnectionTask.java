package com.hack.xapp.http;


import android.os.AsyncTask;
import android.util.Log;

import com.hack.xapp.activity.MainActivity;
import com.hack.xapp.model.ConnectionRequest;
import com.hack.xapp.model.ConnectionResponse;
import com.hack.xapp.model.Maid;
import com.hack.xapp.util.Util;

import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class ConnectionTask extends AsyncTask<ConnectionRequest, Void, ConnectionResponse> {

    private final String TAG = "ConnectionTask";
    static XMPPConnection connection = null;
    final static PacketFilter filter = new MessageTypeFilter(Message.Type.normal);
    final String hostaddr = "52.74.30.11";
    final int port = 5222;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //show progress dialog
    }

    public void establishConnection() {
        Log.i(TAG, "establishConnection");
        ConnectionConfiguration config = new ConnectionConfiguration(hostaddr,
                port, hostaddr);

        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        if (connection == null)
            connection = new XMPPTCPConnection(config);
        try {
            connection.connect();
            Log.i(TAG, "establishConnection connected");
        } catch (SaslException e1) {
            e1.printStackTrace();
        } catch (XMPPException e1) {
            e1.printStackTrace();
        } catch (SmackException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    protected ConnectionResponse doInBackground(ConnectionRequest... params) {

        String data = "";
        String strUrl = params[0].getURL();
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        Log.i(TAG, "ServerCommunicationTask doInBackground");
        String type = params[0].getURL();
        if (connection == null) {
            establishConnection();
        }
        try {

            if (type.equals(Util.EVENT_USER_LOGIN)) {
                connection.login(params[0].getUserName(), params[2].getPassword());
                Log.i(TAG, "ServerLoginTask login success");
            } else if (type.equals(Util.EVENT_REGISTER_MAID)) {
                connection.addPacketListener(mPacketListener, filter);
                Log.i(TAG, "RegisterPacketListnerTask Listner added");
            } /*else if (type.equals(EVENT_SEND_MESSAGE)) {
                Message msg = new Message(params[1], Message.Type.chat);
                msg.setBody(params[2]);
                msg.setTo(params[1]);
                connection.sendPacket(msg);
                Log.i(TAG, "SendChatMsg msg sent");
            }*/
        } catch (SaslException e1) {
            e1.printStackTrace();
        } catch (XMPPException e1) {
            e1.printStackTrace();
        } catch (SmackException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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

    PacketListener mPacketListener = new PacketListener() {

        @Override
        public void processPacket(Packet packet) throws SmackException.NotConnectedException {
            Log.i(TAG, "mPacketListener processPacket");
            Message message = (Message) packet;
            String sub = message.getSubject();
            MainActivity.MainResponseObserver ob = null;
            if (Util.MAIN_ACTIVITY.equals(sub)) {
                ob = (MainActivity.MainResponseObserver) ConnectionResponseHandler.getInstance().getObserver(Util.MAIN_ACTIVITY);
            }
            if (message.getBody() != null) {
                String fromName = StringUtils.parseBareAddress(message
                        .getFrom());
                Log.i(TAG, "mPacketListener Text Recieved from " + fromName);
                Log.i(TAG, message.getBody());

                if (ob != null) {
                    ob.maidsList(parseMaidsList(message.getBody()));
                }
            } else {
                if (ob != null) {
                    ob.onError("No response");
                }
            }
        }
    };

    List<Maid> parseMaidsList(String data) {
        //TODO : need to parse data and return maids list
        return null;
    }


}