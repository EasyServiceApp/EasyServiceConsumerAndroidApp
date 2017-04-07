package com.service.easyservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.Myrequest;
import com.service.easyservice.util.CommonFunctions;

import java.lang.reflect.Type;

public class MyRequestDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Myrequest myrequest = new Myrequest();
    Gson gson = new Gson();
    Type type = new TypeToken<Myrequest>() {}.getType();

    TextView tvBrand,tvStatus,tvRating,tvRequestDate,tvAssignedMechanic,tvIssue,tvRemark,tvFooter,tvRequestId,tvModel;
    ImageView ivToolbarHome;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_details);

        String myRequestStr = getIntent().getStringExtra("myrequest");

        myrequest = gson.fromJson(myRequestStr,type);

        init();

    }

    public void init()
    {

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);

        tvBrand = (TextView)findViewById(R.id.tvBrand);
        tvModel = (TextView)findViewById(R.id.tvModel);
        tvRequestId = (TextView)findViewById(R.id.tvRequestId);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvRating = (TextView)findViewById(R.id.tvRating);
        tvRequestDate = (TextView)findViewById(R.id.tvRequestDate);
        tvAssignedMechanic = (TextView)findViewById(R.id.tvAssignedMechanic);
        tvIssue = (TextView)findViewById(R.id.tvIssue);
        tvRemark = (TextView)findViewById(R.id.tvRemark);
        tvFooter = (TextView)findViewById(R.id.tvFooter);



        //set all data to the layout
        tvRequestId.setText("REQUEST No # "+myrequest.getRequestId());
        tvModel.setText("MODEL: "+ myrequest.getModel());
        tvBrand.setText(myrequest.getBrand()+"\n"+myrequest.getModel());
        tvStatus.setText("Status:"+"\n"+myrequest.getStatusId());
        //display rating if available
        if("".equals(myrequest.getRatings()))
        {
            tvRating.setVisibility(View.INVISIBLE);
        }
        else
        {
            tvRating.setText(myrequest.getRatings());
        }
        tvRequestDate.setText("Request Date"+"\n"+myrequest.getRequestDate()+" "+myrequest.getTimeslot().split("-")[0]);
        if("".equals(myrequest.getEngineerName().trim()))
        {
            tvAssignedMechanic.setVisibility(View.INVISIBLE);
        }
        else
        {
            tvAssignedMechanic.setText("Assigned Mechanic"+"\n"+myrequest.getEngineerName().trim());
        }
        if("".equals(myrequest.getIssue().trim()))
        {
            tvIssue.setText("No Specific Issue");
        }
        else
        {
            String[] issueList = myrequest.getIssue().trim().split("@@");
            String issue = "";
            for(int i =0; i<issueList.length;i++)
            {
                issue = issue + "\n" + issueList[i];
            }
            issue = issue.trim();
            tvIssue.setText(issue);
        }

        if("".equals(myrequest.getRemark().trim()))
        {
            tvRemark.setText("No Remarks");
        }
        else
        {
            tvRemark.setText(myrequest.getRemark().trim());
        }
        if("In-Transit".equals(myrequest.getStatusId().trim()))
        {
            tvFooter.setText("Track");
            tvFooter.setOnClickListener(this);
        }
        else
        {
            tvFooter.setText("");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(MyRequestDetailsActivity.this);
                break;

        }
    }
}
