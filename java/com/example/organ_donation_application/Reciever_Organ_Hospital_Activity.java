package com.example.organ_donation_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
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

public class Reciever_Organ_Hospital_Activity extends   AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
        DrawerLayout drawerLayout;
        SharedPrefHandler sharedPrefHandler;

         private List<OrganBank> organBanks = new ArrayList<>();
         private OrganBankAdapter adapter;
        private static final int STORAGE_PERMISSION_CODE = 1;



@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_organ_hospital);

    sharedPrefHandler=new SharedPrefHandler(this);

    Toolbar toolbar = findViewById(R.id.toolbarw); //Ignore red line errors
    setSupportActionBar(toolbar);
    drawerLayout = findViewById(R.id.drawer_layoutw);
    NavigationView navigationView = findViewById(R.id.nav_viewrr);
    navigationView.setNavigationItemSelectedListener(this);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
            R.string.close_nav);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();


    RecyclerView recyclerView = findViewById(R.id.recyclerViewBloodBanksow);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new OrganBankAdapter(this, organBanks);
    recyclerView.setAdapter(adapter);



    fetchBloodBanks();



}

    private void fetchBloodBanks() {
        String url = "https://nexgautomation.com/Project2023_2024/blood_organ/get_organ_banks.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray dataArray = response.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject object = dataArray.getJSONObject(i);
                            int id = object.getInt("id");
                            String name = object.getString("name");
                            String imageUrl = object.getString("image_url");

                            String mobile_number = object.getString("mobile_number");
                            String latitude = object.getString("latitude");
                            String longitude = object.getString("longitude");

                            Toast.makeText(this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();

                            organBanks.add(new OrganBank(id, name, imageUrl,mobile_number,latitude,longitude));
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Reciever_Organ_Hospital_Activity.this, "Error1: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(Reciever_Organ_Hospital_Activity.this, "Error2: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
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
        // Add your custom logic here

        startActivity(new Intent(getApplication(),reciever_HomeActivity.class));
        finish();
        // Exit the app or perform some exit logic
        super.onBackPressed();

    }
}