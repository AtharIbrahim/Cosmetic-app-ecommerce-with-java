package com.example.shoesecommerceapp.FaceProducts;

import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestseller;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestsellerController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaceBestserllerController {
    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/Face/";
    private  static FaceBestserllerController clientobject;
    private static Retrofit retrofit;

    FaceBestserllerController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FaceBestserllerController getInstance(){
        if(clientobject == null){
            clientobject =   new FaceBestserllerController();
        }
        return clientobject;
    }
    public perfumebestseller getapi(){
        return retrofit.create(perfumebestseller.class);
    }
}
