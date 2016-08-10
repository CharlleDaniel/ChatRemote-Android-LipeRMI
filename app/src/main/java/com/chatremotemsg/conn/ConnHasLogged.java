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


public class ConnHasLogged extends AsyncTask<Void, Void,Boolean> {
    private String ipServer;
    private String number;

    public ConnHasLogged(String ipServer, String number){
        this.ipServer=ipServer;
        this.number= number;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean teste =false;
        try {

            CallHandler callHandler = new CallHandler();
            Client client = new Client(ipServer, 53000, callHandler);
            Services services = (Services) client.getGlobal(Services.class);

            teste = services.hasLogged(number);


            client.close();


        } catch (IOException e) {
            Log.e("Login Chat", e.getMessage());
        }

        return teste;
    }


}