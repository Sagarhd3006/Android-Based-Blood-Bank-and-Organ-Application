package com.example.organ_donation_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {

    Button button_donor,button_reciever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        button_donor=findViewById(R.id.button_choose_donor);
        button_reciever=findViewById(R.id.button_choose_receiver);

        button_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),donar_loginActivity.class));
            }
        });

        button_reciever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),reciever_loginActivity.class));
            }
        });

    }
}