package com.service.easyservice.volley;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.service.easyservice.R;
import com.service.easyservice.models.AddApplianceResponse;
import com.service.easyservice.models.DashboardResponse;
import com.service.easyservice.models.DeleteApplianceResponse;
import com.service.easyservice.models.MyApplianceResponse;
import com.service.easyservice.models.OTPVerifyResponse;
import com.service.easyservice.models.RequestOTPResponse;
import com.service.easyservice.models.RequestServiceListResponse;
import com.service.easyservice.models.RequestServiceResponse;
import com.service.easyservice.models.SupportTicketResponse;
import com.service.easyservice.models.TimeSlotInfo;
import com.service.easyservice.util.Constants;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

import static android.app.SearchManager.QUERY;

public class VolleyJSONCaller implements Constants {

    private String apiName = "";

    private Context context;

    private int httpMethod;
    private Map<String, String> params;
    private JSONObject paramObject;

    private ResponseResult resultGeoname;

    private KProgressHUD progressHUD;

    private boolean flagSilent = false;

    private static final int TIME_OUT = 300000;


    public VolleyJSONCaller(Context context, String apiName, Map<String, String> params, int httpMethod, boolean flagSilent) {
        this.context = context;
        this.apiName = apiName;
        this.resultGeoname = (ResponseResult) context;
        this.params = params;
        this.httpMethod = httpMethod;
        this.flagSilent = flagSilent;

        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getString(R.string.progress_please_wait))
                .setCancellable(false)
                .setAnimationSpeed(2);
    }

    public VolleyJSONCaller(Fragment fragment, String apiName, Map<String, String> params, int httpMethod, boolean flagSilent) {
        this.context = fragment.getActivity();
        this.apiName = apiName;
        this.resultGeoname = (ResponseResult) fragment;
        this.params = params;
        this.httpMethod = httpMethod;
        this.flagSilent = flagSilent;

        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2);
    }

    public VolleyJSONCaller(Fragment fragment, String apiName, Map<String, String> params, JSONObject jsonObject, int httpMethod) {
        this.context = fragment.getActivity();
        this.apiName = apiName;
        this.resultGeoname = (ResponseResult) fragment;
        this.params = params;
        this.httpMethod = httpMethod;
        this.paramObject = jsonObject;

        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .show();

    }

    public VolleyJSONCaller(Fragment fragment, String apiName) {
        this.context = fragment.getActivity();
        this.apiName = apiName;
        this.resultGeoname = (ResponseResult) fragment;

        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .show();
    }


    public void execute() {
        try {

            if (!flagSilent) {
                progressHUD.show();
            }

            String queryString="";

            if(params.containsKey(QUERY))
            {
                queryString = params.get(QUERY);
            }

            StringRequest jsonObjectRequest = new StringRequest(httpMethod, apiName+queryString, new Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println(apiName + " result: " + response);

                        Type type;
                        Gson gson = new Gson();
                        switch (apiName) {


                            case REQUEST_OTP:
                                type = new TypeToken<RequestOTPResponse>() {
                                }.getType();
                                RequestOTPResponse requestOTPResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(requestOTPResponse);
                                break;

                            case VERIFY_OTP:
                            case UPDATE_PROFILE_URL:
                                type = new TypeToken<OTPVerifyResponse>() {
                                }.getType();
                                OTPVerifyResponse otpVerifyResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(otpVerifyResponse);
                                break;
                            case DASHBOARD_DETAILS_URL:
                                type = new TypeToken<DashboardResponse>() {
                                }.getType();
                                DashboardResponse dashboardResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(dashboardResponse);
                                break;
                            case MY_DEVICE_URL:
                                type = new TypeToken<MyApplianceResponse>() {
                                }.getType();
                                MyApplianceResponse myApplianceResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(myApplianceResponse);
                                break;
                            case DELETE_DEVICE_URL:
                                type = new TypeToken<DeleteApplianceResponse>() {
                                }.getType();
                                DeleteApplianceResponse deleteApplianceResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(deleteApplianceResponse);
                                break;
                            case ADD_APPLIANCE_USER_URL:
                                type = new TypeToken<AddApplianceResponse>() {
                                }.getType();
                                AddApplianceResponse addApplianceResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(addApplianceResponse);
                                break;
                            case GET_TIME_SLOT_URL:
                                type = new TypeToken<TimeSlotInfo>() {
                                }.getType();
                                TimeSlotInfo timeSlotInfo = gson.fromJson(response, type);
                                resultGeoname.responseResult(timeSlotInfo);
                                break;
                            case REQUEST_SERVICE_URL:
                                type = new TypeToken<RequestServiceResponse>() {
                                }.getType();
                                RequestServiceResponse requestServiceResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(requestServiceResponse);
                                break;
                            case REQUEST_SERVICE_LIST_URL:
                                type = new TypeToken<RequestServiceListResponse>() {
                                }.getType();
                                RequestServiceListResponse requestServiceListResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(requestServiceListResponse);
                                break;
                            case SUPPORT_TICKET_URL:
                                type = new TypeToken<SupportTicketResponse>() {
                                }.getType();
                                SupportTicketResponse supportTicketResponse = gson.fromJson(response, type);
                                resultGeoname.responseResult(supportTicketResponse);
                                break;



                            default:
                                break;

                        }

                        if (!flagSilent) {
                            progressHUD.dismiss();
                        }
                    }


            }, new ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Response Error", "ER");

                    if (!flagSilent) {
                        progressHUD.dismiss();
                    }
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    return params;
                }

            };
            jsonObjectRequest.setShouldCache(false);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            HttpsTrustManager.allowAllSSL();
            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

            System.out.println("SOP JsonTester Ending.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}