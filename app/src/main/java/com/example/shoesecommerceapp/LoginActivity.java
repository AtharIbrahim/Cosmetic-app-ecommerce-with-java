package com.example.shoesecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoesecommerceapp.databinding.ActivityLoginBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog progressDialog;
    private static final String BASE_URL = "http://192.168.169.98/ShoesAppECommerce/login.php";
    private OkHttpClient client = new OkHttpClient();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(LoginActivity.this);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Accessing Account!");

        // Set the content view of the dialog before finding the buttons
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);


        //Main Buttons working start
        //Forgot button working
        binding.loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        //Signup Button
        binding.LoginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        //Back Button
        binding.LoginBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WelcomeScreen.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        //Login Button Working
        binding.LoginSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String email = binding.LoginEmail.getText().toString();
                String password = binding.LoginPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    dialog.dismiss();
                    binding.LoginEmail.setError("Email is Empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    dialog.dismiss();
                    binding.LoginPassword.setError("Password is Empty");
                    return;
                }

                // Make a network request to perform login
                RequestBody formdata = new FormBody.Builder()
                        .add("Email", email)
                        .add("Password", password)
                        .build();

                Request request = new Request.Builder()
                        .url(BASE_URL + "?action=login")
                        .post(formdata)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        }
                        if (response.isSuccessful()) {
                            String resp = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    if (resp.equals("success")) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish(); // Close the login activity

                                        // Store user login information locally
                                        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("email", email);
                                        editor.apply();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Failed to perform login. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Check if the user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("email", null);
        if (loggedInEmail != null) {
            dialog.dismiss();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the login activity
        }

    }
}