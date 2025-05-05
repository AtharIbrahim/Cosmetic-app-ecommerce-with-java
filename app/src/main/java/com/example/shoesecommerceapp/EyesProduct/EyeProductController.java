package com.example.shoesecommerceapp.EyesProduct;

import com.example.shoesecommerceapp.FaceProducts.FaceProduct;
import com.example.shoesecommerceapp.FaceProducts.FaceProductController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EyeProductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Eyes/";
    private  static EyeProductController clientobject;
    private static Retrofit retrofit;

    EyeProductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized EyeProductController getInstance(){
        if(clientobject == null){
            clientobject =   new EyeProductController();
        }
        return clientobject;
    }
    public EyeProduct getapi(){
        return retrofit.create(EyeProduct.class);
    }
}
