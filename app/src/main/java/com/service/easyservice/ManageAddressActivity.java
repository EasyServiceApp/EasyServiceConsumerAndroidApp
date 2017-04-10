package com.service.easyservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.service.easyservice.models.OTPVerifyResponse;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.util.HashMap;
import java.util.Map;

import static com.service.easyservice.util.Constants.LOCATION_HOME;
import static com.service.easyservice.util.Constants.LOCATION_OTHER;
import static com.service.easyservice.util.Constants.LOCATION_WORK;
import static com.service.easyservice.util.Constants.UPDATE_PROFILE_URL;

public class ManageAddressActivity extends AppCompatActivity implements View.OnClickListener, ResponseResult {

    ImageView ivHomeLocation,ivWorkLocation,ivOtherLocation;

    EditText etHomeAddress,etWorkAddress,etOtherAddress;
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;
    TextView tvFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView)toolbar.findViewById(R.id.ivProfile);
        ivProfile.setVisibility(View.GONE);
        ivDrawerHandel = (ImageView)toolbar.findViewById(R.id.ivDrawerHandel);
        ivDrawerHandel.setImageResource(R.drawable.toolbar_back);
        ivDrawerHandel.setOnClickListener(this);
        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);

        tvFooter = (TextView) findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        etHomeAddress = (EditText) findViewById(R.id.etHomeAddress);
        etWorkAddress = (EditText) findViewById(R.id.etWorkAddress);
        etOtherAddress = (EditText) findViewById(R.id.etOtherAddress);
        ivHomeLocation = (ImageView)findViewById(R.id.ivHomeLocation);
        ivHomeLocation.setOnClickListener(this);
        ivWorkLocation = (ImageView)findViewById(R.id.ivWorkLocation);
        ivWorkLocation.setOnClickListener(this);
        ivOtherLocation = (ImageView)findViewById(R.id.ivOtherLocation);
        ivOtherLocation.setOnClickListener(this);

        OTPVerifyResponse otpVerifyResponse = new AppPreferences(this).getUserInfo();
        etHomeAddress.setText(otpVerifyResponse.getAddress1());
        etWorkAddress.setText(otpVerifyResponse.getAddress2());
        etOtherAddress.setText(otpVerifyResponse.getAddress3());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ivHomeLocation:
                //open map activity
                Intent intentHome = new Intent(ManageAddressActivity.this,AddressMapActivity.class);
                startActivityForResult(intentHome,LOCATION_HOME);
                break;
            case R.id.ivWorkLocation:
                //open map activity
                Intent intentWork = new Intent(ManageAddressActivity.this,AddressMapActivity.class);
                startActivityForResult(intentWork,LOCATION_WORK);
                break;
            case R.id.ivOtherLocation:
                //open map activity
                Intent intentOther = new Intent(ManageAddressActivity.this,AddressMapActivity.class);
                startActivityForResult(intentOther,LOCATION_OTHER);
                break;
            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(ManageAddressActivity.this);
                break;
            case R.id.ivDrawerHandel:
                finish();
                break;
            case R.id.tvFooter:
                if (CommonFunctions.isNetworkAvailable(this)) {
                    //request for OTP
                    //update user profile
                    Map<String, String> addApplianceDetailsParameters = new HashMap<>();

                    OTPVerifyResponse otpVerifyResponse = new AppPreferences(this).getUserInfo();

                    addApplianceDetailsParameters.put("appapi", "yes");
                    addApplianceDetailsParameters.put("userid", otpVerifyResponse.getUserId());
                    addApplianceDetailsParameters.put("fname", otpVerifyResponse.getFirstName());
                    addApplianceDetailsParameters.put("mobile", otpVerifyResponse.getMobile());
                    addApplianceDetailsParameters.put("email", otpVerifyResponse.getEmail());
                    addApplianceDetailsParameters.put("address1", etHomeAddress.getText().toString());
                    addApplianceDetailsParameters.put("address2", etWorkAddress.getText().toString());
                    addApplianceDetailsParameters.put("address3", etOtherAddress.getText().toString());
                    addApplianceDetailsParameters.put("country", "");
                    addApplianceDetailsParameters.put("state", "");
                    addApplianceDetailsParameters.put("city", "");
                    addApplianceDetailsParameters.put("device_id", "");
                    addApplianceDetailsParameters.put("userphone", otpVerifyResponse.getMobile());
                    addApplianceDetailsParameters.put("pobox", "");
                    new VolleyJSONCaller(this, UPDATE_PROFILE_URL, addApplianceDetailsParameters, Request.Method.POST, false).execute();
                } else {
                    CommonFunctions.displayDialog(this, getString(R.string.internet_problem));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == LOCATION_HOME) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                etHomeAddress.setText(data.getStringExtra("address"));
                // Do something with the contact here (bigger example below)
            }
        }

        if (requestCode == LOCATION_WORK) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                etWorkAddress.setText(data.getStringExtra("address"));
                // Do something with the contact here (bigger example below)
            }
        }

        if (requestCode == LOCATION_OTHER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                etOtherAddress.setText(data.getStringExtra("address"));
                // Do something with the contact here (bigger example below)
            }
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

                new AppPreferences(this).setUserInfo(otpVerifyResponse);

                finish();
            }
            else
            {
                CommonFunctions.displayDialog(this,otpVerifyResponse.getMessage());
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
