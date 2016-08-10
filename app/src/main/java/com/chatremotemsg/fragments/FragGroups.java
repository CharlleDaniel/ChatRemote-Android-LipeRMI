package com.chatremotemsg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatremotemsg.R;

/**
 * Created by Charlle Daniel on 08/04/2016.
 */
public class FragGroups extends android.support.v4.app.Fragment {


    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag_layout_groups,container,false);
        return view;

    }
}
