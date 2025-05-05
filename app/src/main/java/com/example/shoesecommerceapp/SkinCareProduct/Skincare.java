package com.example.shoesecommerceapp.SkinCareProduct;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Skincare {
    @GET("displayskincare.php")
    Call<List<responsemodel>> getdata();
}
