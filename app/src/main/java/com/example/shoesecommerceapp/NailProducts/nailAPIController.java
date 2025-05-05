package com.example.shoesecommerceapp.NailProducts;

import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumeAPI;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumeAPIController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class nailAPIController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Nails/";
    private  static nailAPIController clientobject;
    private static Retrofit retrofit;

    nailAPIController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized nailAPIController getInstance(){
        if(clientobject == null){
            clientobject =   new nailAPIController();
        }
        return clientobject;
    }
    public nailAPI getapi(){
        return retrofit.create(nailAPI.class);
    }
}
