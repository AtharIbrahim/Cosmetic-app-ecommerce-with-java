package com.example.shoesecommerceapp.MoisturizerProduct;

import com.example.shoesecommerceapp.EyesProduct.EyefeatureproductController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class moisturizerfeatureproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Moisturizer/";
    private  static moisturizerfeatureproductController clientobject;
    private static Retrofit retrofit;

    moisturizerfeatureproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized moisturizerfeatureproductController getInstance(){
        if(clientobject == null){
            clientobject =   new moisturizerfeatureproductController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
