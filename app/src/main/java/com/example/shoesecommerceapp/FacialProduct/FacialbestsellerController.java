package com.example.shoesecommerceapp.FacialProduct;

import com.example.shoesecommerceapp.EyesProduct.EyebestsellerController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestseller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacialbestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Facial/";
    private  static FacialbestsellerController clientobject;
    private static Retrofit retrofit;

    FacialbestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FacialbestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new FacialbestsellerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
