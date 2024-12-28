package com.example.organ_donation_application;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class reciever_HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    SharedPrefHandler sharedPrefHandler;
    //ListView listView;
    private ViewPager2 viewPager;
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable;
    private ImageSliderAdapter adapter;

    Button button_help,button_share;

    private static final int STORAGE_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_home);
        sharedPrefHandler=new SharedPrefHandler(this);

        Toolbar toolbar = findViewById(R.id.toolbarr); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layoutr);
        NavigationView navigationView = findViewById(R.id.nav_view_reciever);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        button_help=findViewById(R.id.button_helpline_reciever);
        button_share=findViewById(R.id.button_share_reciever);



        button_help.setOnClickListener(v -> {
            Intent intent = new Intent(reciever_HomeActivity.this, HelpLineActivity.class);
            startActivity(intent);
        });

        // Share App
        button_share.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "Check out this amazing app: https://play.google.com/store/apps/details?id=" + getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share App via"));
        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (checkStoragePermission()) {
                // Permissions are granted, proceed with the operation
                Toast.makeText(this, "Storage permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                // Request the permissions
                requestStoragePermission();
            }
        }


        viewPager = findViewById(R.id.viewPagerr);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.i1);
        images.add(R.drawable.i2);
        images.add(R.drawable.i3);
        images.add(R.drawable.i4);
        images.add(R.drawable.i5);
        //images.add(R.drawable.b6);


        // Set up the adapter and ViewPager
        adapter = new ImageSliderAdapter(this, images);
        viewPager.setAdapter(adapter);

        // Set up auto-slide logic
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = viewPager.getCurrentItem() + 1;
                if (nextItem >= adapter.getItemCount()) {
                    nextItem = 0;
                }
                viewPager.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 3000); // Slide every 3 seconds
            }
        };

        // Start auto-slide
        sliderHandler.postDelayed(sliderRunnable, 3000);

        // Pause auto-slide on manual swipe
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true; // Permission is automatically granted on older versions below Android M
    }

    // Method to request storage permission
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission was denied
                // Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }









    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_dashboard_reciever:

                Intent intent=new Intent(getApplication(),reciever_HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_blood_donor_reciever:
                Intent intentpr=new Intent(getApplication(),Blood_donor_list.class);
                startActivity(intentpr);
                 finish();
                break;


            case R.id.nav_blood_bank_reciever:

                Intent intent2=new Intent(getApplication(),Reciever_Blood_Bank_Activity.class);
                startActivity(intent2);
                finish();
                break;


            case R.id.nav_organ_hospital_reciever:

                Intent intent3=new Intent(getApplication(),Reciever_Organ_Hospital_Activity.class);
                startActivity(intent3);
                 finish();
                break;

            case R.id.nav_feedback_reciever:

                Intent intent5=new Intent(getApplication(),Reciever_FeedbackActivity.class);
                startActivity(intent5);
                 finish();
                break;



            case R.id.nav_about_app_reciever:

                Intent intent4=new Intent(getApplication(),AboutAppActivity.class);
                startActivity(intent4);
                finish();
                break;





            case R.id.nav_logout_reciever:
                Logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void Logout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //if user pressed "yes", then he is allowed to exit from application
            sharedPrefHandler.setSharedPreferences("login","false");
            Intent intent=new Intent(getApplication(),reciever_loginActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel();
        });
        AlertDialog alert=builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //if user pressed "yes", then he is allowed to exit from application

            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}