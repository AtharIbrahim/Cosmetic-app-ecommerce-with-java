package com.example.shoesecommerceapp.fetchLipstickRecyclerview;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface lipstickfeatureproduct {
    @GET("displayfeatureproduct.php")
    Call<List<responsemodel>> getdata();
}
