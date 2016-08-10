package com.chatremotemsg;

import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chatremotemsg.adapters.ChatAdapter;
import com.chatremotemsg.conn.ConnMessage;
import com.chatremotemsg.controller.ClientController;
import com.server.Message;
import com.server.Services;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

/**
 * Created by Charlle Daniel on 11/04/2016.
 */
public class ChatActivity extends AppCompatActivity{

    private List<Message> mensagens;
    private ListView listChat;
    public static ChatAdapter mAdapter;
    private String serverIP;
    private String name;
    public static int id;
    private EditText mEdMen;
    private Message message;
    private ClientController sistema;
    public static boolean active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sistema=MainActivity.sistema;

        name=getIntent().getStringExtra("name");
        id=getIntent().getIntExtra("id", 0);
        serverIP = sistema.getIPServer();
        mensagens=sistema.getMessage(id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(name);

        setSupportActionBar(toolbar);

        accessViews();
        buildListChat();
    }

    public void buildListChat(){
        listChat= (ListView) findViewById(R.id.lv_chat);
        mAdapter=new ChatAdapter(this,mensagens);
        listChat.setAdapter(mAdapter);
        listChat.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        listChat.setVerticalScrollBarEnabled(false);


    }

    public void accessViews(){
        mEdMen = (EditText) findViewById(R.id.ed_menssagem);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String type=prefs.getString(getString(R.string.key_send_enter),getString(R.string.pref_send_default));

        if(type.equals("Sim")){
            mEdMen.setInputType(InputType.TYPE_CLASS_TEXT);
            mEdMen.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_UP) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        sendMessage(v);
                        return true;
                    }
                    return false;
                }
            });
        }
    }
    public void sendMessage(View v) {

        if(mEdMen.getText().toString().trim().length()>0){

            byte[] msgCript= sistema.encripitar(mEdMen.getText().toString().trim().getBytes());

            message= new Message(msgCript,id,sistema.getProfile().getId(),false);
            mensagens.add(message);
            mEdMen.setText("");

            try {
                boolean teste = new ConnMessage(sistema.getIPServer(),message).execute().get().booleanValue();
                if(teste){
                    message.setSend(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            buildListChat();

        }else{
            Toast.makeText(this,"Informe um texto.",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

}
