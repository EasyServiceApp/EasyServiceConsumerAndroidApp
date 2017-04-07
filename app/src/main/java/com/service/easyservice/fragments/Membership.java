package com.service.easyservice.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.service.easyservice.MembershipDetailActivity;
import com.service.easyservice.R;

/**
 * Created by Smile on 25-03-2017.
 */

public class Membership extends Fragment implements View.OnClickListener {


//    Context activityContext;
    TextView tvCurrentUpgrade,tvPlan1Upgrade,tvPlan2Upgrade;

    RadioGroup rgOS;

    RadioButton radio0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_membership, container, false);
        tvCurrentUpgrade = (TextView)layout.findViewById(R.id.tvCurrentUpgrade);
        tvPlan1Upgrade = (TextView)layout.findViewById(R.id.tvPlan1Upgrade);
        tvPlan2Upgrade = (TextView)layout.findViewById(R.id.tvPlan2Upgrade);
        rgOS = (RadioGroup) layout.findViewById(R.id.rgOS);
        radio0 = (RadioButton) layout.findViewById(R.id.radio0);




        tvCurrentUpgrade.setOnClickListener(this);
        tvPlan1Upgrade.setOnClickListener(this);
        tvPlan2Upgrade.setOnClickListener(this);

        return layout;

    }

    public void openURL(String url)
    {
        Intent i = new Intent(getContext(),MembershipDetailActivity.class);
        i.putExtra("url",url);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId())
        {
            case R.id.tvCurrentUpgrade:

                if(rgOS.getCheckedRadioButtonId() == R.id.radio0) {
                    openURL("http://easyservices.me/app/membership.php?plan=standard");
                }
                else{
                    openURL("http://easyservices.me/app/membership_ios.php?plan=standard");
                }
                break;
            case R.id.tvPlan1Upgrade:
                if(rgOS.getCheckedRadioButtonId() == R.id.radio0) {
                    openURL("http://easyservices.me/app/membership.php?plan=sar_125");
                }
                else{
                    openURL("http://easyservices.me/app/membership_ios.php?plan=sar_350");
                }
                break;
            case R.id.tvPlan2Upgrade:
                if(rgOS.getCheckedRadioButtonId() == R.id.radio0) {
                    openURL("http://easyservices.me/app/membership.php?plan=sar_70");
                }
                else{
                    openURL("http://easyservices.me/app/membership_ios.php?plan=sar_250");
                }
                break;

        }
    }
}