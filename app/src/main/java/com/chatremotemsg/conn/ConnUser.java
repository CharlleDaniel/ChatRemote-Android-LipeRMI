package com.chatremotemsg.conn;

import android.os.AsyncTask;
import android.util.Log;

import com.server.Services;
import com.server.User;

import java.io.IOException;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

/**
 * Created by Charlle Daniel on 15/04/2016.
 */
public class ConnUser extends AsyncTask<Void, Void,User> {
    private String serverIP;
    private String number;

    public ConnUser(String serverIP,String number) {
        this.number=number;
        this.serverIP = serverIP;
    }

    @Override
    protected User doInBackground(Void... params) {
        User u = null;
        try {

            CallHandler callHandler = new CallHandler();
            Client client = new Client(serverIP, 53000, callHandler);
            Services services = (Services) client.getGlobal(Services.class);

            u = services.getUser(number);


            client.close();


        } catch (IOException e) {
            Log.e("Login Chat", e.getMessage());
        }

        return u;

    }
}