package com.chatremotemsg.controller;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chatremotemsg.ChatActivity;
import com.chatremotemsg.conn.ConnGetMessages;
import com.chatremotemsg.conn.ConnHasLogged;
import com.chatremotemsg.conn.ConnHasMessage;
import com.chatremotemsg.conn.ConnLogin;
import com.chatremotemsg.conn.ConnUser;
import com.chatremotemsg.conn.ConnUsers;
import com.chatremotemsg.conn.ConnVersion;
import com.chatremotemsg.database.BD;
import com.chatremotemsg.extras.Conversation;
import com.chatremotemsg.fragments.FragChat;
import com.chatremotemsg.fragments.FragUsers;
import com.server.Message;
import com.server.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by Charlle Daniel on 15/04/2016.
 */
public class ClientController  {
    private final String ipServer;
    private List<User> users;
    private Map<Integer, Conversation> conversationMap;
    private List<Integer> conversations;
    private BD bd;
    private User profile;
    private  static Context context;
    private final String TAG_DEFAULT="ChateRemote";
    private final String TAG_LOGGED="logged";
    private double version;
    private Thread hasMessage;

    public ClientController(Context context){
        bd= new BD(context);
        this.context=context;
        ipServer="192.168.43.90";
        conversationMap= new HashMap<>();
        conversations= new ArrayList<>();
        version=bd.buscarVersion();
        users=bd.buscarUser();
        List<User>profile=bd.buscarProfile();
        if(profile.size()>0){
            this.profile=profile.get(profile.size()-1);
        }
    }

    public boolean login(String name,byte[] img, String number){
        boolean teste=false;
        User user;

        try {

            user= new ConnLogin(ipServer,name,img,number ).execute().get();
            if(user!=null){
                bd.inserirProfile(user);
                profile=user;
                teste= true;
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return teste;
    }

    public boolean hasLogged(String number){
        boolean teste=false;
        try {
            teste = new ConnHasLogged(ipServer,number).execute().get().booleanValue();
            if(teste== false && profile !=null){
                bd.deletarProfile(profile);
            }else if( teste==true && profile == null){
                bluidProfile(number);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return teste;
    }
    public User bluidProfile(String number){
        User user=null;
        try {
            if(profile==null){
                user=new ConnUser(ipServer,number).execute().get();
                bd.inserirProfile(user);
                profile=user;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getIPServer(){
        return ipServer;
    }

    public void refreshUsers(ProgressBar progressBar){

        try {
            double versionServe=new ConnVersion(ipServer).execute().get().doubleValue();

            if(version!= versionServe){
                progressBar.setVisibility(View.VISIBLE);
                version=versionServe;
                List<User> temp;
                if(profile!=null){
                    bd.inserirVersion(version,profile.getId());
                    temp= new ConnUsers(ipServer, profile.getId()).execute().get();
                    FragUsers.mAdapter.update(temp);
                    progressBar.setVisibility(View.GONE);
                    for(User u :temp){
                        boolean teste =false;
                        for(User us:users){
                            if(u.getId()==us.getId()){
                                teste=true;
                            }
                        }
                        if(teste==false){
                            bd.inserirUser(u);
                            users.add(u);
                        }

                    }

                }

            }else{
                if(FragUsers.mAdapter!=null){
                    FragUsers.mAdapter.update(users);
                }
                progressBar.setVisibility(View.GONE);

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public User getProfile(){
        return profile;
    }
    public List<User> getUsers(){
        return users;
    }

    public static void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static byte[] encripitar(byte[] msg){
        byte[] encrypted= new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // Usando chave de 128-bits (16 bytes)
            byte[] chave = "b7047987dadaeab4".getBytes();
            // Encriptando...
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"));
            encrypted = cipher.doFinal(msg);

        } catch( Exception e){
            e.printStackTrace();
        }
        return encrypted;
    }

    public static byte[] descripitar(byte[] msg){
        byte[] decrypted= new byte[0];
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // Usando chave de 128-bits (16 bytes)
            byte[] chave = "b7047987dadaeab4".getBytes();
            // Decriptando...

            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chave, "AES"));
            decrypted = cipher.doFinal(msg);


        }catch (Exception e) {
            e.printStackTrace();
        }
        return  decrypted;
    }

    public void reciveMSG(){
        try {
            List<Message>messages=new ConnGetMessages(ipServer, getProfile().getId()).execute().get();
            for(Message m:messages){
                int id= m.getIdFrom();
                if(conversationMap.containsKey(id)){
                    conversationMap.get(id).setMessages(m);

                }else{
                    Conversation conversation= new Conversation(id);
                    conversation.setMessages(m);
                    conversationMap.put(id,conversation );
                }

                if(!conversations.contains(id)){
                    conversations.add(id);
                    FragChat.mAdapter.update(getConversation(id));
                }

                if(ChatActivity.active && ChatActivity.id==id){
                    ChatActivity.mAdapter.update();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public List<Message> getMessage(int idFrom){
        if(!conversationMap.containsKey(idFrom)){
            Conversation conversation= new Conversation(idFrom);
            conversationMap.put(idFrom,conversation );
        }
        return conversationMap.get(idFrom).getMessages();
    }

    public List<User> getConversations(){
        List<User> temp= new ArrayList<>();

        for (Integer i:conversations){
            for(User u : users){
                if(i==u.getId()){
                    temp.add(u);
                    break;
                }
            }
        }
        return temp;
    }
    public User getConversation(int idTo){
        for(User u : users){
           if(u.getId()==idTo){
                return u;
           }
        }
        return null;
    }



}
