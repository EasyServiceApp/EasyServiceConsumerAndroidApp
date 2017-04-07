package com.sdk.sampleapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.mlapps.truevaluesdk.CommSharedPreff;
import com.mlapps.truevaluesdk.CommSharedPreff;
import com.mlapps.truevaluesdk.CustomPhoneStateListener;
import com.mlapps.truevaluesdk.TestBase;
import com.service.easyservice.R;


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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import util.logger.AndroidLogAppender;
import util.logger.AndroidUtils;
import util.logger.Appender;
import util.logger.BuildInfo;
import util.logger.FileAppender;
import util.logger.Log;
import util.logger.MultipleAppender;

@SuppressWarnings("deprecation")
public class LaunchActivity extends Activity  {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private PendingIntent mPermissionIntent;
    protected static final int STD_USB_REQUEST_GET_DESCRIPTOR = 0x06;
    // http://libusb.sourceforge.net/api-1.0/group__desc.html
    protected static final int LIBUSB_DT_STRING = 0x03;
    public static String idfaVal = null;
    static Context mContext;
    TelephonyManager tManager;
    public static boolean phoneDualType;
    public static boolean simIdBasedDual;
    public static int phoneTypeBasedDual;
    //  TestBase testBase;

    String gshsh = getInfo();

    Thread splashTread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
       // setMobileDataState(false);

      // getDeviceId();


        CommSharedPreff.saveStringPreferencesusb("USBValue","",getApplicationContext());

        boolean isconnection= isConnected(getApplicationContext());


        TestActivity tst=new TestActivity();
        if(isconnection) {
            CommSharedPreff.saveStringPreferencesusb("USBValue","true",getApplicationContext());
           // Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
            //tst.usbTestInfo = 1;
        }
        else
        {
            CommSharedPreff.saveStringPreferencesusb("USBValue","false",getApplicationContext());
            //Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
            //tst.usbTestInfo = 0;
        }
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                // only for gingerbread and newer versions
                String permissionRcontact = Manifest.permission.READ_CONTACTS;
                String permissionBody = Manifest.permission.BODY_SENSORS;
                String permissionAudio = Manifest.permission.RECORD_AUDIO;
                // String permissionDeviceAdmin = Manifest.permission.BIND_DEVICE_ADMIN;
                // String permissionWriteSetting = Manifest.permission.WRITE_SETTINGS;
                String permissionRead = Manifest.permission.READ_PHONE_STATE;
                // String permissionWrite = Manifest.permission.WRITE_CONTACTS;
                String permissionSendSms = Manifest.permission.SEND_SMS;
                String permissionAccessCoarse = Manifest.permission.ACCESS_COARSE_LOCATION;
                String permissionAccessFine = Manifest.permission.ACCESS_FINE_LOCATION;

