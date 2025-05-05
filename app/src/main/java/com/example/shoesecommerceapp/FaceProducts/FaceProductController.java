package com.example.shoesecommerceapp.FaceProducts;

import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumeAPI;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumeAPIController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaceProductController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Face/";
    private  static FaceProductController clientobject;
    private static Retrofit retrofit;

    FaceProductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FaceProductController getInstance(){
        if(clientobject == null){
            clientobject =   new FaceProductController();
        }
        return clientobject;
    }
    public FaceProduct getapi(){
        return retrofit.create(FaceProduct.class);
    }
}
