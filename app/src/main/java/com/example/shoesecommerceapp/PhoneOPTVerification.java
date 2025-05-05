package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoesecommerceapp.databinding.ActivityPhoneOptverificationBinding;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PhoneOPTVerification extends AppCompatActivity {
    ActivityPhoneOptverificationBinding binding;
    private static final int MAX_FAILED_ATTEMPTS = 0; // Maximum allowed failed attempts
    private static final long LOCK_DURATION_MILLIS = 60 * 1000; // Lock duration: 1 day in milliseconds

    private SharedPreferences sharedPreferences;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneOptverificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(PhoneOPTVerification.this);
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("lock_status", MODE_PRIVATE);

        // Retrieve the OTP from the intent
        int OTP = getIntent().getIntExtra("OTP", -1);
        String email = getIntent().getStringExtra("email");

        if (isAccountLocked(email)) {
            // Account is locked, prevent further attempts
            finish(); // Close activity to prevent further attempts
        }
        // Check if the account is locked
        long remainingTimeMillis = getRemainingLockTime(email);
        if (remainingTimeMillis > 0) {
            // Account is locked, show remaining time in toast message
            String remainingTime = formatRemainingTime(remainingTimeMillis);
            Toast.makeText(this, "Your account is locked. Remaining time: " + remainingTime, Toast.LENGTH_SHORT).show();
            finish(); // Close activity to prevent further attempts
            return;
        }
        // Display the OTP

        binding.PhoneNumberOTPConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String otp = binding.PhoneNumberVeifyOTP.getText().toString();
                if (TextUtils.isEmpty(otp)) {
                    dialog.dismiss();
                    binding.PhoneNumberVeifyOTP.setError("OTP is Empty");
                    return;
                }
                if (!otp.equals(String.valueOf(OTP))) {
                    dialog.dismiss();
                    binding.PhoneNumberVeifyOTP.setError("OTP Verification Failed..");
                    binding.PhoneNumberVeifyOTP.setText("");
                    handleFailedVerification(email);
                    return;
                }
                dialog.dismiss();
                Intent intent = new Intent(PhoneOPTVerification.this, SuccessVerifyPhoneNumber.class);
                finish();
                startActivity(intent);
                updateUser(email, otp);
                finish();
            }
        });

    }



    private void updateUser(String email, String verifyPhone) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Email", email)
                .add("VerifyPhone", verifyPhone)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.169.98/ShoesAppECommerce/verifyphone.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    dialog.dismiss();
                    Toast.makeText(PhoneOPTVerification.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        // Update any UI elements if needed
                    } else {
                        dialog.dismiss();
                        Toast.makeText(PhoneOPTVerification.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void handleFailedVerification(String email) {
        int failedAttempts = sharedPreferences.getInt(email, 0);
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            // Account is locked
            long lockTime = sharedPreferences.getLong(email + "_lock_time", 0);
            long currentTime = System.currentTimeMillis();
            if (currentTime - lockTime < LOCK_DURATION_MILLIS) {
                // Account is still locked, notify the user
                dialog.dismiss();
                Intent intent = new Intent(PhoneOPTVerification.this, VerifyEmailAddress.class);
                startActivity(intent);
                finish();
                return;
            } else {
                // Account lock duration expired, unlock the account
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(email);
                editor.remove(email + "_lock_time");
                editor.apply();
            }
        }
        // Increment failed attempts
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(email, failedAttempts + 0);
        editor.putLong(email + "_lock_time", System.currentTimeMillis()); // Update lock time
        editor.apply();
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