package com.service.easyservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.MyApplianceResponse;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;
import com.service.easyservice.widgets.FlowLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.service.easyservice.util.Constants.MY_DEVICE_URL;

public class MyDevicesActivity extends AppCompatActivity implements View.OnClickListener,ResponseResult {

    LinearLayout llDevices;
    LayoutInflater inflater;
    TextView tvFooter;
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;
    final Type type = new TypeToken<Myappliance>(){}.getType();
    final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView)toolbar.findViewById(R.id.ivProfile);
        ivProfile.setVisibility(View.GONE);
        ivDrawerHandel = (ImageView)toolbar.findViewById(R.id.ivDrawerHandel);
        ivDrawerHandel.setImageResource(R.drawable.toolbar_back);
        ivDrawerHandel.setOnClickListener(this);
        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);

        llDevices = (LinearLayout)findViewById(R.id.llDevices);
        tvFooter = (TextView) findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        inflater = this.getLayoutInflater();
        renderDevices(new AppPreferences(this).getDevices());
        getMyDevice();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyDevice();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public void getMyDevice()
    {
        if(CommonFunctions.isNetworkAvailable(this)) {
            //request for OTP
            Map<String, String> addApplianceDetailsParameters = new HashMap<>();
            addApplianceDetailsParameters.put("appapi", "yes");
            addApplianceDetailsParameters.put("user_id", new AppPreferences(this).getUserInfo().getUserId());
            new VolleyJSONCaller(this, MY_DEVICE_URL, addApplianceDetailsParameters, Request.Method.POST, false).execute();
        }
        else
        {
            CommonFunctions.displayDialog(this,getString(R.string.internet_problem));
        }
    }

    public void renderDevices(List<Myappliance> devices)
    {
        //clear all the views
        llDevices.removeAllViews();

        //hide delete button for the self device
        List<Myappliance> devicesStored = new AppPreferences(this).getDevices();
        Myappliance device1 = devicesStored.get(0);

        //get devices from app preference
        List<Myappliance> devicesTemp = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();
        Myappliance myDevice = null;
        for(int i=0; i<devices.size();i++)
        {
            Myappliance device = devices.get(i);

            if(!categoryList.contains(device.getCategory()))
            {
                categoryList.add(device.getCategory());
            }

            if(device1.getSerialNo().equals(device.getSerialNo()))
            {
                myDevice = device;
                devices.remove(i);
            }


        }

        //add all device to temp list
        if(myDevice!=null)
        {
            devicesTemp.add(myDevice);
        }
        devicesTemp.addAll(devices);
        //populate the category layout

        //rearrange the devices

        List<View> categoryView = new ArrayList<>();
        List<FlowLayout> flowLayouts  = new ArrayList<>();

        for(int k = 0; k<categoryList.size();k++)
        {
            View category = inflater.inflate(R.layout.category_header_my_device,null,false);
            TextView tvCategory = (TextView)category.findViewById(R.id.tvCategory);
            tvCategory.setText(categoryList.get(k));
            categoryView.add(category);
            flowLayouts.add(new FlowLayout(this));
        }



            for (int i=0;i<devicesTemp.size();i++)
            {
                Myappliance device = devicesTemp.get(i);
                View device_view = inflater.inflate(R.layout.my_device,null,false);
                TextView tvModel = (TextView)device_view.findViewById(R.id.tvModel);
                TextView tvBrand = (TextView)device_view.findViewById(R.id.tvBrand);
                TextView tvName = (TextView)device_view.findViewById(R.id.tvName);
                ImageView ivCategory = (ImageView)device_view.findViewById(R.id.ivCategory);
                CommonFunctions.setCategoryImage(ivCategory,device.getCategory());
                tvModel.setText(device.getModel());
                tvBrand.setText(device.getBrand());



                if(device1.getSerialNo().equals(device.getSerialNo()))
                {
                    tvName.setText("My Device");
                }
                else
                {
                    tvName.setVisibility(View.GONE);
                }



                device_view.setTag(gson.toJson(device,type));
            device_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //navigate to service request screen
                    Intent intent = new Intent(MyDevicesActivity.this,ServiceRequestActivity.class);

                    new AppPreferences(MyDevicesActivity.this).addServiceDevice((Myappliance) gson.fromJson(view.getTag().toString(),type));

                    intent.putExtra("device",view.getTag().toString());
                    startActivity(intent);

                }
            });

                //check device belongs to which category
                for(int k = 0; k<categoryList.size();k++)
                {
                    if(categoryList.get(k).equalsIgnoreCase(device.getCategory()))
                    {
                        flowLayouts.get(k).addView(device_view);
                    }
                }
        }
            for(int k = 0; k<categoryList.size();k++)
            {

                llDevices.addView(categoryView.get(k));
                llDevices.addView(flowLayouts.get(k));
            }

    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof MyApplianceResponse)
        {
            MyApplianceResponse myApplianceResponse = (MyApplianceResponse)result;

            if(myApplianceResponse.getStatus().equals("1"))
            {

                renderDevices(myApplianceResponse.getMyappliance());

            }
            else
            {
                CommonFunctions.displayDialog(this,myApplianceResponse.getMessage());
            }


        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(MyDevicesActivity.this);
                break;
            case R.id.ivDrawerHandel:
                finish();
                break;

        }
    }
}
