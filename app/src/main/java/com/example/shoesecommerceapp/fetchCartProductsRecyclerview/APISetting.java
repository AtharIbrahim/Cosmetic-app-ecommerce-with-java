package com.example.shoesecommerceapp.fetchCartProductsRecyclerview;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APISetting {
    @GET("DisplayCartProducts.php")
    Call<List<responsemodel>> getdata(@Query("email") String email);
}
