package com.example.shoesecommerceapp.NailProducts;

import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestseller;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestsellerController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class nailbestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Nails/";
    private  static nailbestsellerController clientobject;
    private static Retrofit retrofit;

    nailbestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized nailbestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new nailbestsellerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
