package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.shoesecommerceapp.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
                startActivity(intent);
                finishAffinity();
            }
        },2500);
    }
}