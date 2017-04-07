package com.service.easyservice.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.service.easyservice.Landing;
import com.service.easyservice.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Smile on 17-04-2016.
 */
 public class CommonFunctions implements Constants {
    public static File photoFile = null ;
    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES);

        if(!storageDir.exists())
        {
            storageDir.mkdirs();
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    public static void setPic(String mCurrentPhotoPath, ImageView addImageImageView, Context context) {
        // Get the dimensions of the View
        int targetW = 0;
        int targetH = 0;

        targetW = context.getResources().getDimensionPixelSize(R.dimen.image_size_width);
        targetH = context.getResources().getDimensionPixelSize(R.dimen.image_size_height);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        addImageImageView.setImageBitmap(bitmap);
    }


    public static boolean isEmailValid(String paramString) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(paramString).matches();
    }

    public static void setLocale(String lang, Context context) {
        if (null != context) {
            Locale myLocale = new Locale(lang);
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            context.getResources().updateConfiguration(conf,
                    context.getResources().getDisplayMetrics());
        }
    }


    // Check Internet connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isConnected();
        } else {
            return false;
        }
    }

    public static void displayDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static String getDeviceDensity(Context context)
    {
        String folder = "x1";

        if(1 == context.getResources().getDisplayMetrics().density || 1.5 == context.getResources().getDisplayMetrics().density)
        {
            folder = "x1";
        }


        else if(2 == context.getResources().getDisplayMetrics().density)
        {
            folder = "x2";
        }

        else if(3 == context.getResources().getDisplayMetrics().density || 4 == context.getResources().getDisplayMetrics().density)
        {
            folder = "x3";
        }

        return folder;
    }

    public static void getPicFromCameraGallery(final Context context, final int requestFrom)
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        photoFile = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // Ensure that there's a camera activity to handle the intent
                    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                        // Create the File where the photo should go
                        try {
                            photoFile = createImageFile(context);
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            ex.printStackTrace();
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(photoFile));
                            takePictureIntent.putExtra("return-data", true);
                            ((Activity)context).startActivityForResult(takePictureIntent, Constants.REQUEST_CAMERA+requestFrom);
                        }
                    }
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            );
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    try {
                        photoFile = createImageFile(context);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        ((Activity)context).startActivityForResult(intent, Constants.SELECT_FILE + requestFrom);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void saveBitmap(File photoFile1,Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(photoFile1);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void navigateToHome(Context context)
    {
        Intent intent = new Intent(context, Landing.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
