package com.example.shoesecommerceapp.SunscreenProducts;

import com.example.shoesecommerceapp.EyesProduct.EyeProduct;
import com.example.shoesecommerceapp.EyesProduct.EyeProductController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SunscreenproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Sunscreen/";
    private  static SunscreenproductController clientobject;
    private static Retrofit retrofit;

    SunscreenproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized SunscreenproductController getInstance(){
        if(clientobject == null){
            clientobject =   new SunscreenproductController();
        }
        return clientobject;
    }
    public SunscreenProductAPI getapi(){
        return retrofit.create(SunscreenProductAPI.class);
    }
}
