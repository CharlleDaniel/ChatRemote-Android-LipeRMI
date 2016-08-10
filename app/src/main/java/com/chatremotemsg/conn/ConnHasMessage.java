package com.chatremotemsg.conn;

import android.os.AsyncTask;
import android.util.Log;

import com.server.Services;

import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

import java.io.IOException;

/**
 * Created by Charlle Daniel on 15/04/2016.
 */


public class ConnHasMessage extends AsyncTask<Void, Void,Boolean> {
    private String ipServer;
    private int id;

    public ConnHasMessage(String ipServer, int id){
        this.ipServer=ipServer;
        this.id= id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean teste =false;
        try {

            CallHandler callHandler = new CallHandler();
            Client client = new Client(ipServer, 53000, callHandler);
            Services services = (Services) client.getGlobal(Services.class);

            teste = services.hasMessage(id);


            client.close();


        } catch (IOException e) {
            Log.e("Login Chat", e.getMessage());
        }

        return teste;
    }


}