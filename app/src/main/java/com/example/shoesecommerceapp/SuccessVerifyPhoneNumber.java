package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SuccessVerifyPhoneNumber extends AppCompatActivity {

  com.example.shoesecommerceapp.databinding.ActivitySuccessVerifyPhoneNumberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.shoesecommerceapp.databinding.ActivitySuccessVerifyPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.SuccessVerifyPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessVerifyPhoneNumber.this, VerifyPhoneNumber.class);
                startActivity(intent);
                finish();
            }
        });
    }
}