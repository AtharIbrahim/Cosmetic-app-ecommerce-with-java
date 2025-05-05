package com.example.shoesecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shoesecommerceapp.databinding.ActivityEditProfileBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EditProfile extends AppCompatActivity {
    ActivityEditProfileBinding binding;

    ProgressDialog progressDialog;
    String userEmail;

    Dialog dialog;


    Uri selectedimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the user's email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", null);

        dialog = new Dialog(EditProfile.this);
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);


        progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setTitle("Uploading Data!");


        binding.UserImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });

        binding.EditAccountSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String name = binding.editTextText2.getText().toString();
                String email = binding.editTextTextEmailAddress.getText().toString();
                String phone = binding.editTextPhone.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    dialog.dismiss();
                    binding.editTextText2.setError("Name is Empty");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    dialog.dismiss();
                    binding.editTextTextEmailAddress.setError("Email is Empty");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    dialog.dismiss();
                    binding.editTextPhone.setError("Phone # is Empty");
                    return;
                }
                // Send request to update user data
                updateUser(name, email, phone);
            }
        });
        fetchUserData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getData() != null) {
                binding.UserImageEdit.setImageURI(data.getData());
                selectedimage = data.getData();
            }
        }
    }

    private void fetchUserData() {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Email", userEmail)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.169.98/ShoesAppECommerce/retrive.php?action=readUser")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    dialog.dismiss();
                    Toast.makeText(EditProfile.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject user = new JSONObject(responseData);
                        if (user.has("error")) {
                            runOnUiThread(() -> {
                                dialog.dismiss();
                                Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            runOnUiThread(() -> {
                                try {
                                    dialog.dismiss();
                                    binding.editTextText2.setText(user.getString("Name"));
                                    binding.editTextTextEmailAddress.setText(user.getString("Email"));
                                    // Assuming you have a field for phone number
                                    binding.editTextPhone.setText(user.getString("PhoneNumber"));
                                    // Load the user image if there's an URL field for it
                                    // Glide.with(EditProfile.this).load(user.getString("ImageURL")).into(binding.UserImageEdit);
                                } catch (Exception e) {
                                    dialog.dismiss();
                                    e.printStackTrace();
                                }
                            });
                        }
                    } catch (Exception e) {
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }
        });
    }




    private void updateUser(String name, String email, String phone) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Email", userEmail)
                .add("Name", name)
                .add("PhoneNumber", phone)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.169.98/ShoesAppECommerce/update.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EditProfile.this.runOnUiThread(() -> {
                    dialog.dismiss();
                    Toast.makeText(EditProfile.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EditProfile.this.runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        Toast.makeText(EditProfile.this, "User data updated successfully", Toast.LENGTH_SHORT).show();
                        // Update any UI elements if needed
                    } else {
                        dialog.dismiss();
                        Toast.makeText(EditProfile.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}

