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


public class ConnLogin extends AsyncTask<Void, Void,User> {
    private String ipServer;
    private String name;
    private byte[]img;
    private String number;

    public ConnLogin(String ipServer,String name,byte[] img, String number){
        this.ipServer=ipServer;
        this.name=name;
        this.img=img;
        this.number=number;

    }

    @Override
    protected User doInBackground(Void... params) {
        User user =null;
        try {

            CallHandler callHandler = new CallHandler();
            Client client = new Client(ipServer, 53000, callHandler);
            Services services = (Services) client.getGlobal(Services.class);

            user = services.login(name,img,number);


            client.close();


        } catch (IOException e) {
            Log.e("Login Chat", e.getMessage());
        }

        return user;
    }


}