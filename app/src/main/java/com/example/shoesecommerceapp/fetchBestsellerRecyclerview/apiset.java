package com.example.shoesecommerceapp.fetchBestsellerRecyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("bestsellerproducts.php")
    Call<List<responsemodel>> getdata();
}
