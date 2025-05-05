package com.example.shoesecommerceapp.fetchPerfumesRecyclerview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class perfumefeatureproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/perfume/";
    private  static perfumefeatureproductController clientobject;
    private static Retrofit retrofit;

    perfumefeatureproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized perfumefeatureproductController getInstance(){
        if(clientobject == null){
            clientobject =   new perfumefeatureproductController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
