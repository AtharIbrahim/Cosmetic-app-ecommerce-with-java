package com.example.shoesecommerceapp.fetchPerfumesRecyclerview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class perfumebestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/perfume/";
    private  static perfumebestsellerController clientobject;
    private static Retrofit retrofit;

    perfumebestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized perfumebestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new perfumebestsellerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
