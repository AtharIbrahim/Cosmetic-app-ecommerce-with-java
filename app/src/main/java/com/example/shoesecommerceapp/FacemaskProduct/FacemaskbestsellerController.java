package com.example.shoesecommerceapp.FacemaskProduct;

import com.example.shoesecommerceapp.EyesProduct.EyebestsellerController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestseller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacemaskbestsellerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/facemask/";
    private  static FacemaskbestsellerController clientobject;
    private static Retrofit retrofit;

    FacemaskbestsellerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FacemaskbestsellerController getInstance(){
        if(clientobject == null){
            clientobject =   new FacemaskbestsellerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
