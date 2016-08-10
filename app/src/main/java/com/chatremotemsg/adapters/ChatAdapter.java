package com.chatremotemsg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatremotemsg.MainActivity;
import com.chatremotemsg.R;
import com.chatremotemsg.controller.ClientController;
import com.server.Message;
import com.server.User;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by Charlle Daniel on 24/03/2016.
 */
public class ChatAdapter extends BaseAdapter {

    private List<Message>mensagens;
    private Context context;
    private int id;
    private ClientController sistema;

    public ChatAdapter(Context context, List<Message> mensagens){
        id= MainActivity.sistema.getProfile().getId();
        this.context=context;
        this.mensagens=mensagens;
        sistema=MainActivity.sistema;
    }
    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Object getItem(int position) {
        return mensagens.get(position);
    }

    public synchronized void update(){
        super.notifyDataSetChanged();

    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message mensagem= mensagens.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout =inflater.inflate(R.layout.chat_adapter, null);
        RelativeLayout rlTo=(RelativeLayout)layout.findViewById(R.id.rl_to);
        RelativeLayout rlFrom=(RelativeLayout)layout.findViewById(R.id.rl_from);

        if(mensagem.getIdFrom()==id){
            rlTo.setVisibility(View.GONE);
            rlFrom.setVisibility(View.VISIBLE);
            TextView tvMSG = (TextView) layout.findViewById(R.id.tv_msg);
            String txt = null;
            try {
                txt = new String(ClientController.descripitar(mensagem.getMsg()),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            tvMSG.setText(txt);
        }else{
            rlFrom.setVisibility(View.GONE);
            rlTo.setVisibility(View.VISIBLE);
            TextView tvMSG = (TextView) layout.findViewById(R.id.tv_msg_to);
            String txt = null;
            try {
                txt = new String(ClientController.descripitar(mensagem.getMsg()),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            tvMSG.setText(txt);
        }


        return layout;
    }
}
