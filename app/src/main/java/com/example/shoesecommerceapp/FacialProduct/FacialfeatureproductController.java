package com.example.shoesecommerceapp.FacialProduct;

import com.example.shoesecommerceapp.EyesProduct.EyefeatureproductController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacialfeatureproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Facial/";
    private  static FacialfeatureproductController clientobject;
    private static Retrofit retrofit;

    FacialfeatureproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FacialfeatureproductController getInstance(){
        if(clientobject == null){
            clientobject =   new FacialfeatureproductController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
