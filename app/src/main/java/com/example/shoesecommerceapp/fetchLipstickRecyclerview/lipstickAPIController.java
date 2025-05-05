package com.example.shoesecommerceapp.fetchLipstickRecyclerview;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.apicontroller;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.apiset;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class lipstickAPIController {

    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/lipstick/";
    private  static lipstickAPIController clientobject;
    private static Retrofit retrofit;

    lipstickAPIController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized lipstickAPIController getInstance(){
        if(clientobject == null){
            clientobject =   new lipstickAPIController();
        }
        return clientobject;
    }
    public lipstickAPI getapi(){
        return retrofit.create(lipstickAPI.class);
    }
}
