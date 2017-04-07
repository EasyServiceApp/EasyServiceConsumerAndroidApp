package com.service.easyservice.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.service.easyservice.Landing;
import com.service.easyservice.ManageAddressActivity;
import com.service.easyservice.R;
import com.service.easyservice.models.OTPVerifyResponse;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.util.HashMap;
import java.util.Map;

import static com.service.easyservice.util.Constants.UPDATE_PROFILE_URL;

/**
 * Created by admin on 3/13/17.
 */

public class Profile extends Fragment implements View.OnClickListener, ResponseResult {


//    Context activityContext;
    EditText etName,etNumber,etEmail;
    TextView tvManageAddress,tvFooter;
    Context activityContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        activityContext = getContext();
        etName = (EditText)layout.findViewById(R.id.etName);
        etNumber = (EditText)layout.findViewById(R.id.etNumber);
        etEmail = (EditText)layout.findViewById(R.id.etEmail);
        tvManageAddress = (TextView) layout.findViewById(R.id.tvManageAddress);
        tvManageAddress.setOnClickListener(this);
        tvFooter = (TextView)layout.findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        init();
        return layout;

    }

    public void init()
    {
        OTPVerifyResponse otpVerifyResponse = new AppPreferences(activityContext).getUserInfo();

        etName.setText(otpVerifyResponse.getFirstName());
        etNumber.setText(otpVerifyResponse.getMobile());
        etEmail.setText(otpVerifyResponse.getEmail());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.tvFooter:

                if("".equals(etName.getText().toString()))
                {
                    CommonFunctions.displayDialog(activityContext,"Please enter Name");
                }else if("".equals(etEmail.getText().toString()))
                {
                    CommonFunctions.displayDialog(activityContext,"Please enter Email");
                }else {


                    if (CommonFunctions.isNetworkAvailable(activityContext)) {
                        //request for OTP
                        //update user profile
                        Map<String, String> addApplianceDetailsParameters = new HashMap<>();

                        OTPVerifyResponse otpVerifyResponse = new AppPreferences(activityContext).getUserInfo();

                        addApplianceDetailsParameters.put("appapi", "yes");
                        addApplianceDetailsParameters.put("userid", otpVerifyResponse.getUserId());
                        addApplianceDetailsParameters.put("fname", etName.getText().toString());
                        addApplianceDetailsParameters.put("mobile", otpVerifyResponse.getMobile());
                        addApplianceDetailsParameters.put("email", etEmail.getText().toString());
                        addApplianceDetailsParameters.put("address1", otpVerifyResponse.getAddress1());
                        addApplianceDetailsParameters.put("address2", otpVerifyResponse.getAddress2());
                        addApplianceDetailsParameters.put("address3", otpVerifyResponse.getAddress3());
                        addApplianceDetailsParameters.put("country", "");
                        addApplianceDetailsParameters.put("state", "");
                        addApplianceDetailsParameters.put("city", "");
                        addApplianceDetailsParameters.put("device_id", "");
                        addApplianceDetailsParameters.put("userphone", otpVerifyResponse.getMobile());
                        addApplianceDetailsParameters.put("pobox", "");
                        new VolleyJSONCaller(Profile.this, UPDATE_PROFILE_URL, addApplianceDetailsParameters, Request.Method.POST, false).execute();
                    } else {
                        CommonFunctions.displayDialog(activityContext, getString(R.string.internet_problem));
                    }
                }
                break;

            case R.id.tvManageAddress:
                Intent intent = new Intent(activityContext, ManageAddressActivity.class);
                startActivity(intent);
                break;

            default:

                break;
        }

    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof OTPVerifyResponse)
        {
            OTPVerifyResponse otpVerifyResponse = (OTPVerifyResponse)result;

            if(otpVerifyResponse.getStatus() == 1)
            {
                //store the user response in preference

                new AppPreferences(activityContext).setUserInfo(otpVerifyResponse);

                //navigate to dashboard
                ((Landing)activityContext).setDashboardPage();
            }
            else
            {
                CommonFunctions.displayDialog(getContext(),otpVerifyResponse.getMessage());
            }


        }
    }
}
