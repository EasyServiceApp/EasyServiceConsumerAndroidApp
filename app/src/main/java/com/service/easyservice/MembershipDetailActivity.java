package com.service.easyservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.service.easyservice.util.CommonFunctions;

public class MembershipDetailActivity extends AppCompatActivity implements View.OnClickListener {

    WebView webView;
    String url = "";
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_detail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView)toolbar.findViewById(R.id.ivProfile);
        ivProfile.setVisibility(View.GONE);
        ivDrawerHandel = (ImageView)toolbar.findViewById(R.id.ivDrawerHandel);
        ivDrawerHandel.setImageResource(R.drawable.toolbar_back);
        ivDrawerHandel.setOnClickListener(this);
        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);

        webView = (WebView)findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        url = getIntent().getStringExtra("url");

        loadURL();

    }

    public void loadURL()
    {
        if("".equals(url))
        {
            url = "http://www.google.com";
        }

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(MembershipDetailActivity.this);
                break;
            case R.id.ivDrawerHandel:
                finish();
                break;

        }
    }
}
