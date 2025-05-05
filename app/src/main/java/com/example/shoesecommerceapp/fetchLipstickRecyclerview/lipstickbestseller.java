package com.example.shoesecommerceapp.fetchLipstickRecyclerview;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface lipstickbestseller {
    @GET("displaybestseller.php")
    Call<List<responsemodel>> getdata();
}
