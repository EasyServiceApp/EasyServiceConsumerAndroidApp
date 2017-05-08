package com.service.easyservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.Myrequest;
import com.service.easyservice.models.NoDataResponse;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static com.service.easyservice.DiagnoseActivity.device;
import static com.service.easyservice.util.Constants.MY_REQUEST_FEEDBACK_URL;

public class MyRequestDetailsActivity extends AppCompatActivity implements View.OnClickListener,ResponseResult {

    Myrequest myrequest = new Myrequest();
    Gson gson = new Gson();
    Type type = new TypeToken<Myrequest>() {}.getType();

    TextView tvBrand,tvStatus,tvRating,tvRequestDate,tvAssignedMechanic,tvIssue,tvRemark,tvFooter,tvRequestId,tvModel,tvRatingLabel;
    RatingBar applianceRating;
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;
    private float rating;
    ImageView ivCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_details);

        String myRequestStr = getIntent().getStringExtra("myrequest");

        myrequest = gson.fromJson(myRequestStr,type);

        init();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        tvModel = (TextView)findViewById(R.id.tvModel);
        tvRequestId = (TextView)findViewById(R.id.tvRequestId);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvRating = (TextView)findViewById(R.id.tvRating);
        tvRequestDate = (TextView)findViewById(R.id.tvRequestDate);
        tvAssignedMechanic = (TextView)findViewById(R.id.tvAssignedMechanic);
        tvIssue = (TextView)findViewById(R.id.tvIssue);
        tvRemark = (TextView)findViewById(R.id.tvRemark);
        tvFooter = (TextView)findViewById(R.id.tvFooter);
        tvFooter.setOnClickListener(this);
        tvRatingLabel = (TextView)findViewById(R.id.tvRatingLabel);
        applianceRating = (RatingBar) findViewById(R.id.applianceRating);
        applianceRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;
            }
        });


        //set all data to the layout
        tvRequestId.setText("REQUEST No # "+myrequest.getRequestId());
        tvModel.setText("MODEL: "+ myrequest.getModel());
        tvBrand.setText(myrequest.getBrand()+"\n"+myrequest.getModel());

        ivCategory = (ImageView)findViewById(R.id.ivCategory);
        CommonFunctions.setCategoryImage(ivCategory,myrequest.getCategory());

        tvStatus.setText("Status:"+"\n"+myrequest.getStatusId());
        //display rating if available
        if("".equals(myrequest.getRatings()))
        {
            tvRating.setVisibility(View.INVISIBLE);

            if("Completed".equalsIgnoreCase(myrequest.getStatusId()))
            {
                //display rating bar and make text of footer as Submit Rating

                tvFooter.setText("Submit Rating");
                tvRatingLabel.setVisibility(View.VISIBLE);
                applianceRating.setVisibility(View.VISIBLE);

            }

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
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(MyRequestDetailsActivity.this);
                break;

            case R.id.ivDrawerHandel:
                finish();
                break;

            case R.id.tvFooter:
                if("Submit Rating".equals(tvFooter.getText().toString()))
                {
                    if(CommonFunctions.isNetworkAvailable(this)) {
                        //submit rating
                        Map<String, String> requestHistoryParameters = new HashMap<>();
                        requestHistoryParameters.put("appapi", "yes");
                        requestHistoryParameters.put("user_id", new AppPreferences(this).getUserInfo().getUserId());
                        requestHistoryParameters.put("rating", rating + "");
                        requestHistoryParameters.put("brand_rating", "");
                        requestHistoryParameters.put("feedback", "");
                        requestHistoryParameters.put("request_id", myrequest.getRequestId());
                        new VolleyJSONCaller(this, MY_REQUEST_FEEDBACK_URL, requestHistoryParameters, Request.Method.POST, false).execute();
                    }
                    else
                    {
                        CommonFunctions.displayDialog(this,getString(R.string.internet_problem));
                    }
                }else if("Track".equals(tvFooter.getText().toString())){
                    Intent mapIntent = new Intent(MyRequestDetailsActivity.this,TrackActivity.class);
                    mapIntent.putExtra("requestId",myrequest.getRequestId());
                    startActivity(mapIntent);
                }
                break;

        }
    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof NoDataResponse)
        {
            NoDataResponse noDataResponse = (NoDataResponse)result;

            if(noDataResponse.getStatus().equals("1"))
            {

                Toast.makeText(this, noDataResponse.getMessage(), Toast.LENGTH_LONG).show();
                finish();

            }
            else
            {
                CommonFunctions.displayDialog(this,noDataResponse.getMessage());
            }


        }
    }
}
