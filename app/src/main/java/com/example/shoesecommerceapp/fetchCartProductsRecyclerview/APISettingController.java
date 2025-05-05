package com.example.shoesecommerceapp.fetchCartProductsRecyclerview;

import com.example.shoesecommerceapp.fetchfeatureProductsRecyclerview.featureAPIcontroller;
import com.example.shoesecommerceapp.fetchfeatureProductsRecyclerview.setApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APISettingController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/";
    private  static APISettingController clientobject;
    private static Retrofit retrofit;

    APISettingController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized APISettingController getInstance(){
        if(clientobject == null){
            clientobject =   new APISettingController();
        }
        return clientobject;
    }
    public APISetting getapi(){
        return retrofit.create(APISetting.class);
    }
}
