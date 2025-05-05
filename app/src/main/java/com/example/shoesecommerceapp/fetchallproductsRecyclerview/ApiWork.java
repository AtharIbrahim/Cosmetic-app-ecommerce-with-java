package com.example.shoesecommerceapp.fetchallproductsRecyclerview;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiWork {
    @GET("AllProducts.php")
    Call<List<responsemodel>> getdata();
}
