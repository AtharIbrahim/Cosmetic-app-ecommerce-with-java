package com.example.shoesecommerceapp.EyesProduct;

import com.example.shoesecommerceapp.FaceProducts.FaceBestserllerController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestseller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EyebestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Eyes/";
    private  static EyebestsellerController clientobject;
    private static Retrofit retrofit;

    EyebestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized EyebestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new EyebestsellerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
