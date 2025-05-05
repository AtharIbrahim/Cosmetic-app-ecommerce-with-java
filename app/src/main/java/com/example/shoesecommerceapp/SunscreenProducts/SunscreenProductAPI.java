package com.example.shoesecommerceapp.SunscreenProducts;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SunscreenProductAPI {
    @GET("displaysunscreen.php")
    Call<List<responsemodel>> getdata();
}
