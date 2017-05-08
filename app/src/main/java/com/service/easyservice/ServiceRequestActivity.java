package com.service.easyservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvFooter,tvBrand;
    LinearLayout llIssueList;
    Myappliance device = null;
    Type type = new TypeToken<Myappliance>(){}.getType();
    Gson gson = new Gson();
    ArrayList<String> selectedIssue = new ArrayList<>();
    private Toolbar toolbar;
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    ImageView ivCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_activty);
        device = new AppPreferences(ServiceRequestActivity.this).getServiceDevices();

        init();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        //finish activity with the confirmation
        finishActivity();

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
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
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
        tvFooter = (TextView)findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        llIssueList = (LinearLayout)findViewById(R.id.llIssueList);

        if(device!=null)
        {
            tvBrand.setText(device.getModel()+"\n"+device.getBrand());
            ivCategory = (ImageView)findViewById(R.id.ivCategory);
            CommonFunctions.setCategoryImage(ivCategory,device.getCategory());
        }

        //populate issue list
        String[] issueList = CommonFunctions.getIssueList(this,device.getCategory()) ;

        for(int i=0; i<issueList.length;i++)
        {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(issueList[i]);
            cb.setLayoutParams(new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        if(!selectedIssue.contains(compoundButton.getText().toString()))
                        {
                            selectedIssue.add(compoundButton.getText().toString());
                        }
                    }
                    else
                    {
                        if(selectedIssue.contains(compoundButton.getText().toString()))
                        {
                            selectedIssue.remove(compoundButton.getText().toString());
                        }
                    }
                }
            });
            llIssueList.addView(cb);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.tvFooter:

                //Check if the device is home device then open the diagnostic activity or open the IssueDiagnosedActivity
                List<Myappliance> devicesStored = new AppPreferences(this).getDevices();
                Myappliance device1 = devicesStored.get(0);
                Intent intent = null;
                if(device1.getSerialNo().equals(device.getSerialNo())){
                    //open the diagnose activity
                    intent = new Intent(ServiceRequestActivity.this,DiagnoseActivity.class);
                }
                else
                {
                    //open the diagnose activity
                    intent = new Intent(ServiceRequestActivity.this,IssuesDiagnosedActivity.class);
                }
                Type typeselectedissue = new TypeToken<ArrayList<String>>(){}.getType();
                String issues = "";
                for(int i=0; i<selectedIssue.size();i++)
                {
                    issues += selectedIssue.get(i) + "@@";
                }

                intent.putExtra("issues",issues);
                startActivity(intent);
                finish();
                break;
            case R.id.ivDrawerHandel:
            case R.id.ivToolbarHome:
                finishActivity();
                break;
            default:
                break;
        }

    }
}
