package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoesecommerceapp.databinding.ActivitySignupSuccessfulBinding;

public class SignupActivitySuccessful extends AppCompatActivity {
    ActivitySignupSuccessfulBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivitySuccessful.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}