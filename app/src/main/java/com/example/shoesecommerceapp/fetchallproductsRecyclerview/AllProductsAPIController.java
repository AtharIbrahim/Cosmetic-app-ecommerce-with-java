package com.example.shoesecommerceapp.fetchallproductsRecyclerview;

import com.example.shoesecommerceapp.fetchfeatureProductsRecyclerview.featureAPIcontroller;
import com.example.shoesecommerceapp.fetchfeatureProductsRecyclerview.setApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllProductsAPIController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/";
    private  static AllProductsAPIController clientobject;
    private static Retrofit retrofit;

    AllProductsAPIController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized AllProductsAPIController getInstance(){
        if(clientobject == null){
            clientobject =   new AllProductsAPIController();
        }
        return clientobject;
    }
    public ApiWork getapi(){
        return retrofit.create(ApiWork.class);
    }
}
