package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoesecommerceapp.databinding.ActivitySuccessVerifyEmailAddressBinding;

public class SuccessVerifyEmailAddress extends AppCompatActivity {
    ActivitySuccessVerifyEmailAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessVerifyEmailAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.SuccessVerifyEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessVerifyEmailAddress.this, VerifyEmailAddress.class);
                startActivity(intent);
                finish();
            }
        });
    }
}