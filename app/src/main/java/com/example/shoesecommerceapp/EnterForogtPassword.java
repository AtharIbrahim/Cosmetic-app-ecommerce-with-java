package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.shoesecommerceapp.databinding.ActivityEnterForogtPasswordBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EnterForogtPassword extends AppCompatActivity {
    ActivityEnterForogtPasswordBinding binding;
    ProgressDialog progressDialog;

    private static final String BASE_URL = "http://192.168.169.98/ShoesAppECommerce/forgotpassword.php";
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterForogtPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String email = getIntent().getStringExtra("Email");
        progressDialog = new ProgressDialog(EnterForogtPassword.this);
        progressDialog.setTitle("Changing Password!");

        //back button
        binding.ResetPasswordBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnterForogtPassword.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Reset Password Working
        binding.ResetChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                String password = binding.ForgotPasswordOnce.getText().toString();
                String confirmpassword = binding.ForgotPasswordRepeat.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    progressDialog.dismiss();
                    binding.ForgotPasswordOnce.setError("Password is Empty");
                    return;
                }
                if (TextUtils.isEmpty(confirmpassword)) {
                    progressDialog.dismiss();
                    binding.ForgotPasswordRepeat.setError("Password is Empty");
                    return;
                }
                // Check password strength
                int passwordStrength = checkPasswordStrength(password);
                if (passwordStrength < 3) {
                    progressDialog.dismiss();
                    // Password is weak, provide feedback to the user
                    binding.ForgotPasswordOnce.setError("Password is Weak");
                    return;
                }
                if(!password.equals(confirmpassword)){
                    progressDialog.dismiss();
                    binding.ForgotPasswordOnce.setError("Password is not correct");
                    binding.ForgotPasswordRepeat.setError("Password is not correct");
                    return;
                }

                updateUser(email, password);
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


    private void updateUser(String email, String password) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Email", email)
                .add("Password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.169.98/ShoesAppECommerce/forgotpassword.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EnterForogtPassword.this.runOnUiThread(() -> {
                    progressDialog.dismiss();
                    Toast.makeText(EnterForogtPassword.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EnterForogtPassword.this.runOnUiThread(() -> {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(EnterForogtPassword.this, IFForgotSuccess.class);
                        startActivity(intent);
                        finish();
                        // Update any UI elements if needed
                    } else {
                        Toast.makeText(EnterForogtPassword.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}