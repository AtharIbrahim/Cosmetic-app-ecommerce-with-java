package com.example.shoesecommerceapp.MoisturizerProduct;

import com.example.shoesecommerceapp.EyesProduct.EyeProduct;
import com.example.shoesecommerceapp.EyesProduct.EyeProductController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class moisturizerproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Moisturizer/";
    private  static moisturizerproductController clientobject;
    private static Retrofit retrofit;

    moisturizerproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized moisturizerproductController getInstance(){
        if(clientobject == null){
            clientobject =   new moisturizerproductController();
        }
        return clientobject;
    }
    public moisturizerproduct getapi(){
        return retrofit.create(moisturizerproduct.class);
    }
}
