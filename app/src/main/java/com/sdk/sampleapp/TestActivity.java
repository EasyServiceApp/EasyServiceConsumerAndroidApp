package com.sdk.sampleapp;

/**
 * Created by vikas on 20/10/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/*import com.mlapps.truevaluesdk.AirPressureTest;
import com.mlapps.truevaluesdk.AirTemperatureTest;
import com.mlapps.truevaluesdk.AutoFocusTest;
import com.mlapps.truevaluesdk.BatteryTest;
import com.mlapps.truevaluesdk.BiometricTest;
import com.mlapps.truevaluesdk.BluetoothTest;
import com.mlapps.truevaluesdk.CallTest;
import com.mlapps.truevaluesdk.CameraTest;
import com.mlapps.truevaluesdk.CommSharedPreff;
import com.mlapps.truevaluesdk.DeviceTemperatureTest;
import com.mlapps.truevaluesdk.EarPieceTest;
import com.mlapps.truevaluesdk.FlipTest;
import com.mlapps.truevaluesdk.FrontSpeakerTest;
import com.mlapps.truevaluesdk.FrontVideoRecorderTest;
import com.mlapps.truevaluesdk.GpsTest;
import com.mlapps.truevaluesdk.GravityTest;
import com.mlapps.truevaluesdk.GyroscopeGamingTest;
import com.mlapps.truevaluesdk.HeadJackTest;
import com.mlapps.truevaluesdk.HeartRateMonitorTest;
import com.mlapps.truevaluesdk.HumidityTest;
import com.mlapps.truevaluesdk.IRTest;
import com.mlapps.truevaluesdk.ImeiTest;
import com.mlapps.truevaluesdk.InternetTest;
import com.mlapps.truevaluesdk.LedTest;
import com.mlapps.truevaluesdk.LightTest;
import com.mlapps.truevaluesdk.MagneticTest;
import com.mlapps.truevaluesdk.MicTest;
import com.mlapps.truevaluesdk.MotionDetectorTest;
import com.mlapps.truevaluesdk.NFCTest;
import com.mlapps.truevaluesdk.NetworkTest;
import com.mlapps.truevaluesdk.OrientationGamingTest;
import com.mlapps.truevaluesdk.PowerKeyTest;
import com.mlapps.truevaluesdk.ProximityTest;
import com.mlapps.truevaluesdk.RamTest;
import com.mlapps.truevaluesdk.RootTest;
import com.mlapps.truevaluesdk.SPO2Test;
import com.mlapps.truevaluesdk.S_PenHoveringActivity;
import com.mlapps.truevaluesdk.ScreenLockTest;
import com.mlapps.truevaluesdk.ScreenTest;
import com.mlapps.truevaluesdk.SimTest;
import com.mlapps.truevaluesdk.Sms;
import com.mlapps.truevaluesdk.SmsTest;
import com.mlapps.truevaluesdk.SoundTest;
import com.mlapps.truevaluesdk.StepCounterTest;
import com.mlapps.truevaluesdk.StepDetectorTest;
import com.mlapps.truevaluesdk.StorageTest;
import com.mlapps.truevaluesdk.SwingTest;
import com.mlapps.truevaluesdk.TestBase;
import com.mlapps.truevaluesdk.TestResultCallbacks;
import com.mlapps.truevaluesdk.TorchTest;
import com.mlapps.truevaluesdk.TrimMemoryClass;
import com.mlapps.truevaluesdk.TrueValueSDK;
import com.mlapps.truevaluesdk.UsbTest;
import com.mlapps.truevaluesdk.UvSensorsTest;
import com.mlapps.truevaluesdk.ValueEnumConstants;
import com.mlapps.truevaluesdk.VibrationTest;
import com.mlapps.truevaluesdk.VideoRecorderTest;
import com.mlapps.truevaluesdk.VolumeKeyTest;
import com.mlapps.truevaluesdk.WifiTest;*/

//import com.mlapps.truevaluesdk.CallTest;
//import com.mlapps.truevaluesdk.CommSharedPreff;
//import com.mlapps.truevaluesdk.S_PenHoveringActivity;
import com.mlapps.truevaluesdk.CommSharedPreff;
import com.mlapps.truevaluesdk.HomeService;
import com.mlapps.truevaluesdk.ProximityService;
import com.mlapps.truevaluesdk.S_PenHoveringActivity;
import com.mlapps.truevaluesdk.TestBase;
import com.mlapps.truevaluesdk.TestResultCallbacks;
//import com.mlapps.truevaluesdk.TrimMemoryClass;
import com.mlapps.truevaluesdk.USBReceiver;
import com.mlapps.truevaluesdk.ValueEnumConstants;
import com.service.easyservice.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import util.logger.Log;

import static android.support.v4.view.ViewCompat.animate;


@SuppressWarnings("deprecation")
public class TestActivity extends Activity implements TestResultCallbacks {

    int biomatricTestInfo = -2, SPO2TestInfo = -2,
            ledNotificationInfo = -2, screenLockTestInfo = -2, nfcTestInfo = -2,
            gyroscopeForGamingTestInfo = -2, uvSensorTestInfo = -2;
    public int hrmTestInfo = -2;
    boolean requestCheck = false;

    JSONObject root;
    JSONObject reportObj;
    HashMap<String, Integer> resultmap=new HashMap<String, Integer>();
    Intent data;

    TestBase testBase = null;
    TestBase testBaseautomated = null;
    FrameLayout viewHolders;


    // TextureView textureView;

    WebView webView;
    SurfaceView surfaceView;
    //  surfaceViewVideo, surfaceViewVideoFront;
    LinearLayout cam_layout;

    ImageView centerImage = null, captureimage = null, playimage = null, capturevideo = null, playvideo = null;
    FrameLayout frameLayout = null;
    TextView scanningHeader = null, scanningScore = null, extraText = null, counterTextView = null;
    AnimatedGifImageView scanningGif;
    ValueEnumConstants.ResultTypeValue stepDetectorResult, stepCounterResult, motionDetectorResult;
    public static boolean testsSkip = false;
    public static boolean forceFail = false;

    public static int currentTestIndex = 0, totalTests = 51;
    private boolean isOptionalTestStarted = false;
    private boolean isOptionalTestStartedbutton = false;

    public ValueEnumConstants.DeviceTestType mTestOrder[];

    public static boolean isDualSim = false;

    public static TestActivity mInstance = null;
    Intent spenIntent;
    public boolean nfcenabled = false;


    public boolean isRetryflip = false;

    public boolean isRetrySwing = false;

    int screenTestInfo = -1, vibratorTestInfo = -1, brightnessTestInfo = -1,
            pixelTestInfo = -1, cameraRearTestInfo = -1, cameraFrontTestInfo = -1, flashTestinfo = -1, torchTestInfo = -1, irTestInfo = -2,
            usbTestInfo = -1, networkTestInfo1 = -1, networkTestInfo2 = -2, ramTestInfo = -1,
            internalStorageTestInfo = -1, externalStorageTestInfo = -1,
            storageTestInfo = -1, btTestInfo = -1, wifiTestInfo = -1,
            jackTestInfo = -1, micTestInfo = -1, batteryTestInfo = -1,
            accelerometerTestInfo = -1, flipactiontest = -1, swingactiontest = -1, gravityTestInfo = -2,
            gyroscopeTestInfo = -1, accelerationTestInfo = -1,
            rotationTestInfo = -1, motionTestInfo = -2,
            stepCounterTestInfo = -2, stepDetectorTestInfo = -2,
            magneticTestInfo = -1, orientationTestInfo = -2,
            proximityTestInfo = -1, airTemperatureTestInfo = -2, videoTestInfo = -1, frontSpeakerTestInfo = -1,
            lightTestInfo = -1, airPressureTestInfo = -2,
            humidityTestInfo = -2, deviceTempTestInfo = -2,
            callTestInfo = -1, callTestInfoSim1 = -1, callTestInfoSim2 = -1,
            smsTestInfo = -1, smsTestInfoSim1 = -1, smsTestInfoSim2 = -1,
            powerKeyTestInfo = -1, backKeyTestInfo = -1, homeKeyTestInfo = -1,
            volumeKeyUpTestInfo = -1, volumeKeyDownTestInfo = -1,
            menuKeyTestInfo = -2, internetTestInfo = -1, cameraAutofocusTestInfo = -1, rootTestInfo = -1,
            imeiTestInfo = -1, simTestInfo1 = -1, simTestInfo2 = -2, loudSpeakerTestInfo = -1, earPieceTestInfo = -1, gpsTestInfo = -1, frontVideoRecoderTest = -1, spenhoveringtestinfo = -2;

    String networkTestData1 = "{}", callTestDataSim1 = "{}", callTestDataSim2 = "{}", smsTestDataSim1 = "{}", smsTestDataSim2 = "{}";
    String wifiTestData = "{}", gpsTestData = "{}", internetTestData = "{}", imeiTestData = "{}", rootTestData = "{}", simTestData1 = "{}", simTestData2 = "{}";
    String cameraRearTestData = "{}", cameraFrontTestData = "{}", flashTestData = "{}", torchTestData = "{}", batteryTestData = "{}";
    String vibratorTestData = "{}", ramTestData = "{}", internalStorageTestData = "{}", externalStorageTestData = "{}", lightTestData = "{}";
    String magneticTestData = "{}", screenTestData = "{}", btTestData = "{}", jackTestData = "{}", earPieceTestData = "{}", micTestData = "{}";
    String loudSpeakerTestData = "{}", frontSpeakerTestData = "{}", cameraAutofocusTestData = "{}", powerKeyTestData = "{}", volumeKeyUpTestData = "{}";
    String volumeKeyDownTestData = "{}", homeKeyTestData = "{}", backKeyTestData = "{}", menuKeyTestData = "{}", accelerometerTestData = "{}";
    String gyroscopeTestData = "{}", proximityTestData = "{}", videoTestData = "{}", frontVideoRecorderTestData = "{}", usbTestData = "{}";

    String airPressureTestData = "{}", airTemperatureTestData = "{}", gravityTestData = "{}", irTestData = "{}", nfcTestData = "{}", gyroscopeForGamingTestData = "{}";
    String orientationTestData = "{}", deviceTempTestData = "{}", stepDetectorTestData = "{}", stepCounterTestData = "{}", motionTestData = "{}";
    String humidityTestData = "{}", biomatricTestData = "{}", hrmTestData = "{}", SPO2TestData = "{}", screenLockTestData = "{}", ledNotificationData = "{}";
    String uvSensorTestData = "{}", spenHoveringTestData = "{}";

