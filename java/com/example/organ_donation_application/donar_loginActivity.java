package com.example.organ_donation_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class donar_loginActivity extends AppCompatActivity {

    Button button_login;
    TextView textView_create_account;
    DatabaseHelper databaseHelper;
TextView forgotRedirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_login);
        databaseHelper = new DatabaseHelper(this);
        button_login=findViewById(R.id.login_button);
        textView_create_account=findViewById(R.id.signUpRedirectText);
        EditText etMobile = findViewById(R.id.login_phone);
        forgotRedirectText=findViewById(R.id.forgotRedirectText);
        EditText etPassword = findViewById(R.id.login_password);

        forgotRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplication(),Donor_forgot_password.class));

            }
        });


        textView_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),donor_singupActivity.class));
            }
        });


        button_login.setOnClickListener(v -> {
            String mobile = etMobile.getText().toString();
            String password = etPassword.getText().toString();

            if (mobile.isEmpty() || password.isEmpty()) {
                Toast.makeText(donar_loginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                boolean exists = databaseHelper.checkUser(mobile, password);
                if (exists) {
                    Toast.makeText(donar_loginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Proceed to next activity
                    startActivity(new Intent(getApplication(),donor_HomeActivity.class));
                } else {
                    Toast.makeText(donar_loginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}