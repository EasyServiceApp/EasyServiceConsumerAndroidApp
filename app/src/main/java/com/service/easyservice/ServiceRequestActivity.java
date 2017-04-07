package com.service.easyservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.AppPreferences;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_activty);
        device = new AppPreferences(ServiceRequestActivity.this).getServiceDevices();

        init();




    }

    public void init()
    {
        tvBrand = (TextView)findViewById(R.id.tvBrand);
        tvFooter = (TextView)findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        llIssueList = (LinearLayout)findViewById(R.id.llIssueList);

        if(device!=null)
        {
            tvBrand.setText(device.getModel()+"\n"+device.getBrand());
        }

        //populate issue list
        String[] issueList = getResources().getStringArray(R.array.issue_list);

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

                break;

            default:
                break;
        }

    }
}
