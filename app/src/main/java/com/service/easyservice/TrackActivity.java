package com.service.easyservice;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.service.easyservice.models.LocationInfo;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.util.Constants;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TrackActivity extends FragmentActivity implements OnMapReadyCallback,Constants,ResponseResult {

    private GoogleMap mMap;

    private Bundle extras;

    private String requestId;

    private Marker deliveryManMarker;

    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        extras = getIntent().getExtras();
        if(null != extras)
        {
            requestId = extras.getString("requestId", "");
        }



        timer.scheduleAtFixedRate(new LocationUpdateTask(), 0, 15000);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private class LocationUpdateTask extends TimerTask
    {



        @Override
        public void run() {

            if(CommonFunctions.isNetworkAvailable(TrackActivity.this)) {
                //get location
                Map<String, String> requestServiceParameters = new HashMap<>();
                requestServiceParameters.put("appapi", "yes");
                requestServiceParameters.put("request_id", requestId);
                new VolleyJSONCaller(TrackActivity.this, ENGINEER_LOCATION_URL, requestServiceParameters, Request.Method.POST, true).execute();
            }
        }
    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof LocationInfo)
        {
            LocationInfo locationInfo = (LocationInfo)result;

            if (locationInfo.getStatus() == 1) {

                LatLng deliveryManAddressLatLong = new LatLng(Float.valueOf(locationInfo.getLat()), Float.valueOf(locationInfo.getLong()));

                if(deliveryManMarker == null)
                {
                    deliveryManMarker = mMap.addMarker(new MarkerOptions().position(deliveryManAddressLatLong));
                }
                else
                {
                    deliveryManMarker.setPosition(deliveryManAddressLatLong);
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(deliveryManAddressLatLong));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            } else {
                CommonFunctions.displayDialog(this, locationInfo.getMessage());
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(timer != null)
        {
            timer.cancel();
        }
    }
}
