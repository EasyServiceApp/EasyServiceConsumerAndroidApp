package com.service.easyservice.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service.easyservice.R;

/**
 * Created by Smile on 17-03-2017.
 */

public class AboutUs extends Fragment {


//    Context activityContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_about_us, container, false);

        return layout;

    }
}