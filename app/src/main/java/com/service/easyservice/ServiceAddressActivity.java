package com.service.easyservice;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.models.OTPVerifyResponse;
import com.service.easyservice.models.RequestServiceResponse;
import com.service.easyservice.models.TimeSlotInfo;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.service.easyservice.util.Constants.GET_TIME_SLOT_URL;
import static com.service.easyservice.util.Constants.REQUEST_SERVICE_URL;


public class ServiceAddressActivity extends AppCompatActivity implements View.OnClickListener,ResponseResult {

    TextView etAddress,tvFooter,tvBrand,tvServiceMode;
    Spinner spinnerLocation;
    static Spinner spinnerTime;
    static EditText etRequestDate;
    List<String> timeSlot = new ArrayList<>();

    Myappliance device = null;
    static Context context;
    String issues,serviceMode;
    Type type = new TypeToken<Myappliance>(){}.getType();
    Gson gson = new Gson();
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_address);
        context = this;
        issues = getIntent().getStringExtra("issues");
        serviceMode = getIntent().getStringExtra("service_mode");

        device = new AppPreferences(ServiceAddressActivity.this).getServiceDevices();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView)toolbar.findViewById(R.id.ivProfile);
        ivProfile.setVisibility(View.GONE);
        ivDrawerHandel = (ImageView)toolbar.findViewById(R.id.ivDrawerHandel);
        ivDrawerHandel.setImageResource(R.drawable.toolbar_back);
        ivDrawerHandel.setOnClickListener(this);
        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);

        etAddress = (EditText)findViewById(R.id.etAddress);
        tvFooter = (TextView) findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        //populate brand and category
        tvBrand.setText(device.getBrand()+"\n"+device.getModel());
        tvServiceMode = (TextView) findViewById(R.id.tvServiceMode);
        tvServiceMode.setText(serviceMode);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        etRequestDate = (EditText) findViewById(R.id.etRequestDate);
