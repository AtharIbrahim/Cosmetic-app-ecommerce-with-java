package com.example.shoesecommerceapp.EyesProduct;

import com.example.shoesecommerceapp.FaceProducts.FaceFeatureController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EyefeatureproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Eyes/";
    private  static EyefeatureproductController clientobject;
    private static Retrofit retrofit;

    EyefeatureproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized EyefeatureproductController getInstance(){
        if(clientobject == null){
            clientobject =   new EyefeatureproductController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
