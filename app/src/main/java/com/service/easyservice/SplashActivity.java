package com.service.easyservice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.util.Constants;


public class SplashActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CommonFunctions.setLocale(new AppPreferences(getApplicationContext()).getUserLanguage(), getApplicationContext());
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                CommonFunctions.navigateToHome(SplashActivity.this);
            }
        }, SPLASH_DURATION);


    }

}
