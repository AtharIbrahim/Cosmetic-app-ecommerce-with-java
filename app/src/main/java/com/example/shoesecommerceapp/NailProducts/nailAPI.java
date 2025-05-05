package com.example.shoesecommerceapp.NailProducts;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface nailAPI {
    @GET("displaynails.php")
    Call<List<responsemodel>> getdata();
}