    SensorManager HeartRateSensorManager = null;
    FrameLayout viewHolder;
    private boolean isTestDonenetwork, isTestDonenetwork2, isTestDonewifi, isTestDoneimei, isTestDonesms1, isTestDonesms2, isTestDonerooted, isTestDoneBattery, isTestDoneRam, isTestDoneLight, isTestDoneMagnet,
            isTestDoneBluetooth, isTestDoneGPS, isTestDonesim1, isTestDonesim2, isTestDonestorageinternal, isTestDonestorageexternal, isTestDoneAirpressure, isTestDoneAirtemperature, isTestDonegravity, isTestDoneinfrared,
            isTestDonedevicetemperature, isTestDonehumidity, isTestDoneUV, isnfctest, isgyrogametest, isorientationgametest, isusbtest, isinternettest;
    private int[] testResultArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getApplicationContext(), ProximityService.class);
        startService(intent);

        mInstance = this;
        HeartRateSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // makeNotification(getApplicationContext());

        setupTestActivity();

        startUpTests();
    }

    public static boolean isConnected(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
        return intent.getExtras().getBoolean("connected");
    }

    private void makeNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("QuTrust True Value")
                .setContentText("True value evalution is going on")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.launcher_icon2)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.launcher_icon2));

        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = builder.build();
        } else {
            n = builder.getNotification();
        }

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, n);
    }

    private void clearNotification() {
        try {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(100);
        } catch (Exception e) {
        }
    }

    private void setupTestActivity() {
        try {
            setContentView(R.layout.activity_test);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

           /* ActionBar actionBar = getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color
                    .parseColor("#00000000")));*/
            frameLayout = (FrameLayout) findViewById(R.id.cameraframelayout);
            scanningHeader = (TextView) findViewById(R.id.scanningHeader);
            scanningHeader.setText("");

            cam_layout = (LinearLayout) findViewById(R.id.camera_layout);
            cam_layout.setVisibility(View.VISIBLE);

            centerImage = (ImageView) findViewById(R.id.centerImage);
            centerImage.setImageDrawable(null);

            scanningGif = ((AnimatedGifImageView) findViewById(R.id.gif1));
            scanningGif.setAnimatedGif(R.raw.scanning8, AnimatedGifImageView.TYPE.AS_IS);
            scanningGif.setVisibility(View.GONE);

            scanningScore = (TextView) findViewById(R.id.scanningScore);
            scanningScore.setText("");
            scanningScore.setVisibility(View.GONE);

            extraText = (TextView) findViewById(R.id.extratext);
            extraText.setText("");
            extraText.setVisibility(View.INVISIBLE);

            captureimage = (ImageView) findViewById(R.id.capimage);
            playimage = (ImageView) findViewById(R.id.recimage);

            capturevideo = (ImageView) findViewById(R.id.capvideo);
            playvideo = (ImageView) findViewById(R.id.recvideo);

            webView = (WebView) findViewById(R.id.webViewInternet);
            webView.setVisibility(View.GONE);

            surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
            surfaceView.setVisibility(View.GONE);

            counterTextView = (TextView) findViewById(R.id.counterText);
            counterTextView.setText("");
            counterTextView.setVisibility(View.INVISIBLE);

            hrmTestInfo = -2;

        } catch (Exception e) {
            Log.info("the exception caught in the viewable create part  " + e.toString());
        }
    }

    private void startUpTests() {
        int index = -1;
        ValueEnumConstants.DeviceTestType tempOrder[] = new ValueEnumConstants.DeviceTestType[55];

        //  ValueEnumConstants.DeviceTestType tempOrderauto[] = new ValueEnumConstants.DeviceTestType[55];
        System.gc();

        // Check for sim type
        testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestCall1, getApplicationContext(), TestActivity.this, TestActivity.this);
        boolean simIdBasedDual = testBase.isDeviceDualSim();
        int phoneTypeBasedDual = new MainActivity().getPhoneCount();
        boolean phoneDualType = new MainActivity().getPhoneSubType(this);

        if (simIdBasedDual || phoneTypeBasedDual >= 2 || phoneDualType)
            isDualSim = true;
        else
            isDualSim = false;


        // Test start from here...

        //Check for call type
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestCall1;
        if (isDualSim) {
            index++;
            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestCall2;
        }


        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestRearCamera;

        // Check for front camera

        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestFrontCamera;

        // mCameraTestObj = new CameraTest(getApplicationContext(), TestActivity.this, TestActivity.this, viewHolder);
        testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestFlash, getApplicationContext(), TestActivity.this, TestActivity.this);

        if (testBase.isTestActionableFlash(ValueEnumConstants.DeviceTestType.ETestFlash)) {
            Log.info("Flash Test", "hasFlashvalue is test ACtionable insde if=");
            index++;
            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestFlash;
        } else {
            Log.info("Flash Test", "hasFlashvalue is test ACtionable insde else=");
            flashTestData = "{}";
            flashTestinfo = -1;
        }
        // Check for torch

        if (flashTestinfo != 0 && flashTestinfo != 1 && flashTestinfo != -1 && flashTestinfo != -2) {
            Log.info("Flash Test", "hasFlashvalue is test ACtionable insde if and value isflashTestinfo != 0 && flashTestinfo != 1 && flashTestinfo != -1 && flashTestinfo != -2 =");
            index++;
            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestTorch;
        } else {
            Log.info("Flash Test", "hasFlashvalue is test ACtionable insde else and value isflashTestinfo != 0 && flashTestinfo != 1 && flashTestinfo != -1 && flashTestinfo != -2 =");
            torchTestData = "{}";
            torchTestInfo = flashTestinfo;
        }

        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestVibrator;

        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestScreen;


        // Check for power key
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestPowerKey;

        // Check for volume up key
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestVolumeUpKey;

        // Check for volume down key
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestVolumeDwnKey;

        // Check for home key
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestHomeKey;

        // Check for back key
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestBackKey;
        //   Check for microphone
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestMic;

        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestLoudSpeaker;

        testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestFrontSpeaker, getApplicationContext(), TestActivity.this, TestActivity.this);
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestFrontSpeaker;

        //Check for ear piece
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestEarPiece;
        //   Check for loudspeaker


        // testBase=TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestFrontSpeaker,getApplicationContext(),TestActivity.this,TestActivity.this);
        String savedvalues = CommSharedPreff.loadStringSavedPreferencesautovalue("PinValueAuto", getApplicationContext());
        if (savedvalues.equalsIgnoreCase("autopin")) {
            cameraAutofocusTestData = "{}";
            cameraAutofocusTestInfo = 1;
        } else {
            // Check for auto focus
            index++;
            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestAutoFocus;
        }

        // Check for Flip action
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestFlipAction;

        // Check for Accelerometer
        // mFlipTestObj = new FlipTest(getApplicationContext(), TestActivity.this);
        testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestFlipAction, getApplicationContext(), TestActivity.this, TestActivity.this);
        if (testBase.isTestActionable(ValueEnumConstants.DeviceTestType.ETestAccelerometer)) {
            Log.info("Actionable Accelerometer");
            index++;
            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestAccelerometer;
        } else {
            accelerometerTestData = "{}";
            accelerometerTestInfo = -1;
        }
        // Check for Gyroscope
        if (testBase.isTestActionable(ValueEnumConstants.DeviceTestType.ETestGyroscope)) {
            Log.info("Actionable Gyro");
            index++;
            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestGyroscope;
        } else {
            gyroscopeTestInfo = -1;
            gyroscopeTestData = "{}";
        }

        // Check for proximity key
        // mProximityTestObj = new ProximityTest(getApplicationContext(), TestActivity.this);
        if (testBase != null) {
            Log.info("Actionable Proximity");
            index++;
            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestProximity;
        } else {
            proximityTestData = "{}";
            proximityTestInfo = -1;
        }

        // Check for video recording key
        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestVideoRecord;

        // Check for FrontVideoRecordTest

        index++;
        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestFrontVideoRecorder;
        // Check for usb

        String savedusbvalues = CommSharedPreff.loadStringSavedPreferencespin("USBValue", getApplicationContext());

        if (savedusbvalues == "false") {
            // Toast.makeText(getApplicationContext(), savedusbvalues, Toast.LENGTH_SHORT).show();
            index++;
            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestUsb;
        } else {
            usbTestInfo = 1;
        }


        mTestOrder = new ValueEnumConstants.DeviceTestType[index + 1];
        //  new multiTest(mTestOrder).execute();
        for (int i = 0; i <= index; i++) {
            mTestOrder[i] = tempOrder[i];
        }

        // start test flow work
        if (getIntent().hasExtra("data")) {
            startTestWorkflow((ValueEnumConstants.DeviceTestType[]) getIntent().getSerializableExtra("data"));
        } else {
            startTestWorkflow(mTestOrder);
        }
    }

    // providing test order to perform
    public void startTestWorkflow(ValueEnumConstants.DeviceTestType... order) {
        currentTestIndex = 0;
        totalTests = order.length;
        mTestOrder = order;

        // perform next test to be performed
        performNextTest();
    }

    // handle callback of tests performed
    public void resultStatus(ValueEnumConstants.DeviceTestType type, ValueEnumConstants.ResultTypeValue result) {

        Log.info(" inside Resultstatus manual=" + type + "value=" + result);
        ValueEnumConstants.DeviceTestType cType = mTestOrder[currentTestIndex - 1];
        if (cType == type) {
            // proceed
        } else {
            return;
        }

        switch (type) {
            case ETestNetwork1: {
                // check network condition
                if (testBaseautomated != null) {
                    networkTestInfo1 = result.getResultValue();
                    // networkTestData1 = result.getResultData();

                    networkTestData1 = result.getResultData().toString();

                    if(networkTestInfo1==0)
                    {
                        resultmap.put("Network Signal SIM 1",networkTestInfo1);
                    }

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                    requestCheck = false;
                }

                break;
            }
            case ETestNetwork2: {
                // check network condition
                if (testBaseautomated != null) {
                    if (callTestInfoSim2 == 1 || smsTestInfoSim2 == 1) {
                        networkTestInfo2 = 1;
                    } else {
                        networkTestInfo2 = 0;
                    }

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                    requestCheck = false;
                }

                break;
            }
            case ETestCall1: {
                // check sim1 for call condition
                if (testBase != null) {
                    callTestInfoSim1 = result.getResultValue();
                    callTestDataSim1 = result.getResultData();

                    if(callTestInfoSim1==0)
                    {
                        resultmap.put("Call SIM 1",callTestInfoSim1);
                    }

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestCall2: {
                // check sim2 for call condition
                if (testBase != null) {
                    callTestInfoSim2 = result.getResultValue();
                    callTestDataSim2 = result.getResultData();

                    if(callTestInfoSim2==0)
                    {
                        resultmap.put("Call SIM 2",callTestInfoSim2);
                    }

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }

            case ETestFrontVideoRecorder: {
                // check wifi condition
                if (testBase != null) {
                    frontVideoRecoderTest = result.getResultValue();
                    frontVideoRecorderTestData = result.getResultData();

                    /*if (USBReceiver.isConnected) {
                        //Log.info("Inside USB Test2=" + savedvalues);
                        usbTestInfo=1;
                    }
                    else {
                        usbTestInfo=0;
                    }*/
                    if(frontVideoRecoderTest==0)
                    {
                        resultmap.put("Front Video Recording",frontVideoRecoderTest);
                    }

                    testBase.endTest();
                    // frameLayout.setVisibility(View.GONE);
                    // cViewVideoFront.setVisibility(View.GONE);
                    testBase = null;
                }

                break;
            }

            case ETestWifi: {
                // check wifi condition
                if (testBaseautomated != null) {
                    wifiTestInfo = result.getResultValue();
                    //wifiTestData = result.getResultData();
                    wifiTestData = result.getResultData().toString();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }


            case ETestImei: {
                // check imei condition
                if (testBaseautomated != null) {
                    imeiTestInfo = result.getResultValue();
                    imeiTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestRooted: {
                // check rooted condition
                if (testBaseautomated != null) {
                    rootTestInfo = result.getResultValue();
                    rootTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestSim1: {
                // check sim condition
                if (testBaseautomated != null) {
                    simTestInfo1 = result.getResultValue();
                    simTestData1 = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestSim2: {
                // check sim condition
                if (testBaseautomated != null) {
                    if (callTestInfoSim2 == 1 || smsTestInfoSim2 == 1) {
                        simTestInfo2 = 1;
                    } else {
                        simTestInfo2 = 0;
                    }

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestRearCamera: {
                // check rear camera condition
                if (testBase != null) {
                    cameraRearTestInfo = result.getResultValue();
                    cameraRearTestData = result.getResultData();

                    if(cameraRearTestInfo==0)
                    {
                        resultmap.put("Back Camera",cameraRearTestInfo);
                    }

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestFrontCamera: {
                // check front camera condition
                if (testBase != null) {
                    cameraFrontTestInfo = result.getResultValue();
                    cameraFrontTestData = result.getResultData();

                    if(cameraFrontTestInfo==0)
                    {
                        resultmap.put("Front Camera",cameraFrontTestInfo);
                    }

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestFlash: {
                // check rear camera condition
                if (testBase != null) {

                    flashTestinfo = result.getResultValue();
                    flashTestData = result.getResultData();

                    torchTestInfo = result.getResultValue();
                    torchTestData = result.getResultData();
                    if(flashTestinfo==0)
                    {
                        resultmap.put("Flash Light",flashTestinfo);
                    }

                    if(torchTestInfo==0)
                    {
                        resultmap.put("Torch Light",torchTestInfo);
                    }


                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestTorch: {
                // check torch condition
                if (testBase != null) {
                    torchTestInfo = result.getResultValue();
                    torchTestData = result.getResultData();

                   /* torchTestInfo = flashTestinfo;
                    torchTestData = flashTestData;*/

                    if(torchTestInfo==0)
                    {
                        resultmap.put("Torch Light",torchTestInfo);
                    }

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestBattery: {
                // check battery condition


                break;
            }
            case ETestVibrator: {
                // check vibrator condition
                if (testBase != null) {
                    Log.info("Vibration test result=" + result);
                    vibratorTestInfo = result.getResultValue();
                    vibratorTestData = "{}";

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestRam: {
                // check ram condition
                if (testBaseautomated != null) {
                    ramTestInfo = result.getResultValue();
                    ramTestData = result.getResultData();
                    // ramTestData = mRamTestObj.getResultDatas().toString();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestStorageInternal: {
                // check stoarge internal condition
                if (testBaseautomated != null) {
                    internalStorageTestInfo = result.getResultValue();
                    internalStorageTestData = result.getResultData();

                    //internalStorageTestData = mStorageTestObj.getResultDatasInternal().toString();
                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestStorageExternal: {
                // check stoarge external condition
                if (testBaseautomated != null) {
                    externalStorageTestInfo = result.getResultValue();
                    externalStorageTestData = result.getResultData();
                    //externalStorageTestData = mStorageTestObj.getResultDatas().toString();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestS_PENHovering: {
                // check s_pen hovering
                spenhoveringtestinfo = result.getResultValue();
                spenHoveringTestData = result.getResultData();

                break;
            }
            case ETestLight: {
                // check light condition
                if (testBaseautomated != null) {
                    lightTestInfo = result.getResultValue();
                    lightTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestMagnetic: {
                // check magnetic condition
                if (testBaseautomated != null) {
                    magneticTestInfo = result.getResultValue();
                    magneticTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestScreen: {
                // check screen condition
                screenTestInfo = result.getResultValue();
                screenTestData = result.getResultData();

                brightnessTestInfo = 1;
                pixelTestInfo = 1;

                break;
            }
            case ETestBluetooth: {
                // check bluetooth condition
                if (testBaseautomated != null) {
                    btTestInfo = result.getResultValue();
                    btTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }

            case ETestEarPiece: {
                // check ear piece condition
                if (testBase != null) {
                    earPieceTestInfo = result.getResultValue();
                    earPieceTestData = result.getResultData();
                    AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                    System.out.println("Audiovalue=" + audioManager.isWiredHeadsetOn());
                    if (earPieceTestInfo == 0 && !audioManager.isWiredHeadsetOn()) {
                        jackTestInfo = 0;
                    }
                    /*if(earPieceTestInfo==0&&)
                    {
                        jackTestInfo=0;
                    }*/

                    if (testBase != null && testBase.getForMicConfirm()) {
                        micTestInfo = 1;
                        micTestData = "{}";
                    }

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestMic: {
                // check micrphone condition
                if (testBase != null) {
                    micTestInfo = result.getResultValue();
                    micTestData = result.getResultData();

                    if (micTestInfo == 1) {
                        loudSpeakerTestInfo = 1;
                        loudSpeakerTestData = "{}";
                        currentTestIndex++;
                    }

                    if(micTestInfo==0)
                    {
                        resultmap.put("Microphone",micTestInfo);
                    }

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestLoudSpeaker: {
                // check loudspeaker condition
                if (testBase != null) {
                    loudSpeakerTestInfo = result.getResultValue();
                    loudSpeakerTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestAutoFocus: {
                // check auto focus condition
                if (testBase != null) {
                    cameraAutofocusTestInfo = result.getResultValue();
                    System.out.println("inside autofocus=" + cameraAutofocusTestInfo);
                    // cameraAutofocusTestData = result.getResultData();
                    cameraAutofocusTestData = "{}";

                    testBase.endTest();
                    // testBase = null;
                }

                break;
            }
            case ETestPowerKey: {
                // check power key condition
                if (testBase != null) {
                    powerKeyTestInfo = result.getResultValue();
                    powerKeyTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestVolumeDwnKey: {
                // check volume down condition
                if (testBase != null) {
                    volumeKeyDownTestInfo = result.getResultValue();
                    volumeKeyDownTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestVolumeUpKey: {
                // check volume up condition
                if (testBase != null) {
                    volumeKeyUpTestInfo = result.getResultValue();
                    volumeKeyUpTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestHomeKey: {
                // check home condition
                if (testBase != null) {
                    homeKeyTestInfo = result.getResultValue();
                    homeKeyTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestBackKey: {
                // check back condition
                if (testBase != null) {
                    backKeyTestInfo = result.getResultValue();
                    backKeyTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestMenuKey: {
                // check menu condition
                if (testBase != null) {
                    menuKeyTestInfo = result.getResultValue();
                    menuKeyTestData = result.getResultData();
                    Log.info("Menu key info=" + menuKeyTestInfo + "Menu key test data=" + menuKeyTestData);

                    testBase.endTest();
                    // testBase = null;
                }

                break;
            }
            case ETestFlipAction: {
                // check flip condition

                if (testBase != null) {
                    if (isRetryflip) {
                        flipactiontest = 0;
                        testBase.endTest();
                        testBase = null;
                    } else {
                        flipactiontest = result.getResultValue();
                        testBase.endTest();
                        testBase = null;
                    }
                }

                break;
            }
            case ETestAccelerometer: {
                // check accelerometer condition

                Log.info("Accelerometer test info=" + flipactiontest + "accelerometerdata=" + result.getResultValue());
                if (testBase != null && flipactiontest == -2) {
                    accelerometerTestInfo = -2;
                    accelerometerTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                } else if (testBase != null && flipactiontest == 0 && result.getResultValue() == 0) {
                    accelerometerTestInfo = 0;
                    accelerometerTestData = result.getResultData();
                } else {
                    accelerometerTestInfo = result.getResultValue();
                    accelerometerTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestGyroscope: {
                // check gyroscope condition
                if (testBase != null && flipactiontest == -2) {
                    gyroscopeTestInfo = -2;
                    gyroscopeTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                } else if (testBase != null && flipactiontest == 0 && result.getResultValue() == 0) {
                    gyroscopeTestInfo = 0;
                    gyroscopeTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                } else {
                    gyroscopeTestInfo = result.getResultValue();
                    gyroscopeTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }

            case ETestProximity: {
                // check proximity condition
                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestProximity, getApplicationContext(), TestActivity.this, TestActivity.this);
                if (testBase != null) {
                    System.out.println("sensor is listening  14....." + testBase);
                    proximityTestInfo = result.getResultValue();
                    System.out.println("sensor is listening  15....." + proximityTestInfo);
                    proximityTestData = result.getResultData();
                    proximityTestData = "{}";
                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestVideoRecord: {
                Log.info(" inside video test=" + testBase);
                // check video recording condition
                if (testBase != null) {
                    Log.info(" inside video test1=");
                    videoTestInfo = result.getResultValue();
                    Log.info(" inside video test2=" + videoTestInfo);
                    videoTestData = result.getResultData();
                    Log.info(" inside video tes3=" + videoTestData);

                    testBase.endTest();
                    testBase = null;
                }
                break;
            }
            case ETestFrontSpeaker: {
                // check video recording condition
                if (testBase != null) {
                    frontSpeakerTestInfo = result.getResultValue();
                    Log.info("Test inside fronspeaker=" + frontSpeakerTestInfo);
                    frontSpeakerTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }

            case ETestUsb: {
                // check usb condition
                if (testBase != null) {
                    Log.info("Inside USB Test00=" + usbTestInfo);

                    usbTestInfo = result.getResultValue();
                    Log.info("Inside USB Test=" + usbTestInfo);
                    // usbTestData = result.getResultData();

                    usbTestData = "{}";
                    Log.info("Inside USB Test1=" + usbTestInfo);

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }

            case ETestAirPressure: {
                // check air pressure condition
                if (testBaseautomated != null) {
                    airPressureTestInfo = result.getResultValue();
                    airPressureTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestAirTemperature: {
                // check air temperature condition
                if (testBaseautomated != null) {
                    airTemperatureTestInfo = result.getResultValue();
                    airTemperatureTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestGravity: {
                // check gravity condition
                if (testBaseautomated != null) {
                    gravityTestInfo = result.getResultValue();
                    gravityTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestIRTest: {
                // check infrared condition
                if (testBaseautomated != null) {
                    irTestInfo = result.getResultValue();
                    irTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestNFC: {
                // check NFC condition
                if (testBase != null) {
                    nfcTestInfo = result.getResultValue();
                    nfcTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestGyroscopeGaming: {
                // check gyroscope gaming condition
                if (testBaseautomated != null) {
                    gyroscopeForGamingTestInfo = gyroscopeTestInfo;
                    gyroscopeForGamingTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestOrientation: {
                // check orientation gaming condition
                if (testBaseautomated != null) {
                    orientationTestInfo = result.getResultValue();
                    orientationTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestDeviceTemperature: {
                // check device temperature condition
                if (testBaseautomated != null) {
                    deviceTempTestInfo = result.getResultValue();
                    deviceTempTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestHumidity: {
                // check humidity condition
                if (testBaseautomated != null) {
                    humidityTestInfo = result.getResultValue();
                    humidityTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestBiometric: {
                // check biometric condition
                if (testBaseautomated != null) {
                    biomatricTestInfo = result.getResultValue();
                    biomatricTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestHRM: {
                // check hrm condition
                if (testBaseautomated != null) {
                    hrmTestInfo = result.getResultValue();
                    hrmTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestSPO2: {
                // check spo2 condition
                if (testBaseautomated != null) {
                    SPO2TestInfo = hrmTestInfo;
                    SPO2TestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestScreenLock: {
                // check screen lock condition
                if (testBase != null) {
                    screenLockTestInfo = result.getResultValue();
                    screenLockTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestLED: {
                // check led condition
                Log.info("Led test data=" + result.getResultValue());
                if (testBase != null) {
                    Log.info("Led test data2=" + result.getResultValue());
                    ledNotificationInfo = result.getResultValue();
                    Log.info("Led test data3=" + ledNotificationInfo);
                    ledNotificationData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestUvSensor: {
                // check uv condition
                if (testBaseautomated != null) {
                    uvSensorTestInfo = result.getResultValue();
                    uvSensorTestData = result.getResultData();

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                    requestCheck = true;
                }

                break;
            }
            case ETestSwingTest: {
                if (testBase != null) {



                    if (testBase != null) {
                        if (isRetrySwing) {
                            // swingactiontest = 0;
                            swingactiontest = result.getResultValue();
                            testBase.endTest();
                            testBase = null;
                        } else {
                            swingactiontest = result.getResultValue();
                            testBase.endTest();
                            testBase = null;
                        }
                    }


                }

                break;
            }
            case ETestStepDetector: {
                // check uv condition
                if (testBase != null) {
                    stepDetectorResult = testBase.getStepDetectorValue();
                    /*stepDetectorTestData = result.getResultData();

                    mStepDetectorTestobj.endTest();
                    mStepDetectorTestobj = null;*/
                }
                Log.info(" inside stepdetectorvalue=" + testBase + "Swingactiobtestvalue=" + swingactiontest + "Resultgetvalue=" + result.getResultValue());
                if (testBase != null && swingactiontest == -2) {
                    stepDetectorTestInfo = -2;
                    stepDetectorTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                } else if (testBase != null && swingactiontest == 0) {
                    stepDetectorTestInfo = 0;
                    stepDetectorTestData = result.getResultData();
                } else if (testBase != null && swingactiontest == -1) {
                    stepDetectorTestInfo = -1;
                } else {
                    stepDetectorTestInfo = result.getResultValue();
                    stepDetectorTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestStepCounter: {
                // check uv condition
                if (testBase != null) {
                    stepCounterResult = testBase.getStepCounterValue();
                    /*stepCounterTestData = result.getResultData();

                    mStepCounterTestObj.endTest();
                    mStepCounterTestObj = null;*/
                }

                if (testBase != null && swingactiontest == -2) {
                    stepCounterTestInfo = -2;
                    stepCounterTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                } else if (testBase != null && swingactiontest == 0) {
                    stepCounterTestInfo = 0;
                    stepCounterTestData = result.getResultData();
                } else if (testBase != null && swingactiontest == -1) {
                    stepCounterTestInfo = -1;
                } else {
                    stepCounterTestInfo = result.getResultValue();
                    stepCounterTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
            case ETestMotionDetector: {
                // check uv condition
                if (testBase != null) {
                    motionDetectorResult = testBase.getMotionDetectorValue();
                    /*motionTestData = result.getResultData();

                    mMotionDetectorTest.endTest();
                    mMotionDetectorTest = null;*/
                }

                if (testBase != null && swingactiontest == -2) {
                    motionTestInfo = -2;
                    motionTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                } else if (testBase != null && swingactiontest == 0) {
                    motionTestInfo = 0;
                    motionTestData = result.getResultData();
                } else if (testBase != null && swingactiontest == -1) {
                    motionTestInfo = -1;
                } else {
                    motionTestInfo = result.getResultValue();
                    motionTestData = result.getResultData();

                    testBase.endTest();
                    testBase = null;
                }

                break;
            }
        }

        // do garbage collection
        System.gc();

        // perform next test to be performed
        performNextTest();
    }

    @Override
    public void resultStatusAuto(ValueEnumConstants.DeviceTestType type, ValueEnumConstants.ResultTypeValue result) {

        Log.info(" Inside type=" + type + "Result=" + result.getResultValue() + "Result1=" + result.getResultData());

        switch (type) {
            case ETestNetwork1: {
                // check network condition
                if (testBaseautomated != null) {
                    networkTestInfo1 = result.getResultValue();
                    // networkTestData1 = result.getResultData();

                    networkTestData1 = result.getResultData().toString();
                    if(networkTestInfo1==0)
                    {
                        resultmap.put("Network Signal SIM 1",networkTestInfo1);
                    }

                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                    requestCheck = false;
                }

                break;
            }
            case ETestInternet: {
                // check internet condition
                if (testBaseautomated != null) {
                    internetTestInfo = result.getResultValue();
                    //internetTestData = result.getResultData();

                    internetTestData = result.getResultData().toString();
                    if(internetTestInfo==0)
                    {
                        resultmap.put("Internet",internetTestInfo);
                    }

                    if (testBaseautomated != null) {
                        testBaseautomated.endTest();
                        //   testBase = null;
                    }
                }

                break;
            }
            case ETestSms1: {
                Log.info(" Inside type1=" + smsTestInfoSim1 + "Result=" + smsTestDataSim1);
                //System.out.println("Vikash Inside type2="+mSmsTestObj+"Result="+smsTestDataSim1);
                // check sim1 for sms condition
                if (testBaseautomated != null) {
                    //  System.out.println("Vikash Inside type3="+smsTestInfoSim1+"Result="+mSmsTestObj);
                    smsTestInfoSim1 = result.getResultValue();
                    smsTestDataSim1 = result.getResultData();
                    Log.info(" Inside type4=" + smsTestInfoSim1 + "Result=" + smsTestDataSim1);

                    testBaseautomated.endTest();
                    // testBaseautomated = null;
                }

                break;
            }
            case ETestSms2: {
                Log.info(" Inside type11=" + smsTestInfoSim2 + "Result=" + smsTestDataSim2);
                //System.out.println("Vikash Inside type22="+mSmsTestObjs+"Result="+smsTestDataSim2);
                // check sim2 for sms condition
                if (testBaseautomated != null) {
                    //  System.out.println("Vikash Inside type33="+smsTestInfoSim2+"Result="+mSmsTestObjs);
                    smsTestInfoSim2 = result.getResultValue();
                    smsTestDataSim2 = result.getResultData();
                    Log.info(" Inside type44=" + smsTestInfoSim2 + "Result=" + smsTestDataSim2);

                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                }

                break;
            }
            case ETestSim1: {
                // check sim condition
                if (testBaseautomated != null) {
                    simTestInfo1 = result.getResultValue();
                    simTestData1 = result.getResultData();

                    if(simTestInfo1==0)
                    {
                        resultmap.put("SIM Slot 1",simTestInfo1);
                    }

                    testBaseautomated.endTest();
                    // testBaseautomated = null;
                }

                break;
            }
            case ETestStorageInternal: {
                // check stoarge internal condition
                if (testBaseautomated != null) {
                    internalStorageTestInfo = result.getResultValue();
                    //internalStorageTestData = result.getResultData();

                    internalStorageTestData = result.getResultDataInternal().toString();

                    if(internalStorageTestInfo==0)
                    {
                        resultmap.put("Internal Storage",internalStorageTestInfo);
                    }
                    Log.info("Internal storage=" + internalStorageTestData);
                    //  testBaseautomated.endTest();
                    //mStorageTestObj = null;
                }

                break;
            }
            case ETestStorageExternal: {
                // check stoarge external condition
                if (testBaseautomated != null) {
                    externalStorageTestInfo = result.getResultValue();
                    externalStorageTestData = result.getResultData();
                    //externalStorageTestData = result.getR
                    if(externalStorageTestInfo==0)
                    {
                        resultmap.put("External Storage",externalStorageTestInfo);
                    }

                    testBaseautomated.endTest();
                    // testBaseautomated = null;
                }

                break;
            }



            case ETestUsb: {
                // check usb condition
                if (testBaseautomated != null) {
                    Log.info("Inside USB Test0=" + usbTestInfo);


                    // usbTestInfo = result.getResultValue();
                    Log.info("Inside USB Test=" + usbTestInfo);
                    // usbTestData = result.getResultData();

                    usbTestData = "{}";
                    Log.info("Inside USB Test1=" + usbTestInfo);

                    if(usbTestInfo==0)
                    {
                        resultmap.put("USB",usbTestInfo);
                    }

                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }

            case ETestProximity: {
                // check proximity condition
                // testBase= TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestProximity, getApplicationContext(), TestActivity.this, TestActivity.this);
                if (testBaseautomated != null) {
                    System.out.println("sensor is listening  144....." + testBase);
                    proximityTestInfo = result.getResultValue();
                    System.out.println("sensor is listening  155....." + proximityTestInfo);
                    // proximityTestData = result.getResultData();
                    proximityTestData = "{}";

                    if(proximityTestInfo==0)
                    {
                        resultmap.put("Proximity",proximityTestInfo);
                    }
                    testBaseautomated.endTest();
                    testBaseautomated = null;
                }

                break;
            }
            case ETestWifi: {
                Log.info(" inside wifi value1=");
                // check network condition
                if (testBaseautomated != null) {
                    Log.info(" inside wifi value2=");
                    wifiTestInfo = result.getResultValue();
                    Log.info(" inside wifi value3=" + wifiTestInfo);
                    // networkTestData1 = result.getResultData();

                    wifiTestData = result.getResultData().toString();
                    if (!isinternettest) {
                        isinternettest = !isinternettest;

                        testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestInternet, getApplicationContext(), TestActivity.this, TestActivity.this);
                        testBaseautomated.startTest();

                    }
                    if(wifiTestInfo==0)
                    {
                        resultmap.put("WiFi",wifiTestInfo);
                    }


                    Log.info(" inside wifi value4=" + wifiTestData);
                    testBaseautomated.endTest();
                    // testBaseautomated = null;
                }

                break;
            }
            case ETestImei: {
                // check imei condition
                if (testBaseautomated != null) {
                    imeiTestInfo = result.getResultValue();
                    imeiTestData = result.getResultData();

                    testBaseautomated.endTest();
                    // testBaseautomated = null;
                }
                if(imeiTestInfo==0)
                {
                    resultmap.put("IMEI",imeiTestInfo);
                }

                break;
            }
            case ETestRooted: {
                // check rooted condition
                if (testBaseautomated != null) {
                    rootTestInfo = result.getResultValue();
                    rootTestData = result.getResultData();

                    testBaseautomated.endTest();
                    // testBaseautomated = null;
                }
                if(rootTestInfo==0)
                {
                    resultmap.put("Device Non Rooted",rootTestInfo);
                }

                break;
            }
            case ETestLight: {
                // check light condition
                if (testBaseautomated != null) {
                    lightTestInfo = result.getResultValue();
                    lightTestData = result.getResultData();

                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                }

                if(lightTestInfo==0)
                {
                    resultmap.put("Light Sensor",lightTestInfo);
                }

                break;
            }
            case ETestMagnetic: {
                // check magnetic condition
                if (testBaseautomated != null) {
                    Log.info(" inside magnetic1=" + testBaseautomated);
                    magneticTestInfo = result.getResultValue();
                    Log.info(" inside magnetic2=" + magneticTestInfo);
                    magneticTestData = result.getResultData();
                    Log.info(" inside magnetic3=" + magneticTestData);

                    if(magneticTestInfo==0)
                    {
                        resultmap.put("Magnetic Sensor",magneticTestInfo);
                    }
                    testBaseautomated.endTest();
                    // testBaseautomated = null;
                }

                break;
            }

            case ETestBluetooth: {
                Log.info(" inside bluetooth1=" + testBaseautomated);
                // check bluetooth condition
                if (testBaseautomated != null) {
                    Log.info(" inside bluetooth2=");
                    btTestInfo = result.getResultValue();
                    Log.info(" inside bluetooth3=" + btTestInfo);
                    btTestData = result.getResultData();
                    Log.info("Vikash inside bluetooth4=" + btTestData);

                    if(btTestInfo==0)
                    {
                        resultmap.put("Bluetooth",btTestInfo);
                    }

                    testBaseautomated.endTest();
                    //   testBaseautomated = null;
                }

                break;
            }
            case ETestBattery: {
                Log.info(" inside battery1=" + batteryTestInfo);
                // check battery condition
                if (testBaseautomated != null) {
                    Log.info(" inside battery2=" + batteryTestInfo);
                    batteryTestInfo = result.getResultValue();
                    batteryTestData = result.getResultData();
                    Log.info(" inside battery3=" + batteryTestInfo);
                    Log.info(" inside battery4=" + batteryTestData);
                    // batteryTestData = result.getResultDatas().toString();

                    if(batteryTestInfo==0)
                    {
                        resultmap.put("Battery",batteryTestInfo);
                    }

                    testBaseautomated.endTest();
                    // testBaseautomated = null;
                }

                break;
            }
            case ETestRam: {
                // check ram condition
                if (testBaseautomated != null) {
                    ramTestInfo = result.getResultValue();
                    ramTestData = result.getResultData();
                    // ramTestData = result.getResultDa.toString();

                    if(ramTestInfo==0)
                    {
                        resultmap.put("RAM",ramTestInfo);
                    }

                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                }

                break;
            }
            case ETestGps: {
                // check gps condition
                System.out.println(" inside GPSresult0=" + testBaseautomated);
                if (testBaseautomated != null) {

                    System.out.println(" inside GPSresult1=" + testBaseautomated);
                    gpsTestInfo = result.getResultValue();
                    System.out.println(" inside GPSresult2=" + gpsTestInfo);
                    // gpsTestData = result.getResultData();
                    gpsTestData = result.getResultData().toString();
                    System.out.println(" inside GPSresult3=" + gpsTestData);
                    if(gpsTestInfo==0)
                    {
                        resultmap.put("GPS",gpsTestInfo);
                    }


                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                }

                break;
            }
            case ETestSim2: {
                // check gps condition
                if (testBaseautomated != null) {
                    if (callTestInfoSim2 == 1 || smsTestInfoSim2 == 1) {
                        simTestInfo2 = 1;
                    } else {
                        simTestInfo2 = 0;
                    }
                    if(simTestInfo2==0)
                    {
                        resultmap.put("SIM Slot 2",simTestInfo2);
                    }

                    testBaseautomated.endTest();
                    //   testBaseautomated = null;
                }

                break;
            }
            case ETestNetwork2: {
                // check gps condition
                if (testBaseautomated != null) {
                    if (callTestInfoSim2 == 1 || smsTestInfoSim2 == 1) {
                        networkTestInfo2 = 1;
                    } else {
                        networkTestInfo2 = 0;
                    }
                    if(networkTestInfo2==0)
                    {
                        resultmap.put("Network Signal SIM 2",networkTestInfo2);
                    }

                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                    requestCheck = false;
                }

                break;
            }

            case ETestGravity: {
                // check gravity condition
                if (testBaseautomated != null) {
                    gravityTestInfo = result.getResultValue();
                    gravityTestData = result.getResultData();

                    testBaseautomated.endTest();

                    if(gravityTestInfo==0)
                    {
                        resultmap.put("Gravity",gravityTestInfo);
                    }
                    // testBaseautomated = null;
                }

                break;
            }
            case ETestIRTest: {
                // check infrared condition
                if (testBaseautomated != null) {
                    irTestInfo = result.getResultValue();
                    irTestData = result.getResultData();

                    if(irTestInfo==0)
                    {
                        resultmap.put("Infrared",irTestInfo);
                    }

                    testBaseautomated.endTest();
                    //   testBaseautomated = null;
                }

                break;
            }

            case ETestAirPressure: {
                // check air pressure condition
                if (testBaseautomated != null) {
                    airPressureTestInfo = result.getResultValue();
                    airPressureTestData = result.getResultData();
                    if(airPressureTestInfo==0)
                    {
                        resultmap.put("Air Pressure",airPressureTestInfo);
                    }
                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                }

                break;
            }
            case ETestAirTemperature: {
                // check air temperature condition
                if (testBaseautomated != null) {
                    airTemperatureTestInfo = result.getResultValue();
                    airTemperatureTestData = result.getResultData();

                    if(airTemperatureTestInfo==0)
                    {
                        resultmap.put("Air Temperature",airTemperatureTestInfo);
                    }

                    testBaseautomated.endTest();
                    //   testBaseautomated = null;
                }

                break;
            }

            case ETestGyroscopeGaming: {
                // check gyroscope gaming condition
                if (testBaseautomated != null) {
                    gyroscopeForGamingTestInfo = gyroscopeTestInfo;
                    gyroscopeForGamingTestData = result.getResultData();

                    if(gyroscopeForGamingTestInfo==0)
                    {
                        resultmap.put("Gyroscope Gaming",gyroscopeForGamingTestInfo);
                    }

                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                }

                break;
            }
            case ETestOrientation: {
                // check orientation gaming condition
                if (testBaseautomated != null) {
                    orientationTestInfo = result.getResultValue();
                    orientationTestData = result.getResultData();

                    if(orientationTestInfo==0)
                    {
                        resultmap.put("Orientation",orientationTestInfo);
                    }

                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                }

                break;
            }
            case ETestNFC: {
                // check NFC condition
                if (testBaseautomated != null) {
                    nfcTestInfo = result.getResultValue();
                    nfcTestData = result.getResultData();

                    if(nfcTestInfo==0)
                    {
                        resultmap.put("NFC",nfcTestInfo);
                    }

                    testBaseautomated.endTest();
                    //  testBaseautomated = null;
                }

                break;
            }

            case ETestHumidity: {
                // check humidity condition
                if (testBaseautomated != null) {
                    humidityTestInfo = result.getResultValue();
                    humidityTestData = result.getResultData();

                    if(humidityTestInfo==0)
                    {
                        resultmap.put("HUMIDITY",humidityTestInfo);
                    }

                    testBaseautomated.endTest();
                    //    testBaseautomated = null;
                }

                break;
            }

            case ETestUvSensor: {
                // check uv condition
                if (testBaseautomated != null) {
                    uvSensorTestInfo = result.getResultValue();
                    uvSensorTestData = result.getResultData();

                    if(uvSensorTestInfo==0)
                    {
                        resultmap.put("UV Sensor",uvSensorTestInfo);
                    }
                    testBaseautomated.endTest();
                    //    testBaseautomated = null;
                    requestCheck = true;
                }

                break;
            }

        }
    }

    @Override
    public void serverLicense(ValueEnumConstants.DeviceTestType type, ValueEnumConstants.ServerTypeValue result) {

    }

    @Override
    public void CertificationValue(String strresult, String strurl, String strcertificatenumber, String strmessage) {

    }

    private void preformBackgroundTest() {
        if (!isTestDonenetwork) {
            isTestDonenetwork = !isTestDonenetwork;

            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestNetwork1, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestNetwork1);



        }


        if (isOptionalTestStartedbutton) {
            if (!isTestDonenetwork2) {
                isTestDonenetwork2 = !isTestDonenetwork2;


                testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestNetwork2, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestNetwork2);

            }
        }
           /* if (!isTestDonesms1) {
                isTestDonesms1 = !isTestDonesms1;
                testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSms1, getApplicationContext(), TestActivity.this, TestActivity.this);


                if (ValueEnumConstants.DeviceTestType.ETestSms1 == ValueEnumConstants.DeviceTestType.ETestSms1 && networkTestInfo1 == 0) {
                    // If network 1 failed then show UI and mark failed
                    testBaseautomated.startTestJump(ValueEnumConstants.DeviceTestType.ETestSms1);
                } else {
                    testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestSms1);
                }

            }
            if (!isTestDonesms2) {
                Log.info("SMS-1");
                isTestDonesms2 = !isTestDonesms2;
                Log.info("SMS-2");
                // mSmsTestObjs = new Sms(getApplicationContext(), TestActivity.this);
                testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSms2, getApplicationContext(), TestActivity.this, TestActivity.this);
                Log.info("SMS-3");
                if (ValueEnumConstants.DeviceTestType.ETestSms2 == ValueEnumConstants.DeviceTestType.ETestSms2 && networkTestInfo2 == 0) {
                    Log.info("SMS-4");
                    // If network 1 failed then show UI and mark failed
                    testBaseautomated.startTestJump(ValueEnumConstants.DeviceTestType.ETestSms2);
                    Log.info("SMS-5");
                } else {
                    Log.info("SMS-6");
                    testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestSms2);
                    Log.info("SMS-7");
                }

            }*/
        if (!isTestDonewifi) {
            isTestDonewifi = !isTestDonewifi;


            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestWifi, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestWifi);
        }

        if (!isTestDoneimei) {
            isTestDoneimei = !isTestDoneimei;


            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestImei, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestImei);
        }

        if (!isTestDonerooted) {
            isTestDonerooted = !isTestDonerooted;




            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestRooted, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestRooted);
        }

        if (!isTestDoneBattery) {
            isTestDoneBattery = !isTestDoneBattery;



            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestBattery, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest();
        }

        if (!isTestDoneRam) {
            isTestDoneRam = !isTestDoneRam;



            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestRam, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest();
        }

        if (!isTestDoneLight) {
            isTestDoneLight = !isTestDoneLight;


            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestLight, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest();
        }


        if (!isTestDoneBluetooth) {
            isTestDoneBluetooth = !isTestDoneBluetooth;

            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestBluetooth, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest();
        }


        if (!isTestDonestorageinternal) {
            isTestDonestorageinternal = !isTestDonestorageinternal;

            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestStorageInternal, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestStorageInternal);


        }

        if (!isTestDonestorageexternal) {
            isTestDonestorageexternal = !isTestDonestorageexternal;

            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestStorageExternal, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestStorageExternal);


        }

        if (!isTestDonesim1) {
            isTestDonesim1 = !isTestDonesim1;


            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSim1, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest();
        }
        if (isOptionalTestStartedbutton) {
            if (!isTestDonesim2) {
                isTestDonesim2 = !isTestDonesim2;


                testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSim2, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBaseautomated.jumpTest();
            }
        }


        if (!isTestDoneAirpressure && isOptionalTestStartedbutton) {
            isTestDoneAirpressure = !isTestDoneAirpressure;
            // mAirPressureTestObj = new AirPressureTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestAirPressure, getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestAirPressure)) {
                Log.info("Actionable Airpressure");
                // Check for air pressure
                testBaseautomated.startTest();
            } else {
                airPressureTestInfo = -1;
                airPressureTestData = "{}";
            }

        }

        if (!isTestDoneAirtemperature && isOptionalTestStartedbutton) {
            // mAirTemperatureTestObj = new AirTemperatureTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestAirTemperature, getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestAirTemperature)) {
                Log.info("Actionable Airtemperature");
                testBaseautomated.startTest();
            } else {
                airTemperatureTestInfo = -1;
                airTemperatureTestData = "{}";
            }

        }

        if (!isTestDonegravity && isOptionalTestStartedbutton) {
            isTestDonegravity = !isTestDonegravity;
            // mGravityTestObj = new GravityTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestGravity, getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestGravity)) {
                Log.info("Actionable Gravity");
//                                    // Check for gravity
                testBaseautomated.startTest();
            } else {
                gravityTestData = "{}";
                gravityTestInfo = -1;
            }

        }
        if (!isTestDoneinfrared && isOptionalTestStartedbutton) {
            isTestDoneinfrared = !isTestDoneinfrared;
            //  mIrTestObj = new IRTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestIRTest, getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestIRTest)) {
                Log.info("Actionable Infrared");
                testBaseautomated.startTest();
            } else {
                irTestData = "{}";
                irTestInfo = -1;
            }

        }
        if (!isTestDonedevicetemperature && isOptionalTestStartedbutton) {
            isTestDonedevicetemperature = !isTestDonedevicetemperature;
            // mTemperatureTestObj = new DeviceTemperatureTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestDeviceTemperature, getApplicationContext(), TestActivity.this, TestActivity.this);
//
//                                    // Check for device temperature
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestDeviceTemperature)) {
                Log.info("Actionable Devicetemperature");
                testBaseautomated.startTest();
            } else {
                deviceTempTestData = "{}";
                deviceTempTestInfo = -1;
            }

        }
        if (!isTestDonehumidity && isOptionalTestStartedbutton) {
            isTestDonehumidity = !isTestDonehumidity;
            // mHumidityTestObj = new HumidityTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestHumidity, getApplicationContext(), TestActivity.this, TestActivity.this);
//
//                                    // Check for humidity
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestHumidity)) {
                Log.info("Actionable Humidity");
                testBaseautomated.startTest();
            } else {
                humidityTestData = "{}";
                humidityTestInfo = -1;
            }


        }

        if (!isnfctest && isOptionalTestStartedbutton) {
            isnfctest = !isnfctest;
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestNFC, getApplicationContext(), TestActivity.this, TestActivity.this);
            // mNfcTestObj = new NFCTest(getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestNFC)) {
                if (testBaseautomated.isTestSettingsDone()) {
                    testBaseautomated.startTest("auto");
                    nfcenabled = true;

                }
            }

        }
        if (!isTestDoneUV && isOptionalTestStartedbutton) {
            isTestDoneUV = !isTestDoneUV;
            // mUvTestObj = new UvSensorsTest(getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestUvSensor, getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestUvSensor)) {
                testBaseautomated.startTest();
            } else {
                uvSensorTestData = "{}";
                uvSensorTestInfo = -1;
            }

        }

        if (!isgyrogametest && isOptionalTestStartedbutton) {
            isgyrogametest = !isgyrogametest;
            // mGyroGameTestObj = new GyroscopeGamingTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestGyroscopeGaming, getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestGyroscopeGaming)) {
                testBaseautomated.startTest();
            } else {
                gyroscopeForGamingTestData = "{}";
                gyroscopeForGamingTestInfo = -1;

            }

        }

        if (!isorientationgametest && isOptionalTestStartedbutton) {
            isorientationgametest = !isorientationgametest;
            // mOrientGameTestObj = new OrientationGamingTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestOrientation, getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestOrientation)) {
                testBaseautomated.startTest();
            } else {
                orientationTestData = "{}";
                orientationTestInfo = -1;

            }

        }
        if (!isTestDoneGPS && isOptionalTestStartedbutton) {
            isTestDoneGPS = !isTestDoneGPS;
            //  mGpsTestObj = new GpsTest();
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestGps, getApplicationContext(), TestActivity.this, TestActivity.this);
            testBaseautomated.startTest();

        }
        if (!isTestDoneMagnet) {
            // mMagneticTestObj = new MagneticTest(getApplicationContext(), TestActivity.this);
            testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestMagnetic, getApplicationContext(), TestActivity.this, TestActivity.this);
            if (testBaseautomated.isTestActionable(ValueEnumConstants.DeviceTestType.ETestMagnetic)) {
                isTestDoneMagnet = !isTestDoneMagnet;
                testBaseautomated.startTest();
            } else {
                magneticTestData = "{}";
                magneticTestInfo = -1;
            }
        }

    }

    // common function to perform next test in order
    private void performNextTest() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    playimage.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // start first test in list
        currentTestIndex++;

        // Check currentTestIntex first to stop tests
        if ((currentTestIndex > totalTests)) {
            // Do end test perform
            if (checkBGTestDone()) {
                Log.info("Inside USB Test5=" + usbTestInfo);
                endDeviceTests();
                return;
            } else {
                checkBGTestDone();
                try {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TestActivity.this);
                    alertDialog.setTitle("Automated Test");
                    alertDialog.setMessage("Automated test is running in background Please wait ...");
                    alertDialog.setCancelable(false);


                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // resultStatus(mConfirmDlgTestType, ValueEnumConstants.ResultTypeValue.EResultPass);

                        }
                    });



                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }

        preformBackgroundTest();

        // Fetch current test type to be performed
        ValueEnumConstants.DeviceTestType testType = mTestOrder[currentTestIndex - 1];

        Log.info("TestActivity peformNext Test : " + currentTestIndex + " , " + testType);

        if (testType == ValueEnumConstants.DeviceTestType.ETestProximity && proximityTestInfo == 1) {

            currentTestIndex++;
            testType = mTestOrder[currentTestIndex - 1];
            Log.info("TestActivity peformNext Test 1 : " + currentTestIndex + " , " + testType);
        }


        // Update test ui as per type
        updateTestUI(testType);

        // Perform test order of current type
        switch (testType) {
//            case ETestNetwork1:
            // start network sim1-sim2 test

//                break;
            case ETestNetwork2: {

                TestBase tb = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestNetwork2, getApplicationContext(), TestActivity.this, TestActivity.this);
                tb.startTest(testType);

                break;
            }
            case ETestCall1:
            case ETestCall2: {
                // start call sim1-sim2 test

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestCall1, getApplicationContext(), TestActivity.this, TestActivity.this);
                // mCallTestObj = new CallTest(getApplicationContext(), TestActivity.this, TestActivity.this);

                if (testType == ValueEnumConstants.DeviceTestType.ETestCall1 && networkTestInfo1 == 0) {
                    // If network 1 failed then show UI and mark failed
                    testBase.startTestJump(testType);
                } else {
                    testBase.startTest(testType,"8001240151");
                }

                break;
            }

            case ETestS_PENHovering: {

                spenIntent = new Intent(getApplicationContext(), S_PenHoveringActivity.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TestActivity.this);
                            alertDialog.setTitle("Stylus Hovering Test");
                            alertDialog.setMessage("Do you have stylus with your mobile?");
                            alertDialog.setCancelable(false);


                            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // resultStatus(mConfirmDlgTestType, ValueEnumConstants.ResultTypeValue.EResultPass);


                                    spenIntent.putExtra("spenkey", "yes");
                                    startActivityForResult(spenIntent, 45);
                                }
                            });

                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //  resultStatus(mConfirmDlgTestType, ValueEnumConstants.ResultTypeValue.EResultFail);

                                    spenIntent.putExtra("spenkey", "no");
                                    startActivityForResult(spenIntent, 45);

                                }
                            });

                            alertDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                break;
            }
            case ETestFrontVideoRecorder: {
                // start video test
                FrameLayout viewHolder = (FrameLayout) TestActivity.this.findViewById(R.id.cameraframelayout);
                viewHolder.setVisibility(View.VISIBLE);


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestFrontVideoRecorder, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.setFrameLayout(viewHolder);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestFrontVideoRecorder);

                break;
            }
            case ETestGps: {
                // start gps test
                //System.out.println("Vikash inside case GPS");
                testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestGps, getApplicationContext(), TestActivity.this, TestActivity.this);
                // mGpsTestObj = new GpsTest(getApplicationContext(), TestActivity.this, TestActivity.this);
                testBaseautomated.startTest();

                break;
            }

            case ETestImei: {
                // start imei test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestImei, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestImei);

                break;
            }
            case ETestRooted: {
                // start rooted test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestRooted, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestRooted);

                break;
            }
            case ETestSim1: {
                // start sim test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSim1, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestSim1);

                break;
            }
            case ETestSim2: {
                // start sim test


                testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSim2, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBaseautomated.jumpTest();

                break;
            }
            case ETestRearCamera:
            case ETestFrontCamera: {
                // start video test
                FrameLayout viewHolder = (FrameLayout) TestActivity.this.findViewById(R.id.cameraframelayout);
                viewHolder.setVisibility(View.VISIBLE);




                testBase = TestBase.getInstance(testType, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.setFrameLayout(viewHolder);
                testBase.startTest(testType);

                break;
            }
            case ETestFlash: {
                // start rear camera test



                viewHolders = (FrameLayout) TestActivity.this.findViewById(R.id.cameraframelayout);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHolders.setVisibility(View.VISIBLE);
                    }
                });
              //  viewHolders.setVisibility(View.VISIBLE);

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestFlash, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.setFrameLayout(viewHolders);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestFlash);

                break;
            }
            case ETestTorch: {
                // start torch test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestTorch, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestTorch);
                Log.info("Test Activity", "Torch test.starttest method called");

                break;
            }
            case ETestBattery: {
                // start battery test


                break;
            }
            case ETestVibrator: {
                // start vibrator test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestVibrator, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();
                break;
            }
            case ETestRam: {
                // start ram test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestRam, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestRam);


                break;
            }
            case ETestStorageInternal:
            case ETestStorageExternal: {
                // start stoarge test

                testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestStorageInternal, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBaseautomated.startTest(ValueEnumConstants.DeviceTestType.ETestStorageInternal);

                break;
            }
            case ETestLight: {
                // start light test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestLight, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestLight);

                break;
            }
            case ETestMagnetic: {
                // start magnetic test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestMagnetic, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestMagnetic);

                break;
            }
            case ETestScreen: {
                // start screen test
                TestBase testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestScreen, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestScreen);

                break;
            }

            case ETestBluetooth: {
                // start bluetooth test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestBluetooth, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestBluetooth);

                break;
            }
            case ETestHeadJack: {
                // start headjack test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestHeadJack, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();

                break;
            }
            case ETestEarPiece: {
                // start ear piece test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestEarPiece, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.setsoundfile(R.raw.mp3_1khz);
                testBase.startTest();

                break;
            }
            case ETestMic: {
                // start microphone test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestMic, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(testType);

                break;
            }
            case ETestLoudSpeaker: {
                // start loudspeaker test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestLoudSpeaker, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.setsoundfile(R.raw.mp3_1khz);
                testBase.loudSpeakertest();

                break;
            }
            case ETestAutoFocus: {
                // start auto focus test
                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestAutoFocus, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.setSurafceView(surfaceView);
                testBase.startTest();


                break;
            }
            case ETestPowerKey: {
                // start power key test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestPowerKey, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();

                break;
            }
            case ETestVolumeDwnKey:
            case ETestVolumeUpKey:
            case ETestHomeKey:
            case ETestBackKey:
            case ETestMenuKey: {
                // start volume down-up-home-back-menu key test


                testBase = TestBase.getInstance(testType, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.setclass(TestActivity.class);
                testBase.startTest(testType);

                break;
            }
            case ETestFlipAction:
            case ETestAccelerometer:
            case ETestGyroscope: {
                // start volume flip-acclerometer-gyroscope key test

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestFlipAction, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(testType);


                break;
            }
            case ETestProximity: {
                // start proximity test

                System.out.println("Vikash inside proximity test info=" + proximityTestInfo);

                System.out.println("Vikash inside proximity test info1=" + proximityTestInfo);
                // if(proximityTestInfo==0||proximityTestInfo==-1) {
                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestProximity, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestProximity);
                // }

                break;
            }
            case ETestVideoRecord: {
                // start video test
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FrameLayout viewHolder = (FrameLayout) TestActivity.this.findViewById(R.id.cameraframelayout);
                        viewHolder.setVisibility(View.VISIBLE);
                        testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestVideoRecord, getApplicationContext(), TestActivity.this, TestActivity.this);
                        testBase.setFrameLayout(viewHolder);
                        testBase.startTest(ValueEnumConstants.DeviceTestType.ETestVideoRecord);
//stuff that updates ui

                    }
                });




                break;
            }
            case ETestFrontSpeaker: {


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestFrontSpeaker, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(R.raw.mp3_1khz);

                break;

            }
            case ETestUsb: {
                // start usb test


                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestUsb, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest(ValueEnumConstants.DeviceTestType.ETestUsb);

                break;
            }
            case ETestAirPressure: {
                // start air pressure test


                break;
            }
            case ETestAirTemperature: {
                // start air temperature test


                break;
            }
            case ETestGravity: {
                // start gravity test


                break;
            }
            case ETestIRTest: {
                // start infrared test


                break;
            }
            case ETestNFC: {
                // start NFC gaming


                testBaseautomated = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestNFC, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBaseautomated.startTest("manual");
                break;
            }
            case ETestGyroscopeGaming: {
                // start gyroscope gaming


                break;
            }
            case ETestOrientation: {
                // start orientation gaming


                break;
            }
            case ETestDeviceTemperature: {
                // start device temperature


                break;
            }
            case ETestHumidity: {
                // start humidity


                break;
            }
            case ETestBiometric: {
                // start biometric

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestBiometric, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();

                break;
            }
            case ETestHRM: {
                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestHRM, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.setSensor(HeartRateSensorManager);
                testBase.startTest1();
                // start hrm


                break;
            }
            case ETestSPO2: {

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSPO2, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();
                // start spo2


                break;
            }
            case ETestScreenLock: {
                // start screen lock


                break;
            }
            case ETestLED: {
                // start led


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TestActivity.this);
                            alertDialog.setTitle("LED Notification Test");
                            alertDialog.setMessage("Do you have LED Notification in your mobile?");
                            alertDialog.setCancelable(false);


                            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                    testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestLED, getApplicationContext(), TestActivity.this, TestActivity.this);
                                    testBase.setIcon(R.drawable.launcher_icon2);
                                    testBase.startTest();
                                }
                            });

                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                    resultStatus(ValueEnumConstants.DeviceTestType.ETestLED, ValueEnumConstants.ResultTypeValue.EResultUnKnown);
                                }
                            });

                            alertDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                break;
            }
            case ETestUvSensor: {
                // start uv


                break;
            }
            case ETestSwingTest: {

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSwingTest, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();

                break;
            }
            case ETestStepDetector: {

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestStepDetector, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();


                break;
            }
            case ETestStepCounter: {

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestStepCounter, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();


                break;
            }
            case ETestMotionDetector: {

                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestMotionDetector, getApplicationContext(), TestActivity.this, TestActivity.this);
                testBase.startTest();

                break;
            }
        }
    }

    private boolean checkBGTestDone() {
        Log.info("Inside checkBGTestDone value0=");
        boolean result = false;
        testResultArray = new int[]{networkTestInfo1, wifiTestInfo, imeiTestInfo, rootTestInfo, batteryTestInfo, ramTestInfo, lightTestInfo, magneticTestInfo, gpsTestInfo, btTestInfo, simTestInfo1};
        for (int i = 0; i < testResultArray.length; i++)
            if (testResultArray[i] != -3) {
                result = true;
                Log.info("Inside checkBGTestDone value=" + testResultArray[i]);
            } else {
                Log.info("Inside checkBGTestDone value1=");
                result = false;
                break;
            }
        return result;
    }

    public void cancelSensorTimer(ValueEnumConstants.DeviceTestType type) {
        mSensorCounter = 0;
        extraTextStr = "";

        if (mSensorTimer != null) {
            mSensorTimer.cancel();
            mSensorTimer.purge();
            mSensorTimer = null;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                counterTextView.setVisibility(View.INVISIBLE);
                extraText.setVisibility(View.INVISIBLE);

                counterTextView.setText("" + mSensorCounter);
                extraText.setText(extraTextStr);
            }
        });
    }

    private String extraTextStr = "";
    private Timer mSensorTimer = null;
    private int mSensorCounter = 0;
    private ValueEnumConstants.DeviceTestType mTestType;

    public void setupSensorTimer(ValueEnumConstants.DeviceTestType type, int counter) {
        Log.info("Inside checkBGTestDone values5=");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                counterTextView.setVisibility(View.VISIBLE);
                extraText.setVisibility(View.VISIBLE);
            }
        });

        mTestType = type;
        mSensorCounter = counter;

        if (type == ValueEnumConstants.DeviceTestType.ETestHeadJack) {
            extraTextStr = "Connect Earphone";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
            if (counter == 7)
                extraTextStr = "Connect Earphone";
            else if (counter == 10)
                extraTextStr = "Say something to record";
            else if (counter == 6)
                extraTextStr = "Playing recorded sound...";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
            if (counter == 10)
                extraTextStr = "Say something to record";
            else if (counter == 6)
                extraTextStr = "Playing recorded sound...";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestMic) {
            extraTextStr = "Say something to record";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
            extraTextStr = "Say something to record";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
            extraTextStr = "Say something to test Receiver";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestPowerKey) {
            extraTextStr = "Press Power Button";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestVolumeDwnKey) {
            extraTextStr = "Press Volume Down Button";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestVolumeUpKey) {
            extraTextStr = "Press Volume Up Button";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestHomeKey) {
            extraTextStr = "Press Home Key";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestBackKey) {
            extraTextStr = "Press Back Key";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestMenuKey) {
            extraTextStr = "Press Menu Key";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestProximity) {
            extraTextStr = "Swipe on top of screen";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestUsb) {
            extraTextStr = "Connect to power source";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestFlipAction) {
            extraTextStr = "Tilt device to continue";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestHRM) {
            extraTextStr = "Place your finger on HRM Sensor";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestBiometric) {
            extraTextStr = "Place finger on Biometric sensor";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestScreenLock) {
            extraTextStr = "Lock and Unlock Screen";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestLED) {
            extraTextStr = "Turn Screen off";
        } else if (type == ValueEnumConstants.DeviceTestType.ETestSwingTest) {
            extraTextStr = "Swing device to check";
        }
        //counter for Screen
       /* else if (type == ValueEnumConstants.DeviceTestType.ETestScreen) {
            extraTextStr = "Touch and move your finger on the grid boxes to mark them as green.";
        }*/

        mSensorTimer = new Timer();
        mSensorTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mSensorCounter <= 0) {
                    Log.info("Inside checkBGTestDone values50=" + mSensorCounter);
                    if (mSensorTimer != null) {
                        Log.info("Inside checkBGTestDone values50=" + mSensorCounter);
                        mSensorTimer.cancel();
                        Log.info("Inside checkBGTestDone values50=" + mSensorCounter);
                        mSensorTimer.purge();
                        System.out.println("Inside checkBGTestDone values50=" + mSensorCounter);
                        mSensorTimer = null;
                        Log.info("Inside checkBGTestDone values50=" + mSensorCounter);
                    }

                    sendTimerExpireCallback(mTestType);

                    return;
                }

                if (mTestType == ValueEnumConstants.DeviceTestType.ETestFlipAction) {
                    if (testBase != null) {
                        testBase.handleTimeTick(mSensorCounter);
                    }
                } else if (mTestType == ValueEnumConstants.DeviceTestType.ETestHRM) {
                    if (testBase != null) {
                        testBase.handleTimeTick(mSensorCounter);

                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mTestType == ValueEnumConstants.DeviceTestType.ETestVolumeDwnKey ||
                                mTestType == ValueEnumConstants.DeviceTestType.ETestVolumeUpKey ||
                                mTestType == ValueEnumConstants.DeviceTestType.ETestHomeKey ||
                                mTestType == ValueEnumConstants.DeviceTestType.ETestMenuKey ||
                                mTestType == ValueEnumConstants.DeviceTestType.ETestHeadJack ||
                                mTestType == ValueEnumConstants.DeviceTestType.ETestMic ||
                                mTestType == ValueEnumConstants.DeviceTestType.ETestBiometric ||
                                mTestType == ValueEnumConstants.DeviceTestType.ETestFlipAction ||
                                mTestType == ValueEnumConstants.DeviceTestType.ETestProximity) {
                            counterTextView.setText((mSensorCounter - 1) + "");
                            extraText.setText(extraTextStr);
                        } else if (mTestType == ValueEnumConstants.DeviceTestType.ETestBackKey) {
                            counterTextView.setText((mSensorCounter - 1) + "");
                            extraText.setText(extraTextStr);
//                            extraText.setText(extraTextStr);
                        } else if (mTestType == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
                            counterTextView.setText((mSensorCounter - 1) + "");
                            extraText.setText(extraTextStr);

                        } else if (mTestType == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
                            counterTextView.setText((mSensorCounter - 1) + "");
                            extraText.setText(extraTextStr);
                        } else if (mTestType == ValueEnumConstants.DeviceTestType.ETestPowerKey) {
                            counterTextView.setText((mSensorCounter - 1) + "");
                            extraText.setText(extraTextStr);
                        }
                        //Counter For SCreen
                       /* else if (mTestType == ValueEnumConstants.DeviceTestType.ETestScreen) {
                            counterTextView.setText((mSensorCounter - 1) + "");
                            extraText.setText(extraTextStr);
                        }*/
                        else {
                            counterTextView.setText((mSensorCounter) + "");
                            extraText.setText(extraTextStr);
                        }
                        mSensorCounter--;
                    }
                });
            }
        }, 0, 1000);
    }

    public void updateMidUI(ValueEnumConstants.DeviceTestType type, final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                extraText.setText(title);
                counterTextView.setText("");
            }
        });
    }

    @Override
    public void updateUII(String title, String msg, ValueEnumConstants.DeviceTestType type) {
        extraText.setVisibility(View.VISIBLE);
        extraText.setText(msg);

        if (type == ValueEnumConstants.DeviceTestType.ETestVideoRecord) {
            playvideo.setVisibility(View.GONE);
            capturevideo.setVisibility(View.GONE);
        }
        if (type == ValueEnumConstants.DeviceTestType.ETestFrontVideoRecorder) {
            capturevideo.setVisibility(View.GONE);
            playvideo.setVisibility(View.GONE);

        }
        if (type == ValueEnumConstants.DeviceTestType.ETestFrontCamera || type == ValueEnumConstants.DeviceTestType.ETestRearCamera) {
            playimage.setVisibility(View.GONE);
            captureimage.setVisibility(View.GONE);
        }

    }

    @Override
    public void updateUIIFlash(final String title, final String msg, final ValueEnumConstants.DeviceTestType type) {
        mConfirmDlgTestTypes = type;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TestActivity.this);
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(msg);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            boolean returnResult = false;

                            if (type == ValueEnumConstants.DeviceTestType.ETestFlash) {
                                Log.info("Cameratest", "Inside startFlashCameraTest method28 inside catch e value is28=");
                                torchTestInfo = flashTestinfo;
                                torchTestData = flashTestData;
                            }

                            System.out.println("TORCH test result value1=" + torchTestInfo + "data=" + torchTestData);
                            Log.info("Cameratest", "Inside startFlashCameraTest method28 inside catch e value is29=" + torchTestInfo + "data=" + torchTestData);
                           /* if(type == ValueEnumConstants.DeviceTestType.ETestTorch)
                            {
                                System.out.println("TORCH test result value1="+flashTestinfo+"data="+flashTestData);
                                   torchTestInfo = flashTestinfo;
                                   torchTestData = flashTestData;
                            }*/

                            if (type == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
                                if (testBase != null && testBase.getForRetryFlow()) {
                                    testBase.startTest();
                                } else if (testBase != null && testBase.getForFinalFlow()) {
                                    testBase.startTest();
                                } else {
                                    returnResult = true;
                                }
                            } else if (type == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
                                if (testBase != null && testBase.getForRetryFlow()) {
                                    testBase.startTest(R.raw.mp3_1khz);
                                } else if (testBase != null && testBase.getForFinalFlow()) {
                                    testBase.startTest(R.raw.mp3_1khz);
                                } else {
                                    returnResult = true;
                                }
                            } else {
                                returnResult = true;
                            }

                            if (returnResult) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        try {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        if (viewHolders != null) {
                                                            viewHolders.removeAllViews();
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            });
                                        } catch (Exception e) {
                                        }
                                        // viewHolders.setVisibility(View.GONE);
                                        resultStatus(mConfirmDlgTestTypes, ValueEnumConstants.ResultTypeValue.EResultPass);
                                    }
                                }, 1000);
                            }
                        }
                    });

                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Log.info("Cameratest", "Inside startFlashCameraTest method28 inside catch e value is30=");
                            boolean returnResult = false;
                            if (type == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
                                if (testBase != null && testBase.getForMicConfirm()) {
                                    testBase.setForRetryFlow();
                                    testBase.startTest();
                                } else if (testBase != null && testBase.getForRetryFlow()) {
                                    testBase.startTest();
                                } else {
                                    returnResult = true;
                                }
                            } else if (type == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
                                if (testBase != null && testBase.getForMicConfirm()) {
                                    testBase.setForRetryFlow();
                                    testBase.startTest(R.raw.mp3_1khz);
                                } else {
                                    returnResult = true;
                                }
                            } else {
                                returnResult = true;
                            }

                            if (returnResult) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        try {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        if (viewHolders != null) {
                                                            viewHolders.removeAllViews();
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            });
                                        } catch (Exception e) {
                                        }
                                        // viewHolders.setVisibility(View.GONE);
                                        resultStatus(mConfirmDlgTestTypes, ValueEnumConstants.ResultTypeValue.EResultFail);
                                    }
                                }, 1000);
                            }
                        }
                    });

                    alertDialog.show();
                } catch (Exception e) {
                    Log.info("Cameratest", "Inside startFlashCameraTest method28 inside catch e value is31=" + e);
                    e.printStackTrace();
                }
            }
        });

    }

    private void sendTimerExpireCallback(ValueEnumConstants.DeviceTestType type) {
        System.out.println("Inside checkBGTestDone values6=");
        if (type == ValueEnumConstants.DeviceTestType.ETestHeadJack) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestMic) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestPowerKey) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestVolumeDwnKey
                || type == ValueEnumConstants.DeviceTestType.ETestVolumeUpKey
                || type == ValueEnumConstants.DeviceTestType.ETestHomeKey
                || type == ValueEnumConstants.DeviceTestType.ETestBackKey
                || type == ValueEnumConstants.DeviceTestType.ETestMenuKey) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestProximity) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestUsb) {
            System.out.println("Inside checkBGTestDone values7=");
            if (testBase != null) {
                System.out.println("Inside checkBGTestDone values8=");
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestFlipAction) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestHRM) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestBiometric) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestScreenLock) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestLED) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        } else if (type == ValueEnumConstants.DeviceTestType.ETestSwingTest) {
            if (testBase != null) {
                testBase.handleTimerExpire();
            }
        }
        //Screen test Extra handling

    }

    String testTitleTxt = "";
    int testImageId = 0;

    private void updateTestUI(final ValueEnumConstants.DeviceTestType testType) {

        switch (testType) {

            case ETestNetwork2: {
                testTitleTxt = "Scanning Network Signal SIM 2";
                testImageId = R.drawable.scan_networksim;
                break;
            }
            case ETestCall1: {
                if (isDualSim)
                    testTitleTxt = "Scanning Call SIM 1";
                else
                    testTitleTxt = "Scanning Call";
                testImageId = R.drawable.scan_deviceablecall;
                break;
            }
            case ETestCall2: {
                testTitleTxt = "Scanning Call SIM 2";
                testImageId = R.drawable.scan_deviceablecall;
                break;
            }

            case ETestFrontVideoRecorder: {
                testTitleTxt = "Scanning Video Front Recording";
                testImageId = R.drawable.scan_videorecording;
                break;
            }

            case ETestS_PENHovering: {
                testTitleTxt = "Scanning Stylus Hovering";
                testImageId = R.drawable.scan_screen;
                break;
            }
            case ETestSim2: {
                testTitleTxt = "Scanning SIM Slot 2";
                testImageId = R.drawable.scan_simcard;
                break;
            }
            case ETestRearCamera: {
                testTitleTxt = "Scanning Back Camera";
                testImageId = R.drawable.scan_camera;
                break;
            }
            case ETestFrontCamera: {
                testTitleTxt = "Scanning Front Camera";
                testImageId = R.drawable.scan_frontcamera;
                break;
            }
            case ETestFlash: {
                testTitleTxt = "Scanning Flash Light";
                testImageId = R.drawable.scan_ledflash;
                break;
            }
            case ETestTorch: {
                testTitleTxt = "Scanning Torch Light";
                testImageId = R.drawable.scan_torch;
                break;
            }
            case ETestVibrator: {
                testTitleTxt = "Scanning Vibrate";
                testImageId = R.drawable.scan_vibrate;
                break;
            }
            case ETestScreen: {
                testTitleTxt = "Scanning Display Touch Panel";
                testImageId = R.drawable.scan_screen;
                break;
            }
            case ETestBluetooth: {
                testTitleTxt = "Scanning Bluetooth";
                testImageId = R.drawable.scan_bluetooth;
                break;
            }
            case ETestHeadJack: {
                testTitleTxt = "Scanning Earphone Jack";
                testImageId = R.drawable.scan_headphonejack;
                break;
            }
            case ETestEarPiece: {
                testTitleTxt = "Scanning Earphone";
                testImageId = R.drawable.scan_earphone;
                break;
            }
            case ETestMic: {
                testTitleTxt = "Scanning Microphone";
                testImageId = R.drawable.scan_microphone;
                break;
            }
            case ETestLoudSpeaker: {
                testTitleTxt = "Scanning Loudspeaker";
                testImageId = R.drawable.scan_loudspeakerclear;
                break;
            }
            case ETestAutoFocus: {
                testTitleTxt = "Scanning Camera Auto Focus";
                testImageId = R.drawable.scan_cameraautofocus;
                break;
            }
            case ETestPowerKey: {
                testTitleTxt = "Scanning Power Button";
                testImageId = R.drawable.scan_powerbutton;
                break;
            }
            case ETestVolumeDwnKey: {
                testTitleTxt = "Scanning Volume-Down Button";
                testImageId = R.drawable.scan_volumebutton;
                break;
            }
            case ETestVolumeUpKey: {
                testTitleTxt = "Scanning Volume-Up Button";
                testImageId = R.drawable.scan_volumebutton;
                break;
            }
            case ETestHomeKey: {
                testTitleTxt = "Scanning Home Key";
                testImageId = R.drawable.scan_homebutton;
                break;
            }
            case ETestBackKey: {
                testTitleTxt = "Scanning Back Key";
                testImageId = R.drawable.scan_backbutton;
                break;
            }
            case ETestMenuKey: {
                testTitleTxt = "Scanning Menu Key";
                testImageId = R.drawable.scan_menu;
                break;
            }
            case ETestFlipAction: {
                testTitleTxt = "Scanning Tilt";
                testImageId = R.drawable.scan_tilt;
                break;
            }
            case ETestAccelerometer: {
                testTitleTxt = "Scanning Accelerometer";
                testImageId = R.drawable.scan_accelerometersensor;
                break;
            }
            case ETestGyroscope: {
                testTitleTxt = "Scanning Gyroscope";
                testImageId = R.drawable.scan_gyroscopesensor;
                break;
            }
            case ETestProximity: {
                testTitleTxt = "Scanning Proximity";
                testImageId = R.drawable.scan_proxomity;
                break;
            }
            case ETestVideoRecord: {
                testTitleTxt = "Scanning Back Video Recording";
                testImageId = R.drawable.scan_videorecording;
                break;
            }
            case ETestFrontSpeaker: {
                testTitleTxt = "Scanning Receiver";
                testImageId = R.drawable.scan_sound;
                break;
            }

            case ETestUsb: {
                testTitleTxt = "Scanning USB";
                testImageId = R.drawable.scan_usb;
                break;
            }
            case ETestNFC: {
                testTitleTxt = "Scanning NFC";
                testImageId = R.drawable.scan_nfc;
                break;
            }
            case ETestGyroscopeGaming: {
                testTitleTxt = "Scanning Gyroscope";
                testImageId = R.drawable.scan_gyroscopeforgaming;
                break;
            }
            case ETestOrientation: {
                testTitleTxt = "Scanning Orientation";
                testImageId = R.drawable.scan_oriantation;
                break;
            }
            case ETestBiometric: {
                testTitleTxt = "Scanning Biometeric";
                testImageId = R.drawable.scan_fingreprintsensor;
                break;
            }
            case ETestHRM: {
                testTitleTxt = "Scanning HRM";
                testImageId = R.drawable.scan_heartratemonitor;
                break;
            }
            case ETestSPO2: {
                testTitleTxt = "Scanning SPO2";
                testImageId = R.drawable.scan_spo2;
                break;
            }
            case ETestScreenLock: {
                testTitleTxt = "Scanning Screen Lock";
                testImageId = R.drawable.scan_screenlock;
                break;
            }
            case ETestLED: {
                testTitleTxt = "Scanning LED Notification";
                testImageId = R.drawable.scan_lednotificationtest;
                break;
            }
            case ETestSwingTest: {
                testTitleTxt = "Scanning Swing";
                testImageId = R.drawable.scan_movement;
                break;
            }
            case ETestStepDetector: {
                testTitleTxt = "Scanning Step Detector";
                testImageId = R.drawable.scan_stepdetector;
                break;
            }
            case ETestStepCounter: {
                testTitleTxt = "Scanning Step Counter";
                testImageId = R.drawable.scan_stepcounter;
                break;
            }
            case ETestMotionDetector: {
                testTitleTxt = "Scanning Motion Detector";
                testImageId = R.drawable.scan_motiondetactor;
                break;
            }

        }

        // Reset timer of sensor
        mSensorCounter = 0;
        extraTextStr = "";
        if (mSensorTimer != null) {
            mSensorTimer.cancel();
            mSensorTimer.purge();
            mSensorTimer = null;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                counterTextView.setVisibility(View.INVISIBLE);
                extraText.setVisibility(View.INVISIBLE);

                counterTextView.setText("" + mSensorCounter);
                extraText.setText(extraTextStr);


                scanningHeader.setText(testTitleTxt);

                centerImage.setImageResource(testImageId);

                scanningGif.setVisibility(View.VISIBLE);
                animate(scanningGif);

                if (testType == ValueEnumConstants.DeviceTestType.ETestRearCamera || testType == ValueEnumConstants.DeviceTestType.ETestFrontCamera) {
                    extraText.setVisibility(View.VISIBLE);
                    extraText.setText("Capturing Photo");
                    captureimage.setVisibility(View.GONE);
                } else if (testType == ValueEnumConstants.DeviceTestType.ETestVideoRecord) {
                    extraText.setVisibility(View.VISIBLE);
                    extraText.setText("Recording Video");
                    capturevideo.setVisibility(View.GONE);
                    playvideo.setVisibility(View.GONE);
                } else if (testType == ValueEnumConstants.DeviceTestType.ETestFrontVideoRecorder) {
                    extraText.setVisibility(View.VISIBLE);
                    extraText.setText("Recording Video");
                    capturevideo.setVisibility(View.GONE);
                    playvideo.setVisibility(View.GONE);
                }

                scanningScore.setText(currentTestIndex + "/" + totalTests);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_test_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help: {
                MainActivity.showHelpInfo(TestActivity.this);
                break;
            }
            case R.id.action_about: {

                MainActivity.showAboutInfo(TestActivity.this);
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println("KeyEvent down : " + keyCode + " , event: " + event.getAction());

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_MENU: {
                if (testBase != null)
                    testBase.handleKeyEvent(keyCode, event);

                break;
            }
        }

        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;


        return super.onKeyDown(keyCode, event);
    }

    private ValueEnumConstants.DeviceTestType mDlgTestType;

    public void showRetryDialog(final String title, final String msg, ValueEnumConstants.DeviceTestType type) {
        mDlgTestType = type;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TestActivity.this);
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(msg);
                    alertDialog.setCancelable(false);
                    if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestMic || mDlgTestType == ValueEnumConstants.DeviceTestType.ETestVideoRecord ||
                            mDlgTestType == ValueEnumConstants.DeviceTestType.ETestFrontVideoRecorder || mDlgTestType == ValueEnumConstants.DeviceTestType.ETestAutoFocus) {
                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestMic || mDlgTestType == ValueEnumConstants.DeviceTestType.ETestVideoRecord ||
                                        mDlgTestType == ValueEnumConstants.DeviceTestType.ETestFrontVideoRecorder || mDlgTestType == ValueEnumConstants.DeviceTestType.ETestAutoFocus) {

                                    Intent closedata = new Intent();
                                    closedata.putExtra("close", "close");
                                    setResult(Activity.RESULT_OK, closedata);
                                    // clearNotification();
                                    finish();
                                }
                            }
                        });

                    } else {
                        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
                                    if (testBase != null) {
                                        testBase.startTest();
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestPowerKey) {
                                    if (testBase != null) {
                                        testBase.startTest();
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestVolumeDwnKey
                                        || mDlgTestType == ValueEnumConstants.DeviceTestType.ETestVolumeUpKey
                                        || mDlgTestType == ValueEnumConstants.DeviceTestType.ETestMenuKey
                                        || mDlgTestType == ValueEnumConstants.DeviceTestType.ETestHomeKey
                                        || mDlgTestType == ValueEnumConstants.DeviceTestType.ETestBackKey) {
                                    if (testBase != null) {
                                        testBase.startTest(mDlgTestType);
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestProximity) {
                                    if (testBase != null) {
                                        testBase.startTest(mDlgTestType);
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestUsb) {
                                    if (testBase != null) {
                                        testBase.startTest(mDlgTestType);
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestFlipAction) {
                                    if (testBase != null) {
                                        flipactiontest = 0;
                                        isRetryflip = true;
                                        testBase.startTest(mDlgTestType);
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestHeadJack) {
                                    if (testBase != null) {
                                        testBase.startTest();
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestHRM) {
                                    if (testBase != null) {
                                        testBase.startTest1();
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestSwingTest) {
                                    if (testBase != null) {
                                        // swingactiontest = 0;
                                        isRetrySwing = true;
                                        testBase.startTest();
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestBiometric) {
                                    if (testBase != null) {
                                        testBase.startTest();
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestScreenLock) {
                                    if (testBase != null) {
                                        testBase.startTest();
                                    }
                                } else if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestLED) {
                                    if (testBase != null) {
                                        testBase.startTest();
                                    }
                                }
                            }
                        });
                    }

                    alertDialog.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            try {

                                if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
                                    jackTestInfo = -2;
                                }
                                resultStatus(mDlgTestType, ValueEnumConstants.ResultTypeValue.EResultSkip);
                                System.out.println("Start or flip test skip is called=" + mDlgTestType + "Test type=" + ValueEnumConstants.ResultTypeValue.EResultSkip);
                            } catch (Exception e) {
                                System.out.println("Exception in skiping the test  " + mDlgTestType + "  " + e.toString());
                            }
                            if (mDlgTestType == ValueEnumConstants.DeviceTestType.ETestSwingTest) {
                                stepDetectorTestInfo = 0;
                                stepCounterTestInfo = 0;
                            }
                        }
                    });

                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ValueEnumConstants.DeviceTestType mProceedDlgType;

    @Override
    public void showProceedDialog(final String title, final String msg, ValueEnumConstants.DeviceTestType type) {
        mProceedDlgType = type;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TestActivity.this);
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(msg);
                    alertDialog.setCancelable(false);
                    alertDialog.setNegativeButton("PROCEED", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if (mProceedDlgType == ValueEnumConstants.DeviceTestType.ETestMic) {
                                if (testBase != null) {
                                    testBase.startTest(mProceedDlgType);
                                }
                            } else if (mProceedDlgType == ValueEnumConstants.DeviceTestType.ETestLoudSpeaker) {
                                if (testBase != null) {
                                    testBase.loudSpeakertest();
                                }
                            } else if (mProceedDlgType == ValueEnumConstants.DeviceTestType.ETestAutoFocus) {
                                if (testBase != null) {
                                    testBase.proceedTest();
                                }
                            } else if (mProceedDlgType == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
                                if (testBase != null) {
                                    testBase.proceedTest();
                                }
                            } else if (mProceedDlgType == ValueEnumConstants.DeviceTestType.ETestRearCamera) {
                                if (testBase != null) {
                                    System.out.println("testbase = null");

                                    testBase.startImageCapture(0, "CameraTestBack.jpg", ValueEnumConstants.DeviceTestType.ETestRearCamera);
                                }
                            } else if (mProceedDlgType == ValueEnumConstants.DeviceTestType.ETestFlash) {
                                if (testBase != null) {
                                    System.out.println("testbase = null");

                                    testBase.startImageCaptureFlash(0, "CameraTestFlash.jpg", ValueEnumConstants.DeviceTestType.ETestFlash);
                                }
                            }
                        }
                    });
                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ValueEnumConstants.DeviceTestType mConfirmDlgTestType;
    ValueEnumConstants.DeviceTestType mConfirmDlgTestTypes;

  /*  @Override
    public void updateUII(String lat, String longi) {

                try {
                    Toast.makeText(getApplicationContext(),lat,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),longi,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    System.out.println("Confirm dialog 1");
                    e.printStackTrace();
                }


    }*/

    @Override
    public void confirmInputDialog(final String title, final String msg, final ValueEnumConstants.DeviceTestType type) {
        Log.info("Cameratest", "Inside startFlashCameraTest method26 inside catch e value is28=" + type);
        mConfirmDlgTestType = type;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TestActivity.this);
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(msg);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            boolean returnResult = false;

                            if (type == ValueEnumConstants.DeviceTestType.ETestFlash) {
                                Log.info("Cameratest", "Inside startFlashCameraTest method28 inside catch e value is28=");
                                torchTestInfo = flashTestinfo;
                                torchTestData = flashTestData;
                            }

                            System.out.println("TORCH test result value1=" + torchTestInfo + "data=" + torchTestData);
                            Log.info("Cameratest", "Inside startFlashCameraTest method28 inside catch e value is29=" + torchTestInfo + "data=" + torchTestData);
                           /* if(type == ValueEnumConstants.DeviceTestType.ETestTorch)
                            {
                                System.out.println("TORCH test result value1="+flashTestinfo+"data="+flashTestData);
                                   torchTestInfo = flashTestinfo;
                                   torchTestData = flashTestData;
                            }*/

                            if (type == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
                                if (testBase != null && testBase.getForRetryFlow()) {
                                    testBase.startTest();
                                } else if (testBase != null && testBase.getForFinalFlow()) {
                                    testBase.startTest();
                                } else {
                                    returnResult = true;
                                    AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

                                    if (audioManager.isWiredHeadsetOn()) {
                                        //Head jack pass
                                        jackTestInfo = 1;
                                    } else {
                                        jackTestInfo = 1;
                                    }
                                }
                            } else if (type == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
                                if (testBase != null && testBase.getForRetryFlow()) {
                                    testBase.startTest(R.raw.mp3_1khz);
                                } else if (testBase != null && testBase.getForFinalFlow()) {
                                    testBase.startTest(R.raw.mp3_1khz);
                                } else {
                                    returnResult = true;
                                    /*AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

                                    if (!audioManager.isWiredHeadsetOn()) {
                                        //Head jack pass
                                        jackTestInfo=1;
                                    }*/

                                }
                            } else {
                                returnResult = true;
                            }

                            if (returnResult) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        resultStatus(mConfirmDlgTestType, ValueEnumConstants.ResultTypeValue.EResultPass);
                                    }
                                }, 1000);
                            }
                        }
                    });

                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Log.info("Cameratest", "Inside startFlashCameraTest method28 inside catch e value is30=");
                            boolean returnResult = false;
                            if (type == ValueEnumConstants.DeviceTestType.ETestEarPiece) {
                                if (testBase != null && testBase.getForMicConfirm()) {
                                    testBase.setForRetryFlow();
                                    testBase.startTest();
                                } else if (testBase != null && testBase.getForRetryFlow()) {
                                    testBase.startTest();
                                } else {
                                    returnResult = true;
                                    AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

                                    if (!audioManager.isWiredHeadsetOn()) {
                                        //Head jack pass
                                        jackTestInfo = 0;
                                    } else {
                                        jackTestInfo = 1;
                                    }
                                }
                            } else if (type == ValueEnumConstants.DeviceTestType.ETestFrontSpeaker) {
                                if (testBase != null && testBase.getForMicConfirm()) {
                                    testBase.setForRetryFlow();
                                    testBase.startTest(R.raw.mp3_1khz);
                                } else {
                                    returnResult = true;
                                    /*AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

                                    if (!audioManager.isWiredHeadsetOn()) {
                                        //Head jack pass
                                        jackTestInfo=1;
                                    }*/
                                }
                            } else {
                                returnResult = true;
                            }

                            if (returnResult) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        resultStatus(mConfirmDlgTestType, ValueEnumConstants.ResultTypeValue.EResultFail);
                                    }
                                }, 1000);
                            }
                        }
                    });

                    alertDialog.show();
                } catch (Exception e) {
                    Log.info("Cameratest", "Inside startFlashCameraTest method28 inside catch e value is31=" + e);
                    e.printStackTrace();
                }
            }
        });
    }

    private int activityResultCode = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        activityResultCode = resultCode;
        if (requestCode == ValueEnumConstants.REQ_SCREEN_TEST) {
            // Handle screen test result
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (activityResultCode == -2) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestScreen, ValueEnumConstants.ResultTypeValue.EResultSkip);
                    } else if (activityResultCode == RESULT_OK) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestScreen, ValueEnumConstants.ResultTypeValue.EResultPass);
                    } else if (activityResultCode == RESULT_CANCELED) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestScreen, ValueEnumConstants.ResultTypeValue.EResultFail);
                    }
                }
            }, 1000);
        } else if (requestCode == ValueEnumConstants.REQ_BT_ENABLE) {
            if (testBaseautomated != null) {
                testBaseautomated.handleActivityResult(resultCode);
            }
        } else if (requestCode == 24) {
            if (testBaseautomated != null) {
                testBaseautomated.startTest();
            }
        } else if (requestCode == 45) {

            // Handle screen test result
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    String str = data.getStringExtra("spen");
                    if (str.equals("spenhoveringpass")) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestS_PENHovering, ValueEnumConstants.ResultTypeValue.EResultPass);
                    } else if (str.equals("spenhoveringfail")) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestS_PENHovering, ValueEnumConstants.ResultTypeValue.EResultFail);
                    } else if (str.equals("spenhoveringnotexist")) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestS_PENHovering, ValueEnumConstants.ResultTypeValue.EResultUnKnown);
                    } else if (str.equals("spenhoveringnotperformed")) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestS_PENHovering, ValueEnumConstants.ResultTypeValue.EResultSkip);
                    }
                    System.out.println("the value of stylus is onActivity " + str);

                }
            }, 1000);
        } else if (requestCode == 55) {
// Handle screen test result
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    String str = data.getStringExtra("led");
                    if (str.equals("ledhoveringpass")) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestS_PENHovering, ValueEnumConstants.ResultTypeValue.EResultPass);
                    } else if (str.equals("ledhoveringfail")) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestS_PENHovering, ValueEnumConstants.ResultTypeValue.EResultFail);
                    } else if (str.equals("ledhoveringnotexist")) {
                        resultStatus(ValueEnumConstants.DeviceTestType.ETestS_PENHovering, ValueEnumConstants.ResultTypeValue.EResultUnKnown);
                    }
                    System.out.println("the value of stylus is onActivity " + str);

                }
            }, 1000);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (testBase != null) {
            testBase.handleOnIntent(intent);
        }
    }

    private void endDeviceTests() {
        data = new Intent();

//        float passCount = 0, failCount = 0, totalCount = 0, notApplicable = 0;
        int mandatorypasscount = 0, mandatoryfailcount = 0, mandatoryna = 0, mandatoryskip = 0, optionalpasscount = 0, optionalfailcount = 0, optionalna = 0, optionalskip = 0, totalCount = 0;

        // Mandatory test
        String lastState = "";
        JSONArray mandatoryData = new JSONArray();
        {
            // Store network1 data
            if (networkTestInfo1 == 0) {
                mandatoryfailcount++;
            } else if (networkTestInfo1 == 1) {
                mandatorypasscount++;
            } else if (networkTestInfo1 == -1) {
                mandatoryna++;
            } else if (networkTestInfo1 == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + networkTestInfo1 + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestNetwork1;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":" + networkTestData1 + "}"));
            } catch (Exception e) {
            }
            System.out.println("Inside isdualsim=" + isDualSim);
            if (isDualSim) {
                if (networkTestInfo2 == 0) {
                    mandatoryfailcount++;
                } else if (networkTestInfo2 == 1) {
                    mandatorypasscount++;
                } else if (networkTestInfo2 == -1) {
                    mandatoryna++;
                } else if (networkTestInfo2 == -2) {
                    mandatoryskip++;
                }
                totalCount++;
                lastState += "" + networkTestInfo2 + ",";
                try {
                    ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestNetwork2;
                    mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
                } catch (Exception e) {
                }
            }

            // Store call1 data
            if (callTestInfoSim1 == 0) {
                mandatoryfailcount++;
            } else if (callTestInfoSim1 == 1) {
                mandatorypasscount++;
            } else if (callTestInfoSim1 == -1) {
                mandatoryna++;
            } else if (callTestInfoSim1 == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + callTestInfoSim1 + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestCall1;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store call2 data
            if (isDualSim) {
                if (callTestInfoSim2 == 0) {
                    mandatoryfailcount++;
                } else if (callTestInfoSim2 == 1) {
                    mandatorypasscount++;
                } else if (callTestInfoSim2 == -1) {
                    mandatoryna++;
                } else if (callTestInfoSim2 == -2) {
                    mandatoryskip++;
                }
                totalCount++;
                lastState += "" + callTestInfoSim2 + ",";
                try {
                    ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestCall2;
                    mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
                } catch (Exception e) {
                }
            }

            // Store sms1 data
            if (smsTestInfoSim1 == 0) {
                mandatoryfailcount++;
            } else if (smsTestInfoSim1 == 1) {
                mandatorypasscount++;
            } else if (smsTestInfoSim1 == -1) {
                mandatoryna++;
            } else if (smsTestInfoSim1 == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + smsTestInfoSim1 + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestSms1;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store sms2 data
            if (isDualSim) {
                if (smsTestInfoSim2 == 0) {
                    mandatoryfailcount++;
                } else if (smsTestInfoSim2 == 1) {
                    mandatorypasscount++;
                } else if (smsTestInfoSim2 == -1) {
                    mandatoryna++;
                } else if (smsTestInfoSim2 == -2) {
                    mandatoryskip++;
                }
                totalCount++;
                lastState += "" + smsTestInfoSim2 + ",";
                try {
                    ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestSms2;
                    mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
                } catch (Exception e) {
                }
            }

            // Store wifi data
            if (wifiTestInfo == 0) {
                mandatoryfailcount++;
            } else if (wifiTestInfo == 1) {
                mandatorypasscount++;
            } else if (wifiTestInfo == -1) {
                mandatoryna++;
            } else if (wifiTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + wifiTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestWifi;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":" + wifiTestData + "}"));
            } catch (Exception e) {
            }

            // Store internet data
            if (internetTestInfo == 0) {
                mandatoryfailcount++;
            } else if (internetTestInfo == 1) {
                mandatorypasscount++;
            } else if (internetTestInfo == -1) {
                mandatoryna++;
            } else if (internetTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + internetTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestInternet;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":" + internetTestData + "}"));
            } catch (Exception e) {
            }

            // Store imei  data
            if (imeiTestInfo == 0) {
                mandatoryfailcount++;
            } else if (imeiTestInfo == 1) {
                mandatorypasscount++;
            } else if (imeiTestInfo == -1) {
                mandatoryna++;
            } else if (imeiTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + imeiTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestImei;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store root data
            if (rootTestInfo == 0) {
                mandatoryfailcount++;
            } else if (rootTestInfo == 1) {
                mandatorypasscount++;
            } else if (rootTestInfo == -1) {
                mandatoryna++;
            } else if (rootTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + rootTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestRooted;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store sim1 data
            if (simTestInfo1 == 0) {
                mandatoryfailcount++;
            } else if (simTestInfo1 == 1) {
                mandatorypasscount++;
            } else if (simTestInfo1 == -1) {
                mandatoryna++;
            } else if (simTestInfo1 == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + simTestInfo1 + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestSim1;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }
            if (isDualSim) {
                if (simTestInfo2 == 0) {
                    mandatoryfailcount++;
                } else if (simTestInfo2 == 1) {
                    mandatorypasscount++;
                } else if (simTestInfo2 == -1) {
                    mandatoryna++;
                } else if (simTestInfo2 == -2) {
                    mandatoryskip++;
                }
                totalCount++;
                lastState += "" + simTestInfo2 + ",";
                try {
                    ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestSim2;
                    mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
                } catch (Exception e) {
                }
            }

            // Store camera back data
            if (cameraRearTestInfo == 0) {
                mandatoryfailcount++;
            } else if (cameraRearTestInfo == 1) {
                mandatorypasscount++;
            } else if (cameraRearTestInfo == -1) {
                mandatoryna++;
            } else if (cameraRearTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + cameraRearTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestRearCamera;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store camera front data
            if (cameraFrontTestInfo == 0) {
                mandatoryfailcount++;
            } else if (cameraFrontTestInfo == 1) {
                mandatorypasscount++;
            } else if (cameraFrontTestInfo == -1) {
                mandatoryna++;
            } else if (cameraFrontTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + cameraFrontTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestFrontCamera;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store flash data
            if (flashTestinfo == 0) {
                mandatoryfailcount++;
            } else if (flashTestinfo == 1) {
                mandatorypasscount++;
            } else if (flashTestinfo == -1) {
                mandatoryna++;
            } else if (flashTestinfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + flashTestinfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestFlash;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store torch data
            if (torchTestInfo == 0) {
                mandatoryfailcount++;
            } else if (torchTestInfo == 1) {
                mandatorypasscount++;
            } else if (torchTestInfo == -1) {
                mandatoryna++;
            } else if (torchTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + torchTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestTorch;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store battery data
            if (batteryTestInfo == 0) {
                mandatoryfailcount++;
            } else if (batteryTestInfo == 1) {
                mandatorypasscount++;
            } else if (batteryTestInfo == -1) {
                mandatoryna++;
            } else if (batteryTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + batteryTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestBattery;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":" + batteryTestData + "}"));
            } catch (Exception e) {
            }

            // Store vibrator data
            if (vibratorTestInfo == 0) {
                mandatoryfailcount++;
            } else if (vibratorTestInfo == 1) {
                mandatorypasscount++;
            } else if (vibratorTestInfo == -1) {
                mandatoryna++;
            } else if (vibratorTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + vibratorTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestVibrator;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store ram data
            if (ramTestInfo == 0) {
                mandatoryfailcount++;
            } else if (ramTestInfo == 1) {
                mandatorypasscount++;
            } else if (ramTestInfo == -1) {
                mandatoryna++;
            } else if (ramTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + ramTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestRam;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":" + ramTestData + "}"));

            } catch (Exception e) {
            }

            // Store internal storage data
            if (internalStorageTestInfo == 0) {
                mandatoryfailcount++;
            } else if (internalStorageTestInfo == 1) {
                mandatorypasscount++;
            } else if (internalStorageTestInfo == -1) {
                mandatoryna++;
            } else if (internalStorageTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + internalStorageTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestStorageInternal;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":" + internalStorageTestData + "}"));
            } catch (Exception e) {
            }

            // Store external storage data
            if (externalStorageTestInfo == 0) {
                mandatoryfailcount++;
            } else if (externalStorageTestInfo == 1) {
                mandatorypasscount++;
            } else if (externalStorageTestInfo == -1) {
                mandatoryna++;
            } else if (externalStorageTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + externalStorageTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestStorageExternal;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":" + externalStorageTestData + "}"));
            } catch (Exception e) {
            }

            // Store light data
            if (lightTestInfo == 0) {
                mandatoryfailcount++;
            } else if (lightTestInfo == 1) {
                mandatorypasscount++;
            } else if (lightTestInfo == -1) {
                mandatoryna++;
            } else if (lightTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + lightTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestLight;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store screen data
            if (screenTestInfo == 0) {
                mandatoryfailcount++;
            } else if (screenTestInfo == 1) {
                mandatorypasscount++;
            } else if (screenTestInfo == -1) {
                mandatoryna++;
            } else if (screenTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + screenTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestScreen;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store brightness data
            if (brightnessTestInfo == 0) {
                mandatoryfailcount++;
            } else if (brightnessTestInfo == 1) {
                mandatorypasscount++;
            } else if (brightnessTestInfo == -1) {
                mandatoryna++;
            } else if (brightnessTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + brightnessTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestBrightness;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store dead pixel data
            if (pixelTestInfo == 0) {
                mandatoryfailcount++;
            } else if (pixelTestInfo == 1) {
                mandatorypasscount++;
            } else if (pixelTestInfo == -1) {
                mandatoryna++;
            } else if (pixelTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + pixelTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestDeadPixel;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store bluetooth data
            if (btTestInfo == 0) {
                mandatoryfailcount++;
            } else if (btTestInfo == 1) {
                mandatorypasscount++;
            } else if (btTestInfo == -1) {
                mandatoryna++;
            } else if (btTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + btTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestBluetooth;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store jack data
            if (jackTestInfo == 0) {
                mandatoryfailcount++;
            } else if (jackTestInfo == 1) {
                mandatorypasscount++;
            } else if (jackTestInfo == -1) {
                mandatoryna++;
            } else if (jackTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + jackTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestHeadJack;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store power key data
            if (powerKeyTestInfo == 0) {
                mandatoryfailcount++;
            } else if (powerKeyTestInfo == 1) {
                mandatorypasscount++;
            } else if (powerKeyTestInfo == -1) {
                mandatoryna++;
            } else if (powerKeyTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + powerKeyTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestPowerKey;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store volume up key data
            if (volumeKeyUpTestInfo == 0) {
                mandatoryfailcount++;
            } else if (volumeKeyUpTestInfo == 1) {
                mandatorypasscount++;
            } else if (volumeKeyUpTestInfo == -1) {
                mandatoryna++;
            } else if (volumeKeyUpTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + volumeKeyUpTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestVolumeUpKey;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store volume down key data
            if (volumeKeyDownTestInfo == 0) {
                mandatoryfailcount++;
            } else if (volumeKeyDownTestInfo == 1) {
                mandatorypasscount++;
            } else if (volumeKeyDownTestInfo == -1) {
                mandatoryna++;
            } else if (volumeKeyDownTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + volumeKeyDownTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestVolumeDwnKey;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store home key data
            if (homeKeyTestInfo == 0) {
                mandatoryfailcount++;
            } else if (homeKeyTestInfo == 1) {
                mandatorypasscount++;
            } else if (homeKeyTestInfo == -1) {
                mandatoryna++;
            } else if (homeKeyTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + homeKeyTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestHomeKey;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store back key data
            if (backKeyTestInfo == 0) {
                mandatoryfailcount++;
            } else if (backKeyTestInfo == 1) {
                mandatorypasscount++;
            } else if (backKeyTestInfo == -1) {
                mandatoryna++;
            } else if (backKeyTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + backKeyTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestBackKey;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store microphone data
            if (micTestInfo == 0) {
                mandatoryfailcount++;
            } else if (micTestInfo == 1) {
                mandatorypasscount++;
            } else if (micTestInfo == -1) {
                mandatoryna++;
            } else if (micTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + micTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestMic;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store loud speaker data
            if (loudSpeakerTestInfo == 0) {
                mandatoryfailcount++;
            } else if (loudSpeakerTestInfo == 1) {
                mandatorypasscount++;
            } else if (loudSpeakerTestInfo == -1) {
                mandatoryna++;
            } else if (loudSpeakerTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + loudSpeakerTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestLoudSpeaker;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store ear piece data
            if (earPieceTestInfo == 0) {
                mandatoryfailcount++;
            } else if (earPieceTestInfo == 1) {
                mandatorypasscount++;
            } else if (earPieceTestInfo == -1) {
                mandatoryna++;
            } else if (earPieceTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + earPieceTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestEarPiece;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store front speaker data
            if (frontSpeakerTestInfo == 0) {
                mandatoryfailcount++;
            } else if (frontSpeakerTestInfo == 1) {
                mandatorypasscount++;
            } else if (frontSpeakerTestInfo == -1) {
                mandatoryna++;
            } else if (frontSpeakerTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + frontSpeakerTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestFrontSpeaker;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store camera auto focus data
            if (cameraAutofocusTestInfo == 0) {
                mandatoryfailcount++;
            } else if (cameraAutofocusTestInfo == 1) {
                mandatorypasscount++;
            } else if (cameraAutofocusTestInfo == -1) {
                mandatoryna++;
            } else if (cameraAutofocusTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + cameraAutofocusTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestAutoFocus;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store accelerometer data
            if (accelerometerTestInfo == 0) {
                mandatoryfailcount++;
            } else if (accelerometerTestInfo == 1) {
                mandatorypasscount++;
            } else if (accelerometerTestInfo == -1) {
                mandatoryna++;
            } else if (accelerometerTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + accelerometerTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestAccelerometer;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store gyroscope data
            if (gyroscopeTestInfo == 0) {
                mandatoryfailcount++;
            } else if (gyroscopeTestInfo == 1) {
                mandatorypasscount++;
            } else if (gyroscopeTestInfo == -1) {
                mandatoryna++;
            } else if (gyroscopeTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + gyroscopeTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestGyroscope;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store proximity data
            if (proximityTestInfo == 0) {
                mandatoryfailcount++;
            } else if (proximityTestInfo == 1) {
                mandatorypasscount++;
            } else if (proximityTestInfo == -1) {
                mandatoryna++;
            } else if (proximityTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + proximityTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestProximity;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store video back data
            if (videoTestInfo == 0) {
                mandatoryfailcount++;
            } else if (videoTestInfo == 1) {
                mandatorypasscount++;
            } else if (videoTestInfo == -1) {
                mandatoryna++;
            } else if (videoTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + videoTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestVideoRecord;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store video front data
            if (frontVideoRecoderTest == 0) {
                mandatoryfailcount++;
            } else if (frontVideoRecoderTest == 1) {
                mandatorypasscount++;
            } else if (frontVideoRecoderTest == -1) {
                mandatoryna++;
            } else if (frontVideoRecoderTest == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + frontVideoRecoderTest + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestFrontVideoRecorder;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store usb data
            if (usbTestInfo == 0) {
                mandatoryfailcount++;
            } else if (usbTestInfo == 1) {
                mandatorypasscount++;
            } else if (usbTestInfo == -1) {
                mandatoryna++;
            } else if (usbTestInfo == -2) {
                mandatoryskip++;
            }
            totalCount++;
            lastState += "" + usbTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestUsb;
                mandatoryData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            System.out.println("Resultsvalueare=" + mandatoryData.toString());
        }

        // Optional tests
        String optionalTest = "";
        JSONArray optionalData = new JSONArray();
        {
            // Store air pressure data
            if (airPressureTestInfo == 0) {
                optionalfailcount++;
            } else if (airPressureTestInfo == 1) {
                optionalpasscount++;
            } else if (airPressureTestInfo == -1) {
                optionalna++;
            } else if (airPressureTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + airPressureTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestAirPressure;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store air temperature data
            if (airTemperatureTestInfo == 0) {
                optionalfailcount++;
            } else if (airTemperatureTestInfo == 1) {
                optionalpasscount++;
            } else if (airTemperatureTestInfo == -1) {
                optionalna++;
            } else if (airTemperatureTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + airTemperatureTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestAirTemperature;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store gravity data
            if (gravityTestInfo == 0) {
                optionalfailcount++;
            } else if (gravityTestInfo == 1) {
                optionalpasscount++;
            } else if (gravityTestInfo == -1) {
                optionalna++;
            } else if (gravityTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + gravityTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestGravity;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store infra red data
            if (irTestInfo == 0) {
                optionalfailcount++;
            } else if (irTestInfo == 1) {
                optionalpasscount++;
            } else if (irTestInfo == -1) {
                optionalna++;
            } else if (irTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + irTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestIRTest;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store nfc data
            if (nfcTestInfo == 0) {
                optionalfailcount++;
            } else if (nfcTestInfo == 1) {
                optionalpasscount++;
                // totalCount++;
            } else if (nfcTestInfo == -1) {
                optionalna++;
            } else if (nfcTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + nfcTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestNFC;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store gyroscope gaming data
            if (gyroscopeForGamingTestInfo == 0) {
                optionalfailcount++;
            } else if (gyroscopeForGamingTestInfo == 1) {
                optionalpasscount++;
            } else if (gyroscopeForGamingTestInfo == -1) {
                optionalna++;
            } else if (gyroscopeForGamingTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + gyroscopeForGamingTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestGyroscopeGaming;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store orientation data
            if (orientationTestInfo == 0) {
                optionalfailcount++;
            } else if (orientationTestInfo == 1) {
                optionalpasscount++;
            } else if (orientationTestInfo == -1) {
                optionalna++;
            } else if (orientationTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + orientationTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestOrientation;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store device temperature data
            if (deviceTempTestInfo == 0) {
                optionalfailcount++;
            } else if (deviceTempTestInfo == 1) {
                optionalpasscount++;
            } else if (deviceTempTestInfo == -1) {
                optionalna++;
            } else if (deviceTempTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + deviceTempTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestDeviceTemperature;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store step detector data
            if (stepDetectorTestInfo == 0) {
                optionalfailcount++;
            } else if (stepDetectorTestInfo == 1) {
                optionalpasscount++;
            } else if (stepDetectorTestInfo == -1) {
                optionalna++;
            } else if (stepDetectorTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + stepDetectorTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestStepDetector;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store step counter data
            if (stepCounterTestInfo == 0) {
                optionalfailcount++;
            } else if (stepCounterTestInfo == 1) {
                optionalpasscount++;
            } else if (stepCounterTestInfo == -1) {
                optionalna++;
            } else if (stepCounterTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + stepCounterTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestStepCounter;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store motion data
            if (motionTestInfo == 0) {
                optionalfailcount++;
            } else if (motionTestInfo == 1) {
                optionalpasscount++;
            } else if (motionTestInfo == -1) {
                optionalna++;
            } else if (motionTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + motionTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestMotionDetector;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store humidity data
            if (humidityTestInfo == 0) {
                optionalfailcount++;
            } else if (humidityTestInfo == 1) {
                optionalpasscount++;
            } else if (humidityTestInfo == -1) {
                optionalna++;
            } else if (humidityTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + humidityTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestHumidity;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store biometric data
            if (biomatricTestInfo == 0) {
                optionalfailcount++;
            } else if (biomatricTestInfo == 1) {
                optionalpasscount++;
            } else if (biomatricTestInfo == -1) {
                optionalna++;
            } else if (biomatricTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + biomatricTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestBiometric;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store hrm data
            if (hrmTestInfo == 0) {
                optionalfailcount++;
            } else if (hrmTestInfo == 1) {
                optionalpasscount++;
            } else if (hrmTestInfo == -1) {
                optionalna++;
            } else if (hrmTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + hrmTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestHRM;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            if (magneticTestInfo == 0) {
                optionalfailcount++;
            } else if (magneticTestInfo == 1) {
                optionalpasscount++;
            } else if (magneticTestInfo == -1) {
                optionalna++;
            } else if (magneticTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + magneticTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestMagnetic;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }
            if (gpsTestInfo == 0) {
                optionalfailcount++;
            } else if (gpsTestInfo == 1) {
                optionalpasscount++;
            } else if (gpsTestInfo == -1) {
                optionalna++;
            } else if (gpsTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + gpsTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestGps;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":" + gpsTestData + "}"));
            } catch (Exception e) {
            }

            // Store spo2 data
            if (SPO2TestInfo == 0) {
                optionalfailcount++;
            } else if (SPO2TestInfo == 1) {
                optionalpasscount++;
            } else if (SPO2TestInfo == -1) {
                optionalna++;
            } else if (SPO2TestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + SPO2TestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestSPO2;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store menu key data
            if (menuKeyTestInfo == 0) {
                optionalfailcount++;
            } else if (menuKeyTestInfo == 1) {
                optionalpasscount++;
            } else if (menuKeyTestInfo == -1) {
                optionalna++;
            } else if (menuKeyTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + menuKeyTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestMenuKey;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Store screen lock data
            if (screenLockTestInfo == 0) {
                // System.out.println("Vikash inside optional test-0=" + optionalTest);
                optionalfailcount++;
            } else if (screenLockTestInfo == 1) {
                //System.out.println("Vikash inside optional test-1=" + optionalTest);
                //System.out.println("Vikash inside screen test-9=" + screenLockTestInfo);
                optionalpasscount++;
            } else if (screenLockTestInfo == -1) {
                optionalna++;
            } else if (screenLockTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;

            optionalTest += "" + screenLockTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestScreenLock;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Start led data
            if (ledNotificationInfo == 0) {
                optionalfailcount++;
            } else if (ledNotificationInfo == 1) {
                optionalpasscount++;
            } else if (ledNotificationInfo == -1) {
                optionalna++;
            } else if (ledNotificationInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + ledNotificationInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestLED;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Start uv data
            if (uvSensorTestInfo == 0) {
                optionalfailcount++;
            } else if (uvSensorTestInfo == 1) {
                optionalpasscount++;
            } else if (uvSensorTestInfo == -1) {
                optionalna++;
            } else if (uvSensorTestInfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + uvSensorTestInfo + ",";
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestUvSensor;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }

            // Start spen data
            if (spenhoveringtestinfo == 0) {
                optionalfailcount++;
            } else if (spenhoveringtestinfo == 1) {
                optionalpasscount++;
            } else if (spenhoveringtestinfo == -1) {
                optionalna++;
            } else if (spenhoveringtestinfo == -2) {
                optionalskip++;
            }
            totalCount++;
            optionalTest += "" + spenhoveringtestinfo;
            try {
                ValueEnumConstants.DeviceTestType testtype = ValueEnumConstants.DeviceTestType.ETestS_PENHovering;
                optionalData.put(new JSONObject("{\"key\":\"" + testtype.getKeyName() + "\",\"result\":\"\",\"data\":{}}"));
            } catch (Exception e) {
            }


        }
        System.out.println("Resultsvalueare1=" + optionalData.toString());

        System.out.println("Vikash inside Score Values mandat=" + mandatorypasscount + "Mandatory fail count=" + mandatoryfailcount);

        int scoreValnew = (int) ((mandatorypasscount * 100) / (mandatorypasscount + mandatoryfailcount));


        System.out.println("Vikash inside Score Values final=" + scoreValnew);


        Log.info("Tests result for Mandatory -: Network1: " + networkTestInfo1 + " , Call1: " + callTestInfoSim1 + " , Call2: " + callTestInfoSim2 + " , Sms1: " + smsTestInfoSim1 + " , Sms2: " + smsTestInfoSim2 + " , Wifi " + wifiTestInfo + " , Gps: " + gpsTestInfo + " , Internet: " + internetTestInfo + " , Imei: " + imeiTestInfo + " , Rooted: " + rootTestInfo + " , Sim1: " + simTestInfo1 + " , RCamera: " + cameraRearTestInfo + " , FCamera : " + cameraFrontTestInfo + " , Flash: " + flashTestinfo + " , Torch: " + torchTestInfo + " , Battery: " + batteryTestInfo + " , Vibrator: " + vibratorTestInfo + " , Ram: " + ramTestInfo + " , InternalSpace: " + internalStorageTestInfo + " , ExternalSpace: " + externalStorageTestInfo + " , Light: " + lightTestInfo + " , Magnetic: " + magneticTestInfo + " , Screen: " + screenTestInfo + " , BT: " + btTestInfo + " , Jack: " + jackTestInfo + " , EarPhone: " + earPieceTestInfo + " , Mic: " + micTestInfo + " , Speaker : " + loudSpeakerTestInfo + " , EarSpeaker: " + frontSpeakerTestInfo + " , AutoFocus: " + cameraAutofocusTestInfo + " , PowerKey: " + powerKeyTestInfo + " , VolumeUp : " + volumeKeyUpTestInfo + " , VolumeDown : " + volumeKeyDownTestInfo + " , HomeKey: " + homeKeyTestInfo + " , BackKey: " + backKeyTestInfo + " , MenuKey: " + menuKeyTestInfo + " , Acclerometer: " + accelerometerTestInfo + " , Gyroscope: " + gyroscopeTestInfo + " , Proximity: " + proximityTestInfo + " , VideoTest: " + videoTestInfo + " , FrontVideoTest: " + frontVideoRecoderTest + " , S_penHovering: " + spenhoveringtestinfo + " , Usb: " + usbTestInfo);
        Log.info("TestProcess", "Tests result for Optional -: AirPressure: " + airPressureTestInfo + " , AirTemperature: " + airTemperatureTestInfo + " , Gravity: " + gravityTestInfo + " , IR: " + irTestInfo + " , NFC: " + nfcTestInfo + " , GyroGame: " + gyroscopeForGamingTestInfo + " , OrientationGame: " + orientationTestInfo + " , DeviceTemperature: " + deviceTempTestInfo + " , SetpCount: " + stepCounterTestInfo + " , StepDetect: " + stepDetectorTestInfo + " , Motion: " + motionTestInfo + " , Humidity: " + humidityTestInfo + " , Biometric: " + biomatricTestInfo + " , Hrm: " + hrmTestInfo + " , Spo2: " + hrmTestInfo + " , ScreenLock: " + screenLockTestInfo + " , LED: " + ledNotificationInfo + " , UV: " + uvSensorTestInfo);
        Log.info("TestProcess", "Tests score -: PASS:" + mandatorypasscount + " , FAIL:"
                + mandatoryfailcount + " , TOTAL:" + totalCount + " , SCORE:" + scoreValnew);

        lastState = "0," + lastState;
        optionalTest = "0," + optionalTest;

        data.putExtra("score", scoreValnew);
        data.putExtra("date", System.currentTimeMillis());

        data.putExtra("mandatorydata", lastState);
        data.putExtra("mandatoryjson", mandatoryData.toString());

        data.putExtra("optionaldata", optionalTest);
        data.putExtra("optionaljson", optionalData.toString());

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imeiVal = telephonyManager.getDeviceId();

        root = new JSONObject();
        try {

            String otpVal = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyOTPVal, TestActivity.this);
            if (otpVal == null || otpVal.length() <= 0) {
                String utmotp = CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyUtmOtp, getApplicationContext());
                if (utmotp != null &&


                        utmotp.length() > 0) {
                    root.put("otp", utmotp);
                }
            } else {
                root.put("otp", otpVal);
            }
            String savedvalues = CommSharedPreff.loadStringSavedPreferencescalltype("CallType", getApplicationContext());
            root.put("partnerid", Config.partnerId);
            root.put("CallType", savedvalues);
            root.put("emailId", CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyOTPEmail, TestActivity.this));
            root.put("mobileNo", CommSharedPreff.loadStringSavedPreferences(CommSharedPreff.spKeyOTPPhNo, TestActivity.this));


            reportObj = new JSONObject();

            // Store test performed entry
            {
                JSONObject allTestPerformedObj = new JSONObject();

                // Add up mandatory test list
                {
                    JSONObject testperformObj = new JSONObject();

                    int counter = 0;
                    if (!isDualSim) {
                        counter++;
                        testperformObj.put("Network Signal", new JSONObject("{\"result\":" + networkTestInfo1 + ",\"order\":" + counter + ",\"data\":" + networkTestData1 + "}"));
                    } else {
                        counter++;
                        testperformObj.put("Network Signal SIM 1", new JSONObject("{\"result\":" + networkTestInfo1 + ",\"order\":" + counter + ",\"data\":" + networkTestData1 + "}"));
                    }
                    if (isDualSim) {
                        counter++;
                        testperformObj.put("Network Signal SIM 2", new JSONObject("{\"result\":" + networkTestInfo2 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    }
                    if (!isDualSim) {
                        counter++;
                        testperformObj.put("Call", new JSONObject("{\"result\":" + callTestInfoSim1 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    } else {
                        counter++;
                        testperformObj.put("Call SIM 1", new JSONObject("{\"result\":" + callTestInfoSim1 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                        counter++;
                        testperformObj.put("Call SIM 2", new JSONObject("{\"result\":" + callTestInfoSim2 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    }
                    if (!isDualSim) {
                        counter++;
                        testperformObj.put("SMS", new JSONObject("{\"result\":" + smsTestInfoSim1 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    } else {
                        counter++;
                        testperformObj.put("SMS SIM 1", new JSONObject("{\"result\":" + smsTestInfoSim1 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                        counter++;
                        testperformObj.put("SMS SIM 2", new JSONObject("{\"result\":" + smsTestInfoSim2 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    }
                    counter++;
                    testperformObj.put("WiFi", new JSONObject("{\"result\":" + wifiTestInfo + ",\"order\":" + counter + ",\"data\":" + wifiTestData + "}"));

                    counter++;
                    testperformObj.put("Internet", new JSONObject("{\"result\":" + internetTestInfo + ",\"order\":" + counter + ",\"data\":" + internetTestData + "}"));
                    counter++;
                    testperformObj.put("IMEI", new JSONObject("{\"result\":" + imeiTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Device Non-Rooted", new JSONObject("{\"result\":" + rootTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    if (!isDualSim) {
                        counter++;
                        testperformObj.put("SIM", new JSONObject("{\"result\":" + simTestInfo1 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    } else {
                        counter++;
                        testperformObj.put("SIM Slot 1", new JSONObject("{\"result\":" + simTestInfo1 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    }

                    if (isDualSim) {
                        counter++;
                        testperformObj.put("SIM Slot 2", new JSONObject("{\"result\":" + simTestInfo2 + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    }

                    counter++;
                    testperformObj.put("Back Camera", new JSONObject("{\"result\":" + cameraRearTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Front Camera", new JSONObject("{\"result\":" + cameraFrontTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Flash Light", new JSONObject("{\"result\":" + flashTestinfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Torch Light", new JSONObject("{\"result\":" + torchTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Battery", new JSONObject("{\"result\":" + batteryTestInfo + ",\"order\":" + counter + ",\"data\":" + batteryTestData + "}"));
                    counter++;
                    testperformObj.put("Vibrate", new JSONObject("{\"result\":" + vibratorTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("RAM", new JSONObject("{\"result\":" + ramTestInfo + ",\"order\":" + counter + ",\"data\":" + ramTestData + "}"));
                    counter++;
                    testperformObj.put("Internal Storage", new JSONObject("{\"result\":" + internalStorageTestInfo + ",\"order\":" + counter + ",\"data\":" + internalStorageTestData + "}"));
                    counter++;
                    testperformObj.put("External Storage", new JSONObject("{\"result\":" + externalStorageTestInfo + ",\"order\":" + counter + ",\"data\":" + externalStorageTestData + "}"));
                    counter++;
                    testperformObj.put("Light Sensor", new JSONObject("{\"result\":" + lightTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));

                    counter++;
                    testperformObj.put("Display Touch Panel", new JSONObject("{\"result\":" + screenTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Display Brightness", new JSONObject("{\"result\":" + brightnessTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Display Dead Pixel", new JSONObject("{\"result\":" + pixelTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Bluetooth", new JSONObject("{\"result\":" + btTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Earphone Jack", new JSONObject("{\"result\":" + jackTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Power Button", new JSONObject("{\"result\":" + powerKeyTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Volume-Up Button", new JSONObject("{\"result\":" + volumeKeyUpTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Volume-Down Button", new JSONObject("{\"result\":" + volumeKeyDownTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Home Key", new JSONObject("{\"result\":" + homeKeyTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Back Key", new JSONObject("{\"result\":" + backKeyTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Microphone", new JSONObject("{\"result\":" + micTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("LoudSpeaker", new JSONObject("{\"result\":" + loudSpeakerTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Earphone", new JSONObject("{\"result\":" + earPieceTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Receiver", new JSONObject("{\"result\":" + frontSpeakerTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Camera Auto Focus", new JSONObject("{\"result\":" + cameraAutofocusTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Accelerometer", new JSONObject("{\"result\":" + accelerometerTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Gyroscope", new JSONObject("{\"result\":" + gyroscopeTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Proximity", new JSONObject("{\"result\":" + proximityTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Back Video Recording", new JSONObject("{\"result\":" + videoTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Front Video Recording", new JSONObject("{\"result\":" + frontVideoRecoderTest + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("USB", new JSONObject("{\"result\":" + usbTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));


                    allTestPerformedObj.put("mandatory", testperformObj);
                }

                // Add up optional test list
                {
                    JSONObject testperformObj = new JSONObject();
                    int counter = 0;

                    counter++;
                    testperformObj.put("Air Pressure", new JSONObject("{\"result\":" + airPressureTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Air Temperature", new JSONObject("{\"result\":" + airTemperatureTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Gravity", new JSONObject("{\"result\":" + gravityTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Infrared", new JSONObject("{\"result\":" + irTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("NFC", new JSONObject("{\"result\":" + nfcTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Gyroscope Gaming", new JSONObject("{\"result\":" + gyroscopeForGamingTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Orientation", new JSONObject("{\"result\":" + orientationTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Device Temperature", new JSONObject("{\"result\":" + deviceTempTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Step Detector", new JSONObject("{\"result\":" + stepDetectorTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Step Counter", new JSONObject("{\"result\":" + stepCounterTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Motion Detector", new JSONObject("{\"result\":" + motionTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Humidity", new JSONObject("{\"result\":" + humidityTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Biometric", new JSONObject("{\"result\":" + biomatricTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("HRM", new JSONObject("{\"result\":" + hrmTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));

                    counter++;
                    testperformObj.put("Magnetic Sensor", new JSONObject("{\"result\":" + magneticTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("GPS", new JSONObject("{\"result\":" + gpsTestInfo + ",\"order\":" + counter + ",\"data\":" + gpsTestData + "}"));
                    counter++;
                    testperformObj.put("SPO2", new JSONObject("{\"result\":" + SPO2TestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Menu Key", new JSONObject("{\"result\":" + menuKeyTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Screen Lock", new JSONObject("{\"result\":" + screenLockTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("LED Notification", new JSONObject("{\"result\":" + ledNotificationInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("UV Sensor", new JSONObject("{\"result\":" + uvSensorTestInfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));
                    counter++;
                    testperformObj.put("Stylus Hovering", new JSONObject("{\"result\":" + spenhoveringtestinfo + ",\"order\":" + counter + ",\"data\":" + "{}" + "}"));

                    allTestPerformedObj.put("optional", testperformObj);
                }

                reportObj.put("TESTS_PERFORMED", allTestPerformedObj);
            }

            // Store test result info
            {
                JSONObject testresultObj = new JSONObject();
                testresultObj.put("MANDATORYTESTS_PASS", mandatorypasscount);
                testresultObj.put("MANDATORYTESTS_FAIL", mandatoryfailcount);
                testresultObj.put("MANDATORYTEST_NOTPERFORMED", mandatoryskip);
                testresultObj.put("MANDATORYTEST_NOTTESTED", mandatoryna);

                testresultObj.put("OPTIONALTESTS_PASS", optionalpasscount);
                testresultObj.put("OPTIONALTESTS_FAIL", optionalfailcount);
                testresultObj.put("OPTIONALTEST_NOTPERFORMED", optionalskip);
                testresultObj.put("OPTIONALTEST_NOTTESTED", optionalna);

                System.out.println("TotalCount=" + totalCount);
                testresultObj.put("TESTS_TOTAL", totalCount);
                testresultObj.put("TESTS_SCORE", scoreValnew);
                if (mandatoryfailcount > 0) {
                    testresultObj.put("TESTS_RESULT", "FAIL");
                } else {
                    testresultObj.put("TESTS_RESULT", "PASS");
                }
                reportObj.put("TESTS_RESULT", testresultObj);
            }

            // Store device info
            {
                String androidId = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                String arr[];

                String buildNumber = Build.DISPLAY;
                arr = splitStringEvery(buildNumber, 30);
                String PinValue = CommSharedPreff.loadStringSavedPreferencespin("PinValue", getApplicationContext());
                String pinvalueauto = CommSharedPreff.loadStringSavedPreferencespinqr("PinValueAuto", getApplicationContext());

                System.out.println("Inside pin value=" + pinvalueauto);


                if (buildNumber.length() > 30) {

                    JSONObject deviceinfoObj = new JSONObject();
                    deviceinfoObj.put("PIN", CommSharedPreff.loadStringSavedPreferencespin("PinValue", getApplicationContext()));
                    deviceinfoObj.put("IMEI", imeiVal);
                    deviceinfoObj.put("DEVICE_ID", androidId);
                    deviceinfoObj.put("MAKE", Build.MANUFACTURER + "");
                    deviceinfoObj.put("OS_VERSION", System.getProperty("os.version") + "(" + Build.VERSION.INCREMENTAL + ")");
                    deviceinfoObj.put("OS_API_LEVEL", Build.VERSION.SDK_INT + "");
                    deviceinfoObj.put("DEVICE", Build.DEVICE + "");
                    deviceinfoObj.put("MODEL", Build.MODEL + "");
                    deviceinfoObj.put("BUILDNUMBER1", arr[0].toString() + "");
                    deviceinfoObj.put("BUILDNUMBER2", arr[1].toString() + "");
                    deviceinfoObj.put("CPUArchitecture", System.getProperty("os.arch"));
                    deviceinfoObj.put("CPUCore", getNumCores());
                    deviceinfoObj.put("CPUSpeed", getMaxCPUFreqMHz());
                    String version = System.getProperty("os.version");
                    if (version.contains("cyanogenmod")) {
                        deviceinfoObj.put("RELEASE", "Cynogen " + Build.VERSION.RELEASE + "");
                    } else {
                        deviceinfoObj.put("RELEASE", "Android " + Build.VERSION.RELEASE + "");
                    }
                    deviceinfoObj.put("BRAND", Build.BRAND + "");
                    deviceinfoObj.put("DISPLAY", Build.DISPLAY + "");
                    deviceinfoObj.put("HARDWARE", Build.HARDWARE + "");
                    deviceinfoObj.put("Build_ID", Build.ID + "");
                    deviceinfoObj.put("MANUFACTURER", Build.MANUFACTURER + "");
                    deviceinfoObj.put("SERIAL", Build.SERIAL + "");
                    deviceinfoObj.put("USER", Build.USER + "");
                    deviceinfoObj.put("HOST", Build.HOST + "");

                    reportObj.put("DEVICE_INFO", deviceinfoObj);
                } else {
                    JSONObject deviceinfoObj = new JSONObject();

                    deviceinfoObj.put("PIN", CommSharedPreff.loadStringSavedPreferencespin("PinValue", getApplicationContext()));
                    deviceinfoObj.put("IMEI", imeiVal);
                    deviceinfoObj.put("DEVICE_ID", androidId);
                    deviceinfoObj.put("MAKE", Build.MANUFACTURER + "");
                    deviceinfoObj.put("OS_VERSION", System.getProperty("os.version") + "(" + Build.VERSION.INCREMENTAL + ")");
                    deviceinfoObj.put("OS_API_LEVEL", Build.VERSION.SDK_INT + "");
                    deviceinfoObj.put("DEVICE", Build.DEVICE + "");
                    deviceinfoObj.put("MODEL", Build.MODEL + "");
                    deviceinfoObj.put("BUILDNUMBER1", buildNumber + "");
                    deviceinfoObj.put("BUILDNUMBER2", "");
                    deviceinfoObj.put("CPUArchitecture", System.getProperty("os.arch"));
                    deviceinfoObj.put("CPUCore", getNumCores());
                    deviceinfoObj.put("CPUSpeed", getMaxCPUFreqMHz());
                    String version = System.getProperty("os.version");
                    if (version.contains("cyanogenmod")) {
                        deviceinfoObj.put("RELEASE", "Cynogen " + Build.VERSION.RELEASE + "");
                    } else {
                        deviceinfoObj.put("RELEASE", "Android " + Build.VERSION.RELEASE + "");
                    }
                    deviceinfoObj.put("BRAND", Build.BRAND + "");
                    deviceinfoObj.put("DISPLAY", Build.DISPLAY + "");
                    deviceinfoObj.put("HARDWARE", Build.HARDWARE + "");
                    deviceinfoObj.put("Build_ID", Build.ID + "");
                    deviceinfoObj.put("MANUFACTURER", Build.MANUFACTURER + "");
                    deviceinfoObj.put("SERIAL", Build.SERIAL + "");
                    deviceinfoObj.put("USER", Build.USER + "");
                    deviceinfoObj.put("HOST", Build.HOST + "");

                    reportObj.put("DEVICE_INFO", deviceinfoObj);

                }
            }

            root.put("diagosticreport", reportObj);//changed earlier it was report ,server value changed


            System.out.println("Json value from client=" + reportObj.toString());


        } catch (Exception e) {
            System.out.println("Json value from client with exception=");
            e.printStackTrace();
        }
        try {
            // get the path to sdcard
            File sdcard = Environment.getDataDirectory().getAbsoluteFile();

            System.out.println("Directory save=" + sdcard);
// to this path add a new directory patho
            File dir = new File(sdcard.getAbsolutePath() + "Qutrust");
// create this directory if not already created
            dir.mkdir();
// create the file in which we will write the contents
            File file = new File(dir, "Qutrust.txt");
            FileOutputStream os = new FileOutputStream(file);
            String data = reportObj.toString();
            os.write(data.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("/data/local/tmp/QuTrust", "Qutrust.txt");
            FileOutputStream os = new FileOutputStream(file);
            String data = reportObj.toString();
            ;
            os.write(data.getBytes());
            os.close();
        } catch (IOException e) {
            System.out.println("Vikashinside Io exception0=" + e);
            e.printStackTrace();
        }

        // Store data to shared preff
        SharedPreferences sf = getSharedPreferences("Result", MODE_PRIVATE);
        SharedPreferences.Editor edit = sf.edit();

        edit.putString("root", "" + root);
        edit.putInt("currentCounter", currentTestIndex);
        edit.putFloat("score", mandatorypasscount);
        edit.putFloat("totalcount", totalCount);
        edit.putLong("date", System.currentTimeMillis());

        edit.putString("mandatorydata", lastState);
        edit.putString("mandatoryjson", mandatoryData.toString());

        edit.putString("optionaldata", optionalTest);
        edit.putString("optionaljson", optionalData.toString());

        edit.commit();

//        System.out.println("json value for client1="+mandatoryData.toString());
//        System.out.println("json value for client2="+optionalData.toString());

        if (isOptionalTestStarted == false) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // setContentView(R.layout.optional_testscreen);

                    // ImageView countinue = (ImageView) findViewById(R.id.imageViewContinue);
                    //final ImageView finish = (ImageView) findViewById(R.id.imageViewFinish);
                    // countinue.setOnClickListener(new View.OnClickListener() {
                       /* @Override
                        public void onClick(View view) {*/

                    isOptionalTestStartedbutton = true;
//                        if (MainActivity.mInstance != null) {
//                            MainActivity.mInstance.invokeOptionalTests();
//                        }
//                        finish();

                    System.gc();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //  Toast.makeText(getApplicationContext(),"jhfjsdkfkdfk",Toast.LENGTH_LONG).show();

                            setupTestActivity();
                            System.gc();

                            // Add optional test to list
                            int index = mTestOrder.length - 1;
                            ValueEnumConstants.DeviceTestType tempOrder[] = new ValueEnumConstants.DeviceTestType[70];
                            for (int i = 0; i <= mTestOrder.length - 1; i++) {
                                tempOrder[i] = mTestOrder[i];
                            }

                            if (!nfcenabled) {
                                testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestNFC, getApplicationContext(), TestActivity.this, TestActivity.this);
                                if (!testBase.isTestSettingsDone()) {
                                    if (testBase.isTestActionable(ValueEnumConstants.DeviceTestType.ETestNFC)) {
                                        System.out.println("Actionable NFC");
                                        nfcenabled = true;
                                        index++;
                                        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestNFC;
                                    } else {
                                        nfcTestData = "{}";
                                        nfcTestInfo = -1;
                                    }
                                }
                            }


                            testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestSwingTest, getApplicationContext(), TestActivity.this, TestActivity.this);
                            if (testBase.checkSensor()) {
//
//                                    // Check for swing test
                                index++;
                                tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestSwingTest;
//
//                                    // Check for step detector
                                index++;
                                tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestStepDetector;
//
//                                    // Check for step counter
                                index++;
                                tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestStepCounter;
//
//                                    // Check for motion detector
                                index++;
                                tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestMotionDetector;
                            } else {
                                stepDetectorTestData = "{}";
                                stepDetectorTestInfo = -1;

                                stepCounterTestData = "{}";
                                stepCounterTestInfo = -1;

                                motionTestData = "{}";
                                motionTestInfo = -1;
                            }


                                    /*mHumidityTestObj = new HumidityTest(getApplicationContext(), TestActivity.this);
//
//                                    // Check for humidity
                                    if (mHumidityTestObj.isTestActionable()) {
                                        System.out.println("Actionable Humidity");
                                        index++;
                                        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestHumidity;
//
                                    } else {
                                        humidityTestData = "{}";
                                        humidityTestInfo = -1;
                                    }*/
                            //  mBiometricTestObj = new BiometricTest(getApplicationContext(), TestActivity.this, TestActivity.this);
//                                    // Check for biometric
                            testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestBiometric, getApplicationContext(), TestActivity.this, TestActivity.this);
                            if (testBase.isTestActionable(ValueEnumConstants.DeviceTestType.ETestBiometric)) {
                                System.out.println("Actionable Biometric");
                                index++;
                                tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestBiometric;
                            } else {
                                biomatricTestData = "{}";
                                biomatricTestInfo = -1;
                            }
//
//                                    // Check for hrm
                            //  mHRMTestObj = new HeartRateMonitorTest(getApplicationContext(), TestActivity.this, HeartRateSensorManager);
                            testBase = TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestHRM, getApplicationContext(), TestActivity.this, TestActivity.this);
                            if (testBase.isTestActionable(ValueEnumConstants.DeviceTestType.ETestHRM)) {
                                System.out.println("Actionable HRM");
                                index++;
                                tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestHRM;
                            } else {
                                hrmTestData = "{}";
                                hrmTestInfo = -1;
                            }
                            // Check for network2
                                    /*if (isDualSim) {
                                        index++;
                                        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestNetwork2;
                                    }*/

                            // Check for sim2
                                    /*if (isDualSim) {
                                        index++;
                                        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestSim2;
                                    }*/
                            if (testBase.isTestActionable(ValueEnumConstants.DeviceTestType.ETestHRM)) {
                                System.out.println("Actionable HRM");

                                index++;
                                tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestSPO2;
                            } else {
                                SPO2TestData = "{}";
                                SPO2TestInfo = -1;
                            }

                            index++;
                            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestMenuKey;

                            // Check for screen lock
                            if (powerKeyTestInfo != 0 && powerKeyTestInfo != 1 && powerKeyTestInfo != -1 && powerKeyTestInfo != -2) {
                                index++;
                                tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestScreenLock;
                            } else {
                                screenLockTestInfo = powerKeyTestInfo;
                                screenLockTestData = "{}";
                            }

                            // Check for led
                            //Test commented for partner basis
                            index++;
                            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestLED;

                            // Check for uv
                                   /* mUvTestObj = new UvSensorsTest(getApplicationContext(), TestActivity.this, TestActivity.this);
                                    if (mUvTestObj.isTestActionable()) {
                                        index++;
                                        tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestUvSensor;
                                    } else {
                                        uvSensorTestData = "{}";
                                        uvSensorTestInfo = -1;
                                    }*/
                            // Check for uv
                            //test commented for partner basis
                            index++;
                            tempOrder[index] = ValueEnumConstants.DeviceTestType.ETestS_PENHovering;

                            // Add up optional tests to same order
                            mTestOrder = new ValueEnumConstants.DeviceTestType[index + 1];
                            for (int i = 0; i <= index; i++) {
                                mTestOrder[i] = tempOrder[i];
                            }

                            isOptionalTestStarted = true;
                            currentTestIndex--;
                            totalTests = mTestOrder.length;
                            mTestOrder = tempOrder;

                            System.gc();

                            performNextTest();
                        }
                        // });
                        // }
                    });
                   /* finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setResult(Activity.RESULT_OK);
                            MainActivity.resultObject = root;

                            setResult(Activity.RESULT_OK, data);
                            //clearNotification();
                            finish();
                        }
                    });*/
                }
            });
        } else {
            setResult(Activity.RESULT_OK);
            MainActivity.resultObject = root;
            MainActivity.resultmapfailed=resultmap;

            setResult(Activity.RESULT_OK, data);
            //clearNotification();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // testBase=TestBase.getInstance(ValueEnumConstants.DeviceTestType.ETestAccelerometer,getApplicationContext(),TestActivity.this,TestActivity.this);
        //TrimMemoryClass.wasInBackground = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //clearNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //clearNotification();
    }


    private int getNumCores() {
//Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
//Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
//Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
//Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
//Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
//Print exception
            e.printStackTrace();
//Default to return 1 core
            return 1;
        }
    }

    public static int getMaxCPUFreqMHz() {

        int maxFreq = -1;
        try {

            RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r");

            boolean done = false;
            while (!done) {
                String line = reader.readLine();
                if (null == line) {
                    done = true;
                    break;
                }
                String[] splits = line.split("\\s+");
                assert (splits.length == 2);
                int timeInState = Integer.parseInt(splits[1]);
                if (timeInState > 0) {
                    int freq = Integer.parseInt(splits[0]) / 1000;
                    if (freq > maxFreq) {
                        maxFreq = freq;
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return maxFreq;
    }

    private String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double) interval)));
        String[] result = new String[arrayLength];
        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } // Add the last bit
        result[lastIndex] = s.substring(j);
        return result;

    }

}
