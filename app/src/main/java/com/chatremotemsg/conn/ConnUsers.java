package com.chatremotemsg.conn;

import android.os.AsyncTask;
import android.util.Log;

import com.server.Services;
import com.server.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

/**
 * Created by Charlle Daniel on 15/04/2016.
 */
public class ConnUsers extends AsyncTask<Void, Void,List<User>> {

    private String serverIP;
    private int id;

    public ConnUsers(String serverIP, int id) {
        this.serverIP = serverIP;
        this.id=id;
    }

    @Override
    protected List<User> doInBackground(Void... params) {
        List<User> u = new LinkedList<>();
        try {

            CallHandler callHandler = new CallHandler();
            Client client = new Client(serverIP, 53000, callHandler);
            Services services = (Services) client.getGlobal(Services.class);


            u = services.getUsers(id);


            client.close();


        } catch (IOException e) {
            Log.e("Login Chat", e.getMessage());
        }

        return u;

    }
}