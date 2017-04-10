package com.service.easyservice;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.service.easyservice.util.GPSTracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.service.easyservice.R.id.map;

public class AddressMapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener, View.OnClickListener {

    TextView tvFooter,tvAddress;
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_map);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        tvFooter = (TextView)findViewById(R.id.tvFooter);
        tvAddress = (TextView)findViewById(R.id.tvAddress);
        tvFooter.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
// Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
//        LatLng sydney = new LatLng(-33.852, 151.211);
        LatLng latLng1 = new LatLng(-33.852, 151.211);
        GPSTracker tracker = new GPSTracker(this);
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        } else {

            latLng1 = new LatLng(tracker.getLatitude(),tracker.getLongitude());

        }

        googleMap.addMarker(new MarkerOptions().position(latLng1)
                .title("")).setDraggable(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        setAddress(latLng1);

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker markerDragStart) {

            }

            @Override
            public void onMarkerDragEnd(Marker markerDragEnd) {
                latLng = markerDragEnd.getPosition();
                Log.e("Latitude",latLng.latitude+"");
                Log.e("Longitude",latLng.longitude+"");
                setAddress(latLng);
                String url = String
                        .format(Locale.ENGLISH,"http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=true&language="
                                + Locale.getDefault().getCountry(), latLng.latitude, latLng.longitude);
                Log.e("Url",url);
            }

            @Override
            public void onMarkerDrag(Marker markerDrag) {

            }
        });
    }

    public String setAddress(LatLng latLng)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        String addressStr = "";
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            //check if the address are available
            if(addresses !=null && addresses.size()>0)
            {
                Address address = addresses.get(0);
                for(int i = 0; i<address.getMaxAddressLineIndex();i++)
                {
                    addressStr = addressStr + "\n"+address.getAddressLine(i);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        tvAddress.setText(addressStr.trim());
        return addressStr.trim();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvFooter:
                //send the address back to user
                Intent resultIntent = new Intent();
                // TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra("address", tvAddress.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