                if (ContextCompat.checkSelfPermission(getApplicationContext(), permissionRcontact) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionBody) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionAudio) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionRead) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(),
                        permissionSendSms) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionAccessCoarse) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionAccessFine) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LaunchActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.BODY_SENSORS, Manifest.permission.READ_CONTACTS}, 1);
                } else {
                    if(CheckInternet()) {
                        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                        tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                                PhoneStateListener.LISTEN_SERVICE_STATE);

                        tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

                        tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                                PhoneStateListener.LISTEN_CELL_INFO);

                        tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                                PhoneStateListener.LISTEN_SIGNAL_STRENGTH);
                        new OTPCheckTaskOnline().execute();
                    }
                    else {
                        Toast.makeText(LaunchActivity.this, "Unable to proceed,Please Check Internet Connectivity.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
            else {
                if(CheckInternet()) {
                    tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                            PhoneStateListener.LISTEN_SERVICE_STATE);

                    tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                            PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

                    tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                            PhoneStateListener.LISTEN_CELL_INFO);

                    tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                            PhoneStateListener.LISTEN_SIGNAL_STRENGTH);
                    new OTPCheckTaskOnline().execute();
                }
                else {
                    Toast.makeText(LaunchActivity.this, "Unable to proceed,Please Check Internet Connectivity.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


      //  sendValueToServer();

       // finish();
       // return;




       /* try {
            //com.mediatek.telephony.TelephonyManagerEx obj = (com.mediatek.telephony.TelephonyManagerEx)Class.forName(className);
            TelephonyManagerEx mgr = (TelephonyManagerEx) getSystemService(Context.TELEPHONY_SERVICE);
            System.out.println("Vikash Check here1");
        } catch (Exception e) {
            System.out.println("Vikash Check here2");
            e.printStackTrace();
        }
        System.exit(0);*/

        /*try {
            if (Build.VERSION.SDK_INT >= 23) {
                // only for gingerbread and newer versions
                String permissionRcontact = Manifest.permission.READ_CONTACTS;
                String permissionBody = Manifest.permission.BODY_SENSORS;
                String permissionAudio = Manifest.permission.RECORD_AUDIO;
                // String permissionDeviceAdmin = Manifest.permission.BIND_DEVICE_ADMIN;
                // String permissionWriteSetting = Manifest.permission.WRITE_SETTINGS;
                String permissionRead = Manifest.permission.READ_PHONE_STATE;
                // String permissionWrite = Manifest.permission.WRITE_CONTACTS;
                String permissionSendSms = Manifest.permission.SEND_SMS;
                String permissionAccessCoarse = Manifest.permission.ACCESS_COARSE_LOCATION;
                String permissionAccessFine = Manifest.permission.ACCESS_FINE_LOCATION;

                if (ContextCompat.checkSelfPermission(getApplicationContext(), permissionRcontact) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionBody) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionAudio) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionRead) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(),
                        permissionSendSms) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionAccessCoarse) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), permissionAccessFine) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LaunchActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.BODY_SENSORS, Manifest.permission.READ_CONTACTS}, 1);
                } else {


                    checkForCrashes();//Check for hockey crash

                    //new SendLog(getApplicationContext()).execute();//function for send log

                    // checkForCrashes();
                    mContext = getApplicationContext();
                    intializeLogger(getApplicationContext());

                    Log.info("SplashActivity opened");

                    android.util.Log.i("SplashActivity", "Testing isOfflineBuild: " + Config.isOfflineBuild);
                    // launchMainScreen();
                    if (Config.isOfflineBuild == false) {
//                        StartAnimations();
                        launchMainScreen();
                    } else
                        doLicenseCheck();//License Check

                    boolean network = CheckInternet();
                    if (network)
                        new validateImei(LaunchActivity.this).execute();
                }
            } else {
                Log.info("Permission-1");

                tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                        PhoneStateListener.LISTEN_SERVICE_STATE);//Listen for changes to the network service state

                tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);//Listen for changes to the network signal strengths

                tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                        PhoneStateListener.LISTEN_CELL_INFO);

                tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                        PhoneStateListener.LISTEN_SIGNAL_STRENGTH);


                checkForCrashes();//Crash check

                // new SendLog(getApplicationContext()).execute();//Log send APi

                // checkForCrashes();
                mContext = getApplicationContext();
                intializeLogger(getApplicationContext());

                Log.info("SplashActivity opened");

                android.util.Log.i("SplashActivity", "Testing isOfflineBuild: " + Config.isOfflineBuild);
                // launchMainScreen();
                if (Config.isOfflineBuild == false) {
//                    StartAnimations();
                    launchMainScreen();
                } else
                    doLicenseCheck();

                //  boolean network = CheckInternet();
                // if (network)
                //new validateImei(SplashActivity.this).execute();

            }
        } catch (Exception ex) {
            //System.out.println("Exception ...." + e.toString());
            Log.info("Exceptions" + ex.toString());
        }*/
    }

    public static boolean isConnected(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
        return intent.getExtras().getBoolean("connected");
    }

    private void checkForCrashes() {
        // System.out.println("hockey-1");
       /* CrashManager.register(SplashActivity.this,
                CommonConfig.HockeyKey);*/
    }

    private void doLicenseCheck() {

        /*
        * This listeners is for listen servise state changed and cell info changes.
        * **/
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                PhoneStateListener.LISTEN_SERVICE_STATE);

        tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

        tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                PhoneStateListener.LISTEN_CELL_INFO);

        tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                PhoneStateListener.LISTEN_SIGNAL_STRENGTH);

        System.out.println("Do license check-1");
        int validationVal = CommSharedPreff.loadIntSavedPreferences(
                CommSharedPreff.spKeyAuthorizedVal, mContext);
        if (validationVal == -1) {
            getIdfaVal(getApplicationContext());
        } else if (validationVal == 1) {
            if (CheckInternet()) {
            launchMainScreen();
            } else {
                showErrorInfo(LaunchActivity.this, "Internet connection not available\n\nPlease turn on internet and try again");
            }
            // new SendLog(getApplicationContext()).execute();
        } else if (validationVal == 0) {
            String errStr = CommSharedPreff.loadStringSavedPreferences(
                    CommSharedPreff.spKeyAuthorizedStr, mContext);
            showErrorInfo(LaunchActivity.this, errStr);
        }
    }

    private void showErrorInfo(Context ctx, String msg) {
        new AlertDialog.Builder(ctx).setTitle("Error").setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LaunchActivity.this.finish();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
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
                        idfaVal = Secure.getString(ctx.getContentResolver(),
                                Secure.ANDROID_ID);
                    }
                    return null;
                }

                protected void onPostExecute(Void result) {

                    checkNetworkAndValidate();
                }
            }.execute();
        } else {
            checkNetworkAndValidate();
        }
        return idfaVal;
    }

    private void checkNetworkAndValidate() {
        boolean network = CheckInternet();
          if (network) {
       // new CheckValidLicense().execute();
        launchMainScreen();
          }
        else {
            showErrorInfo(LaunchActivity.this,
                    "Internet not working\n\nPlease turn on internet and try again.");
        }
    }

    public boolean CheckInternet() {
        // Check Internet connection
        ConnectivityManager connec = (ConnectivityManager) LaunchActivity.this
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

    class CheckValidLicense extends AsyncTask<Void, Void, Void> {

        boolean isAllowed = false;
        String errMsg = "";

        @Override
        protected Void doInBackground(Void... params) {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imeiVal = telephonyManager.getDeviceId();
            String androidId = Secure.getString(getContentResolver(),
                    Secure.ANDROID_ID);

            String URL = Config.baseUrl + Config.deviceCertificationRegisterURL;
            String postData = "imei=" + imeiVal + "&deviceid=" + androidId
                    + "&idfa=" + idfaVal + "&partnerid=" + Config.partnerId;

            String serverResponse = PostRequest(URL, postData);

            Log.info("License", "Server Req : " + URL + " , PostData : "
                    + postData + " , Server Response : " + serverResponse);
            try {
                JSONObject root = new JSONObject(serverResponse);
                String result = root.getString("result");
                if (result != null && result.length() > 0) {
                    if (result.equalsIgnoreCase("true")) {
                        isAllowed = true;
                        CommSharedPreff
                                .saveIntPreferences(
                                        CommSharedPreff.spKeyAuthorizedVal, 1,
                                        mContext);
                    } else {
                        isAllowed = false;
                        CommSharedPreff.saveIntPreferences(
                                CommSharedPreff.spKeyAuthorizedVal, 0,
                                mContext);
                    }

                    getDeviceSuperInfo();
                }

                errMsg = root.getString("message");
                CommSharedPreff.saveStringPreferences(
                        CommSharedPreff.spKeyAuthorizedStr, errMsg, mContext);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            if (isAllowed) {
                launchMainScreen();
            } else {
                Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
            }

            // new SendLog(getApplicationContext()).execute();
        }

        private String PostRequest(String uRL, String dataSendToTheServer) {
            BufferedReader in = null;
            StringBuffer sb = new StringBuffer("");
            try {
                /** Getting Http Client to hit the authentication URI. */

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
                se.setContentType("application/x-www-form-urlencoded");
                request.setEntity(se);
                request.getParams().setBooleanParameter(
                        "http.protocol.expect-continue", false);
                /** Getting Response from server. */
                HttpResponse response = client.execute(request);
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

        private void getDeviceSuperInfo() {
            String s = "";
            try {
                s += "OS Version: " + System.getProperty("os.version") + "("
                        + Build.VERSION.INCREMENTAL + ")";
                s += "\n OS API Level: " + Build.VERSION.SDK_INT;
                s += "\n Device: " + Build.DEVICE;
                s += "\n Model (and Product): " + Build.MODEL + " ("
                        + Build.PRODUCT + ")";

                s += "\n RELEASE: " + Build.VERSION.RELEASE;
                s += "\n BRAND: " + Build.BRAND;
                s += "\n DISPLAY: " + Build.DISPLAY;
                s += "\n CPU_ABI: " + Build.CPU_ABI;
                s += "\n CPU_ABI2: " + Build.CPU_ABI2;
                s += "\n UNKNOWN: " + Build.UNKNOWN;
                s += "\n HARDWARE: " + Build.HARDWARE;
                s += "\n Build ID: " + Build.ID;
                s += "\n MANUFACTURER: " + Build.MANUFACTURER;
                s += "\n SERIAL: " + Build.SERIAL;
                s += "\n USER: " + Build.USER;
                s += "\n HOST: " + Build.HOST;

                Log.info("DeviceInfo" + "Device Info > ", s);

            } catch (Exception e) {
                Log.info("DeviceInfo", "Error getting Device INFO");
            }

        }
    }

    String check = null;

    private void launchMainScreen() {
        android.util.Log.i("SplashActivity", "Testing launchMainScreen");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                android.util.Log.i("SplashActivity", "Testing Saved: " + CommSharedPreff.checkifPersonalInfoIsSaved(LaunchActivity.this));
                // if (CommSharedPreff.checkifPersonalInfoIsSaved(SplashActivity.this)) {

                String savedvalues = CommSharedPreff.loadStringSavedPreferencespin("PinValue", getApplicationContext());

                System.out.println("SplashActivity1" + savedvalues);
                if (savedvalues.length() > 0) {
                    Intent intent = new Intent(LaunchActivity.this,
                            MainActivity.class);
                    startActivity(intent);

                    Log.info("SplashActivity move to MainActivity");

                    finish();
                } else {
                    Intent intent = new Intent(LaunchActivity.this,
                            MainActivity.class);
                    startActivity(intent);

                    Log.info("SplashActivity move to MainActivity");

                    finish();
                }

            }
        }, 2000);
    }

    private void intializeLogger(Context ctx) {
        MultipleAppender ma = new MultipleAppender();
        String fileName = "smartscan.txt";
        String userDir;


        /*try {
            File file = new File("/data/local/tmp/", fileName);
            FileOutputStream os = new FileOutputStream(file);
            String ff="vik";
            os.write(ff.getBytes());
            os.close();
        } catch (IOException e) {
            Log.info("Inside License Activity3", " Inside exception=" + e);
            e.printStackTrace();
        }*/
        if (AndroidUtils.isSDCardMounted()) {
            userDir = "/data/local/tmp" + System.getProperty("file.separator");
            ;
        } else {
            userDir = "/data/local/tmp" + System.getProperty("file.separator");
            ;
        }

        /*if (AndroidUtils.isSDCardMounted()) {
            userDir = Environment.getExternalStorageDirectory().getPath()
                    + System.getProperty("file.separator");
            System.out.println("Vikash inside file="+userDir);
        } else {
            userDir = ctx.getFilesDir().getAbsolutePath()
                    + System.getProperty("file.separator");
        }*/

        FileAppender fileAppender = new FileAppender(userDir, fileName);
        fileAppender.setLogContentType(!AndroidUtils.isSDCardMounted());
        fileAppender.setMaxFileSize(1024 * 1024); // Set 256KB log size
        ma.addAppender(fileAppender);

        // If we are running in the emulator, we also use the AndroidLogger
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if ("000000000000000".equals(deviceId)
                || "debug".equals(BuildInfo.MODE)) {
            // This is an emulator, or a debug build
            AndroidLogAppender androidLogAppender = new AndroidLogAppender(
                    "SmartScan");
            ma.addAppender(androidLogAppender);
        }

        Log.initLog(ma, Log.TRACE);

        try {
            if (Log.getAppender() != null) {
                Appender appender = Log.getAppender();
                Log.initLog(appender, Log.INFO, 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class validateImei extends AsyncTask<Void, Void, Void> {
        Context mContext;

        public validateImei(Activity context) {

            mContext = context;

        }

        @Override
        protected Void doInBackground(Void... params) {
            android.util.Log.i("SplashActivity", "validateImei FetchImeiResponse");
            FetchImeiResponse();
            return null;
        }

        public void FetchImeiResponse() {
            String post = null;
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imeiVal = telephonyManager.getDeviceId();
            android.util.Log.i("SplashActivity", "validateImei: imeiVal: " + imeiVal);
            try {
                if (imeiVal == null || imeiVal.equals("")) {
                    post = "imei=na" + "&partnerid=" + Config.partnerId;
                } else {
                    post = "imei=" + imeiVal + "&partnerid=" + Config.partnerId;
                }
            } catch (Exception e) {
                System.out.println("exception in splash due to  " + e.toString());
                Log.info("Exceptions=" + e.toString());
            }
            // android.util.Log.i("SplashActivity", "validateImei post: " + post);
            Log.info("SplashActivity", "validateImei post: " + post);
            // String Response = SendLog.PostRequest(Config.IMEIValidationUrl, post, false);
            String Response = "true";
            try {
                JSONObject jsonObject = new JSONObject(Response);
                String contactNo = jsonObject.getString("contact");
                CommSharedPreff.savePhoneNoPreferences(contactNo, mContext);
                String resultImei = jsonObject.getString("result");
                // android.util.Log.i("SplashActivity","validateImei contactNo: "+contactNo+" resultImei: "+resultImei);
                Log.info("SplashActivity", "validateImei contactNo: " + contactNo + " resultImei: " + resultImei);
                if (resultImei.equals("true")) {
                    CommSharedPreff.saveImeiPreferences("true", mContext, "", "");

                } else {
                    String result = jsonObject.getString("error");
                    JSONObject object = new JSONObject(result);
                    Log.info("Android : in result else");
                    // System.out.println("Android : in result else");
                    String errorCode = object.getString("errocode");
                    // System.out.println("Android : in result else " + errorCode);
                    Log.info("Android : in result else" + errorCode);

                    String errorMessage = object.getString("errormessage");

                    CommSharedPreff.saveImeiPreferences("false", mContext, "", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("permission-0=" + requestCode + "Grant Result=" + Arrays.toString(grantResults));
        System.out.println("permission-00=" + requestCode + "Grant Result=" + Arrays.toString(permissions));
        int check = 0;
        System.out.println("permission-1=");
        try {
            System.out.println("permission-2=");
            switch (requestCode) {
                case 1: {
                    System.out.println("permission-3=");
                    // If request is cancelled, the result arrays are empty.
//                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    for (int i = 0; i < permissions.length; i++) {
                        System.out.println("permission-4=" + permissions.length);
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            System.out.println("permission-5=");
                            check++;
                            System.out.println("permission   " + i + check);
//                {
                            if (i == 10 && check == 11) {
                                System.out.println("permission-6=");

                                checkForCrashes();
                                System.out.println("permission-7=");

                                // new SendLog(getApplicationContext()).execute();
                                System.out.println("permission-8=");

                                // checkForCrashes();
                                mContext = getApplicationContext();
                                System.out.println("permission-9=");
                                intializeLogger(getApplicationContext());
                                System.out.println("permission-10=");

                                Log.info("SplashActivity opened");

                                android.util.Log.i("SplashActivity", "Testing isOfflineBuild: " + Config.isOfflineBuild);
                                // launchMainScreen();
                                if (Config.isOfflineBuild == false) {
                                    System.out.println("permission-11=");
//                    StartAnimations();
                                    launchMainScreen();
                                } else
                                    doLicenseCheck();

                                tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                                tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                                        PhoneStateListener.LISTEN_SERVICE_STATE);

                                tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                                tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                                        PhoneStateListener.LISTEN_CELL_INFO);

                                tManager.listen(new CustomPhoneStateListener(this.getApplicationContext()),
                                        PhoneStateListener.LISTEN_SIGNAL_STRENGTH);

                                /*boolean network = CheckInternet();
                                if (network)
                                    new validateImei(SplashActivity.this).execute();*/
                            } else if (i == 13 && check < 10) {
                                System.out.println("permission-12=");
                                deniedialog();
                            }

                            // Toast.makeText(SplashActivity.this, "yes Permission granted", Toast.LENGTH_SHORT).show();
                            //               Log.d( "Permissions", "Permission Granted: " + permissions[i] );
                        } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            System.out.println("permission-13=" + grantResults[i]);
                            //            Log.d( "Permissions", "Permission Denied: " + permissions[i] );
//                    System.out.println("permission  no " + i  +check);
//                    Toast.makeText(SplashActivity.this, "NO Permission granted", Toast.LENGTH_SHORT).show();
                            if (i == 13 && check < 10) {
                                System.out.println("permission-14=");
                                deniedialog();
                            }
                        }
                    }

                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("permission-15=");
            System.out.println("Exception cause in the permission acceptance tag   " + e);
        }
    }

    public void deniedialog() {
        try {


            android.support.v7.app.AlertDialog.Builder ad = new android.support.v7.app.AlertDialog.Builder(LaunchActivity.this);
            ad.setTitle("App not Install");
            ad.setMessage("Re-install the App and allow all the permission");
            ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            ad.setCancelable(false);
            ad.show();
        } catch (Exception e) {
            System.out.println("Exception cause in the Denied dialog acceptance tag   " + e);
        }
    }

    private String getInfo() {

        StringBuffer sb = new StringBuffer();

        sb.append("abi: ").append(Build.CPU_ABI).append("n");

        if (new File("/proc/meminfo").exists()) {

            try {

                BufferedReader br = new BufferedReader(new FileReader(new File("/proc/meminfo")));

                String aLine;

                while ((aLine = br.readLine()) != null) {

                    sb.append(aLine + "n");

                }

                if (br != null) {

                    br.close();

                }

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return sb.toString();

    }

    public String read(String fname) {

        BufferedReader br = null;
        String response = null;

        try {

            StringBuffer output = new StringBuffer();
            String fpath = "/data/local/tmp/QuTrust/" + fname + ".txt";

            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return response;

    }

    private String readFromFile(Context context, String filename) {

        String ret = "";

        try {
            FileInputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.info("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.info("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public String collectOfflineResponse(String fname) {

        String fpath = "/data/local/tmp/QuTrust/" + fname + ".txt";
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
        System.out.println("Vikash inside value=" + text);

        return String.valueOf(text);
    }

    public void sendValueToServer()
    {

        new OTPCheckTask().execute("8894617");
    }
    private class OTPCheckTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        String resultVal = "";
        String errorStr = "";
        String otpVal = "";

       /* @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(SplashActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Validating License...");
            pDialog.show();
        }*/

        @Override
        protected String doInBackground(String... params) {
            //doHttpReq();
            String androidId = Build.SERIAL;;
            Log.info("Before login called file delete");
            fileReEntry("request");
            fileReEntry(androidId + "_response");
            doOfflineRequest();
            return null;
        }


        protected void onPostExecute(String params) {
            super.onPostExecute(params);
            Log.info("Inside Splash Activity4", " Inside post execute=");
            // pDialog.dismiss();
            String result = null;
            String message = null;


            /*failureTimer = new Timer();
            failureTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    pDialog.dismiss();
                     String result = collectOfflineResponse("response");
                    proceedRequests(result);
                }
            }, 10000);*/

            try {
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    public void run() {
                        Log.info("Inside splash Activity4", " Inside post delayed after 10 second ready for client read=");
                        //pDialog.dismiss();
                      /*  String androidId = Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.ANDROID_ID);*/
                        String androidId = Build.SERIAL;

                        androidId += "_response";
                        Log.info("Android id value=" + androidId);
                        String result = collectOfflineResponse(androidId);
                        Log.info("Inside splash Activity7", " collectOfflineResponse path=" + result);
                        proceedRequests(result, androidId);


                    }
                }, 3000);
            } catch (Exception e) {
                Log.info("Inside splash Activity7", " collectOfflineResponse path=" + e);
                e.printStackTrace();
            }

           /* result = collectOfflineResponse("response");
            proceedRequests(result);*/
            // File fpath = "/data/local/tmp/QuTrust/"+"response_validation"+".txt";
            /*try {
                result=getFileContents("QuTrust");
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            // String results=result.substring(1, result.length()-1);
            // String results=stripLeadingAndTrailingQuotes(result);

            // ContextSdk.tagEvent("OnOTPValidation", null);
           /* if (result.equalsIgnoreCase("")) {
                result = collectOfflineResponse("response");
                if(result.equalsIgnoreCase(""))
                {
                    collectOfflineResponse("response");
                }
                else
                {
                    proceedRequest(result);
                }
            }*/
        }
    }
    public void fileReEntry(String fname) {
        Log.info("Inside splash Activity19", " fileReEntry=" + fname);
        try {
            File file = new File("/data/local/tmp", fname + ".txt");
            FileOutputStream os = new FileOutputStream(file);
            String data = "";
            os.write(data.getBytes());
            os.close();
        } catch (IOException e) {
            Log.info("Inside splash Activity20", " fileReEntry=" + e);
            e.printStackTrace();
        }
    }

    public void doOfflineRequest() {
        Log.info("Inside splash Activity1");

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imeiVal = telephonyManager.getDeviceId();
        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String post = null;

        try {
           // edittextval = editText.getText().toString().trim();
            System.out.println("Vikash edittext value2=" + Build.SERIAL);
            JSONObject finalJSON = new JSONObject();
            JSONObject validationDetailsJSON = new JSONObject();
            validationDetailsJSON.put("Password", "8894617");
           // validationDetailsJSON.put("appid", "2181118B-CC1B-9484-1AFA-7DB42B3C1C0E");
            validationDetailsJSON.put("appid", "79E36F76-2DE6-84FE-6BF7-05713353DFD3");
            validationDetailsJSON.put("CallType", "Entry Level Screening");
            JSONObject basicDetailsJSON = new JSONObject();
            basicDetailsJSON.put("IMEI", imeiVal);
            basicDetailsJSON.put("DeviceID", androidId);
            basicDetailsJSON.put("make", Build.MANUFACTURER + "");
            basicDetailsJSON.put("model", Build.MODEL + "");
            basicDetailsJSON.put("serialno", Build.SERIAL + "");
            basicDetailsJSON.put("buildnumber", Build.DISPLAY + "");
            basicDetailsJSON.put("OS Version", System.getProperty("os.version") + "(" + Build.VERSION.INCREMENTAL + ")");
            basicDetailsJSON.put("PartnerID", "24");
            basicDetailsJSON.put("idfa", "10");
            validationDetailsJSON.put("Basic Details", basicDetailsJSON);
            finalJSON.put("validation details", validationDetailsJSON);
            post = finalJSON.toString();
            Log.info("splash Activity","pin json data="+post);
        } catch (JSONException e) {
            e.printStackTrace();
        }

           /* String postData = "imei=" + imeiVal + "&pin="+edittextval+"&deviceid=" + androidId
                    + "&idfa=" + getIdfaVal(getApplicationContext()) + "&partnerid=10";*/

        // Log.info("MainActivity otpValidation call URL : " + URL + " , postData : " + post);

        // otp validation - check if client id is not null and then add clientid in api call
        String utmClientId = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyUtmClientId, LaunchActivity.this);
        if (utmClientId != null && utmClientId.length() > 0) {
            post += "&clientid=" + utmClientId;
        }
        Log.info("Inside splash Activity2", " Before File value=" + post);
        try {
            File file = new File("/data/local/tmp/", "request.txt");
            FileOutputStream os = new FileOutputStream(file);
            os.write(post.getBytes());
            os.close();
        } catch (IOException e) {
            Log.info("Inside splash Activity3", " Inside exception=" + e);
            e.printStackTrace();
        }
    }

    public void proceedRequests(String result, String androidid) {
        Log.info("Inside splash Activity8", " proceedRequests=" + result);
        //if (result.equalsIgnoreCase("")) {
        Log.info("Inside splash Activity9", " proceedRequests=" + result);
        result = collectOfflineResponse(androidid);
        Log.info("Inside splash Activity10", " proceedRequests=" + result);
        if (result.equalsIgnoreCase("")) {
            Log.info("Inside splash Activity inside report empty", " proceedRequests=" + result);
            fileReEntry("request");
            String androidIds = Build.SERIAL;
            fileReEntry(androidIds + "_response");
            /*Toast.makeText(SplashActivity.this, "Unable to login. Please check USB connection.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SplashActivity.this,
                    LoginOption.class);
            startActivity(intent);
            finish();*/
            Log.info("Inside splash Activity11", " proceedRequests=" + result);
            //collectOfflineResponse(androidid);
        } else {
            Log.info("Inside splash Activity12", " proceedRequests=" + result);
            proceedRequest(result, androidid);
        }
        // }
        /*else {
            proceedRequest(result, androidid);
        }*/
    }

    public void proceedRequest(String result, String androidid) {

       /* String androidIds = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);*/
        String androidIds = Build.SERIAL;

        Log.info("Inside splash Activity13", " proceedRequest=" + result);

        JSONObject jsonObject;
        String results = null;
        String messagse = null;
        String isinternet = null;


        try {
            jsonObject = new JSONObject(result);
            results = jsonObject.getString("result");
            messagse = jsonObject.getString("message");
            isinternet = jsonObject.getString("isinternet");
            Log.info("Inside splash Activity14", " proceedRequest=" + results);
            Log.info("Inside splash Activity15", " proceedRequest=" + messagse);
            System.out.println("Vikash inside Result=" + result);
            System.out.println("Vikash inside messagse=" + messagse);

            if (results.equalsIgnoreCase("")) {
                Log.info("Inside splash Activity", " result empty=" + messagse);
                fileReEntry("request");
                fileReEntry(androidIds + "_response");
               // Toast.makeText(SplashActivity.this, "Unable to login. Please check USB connection.", Toast.LENGTH_LONG).show();
               /* Intent intent = new Intent(SplashActivity.this,
                        LoginOption.class);
                startActivity(intent);
                finish();*/
            } else if (results.equalsIgnoreCase("false") && isinternet.equalsIgnoreCase("false")) {
                Log.info("Inside splash Activity17", " proceedRequest=" + messagse);

                //CommSharedPreff.saveStringPreferencespin("PinValue",edittextval,getApplicationContext());
               /* Intent intent = new Intent(LicenseActivity.this,
                        MainActivity.class);
                startActivity(intent);*/
               // Toast.makeText(SplashActivity.this, "Unable to login. Please check desktop internet connection.", Toast.LENGTH_LONG).show();

                Log.info("Inside splash Activity18", " proceedRequest=" + messagse);

                fileReEntry("request");
                fileReEntry(androidIds + "_response");
                Log.info("Inside splash Activity21", " fileReEntry deleted=");
                /*Intent intent = new Intent(SplashActivity.this,
                        LoginOption.class);
                startActivity(intent);
                finish();*/
                Log.info("Inside splash Activity22", " Next activity called=");

                // finish();
         /*   new AlertDialog.Builder(LicenseActivity.this)
                    .setTitle("Login Error")
                    .setCancelable(false)
                    .setMessage("Invalid PIN")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            fileReEntry("request.txt");
                            fileReEntry("response.txt");
                            Intent intent = new Intent(LicenseActivity.this,
                                    LoginOption.class);
                            startActivity(intent);
                            finish();
                            // showOTPAlert();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();*/
            } else if (results.equalsIgnoreCase("false") && isinternet.equalsIgnoreCase("true")) {
                Log.info("splash", "results.equalsIgnoreCase(\"false\") && isinternet.equalsIgnoreCase(\"true\")");
               // Toast.makeText(SplashActivity.this, "Invalid PIN.", Toast.LENGTH_SHORT).show();
                fileReEntry("request");
                fileReEntry(androidIds + "_response");
                Log.info("Inside splash Activity21", " fileReEntry deleted=");
                /*Intent intent = new Intent(SplashActivity.this,
                        LoginOption.class);
                startActivity(intent);
                finish();*/
            } else if (results.equalsIgnoreCase("true") && isinternet.equalsIgnoreCase("true")) {
                Log.info("splashn", "results.equalsIgnoreCase(\"true\")&&isinternet.equalsIgnoreCase(\"true\")");
                CommSharedPreff.saveStringPreferencespin("PinValue", "8894617", getApplicationContext());
                CommSharedPreff.saveStringPreferencescalltype("CallType", "Entry Level Screening", getApplicationContext());
                fileReEntry("request");
                fileReEntry(androidIds + "_response");
                /*Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);*/

                Log.info("splash Activity move to MainActivity");

                finish();
            } else if (results.equalsIgnoreCase("error")) {
                Log.info("splash", "results.equalsIgnoreCase(\"error\")");
                fileReEntry("request");
                fileReEntry(androidIds + "response");
               /* Toast.makeText(SplashActivity.this, "Unable to validate PIN.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashActivity.this,
                        LoginOption.class);
                startActivity(intent);*/

                util.logger.Log.info("splash Activity move to MainActivity");

                finish();
            } else {
                Log.info("splash activity", "Inside else ");
                fileReEntry("request");
                fileReEntry(androidIds + "response");
               /* Toast.makeText(SplashActivity.this, "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SplashActivity.this,
                        LoginOption.class);
                startActivity(intent);

                util.logger.Log.info("License Activity move to MainActivity");

                finish();*/
            }
        }
        catch (JSONException e) {
           // Toast.makeText(SplashActivity.this, "Unexpected error reading signin response . Please try again.", Toast.LENGTH_LONG).show();
            Log.info("splash activity", "Inside json exception ");
            fileReEntry("request");
            fileReEntry(androidIds + "response");
            /*Intent intent = new Intent(SplashActivity.this,
                    LoginOption.class);
            startActivity(intent);

            util.logger.Log.info("License Activity move to MainActivity");

            finish();*/
            Log.info("splash activity", " proceedRequest=" + e);
            e.printStackTrace();
        }
        catch (Exception e) {

           // Toast.makeText(SplashActivity.this, "Unexpected error reading signin response . Please try again.", Toast.LENGTH_LONG).show();
            Log.info("splash activity", "Inside json exception ");
            fileReEntry("request");
            fileReEntry(androidIds + "response");
           /* Intent intent = new Intent(SplashActivity.this,
                    LoginOption.class);
            startActivity(intent);

            util.logger.Log.info("License Activity move to MainActivity");

            finish();*/
            Log.info("Inside splash activity6", " proceedRequest=" + e);
            e.printStackTrace();
        }

    }
    public void setMobileDataState(boolean mobileDataEnabled)
    {
        try
        {
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

            if (null != setMobileDataEnabledMethod)
            {
                setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
            }
        }
        catch (Exception ex)
        {
            Log.info("Error setting mobile data state"+ ex);
        }
    }
    public void getDeviceId()
    {
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

        UsbManager manager = (UsbManager)getSystemService(Context.USB_SERVICE);

        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while(deviceIterator.hasNext()){
            UsbDevice device = deviceIterator.next();

            manager.requestPermission(device, mPermissionIntent);

           /* txtInfo.append("Model:" + device.getDeviceName() + "\n");
            txtInfo.append("DeviceID:" + device.getDeviceId() + "\n");
            txtInfo.append("Vendor:" + device.getVendorId() + "\n");
            txtInfo.append("Product:" + device.getProductId() + "\n");
            txtInfo.append("Class:" + device.getDeviceClass() + "\n");
            txtInfo.append("Subclass:" + device.getDeviceSubclass() + "\n");
            txtInfo.append("Protocol:" + device.getDeviceProtocol() + "\n");*/

            UsbInterface intf = device.getInterface(0);
            int epc = 0;
            epc = intf.getEndpointCount();
           // txtInfo.append("Endpoints:" + epc + "\n");

           // txtInfo.append("Permission:" + Boolean.toString(manager.hasPermission(device))  + "\n");

            UsbDeviceConnection connection = manager.openDevice(device);
            if(null==connection){
               // txtInfo.append("(unable to establish connection)\n");
            } else {

                // Claims exclusive access to a UsbInterface.
                // This must be done before sending or receiving data on
                // any UsbEndpoints belonging to the interface.
                connection.claimInterface(intf, true);

                // getRawDescriptors can be used to access descriptors
                // not supported directly via the higher level APIs,
                // like getting the manufacturer and product names.
                // because it returns bytes, you can get a variety of
                // different data types.
                byte[] rawDescs = connection.getRawDescriptors();
                String manufacturer = "", product = "";

                try
                {
                    byte[] buffer = new byte[255];
                    int idxMan = rawDescs[14];
                    int idxPrd = rawDescs[15];

                    int rdo = connection.controlTransfer(UsbConstants.USB_DIR_IN
                                    | UsbConstants.USB_TYPE_STANDARD, STD_USB_REQUEST_GET_DESCRIPTOR,
                            (LIBUSB_DT_STRING << 8) | idxMan, 0, buffer, 0xFF, 0);
                    manufacturer = new String(buffer, 2, rdo - 2, "UTF-16LE");

                    rdo = connection.controlTransfer(UsbConstants.USB_DIR_IN
                                    | UsbConstants.USB_TYPE_STANDARD, STD_USB_REQUEST_GET_DESCRIPTOR,
                            (LIBUSB_DT_STRING << 8) | idxPrd, 0, buffer, 0xFF, 0);
                    product = new String(buffer, 2, rdo - 2, "UTF-16LE");

                } catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }

                /*txtInfo.append("Manufacturer:" + manufacturer + "\n");
                txtInfo.append("Product:" + product + "\n");
                txtInfo.append("Serial#:" + connection.getSerial() + "\n");*/

                System.out.println("Manufacturer:" + manufacturer + "\n");
                System.out.println("Product:" + product + "\n");
                System.out.println("Serial#:" + connection.getSerial() + "\n");
            }

          //  txtInfo.append("------------------------------------\n");
        }

    }
    private class OTPCheckTaskOnline extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        String resultVal = "";
        String errorStr = "";
        String otpVal = "";

       /* @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(SplashActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Validating License...");
            pDialog.show();
        }*/

        @Override
        protected String doInBackground(String... params) {
            doHttpReq();
            return null;
        }

        private void doHttpReq() {

            /*edittextval=editText.getText().toString().trim();

            if(edittextval.length()<0&&edittextval.length()>5)
            {*/
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imeiVal = telephonyManager.getDeviceId();
            String androidId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            String post = null;
            /*JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("Password","12345");
                jsonObject.put("IMEI",imeiVal);
                jsonObject.put("DeviceID",androidId);
                jsonObject.put("PartnerID",24);
                jsonObject.put("idfa", getIdfaVal(getApplicationContext()));

                post=jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            try {
                // edittextval = editText.getText().toString().trim();
                // System.out.println("Vikash edittext value2=" + edittextval);
                JSONObject finalJSON = new JSONObject();
                JSONObject validationDetailsJSON = new JSONObject();
                validationDetailsJSON.put("Password", "5354656");
                validationDetailsJSON.put("appid", "66SD6-2DIE6-84FFE-6BF37-5767T");
                validationDetailsJSON.put("CallType", "Entry Level Screening");
                JSONObject basicDetailsJSON = new JSONObject();
                basicDetailsJSON.put("IMEI", imeiVal);
                basicDetailsJSON.put("DeviceID", androidId);
                basicDetailsJSON.put("PartnerID", "24");
                basicDetailsJSON.put("idfa", "10");
                validationDetailsJSON.put("Basic Details", basicDetailsJSON);
                finalJSON.put("validation details", validationDetailsJSON);
                post = finalJSON.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String URL = Config.baseUrl + Config.LIcensePinValidationUrl;
           /* String postData = "imei=" + imeiVal + "&pin="+edittextval+"&deviceid=" + androidId
                    + "&idfa=" + getIdfaVal(getApplicationContext()) + "&partnerid=10";*/

            Log.info("MainActivity otpValidation call URL : " + URL + " , postData : " + post);

            // otp validation - check if client id is not null and then add clientid in api call
            String utmClientId = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyUtmClientId, LaunchActivity.this);
            if (utmClientId != null && utmClientId.length() > 0) {
                post += "&clientid=" + utmClientId;
            }


            resultVal = PostRequest(URL, post);
            // Log.info("MainActivity otpValidation api response : " + resultVal + " , code : " + statusCode);

            /*if (statusCode != 200) {
                Log.info("MainActivity otpValidation api response : " + resultVal + " , code : " + statusCode);
                resultVal = "{\"result\":\"error\",\"message\":\"Internet is Not Working\"}";
                return;
            }*/
          /*  }
            else{
                Toast.makeText(getApplicationContext(),"Please Enter Five Digit Pin",Toast.LENGTH_LONG).show();
        }*/

        }

        protected void onPostExecute(String params) {
            super.onPostExecute(params);
          //  pDialog.dismiss();

            // ContextSdk.tagEvent("OnOTPValidation", null);
            JSONObject jsonObject;

            String result=null;
            String message=null;
            try {
                jsonObject=new JSONObject(resultVal);
                result=jsonObject.getString("result");
                message=jsonObject.getString("message");
                System.out.println("Vikash inside Result="+result);

            if (result.equalsIgnoreCase("false")) {
                //CommSharedPreff.saveStringPreferencespin("PinValue",edittextval,getApplicationContext());
               /* Intent intent = new Intent(LicenseActivity.this,
                        MainActivity.class);
                startActivity(intent);*/

                Log.info("License Activity move to MainActivity");

                Toast.makeText(LaunchActivity.this, "Unable to proceed,Please Check Internet Connectivity.", Toast.LENGTH_SHORT).show();

                finish();
                /*new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Login Error")
                        .setCancelable(false)
                        .setMessage("Invalid PIN")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                *//*Intent intent = new Intent(SplashActivity.this,
                                        LoginOption.class);
                                startActivity(intent);
                                finish();*//*
                                return;
                                // showOTPAlert();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();*/
            } else if (result.equalsIgnoreCase("true")) {

                CommSharedPreff.saveStringPreferencespin("PinValue","5354656",getApplicationContext());
                CommSharedPreff.saveStringPreferencescalltype("CallType","Entry Level Screening",getApplicationContext());
                Intent intent = new Intent(LaunchActivity.this,
                        MainActivity.class);
                startActivity(intent);

                Log.info("License Activity move to MainActivity");

                finish();
            }
            else if (result.equalsIgnoreCase("error")) {
                Toast.makeText(LaunchActivity.this, "Unable to proceed,Please Check Internet Connectivity.", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);*/

                Log.info("License Activity move to MainActivity");

                finish();
            }
            else
            {
                Toast.makeText(LaunchActivity.this, "Unable to proceed,Please Check Internet Connectivity.", Toast.LENGTH_SHORT).show();
                finish();
            }
            } catch (JSONException e) {
                Toast.makeText(LaunchActivity.this, "Unable to proceed,Please Check Internet Connectivity.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
            catch(Exception e)
            {
                Toast.makeText(LaunchActivity.this, "Unable to proceed,Please Check Internet Connectivity.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }
    }

    private String PostRequest(String uRL, String dataSendToTheServer) {

        System.out.println("Vikash inside postreq="+dataSendToTheServer);
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

            int timeoutConnection = 50000;
            HttpConnectionParams
                    .setConnectionTimeout(params, timeoutConnection);
            int timeoutSocket = 50000;
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
            // se.setContentType("application/json");
            request.setEntity(se);
            // ArrayList<NameValuePair> seClientID = new ArrayList<NameValuePair>();
            //seClientID.add(new BasicNameValuePair("partner_id", Config.partnerId));
            // request.setEntity(new UrlEncodedFormEntity(seClientID));
           /* request.getParams().setBooleanParameter(
                    "http.protocol.expect-continue", false);*/
            /** Getting Response from server. */
            HttpResponse response = client.execute(request);
            //statusCode = response.getStatusLine().getStatusCode();

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

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                        }
                    } else {
                         System.out.println("permission denied for device " + device);
                    }
                }
            }
        }
    };

}

