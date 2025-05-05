package com.example.shoesecommerceapp.SunscreenProducts;

import com.example.shoesecommerceapp.EyesProduct.EyefeatureproductController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SunscreenfeatureController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Sunscreen/";
    private  static SunscreenfeatureController clientobject;
    private static Retrofit retrofit;

    SunscreenfeatureController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized SunscreenfeatureController getInstance(){
        if(clientobject == null){
            clientobject =   new SunscreenfeatureController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
