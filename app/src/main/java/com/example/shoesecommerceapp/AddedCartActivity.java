package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoesecommerceapp.Fragments.HomeFragment;
import com.example.shoesecommerceapp.databinding.ActivityAddedCartBinding;

public class AddedCartActivity extends AppCompatActivity {
    ActivityAddedCartBinding binding;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddedCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();


    }
    // Method to replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}