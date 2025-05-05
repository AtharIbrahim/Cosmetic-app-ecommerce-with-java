package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoesecommerceapp.databinding.ActivityVerifyPhoneNumberBinding;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VerifyPhoneNumber extends AppCompatActivity {
    ActivityVerifyPhoneNumberBinding binding;

    String userEmail;

    private static final int MAX_FAILED_ATTEMPTS = 0; // Maximum allowed failed attempts
    private static final long LOCK_DURATION_MILLIS = 60 * 1000;

    private SharedPreferences sharedPreferences;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(VerifyPhoneNumber.this);
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);

        String VerifyPhone = getIntent().getStringExtra("VerifyPhone");
        if ("null".equals(VerifyPhone)) { // Use "null".equals(verifyEmail) for string comparison
            binding.PhoneNumberConfirmButton.setVisibility(View.VISIBLE);
        } else {
            binding.PhoneNumberConfirmButton.setVisibility(View.GONE);
        }
        // Retrieve the user's email from SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", null);
        sharedPreferences = getSharedPreferences("lock_status", MODE_PRIVATE);
        if (isAccountLocked(userEmail)) {
            // Account is locked, prevent further attempts
//            Toast.makeText(this, "Your account is locked. Please try again later.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity to prevent further attempts
        }
        // Check if the account is locked
        long remainingTimeMillis = getRemainingLockTime(userEmail);
        if (remainingTimeMillis > 0) {
            // Account is locked, show remaining time in toast message
            String remainingTime = formatRemainingTime(remainingTimeMillis);
            Toast.makeText(this, "Your account is locked. Remaining time: " + remainingTime, Toast.LENGTH_SHORT).show();
            finish(); // Close activity to prevent further attempts
            return;
        }

        fetchUserData();

        binding.PhoneNumberConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Generate a 4-digit random number
                int randomCode = generateRandomCode();

                // Show the random code in a progress dialog
                ProgressDialog progressDialog = new ProgressDialog(VerifyPhoneNumber.this);
                progressDialog.setMessage("Your verification code: " + randomCode);
                progressDialog.setCancelable(true);
                progressDialog.show();

                // Pass the random code to the next activity
                Intent intent = new Intent(VerifyPhoneNumber.this, PhoneOPTVerification.class);
                intent.putExtra("OTP", randomCode);
                intent.putExtra("email", userEmail);

                // For demonstration, just show the EmailOTPVerification activity after a delay
                view.postDelayed(() -> {
                    progressDialog.dismiss();
                    startActivity(intent);
                    finish();
                }, 3000); // delay for 3 seconds
            }
        });
    }



    private int generateRandomCode() {
        Random random = new Random();
        return 1000 + random.nextInt(9000); // generates a random number between 1000 and 9999
    }



    private void fetchUserData() {
        dialog.show();
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
                    Toast.makeText(VerifyPhoneNumber.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(VerifyPhoneNumber.this, "Error", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            runOnUiThread(() -> {
                                try {
                                    dialog.dismiss();
                                    binding.PhoneNumberVeify.setText(user.getString("PhoneNumber"));
                                    // Assuming you have a field for phone number
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
    private boolean isAccountLocked(String email) {
        int failedAttempts = sharedPreferences.getInt(email, 0);
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            // Account is locked
            long lockTime = sharedPreferences.getLong(email + "_lock_time", 0);
            long currentTime = System.currentTimeMillis();
            if (currentTime - lockTime < LOCK_DURATION_MILLIS) {
                // Account is still locked
                return true;
            } else {
                // Account lock duration expired, unlock the account
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(email);
                editor.remove(email + "_lock_time");
                editor.apply();
            }
        }
        return false; // Account is not locked
    }
    private long getRemainingLockTime(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("lock_status", MODE_PRIVATE);
        long lockTime = sharedPreferences.getLong(email + "_lock_time", 0);
        long currentTime = System.currentTimeMillis();
        long elapsedTimeMillis = currentTime - lockTime;
        return Math.max(LOCK_DURATION_MILLIS - elapsedTimeMillis, 0);
    }

    private String formatRemainingTime(long remainingTimeMillis) {
        long hours = TimeUnit.MILLISECONDS.toHours(remainingTimeMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTimeMillis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTimeMillis) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
}