//adding one day as the request cn be booked for the next day
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);     //same day service booking is allowed
        etRequestDate.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR));
        etRequestDate.setOnClickListener(this);

        timeSlotDetails();


    }

    public void finishActivity()
    {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Are you sure you want to cancel the Service Request?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        //navigate to IssueDiagnosedActivity
                        Intent intent = new Intent(ServiceAddressActivity.this,IssuesDiagnosedActivity.class);
                        intent.putExtra("issues",issues);
                        startActivity(intent);
                        finish();

                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        finishActivity();

    }


    private static void timeSlotDetails() {

        List<String> timeSlot = new ArrayList<>();
        timeSlot.add("Select Time");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, timeSlot);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
        spinnerTime.setAdapter(spinnerArrayAdapter);


        if(CommonFunctions.isNetworkAvailable(context)) {
            //request for OTP
            Map<String, String> timeSlotDetailsParameters = new HashMap<>();
            timeSlotDetailsParameters.put("appapi", "yes");
            timeSlotDetailsParameters.put("store_id", "36");
            timeSlotDetailsParameters.put("request_date", etRequestDate.getText().toString().trim());
            new VolleyJSONCaller(context, GET_TIME_SLOT_URL, timeSlotDetailsParameters, Request.Method.POST, false).execute();
        }
        else
        {
            CommonFunctions.displayDialog(context,context.getString(R.string.internet_problem));
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvFooter:
                //store details on server and finish the activity
                String address = "";
                OTPVerifyResponse otpVerifyResponse = new AppPreferences(this).getUserInfo();
                switch (spinnerLocation.getSelectedItem().toString()){
                    case "Home":
                        address = otpVerifyResponse.getAddress1();
                        break;

                    case "Work":
                        address = otpVerifyResponse.getAddress2();
                        break;

                    case "Others":
                        address = otpVerifyResponse.getAddress3();
                        break;
                }

                //validate the if the time slot is selected or not
                if("Select Time".equals(spinnerTime.getSelectedItem().toString()))
                {
                    CommonFunctions.displayDialog(this,"Please select the time slot or try changing the date and select again");
                } else if("".equals(address.trim()))
                {
                    //validate is the address is available or not

                    new AlertDialog.Builder(ServiceAddressActivity.this)
                            .setTitle(R.string.app_name)
                            .setMessage("Please update your "+spinnerLocation.getSelectedItem().toString()+" Address.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    Intent intent = new Intent(ServiceAddressActivity.this,ManageAddressActivity.class);
                                    startActivity(intent);
                                }
                            })

                            .show();


                }
                else {


                    if (CommonFunctions.isNetworkAvailable(this)) {

                        Map<String, String> addIssueParameters = new HashMap<>();
                        addIssueParameters.put("appapi", "yes");
                        addIssueParameters.put("user_id", new AppPreferences(this).getUserInfo().getUserId());
                        addIssueParameters.put("remark", "");
                        addIssueParameters.put("issue", issues);
                        addIssueParameters.put("issue_category", "");   //will be added later
                        addIssueParameters.put("request_date", etRequestDate.getText().toString());
                        addIssueParameters.put("timeslot", spinnerTime.getSelectedItem().toString());
                        addIssueParameters.put("location", spinnerLocation.getSelectedItem().toString()); //will be added ltr
                        addIssueParameters.put("user_appliance_id", device.getId());
                        addIssueParameters.put("category_id", device.getCategory());
                        addIssueParameters.put("subcategory_id", "0");
                        addIssueParameters.put("brand_id", device.getBrand());
                        addIssueParameters.put("model_id", device.getModel());
                        addIssueParameters.put("other_model", "");
                        addIssueParameters.put("store_id", "36");
                        addIssueParameters.put("service_mode", serviceMode);
                        new VolleyJSONCaller(this, REQUEST_SERVICE_URL, addIssueParameters, Request.Method.POST, false).execute();

                    } else {
                        CommonFunctions.displayDialog(this, getString(R.string.internet_problem));
                    }
                }
                break;
            case R.id.etRequestDate:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.ivDrawerHandel:
            case R.id.ivToolbarHome:
                finishActivity();
                break;
        }
    }

    @Override
    public void responseResult(Object result) {
        if (result != null && result instanceof TimeSlotInfo) {
            TimeSlotInfo timeSlotInfo = (TimeSlotInfo) result;
            if (1 == timeSlotInfo.getStatus()) {
                //set time slot received according to date
                timeSlot.clear();
                for(int i=0;i<timeSlotInfo.getDatetimeslot().size();i++)
                {
                    timeSlot.add(timeSlotInfo.getDatetimeslot().get(i).getTimeslot());
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSlot);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                spinnerTime.setAdapter(spinnerArrayAdapter);

            } else {
                CommonFunctions.displayDialog(this, timeSlotInfo.getMessage());
            }
        }else if (result != null && result instanceof RequestServiceResponse) {
            RequestServiceResponse requestServiceResponse = (RequestServiceResponse) result;
            if (requestServiceResponse.getStatus() == 1) {

                new AlertDialog.Builder(ServiceAddressActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(requestServiceResponse.getMessage())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                //delete the device
                                new AppPreferences(ServiceAddressActivity.this).addServiceDevice(null);
                                CommonFunctions.navigateToHome(ServiceAddressActivity.this);

                            }
                        })

                        .show();

            } else {
                CommonFunctions.displayDialog(this, requestServiceResponse.getMessage());
            }
        }
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker   //mm/dd/yyyy
            String[] date = etRequestDate.getText().toString().split("/");
            int year = Integer.parseInt(date[2]);
            int month = Integer.parseInt(date[0]) - 1;
            int day = Integer.parseInt(date[1]);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 0);
            datePickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime() - 1000);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            etRequestDate.setText((month + 1) + "/" + day + "/" + year);
            timeSlotDetails();
        }
    }

}
