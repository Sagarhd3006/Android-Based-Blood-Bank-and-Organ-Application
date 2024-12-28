package com.example.organ_donation_application;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class reciever_singupActivity extends AppCompatActivity {
    Button button_singup;
    EditText editText_name,editText_mno,editText_email,editText_address,editText_password;
    DatabaseHelper_R databaseHelper;
    String name,mobile,email,address,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_singup);
        databaseHelper = new DatabaseHelper_R(this);

        button_singup=findViewById(R.id.singup_buttonr);
        editText_name=findViewById(R.id.singup_namer);
        editText_mno=findViewById(R.id.singup_phoner);
        editText_email=findViewById(R.id.singup_emailr);
        editText_address=findViewById(R.id.singup_addressr);
        editText_password=findViewById(R.id.singup_passwordr);


        button_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                // Call validateFields() before proceeding with form submission
                if (validateFields()) {
                    // Proceed with form submission

                    boolean success = databaseHelper.addUser(name, mobile, email, address, password);
                    if (success)
                    {
                        Toast.makeText(reciever_singupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                        RecieverAccount(name,mobile,email,address,password);
                        startActivity(new Intent(getApplication(),reciever_loginActivity.class));
                        finish();
                    }

                } else {
                    Toast.makeText(reciever_singupActivity.this, "Signup failed. Try again!", Toast.LENGTH_SHORT).show();
                }


//                String name = editText_name.getText().toString();
//                String mobile = editText_mno.getText().toString();
//                String email = editText_email.getText().toString();
//                String address = editText_address.getText().toString();
//                String password = editText_password.getText().toString();

//                if (name.isEmpty() || mobile.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(reciever_singupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
//                } else {
//                    boolean success = databaseHelper.addUser(name, mobile, email, address, password);
//                    if (success) {
//                        Toast.makeText(reciever_singupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
//                        RecieverAccount(name,mobile,email,address,password);
//                        startActivity(new Intent(getApplication(),reciever_loginActivity.class));
//                        finish();
//                    } else {
//                        Toast.makeText(reciever_singupActivity.this, "Signup failed. Try again!", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });


    }
    private boolean validateFields() {
        name = editText_name.getText().toString().trim();
        mobile = editText_mno.getText().toString().trim();
        email = editText_email.getText().toString().trim();
        address = editText_address.getText().toString().trim();
        password = editText_password.getText().toString().trim();

        // Validate mobile number (10 digits, starts with 6, 7, 8, or 9)
        if (!mobile.matches("^[6-9]\\d{9}$")) {
            editText_mno.setError("Enter a valid mobile number starting with 6, 7, 8, or 9");
            return false;
        }

        // Validate email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText_email.setError("Enter a valid email address");
            return false;
        }

        // Validate address (non-empty)
        if (address.isEmpty()) {
            editText_address.setError("Address cannot be empty");
            return false;
        }

        // Validate password (at least 6 characters, one uppercase, one lowercase, one digit, one special character)
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$")) {
            editText_password.setError("Password must be at least 6 characters long and include one uppercase letter, one lowercase letter, one digit, and one special character");
            return false;
        }

        return true;
    }
    private void RecieverAccount(String name,String mobile,String email,String address,String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        // Make API call
        Call<IsExist> call = api.RecieverAccount(name,mobile,email,address,password);

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
}