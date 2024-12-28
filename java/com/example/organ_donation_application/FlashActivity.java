package com.example.organ_donation_application;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FlashActivity extends AppCompatActivity {
    private TextView flashTextView;
    private static final int FLASH_DURATION = 4000; // 3 seconds
    SharedPrefHandler sharedPrefHandler;
    private static final int STORAGE_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        sharedPrefHandler=new SharedPrefHandler(this);
        flashTextView = findViewById(R.id.flashTextView);

        // Start the flash animation
        startFlashAnimation();
        checkAndRequestStoragePermission();
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // Get dimensions only
//        BitmapFactory.decodeResource(getResources(), R.drawable.logo, options);
//        options.inSampleSize = calculateInSampleSize(options, 100, 100);
//        options.inJustDecodeBounds = false; // Decode with inSampleSize set
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo, options);


        if (isConnected()) {
            Thread background = new Thread() {
                public void run() {

                    try {
                        // Thread will sleep for 5 seconds
                        sleep(4000);

                        sharedPrefHandler = new SharedPrefHandler(getApplicationContext());



                            Intent i = new Intent(getBaseContext(), ChooseActivity.class);
                            startActivity(i);
//



                        finish();

                    } catch (Exception e) {

                    }


                }
            };
            background.start();
        } else
        {
            alertBox();
            //finish();
        }





    }

    private void checkAndRequestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API level 33)
            requestPermissions(
                    new String[] {
                            android.Manifest.permission.READ_MEDIA_IMAGES,
                            android.Manifest.permission.READ_MEDIA_VIDEO,
                            android.Manifest.permission.READ_MEDIA_AUDIO
                    },
                    STORAGE_PERMISSION_CODE
            );
        } else {
            // Android 6.0 to Android 12 (API level 23 to 32)
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {

                // Request READ_EXTERNAL_STORAGE permission
                requestPermissions(
                        new String[] { android.Manifest.permission.READ_EXTERNAL_STORAGE },
                        STORAGE_PERMISSION_CODE
                );
            } else {
                // Permission already granted
                Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with storage-related tasks
                Toast.makeText(this, "Storage Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
              //  Toast.makeText(this, "Storage Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startFlashAnimation() {
        // Animation: Fade in and out
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(flashTextView, "alpha", 0f, 1f);
        fadeIn.setDuration(500);
        fadeIn.setRepeatCount(ObjectAnimator.INFINITE);
        fadeIn.setRepeatMode(ObjectAnimator.REVERSE);

        // Start the animation
        fadeIn.start();
    }

    public boolean isConnected()
    {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    public  void  alertBox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(FlashActivity.this);
        builder.setTitle("No Internet Connection")
                .setMessage("Enable Internet or Wifi Connection and Restart the Application")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(FlashActivity.this,"Selected Option: No",Toast.LENGTH_SHORT).show();
//                    }
//                });
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();


    }
}