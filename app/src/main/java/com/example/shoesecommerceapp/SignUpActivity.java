package com.example.shoesecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoesecommerceapp.databinding.ActivitySignUpBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    ProgressDialog progressDialog;

    private static final String BASE_URL = "http://192.168.169.98/ShoesAppECommerce/registration.php";
    private OkHttpClient client = new OkHttpClient();

    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(SignUpActivity.this);
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);

        //Login Button
        binding.SignupLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Back Button
        binding.SignupBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        //Sign up Button working
        binding.SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String email = binding.SignupEmail.getText().toString();
                String name = binding.SignupName.getText().toString();
                String password = binding.SignupPassoword.getText().toString();
                String confirmpassword = binding.SignupConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    dialog.show();
                    binding.SignupEmail.setError("Email is Empty");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    dialog.show();
                    binding.SignupName.setError("Name is Empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    dialog.show();
                    binding.SignupPassoword.setError("Password is Empty");
                    return;
                }
                if (TextUtils.isEmpty(confirmpassword)) {
                    dialog.show();
                    binding.SignupConfirmPassword.setError("Password is Empty");
                    return;
                }
                // Check password strength
                int passwordStrength = checkPasswordStrength(password);
                if (passwordStrength < 3) {
                    dialog.show();
                    // Password is weak, provide feedback to the user
                    binding.SignupPassoword.setError("Password is Weak");
                    return;
                }
                if(!password.equals(confirmpassword)){
                    dialog.show();
                    binding.SignupConfirmPassword.setError("Password is not correct");
                    binding.SignupPassoword.setError("Password is not correct");
                    return;
                }
                if (name.length()>25) {
                    dialog.show();
                    binding.SignupPassoword.setError("Name is too large");
                    return;
                }
                checkEmailExists(name,email,password);
            }
        });

    }



    // Method to check password strength
    private int checkPasswordStrength(String password) {
        int score = 0;

        // Rule 1: Password length should be at least 8 characters
        if (password.length() >= 8) {
            score++;
        }

        // Rule 2: Password contains both uppercase and lowercase letters
        if (password.matches(".*[a-z].*") && password.matches(".*[A-Z].*")) {
            score++;
        }

        // Rule 3: Password contains numbers
        if (password.matches(".*\\d.*")) {
            score++;
        }

        // Rule 4: Password contains special characters
        if (password.matches(".*[!@#$%^&*()\\[\\]{}\\|;:',./<>?`~].*")) {
            score++;
        }

        return score;
    }





    // Method to check if email already exists
    private void checkEmailExists(String name, String email, String password) {
        // Make a network request to the server to check if the email already exists
        RequestBody formdata = new FormBody.Builder()
                .add("Email", email)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "?action=checkEmail")
                .post(formdata)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                dialog.show();
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resp = response.body().string();
                    SignUpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                if (resp.equals("exists")) {
                                    // Email already exists, inform the user
                                    dialog.show();
                                    runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Email already registered", Toast.LENGTH_SHORT).show());
                                } else {
                                    // Email doesn't exist, proceed with registration
                                    saveData(name, email, password);
                                }
                            }
                            catch (Exception e){
                                dialog.show();
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {
                    dialog.show();
                    Toast.makeText(SignUpActivity.this, "Failed to check email existence", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Save data and get to mysql database Start
    public void saveData(String name, String email, String password){
        RequestBody formdata = new FormBody.Builder()
                .add("Name", name)
                .add("Email", email)
                .add("Password", password)
                .build();

        Request request  =new Request.Builder()
                .url(BASE_URL)
                .post(formdata)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                dialog.show();
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String resp =  response.body().string();
                    if(response.isSuccessful()){
                        SignUpActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    dialog.show();
                                    Intent intent = new Intent(SignUpActivity.this, SignupActivitySuccessful.class);
                                    startActivity(intent);
                                    finish();
                                }
                                catch (Exception e){
                                    dialog.show();
                                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

            }
        });
    }
    //Save data and get to mysql database End
}