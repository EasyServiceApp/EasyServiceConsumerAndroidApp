package com.sdk.sampleapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mlapps.truevaluesdk.CommSharedPreff;
import com.mlapps.truevaluesdk.GpsTest;
import com.mlapps.truevaluesdk.SystemProperties;
import com.mlapps.truevaluesdk.TestResultCallbacks;
import com.service.easyservice.DiagnoseActivity;
import com.service.easyservice.R;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import util.logger.Log;



@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    int mDay = 0, mMonth = 0, mYear = 0;

    boolean isResultScreen = false;
    boolean isResultState = false;
    ViewPager viewPager;

    public static HashMap<String, Integer> resultmapfailed=new HashMap<String, Integer>();
    public static int isResult;
    public static final String PROPERTY_MULTI_SIM_CONFIG = "persist.radio.multisim.config";


    public static MainActivity mInstance = null;
    private TestResultCallbacks mCallback = null;

    String resultstr = null;
    String result = null;
    String pdfurlstr = null;
    String strMessage = null;
    String errorcodestr = null;
    String errormsgstr = null;
    String certinumberstr = null;
    String error = null;
    String issynced = null;
    String idfaVal = null;
    RelativeLayout qutrustRelativeLayout = null;
    public static JSONObject resultObject = null;


    public static boolean isResultSubmit = false;
    String filePath = null;

    int statusCode = -1;
    boolean isValidating = false;

    AlertDialog mOtpFetchDlg = null;
    Timer mOtpFetchTimer = null;
    int mOtpFetchTime = 10;
    TextView mOtpTxtView = null;
    public static boolean dualsimcheck = false;
    ImageButton imgButtonViewCertificate;
    public TextView certificatenumbers, certificatenumbertitle1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        LocationManager mService = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // TrueValueSDK tv=new TrueValueSDK();
        //tv.telInitialization(getApplicationContext());
        boolean extcom = mService.sendExtraCommand(LocationManager.GPS_PROVIDER, "delete_aiding_data", null);
        //System.out.println("Vikash ext comm1="+extcom);
        Bundle bundle = new Bundle();
        boolean extcomm1 = mService.sendExtraCommand("gps", "force_xtra_injection", bundle);
        //System.out.println("Vikash ext comm2=" + extcomm1);
        boolean extcomm2 = mService.sendExtraCommand("gps", "force_time_injection", bundle);
        // System.out.println("Vikash ext comm3=" + extcomm2);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



        mInstance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        checkForCrashes();
        getIdfaVal(getApplicationContext());

        imgButtonViewCertificate = (ImageButton) findViewById(R.id.view_certificate_button);
        certificatenumbers = (TextView) findViewById(R.id.certificatenumber);
        certificatenumbertitle1 = (TextView) findViewById(R.id.certificatenumbertitle);
        qutrustRelativeLayout = (RelativeLayout) findViewById(R.id.qutrustlayout);
        qutrustRelativeLayout.setVisibility(View.GONE);
       // ActionBar actionBar = getSupportActionBar();
       /* actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));*/

        viewPager = (ViewPager) findViewById(R.id.viewPager);


        // reset otp status
        CommSharedPreff.saveBooleanPreferences(CommSharedPreff.spKeyOTPStatus, false, getApplicationContext());

        // update top ui
        updateTopUI();

        // update mid ui
        updateMidUI();

        // update bottom ui
        isResultScreen = false;
        updateBottomUI();

        Log.info("MainActivity opened");

        if (!Config.isOfflineBuild) {
            processOtpStuff();
        }
        boolean checkRoot = true;
        if (Config.isMpHsBuild) {
            callBeginTest();
        } else if (checkRoot) {
            callBeginTest();
        }

        imgButtonViewCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {



                   SendReportOnline();
                   // SendReport();

                } catch (Exception e) {
                    Log.info("exception is  " + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    private void processOtpStuff() {
        boolean isOTPDone = CommSharedPreff.loadBooleanSavedPreferences(CommSharedPreff.spKeyOTPStatus, getApplicationContext());
        if (!isOTPDone) {
            String utmotp = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyUtmOtp, getApplicationContext());
            if (utmotp != null && utmotp.length() > 0) {
                Log.info("MainActivity utm otp found : " + utmotp);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String utmotp = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyUtmOtp, getApplicationContext());
                                new OTPCheckTask(utmotp).execute();
                            }
                        });
                    }
                }, 10);
            } else {
                Log.info("MainActivity show otp enter dialog");

                //showOTPAlert();

                showFetchOtpDlg();
            }
        }
    }

    private void showFetchOtpDlg() {
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.otp_fetch, null);

        mOtpFetchTime = 10;

        mOtpTxtView = (TextView) promptsView.findViewById(R.id.timeview);
        mOtpTxtView.setText(mOtpFetchTime + "");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(false);
        mOtpFetchDlg = alertDialogBuilder.create();
        mOtpFetchDlg.show();

        mOtpFetchTimer = new Timer();
        mOtpFetchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mOtpFetchTime = mOtpFetchTime - 1;
                        if (mOtpFetchTime <= 0) {
                            if (mOtpFetchTimer != null) {
                                mOtpFetchTimer.cancel();
                                mOtpFetchTimer.purge();
                                mOtpFetchTimer = null;
                            }

                            if (mOtpFetchDlg != null)
                                mOtpFetchDlg.dismiss();

                            showOTPAlert();
                        } else {
                            String utmotp = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyUtmOtp, getApplicationContext());
                            if (utmotp != null && utmotp.length() > 0) {
                                if (mOtpFetchTimer != null) {
                                    mOtpFetchTimer.cancel();
                                    mOtpFetchTimer.purge();
                                    mOtpFetchTimer = null;
                                }

                                if (mOtpFetchDlg != null)
                                    mOtpFetchDlg.dismiss();

                                new OTPCheckTask(utmotp).execute();
                            }

                            if (mOtpFetchDlg != null && mOtpTxtView != null) {
                                mOtpTxtView.setText(mOtpFetchTime + "");
                            }
                        }
                    }
                });
            }
        }, 1 * 1000, 1 * 1000);
    }


    public String getIdfaVal(final Context ctx) {
        if (idfaVal == null || idfaVal.length() <= 0) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        AdvertisingIdClient.Info adinfo = AdvertisingIdClient
                                .getAdvertisingIdInfo(ctx);
                        idfaVal = adinfo.getId();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (idfaVal == null || idfaVal.length() <= 0) {
                        idfaVal = Settings.Secure.getString(ctx.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                    }
                    return null;
                }
            }.execute();
        }
        return idfaVal;
    }

    AlertDialog alertDialog = null;
    EditText otpFld = null;

    private void showOTPAlert() {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.otp_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        alertDialogBuilder.setView(promptsView);

        otpFld = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        otpFld.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        otpFld.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String otpVal = otpFld.getText().toString().trim();
//                System.out.println("otpVal : "+ otpVal);
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    finish();
//                }
//                else
                if (otpVal.length() == 6) {
                    alertDialog.dismiss();

                    // start
                    new OTPCheckTask(otpVal).execute();
                }
            }
        });

        // set dialog message
