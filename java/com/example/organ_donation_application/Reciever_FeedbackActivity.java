package com.example.organ_donation_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Reciever_FeedbackActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    SharedPrefHandler sharedPrefHandler;
    //ListView listView;

    EditText text;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_feedback);

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

        text=(EditText)findViewById(R.id.feedback_text);
        send=(Button)findViewById(R.id.feedback_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"sdalabanjan@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback from App");
                i.putExtra(Intent.EXTRA_TEXT, "text: " + text.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send feedback..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }            }
        });

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