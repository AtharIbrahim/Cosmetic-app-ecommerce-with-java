package com.example.shoesecommerceapp.fetchfeatureProductsRecyclerview;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface setApi {
    @GET("featureproducts.php")
    Call<List<responsemodel>> getdata();
}
