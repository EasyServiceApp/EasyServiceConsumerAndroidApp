package com.service.easyservice.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.service.easyservice.Landing;
import com.service.easyservice.R;
import com.service.easyservice.models.SupportTicketResponse;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.util.HashMap;
import java.util.Map;

import static com.service.easyservice.util.Constants.SUPPORT_TICKET_URL;

/**
 * Created by Smile on 17-03-2017.
 */

public class Support extends Fragment implements ResponseResult {


    Context activityContext;
    TextView tvName,tvMobile,tvEmail,tvFooter;
    EditText etMessage;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_support, container, false);
        activityContext = getContext();
        tvName = (TextView)layout.findViewById(R.id.tvName);
        tvMobile = (TextView)layout.findViewById(R.id.tvMobile);
        tvEmail = (TextView)layout.findViewById(R.id.tvEmail);
        etMessage = (EditText) layout.findViewById(R.id.etMessage);
        tvFooter = (TextView) layout.findViewById(R.id.tvFooter);
        spinner = (Spinner) layout.findViewById(R.id.spinner);
        AppPreferences appPreferences = new AppPreferences(activityContext);
        tvName.setText(appPreferences.getUserInfo().getFirstName());
        tvMobile.setText(appPreferences.getUserInfo().getMobile());
        tvEmail.setText(appPreferences.getUserInfo().getEmail());

        tvFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etMessage.getText().toString().trim().equals(""))
                {
                    CommonFunctions.displayDialog(activityContext,"Please enter the Message.");
                }
                else
                {
                    //submit the support ticket
                    if(CommonFunctions.isNetworkAvailable(activityContext)) {
                        //request for OTP
                        Map<String, String> addApplianceDetailsParameters = new HashMap<>();
                        addApplianceDetailsParameters.put("appapi", "yes");
                        addApplianceDetailsParameters.put("user_id", new AppPreferences(activityContext).getUserInfo().getUserId());
                        addApplianceDetailsParameters.put("subject", spinner.getSelectedItem().toString());
                        addApplianceDetailsParameters.put("content", etMessage.getText().toString().trim());
                        new VolleyJSONCaller(Support.this, SUPPORT_TICKET_URL, addApplianceDetailsParameters, Request.Method.POST, false).execute();
                    }
                    else
                    {
                        CommonFunctions.displayDialog(activityContext,getString(R.string.internet_problem));
                    }

                }
            }
        });

        return layout;

    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof SupportTicketResponse)
        {
            SupportTicketResponse supportTicketResponse = (SupportTicketResponse)result;

            if(supportTicketResponse.getStatus() == 1)
            {
                new AlertDialog.Builder(activityContext)
                        .setTitle(R.string.app_name)
                        .setMessage(supportTicketResponse.getMessage())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                ((Landing)activityContext).setDashboardPage();
                            }
                        })

                        .show();

            }
            else
            {
                CommonFunctions.displayDialog(getContext(),supportTicketResponse.getMessage());
            }


        }
    }
}
