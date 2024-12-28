package com.example.organ_donation_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class donor_ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    SharedPrefHandler sharedPrefHandler;
String selectedLabel,selectedValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);
        sharedPrefHandler=new SharedPrefHandler(this);
        // Find views by ID
        EditText etDonorName = findViewById(R.id.et_donor_name);
        EditText etDonorAge = findViewById(R.id.et_donor_age);
        EditText etDonorMobile = findViewById(R.id.et_donor_mobile);
        EditText etDonorEmail = findViewById(R.id.et_donor_email);
        Spinner spinnerBloodGroup = findViewById(R.id.spinner_blood_group);
        CheckBox checkboxHeart = findViewById(R.id.checkbox_heart);
        CheckBox checkboxLungs = findViewById(R.id.checkbox_lungs);
        CheckBox checkboxKidneys = findViewById(R.id.checkbox_kidneys);
        CheckBox checkboxLiver = findViewById(R.id.checkbox_liver);
        CheckBox checkboxEyes = findViewById(R.id.checkbox_eyes);
        CheckBox checkboxConsent = findViewById(R.id.checkbox_consent);
        Button btnSubmit = findViewById(R.id.btn_submit_donor_form);






        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.blood_group_values, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerBloodGroup.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.blood_group_labels,
                android.R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter1);

        // Get backend values array
        String[] bloodGroupValues = getResources().getStringArray(R.array.blood_group_values);

        spinnerBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Ignore the first item if it's a "Select Blood Group" prompt
                      selectedLabel = parent.getItemAtPosition(position).toString();
                      selectedValue = bloodGroupValues[position]; // Match the backend value

                    Toast.makeText(
                            donor_ProfileActivity.this,
                            "Selected: " + selectedLabel + " (" + selectedValue + ")",
                            Toast.LENGTH_SHORT
                    ).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: handle no selection
            }
        });




        // Submit Button Logic
        btnSubmit.setOnClickListener(v -> {
            String name = etDonorName.getText().toString();
            String age = etDonorAge.getText().toString();
            String mobile = etDonorMobile.getText().toString();
            String email = etDonorEmail.getText().toString();
            //String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();

            ArrayList<String> organsToDonate = new ArrayList<>();
            if (checkboxHeart.isChecked()) organsToDonate.add("Heart");
            if (checkboxLungs.isChecked()) organsToDonate.add("Lungs");
            if (checkboxKidneys.isChecked()) organsToDonate.add("Kidneys");
            if (checkboxLiver.isChecked()) organsToDonate.add("Liver");
            if (checkboxEyes.isChecked()) organsToDonate.add("Eyes");

            boolean consentGiven = checkboxConsent.isChecked();

            if (name.isEmpty() || age.isEmpty() || mobile.isEmpty() || email.isEmpty() || !consentGiven || !mobile.matches("^[6-9]\\d{9}$") || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(donor_ProfileActivity.this, "Please fill all required Valid Information and provide consent.", Toast.LENGTH_SHORT).show();
            } else {
                String organs = organsToDonate.isEmpty() ? "None" : String.join(", ", organsToDonate);
                Toast.makeText(donor_ProfileActivity.this,
                        "Thank you, " + name + "!\nBlood Group: " + selectedValue + "\nOrgans: " + organs,
                        Toast.LENGTH_LONG).show();
                // You can save this data to a database here

                DonorUpdateProfile(name,age,mobile,email,selectedValue,organs);
                startActivity(new Intent(getApplication(),donor_HomeActivity.class));

            }
        });


    }

    private void DonorUpdateProfile(String name,String age,String mobile,String email,String selectedValue,String organs) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        // Make API call
        Call<IsExist> call = api.DonorForm(name,age,mobile,email,selectedValue,organs);

        call.enqueue(new Callback<IsExist>() {
            @Override
            public void onResponse(Call<IsExist> call, Response<IsExist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    IsExist responseResult = response.body();

                    if (responseResult.getSuccess()) {
                        // Handle success (e.g., show success message)
                        Toast.makeText(getApplicationContext(), "Update Successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle failure response from server
                        Toast.makeText(getApplicationContext(), "Update Failed: " + responseResult.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unexpected responses
                    Toast.makeText(getApplicationContext(), "Server Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IsExist> call, Throwable t) {
                // Handle connection or request failure
               // Toast.makeText(getApplicationContext(), "Request Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_dashboard:

                Intent intent=new Intent(getApplication(),donor_HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_profile:
                Intent intentpr=new Intent(getApplication(),donor_ProfileActivity.class);
                startActivity(intentpr);
                finish();
                break;


            case R.id.nav_blood_bank:

                Intent intent2=new Intent(getApplication(),Blood_Bank_Activity.class);
                startActivity(intent2);
                finish();
                break;


            case R.id.nav_organ_hospital:

                Intent intent3=new Intent(getApplication(),Organ_Hospital_Activity.class);
                startActivity(intent3);
                finish();
                break;

            case R.id.nav_feedback:

                Intent intent5=new Intent(getApplication(),FeedbackActivity.class);
                startActivity(intent5);
                finish();
                break;



            case R.id.nav_about_app:

                Intent intent4=new Intent(getApplication(),AboutAppActivity.class);
                startActivity(intent4);
                finish();
                break;





            case R.id.nav_logout:
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
            Intent intent=new Intent(getApplication(),donar_loginActivity.class);
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

        startActivity(new Intent(getApplication(),donor_HomeActivity.class));
        finish();
        // Exit the app or perform some exit logic
        super.onBackPressed();

    }

}