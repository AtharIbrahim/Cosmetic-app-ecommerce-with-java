package com.example.shoesecommerceapp.SkinCareProduct;

import com.example.shoesecommerceapp.EyesProduct.EyeProduct;
import com.example.shoesecommerceapp.EyesProduct.EyeProductController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SkincareController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Skincare/";
    private  static SkincareController clientobject;
    private static Retrofit retrofit;

    SkincareController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized SkincareController getInstance(){
        if(clientobject == null){
            clientobject =   new SkincareController();
        }
        return clientobject;
    }
    public Skincare getapi(){
        return retrofit.create(Skincare.class);
    }
}
