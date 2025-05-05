package com.example.shoesecommerceapp.SkinCareProduct;

import com.example.shoesecommerceapp.EyesProduct.EyefeatureproductController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class skincarefeatureproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/EySkincarees/";
    private  static skincarefeatureproductController clientobject;
    private static Retrofit retrofit;

    skincarefeatureproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized skincarefeatureproductController getInstance(){
        if(clientobject == null){
            clientobject =   new skincarefeatureproductController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
