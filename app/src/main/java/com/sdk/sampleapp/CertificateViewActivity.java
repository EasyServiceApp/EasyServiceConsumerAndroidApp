package com.sdk.sampleapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.mlapps.truevaluesdk.CommSharedPreff;
import com.service.easyservice.R;

import java.io.File;

@SuppressWarnings("deprecation")
public class CertificateViewActivity extends ActionBarActivity {

    WebView webview;
    ProgressDialog progressBar;
    Uri path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_view);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_action_orange)));
        webview = (WebView) findViewById(R.id.webViewcerti);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        progressBar = ProgressDialog.show(CertificateViewActivity.this, "Loading Certificate.", "Please wait...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("the value of the pdf file is   " + url);

                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                System.out.println("the value of the pdf file is   " + description);
                System.out.println("the value of the pdf file is   "+failingUrl);

                System.out.println("the value of the pdf file is   " + errorCode);
                System.out.println("the value of the pdf file is   "+view);

                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(CertificateViewActivity.this);
                builder.setTitle("Error");
                builder.setMessage(description);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.show();
            }
        });


        String arr_check[]= CommSharedPreff.loadCertificateData(CertificateViewActivity.this);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/qutrust/"+arr_check[2]+".pdf");
        path = Uri.fromFile(file);
       // System.out.println("the value of the pdf file is   "+path.toString());

        String str = path.toString();
    //    webview.loadUrl(path.toString());
        webview.getSettings().setBuiltInZoomControls(true);
       webview.loadUrl("https://docs.google.com/gview?embedded=true&url="+arr_check[1]);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.home:
                finish();
                break;
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
