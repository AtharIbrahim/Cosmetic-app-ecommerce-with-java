package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoesecommerceapp.databinding.ActivityIfforgotSuccessBinding;

public class IFForgotSuccess extends AppCompatActivity {
    ActivityIfforgotSuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIfforgotSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Done button working
        binding.ForgotSuccessDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IFForgotSuccess.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}