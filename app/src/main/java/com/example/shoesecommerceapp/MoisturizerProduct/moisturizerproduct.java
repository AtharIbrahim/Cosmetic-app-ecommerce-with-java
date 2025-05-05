package com.example.shoesecommerceapp.MoisturizerProduct;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface moisturizerproduct {
    @GET("displaymoisturizer.php")
    Call<List<responsemodel>> getdata();
}
