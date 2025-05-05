package com.example.shoesecommerceapp.FaceProducts;

import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproductController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaceFeatureController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Face/";
    private  static FaceFeatureController clientobject;
    private static Retrofit retrofit;

    FaceFeatureController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FaceFeatureController getInstance(){
        if(clientobject == null){
            clientobject =   new FaceFeatureController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
