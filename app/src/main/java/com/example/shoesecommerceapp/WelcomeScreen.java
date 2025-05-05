package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.shoesecommerceapp.databinding.ActivityWelcomeScreenBinding;

public class WelcomeScreen extends AppCompatActivity {
    ActivityWelcomeScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check if the user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("email", null);

        if (loggedInEmail != null) {
            // User is not logged in, navigate to login activity
            Intent intent = new Intent(WelcomeScreen.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        }
        binding.GetStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}