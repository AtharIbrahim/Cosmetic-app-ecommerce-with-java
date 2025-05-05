package com.example.shoesecommerceapp.fetchLipstickRecyclerview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class lipstickbestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/lipstick/";
    private  static lipstickbestsellerController clientobject;
    private static Retrofit retrofit;

    lipstickbestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized lipstickbestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new lipstickbestsellerController();
        }
        return clientobject;
    }
    public lipstickbestseller getapi(){
        return retrofit.create(lipstickbestseller.class);
    }
}
