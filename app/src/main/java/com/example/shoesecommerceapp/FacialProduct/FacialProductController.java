package com.example.shoesecommerceapp.FacialProduct;

import com.example.shoesecommerceapp.EyesProduct.EyeProduct;
import com.example.shoesecommerceapp.EyesProduct.EyeProductController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacialProductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Facial/";
    private  static FacialProductController clientobject;
    private static Retrofit retrofit;

    FacialProductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FacialProductController getInstance(){
        if(clientobject == null){
            clientobject =   new FacialProductController();
        }
        return clientobject;
    }
    public FacialProduct getapi(){
        return retrofit.create(FacialProduct.class);
    }
}
