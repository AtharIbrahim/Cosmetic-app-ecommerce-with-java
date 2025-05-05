package com.example.shoesecommerceapp.fetchLipstickRecyclerview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class lipstickfeatureproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/lipstick/";
    private  static lipstickfeatureproductController clientobject;
    private static Retrofit retrofit;

    lipstickfeatureproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized lipstickfeatureproductController getInstance(){
        if(clientobject == null){
            clientobject =   new lipstickfeatureproductController();
        }
        return clientobject;
    }
    public lipstickfeatureproduct getapi(){
        return retrofit.create(lipstickfeatureproduct.class);
    }
}
