package com.example.shoesecommerceapp.FacemaskProduct;

import com.example.shoesecommerceapp.EyesProduct.EyefeatureproductController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacemaskfeatureproductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/facemask/";
    private  static FacemaskfeatureproductController clientobject;
    private static Retrofit retrofit;

    FacemaskfeatureproductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FacemaskfeatureproductController getInstance(){
        if(clientobject == null){
            clientobject =   new FacemaskfeatureproductController();
        }
        return clientobject;
    }
    public perfumefeatureproduct getapi(){
        return retrofit.create(perfumefeatureproduct.class);
    }
}
