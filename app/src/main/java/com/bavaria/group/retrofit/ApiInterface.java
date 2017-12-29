package com.bavaria.group.retrofit;


import com.bavaria.group.retrofit.Model.AvailabilityCheckData;
import com.bavaria.group.retrofit.Model.FaqPojo;
import com.bavaria.group.retrofit.Model.GetLogin;
import com.bavaria.group.retrofit.Model.GetRegister;
import com.bavaria.group.retrofit.Model.InstallmentPojo;
import com.bavaria.group.retrofit.Model.MembershipPojo;
import com.bavaria.group.retrofit.Model.MyTicketPojo;
import com.bavaria.group.retrofit.Model.WaterBillPojo;
import com.bavaria.group.retrofit.Model.verifyUserData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/")
    Call<GetRegister> register(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<GetLogin> login(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<MembershipPojo> yearlyMembership(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<InstallmentPojo> installment(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<WaterBillPojo> waterBill(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<ArrayList<MyTicketPojo>> viewTicket(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/")
    Call<ArrayList<verifyUserData>> verifyUser(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<JsonObject> openTicket(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<JsonArray> viewTicketDetail(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<JsonObject> paymentHistory(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<ArrayList<AvailabilityCheckData>> availabilityCheck(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<JsonObject> chngePassword(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<ArrayList<FaqPojo>> faq(@Field("view") String view, @Field("page") String page);

    @FormUrlEncoded
    @POST("/index.php")
    Call<JsonObject> reply(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<JsonObject> closeTicket(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<JsonObject> updateProfile(@FieldMap HashMap<String, String> hashmap);

    @FormUrlEncoded
    @POST("/index.php")
    Call<JsonObject> userProfile(@FieldMap HashMap<String, String> hashmap);
}