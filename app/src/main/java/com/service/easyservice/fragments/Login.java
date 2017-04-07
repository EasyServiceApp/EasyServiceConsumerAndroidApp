package com.service.easyservice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.service.easyservice.Landing;
import com.service.easyservice.R;
import com.service.easyservice.models.OTPVerifyResponse;
import com.service.easyservice.models.RequestOTPResponse;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.util.Constants;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 3/12/17.
 */

public class Login extends Fragment implements View.OnClickListener,Constants,ResponseResult {


    Context activityContext;

    TextView tvOTP,tvResendOTP,tvChangeNumber,tvFooter;
    EditText etMobile,etVerifyOTP,etName,etEmail;
    RelativeLayout rlLogin,rlVerification;
    Spinner spinnerCountry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);

        activityContext = getContext();
        tvOTP = (TextView)layout.findViewById(R.id.tvOTP);
        tvResendOTP = (TextView)layout.findViewById(R.id.tvResendOTP);
        tvChangeNumber = (TextView)layout.findViewById(R.id.tvChangeNumber);
        tvFooter = (TextView)layout.findViewById(R.id.tvFooter);
        rlLogin = (RelativeLayout) layout.findViewById(R.id.rlLogin);
        rlVerification = (RelativeLayout) layout.findViewById(R.id.rlVerification);
        etMobile = (EditText) layout.findViewById(R.id.etMobile);
        etName = (EditText) layout.findViewById(R.id.etName);
        etEmail = (EditText) layout.findViewById(R.id.etEmail);
        etVerifyOTP = (EditText) layout.findViewById(R.id.etVerifyOTP);
        spinnerCountry = (Spinner) layout.findViewById(R.id.spinnerCountry);

        etMobile.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            generateOTP();
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });
        etVerifyOTP.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            verifyOTP();
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

        tvOTP.setOnClickListener(this);
        tvResendOTP.setOnClickListener(this);
        tvChangeNumber.setOnClickListener(this);
        tvFooter.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.tvOTP:
            case R.id.tvResendOTP:
                //request for OTP for the mobile number provided
                generateOTP();
                break;
            case R.id.tvChangeNumber:
                rlLogin.setVisibility(View.VISIBLE);
                rlVerification.setVisibility(View.GONE);
                tvFooter.setText("");
                break;
            case R.id.tvFooter:
                if(!"".equals(tvFooter.getText().toString()))
                {
                    //verify OTP
                    verifyOTP();

                }

            default:

        }
    }

    public void generateOTP()
    {
        //validate the number
        //validate mobile number
        String[] countryCodeArray = getResources().getStringArray(R.array.country_code);
        if("".equals(etName.getText().toString().trim()))
        {
            CommonFunctions.displayDialog(activityContext,"Please enter your Name.");
        }else if("".equals(etEmail.getText().toString().trim()))
        {
            CommonFunctions.displayDialog(activityContext,"Please enter your Email.");
        }else if("".equals(etMobile.getText().toString().trim()))
        {
            CommonFunctions.displayDialog(activityContext,"Please enter Mobile Number.");
        }
        else if("+91".equals(countryCodeArray[spinnerCountry.getSelectedItemPosition()]) && etMobile.getText().toString().trim().length() != 10)
        {
            CommonFunctions.displayDialog(activityContext,"Please enter a 10 digit Mobile Number to proceed.");
        }else if(!"+91".equals(countryCodeArray[spinnerCountry.getSelectedItemPosition()]) && etMobile.getText().toString().trim().length() != 9)
        {
            CommonFunctions.displayDialog(activityContext,"Please enter a 9 digit Mobile Number to proceed.");
        }
        else
        {
            if(CommonFunctions.isNetworkAvailable(activityContext)) {
                //request for OTP

                Map<String, String> addApplianceDetailsParameters = new HashMap<>();
                addApplianceDetailsParameters.put("appapi", "yes");
                addApplianceDetailsParameters.put("userphone", etMobile.getText().toString().trim());
                addApplianceDetailsParameters.put("cccode", countryCodeArray[spinnerCountry.getSelectedItemPosition()]);
                addApplianceDetailsParameters.put("fname", etName.getText().toString().trim());
                addApplianceDetailsParameters.put("emailz", etEmail.getText().toString().trim());
                new VolleyJSONCaller(Login.this, REQUEST_OTP, addApplianceDetailsParameters, Request.Method.POST, false).execute();
            }
            else
            {
                CommonFunctions.displayDialog(activityContext,getString(R.string.internet_problem));
            }

        }
    }


    public void verifyOTP()
    {
        //verify OTP Number
        if("".equals(etVerifyOTP.getText().toString().trim()))
        {
            CommonFunctions.displayDialog(activityContext,"Please enter your One Time Password(OTP).");
        }
        else
        {
            if(CommonFunctions.isNetworkAvailable(activityContext)) {
                //request for OTP
                Map<String, String> verifyOTPParameters = new HashMap<>();
                verifyOTPParameters.put("appapi", "yes");
                verifyOTPParameters.put("userphone", etMobile.getText().toString().trim());
                verifyOTPParameters.put("fname", etName.getText().toString().trim());
                verifyOTPParameters.put("userotp", etVerifyOTP.getText().toString().trim());
                new VolleyJSONCaller(Login.this, VERIFY_OTP, verifyOTPParameters, Request.Method.POST, false).execute();
            }
            else
            {
                CommonFunctions.displayDialog(activityContext,getString(R.string.internet_problem));
            }

        }
    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof RequestOTPResponse)
        {
            RequestOTPResponse requestOTPResponse = (RequestOTPResponse)result;

            if(requestOTPResponse.getStatus() == 1)
            {
                CommonFunctions.displayDialog(getContext(),requestOTPResponse.getMessage());
                //request for OTP
                rlLogin.setVisibility(View.GONE);
                rlVerification.setVisibility(View.VISIBLE);
                tvFooter.setText("Verify OTP");
            }
            else
            {
                CommonFunctions.displayDialog(getContext(),requestOTPResponse.getMessage());
            }


        }else if(result instanceof OTPVerifyResponse)
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
