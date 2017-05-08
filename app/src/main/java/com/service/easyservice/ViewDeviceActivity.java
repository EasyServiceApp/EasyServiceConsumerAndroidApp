package com.service.easyservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.DeleteApplianceResponse;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.service.easyservice.util.Constants.DELETE_DEVICE_URL;

public class ViewDeviceActivity extends AppCompatActivity implements View.OnClickListener,ResponseResult {

    TextView tvBrand,tvFooter,tvDelete,tvIMEI;
    EditText etName;
    EditText etWarranty;
    EditText etIMEI;
    EditText etPurchaseDate;
    ImageView ivInvoiceImage1,ivInvoiceImage2,ivInvoiceImage3;
    private Myappliance myappliance;
    Gson gson = new Gson();
    Type type = new TypeToken<Myappliance>() {}.getType();
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;
    String myApplianceStr;
    ImageView ivCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_device);

        myApplianceStr = getIntent().getStringExtra("myappliance");

        myappliance = gson.fromJson(myApplianceStr,type);

        init();
    }



    public void init()
    {

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView)toolbar.findViewById(R.id.ivProfile);
        ivProfile.setVisibility(View.GONE);
        ivDrawerHandel = (ImageView)toolbar.findViewById(R.id.ivDrawerHandel);
        ivDrawerHandel.setImageResource(R.drawable.toolbar_back);
        ivDrawerHandel.setOnClickListener(this);
        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);


        tvBrand = (TextView)findViewById(R.id.tvBrand);
        tvIMEI = (TextView)findViewById(R.id.tvIMEI);
        tvDelete = (TextView)findViewById(R.id.tvDelete);
        tvFooter = (TextView)findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.etName);
        etWarranty = (EditText) findViewById(R.id.etWarranty);
        etIMEI = (EditText) findViewById(R.id.etIMEI);
        etPurchaseDate = (EditText) findViewById(R.id.etPurchaseDate);
        ivInvoiceImage1 = (ImageView) findViewById(R.id.ivInvoiceImage1);
        ivInvoiceImage1.setOnClickListener(this);
        ivInvoiceImage2 = (ImageView) findViewById(R.id.ivInvoiceImage2);
        ivInvoiceImage2.setOnClickListener(this);
        ivInvoiceImage3 = (ImageView) findViewById(R.id.ivInvoiceImage3);
        ivInvoiceImage3.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        if(null != myappliance.getInvoice1() && !"".equals(myappliance.getInvoice1()))
        {
            ivInvoiceImage1.setVisibility(View.VISIBLE);
            Picasso.with(this).load(myappliance.getInvoice1()).fit().into(ivInvoiceImage1);
        }
        else
        {
            ivInvoiceImage1.setVisibility(View.GONE);
        }

        if(null != myappliance.getInvoice2() && !"".equals(myappliance.getInvoice2()))
        {
            ivInvoiceImage2.setVisibility(View.VISIBLE);
            Picasso.with(this).load(myappliance.getInvoice2()).fit().into(ivInvoiceImage2);
        }
        else
        {
            ivInvoiceImage2.setVisibility(View.GONE);
        }

        if(null != myappliance.getInvoice3() && !"".equals(myappliance.getInvoice3()))
        {
            ivInvoiceImage3.setVisibility(View.VISIBLE);
            Picasso.with(this).load(myappliance.getInvoice3()).fit().into(ivInvoiceImage3);
        }
        else
        {
            ivInvoiceImage3.setVisibility(View.GONE);
        }

        //populate brand and category
        tvBrand.setText(myappliance.getBrand()+"\n"+myappliance.getModel());

        ivCategory = (ImageView)findViewById(R.id.ivCategory);
        CommonFunctions.setCategoryImage(ivCategory,myappliance.getCategory());

        etName.setText(myappliance.getModel());
        etIMEI.setText(myappliance.getSerialNo());

        //if category is not mobile change label of serial no to serial no
        if(!"mobile".equalsIgnoreCase(myappliance.getCategory()))
        {
            tvIMEI.setText("Serial No.");
        }

        etWarranty.setText(myappliance.getWarranty());
        etPurchaseDate.setText(myappliance.getPurchaseDate());


        //hide delete button for the self device
        List<Myappliance> devices = new AppPreferences(this).getDevices();
        Myappliance device1 = devices.get(0);

        if(device1.getSerialNo().equals(myappliance.getSerialNo()))
        {
            tvDelete.setVisibility(View.GONE);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(this,Landing.class));
        finish();
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this,ImageZoomActivity.class);
        switch (v.getId())
        {
            case R.id.ivInvoiceImage1:
                intent.putExtra("image",myappliance.getInvoice1());
                startActivity(intent);
                break;
            case R.id.ivInvoiceImage2:
                intent.putExtra("image",myappliance.getInvoice2());
                startActivity(intent);
                break;
            case R.id.ivInvoiceImage3:
                intent.putExtra("image",myappliance.getInvoice3());
                startActivity(intent);
                break;
            case R.id.tvDelete:
                //delete confirmation dialog

                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_name)
                        .setMessage("Are you sure you want to remove this device from your My Device list?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                //delete the device
                                Map<String, String> verifyOTPParameters = new HashMap<>();
                                verifyOTPParameters.put("appapi", "yes");
                                verifyOTPParameters.put("user_id", new AppPreferences(ViewDeviceActivity.this).getUserInfo().getUserId());
                                verifyOTPParameters.put("user_appliance_id", myappliance.getId());
                                new VolleyJSONCaller(ViewDeviceActivity.this, DELETE_DEVICE_URL, verifyOTPParameters, Request.Method.POST, false).execute();

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                //dismiss the dialog

                            }
                        })
                        .show();
                break;

            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(ViewDeviceActivity.this);
                break;
            case R.id.tvFooter:
                Intent intentRequestService = new Intent(ViewDeviceActivity.this,ServiceRequestActivity.class);
                new AppPreferences(ViewDeviceActivity.this).addServiceDevice(myappliance);
                startActivity(intentRequestService);
                finish();
                break;

            case R.id.ivDrawerHandel:
                finish();
                break;


        }

    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof DeleteApplianceResponse)
        {
            DeleteApplianceResponse deleteApplianceResponse = (DeleteApplianceResponse)result;

            if(deleteApplianceResponse.getStatus() == 1)
            {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_name)
                        .setMessage(deleteApplianceResponse.getMessage())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                                //delete the device

                            }
                        })
                        .setCancelable(false)
                        .show();

            }
            else
            {
                CommonFunctions.displayDialog(this,deleteApplianceResponse.getMessage());
            }


        }
    }
}
