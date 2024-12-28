package com.example.organ_donation_application;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://nexgautomation.com/Project2023_2024/blood_organ/";

    @POST("donor_form.php") // Use relative path
    Call<IsExist> DonorForm(
            @Query("f1") String name,
            @Query("f2") String age,
            @Query("f3") String mobile,
            @Query("f4") String email,
            @Query("f5") String selectedValue,
            @Query("f6") String organs
    );

    @POST("donor_account.php") // Use relative path
    Call<IsExist> DonorAccount(
            @Query("f1") String name,
            @Query("f2") String mobile,
            @Query("f3") String email,
            @Query("f4") String address,
            @Query("f5") String password

    );


    @POST("reciever_account.php") // Use relative path
    Call<IsExist> RecieverAccount(
            @Query("f1") String name,
            @Query("f2") String mobile,
            @Query("f3") String email,
            @Query("f4") String address,
            @Query("f5") String password

    );

    @GET("donor_password.php")
    Call<List<donor>> getdonor_pass(@Query("f1") String str_mno);


    @GET("reciever_password.php")
    Call<List<reciever>> getreciever_pass(@Query("f1") String str_mno);


}
