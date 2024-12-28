package com.example.organ_donation_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class BloodBankDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_details);


        Intent intent = getIntent();
        String lat = intent.getStringExtra("bloodBanklat");
        String lng = intent.getStringExtra("bloodBanklng");

        if (lat != null && lng != null) {
            // Construct the Google Maps link
            String mapsUrl = "https://www.google.com/maps?q=" + lat + "," + lng;

            // Create an Intent to open Google Maps
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl));
            startActivity(mapIntent);
            finish();
        } else {
            Log.e("Error", "Latitude or Longitude not found in Intent.");
        }
    }
}