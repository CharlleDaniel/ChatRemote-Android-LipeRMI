package com.chatremotemsg.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chatremotemsg.ChatActivity;
import com.chatremotemsg.MainActivity;
import com.chatremotemsg.R;
import com.chatremotemsg.adapters.AdapterUsers;
import com.chatremotemsg.adapters.AdapterUsersChat;
import com.chatremotemsg.controller.ClientController;
import com.server.User;

import java.util.LinkedList;

/**
 * Created by Charlle Daniel on 08/04/2016.
 */
public class FragChat extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    private View view;
    private ClientController sistema;
    private ListView mUsers;
    public static AdapterUsersChat mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema= MainActivity.sistema;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag_layout_chat,container,false);
        buildListChat();
        return view;

    }

    public void buildListChat(){
        mUsers=(ListView) view.findViewById(R.id.lv_chat);
        mAdapter= new AdapterUsersChat(getContext(),sistema.getConversations());
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
