package com.chatremotemsg.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chatremotemsg.fragments.FragChat;
import com.chatremotemsg.fragments.FragGroups;
import com.chatremotemsg.fragments.FragUsers;

/**
 * Created by CharlleNot on 09/10/2015.
 */
public class TabAdapters extends FragmentPagerAdapter {
    private Context mContext;
    private String[]titles= {"Usuarios","Conversas","Grupos"};

    public TabAdapters(FragmentManager fm, Context c) {
        super(fm);
        this.mContext=c;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment frag= null;
        if(i==0){
            frag= new FragUsers();
        }else if (i==1){
            frag= new FragChat();
        }else if (i==2){
            frag= new FragGroups();
        }

        Bundle b = new Bundle();
        b.putInt("position",i);

        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return (titles[position]);
    }
}
