package com.example.organ_donation_application;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Donor_forgot_password extends AppCompatActivity {
    List<donor> productList1;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_forgot_password);

        editText=findViewById(R.id.et_for_mno);
        button=findViewById(R.id.btn_for_sub);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String str_mno=editText.getText().toString();
                CheckPassword(str_mno);

            }
        });
    }

    private void CheckPassword(final String str_mno)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<donor>> call = api.getdonor_pass(str_mno);

        call.enqueue(new Callback<List<donor>>() {
            @Override
            public void onResponse(Call<List<donor>> call, Response<List<donor>> response) {
                productList1 = response.body();

                Boolean isSuccess = false;
                if(response.body() != null) {
                    isSuccess = true;
                }

                if(isSuccess) {
                    String st_pass=productList1.get(0).getPassword();

                    Toast.makeText(Donor_forgot_password.this, "Your Password Is : "+st_pass, Toast.LENGTH_SHORT).show();



                } else {

                }
            }

            @Override
            public void onFailure(Call<List<donor>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}