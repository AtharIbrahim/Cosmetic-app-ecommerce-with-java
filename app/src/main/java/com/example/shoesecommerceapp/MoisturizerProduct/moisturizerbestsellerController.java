package com.example.shoesecommerceapp.MoisturizerProduct;

import com.example.shoesecommerceapp.EyesProduct.EyebestsellerController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestseller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class moisturizerbestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Moisturizer/";
    private  static moisturizerbestsellerController clientobject;
    private static Retrofit retrofit;

    moisturizerbestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized moisturizerbestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new moisturizerbestsellerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