//        .setPositiveButton("Validate", null)
        alertDialogBuilder
                .setCancelable(false);
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                finish();
//                            }
//                        });

        // create alert dialog
        alertDialog = alertDialogBuilder.create();


        // show it
        alertDialog.show();
    }

    private void checkInternet() {
        Log.info("Inside alert2");
        System.gc();
        final AlertDialog.Builder alrt = new AlertDialog.Builder(MainActivity.this);

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();


        try {
            ConnectivityManager connectivityManagers
                    = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfos = connectivityManager.getActiveNetworkInfo();

            LocationManager mService = null;
            mService = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            final List<String> providers = mService.getAllProviders();
            if (mService != null && providers != null && (providers.contains(LocationManager.GPS_PROVIDER))) {
                boolean enabled1 = mService.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (!enabled1) {
                    Log.info("Inside alert1");

                    alrt.setTitle("Location Problem");
                    alrt.setCancelable(false);
                    alrt.setMessage("Your location Service is not on please click on the Enable button to turn on the Location service");
                    alrt.setNeutralButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                            // turnGPSOn();
                            //dialog.cancel();

                            //turnGpsOn();
                            // startTestProcess();
                            MainActivity.this.startActivityForResult(intent, 24);

                        }
                    });

                    alrt.show();
                    // showAlert();
                } else {
                    GpsTest.startGpsSearch(MainActivity.this.getApplicationContext());
                    startTestProcess();
                }

            }
            else {
                Log.info("MAin ACtivity","GPS PRovider unable to find through android code");
                startTestProcess();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //  }
    }

    public void showAlert() {
        Log.info("Inside alert");

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = ((Activity) MainActivity.this).getLayoutInflater();
        View alertView = inflater.inflate(R.layout.layout_test, null);
        alertDialog.setView(alertView);

        final AlertDialog show = alertDialog.show();

        Button alertButton = (Button) alertView.findViewById(R.id.btn_test);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                // turnGPSOn();

                //turnGpsOn();
                // startTestProcess();
                // MainActivity.this.startActivityForResult(intent, 24);
                turnGPSOn();
                show.dismiss();
            }
        });
    }

    public class SendTestResult extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        String resultVal = "";
        String errorStr = "";

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Submitting Result...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            doHttpReq();
            return null;
        }

        @Override
        protected void onPostExecute(String params) {
            super.onPostExecute(params);
            pDialog.dismiss();

            if (resultVal.equalsIgnoreCase("false")) {
//                isResultSubmit = false;
                if (errorStr == null || errorStr.length() <= 0) {
                    errorStr = "Unable to submit result. Please try again!";
                }
                android.util.Log.i("MainActivity", "errorStr: " + errorStr);

            } else if (resultVal.equalsIgnoreCase("true")) {
                isResultSubmit = true;
                updateTopUI();
                updateMidUI();

//                .setTitle("Success")
                if (!Config.isOfflineBuild) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("All checks done !")
                            .setMessage(Html.fromHtml("Complete your order on QuikrX and get the true price for this phone.<br><br>Click continue to go there now."))
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    openBrowser();
                                }
                            }).show();
                }
            } else {
                isResultSubmit = true;
                updateTopUI();
                updateMidUI();
            }

            if (isResultSubmit == true) {
                CommSharedPreff.saveStringPreferences(CommSharedPreff.spKeyUtmOtp, "", MainActivity.this);


            }
        }

        private void doHttpReq() {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


            String URL = Config.baseUrl + Config.deviceCertificationReportURL;
            String postData = resultObject.toString();

            String serverResponse = PostRequest(URL, postData);

            Log.info("UploadResult", "Server Req : " + URL + " , PostData : "
                    + postData + " , Server Response : " + serverResponse);

            // simulate

            try {
                JSONObject root = new JSONObject(serverResponse);
                resultVal = root.getString("result");

                JSONArray errorArr = root.getJSONArray("error");
                if (errorArr != null) {
                    try {
                        JSONObject errorObj = (JSONObject) errorArr.get(0);
                        if (errorObj != null) {
                            errorStr = errorObj.getString("message");
                        }
                    } catch (Exception e) {
                    }
                }

                if (resultVal != null && resultVal.length() > 0) {
                    if (resultVal.equalsIgnoreCase("true")) {

                        String otpmessage = root.getString("message");
                        CommSharedPreff
                                .saveStringPreferences(
                                        CommSharedPreff.spKeyOTPMessage, otpmessage,
                                        MainActivity.this);

                        String reportid = root.getString("reportid");
                        CommSharedPreff
                                .saveStringPreferences(
                                        CommSharedPreff.spKeyOTPReportid, reportid,
                                        MainActivity.this);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void openBrowser() {
        final String landingPageUrl = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyLandingPageUrl, MainActivity.this);

        Log.info("landing url1 : " + landingPageUrl);
        // add query params
        if (landingPageUrl == null && landingPageUrl.length() <= 0) {
            return;
        }

        // ContextSdk.tagEvent("OnContinueExchangeClick", null);

        String newProductId = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyNewProductId, MainActivity.this);
        String exchangeProductId = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyExchangeProductId, MainActivity.this);
        String reportId = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyOTPReportid, MainActivity.this);

        Log.info("landingPageUrl " + landingPageUrl);
        Log.info("landingPageUrl " + landingPageUrl);
        String landingUrl = landingPageUrl + "?newProductId=" + newProductId + "&exchangeProductId=" + exchangeProductId + "&reportId=" + reportId;

        Log.info("landingUrl " + landingUrl);
        if (!landingUrl.startsWith("https://") && !landingUrl.startsWith("http://")) {
            landingUrl = "http://" + landingUrl;
        }


        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse(landingUrl));
        startActivity(i);
    }



    private class OTPCheckTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        String resultVal = "";
        String errorStr = "";
        String otpVal = "";

        public OTPCheckTask(String otpValue) {
            this.otpVal = otpValue;
            isValidating = true;
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Connecting to Quikr...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            doHttpReq();
            return null;
        }

        private void doHttpReq() {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imeiVal = telephonyManager.getDeviceId();
            String androidId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            String URL = Config.baseUrl + Config.deviceCertificationOTPURL;
            String postData = "imei=" + imeiVal + "&deviceid=" + androidId
                    + "&idfa=" + idfaVal + "&partnerid=4&otp=" + this.otpVal;

            Log.info("MainActivity otpValidation call URL : " + URL + " , postData : " + postData);

            // otp validation - check if client id is not null and then add clientid in api call
            String utmClientId = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyUtmClientId, MainActivity.this);
            if (utmClientId != null && utmClientId.length() > 0) {
                postData += "&clientid=" + utmClientId;
            }

            String serverResponse = PostRequest(URL, postData);
            Log.info("MainActivity otpValidation api response : " + serverResponse + " , code : " + statusCode);

            if (statusCode != 200) {
                resultVal = "Internet";
                return;
            }

            try {
                JSONObject root = new JSONObject(serverResponse);
                resultVal = root.getString("result");

                JSONArray errorArr = root.getJSONArray("error");
                if (errorArr != null) {
                    try {
                        JSONObject errorObj = (JSONObject) errorArr.get(0);
                        if (errorObj != null) {
                            errorStr = errorObj.getString("message");
                        }
                    } catch (Exception e) {
                    }
                }

                if (resultVal != null && resultVal.length() > 0) {
                    if (resultVal.equalsIgnoreCase("true")) {
                        if (alertDialog != null)
                            alertDialog.cancel();

                        CommSharedPreff
                                .saveBooleanPreferences(
                                        CommSharedPreff.spKeyOTPStatus, true,
                                        MainActivity.this);

                        try {
                            if (otpFld != null) {
                                CommSharedPreff
                                        .saveStringPreferences(
                                                CommSharedPreff.spKeyOTPVal, otpFld.getText().toString(),
                                                MainActivity.this);
                            }
                        } catch (Exception e) {
                        }


                        JSONObject subroot = root.getJSONObject("details");
                        if (subroot != null) {
                            try {
                                String emailId = subroot.getString("emailId");
                                if (emailId == null || emailId.equalsIgnoreCase("null") || emailId.length() <= 0) {
                                    emailId = "";
                                }
                                CommSharedPreff
                                        .saveStringPreferences(
                                                CommSharedPreff.spKeyOTPEmail, emailId,
                                                MainActivity.this);
                            } catch (Exception e) {
                            }

                            try {
                                String mobileNo = subroot.getString("mobileNo");
                                if (mobileNo == null || mobileNo.equalsIgnoreCase("null") || mobileNo.length() <= 0) {
                                    mobileNo = "";
                                }
                                CommSharedPreff
                                        .saveStringPreferences(
                                                CommSharedPreff.spKeyOTPPhNo, mobileNo,
                                                MainActivity.this);
                            } catch (Exception e) {
                            }

                            JSONObject otproot = subroot.getJSONObject("otpAttributes");
                            if (otproot != null) {
                                try {
                                    String newProductId = otproot.getString("newProductId");
                                    if (newProductId == null || newProductId.equalsIgnoreCase("null") || newProductId.length() <= 0) {
                                        newProductId = "";
                                    }
                                    CommSharedPreff
                                            .saveStringPreferences(
                                                    CommSharedPreff.spKeyNewProductId, newProductId,
                                                    MainActivity.this);
                                } catch (Exception e) {
                                }
                                try {
                                    String exchangeProductId = otproot.getString("exchangeProductId");
                                    if (exchangeProductId == null || exchangeProductId.equalsIgnoreCase("null") || exchangeProductId.length() <= 0) {
                                        exchangeProductId = "";
                                    }
                                    CommSharedPreff
                                            .saveStringPreferences(
                                                    CommSharedPreff.spKeyExchangeProductId, exchangeProductId,
                                                    MainActivity.this);
                                } catch (Exception e) {
                                }
                                try {
                                    String landingPageUrl = otproot.getString("landingPageUrl");
                                    if (landingPageUrl == null || landingPageUrl.equalsIgnoreCase("null") || landingPageUrl.length() <= 0) {
                                        landingPageUrl = "";
                                    }
                                    Log.info("landing url : " + landingPageUrl);
                                    CommSharedPreff
                                            .saveStringPreferences(
                                                    CommSharedPreff.spKeyLandingPageUrl, landingPageUrl,
                                                    MainActivity.this);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(String params) {
            super.onPostExecute(params);
            pDialog.dismiss();

            //ContextSdk.tagEvent("OnOTPValidation", null);

            if (resultVal.equalsIgnoreCase("false")) {
                if (errorStr != null && errorStr.length() > 0) {
                    // nothing to be done
                } else {
                    errorStr = "Unable to validate OTP. Please try again!";
                }
                android.util.Log.i("MainActivity", "errorStr1: " + errorStr);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Error")
                        .setCancelable(false)
                        .setMessage(errorStr)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                showOTPAlert();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            } else if (resultVal.equalsIgnoreCase("true")) {
                //checkInternet();
            } else if (resultVal.equalsIgnoreCase("Internet")) {
                Toast.makeText(MainActivity.this, "Unable to validate OTP. Please try again!", Toast.LENGTH_LONG).show();
                showOTPAlert();
            }
        }
    }

    private void startTestProcess() {
        Log.info("Inside startTestProcess");
        // show layer of pulse
        RelativeLayout pulselayer = (RelativeLayout) MainActivity.this.findViewById(R.id.pulseview);
        if (pulselayer != null) {
            Log.info("Inside pulselayer not null");
            pulselayer.setVisibility(View.VISIBLE);
            Log.info("Inside pulselayer setvisibility visible");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.info("Inside timer");
                            RelativeLayout pulselayer = (RelativeLayout) MainActivity.this.findViewById(R.id.pulseview);
                            Log.info("Inside timer-0");
                            pulselayer.setVisibility(View.GONE);
                            Log.info("Inside timer-1");
                            Log.info("MainActivity starting TestProcess");

                            // starttesting process
                            Intent intent = new Intent(
                                    getApplicationContext(),
                                    TestActivity.class);
                            startActivityForResult(intent, 100);
                        }
                    });
                }
            }, 2 * 1000);
        }
    }

    private String PostRequest(String uRL, String dataSendToTheServer) {

        Log.info("Vikash inside postreq=" + dataSendToTheServer);
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer("");
        try {
            /**
             * Load Key store from certificate and password As now it is set
             * to NULL
             */
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);
            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
                    HttpVersion.HTTP_1_1);
            params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
                    HTTP.UTF_8);

            int timeoutConnection = 5000;
            HttpConnectionParams
                    .setConnectionTimeout(params, timeoutConnection);
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(params, timeoutSocket);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);
            HttpClient client = new DefaultHttpClient(ccm, params);
            HttpPost request = new HttpPost(uRL);
            request.setEntity(new ByteArrayEntity(dataSendToTheServer
                    .getBytes("UTF8")));
            StringEntity se = new StringEntity(dataSendToTheServer,
                    HTTP.UTF_8);
            //se.setContentType("application/x-www-form-urlencoded");
            request.setEntity(se);
           /* ArrayList<NameValuePair> seClientID = new ArrayList<NameValuePair>();
            seClientID.add(new BasicNameValuePair("partner_id", Config.partnerId));
            request.setEntity(new UrlEncodedFormEntity(seClientID));
            request.getParams().setBooleanParameter(
                    "http.protocol.expect-continue", false);*/
            /** Getting Response from server. */
            HttpResponse response = client.execute(request);
            statusCode = response.getStatusLine().getStatusCode();

            /** Reading Response stream from server. */
            in = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));
            String line = "";
            String NL = System.getProperty("line.separator");
            /** Reading server response. */
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();

                } catch (IOException e) {

                }
            }
        }
        return sb.toString();
    }

    private void updateBottomUI() {

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        indicator.setVisibility(View.INVISIBLE);
    }

    private void updateTopUI() {
        TextView lastScore = (TextView) findViewById(R.id.scoreValue);
        int lastScoreVal = CommSharedPreff.loadIntSavedPreferences(
                CommSharedPreff.spKeyLastScoreVal, getApplicationContext());
        if (lastScoreVal <= 0)
            lastScore.setText("0/0");
        else
            lastScore.setText("" + lastScoreVal + "/100");

        TextView lastTime = (TextView) findViewById(R.id.lastCheckDate);
        long lastCheckTime = CommSharedPreff.loadLongSavedPreferences(
                CommSharedPreff.spKeyLastCheckTime, getApplicationContext());
        if (lastCheckTime <= 0)
            lastTime.setText("  Last Checked: Starting...");
        else {
            Date date = new Date(lastCheckTime);
            SimpleDateFormat sdf = new SimpleDateFormat("ccc, MMM d yyyy");
            String formattedDate = sdf.format(date);
            lastTime.setText("  Last Checked: " + formattedDate);
        }


    }

    private void updateMidUI() {
        RelativeLayout quikrlayout = (RelativeLayout) findViewById(R.id.quikrlayout);
        android.util.Log.i("TestActivity", "isResultSubmit: " + isResultSubmit);
//        isResultSubmit = true;
//        quikrlayout.setVisibility(View.GONE);
        if (isResultSubmit == true) {
//            Button btn = (Button) quikrlayout.findViewById(R.id.continueExBtn);
            quikrlayout.setVisibility(View.VISIBLE);

        } else {
            quikrlayout.setVisibility(View.GONE);
        }
    }

    private void updateBottomUISelectDate(View root) {

        TextView modelNo = (TextView) root.findViewById(R.id.modelNum);
        modelNo.setText("" + Build.MODEL);

        TextView BuildNo = (TextView) root.findViewById(R.id.buildnumber);
        BuildNo.setText("" + Build.DISPLAY);

        Log.info("BuildNumber=" + Build.DISPLAY);

        TextView androidVer = (TextView) root.findViewById(R.id.androidVer);
        androidVer.setText("" + Build.VERSION.RELEASE);

        TextView dateTxt = (TextView) root.findViewById(R.id.purchaseDate);
        dateTxt.setText("Select date");

        TextView selectDate = (TextView) root.findViewById(R.id.purchaseDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show date picker to user
                Date date = new Date(System.currentTimeMillis());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                DatePickerDialog dlg = new DatePickerDialog(MainActivity.this,
                        myDateListener, cal.get(Calendar.YEAR), cal
                        .get(Calendar.MONTH), cal.get(Calendar.DATE));
                // To hide date column
                // ((ViewGroup) dlg.getDatePicker()).findViewById(
                // Resources.getSystem().getIdentifier("day", "id",
                // "android")).setVisibility(View.GONE);
                dlg.show();
            }
        });

        Button startBtn = (Button) root.findViewById(R.id.beginTest);
        if (Config.isOfflineBuild || Config.isMpHsBuild) {
            startBtn.setVisibility(View.INVISIBLE);
        } else {
            startBtn.setVisibility(View.VISIBLE);
        }
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                genearteCrash();
                boolean checkRoot = true;


                if (Config.isMpHsBuild) {
                    callBeginTest();
                } else if (checkRoot) {
                    callBeginTest();
                }
            }
        });


    }

    private void callBeginTest() {


        TestActivity.testsSkip = false;
        TestActivity.forceFail = false;

        if (Config.isMpHsBuild) {
            checkInternet();
        } else {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Important")

                    .setMessage(getResources().getString(R.string.proceed))
                    .setPositiveButton("Proceed",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // getPhoneCount();
                                    checkInternet();


                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }

    private void updateBottomUIResult(View root) {
        if (isResultState) {
            TextView resultTitle = (TextView) root
                    .findViewById(R.id.resultTitle);
            resultTitle.setText("CONGRATULATIONS!");

            TextView resultInfo = (TextView) root.findViewById(R.id.resultInfo);
            // resultInfo
            // .setText("Your device score qualifies\nyou for an insurance");
            resultInfo.setText("");

            Button resultBtn = (Button) root.findViewById(R.id.resultBtn);
            resultBtn.setBackgroundResource(R.drawable.button_viewreport);
            resultBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // updateBottomUIReport();
                    viewPager.setCurrentItem(1, true);
                }
            });
        } else {
            TextView resultTitle = (TextView) root
                    .findViewById(R.id.resultTitle);
            resultTitle.setText("SORRY!");

            TextView resultInfo = (TextView) root.findViewById(R.id.resultInfo);
            resultInfo
                    .setText("Your device score is\ntoo low for an insurance");

            Button resultBtn = (Button) root.findViewById(R.id.resultBtn);
            resultBtn.setBackgroundResource(R.drawable.button_viewreport);
            resultBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // updateBottomUIReport();
                    viewPager.setCurrentItem(1, true);
                }
            });
        }
    }

    private void updateBottomUIReport(View root) {
        SharedPreferences sf = getSharedPreferences("FinalResult", MODE_PRIVATE);

        String lastMandatoryReport = sf.getString("Mandatory", "");
//        System.out.println("App Report mandatory report="+lastMandatoryReport);
        String[] listMandatoryArr = lastMandatoryReport.split(",");
//        System.out.println("App Report mandatory report1="+listMandatoryArr);

        String lastMandatoryJson = sf.getString("MandatoryJson", "");
//        System.out.println("App Report mandatory report2="+lastMandatoryJson);


        String lastOptionalReport = sf.getString("Optional", "");
//        System.out.println("App Report optional report3="+lastMandatoryJson);
        String[] listOptionalArr = lastOptionalReport.split(",");
//        System.out.println("App Report optional report4="+listOptionalArr);

        String lastOptionalJson = sf.getString("OptionalJson", "");

//        System.out.println("App Report optional report5="+lastOptionalJson);

        ArrayList<JSONObject> jsondata = new ArrayList<>();
        try {
            jsondata.add(new JSONObject("{}"));

            JSONArray mandatoryJson = new JSONArray(lastMandatoryJson);
            if (mandatoryJson != null) {
                for (int i = 0; i < mandatoryJson.length(); i++)
                    jsondata.add((JSONObject) mandatoryJson.get(i));
            }

            jsondata.add(new JSONObject("{}"));

            JSONArray optionalJson = new JSONArray(lastOptionalJson);
            if (optionalJson != null) {
                for (int i = 0; i < optionalJson.length(); i++)
                    jsondata.add((JSONObject) optionalJson.get(i));
            }
        } catch (Exception e) {
            Log.info("App report Exception");
        }

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(listMandatoryArr));
        list.addAll(Arrays.asList(listOptionalArr));
        Log.info("App Report optional report6=" + list);
        Log.info("App Report optional report7=" + jsondata);


        Object[] listArr = list.toArray();
        ExpandableListView listview = (ExpandableListView) root.findViewById(R.id.reportList);

