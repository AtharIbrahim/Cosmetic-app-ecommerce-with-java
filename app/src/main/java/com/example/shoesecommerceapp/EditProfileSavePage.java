package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoesecommerceapp.Fragments.AccountFragment;
import com.example.shoesecommerceapp.Fragments.HomeFragment;
import com.example.shoesecommerceapp.databinding.ActivityEditProfileSavePageBinding;

public class EditProfileSavePage extends AppCompatActivity {
    ActivityEditProfileSavePageBinding binding;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileSavePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();

        binding.EditProfileInfoDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the initial fragment);
                Intent intent = new Intent(EditProfileSavePage.this, EditProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}