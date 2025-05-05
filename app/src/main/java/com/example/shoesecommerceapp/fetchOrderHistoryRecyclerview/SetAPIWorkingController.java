package com.example.shoesecommerceapp.fetchOrderHistoryRecyclerview;

import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.apicontroller;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.apiset;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetAPIWorkingController {

    private static final String URL = "http://192.168.169.98/ShoesAppECommerce/";
    private  static SetAPIWorkingController clientobject;
    private static Retrofit retrofit;

    SetAPIWorkingController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized SetAPIWorkingController getInstance(){
        if(clientobject == null){
            clientobject =   new SetAPIWorkingController();
        }
        return clientobject;
    }
    public SetAPIWork getapi(){
        return retrofit.create(SetAPIWork.class);
    }
}
