package com.service.easyservice;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.AddApplianceResponse;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.util.Constants;
import com.service.easyservice.volley.MultipartRequest;
import com.service.easyservice.volley.VolleySingleton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener,Constants,Response.Listener<String>,Response.ErrorListener {

    TextView tvBrand,tvFooter,tvModel,tvIMEI;
    ImageView ivCategory;
    EditText etModel;
    EditText etIMEI;
    RadioGroup rgWarranty;
    static EditText etPurchaseDate;
    ImageView ivInvoiceImage1,ivInvoiceImage2,ivInvoiceImage3,deleteFirstInvoiceImage,deleteSecondInvoiceImage,deleteThirdInvoiceImage;
    private File photoFile1, photoFile2, photoFile3;
    private final int REQUEST_CAMERA_IMAGE1 = 1, REQUEST_CAMERA_IMAGE2 = 2, REQUEST_CAMERA_IMAGE3 = 3;
    private FileBody cameraFb1, cameraFb2, cameraFb3;
    private ProgressDialog loading;
    private Myappliance myappliance;
    Gson gson = new Gson();
    Type type = new TypeToken<Myappliance>() {}.getType();
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;
    String filep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        String myApplianceStr = getIntent().getStringExtra("myappliance");

        myappliance = gson.fromJson(myApplianceStr,type);

        init();

    }

    public void init()
    {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView)toolbar.findViewById(R.id.ivProfile);
        ivProfile.setVisibility(View.GONE);
        ivDrawerHandel = (ImageView)toolbar.findViewById(R.id.ivDrawerHandel);
        ivDrawerHandel.setImageResource(R.drawable.toolbar_back);
        ivDrawerHandel.setOnClickListener(this);
        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);

        ivCategory = (ImageView)findViewById(R.id.ivCategory);

        tvBrand = (TextView)findViewById(R.id.tvBrand);
        tvFooter = (TextView)findViewById(R.id.tvFooter);
        tvModel = (TextView)findViewById(R.id.tvModel);
        tvIMEI = (TextView)findViewById(R.id.tvIMEI);
        tvFooter.setOnClickListener(this);
        etModel = (EditText) findViewById(R.id.etModel);
        etIMEI = (EditText) findViewById(R.id.etIMEI);
        rgWarranty = (RadioGroup) findViewById(R.id.rgWarranty);
        etPurchaseDate = (EditText) findViewById(R.id.etPurchaseDate);
        etPurchaseDate.setOnClickListener(this);
        ivInvoiceImage1 = (ImageView) findViewById(R.id.ivInvoiceImage1);
        ivInvoiceImage1.setOnClickListener(this);
        ivInvoiceImage2 = (ImageView) findViewById(R.id.ivInvoiceImage2);
        ivInvoiceImage2.setOnClickListener(this);
        ivInvoiceImage3 = (ImageView) findViewById(R.id.ivInvoiceImage3);
        ivInvoiceImage3.setOnClickListener(this);
        deleteFirstInvoiceImage = (ImageView) findViewById(R.id.deleteFirstInvoiceImage);
        deleteFirstInvoiceImage.setOnClickListener(this);
        deleteSecondInvoiceImage = (ImageView) findViewById(R.id.deleteSecondInvoiceImage);
        deleteSecondInvoiceImage.setOnClickListener(this);
        deleteThirdInvoiceImage = (ImageView) findViewById(R.id.deleteThirdInvoiceImage);
        deleteThirdInvoiceImage.setOnClickListener(this);
        loading = new ProgressDialog(this);

        //populate brand and category

        //if model is others then display model input

        if("others".equalsIgnoreCase(myappliance.getModel()))
        {
            tvBrand.setText(myappliance.getBrand());

        }
        else
        {
            tvBrand.setText(myappliance.getBrand()+"\n"+myappliance.getModel());
            tvModel.setVisibility(View.GONE);
            etModel.setVisibility(View.GONE);
            etModel.setText(myappliance.getModel());
        }

        //set category image
        CommonFunctions.setCategoryImage(ivCategory,myappliance.getCategory());

        //if category is not mobile change label of serial no to serial no
        if(!"mobile".equalsIgnoreCase(myappliance.getCategory()))
        {
            tvIMEI.setText("Serial No.");
        }

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            DatePickerDialog datePickerDialog;
            if(!"".equals(etPurchaseDate.getText().toString().trim())) {
                String[] date = etPurchaseDate.getText().toString().split("/");
                int year = Integer.parseInt(date[2]);
                int month = Integer.parseInt(date[1]) - 1;
                int day = Integer.parseInt(date[0]);

                // Create a new instance of DatePickerDialog and return it
                datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            }
            else
            {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTime().getTime());
            datePickerDialog.getDatePicker().setSpinnersShown(true);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            etPurchaseDate.setText(day + "/" + (month + 1) + "/" + year);

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(AddDeviceActivity.this);
                break;
            case R.id.etPurchaseDate:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.ivInvoiceImage1:
                CommonFunctions.getPicFromCameraGallery(this, REQUEST_CAMERA_IMAGE1);
                break;
            case R.id.ivInvoiceImage2:
                CommonFunctions.getPicFromCameraGallery(this, REQUEST_CAMERA_IMAGE2);
                break;
            case R.id.ivInvoiceImage3:
                CommonFunctions.getPicFromCameraGallery(this, REQUEST_CAMERA_IMAGE3);
                break;
            case R.id.deleteFirstInvoiceImage:
                ivInvoiceImage1.setImageResource(R.drawable.add_image);
                photoFile1 = null;
                cameraFb1 = null;
                deleteFirstInvoiceImage.setVisibility(View.INVISIBLE);
                break;
            case R.id.deleteSecondInvoiceImage:
                ivInvoiceImage2.setImageResource(R.drawable.add_image);
                photoFile2 = null;
                cameraFb2 = null;
                deleteSecondInvoiceImage.setVisibility(View.INVISIBLE);
                break;
            case R.id.deleteThirdInvoiceImage:
                ivInvoiceImage3.setImageResource(R.drawable.add_image);
                photoFile3 = null;
                cameraFb3 = null;
                deleteThirdInvoiceImage.setVisibility(View.INVISIBLE);
                break;

            case R.id.tvFooter:
                //store details on server and finish the activity
                Map<String, String> addApplianceParameters = new HashMap<>();
                addApplianceParameters.put("appapi", "yes");
                addApplianceParameters.put("category_id", myappliance.getCategory());
                addApplianceParameters.put("subcategory_id", myappliance.getSubcategoryId());
                addApplianceParameters.put("brand_id", myappliance.getBrand());
                addApplianceParameters.put("store_id", "36");
                addApplianceParameters.put("model_id", etModel.getText().toString());
                addApplianceParameters.put("serial_no", etIMEI.getText().toString().trim());
                addApplianceParameters.put("warranty", ((RadioButton)findViewById(rgWarranty.getCheckedRadioButtonId())).getText().toString());
                addApplianceParameters.put("purchase_date", etPurchaseDate.getText().toString().trim());
                addApplianceParameters.put("user_id", new AppPreferences(this).getUserInfo().getUserId());

                List<FileBody> fileBodies = new ArrayList<>();
                List<String> fileBodiesKey = new ArrayList<>();

                if(null != cameraFb1)
                {
                    fileBodies.add(cameraFb1);
                    fileBodiesKey.add("invoice1");
                }

                if(null != cameraFb2)
                {
                    fileBodies.add(cameraFb2);
                    fileBodiesKey.add("invoice2");
                }

                if(null != cameraFb3)
                {
                    fileBodies.add(cameraFb3);
                    fileBodiesKey.add("invoice3");
                }

                MultipartRequest multipartRequest = new MultipartRequest(this, ADD_APPLIANCE_ACTION, ADD_APPLIANCE_USER_URL,this,this,fileBodies,fileBodiesKey, addApplianceParameters);
                multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loading.setMessage("Please wait...");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
                VolleySingleton.getInstance(this).addToRequestQueue(multipartRequest);
                break;

            case R.id.ivDrawerHandel:
                finishActivity();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA_IMAGE1 + REQUEST_CAMERA || requestCode == REQUEST_CAMERA_IMAGE1 + SELECT_FILE) {

                if (requestCode == REQUEST_CAMERA_IMAGE1 + REQUEST_CAMERA) {
                    photoFile1 = CommonFunctions.photoFile;
                    cameraFb1 = new FileBody(photoFile1);
                    Log.e("File Path", photoFile1.getAbsolutePath());
                    CommonFunctions.setPic(photoFile1.getAbsolutePath(), ivInvoiceImage1, this);
                    //Picasso.with(this).load(photoFile1).fit().into(ivInvoiceImage1);

                } else {
                    Uri selectedImageUri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                    if(cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filep = cursor.getString(columnIndex);
                        if(filep.startsWith("http") || filep.startsWith("https"))
                        {
                            photoFile1 = CommonFunctions.photoFile;
                            cameraFb1 = new FileBody(photoFile1);
                            Log.e("File Path", photoFile1.getAbsolutePath());
//                        CommonFunctions.setPic(photoFile1.getAbsolutePath(), ivInvoiceImage1, this);
                            Picasso.with(this).load(selectedImageUri).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    CommonFunctions.saveBitmap(photoFile1, bitmap);
                                    ivInvoiceImage1.setImageBitmap(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });
                        }
                        else {
                            cameraFb1 = new FileBody(new File(filep));
                            CommonFunctions.setPic(filep, ivInvoiceImage1, this);
                            cursor.close();
                        }
                    }
                    else {
                        photoFile1 = new File(selectedImageUri.getPath());
                        cameraFb1 = new FileBody(photoFile1);
                        Log.e("File Path", photoFile1.getAbsolutePath());
//                        CommonFunctions.setPic(photoFile1.getAbsolutePath(), ivInvoiceImage1, this);
                        Picasso.with(this).load(selectedImageUri).fit().into(ivInvoiceImage1);
                    }
                }

                deleteFirstInvoiceImage.setVisibility(View.VISIBLE);

            } else if (requestCode == REQUEST_CAMERA_IMAGE2 + REQUEST_CAMERA || requestCode == REQUEST_CAMERA_IMAGE2 + SELECT_FILE) {
                if (requestCode == REQUEST_CAMERA_IMAGE2 + REQUEST_CAMERA) {
                    photoFile2 = CommonFunctions.photoFile;
                    cameraFb2 = new FileBody(photoFile2);
                    Log.e("File Path 2", photoFile2.getAbsolutePath());
//                    CommonFunctions.setPic(photoFile2.getAbsolutePath(), ivInvoiceImage2, this);
                    Picasso.with(this).load(photoFile2).fit().into(ivInvoiceImage2);

                } else  {
                    Uri selectedImageUri = data.getData();
                    String[] filePathColumn = {MediaStore.MediaColumns.DATA};

                    Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                    if(cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filep = cursor.getString(columnIndex);
                        if(filep.startsWith("http") || filep.startsWith("https"))
                        {
                            photoFile2 = CommonFunctions.photoFile;
                            cameraFb2 = new FileBody(photoFile2);
                            Log.e("File Path", photoFile2.getAbsolutePath());
//                        CommonFunctions.setPic(photoFile1.getAbsolutePath(), ivInvoiceImage1, this);
                            Picasso.with(this).load(selectedImageUri).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    CommonFunctions.saveBitmap(photoFile2, bitmap);
                                    ivInvoiceImage2.setImageBitmap(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });
                        }
                        else {
                            cameraFb2 = new FileBody(new File(filep));
                            CommonFunctions.setPic(filep, ivInvoiceImage2, this);
                            cursor.close();
                        }
                    }
                    else {
                        photoFile2 = new File(selectedImageUri.getPath());
                        cameraFb2 = new FileBody(photoFile2);
                        Log.e("File Path", photoFile2.getAbsolutePath());
//                        CommonFunctions.setPic(photoFile1.getAbsolutePath(), ivInvoiceImage1, this);
                        Picasso.with(this).load(selectedImageUri).fit().into(ivInvoiceImage2);
                    }
                }
                deleteSecondInvoiceImage.setVisibility(View.VISIBLE);

            } else if (requestCode == REQUEST_CAMERA_IMAGE3 + REQUEST_CAMERA || requestCode == REQUEST_CAMERA_IMAGE3 + SELECT_FILE) {
                if (requestCode == REQUEST_CAMERA_IMAGE3 + REQUEST_CAMERA) {
                    photoFile3 = CommonFunctions.photoFile;
                    cameraFb3 = new FileBody(photoFile3);
                    Log.e("File Path 3", photoFile3.getAbsolutePath());
//                    CommonFunctions.setPic(photoFile3.getAbsolutePath(), ivInvoiceImage3, this);
                    Picasso.with(this).load(photoFile3).fit().into(ivInvoiceImage3);

                } else if (requestCode == REQUEST_CAMERA_IMAGE3 + SELECT_FILE) {
                    Uri selectedImageUri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                    if(cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filep = cursor.getString(columnIndex);
                        if(filep.startsWith("http") || filep.startsWith("https"))
                        {
                            photoFile3 = CommonFunctions.photoFile;
                            cameraFb3 = new FileBody(photoFile3);
                            Log.e("File Path", photoFile3.getAbsolutePath());
//                        CommonFunctions.setPic(photoFile1.getAbsolutePath(), ivInvoiceImage1, this);
                            Picasso.with(this).load(selectedImageUri).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    CommonFunctions.saveBitmap(photoFile3, bitmap);
                                    ivInvoiceImage3.setImageBitmap(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });
                        }
                        else {
                            cameraFb3 = new FileBody(new File(filep));
                            CommonFunctions.setPic(filep, ivInvoiceImage3, this);
                            cursor.close();
                        }
                    }
                    else {
                        photoFile3 = new File(selectedImageUri.getPath());
                        cameraFb3 = new FileBody(photoFile3);
                        Log.e("File Path", photoFile3.getAbsolutePath());
//                        CommonFunctions.setPic(photoFile1.getAbsolutePath(), ivInvoiceImage1, this);
                        Picasso.with(this).load(selectedImageUri).fit().into(ivInvoiceImage3);
                    }
                }
                deleteThirdInvoiceImage.setVisibility(View.VISIBLE);
            }
        }
        if(CommonFunctions.photoFile.length() == 0)
        {
            CommonFunctions.photoFile.delete();
        }
    }

    @Override
    public void onResponse(String response) {
        loading.dismiss();
        Type type = new TypeToken<AddApplianceResponse>(){}.getType();
        Gson gson = new Gson();
        AddApplianceResponse addApplianceResponse = gson.fromJson(response, type);
        if (1 == addApplianceResponse.getStatus()) {
            //set model list and accordingly store list
            Toast.makeText(this, addApplianceResponse.getMessage(), Toast.LENGTH_SHORT).show();

            finish();

        } else {
            CommonFunctions.displayDialog(this, addApplianceResponse.getMessage());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Log.e("Response Error", error.getMessage());
        loading.dismiss();
        CommonFunctions.displayDialog(this, RESPONSE_ISSUE_MESSAGE);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        finishActivity();
    }

    public void finishActivity()
    {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Are you sure you want to cancel Adding Device?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
