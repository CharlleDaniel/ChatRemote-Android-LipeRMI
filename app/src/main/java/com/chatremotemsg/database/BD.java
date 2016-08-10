package com.chatremotemsg.database;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.server.Message;
import com.server.User;


public class BD {
    private SQLiteDatabase bd;

    public BD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserirUser(User usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getName());
        valores.put("foto", usuario.getFoto());
        valores.put("number", usuario.getNumber());
        valores.put("_id", usuario.getId());

        bd.insert("user", null, valores);
    }

    public void atualizarUser(User usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getName());
        valores.put("foto", usuario.getFoto());
        valores.put("number", usuario.getNumber());
        bd.update("user", valores, "_id = ?", new String[]{"" + usuario.getId()});

    }



    public void deletarUser(User usuario) {
        bd.delete("user", "_id = " + usuario.getId(), null);
    }


    public List<User> buscarUser() {
        List<User> list = new ArrayList<User>();
        String[] colunas = new String[]{"_id", "nome","foto","number"};

        Cursor cursor = bd.query("user", colunas, null, null, null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                User u = new User();
                u.setId(cursor.getInt(0));
                u.setName(cursor.getString(1));
                u.setFoto(cursor.getBlob(2));
                u.setNumber(cursor.getString(3));
                list.add(u);

            } while (cursor.moveToNext());
        }

        return (list);
    }


    public void inserirMessage(Message msg) {
        ContentValues valores = new ContentValues();
        valores.put("idFrom", msg.getIdFrom());
        valores.put("idTo", msg.getIdTo());
        valores.put("msg", msg.getMsg());
        valores.put("isSeng", ""+msg.isSend());

        bd.insert("message", null, valores);
    }

    public void atualizarMessage(Message msg) {
        ContentValues valores = new ContentValues();
        valores.put("idFrom", msg.getIdFrom());
        valores.put("idTo", msg.getIdTo());
        valores.put("msg", msg.getMsg());
        valores.put("isSeng", ""+msg.isSend());
        bd.update("message", valores, "_id = ?", new String[]{"" + msg.getId()});

    }



    public void deletarMessage(Message msg) {
        bd.delete("message", "_id = " + msg.getId(), null);
    }


    public List<Message> buscarMessage() {
        List<Message> list = new ArrayList<>();
        String[] colunas = new String[]{"_id", "idFrom","idTo","msg","isSend"};

        Cursor cursor = bd.query("message", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Message m = new Message();
                m.setId(cursor.getInt(0));
                m.setIdFrom(cursor.getInt(1));
                m.setIdTo(cursor.getInt(2));
                m.setMsg(cursor.getBlob(3));
                if(cursor.getString(4).contains("true")){
                    m.setSend(true);
                }else{
                    m.setSend(false);
                }

                list.add(m);

            } while (cursor.moveToNext());
        }

        return (list);
    }

    public void inserirProfile(User usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getName());
        valores.put("foto", usuario.getFoto());
        valores.put("number", usuario.getNumber());
        valores.put("_id", usuario.getId());
        bd.insert("profile", null, valores);
    }
    public void inserirVersion(double version,int id) {
        ContentValues valores = new ContentValues();
        valores.put("Version", version);

        bd.update("profile", valores, "_id = ?", new String[]{"" + id});
    }


    public void atualizarProfile(User usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getName());
        valores.put("foto", usuario.getFoto());
        valores.put("number", usuario.getNumber());
        bd.update("profile", valores, "_id = ?", new String[]{"" + usuario.getId()});

    }



    public void deletarProfile(User usuario) {
        bd.delete("Profile", "_id = " + usuario.getId(), null);
    }
    public double buscarVersion() {
        double version=0.0;
        String[] colunas = new String[]{"version"};

        Cursor cursor = bd.query("Profile", colunas, null, null, null, null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                version=cursor.getDouble(0);

            } while (cursor.moveToNext());
        }

        return version;
    }

    public List<User> buscarProfile() {
        List<User> list = new ArrayList<User>();
        String[] colunas = new String[]{"_id", "nome","foto","number"};

        Cursor cursor = bd.query("Profile", colunas, null, null, null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                User u = new User();
                u.setId(cursor.getInt(0));
                u.setName(cursor.getString(1));
                u.setFoto(cursor.getBlob(2));
                u.setNumber(cursor.getString(3));
                list.add(u);

            } while (cursor.moveToNext());
        }

        return (list);
    }
}