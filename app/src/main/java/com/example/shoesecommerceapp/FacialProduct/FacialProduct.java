package com.example.shoesecommerceapp.FacialProduct;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FacialProduct {
    @GET("displayfacial.php")
    Call<List<responsemodel>> getdata();
}
