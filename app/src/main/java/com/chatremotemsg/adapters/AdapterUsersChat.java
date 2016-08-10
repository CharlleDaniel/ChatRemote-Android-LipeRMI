package com.chatremotemsg.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chatremotemsg.R;
import com.chatremotemsg.extras.CircleImageView;
import com.server.User;

import java.util.List;

public class AdapterUsersChat extends BaseAdapter {

    private static List<User> users;
    private Context context;

    public AdapterUsersChat(Context context, List<User> users){
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return this.users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public synchronized void update(User u){
        if(u!=null){
            users.add(u);
        }
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User user = users.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.adapter_users, null);

        final CircleImageView img = (CircleImageView)view.findViewById(R.id.CIV_User);
        TextView tv = (TextView) view.findViewById(R.id.userTV);

        if(user.getFoto()==null){
            img.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getFoto(), 0, user.getFoto().length);
            img.setImageBitmap(bitmap);

        }

        tv.setText(user.getName());

        return view;
    }

}
