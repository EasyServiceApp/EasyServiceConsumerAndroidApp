package com.service.easyservice.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.service.easyservice.Landing;
import com.service.easyservice.MyDevicesActivity;
import com.service.easyservice.R;
import com.service.easyservice.models.AddApplianceResponse;
import com.service.easyservice.models.DashboardResponse;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.service.easyservice.util.Constants.ADD_APPLIANCE_USER_URL;
import static com.service.easyservice.util.Constants.DASHBOARD_DETAILS_URL;

/**
 * Created by Smile on 15-04-2016.
 */
public class Dashboard extends Fragment implements View.OnClickListener,ResponseResult {


    Context activityContext;

    TextView tvMake,tvModel,tvIMEI,tvMyDevicesCount,tvFooter;
    AppPreferences appPreferences;
    LinearLayout llMyRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dashboard, container, false);
        activityContext = getContext();


        tvMake = (TextView)layout.findViewById(R.id.tvMake);
        tvModel = (TextView)layout.findViewById(R.id.tvModel);
        tvIMEI = (TextView)layout.findViewById(R.id.tvIMEI);
        tvMyDevicesCount = (TextView)layout.findViewById(R.id.tvMyDevicesCount);
        llMyRequest = (LinearLayout) layout.findViewById(R.id.llMyRequest);
        tvFooter = (TextView)layout.findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        llMyRequest.setOnClickListener(this);
        tvMyDevicesCount.setOnClickListener(this);

        init();

        return layout;
    }

    public void init()
    {
        appPreferences = new AppPreferences(activityContext);
        if(!appPreferences.isUserLoggedIn()) {
            if ("".equals(appPreferences.getSelfDevice())) {
                //no device are added so add self device
                getDeviceInformation();
            } else {
                List<Myappliance> devices = appPreferences.getDevices();

                Myappliance device1 = devices.get(0);

                tvIMEI.setText(device1.getSerialNo());
                tvMake.setText(device1.getBrand());
                tvModel.setText(device1.getModel());
                tvMyDevicesCount.setText(devices.size() + "\nDevice Added\n\nHit to add more");
            }
        }
        else
        {
            List<Myappliance> devices = appPreferences.getDevices();



            Myappliance device1 = devices.get(0);

            tvIMEI.setText(device1.getSerialNo());
            tvMake.setText(device1.getBrand());
            tvModel.setText(device1.getModel());
            tvMyDevicesCount.setText(devices.size() + "\nDevice Added\n\nHit to add more");

                //check if the self device is added or not
            if(!appPreferences.isSelfDeviceAdded()) {

                if(CommonFunctions.isNetworkAvailable(activityContext)) {
                    //request for OTP
                    Map<String, String> addApplianceParameters = new HashMap<>();
                    addApplianceParameters.put("appapi", "yes");
                    addApplianceParameters.put("category_id", device1.getCategory());
                    addApplianceParameters.put("subcategory_id", "");
                    addApplianceParameters.put("brand_id", device1.getBrand());

                    addApplianceParameters.put("store_id", "36");
                    addApplianceParameters.put("model_id", device1.getModel());


                    addApplianceParameters.put("serial_no", device1.getSerialNo());
                    addApplianceParameters.put("warranty", "");
                    addApplianceParameters.put("purchase_date", "");
                    addApplianceParameters.put("user_id", appPreferences.getUserInfo().getUserId());
                    new VolleyJSONCaller(this, ADD_APPLIANCE_USER_URL, addApplianceParameters, Request.Method.POST, false).execute();
                }
                else
                {
                    CommonFunctions.displayDialog(activityContext,getString(R.string.internet_problem));
                }


            }
            //get the details from server for the number of devices added
            dashboardDetails();
        }
    }

    private void dashboardDetails() {

        if(CommonFunctions.isNetworkAvailable(activityContext)) {
            //request for OTP
            Map<String, String> addApplianceDetailsParameters = new HashMap<>();
            addApplianceDetailsParameters.put("appapi", "yes");
            addApplianceDetailsParameters.put("user_id", new AppPreferences(activityContext).getUserInfo().getUserId());
            new VolleyJSONCaller(this, DASHBOARD_DETAILS_URL, addApplianceDetailsParameters, Request.Method.POST, false).execute();
        }
        else
        {
            CommonFunctions.displayDialog(activityContext,getString(R.string.internet_problem));
        }
    }

    void getDeviceInformation()
    {
        TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            tvIMEI.setText(telephonyManager.getDeviceId());
        }
        catch (Exception e)
        {
            tvIMEI.setText("Not Found");

            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        //builder.append("android : ").append(Build.VERSION.RELEASE);

        /*Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(fieldName);
            }
        }*/

        //Log.d(LOG_TAG, "OS: " + builder.toString());
        try {

            tvMake.setText(Build.MANUFACTURER);
            tvModel.setText(Build.MODEL);
            //add device in preference
            AppPreferences appPreferences = new AppPreferences(activityContext);
            appPreferences.setSelfDevice("added");
            Myappliance device = new Myappliance();
            device.setId("0");
            device.setCategory("MOBILE");
            device.setBrand(Build.MANUFACTURER);
            device.setModel(Build.MODEL);
            device.setSerialNo(tvIMEI.getText().toString());
            appPreferences.addDevice(device);

            //notify that your device has been added successfully
            CommonFunctions.displayDialog(activityContext," Welcome to Easy Service! \n Your device "+Build.MODEL+" is added to My Devices list.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvMyDevicesCount:

                //check if the user is logged in or not
                if(new AppPreferences(activityContext).isUserLoggedIn()){

                ((Landing)activityContext).setMyDevicesPage();
                }
                else {
                    ((Landing)activityContext).setLoginPage();
                }

                break;

            case R.id.tvFooter:
                //open my device page
                //check if the user is logged in or not
                if(new AppPreferences(activityContext).isUserLoggedIn()){

                    Intent intent = new Intent(activityContext, MyDevicesActivity.class);
                    startActivity(intent);
                    break;
                }
                else {
                    ((Landing)activityContext).setLoginPage();
                }
                break;

            case R.id.llMyRequest:

                ((Landing)activityContext).setServiceHistory();
                break;
            default:
                break;
        }
    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof DashboardResponse)
        {
            DashboardResponse dashboardResponse = (DashboardResponse)result;

            if(dashboardResponse.getStatus() == 1)
            {
                //set the user appliance count in dashboard
                tvMyDevicesCount.setText(dashboardResponse.getTotalmyappliance() + "\nDevice Added\n\nHit to add more");
            }
            else
            {
                CommonFunctions.displayDialog(getContext(),dashboardResponse.getMessage());
            }


        }else if(result instanceof AddApplianceResponse)
        {
            AddApplianceResponse addApplianceResponse = (AddApplianceResponse)result;

            if(addApplianceResponse.getStatus() == 1)
            {
                //set the preference for the self device added
                new AppPreferences(activityContext).setSelfDeviceAdded();
                List<Myappliance> devicesStored = new AppPreferences(activityContext).getDevices();
                Myappliance device1 = devicesStored.get(0);
                device1.setId(addApplianceResponse.getNewUserApplianceId().toString());
                new AppPreferences(activityContext).addDevice(device1);
            }
            else
            {
                CommonFunctions.displayDialog(getContext(),addApplianceResponse.getMessage());
            }


        }
    }


}


