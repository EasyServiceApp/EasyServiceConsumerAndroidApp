package com.service.easyservice.util;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Smile on 09-04-2017.
 */
@ReportsCrashes(formUri = "", // will not be used
        mailTo = "visaninasir@gmail.com", // my email here
        mode = ReportingInteractionMode.SILENT
)

public class MyErrorApplication extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }

}
