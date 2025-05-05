package com.example.shoesecommerceapp.FacemaskProduct;

import com.example.shoesecommerceapp.EyesProduct.EyeProduct;
import com.example.shoesecommerceapp.EyesProduct.EyeProductController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacemaskProductController {private static final String URL = "http://192.168.169.98/ShoesAppECommerce/facemask/";
    private  static FacemaskProductController clientobject;
    private static Retrofit retrofit;

    FacemaskProductController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized FacemaskProductController getInstance(){
        if(clientobject == null){
            clientobject =   new FacemaskProductController();
        }
        return clientobject;
    }
    public FacmaskProductAPI getapi(){
        return retrofit.create(FacmaskProductAPI.class);
    }

}
