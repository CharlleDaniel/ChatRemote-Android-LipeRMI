package com.chatremotemsg.conn;

import android.os.AsyncTask;
import android.util.Log;

import com.server.Message;
import com.server.Services;

import java.io.IOException;

import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

/**
 * Created by Charlle Daniel on 01/06/2016.
 */
public class ConnMessage extends AsyncTask<Void, Void, Boolean> {
    private Message message;

    private String serverIP;

    public ConnMessage(String serverIP,Message message){
        this.message=message;
        this.serverIP=serverIP;
    }

    @Override
    protected void onPostExecute(Boolean msg) {
        super.onPostExecute(msg);

    }


    @Override
    protected Boolean doInBackground(Void... params) {
        boolean teste=false;
        try {

            CallHandler callHandler = new CallHandler();
            Client client = new Client(serverIP, 53000, callHandler);
            Services testService = (Services) client.getGlobal(Services.class);
            testService.sendMsg(message);
            teste=true;
            client.close();



        } catch (IOException e) {
            Log.e("aQUIII", e.getMessage());
        }

        return teste;
    }

}
