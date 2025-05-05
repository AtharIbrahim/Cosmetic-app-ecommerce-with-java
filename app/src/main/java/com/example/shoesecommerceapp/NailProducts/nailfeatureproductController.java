package com.example.shoesecommerceapp.NailProducts;

import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproductController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class nailfeatureproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Nails/";
    private  static nailfeatureproductController clientobject;
    private static Retrofit retrofit;

    nailfeatureproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized nailfeatureproductController getInstance(){
        if(clientobject == null){
            clientobject =   new nailfeatureproductController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
