package com.service.easyservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdk.sampleapp.LaunchActivity;
import com.sdk.sampleapp.MainActivity;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.AppPreferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DiagnoseActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvFooter,tvBrand;
    LinearLayout llIssueList;
    static Myappliance device = null;
    Type type = new TypeToken<Myappliance>(){}.getType();
    Type typeselectedissue = new TypeToken<ArrayList<String>>(){}.getType();
    Gson gson = new Gson();
    String[] selectedIssueArray;
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        String selectedIssue = getIntent().getStringExtra("issues");

        if(selectedIssue !=null)
        {
            selectedIssueArray = selectedIssue.split("@@");
        }

        device = new AppPreferences(DiagnoseActivity.this).getServiceDevices();
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

        if(device!=null)
        {
            tvBrand.setText(device.getModel()+"\n"+device.getBrand());
        }

        llIssueList = (LinearLayout)findViewById(R.id.llIssueList);

        //populate issue list
        //String[] issueList = getResources().getStringArray(R.array.issue_list);
        if(null != selectedIssueArray) {
            for (int i = 0; i < selectedIssueArray.length; i++) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(selectedIssueArray[i]);
                textView.setLayoutParams(new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setTextColor(getResources().getColor(R.color.black));
                llIssueList.addView(textView);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvFooter :
                //diagnostic will start here
                Intent intent=new Intent(DiagnoseActivity.this, LaunchActivity.class);
                startActivity(intent);
                finish();

                break;
            case R.id.ivDrawerHandel:
            case R.id.ivToolbarHome:
                finishActivity();
                break;
            default:

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

       HashMap<String, Integer> faileddatainmap = MainActivity.resultmapfailed;
        String diagnose_report = intent.getStringExtra("diagnose_report");
        Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
        Gson gson = new Gson();

        //if(diagnose_report != null) {
           // faileddatainmap  = gson.fromJson(diagnose_report,type);
            if (faileddatainmap != null) {
                //send diagnostic report to next screen
                Iterator myVeryOwnIterator = faileddatainmap.keySet().iterator();
                String issues = "";
                while (myVeryOwnIterator.hasNext()) {
                    String key = (String) myVeryOwnIterator.next();
                    int value = (Integer) faileddatainmap.get(key);
                    // textViewdata.setText(String.valueOf(value));
                    Toast.makeText(getApplicationContext(), "Key: " + key + " Value: " + value, Toast.LENGTH_LONG).show();
                    issues += key + "@@";
                }

                //send the report to IssueDiagnoseActivity
                Intent issueDiagnoseActivity = new Intent(DiagnoseActivity.this, IssuesDiagnosedActivity.class);
                intent.putExtra("selectedIssue", issues);
                startActivity(issueDiagnoseActivity);
                finish();
            }
        //}

    }

    @Override
    protected void onRestart() {
        super.onRestart();



        Intent intent = getIntent();
        HashMap<String, Integer> faileddatainmap = MainActivity.resultmapfailed;
        String diagnose_report = intent.getStringExtra("diagnose_report");
        Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
        Gson gson = new Gson();

        ///if(diagnose_report != null) {
            //faileddatainmap  = gson.fromJson(diagnose_report,type);
            if (faileddatainmap != null) {
                //send diagnostic report to next screen
                Iterator myVeryOwnIterator = faileddatainmap.keySet().iterator();
                String issues = "";
                while (myVeryOwnIterator.hasNext()) {
                    String key = (String) myVeryOwnIterator.next();
                    int value = (Integer) faileddatainmap.get(key);
                    // textViewdata.setText(String.valueOf(value));
                    Toast.makeText(getApplicationContext(), "Key: " + key + " Value: " + value, Toast.LENGTH_LONG).show();
                    issues += key + "@@";
                }

                //send the report to IssueDiagnoseActivity
                Intent issueDiagnoseActivity = new Intent(DiagnoseActivity.this, IssuesDiagnosedActivity.class);
                intent.putExtra("issues", issues);
                startActivity(issueDiagnoseActivity);
                finish();
            }
        //}
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent i = getIntent();
        Log.e(getLocalClassName(),"intent received");


        HashMap<String, Integer> faileddatainmap = new HashMap<>();
        String diagnose_report = i.getStringExtra("diagnose_report");
        Type typehasmap = new TypeToken<HashMap<String, Integer>>(){}.getType();
        Gson gson = new Gson();

        if(diagnose_report != null) {
        faileddatainmap  = gson.fromJson(diagnose_report,typehasmap);
        if (faileddatainmap != null) {
            //send diagnostic report to next screen
            Iterator myVeryOwnIterator = faileddatainmap.keySet().iterator();
            String issues = "";
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                int value = (Integer) faileddatainmap.get(key);
                // textViewdata.setText(String.valueOf(value));
                //Toast.makeText(getApplicationContext(), "Key: " + key + " Value: " + value, Toast.LENGTH_LONG).show();
                issues += key + "@@";
            }

            //send the report to IssueDiagnoseActivity
            Intent issueDiagnoseActivity = new Intent(DiagnoseActivity.this, IssuesDiagnosedActivity.class);
            List<Myappliance> devices = new AppPreferences(this).getDevices();

            Myappliance device1 = devices.get(0);
            issueDiagnoseActivity.putExtra("issues", issues);
            startActivity(issueDiagnoseActivity);
            finish();
        }
        }

    }
}
