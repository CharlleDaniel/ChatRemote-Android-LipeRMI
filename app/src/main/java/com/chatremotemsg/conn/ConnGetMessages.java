package com.chatremotemsg.conn;

import android.os.AsyncTask;
import android.util.Log;

import com.server.Message;
import com.server.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

/**
 * Created by Charlle Daniel on 01/06/2016.
 */public class ConnGetMessages extends AsyncTask<Void, Void, List<Message>> {
    private int id;

    private String serverIP;

    public ConnGetMessages(String serverIP, int id){
        this.id=id;
        this.serverIP=serverIP;
    }

    @Override
    protected void onPostExecute(List<Message> msgs) {
        super.onPostExecute(msgs);

    }


    @Override
    protected List<Message> doInBackground(Void... params) {
        List<Message>list= new ArrayList<Message>();
        try {

            CallHandler callHandler = new CallHandler();
            Client client = new Client(serverIP, 53000, callHandler);
            Services testService = (Services) client.getGlobal(Services.class);
            list=testService.getMsg(id);

            client.close();



        } catch (IOException e) {
            Log.e("aQUIII", e.getMessage());
        }

        return list;
    }

}
