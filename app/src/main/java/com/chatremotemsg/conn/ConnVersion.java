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
public class ConnVersion extends AsyncTask<Void, Void,Double> {
    private String serverIP;

    public ConnVersion(String serverIP) {
        this.serverIP = serverIP;

    }

    @Override
    protected Double doInBackground(Void... params) {
        double u = 0.0;
        try {

            CallHandler callHandler = new CallHandler();
            Client client = new Client(serverIP, 53000, callHandler);
            Services services = (Services) client.getGlobal(Services.class);


            u = services.getVersion();



            client.close();


        } catch (IOException e) {
            Log.e("Login Chat", e.getMessage());
        }

        return u;

    }
}