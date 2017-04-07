package com.service.easyservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;

import java.lang.reflect.Type;

public class IssuesDiagnosedActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvFooter,tvBrand,tvPickUp,tvAtHome,tvDropOff;
    LinearLayout llIssueList;
    Myappliance device = null;
    Type type = new TypeToken<Myappliance>(){}.getType();
    Gson gson = new Gson();
    String[] issuesList;
    String deviceStr,issues;
    ImageView ivToolbarHome;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_diagnosed);

        issues = getIntent().getStringExtra("issues");

        device = new AppPreferences(IssuesDiagnosedActivity.this).getServiceDevices();
        if(issues != null)
        {
            issuesList = issues.split("@@");
        }
        init();

    }
    public void init()
    {

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);

        tvBrand = (TextView)findViewById(R.id.tvBrand);
        tvFooter = (TextView)findViewById(R.id.tvFooter);
        tvPickUp = (TextView)findViewById(R.id.tvPickUp);
        tvAtHome = (TextView)findViewById(R.id.tvAtHome);
        tvDropOff = (TextView)findViewById(R.id.tvDropOff);
        tvPickUp.setOnClickListener(this);
        tvAtHome.setOnClickListener(this);
        tvDropOff.setOnClickListener(this);

        tvBrand.setText(device.getModel()+"\n"+device.getBrand());


        llIssueList = (LinearLayout)findViewById(R.id.llIssueList);



        for(int i=0; i<issuesList.length;i++)
        {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(issuesList[i]);
            textView.setLayoutParams(new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setTextColor(getResources().getColor(R.color.black));
            llIssueList.addView(textView);
            if(i==2)
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvPickUp:
                Intent intent = new Intent(IssuesDiagnosedActivity.this,ServiceAddressActivity.class);
                intent.putExtra("issues",issues);
                intent.putExtra("device",deviceStr);
                intent.putExtra("service_mode","pick_up");
                startActivity(intent);
                break;
            case R.id.tvAtHome:
                //navigate to address activity
                Intent intent1 = new Intent(IssuesDiagnosedActivity.this,ServiceAddressActivity.class);
                intent1.putExtra("issues",issues);
                intent1.putExtra("service_mode","at_home");
                startActivity(intent1);
                break;

            case R.id.tvDropOff:
                Intent intent2 = new Intent(IssuesDiagnosedActivity.this,ServiceDropOffAddressActivity.class);
                intent2.putExtra("issues",issues);
                intent2.putExtra("service_mode","drop_off");
                startActivity(intent2);
                break;



            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(IssuesDiagnosedActivity.this);
                break;

        }
    }
}
