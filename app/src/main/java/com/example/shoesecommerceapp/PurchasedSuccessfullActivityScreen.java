package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoesecommerceapp.databinding.ActivityPurchasedSuccessfullScreenBinding;

public class PurchasedSuccessfullActivityScreen extends AppCompatActivity {
    ActivityPurchasedSuccessfullScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchasedSuccessfullScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.PurchaseSuccessViewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurchasedSuccessfullActivityScreen.this, OrderHistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}