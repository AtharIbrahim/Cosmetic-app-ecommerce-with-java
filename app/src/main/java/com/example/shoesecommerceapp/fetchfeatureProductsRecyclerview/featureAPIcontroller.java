package com.example.shoesecommerceapp.fetchfeatureProductsRecyclerview;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.apicontroller;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.apiset;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class featureAPIcontroller {

    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/";
    private  static featureAPIcontroller clientobject;
    private static Retrofit retrofit;

    featureAPIcontroller(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized featureAPIcontroller getInstance(){
        if(clientobject == null){
            clientobject =   new featureAPIcontroller();
        }
        return clientobject;
    }
    public setApi getapi(){
        return retrofit.create(setApi.class);
    }
}
