package com.example.shoesecommerceapp.fetchPerfumesRecyclerview;

import com.example.shoesecommerceapp.fetchallproductsRecyclerview.AllProductsAPIController;
import com.example.shoesecommerceapp.fetchallproductsRecyclerview.ApiWork;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class perfumeAPIController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/perfume/";
    private  static perfumeAPIController clientobject;
    private static Retrofit retrofit;

    perfumeAPIController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized perfumeAPIController getInstance(){
        if(clientobject == null){
            clientobject =   new perfumeAPIController();
        }
        return clientobject;
    }
    public perfumeAPI getapi(){
        return retrofit.create(perfumeAPI.class);
    }
}
