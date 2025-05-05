package com.example.shoesecommerceapp.fetchOrderHistoryRecyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SetAPIWork {
    @GET("DisplayOrderHistory.php")
    Call<List<Orderresponemodel>> getdata(@Query("email") String email);
}
