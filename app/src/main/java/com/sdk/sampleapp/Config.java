package com.sdk.sampleapp;

/**
 * Created by gargi on 7/8/15.
 */
public class Config {

    // 4 - quikr
    // 5 - qutrust
    // 6 - reliance

    // India- 18 prabudh
    // UAE - 19 prabudh
    // YNew-24 date:21 july

    //Internal testing
    public static String devversion ="prabudh_UAE_3";
    public static boolean isMpHsBuild=true;

    public static boolean isOfflineBuild=true;
    public static String partnerId = "24";

    public static String baseUrl = "http://devicesapi.moonglabs.com";
    public static String deviceCertificationReportURL = "/devicecertification/report";
    public static String certificationqutrust ="http://devicesapi.moonglabs.com/Certificates/submitreport";

    // public static String deviceCertificationOTPURL = "/devicecertification/getOTPDetails";
    public static String deviceCertificationOTPURL = "/1.1/devicecertification/getOTPDetails";
    public static String LIcensePinValidationUrl = "/login";


    public static String deviceCertificationRegisterURL = "/devicecertification/register";
    public static String uploadLogURL = "/logmanager/uploadlog";
    public static String IMEIValidationUrl = "http://stgdevices.moonglabs.com/validateimei";


}

