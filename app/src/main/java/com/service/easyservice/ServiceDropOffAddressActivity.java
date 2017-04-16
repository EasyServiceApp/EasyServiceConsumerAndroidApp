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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.models.RequestServiceResponse;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.service.easyservice.util.Constants.REQUEST_SERVICE_URL;

public class ServiceDropOffAddressActivity extends AppCompatActivity implements View.OnClickListener,ResponseResult {

    TextView tvFooter,tvBrand,tvServiceMode;
    EditText etAddress;
    static EditText etRequestDate;

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
        setContentView(R.layout.activity_service_drop_off_address);

        context = this;
        issues = getIntent().getStringExtra("issues");
        serviceMode = getIntent().getStringExtra("service_mode");

        device = new AppPreferences(ServiceDropOffAddressActivity.this).getServiceDevices();

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

        etRequestDate = (EditText) findViewById(R.id.etRequestDate);
        //adding one day as the request cn be booked for the next day
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);     //same day service booking is allowed
        etRequestDate.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR));
        etRequestDate.setOnClickListener(this);



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
                        Intent intent = new Intent(ServiceDropOffAddressActivity.this,IssuesDiagnosedActivity.class);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvFooter:
                //store details on server and finish the activity
                if(CommonFunctions.isNetworkAvailable(this)) {

                    Map<String, String> addIssueParameters = new HashMap<>();
                    addIssueParameters.put("appapi", "yes");
                    addIssueParameters.put("user_id", new AppPreferences(this).getUserInfo().getUserId());
                    addIssueParameters.put("remark", "");
                    addIssueParameters.put("issue", issues);
                    addIssueParameters.put("issue_category", "");   //will be added later
                    addIssueParameters.put("request_date", etRequestDate.getText().toString());
                    addIssueParameters.put("timeslot", "");
                    addIssueParameters.put("location", etAddress.getText().toString()); //will be added ltr
                    addIssueParameters.put("user_appliance_id", device.getId());
                    addIssueParameters.put("category_id", device.getCategory());
                    addIssueParameters.put("subcategory_id", "0");
                    addIssueParameters.put("brand_id", device.getBrand());
                    addIssueParameters.put("model_id", device.getModel());
                    addIssueParameters.put("other_model", "");
                    addIssueParameters.put("store_id", "36");
                    addIssueParameters.put("service_mode", serviceMode);
                    new VolleyJSONCaller(this, REQUEST_SERVICE_URL, addIssueParameters, Request.Method.POST, false).execute();

                }
                else
                {
                    CommonFunctions.displayDialog(this,getString(R.string.internet_problem));
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
        }
    }

    @Override
    public void responseResult(Object result) {
        if (result != null && result instanceof RequestServiceResponse) {
            RequestServiceResponse requestServiceResponse = (RequestServiceResponse) result;
            if (requestServiceResponse.getStatus() == 1) {

                new AlertDialog.Builder(ServiceDropOffAddressActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(requestServiceResponse.getMessage())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                //delete the device
                                new AppPreferences(ServiceDropOffAddressActivity.this).addServiceDevice(null);
                                CommonFunctions.navigateToHome(ServiceDropOffAddressActivity.this);
                            }
                        })

                        .show();

            } else {
                CommonFunctions.displayDialog(this, requestServiceResponse.getMessage());
            }
        }
    }
}
