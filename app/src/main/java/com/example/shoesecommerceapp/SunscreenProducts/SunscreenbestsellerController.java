package com.example.shoesecommerceapp.SunscreenProducts;

import com.example.shoesecommerceapp.EyesProduct.EyebestsellerController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestseller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SunscreenbestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Sunscreen/";
    private  static SunscreenbestsellerController clientobject;
    private static Retrofit retrofit;

    SunscreenbestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized SunscreenbestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new SunscreenbestsellerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
