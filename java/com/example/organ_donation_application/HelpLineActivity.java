package com.example.organ_donation_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HelpLineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_line);

        TextView helpText = findViewById(R.id.help_text);
        helpText.setText("For assistance, please call our helpline: +1-800-123-4567\nOr email us at: support@example.com");
    }
}