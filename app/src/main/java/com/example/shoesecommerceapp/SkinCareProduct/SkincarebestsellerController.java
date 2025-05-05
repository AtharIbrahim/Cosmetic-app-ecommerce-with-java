package com.example.shoesecommerceapp.SkinCareProduct;

import com.example.shoesecommerceapp.EyesProduct.EyebestsellerController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestseller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SkincarebestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Skincare/";
    private  static SkincarebestsellerController clientobject;
    private static Retrofit retrofit;

    SkincarebestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized SkincarebestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new SkincarebestsellerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
