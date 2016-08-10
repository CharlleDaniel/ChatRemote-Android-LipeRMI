package com.chatremotemsg.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chatremotemsg.ChatActivity;
import com.chatremotemsg.MainActivity;
import com.chatremotemsg.R;
import com.chatremotemsg.adapters.AdapterUsers;
import com.chatremotemsg.controller.ClientController;
import com.server.Services;
import com.server.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

/**
 * Created by Charlle Daniel on 08/04/2016.
 */
public class FragUsers extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView mUsers;
    public static AdapterUsers mAdapter;
    private ClientController sistema;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema= MainActivity.sistema;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag_layout_users,container,false);

        buildListUsers();
        return view;

    }

    public void buildListUsers(){
        mUsers=(ListView) view.findViewById(R.id.lv_users);
        mAdapter= new AdapterUsers(getContext(),sistema.getUsers());
        mUsers.addFooterView(new View(getContext()));
        mUsers.setAdapter(mAdapter);

        mUsers.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User u = mAdapter.getItem(position);
        Intent it = new Intent(getActivity(), ChatActivity.class);
        it.putExtra("name", u.getName());
        it.putExtra("id",u.getId());
        startActivity(it);
    }
}