//        ResultListAdapter adapter = new ResultListAdapter(getApplicationContext(), list);
        ExpandableListAdapter adapter = new ExpandableListAdapter(getApplicationContext(), list, jsondata);

        for (int i = 0; i < (listArr.length); i++) {
            if (i == 0) {
                adapter.addSeparatorItem("MANDATORY TEST");
            } else if (listMandatoryArr.length == i) {
                adapter.addSeparatorItem("OPTIONAL TEST");
            } else {
                adapter.addItem(listArr[i]);
                Log.info("App Report optional report8=" + listArr[i]);
            }
        }
        listview.setAdapter(adapter);

      /*  FloatingActionButton FAB = (FloatingActionButton) root.findViewById(R.id.fab1);
        FAB.setVisibility(View.GONE);*/

    }




    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (isResultScreen) {
                return 1;
            } else {
                return 1;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View root = null;

            if (isResultScreen) {
                // if (position == 0) {
                // root = mInflater.inflate(R.layout.subview_testresult,
                // container, false);
                // updateBottomUIResult(root);Inside N/w Test
                // } else if (position == 1)
                {
                    root = mInflater.inflate(R.layout.subview_testreport,
                            container, false);
                    updateBottomUIReport(root);
                }
            } else {
                if (position == 0) {
                    root = mInflater.inflate(R.layout.subview_begintest,
                            container, false);
                    updateBottomUISelectDate(root);
                }
            }
            ((ViewPager) container).addView(root);
            return root;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        String arr_check[] = CommSharedPreff.loadCertificateData(MainActivity.this);
        if (arr_check[0].equals("no") && arr_check[1].equals("no") && arr_check[2].equals("no") && arr_check[3].equals("no") && arr_check[4].equals("no") && arr_check[5].equals("no")) {
            inflater.inflate(R.menu.activity_main_action, menu);

        } else {
            inflater.inflate(R.menu.activity_certificate_menu, menu);
        }
//        inflater.inflate(R.menu.activity_main_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String arr_check[] = CommSharedPreff.loadCertificateData(MainActivity.this);
        // menu.clear();
        if (arr_check[0].equals("no") && arr_check[1].equals("no") && arr_check[2].equals("no") && arr_check[3].equals("no") && arr_check[4].equals("no") && arr_check[5].equals("no")) {
//            menu.add(0, MENU_ADD, Menu.NONE,"Download");
//            menu.add(0, MENU_ADD, Menu.NONE,"Download");
        } else {

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_retest: {
                callBeginTest();
                break;
            }*/
            case R.id.action_help: {
                showHelpInfo(MainActivity.this);
                break;
            }
            case R.id.action_about: {
                showAboutInfo(MainActivity.this);
                break;
            }

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public static void showHelpInfo(Context ctx) {
        String appName = ctx.getResources().getString(R.string.app_name);
        new AlertDialog.Builder(ctx)
                .setTitle("Help")
                .setMessage(
                        appName + ctx.getResources().getString(R.string.help))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }

    public static void showAboutInfo(Context ctx) {
        String appName = ctx.getResources().getString(R.string.app_name);
        String versionName = "";
        try {
            versionName = ctx.getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
        new AlertDialog.Builder(ctx)
                .setTitle("About Us")
                .setMessage(
                        appName + ctx.getResources().getString(R.string.about_us))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            mDay = day;
            mMonth = month;
            mYear = year;

            SimpleDateFormat format = new SimpleDateFormat("ccc, MMM dd yyyy");
            String fromatStr = format.format(new Date(year - 1900, month, day));
            TextView dateTxt = (TextView) findViewById(R.id.purchaseDate);
            dateTxt.setText(fromatStr);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            Log.info("Vikash inside onActivityResult hundred");
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            if (resultCode == RESULT_OK) {
                String str = data.getStringExtra("close");
                if ("close".equals(str)) {
                    finish();
                }


                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                //ContextSdk.tagEvent("OnTestCompleted", null);

                Log.info("MainActivity Test performed ok");

                if (data != null) {
                    int scoreVal = data.getIntExtra("score", 0);
                    CommSharedPreff.saveIntPreferences(
                            CommSharedPreff.spKeyLastScoreVal, scoreVal,
                            getApplicationContext());

                    long dateVal = data.getLongExtra("date", 0);
                    CommSharedPreff.saveLongPreferences(
                            CommSharedPreff.spKeyLastCheckTime, dateVal,
                            getApplicationContext());

//                    String resultReport = data.getStringExtra("data");
//                    CommSharedPreff.saveStringPreferences(
//                            CommSharedPreff.spKeyLastReportVal, resultReport,
//                            getApplicationContext());

                    String resultMandatoryReport = data.getStringExtra("mandatorydata");
                    String resultMandatoryJson = data.getStringExtra("mandatoryjson");
                    String resultOptionalReport = data.getStringExtra("optionaldata");
                    String resultOptionalJson = data.getStringExtra("optionaljson");

                    CommSharedPreff.SaveResult(resultMandatoryReport, resultMandatoryJson, resultOptionalReport, resultOptionalJson, MainActivity.this);

                    // send result info to server
//                    new SendTestResult().execute();

                    // update Top ui with info
                    updateTopUI();
                    updateMidUI();

                    if (scoreVal > 80) {
                        isResultState = true;
                        isResultScreen = true;
                        updateBottomUI();
                        //new SendLog(getApplicationContext()).execute();
                    } else {
                        isResultState = false;
                        isResultScreen = true;
                        updateBottomUI();
                        // new SendLog(getApplicationContext()).execute();
                    }
                } else {
                    updateTopUI();
                    updateMidUI();
                    isResultScreen = false;
                    updateBottomUI();

                    // new SendLog(getApplicationContext()).execute();
                }
            } else if (resultCode == RESULT_CANCELED) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                Log.info("MainActivity test performed cancelled");

                updateTopUI();
                updateMidUI();
                isResultState = false;
                isResultScreen = true;
                updateBottomUI();

                // new SendLog(getApplicationContext()).execute();
            }

            String arr_check[] = CommSharedPreff.loadCertificateData(MainActivity.this);
            if (arr_check[0].equals("no") && arr_check[1].equals("no") && arr_check[2].equals("no") && arr_check[3].equals("no") && arr_check[4].equals("no") && arr_check[5].equals("no")) {
                qutrustRelativeLayout.setVisibility(View.GONE);

            } else {
                qutrustRelativeLayout.setVisibility(View.VISIBLE);
            }

            SendReportOnline();
          //  SendReport();

        } else if (requestCode == 24) {
            GpsTest.startGpsSearch(MainActivity.this.getApplicationContext());

            startTestProcess();

        }
    }

    class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final Context context;
        private final ArrayList<String> values;
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;
        private ArrayList<String> mData = new ArrayList<String>();
        //        private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();
        private ArrayList<JSONObject> listData = null;

        public ExpandableListAdapter(Context context, ArrayList<String> values, ArrayList<JSONObject> listData) {
            this.context = context;
            this.values = values;
            this.listData = listData;
        }

        public void addItem(final Object item) {
            mData.add((String) item);
        }

        public void addSeparatorItem(final String item) {
            mData.add(item);
//            mSeparatorsSet.add(mData.size() - 1);
        }

        @Override
        public int getGroupCount() {
            return mData.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mData.get(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            Log.info("Vikas inside list9=" + groupPosition);
            if (mData.get(groupPosition).length() > 2) {
                // Separator section return zero child count
                return 0;
            } else {
                // Item section return inside child count
                int childCount = 0;
                try {
                    Log.info("Vikas inside list10=" + groupPosition);
                    JSONObject root = listData.get(groupPosition);
                    Log.info("Vikas inside list11=" + root);
                    if (root != null) {
                        JSONObject data = root.getJSONObject("data");
                        Log.info("Vikas inside list12=" + data);
                        if (data != null) {
                            Log.info("Vikas inside list13=" + data);
                            if (data.names() != null) {
                                childCount = data.names().length();
                            }
                            Log.info("Vikas inside list14=" + childCount);
                        }
                    }
                } catch (Exception e) {
                    Log.info("Vikas inside list15=" + e);
                }
                return childCount;
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            Log.info("Vikas inside list0=" + groupPosition + "Child position=" + childPosition);
            if (mData.get(groupPosition).length() > 2) {
                // Separator section return zero child count
                return "";
            } else {
                // Item section return inside child count
                try {
                    JSONObject root = listData.get(groupPosition);
                    Log.info("Vikas inside list1=" + root);
                    if (root != null) {
                        Log.info("Vikas inside list2=");
                        JSONObject data = root.getJSONObject("data");
                        Log.info("Vikas inside list3=" + data);
                        if (data != null) {
                            Log.info("Vikas inside list4=" + data.names().get(childPosition));
                            return data.names().get(childPosition);
                        }
                    }
                } catch (Exception e) {
                    Log.info("Vikas inside list5=");
                    return null;
                }
            }
            return null;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        private int mWifGroupIndex = -1;

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            Log.info("Vikas inside list7=" + groupPosition + "Expanded=" + isExpanded + "ConvertView=" + convertView + "Viewgroupparent=" + parent);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int type = 0;
            if (mData.get(groupPosition).length() > 2) {
                Log.info("Vikas inside list8=" + groupPosition + "Expanded=" + isExpanded + "ConvertView=" + convertView + "Viewgroupparent=" + parent);
                type = TYPE_SEPARATOR;
            } else
                type = TYPE_ITEM;

//            int type = getItemViewType(position);
//            System.out.println("Android " + type);
            View rowView = null;
            switch (type) {
                case TYPE_ITEM:
                    rowView = inflater.inflate(R.layout.rowlayout, parent, false);

                    ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
                    TextView text = (TextView) rowView.findViewById(R.id.text);
                    ImageView info = (ImageView) rowView.findViewById(R.id.info);
                    ImageView expand = (ImageView) rowView.findViewById(R.id.expand_img);


                    Object s1 = values.get(groupPosition);
                    if (TestActivity.isDualSim) {
                        switch (groupPosition) {
                            case 0:// Condition to handle header content for MandatoryTest
                                break;
                            case 1:
                                icon.setImageResource(R.drawable.report_networksim);
                                text.setText("Network Signal SIM 1");
                                break;
                            case 2:
                                icon.setImageResource(R.drawable.report_networksim);
                                text.setText("Network Signal SIM 2");
                                break;
                            case 3:
                                icon.setImageResource(R.drawable.report_deviceablecall);
                                text.setText("Call SIM 1");
                                break;
                            case 4:
                                icon.setImageResource(R.drawable.report_deviceablecall);
                                text.setText("Call SIM 2");
                                break;
                            case 5:
                                icon.setImageResource(R.drawable.report_sms);
                                text.setText("SMS SIM 1");
                                break;
                            case 6:
                                icon.setImageResource(R.drawable.report_sms);
                                text.setText("SMS SIM 2");
                                break;
                            case 7:
                                mWifGroupIndex = groupPosition;
                                icon.setImageResource(R.drawable.report_wifi);
                                text.setText("WiFi");
                                break;
                            case 8:
                                icon.setImageResource(R.drawable.report_internet);
                                text.setText("Internet");
                                break;
                            case 9:
                                icon.setImageResource(R.drawable.report_imei);
                                text.setText("IMEI");
                                break;
                            case 10:
                                icon.setImageResource(R.drawable.report_devicerooted);
                                text.setText("Device Non Rooted");
                                break;
                            case 11:
                                icon.setImageResource(R.drawable.report_simcard);
                                text.setText("SIM Slot 1");
                                break;
                            case 12:
                                icon.setImageResource(R.drawable.report_simcard);
                                text.setText("SIM Slot 2");
                                break;
                            case 13:
                                icon.setImageResource(R.drawable.report_camera);
                                text.setText("Back Camera");
                                break;
                            case 14:
                                icon.setImageResource(R.drawable.report_frontcamera);
                                text.setText("Front Camera");
                                break;
                            case 15:
                                icon.setImageResource(R.drawable.report_ledflash);
                                text.setText("Flash Light");
                                break;
                            case 16:
                                icon.setImageResource(R.drawable.report_torch);
                                text.setText("Torch Light");
                                break;
                            case 17:
                                icon.setImageResource(R.drawable.report_battery);
                                text.setText("Battery");
                                break;
                            case 18:
                                icon.setImageResource(R.drawable.report_vibrate);
                                text.setText("Vibrate");
                                break;
                            case 19:
                                icon.setImageResource(R.drawable.report_ram);
                                text.setText("RAM");
                                break;
                            case 20:
                                icon.setImageResource(R.drawable.report_storageinternal);
                                text.setText("Internal Storage");
                                break;
                            case 21:
                                icon.setImageResource(R.drawable.report_storageexternal);
                                text.setText("External Storage");
                                break;
                            case 22:
                                icon.setImageResource(R.drawable.report_light);
                                text.setText("Light Sensor");
                                break;

                            case 23:
                                icon.setImageResource(R.drawable.report_screen);
                                text.setText("Display Touch Panel");
                                break;
                            case 24:
                                icon.setImageResource(R.drawable.brightness);
                                text.setText("Display Brightness");
                                break;
                            case 25:
                                icon.setImageResource(R.drawable.deadpixels);
                                text.setText("Display Dead Pixel");
                                break;
                            case 26:
                                icon.setImageResource(R.drawable.report_bluetooth);
                                text.setText("Bluetooth");
                                break;
                            case 27:
                                icon.setImageResource(R.drawable.report_headphonejack);
                                text.setText("Earphone Jack");
                                break;
                            case 28:
                                icon.setImageResource(R.drawable.report_powerbutton);
                                text.setText("Power Button");
                                break;
                            case 29:
                                icon.setImageResource(R.drawable.report_volumebutton);
                                text.setText("Volume Up Button");
                                break;
                            case 30:
                                icon.setImageResource(R.drawable.report_volumebutton);
                                text.setText("Volume Down Button");
                                break;
                            case 31:
                                icon.setImageResource(R.drawable.report_homebutton);
                                text.setText("Home Key");
                                break;
                            case 32:
                                icon.setImageResource(R.drawable.report_backbutton);
                                text.setText("Back Key");
                                break;
                            case 33:
                                icon.setImageResource(R.drawable.report_microphone);
                                text.setText("Microphone");
                                break;
                            case 34:
                                icon.setImageResource(R.drawable.report_loudspeakerclear);
                                text.setText("Loudspeaker");
                                break;
                            case 35:
                                icon.setImageResource(R.drawable.report_earphone);
                                text.setText("Earphone");
                                break;
                            case 36:
                                icon.setImageResource(R.drawable.report_loudspeakerclear);
                                text.setText("Receiver");
                                break;
                            case 37:
                                icon.setImageResource(R.drawable.report_cameraautofocus);
                                text.setText("Camera Autofocus");
                                break;
                            case 38:
                                icon.setImageResource(R.drawable.report_accelerometersensor);
                                text.setText("Accelerometer");
                                break;
                            case 39:
                                icon.setImageResource(R.drawable.report_gyroscopesensor);
                                text.setText("Gyroscope");
                                break;
                            case 40:
                                icon.setImageResource(R.drawable.report_proxomity);
                                text.setText("Proximity");
                                break;
                            case 41:
                                icon.setImageResource(R.drawable.report_videorecording);
                                text.setText("Back Video Recording");
                                break;
                            case 42:
                                icon.setImageResource(R.drawable.report_videorecording);
                                text.setText("Front Video Recording");
                                break;
                            case 43:
                                icon.setImageResource(R.drawable.report_usb);
                                text.setText("USB");
                                break;

                            case 44:// Condition to handle header content for OptionalTest
                                break;
                            case 45:
                                icon.setImageResource(R.drawable.report_airpressure);
                                text.setText("Air Pressure");
                                break;
                            case 46:
                                icon.setImageResource(R.drawable.report_airtempretute);
                                text.setText("Air Temperature");
                                break;
                            case 47:
                                icon.setImageResource(R.drawable.report_gravity);
                                text.setText("Gravity");
                                break;
                            case 48:
                                icon.setImageResource(R.drawable.report_infrared);
                                text.setText("Infrared");
                                break;
                            case 49:
                                icon.setImageResource(R.drawable.report_nfc);
                                text.setText("NFC");
                                break;
                            case 50:
                                icon.setImageResource(R.drawable.report_gyroscopeforgaming);
                                text.setText("Gyroscope Gaming");
                                break;
                            case 51:
                                icon.setImageResource(R.drawable.report_oriantation);
                                text.setText("Orientation");
                                break;
                            case 52:
                                icon.setImageResource(R.drawable.report_devicetemprature);
                                text.setText("Device Temperature");
                                break;
                            case 53:
                                icon.setImageResource(R.drawable.report_stepdetector);
                                text.setText("Step Detector");
                                break;
                            case 54:
                                icon.setImageResource(R.drawable.report_stepcounter);
                                text.setText("Step Counter");
                                break;
                            case 55:
                                icon.setImageResource(R.drawable.report_motiondetactor);
                                text.setText("MotionDetector");
                                break;
                            case 56:
                                icon.setImageResource(R.drawable.report_humidity);
                                text.setText("Humidity");
                                break;
                            case 57:
                                icon.setImageResource(R.drawable.report_fingreprintsensor);
                                text.setText("Biometric");
                                break;
                            case 58:
                                icon.setImageResource(R.drawable.report_heartratemonitor);
                                text.setText("HRM");
                                break;
                            case 59:
                                icon.setImageResource(R.drawable.report_megnaticsensor);
                                text.setText("Magnetic Sensor");
                                break;

                            case 60:
                                icon.setImageResource(R.drawable.report_gps);
                                text.setText("GPS");
                                break;

                            case 61:
                                icon.setImageResource(R.drawable.report_spo2);
                                text.setText("SPO2");
                                break;
                            case 62:
                                icon.setImageResource(R.drawable.report_menu);
                                text.setText("Menu Key");
                                break;
                            case 63:
                                icon.setImageResource(R.drawable.report_screenlock);
                                text.setText("Screen Lock");
                                break;
                            case 64:
                                icon.setImageResource(R.drawable.report_lednotificationtest);
                                text.setText("LED Notification");
                                break;
                            case 65:
                                icon.setImageResource(R.drawable.report_uvsencor);
                                text.setText("UV Sensor");
                                break;
                            case 66:
                                icon.setImageResource(R.drawable.report_screen);
                                text.setText("Stylus Hovering");
                                break;
                        }
                    } else {
                        switch (groupPosition) {
                            case 0:// Condition to handle header content for MandatoryTest
                                break;
                            case 1:
                                icon.setImageResource(R.drawable.report_networksim);
                                text.setText("Network Signal");
                                break;
                            case 2:
                                icon.setImageResource(R.drawable.report_deviceablecall);
                                text.setText("Call");
                                break;
                            case 3:
                                icon.setImageResource(R.drawable.report_sms);
                                text.setText("SMS");
                                break;
                            case 4:
                                mWifGroupIndex = groupPosition;
                                icon.setImageResource(R.drawable.report_wifi);
                                text.setText("WiFi");
                                break;
                            case 5:
                                icon.setImageResource(R.drawable.report_internet);
                                text.setText("Internet");
                                break;
                            case 6:
                                icon.setImageResource(R.drawable.report_imei);
                                text.setText("IMEI");
                                break;
                            case 7:
                                icon.setImageResource(R.drawable.report_devicerooted);
                                text.setText("Device Non Rooted");
                                break;
                            case 8:
                                icon.setImageResource(R.drawable.report_simcard);
                                text.setText("SIM");
                                break;
                            case 9:
                                icon.setImageResource(R.drawable.report_camera);
                                text.setText("Back Camera");
                                break;
                            case 10:
                                icon.setImageResource(R.drawable.report_frontcamera);
                                text.setText("Front Camera");
                                break;
                            case 11:
                                icon.setImageResource(R.drawable.report_ledflash);
                                text.setText("Flash Light");
                                break;
                            case 12:
                                icon.setImageResource(R.drawable.report_torch);
                                text.setText("Torch Light");
                                break;
                            case 13:
                                icon.setImageResource(R.drawable.report_battery);
                                text.setText("Battery");
                                break;
                            case 14:
                                icon.setImageResource(R.drawable.report_vibrate);
                                text.setText("Vibrate");
                                break;
                            case 15:
                                icon.setImageResource(R.drawable.report_ram);
                                text.setText("RAM");
                                break;
                            case 16:
                                icon.setImageResource(R.drawable.report_storageinternal);
                                text.setText("Internal Storage");
                                break;
                            case 17:
                                icon.setImageResource(R.drawable.report_storageexternal);
                                text.setText("External Storage");
                                break;
                            case 18:
                                icon.setImageResource(R.drawable.report_light);
                                text.setText("Light Sensor");
                                break;

                            case 19:
                                icon.setImageResource(R.drawable.report_screen);
                                text.setText("Display Touch Panel");
                                break;
                            case 20:
                                icon.setImageResource(R.drawable.brightness);
                                text.setText("Display Brightness");
                                break;
                            case 21:
                                icon.setImageResource(R.drawable.deadpixels);
                                text.setText("Display Dead Pixel");
                                break;
                            case 22:
                                icon.setImageResource(R.drawable.report_bluetooth);
                                text.setText("Bluetooth");
                                break;
                            case 23:
                                icon.setImageResource(R.drawable.report_headphonejack);
                                text.setText("Earphone Jack");
                                break;
                            case 24:
                                icon.setImageResource(R.drawable.report_powerbutton);
                                text.setText("Power Button");
                                break;
                            case 25:
                                icon.setImageResource(R.drawable.report_volumebutton);
                                text.setText("Volume Up Button");
                                break;
                            case 26:
                                icon.setImageResource(R.drawable.report_volumebutton);
                                text.setText("Volume Down Button");
                                break;
                            case 27:
                                icon.setImageResource(R.drawable.report_homebutton);
                                text.setText("Home Key");
                                break;
                            case 28:
                                icon.setImageResource(R.drawable.report_backbutton);
                                text.setText("Back Key");
                                break;
                            case 29:
                                icon.setImageResource(R.drawable.report_microphone);
                                text.setText("Microphone");
                                break;
                            case 30:
                                icon.setImageResource(R.drawable.report_loudspeakerclear);
                                text.setText("Loudspeaker");
                                break;
                            case 31:
                                icon.setImageResource(R.drawable.report_earphone);
                                text.setText("Earphone");
                                break;
                            case 32:
                                icon.setImageResource(R.drawable.report_loudspeakerclear);
                                text.setText("Receiver");
                                break;
                            case 33:
                                icon.setImageResource(R.drawable.report_cameraautofocus);
                                text.setText("Camera Autofocus");
                                break;
                            case 34:
                                icon.setImageResource(R.drawable.report_accelerometersensor);
                                text.setText("Accelerometer");
                                break;
                            case 35:
                                icon.setImageResource(R.drawable.report_gyroscopesensor);
                                text.setText("Gyroscope");
                                break;
                            case 36:
                                icon.setImageResource(R.drawable.report_proxomity);
                                text.setText("Proximity");
                                break;
                            case 37:
                                icon.setImageResource(R.drawable.report_videorecording);
                                text.setText("Back Video Recording");
                                break;
                            case 38:
                                icon.setImageResource(R.drawable.report_videorecording);
                                text.setText("Front Video Recording");
                                break;
                            case 39:
                                icon.setImageResource(R.drawable.report_usb);
                                text.setText("USB");
                                break;

                            case 40:// Condition to handle header content for OptionalTest
                                break;
                            case 41:
                                icon.setImageResource(R.drawable.report_airpressure);
                                text.setText("Air Pressure");
                                break;
                            case 42:
                                icon.setImageResource(R.drawable.report_airtempretute);
                                text.setText("Air Temperature");
                                break;
                            case 43:
                                icon.setImageResource(R.drawable.report_gravity);
                                text.setText("Gravity");
                                break;
                            case 44:
                                icon.setImageResource(R.drawable.report_infrared);
                                text.setText("Infrared");
                                break;
                            case 45:
                                icon.setImageResource(R.drawable.report_nfc);
                                text.setText("NFC");
                                break;
                            case 46:
                                icon.setImageResource(R.drawable.report_gyroscopeforgaming);
                                text.setText("Gyroscope Gaming");
                                break;
                            case 47:
                                icon.setImageResource(R.drawable.report_oriantation);
                                text.setText("Orientation");
                                break;
                            case 48:
                                icon.setImageResource(R.drawable.report_devicetemprature);
                                text.setText("Device Temperature");
                                break;
                            case 49:
                                icon.setImageResource(R.drawable.report_stepdetector);
                                text.setText("Step Detector");
                                break;
                            case 50:
                                icon.setImageResource(R.drawable.report_stepcounter);
                                text.setText("Step Counter");
                                break;
                            case 51:
                                icon.setImageResource(R.drawable.report_motiondetactor);
                                text.setText("MotionDetector");
                                break;
                            case 52:
                                icon.setImageResource(R.drawable.report_humidity);
                                text.setText("Humidity");
                                break;
                            case 53:
                                icon.setImageResource(R.drawable.report_fingreprintsensor);
                                text.setText("Biometric");
                                break;
                            case 54:
                                icon.setImageResource(R.drawable.report_heartratemonitor);
                                text.setText("HRM");
                                break;
                            case 55:
                                icon.setImageResource(R.drawable.report_megnaticsensor);
                                text.setText("Magnetic Sensor");
                                break;
                            case 56:
                                icon.setImageResource(R.drawable.report_gps);
                                text.setText("GPS");
                                break;

                            case 57:
                                icon.setImageResource(R.drawable.report_spo2);
                                text.setText("SPO2");
                                break;
                            case 58:
                                icon.setImageResource(R.drawable.report_menu);
                                text.setText("Menu Key");
                                break;
                            case 59:
                                icon.setImageResource(R.drawable.report_screenlock);
                                text.setText("Screen Lock");
                                break;
                            case 60:
                                icon.setImageResource(R.drawable.report_lednotificationtest);
                                text.setText("LED Notification");
                                break;
                            case 61:
                                icon.setImageResource(R.drawable.report_uvsencor);
                                text.setText("UV Sensor");
                                break;
                            case 62:
                                icon.setImageResource(R.drawable.report_stylus);
                                text.setText("Stylus Hovering");
                                break;
                        }
                    }
                    Object s = values.get(groupPosition);
                    if (s.equals("1"))
                        info.setImageResource(R.drawable.button_right);
                    else if (s.equals("0"))
                        info.setImageResource(R.drawable.button_cross);
                    else if (s.equals("-1"))
                        info.setImageResource(R.drawable.button_na);
                    else if (s.equals("-2"))
                        info.setImageResource(R.drawable.button_skip_triangle);

                    if (getChildrenCount(groupPosition) > 0) {
                        if (isExpanded) {
                            expand.setImageResource(R.drawable.arrow_up);
                        } else {
                            expand.setImageResource(R.drawable.arrow_down);
                        }
                    } else {
                        expand.setEnabled(false);
                        expand.setImageResource(R.drawable.button_light);
                    }

                    break;
                case TYPE_SEPARATOR:
                    rowView = inflater.inflate(R.layout.listtitle, parent, false);
                    text = (TextView) rowView.findViewById(R.id.textSeparator);
                    text.setText(mData.get(groupPosition));
                    break;
            }
            return rowView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.subrowlayout, parent, false);

            try {
                JSONObject root = listData.get(groupPosition);
                if (root != null) {
                    JSONObject data = root.getJSONObject("data");
                    if (data != null) {
                        String orgKey = (String) data.names().get(childPosition);
                        String key = orgKey;
                        if (groupPosition == mWifGroupIndex) {
                            if (key.length() > 16)
                                key = key.substring(0, 15) + "...";
                        }
                        TextView keyId = (TextView) rowView.findViewById(R.id.text);
                        keyId.setText(key);

                        String value = "" + data.get(orgKey);

                        TextView valueId = (TextView) rowView.findViewById(R.id.subtext);
                        valueId.setText(value);
                    }
                }
            } catch (Exception e) {
            }

            return rowView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    private ProgressDialog pDialog;

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Downloading Certificate." + "\n" + " Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "/qutrust/");
            folder.mkdir();
            filePath = extStorageDirectory + "/qutrust/" + fileName;
            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            downloadFile(fileUrl, pdfFile);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            final AlertDialog.Builder alrtdlg;
            alrtdlg = new AlertDialog.Builder(MainActivity.this);
            alrtdlg.setTitle("Certificate Downloaded.");
            alrtdlg.setMessage("File downloaded at " + filePath);
            alrtdlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alrtdlg.show();
        }

    }

    private static final int MEGABYTE = 1024 * 1024;

    public static void downloadFile(String fileUrl, File directory) {
        try {

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean CheckInternet() {
        // Check Internet connection
        ConnectivityManager connec = (ConnectivityManager) MainActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (connec != null) {
            NetworkInfo info = connec.getNetworkInfo(0);
            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED
                        || connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
                        || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING
                        || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                } else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
                        || connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
                    return false;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    public int getPhoneCount() {
        int phoneCount = 1;
        switch (getMultiSimConfiguration()) {
            case 3:
//                Toast.makeText(getApplicationContext(),"getPhoneCount 1",Toast.LENGTH_LONG).show();
                phoneCount = 1;
                break;
            case 0:
            case 1:
//                Toast.makeText(getApplicationContext(),"getPhoneCount 2",Toast.LENGTH_LONG).show();
                phoneCount = 2;
                break;
            case 2:
//                Toast.makeText(getApplicationContext(),"getPhoneCount 3",Toast.LENGTH_LONG).show();
                phoneCount = 3;
                break;
        }
        return phoneCount;
    }

    public int getMultiSimConfiguration() {
//        Toast.makeText(getApplicationContext(),"Value is inside getMultiSimConfiguration()",Toast.LENGTH_LONG).show();
        String mSimConfig =
                SystemProperties.get(PROPERTY_MULTI_SIM_CONFIG);
//        Toast.makeText(getApplicationContext(),mSimConfig.toString(),Toast.LENGTH_LONG).show();
        if (mSimConfig.equals("dsds")) {
//           Toast.makeText(getApplicationContext(),"Value is Zero",Toast.LENGTH_LONG).show();
            return 0;
        } else if (mSimConfig.equals("dsda")) {
//            Toast.makeText(getApplicationContext(),"Value is One ",Toast.LENGTH_LONG).show();
            return 1;
        } else if (mSimConfig.equals("tsts")) {
//            Toast.makeText(getApplicationContext(),"Value is Two",Toast.LENGTH_LONG).show();
            return 2;
        } else {
            return 3;
        }
    }

    public boolean getPhoneSubType(Context ctx) {
        boolean subType = false;
        try {
            ArrayList<String> subArr = new ArrayList<>();
            ArrayList<String> smsArr = new ArrayList<>();
            try {
                Process p = Runtime.getRuntime().exec("service list");
                p.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.contains("internal.telephony.ISub"))
                        subArr.add(line.substring(line.indexOf("\t") + 1, line.indexOf(":")));
                    else if (line.contains("internal.telephony.ISms") || line.contains("internal.telephony.msim.ISmsMSim"))
                        smsArr.add(line.substring(line.indexOf("\t") + 1, line.indexOf(":")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (subArr.size() == 1 && smsArr.size() == 1) {
                // may be android 22 version
                Object isubObj = null;
                {
                    Method method = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class});
                    method.setAccessible(true);
                    Object param = method.invoke(null, new Object[]{"isub"});

                    method = Class.forName("com.android.internal.telephony.ISub$Stub").getDeclaredMethod("asInterface", new Class[]{IBinder.class});
                    method.setAccessible(true);
                    isubObj = method.invoke(null, new Object[]{param});
                }

                List list = null;
                Method method = null;
                try {
                    method = isubObj.getClass().getMethod("getAllSubInfoList");
                    list = (List) method.invoke(isubObj);
                } catch (Exception e) {
                    method = isubObj.getClass().getMethod("getAllSubInfoList", String.class);
                    list = (List) method.invoke(isubObj, ctx.getPackageName());
                }


                boolean zeroSim = false, oneSim = false;
                for (int i = 0; i < list.size(); i++) {
                    Object ob = list.get(i);
                    int slotId = -1;



                    try {
                        slotId = (int) ob.getClass().getMethod("getSimSlotIndex").invoke(ob);
                    } catch (Exception e) {
                        try {
                            slotId = (int) ob.getClass().getField("mSlotId").get(ob);
                        } catch (Exception e1) {
                            slotId = (int) ob.getClass().getField("slotId").get(ob);
                        }
                    }
                    if (0 == slotId) {
                        zeroSim = true;
                    } else if (1 == slotId) {
                        oneSim = true;
                    }
                }
                if (zeroSim && oneSim) {
                    subType = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subType;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // progressBar.d
    }
    public void SendReportOnline()
    {
        new AsyncTask<String, String, String>() {
            ProgressDialog progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressBar = ProgressDialog.show(MainActivity.this, "Uploading test report...", "Please wait.");
                Log.info("Json Value for Server1 " + resultObject.toString());
            }

            @Override
            protected String doInBackground(String... params) {

                String sendjObjstring = resultObject.toString();
                Log.info("Json Value for Server " + sendjObjstring);
                OutputStream os = null;
                InputStream is = null;
                HttpURLConnection conn = null;
                try {
                    result = PostRequest(Config.certificationqutrust,sendjObjstring);
                    util.logger.Log.info("MainActivity otpValidation api response1 : " + sendjObjstring + " , code : " + statusCode);

                    if (statusCode != 200) {
                        util.logger.Log.info("MainActivity otpValidation api response : " + result + " , code : " + statusCode);

                        //resultVal = "error";
                        // UiHandle();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.info("Results are1: " + result);
                progressBar.dismiss();



                Log.info("Results are: " + result);

                JSONObject resultobj = null;
                try {
                    resultobj = new JSONObject(result);
                    resultstr = resultobj.getString("result");
                    pdfurlstr = resultobj.getString("url");
                    certinumberstr = resultobj.getString("certificatecode");
                    strMessage = resultobj.getString("message");




                CommSharedPreff.saveCertificateData(MainActivity.this, resultstr, pdfurlstr, certinumberstr, strMessage, errorcodestr, errormsgstr);
                if(result.equalsIgnoreCase(""))
                {

                    final AlertDialog.Builder alrtdlg;
                    alrtdlg = new AlertDialog.Builder(MainActivity.this);
                    alrtdlg.setTitle("Error");
                    alrtdlg.setCancelable(false);
                    alrtdlg .setIcon(android.R.drawable.ic_dialog_alert);
                    alrtdlg.setMessage("Unable to upload report\n\nPlease check internet connection and click retry.");
                    alrtdlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            qutrustRelativeLayout.setVisibility(View.VISIBLE);
                            imgButtonViewCertificate.setVisibility(View.VISIBLE);
                            certificatenumbers.setVisibility(View.GONE);
                            certificatenumbertitle1.setVisibility(View.GONE);
                        }
                    });

                    alrtdlg.show();

                }


                else if(resultstr.equalsIgnoreCase("true")) {

                    Toast.makeText(getApplicationContext(),"Report uploaded successfully",Toast.LENGTH_LONG).show();
                    invalidateOptionsMenu();
                    qutrustRelativeLayout.setVisibility(View.VISIBLE);
                    imgButtonViewCertificate.setVisibility(View.GONE);
                    certificatenumbers.setVisibility(View.VISIBLE);
                    certificatenumbertitle1.setVisibility(View.VISIBLE);
                    imgButtonViewCertificate.setVisibility(View.GONE);
                    certificatenumbers.setText(certinumberstr);

                    final AlertDialog.Builder alrtdlg;
                    alrtdlg = new AlertDialog.Builder(MainActivity.this);
                    alrtdlg.setTitle("Report Status!");
                    alrtdlg.setMessage("Press Ok to continue");
                    alrtdlg.setCancelable(false);
                    alrtdlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.out.println("Failed data="+resultmapfailed);
                           Intent intent=new Intent(getApplicationContext(), DiagnoseActivity.class);
                            Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
                            Gson gson = new Gson();

                            intent.putExtra("diagnose_report",gson.toJson(resultmapfailed,type));
                            intent.putExtra("faileddata", resultmapfailed);
                            startActivity(intent);
                            finish();
                        }
                    });

                    alrtdlg.show();
                }
                else if(resultstr.equalsIgnoreCase("false"))
                {

                    final AlertDialog.Builder alrtdlg;
                    alrtdlg = new AlertDialog.Builder(MainActivity.this);
                    alrtdlg.setTitle("Error");
                    alrtdlg.setMessage("Unable to upload report\n\n Please check internet connection and click retry");
                    alrtdlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            qutrustRelativeLayout.setVisibility(View.VISIBLE);
                            imgButtonViewCertificate.setVisibility(View.VISIBLE);
                            certificatenumbers.setVisibility(View.GONE);
                            certificatenumbertitle1.setVisibility(View.GONE);
                        }
                    });

                    alrtdlg.show();

                }
                } catch (JSONException e) {
                    final AlertDialog.Builder alrtdlg;
                    alrtdlg = new AlertDialog.Builder(MainActivity.this);
                    alrtdlg.setTitle("Error");
                    alrtdlg.setMessage("Unable to upload report\n\n Please check internet connection and click retry");
                    alrtdlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            qutrustRelativeLayout.setVisibility(View.VISIBLE);
                            imgButtonViewCertificate.setVisibility(View.VISIBLE);
                            certificatenumbers.setVisibility(View.GONE);
                            certificatenumbertitle1.setVisibility(View.GONE);
                        }
                    });

                    alrtdlg.show();
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    final AlertDialog.Builder alrtdlg;
                    alrtdlg = new AlertDialog.Builder(MainActivity.this);
                    alrtdlg.setTitle("Error");
                    alrtdlg.setMessage("Unable to upload report\n\n Please check internet connection and click retry");
                    alrtdlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            qutrustRelativeLayout.setVisibility(View.VISIBLE);
                            imgButtonViewCertificate.setVisibility(View.VISIBLE);
                            certificatenumbers.setVisibility(View.GONE);
                            certificatenumbertitle1.setVisibility(View.GONE);
                        }
                    });

                    alrtdlg.show();
                    e.printStackTrace();
                }

            }
        }.execute();
    }

    public void SendReport() {
        new AsyncTask<String, String, String>() {
            ProgressDialog progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressBar = ProgressDialog.show(MainActivity.this, "Uploading test report...", "Please wait.");
                Log.info("Json Value for Server1 " + resultObject.toString());
            }

            @Override
            protected String doInBackground(String... params) {

                String sendjObjstring = resultObject.toString();
                doOfflineRequest(sendjObjstring);

                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.info("Results are1: " + result);
                // progressBar.dismiss();


                try {
                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // pDialog.dismiss();
                           /* String androidId = Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);*/
                            String androidId = Build.SERIAL;
                            androidId += "_response_validation";
                            String result = collectOfflineResponse(androidId);
                            progressBar.dismiss();
                            proceedRequests(result, androidId);

                        }
                    }, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        }.execute();
    }

    private void turnGPSOn() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    public void doOfflineRequest(String post) {

        String utmClientId = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyUtmClientId, MainActivity.this);
        if (utmClientId != null && utmClientId.length() > 0) {
            post += "&clientid=" + utmClientId;
        }
        try {
            File file = new File("/data/local/tmp/", "report_request.txt");
            FileOutputStream os = new FileOutputStream(file);
            os.write(post.getBytes());
            os.close();
        } catch (IOException e) {
            Log.info("Vikashinside Io exception0=" + e);
            e.printStackTrace();
        }
    }

    public String collectOfflineResponse(String fname) {


        String fpath = "/data/local/tmp/" + fname + ".txt";
        StringBuilder text = new StringBuilder();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(fpath));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            // do exception handling
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
        }
        Log.info("Vikash inside value=" + text);

        return String.valueOf(text);
    }

    public void fileReEntry(String fname) {
        try {
            File file = new File("/data/local/tmp", fname);
            FileOutputStream os = new FileOutputStream(file);
            String data = "";
            os.write(data.getBytes());
            os.close();
        } catch (IOException e) {
            Log.info("Vikashinside Io exception0=" + e);
            e.printStackTrace();
        }
    }

    public void proceedRequests(String result, String androidid) {


        if (result.equalsIgnoreCase("")) {
            String androidIds = Build.SERIAL;
            Toast.makeText(getApplicationContext(), "Unable to upload report,Please check device connectivity and click retry", Toast.LENGTH_LONG).show();
            fileReEntry(androidIds + "_response_validation.txt");
            fileReEntry("report_request.txt");
            qutrustRelativeLayout.setVisibility(View.VISIBLE);
            imgButtonViewCertificate.setVisibility(View.VISIBLE);
            certificatenumbers.setVisibility(View.GONE);
            // collectOfflineResponse(androidid);
        } else {
            proceedRequest(result);
        }

    }

    public void proceedRequest(String result) {
        String androidIds = Build.SERIAL;
        JSONObject jsonObject;
        String results = null;
        String messagse = null;




        Log.info("Results are: " + result);

        JSONObject resultobj = null;
        try {
            resultobj = new JSONObject(result);
            resultstr = resultobj.getString("result");
            pdfurlstr = resultobj.getString("url");
            certinumberstr = resultobj.getString("certificatecode");
            strMessage = resultobj.getString("message");
            error = resultobj.getString("error");
            issynced = resultobj.getString("issynced");




            CommSharedPreff.saveCertificateData(MainActivity.this, resultstr, pdfurlstr, certinumberstr, strMessage, errorcodestr, errormsgstr);
            if (result.equalsIgnoreCase("")) {

                Toast.makeText(getApplicationContext(), "Unable to upload report,Please check device connectivity and click retry", Toast.LENGTH_LONG).show();
                fileReEntry(androidIds + "_response_validation.txt");
                fileReEntry("report_request.txt");
                qutrustRelativeLayout.setVisibility(View.VISIBLE);
                imgButtonViewCertificate.setVisibility(View.VISIBLE);
                certificatenumbers.setVisibility(View.GONE);
                certificatenumbertitle1.setVisibility(View.GONE);


            } else if (resultstr.equalsIgnoreCase("true") && issynced.equalsIgnoreCase("true")) {
                fileReEntry(androidIds + "_response_validation.txt");
                fileReEntry("report_request.txt");
                Toast.makeText(getApplicationContext(), "Report synced and uploaded successfully", Toast.LENGTH_LONG).show();
                invalidateOptionsMenu();
                qutrustRelativeLayout.setVisibility(View.VISIBLE);
                imgButtonViewCertificate.setVisibility(View.GONE);
                certificatenumbers.setVisibility(View.VISIBLE);
                certificatenumbertitle1.setText("Your certificate ID");
                imgButtonViewCertificate.setVisibility(View.GONE);
                certificatenumbers.setText(certinumberstr);
            } else if (resultstr.equalsIgnoreCase("true") && issynced.equalsIgnoreCase("false")) {
                fileReEntry(androidIds + "_response_validation.txt");
                fileReEntry("report_request.txt");
                Toast.makeText(getApplicationContext(), "Report sent to desktop. Use Upload to CRM to complete sync.", Toast.LENGTH_LONG).show();
                invalidateOptionsMenu();
                qutrustRelativeLayout.setVisibility(View.VISIBLE);
                imgButtonViewCertificate.setVisibility(View.GONE);
                certificatenumbers.setVisibility(View.VISIBLE);
                certificatenumbertitle1.setVisibility(View.VISIBLE);
                imgButtonViewCertificate.setVisibility(View.GONE);
                certificatenumbers.setText("SUCCESS");
            } else if (resultstr.equalsIgnoreCase("false")) {
                Toast.makeText(getApplicationContext(), "Unable to sync report. Please check USB connection.", Toast.LENGTH_LONG).show();
                fileReEntry(androidIds + "_response_validation.txt");
                fileReEntry("report_request.txt");
                qutrustRelativeLayout.setVisibility(View.VISIBLE);
                imgButtonViewCertificate.setVisibility(View.VISIBLE);
                certificatenumbers.setVisibility(View.GONE);
                certificatenumbertitle1.setVisibility(View.GONE);
                certificatenumbertitle1.setText("Report failed to sync");


            } else {
                Toast.makeText(MainActivity.this, "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
                fileReEntry(androidIds + "_response_validation.txt");
                fileReEntry("report_request.txt");
                qutrustRelativeLayout.setVisibility(View.VISIBLE);
                imgButtonViewCertificate.setVisibility(View.VISIBLE);
                certificatenumbers.setVisibility(View.GONE);
                certificatenumbertitle1.setVisibility(View.GONE);
            }
        }
        catch (JSONException e) {

            Toast.makeText(MainActivity.this, "Unexpected error reading upload response. Please try again.", Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
            fileReEntry(androidIds + "_response_validation.txt");
            fileReEntry("report_request.txt");
            qutrustRelativeLayout.setVisibility(View.VISIBLE);
            imgButtonViewCertificate.setVisibility(View.VISIBLE);
            certificatenumbers.setVisibility(View.GONE);
            certificatenumbertitle1.setVisibility(View.GONE);
            e.printStackTrace();
        }
        catch(Exception e)
        {
            Toast.makeText(MainActivity.this, "Unexpected error reading upload response. Please try again.", Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
            fileReEntry(androidIds + "_response_validation.txt");
            fileReEntry("report_request.txt");
            qutrustRelativeLayout.setVisibility(View.VISIBLE);
            imgButtonViewCertificate.setVisibility(View.VISIBLE);
            certificatenumbers.setVisibility(View.GONE);
            certificatenumbertitle1.setVisibility(View.GONE);
            e.printStackTrace();
        }


    }
}