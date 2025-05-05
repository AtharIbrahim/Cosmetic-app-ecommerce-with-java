package com.example.shoesecommerceapp.FaceProducts;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FaceProduct {
    @GET("displayface.php")
    Call<List<responsemodel>> getdata();
}
