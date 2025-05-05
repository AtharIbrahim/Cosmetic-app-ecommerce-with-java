package com.example.shoesecommerceapp.fetchBestsellerRecyclerview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apicontroller {

    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/";
    private  static apicontroller clientobject;
    private static Retrofit retrofit;

    apicontroller(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized apicontroller getInstance(){
        if(clientobject == null){
            clientobject =   new apicontroller();
        }
        return clientobject;
    }
    public apiset getapi(){
        return retrofit.create(apiset.class);
    }
}
