package com.example.shoesecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shoesecommerceapp.Fragments.AccountFragment;
import com.example.shoesecommerceapp.Fragments.CartFragment;
import com.example.shoesecommerceapp.Fragments.FavoriteFragment;
import com.example.shoesecommerceapp.Fragments.HomeFragment;
import com.example.shoesecommerceapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String LOGOUT_URL = "http://192.168.169.98/ShoesAppECommerce/login.php";
    private OkHttpClient client = new OkHttpClient();

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize FragmentManager
        fragmentManager = getSupportFragmentManager();


        // Set listener for bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Get the title of the selected menu item
                String title = item.getTitle().toString();
                // Use switch on the title
                switch (title) {
                    case "Home":
                        replaceFragment(new HomeFragment());
                        return true;
                    case "Favorite":
                        replaceFragment(new FavoriteFragment());
                        return true;
                    case "Cart":
                        replaceFragment(new CartFragment());
                        return true;
                    case "Account":
                        replaceFragment(new AccountFragment());
                        return true;
                }
                return true;
            }
        });
        // Set the initial fragment
        replaceFragment(new HomeFragment());

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("email", null);
        if (loggedInEmail == null) {
            // User is not logged in, redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        }

//        binding.LogoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Make a network request to logout
//                Request request = new Request.Builder()
//                        .url(LOGOUT_URL)
//                        .build();
//
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                        e.printStackTrace();
//                        // Handle failure
//                    }
//
//                    @Override
//                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                        if (response.isSuccessful()) {
//                            // Handle successful logout
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // Clear user login information from SharedPreferences
//                                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.remove("email");
//                                    editor.apply();
//
//                                    // Redirect to LoginActivity
//                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    finish(); // Close the current activity
//                                }
//                            });
//                        } else {
//                            // Handle logout failure
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(MainActivity.this, "Logout failed. Please try again.", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    }
//                });
//            }
//        });
    }

    // Method to replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
