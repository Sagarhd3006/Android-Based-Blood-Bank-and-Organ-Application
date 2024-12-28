package com.example.organ_donation_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class reciever_loginActivity extends AppCompatActivity {
    Button button_login;
    TextView textView_create_account;
    DatabaseHelper_R databaseHelper;
    TextView forget_receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_login);

        databaseHelper = new DatabaseHelper_R(this);
        forget_receiver=findViewById(R.id.forget_receiver);
        button_login=findViewById(R.id.login_button_receiver);
        textView_create_account=findViewById(R.id.signUpRedirectText_receiver);
        EditText etMobile = findViewById(R.id.login_phone_receiver);
        EditText etPassword = findViewById(R.id.login_password_receiver);
        textView_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),reciever_singupActivity.class));
            }
        });

        forget_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),Receiver_foregt.class));
            }
        });


        button_login.setOnClickListener(v -> {
            String mobile = etMobile.getText().toString();
            String password = etPassword.getText().toString();

            if (mobile.isEmpty() || password.isEmpty()) {
                Toast.makeText(reciever_loginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                boolean exists = databaseHelper.checkUser(mobile, password);
                if (exists) {
                    Toast.makeText(reciever_loginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Proceed to next activity
                    startActivity(new Intent(getApplication(),reciever_HomeActivity.class));
                } else {
                    Toast.makeText(reciever_loginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}