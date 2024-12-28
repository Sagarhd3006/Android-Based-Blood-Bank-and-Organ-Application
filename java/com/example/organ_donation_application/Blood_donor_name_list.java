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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Blood_donor_name_list extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    SharedPrefHandler sharedPrefHandler;
    //ListView listView;
    Button button_help, button_share;

    private static final int STORAGE_PERMISSION_CODE = 1;


    private List<BloodDonorList> blooddonorLists = new ArrayList<>();
    private BloodDonorListAdapter adapter;


String string_blood_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor_name_list);
        sharedPrefHandler = new SharedPrefHandler(this);

        string_blood_group=sharedPrefHandler.getSharedPreferences("bg");

        Toast.makeText(this, ""+string_blood_group, Toast.LENGTH_SHORT).show();


        Toolbar toolbar = findViewById(R.id.toolbarr); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layoutr);
        NavigationView navigationView = findViewById(R.id.nav_view_reciever);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (checkStoragePermission()) {
                // Permissions are granted, proceed with the operation
                Toast.makeText(this, "Storage permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                // Request the permissions
                requestStoragePermission();
            }
        }



        RecyclerView recyclerView = findViewById(R.id.recyclerViewBlooddonorname);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BloodDonorListAdapter(this, blooddonorLists);
        recyclerView.setAdapter(adapter);


        fetchBloodBanks(string_blood_group);












    }


    private void fetchBloodBanks(String string_blood_group) {
        String url = "https://nexgautomation.com/Project2023_2024/blood_organ/get_blood_donor_details.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url+"?id="+string_blood_group, null,
                response -> {
                    try {
                        JSONArray dataArray = response.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject object = dataArray.getJSONObject(i);
                            int id = object.getInt("id");
                            String name = object.getString("name");
                            String age = object.getString("age");

                            String mobile = object.getString("mobile");
                            String email = object.getString("email");
                            String bloodGroup = object.getString("bloodGroup");

                            //Toast.makeText(this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();

                            blooddonorLists.add(new BloodDonorList(id, name, age,mobile,email,bloodGroup));
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Blood_donor_name_list.this, "Error1: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(Blood_donor_name_list.this, "Error2: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
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
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.MANAGE_EXTERNAL_STORAGE},
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
        switch (item.getItemId()) {
            case R.id.nav_dashboard_reciever:

                Intent intent = new Intent(getApplication(), reciever_HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_blood_donor_reciever:
                Intent intentpr = new Intent(getApplication(), Blood_donor_list.class);
                startActivity(intentpr);
                 finish();
                break;


            case R.id.nav_blood_bank_reciever:

                Intent intent2 = new Intent(getApplication(), Reciever_Blood_Bank_Activity.class);
                startActivity(intent2);
                finish();
                break;


            case R.id.nav_organ_hospital_reciever:

                Intent intent3 = new Intent(getApplication(), Reciever_Organ_Hospital_Activity.class);
                startActivity(intent3);
                 finish();
                break;

            case R.id.nav_feedback_reciever:

                Intent intent5 = new Intent(getApplication(), Reciever_FeedbackActivity.class);
                startActivity(intent5);
                 finish();
                break;


            case R.id.nav_about_app_reciever:

                Intent intent4 = new Intent(getApplication(), AboutAppActivity.class);
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

    public void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //if user pressed "yes", then he is allowed to exit from application
            sharedPrefHandler.setSharedPreferences("login", "false");
            Intent intent = new Intent(getApplication(), reciever_loginActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onBackPressed() {
        // Add your custom logic here

        startActivity(new Intent(getApplication(),reciever_HomeActivity.class));
        finish();
        // Exit the app or perform some exit logic
        super.onBackPressed();

    }
}