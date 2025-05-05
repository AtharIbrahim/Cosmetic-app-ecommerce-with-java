package com.example.shoesecommerceapp.FacemaskProduct;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FacmaskProductAPI {
    @GET("displayfacemask.php")
    Call<List<responsemodel>> getdata();
}
