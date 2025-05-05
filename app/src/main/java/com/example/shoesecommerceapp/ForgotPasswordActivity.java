package com.example.shoesecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.shoesecommerceapp.databinding.ActivityForgotPasswordBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    ProgressDialog progressDialog;
    private static final String BASE_URL = "http://192.168.169.98/ShoesAppECommerce/registration.php";
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setTitle("Accessing Account!");

        //Back button
        binding.ForgotBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        //Send button working
        binding.ForgotSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                String email = binding.ForgotEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    progressDialog.dismiss();
                    binding.ForgotEmail.setError("Email is Empty");
                    return;
                }
                checkEmailExists(email);
            }
        });
    }


    // Method to check if email already exists
    private void checkEmailExists(String email) {
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
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resp = response.body().string();
                    ForgotPasswordActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                if (resp.equals("exists")) {
                                    // Email already exists, inform the user
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(ForgotPasswordActivity.this, EnterForogtPassword.class);
                                    intent.putExtra("Email", email);
                                    startActivity(intent);

                                } else {

                                }
                            }
                            catch (Exception e){
                                Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "Failed to check email existence", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}