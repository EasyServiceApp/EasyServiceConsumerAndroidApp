package com.service.easyservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.service.easyservice.util.TouchImageView;
import com.squareup.picasso.Picasso;

public class ImageZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        TouchImageView ivZoomed = (TouchImageView)findViewById(R.id.ivZoomed);

        Bundle extras = getIntent().getExtras();

        if(extras != null && extras.containsKey("image"))
        {
            Picasso.with(this).load(extras.getString("image")).fit().into(ivZoomed);
        }

    }
}
