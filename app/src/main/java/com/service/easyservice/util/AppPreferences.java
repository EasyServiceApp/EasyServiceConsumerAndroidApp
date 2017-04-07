package com.service.easyservice.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.models.OTPVerifyResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AppPreferences
{	 
	 private SharedPreferences  appSharedPrefs;
	 private SharedPreferences.Editor prefsEditor;

	private static final String USER_LANGUAGE = "language";
	private static final String SELF_DEVICE_ID = "self_device_id";
	private static final String MY_DEVICES = "my_devices";
	private static final String USER_INFO = "user_info";
	private static final String SELF_DEVICE_ADDED = "self_device_added";
	private static final String SERVICE_DEVICE = "service_device";

	 public AppPreferences(Context paramContext)
	 {
		this.appSharedPrefs = paramContext.getSharedPreferences(paramContext.getApplicationContext().getPackageName(), 0);
		this.prefsEditor = this.appSharedPrefs.edit();
	 }

	public void setUserLanguage(String language)
	{
		this.prefsEditor.putString(USER_LANGUAGE, language);
		this.prefsEditor.commit();
	}

	public String getUserLanguage()
	{
		return appSharedPrefs.getString(USER_LANGUAGE,"en");
	}
	public void setSelfDevice(String selfDevice)
	{
		this.prefsEditor.putString(SELF_DEVICE_ID, selfDevice);
		this.prefsEditor.commit();
	}

	public String getSelfDevice()
	{
		return appSharedPrefs.getString(SELF_DEVICE_ID,"");
	}




    public void clearPreference()
    {
        prefsEditor.clear();
        this.prefsEditor.commit();
    }


	public void addDevice(Myappliance device)
	{
		List<Myappliance> devices  = getDevices();
		devices.clear();
		devices.add(device);

		//store devices
		Type type = new TypeToken<List<Myappliance>>(){}.getType();
		Gson gson = new Gson();

		this.prefsEditor.putString(MY_DEVICES, gson.toJson(devices,type));
		this.prefsEditor.commit();

	}

	public List<Myappliance> getDevices()
	{
		List<Myappliance> devices  = new ArrayList<>() ;
		Type type = new TypeToken<List<Myappliance>>(){}.getType();
		Gson gson = new Gson();

		String storedDevices = this.appSharedPrefs.getString(MY_DEVICES,"");

		if(!"".equals(storedDevices)){
			devices = gson.fromJson(storedDevices,type);
		}

		return devices;
	}
	public void addServiceDevice(Myappliance device)
	{

		//store devices
		Type type = new TypeToken<Myappliance>(){}.getType();
		Gson gson = new Gson();

		if(null != device) {
			this.prefsEditor.putString(SERVICE_DEVICE, gson.toJson(device, type));
		}
		else
		{
			this.prefsEditor.putString(SERVICE_DEVICE, "");
		}
		this.prefsEditor.commit();

	}

	public Myappliance getServiceDevices()
	{
		Myappliance device  = null ;
		Type type = new TypeToken<Myappliance>(){}.getType();
		Gson gson = new Gson();

		String storedDevices = this.appSharedPrefs.getString(SERVICE_DEVICE,"");

		if(!"".equals(storedDevices)){
			device = gson.fromJson(storedDevices,type);
		}

		return device;
	}


	public void setUserInfo(OTPVerifyResponse otpVerifyResponse)
	{
		//store devices
		Type type = new TypeToken<OTPVerifyResponse>(){}.getType();
		Gson gson = new Gson();

		this.prefsEditor.putString(USER_INFO, gson.toJson(otpVerifyResponse,type));
		this.prefsEditor.commit();

	}

	public OTPVerifyResponse getUserInfo()
	{
		OTPVerifyResponse otpVerifyResponse  = null;
		Type type = new TypeToken<OTPVerifyResponse>(){}.getType();
		Gson gson = new Gson();

		String storedDevices = this.appSharedPrefs.getString(USER_INFO,"");

		if(!"".equals(storedDevices)){
			otpVerifyResponse = gson.fromJson(storedDevices,type);
		}

		return otpVerifyResponse;
	}

	public boolean isUserLoggedIn()
	{
		boolean isUserLoggedIn = false;

		String storedDevices = this.appSharedPrefs.getString(USER_INFO,"");

		if(!"".equals(storedDevices)){
			isUserLoggedIn = true;
		}

		return isUserLoggedIn;
	}
	public boolean isSelfDeviceAdded()
	{

		return this.appSharedPrefs.getBoolean(SELF_DEVICE_ADDED,false);
	}
	public void setSelfDeviceAdded()
	{
		this.prefsEditor.putBoolean(SELF_DEVICE_ADDED, true);
		this.prefsEditor.commit();
	}
}
