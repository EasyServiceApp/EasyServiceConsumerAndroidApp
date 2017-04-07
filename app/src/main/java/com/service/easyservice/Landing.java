package com.service.easyservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.service.easyservice.fragments.AboutUs;
import com.service.easyservice.fragments.Dashboard;
import com.service.easyservice.fragments.Login;
import com.service.easyservice.fragments.Membership;
import com.service.easyservice.fragments.MyDevices;
import com.service.easyservice.fragments.MyRequest;
import com.service.easyservice.fragments.Profile;
import com.service.easyservice.fragments.Support;
import com.service.easyservice.util.AppPreferences;


public class Landing extends AppCompatActivity implements View.OnClickListener {



    private Toolbar toolbar;                              // Declaring the Toolbar Object

    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    RelativeLayout rlLeftDrawer;

    Bundle bundle;

   // ActionBarDrawerToggle mDrawerToggle;


    AppPreferences appPreferences;

    int DASHBOARD = 0, LOGIN = 2,PROFILE = 1,MYDEVICES = 3,ABOUTUS = 4,SUPPORT = 5, MEMBERSHIP = 6,SERVICEHISTORY = 7;
    int selectedScreen = 0;

    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;

    TextView tvMyAccount,tvMyProfile,tvMyDevices,tvAboutEasyService,tvSupport,tvMembership,tvServiceHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        init();



    }

    protected void init()
    {
        //=============Drawer code Start========================================
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView)toolbar.findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(this);
        ivDrawerHandel = (ImageView)toolbar.findViewById(R.id.ivDrawerHandel);
        ivDrawerHandel.setOnClickListener(this);
        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);
        rlLeftDrawer = (RelativeLayout)findViewById(R.id.rlLeftDrawer);
        tvMyAccount = (TextView) findViewById(R.id.tvMyAccount);
        tvMyAccount.setOnClickListener(this);
        tvMyProfile = (TextView) findViewById(R.id.tvMyProfile);
        tvMyProfile.setOnClickListener(this);
        tvMyDevices = (TextView) findViewById(R.id.tvMyDevices);
        tvMyDevices.setOnClickListener(this);
        tvMembership = (TextView) findViewById(R.id.tvMembership);
        tvMembership.setOnClickListener(this);
        tvAboutEasyService = (TextView) findViewById(R.id.tvAboutEasyService);
        tvAboutEasyService.setOnClickListener(this);
        tvServiceHistory = (TextView) findViewById(R.id.tvServiceHistory);
        tvServiceHistory.setOnClickListener(this);

        tvSupport = (TextView) findViewById(R.id.tvSupport);
        tvSupport.setOnClickListener(this);

        rlLeftDrawer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        appPreferences = new AppPreferences(this);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view

        //initialise the frame with dashboard
        setDashboardPage();
        appPreferences.addServiceDevice(null);

    }





    @Override
    public void onClick(View view) {


        switch (view.getId())
        {
            case R.id.ivDrawerHandel:
                //handel drawer open close
                if (Drawer.isDrawerVisible(GravityCompat.START)) {
                    Drawer.closeDrawer(GravityCompat.START);
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    int backStackEntryCount = fragmentManager.getBackStackEntryCount();
                    if(backStackEntryCount > 1)
                    {
                        onBackPressed();
                    }
                    else
                        Drawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.ivProfile:
                //open login page if not logged in else open profile page
                setProfilePage();
                Drawer.closeDrawer(rlLeftDrawer);
                break;
            case R.id.ivToolbarHome:
            case R.id.tvMyAccount:
                //open login page if not logged in else open profile page
                setDashboardPage();
                Drawer.closeDrawer(rlLeftDrawer);
                break;
            case R.id.tvMyProfile:
                //open login page if not logged in else open profile page
                setProfilePage();
                Drawer.closeDrawer(rlLeftDrawer);
                break;
            case R.id.tvMyDevices:
                setMyDevicesPage();
                Drawer.closeDrawer(rlLeftDrawer);
                break;
            case R.id.tvMembership:
                setMembershipPage();
                Drawer.closeDrawer(rlLeftDrawer);
                break;
            case R.id.tvAboutEasyService:
                setAboutUsPage();
                Drawer.closeDrawer(rlLeftDrawer);
                break;
            case R.id.tvSupport:
                setSupportPage();
                Drawer.closeDrawer(rlLeftDrawer);
                break;
            case R.id.tvServiceHistory:
                setServiceHistory();
                Drawer.closeDrawer(rlLeftDrawer);
                break;


            default:

        }

    }



    public void setDashboardPage()
    {
        Dashboard fragment = new Dashboard();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("");
        selectedScreen = DASHBOARD;
    }

    public void setLoginPage()
    {
        //give alert to login and proceed further to login page
        if(selectedScreen != LOGIN) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage("Please register to continue.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Login fragment = new Login();
                            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame, fragment);
                            fragmentTransaction.commit();
                            getSupportActionBar().setTitle("");
                            selectedScreen = LOGIN;
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

    }

    public void setProfilePage()
    {
        //check if the user is logged in or not
        if(new AppPreferences(this).isUserLoggedIn())
        {
            Profile fragment = new Profile();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("");
            selectedScreen = PROFILE;
        }
        else {
            Login fragment = new Login();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("");
            selectedScreen = LOGIN;
        }


    }

    public void setMembershipPage()
    {
        //check if the user is logged in or not
        if(new AppPreferences(this).isUserLoggedIn())
        {
            Membership fragment = new Membership();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("");
            selectedScreen = MEMBERSHIP;
        }
        else {
            setLoginPage();
        }


    }

    public void setMyDevicesPage()
    {
        //check if the user is logged in or not
        if(new AppPreferences(this).isUserLoggedIn()) {
            MyDevices fragment = new MyDevices();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("");
            selectedScreen = MYDEVICES;
        }
        else {
            setLoginPage();
        }
    }

    public void setAboutUsPage()
    {
        AboutUs fragment = new AboutUs();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("");
        selectedScreen = ABOUTUS;
    }
    public void setSupportPage() {
        //check if the user is logged in or not
        if(new AppPreferences(this).isUserLoggedIn()) {
            Support fragment = new Support();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("");
            selectedScreen = SUPPORT;
        }else {
            setLoginPage();
        }
    }

    public void setServiceHistory() {
        //check if the user is logged in or not
        if(new AppPreferences(this).isUserLoggedIn()) {
            MyRequest fragment = new MyRequest();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("");
            selectedScreen = SERVICEHISTORY;
        }else {
            setLoginPage();
        }
    }



    @Override
    public void onBackPressed() {
        if(DASHBOARD == selectedScreen)
        {
            finish();
        }
        else
        {
            //display dashboard
            setDashboardPage();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setDashboardPage();

    }


}
