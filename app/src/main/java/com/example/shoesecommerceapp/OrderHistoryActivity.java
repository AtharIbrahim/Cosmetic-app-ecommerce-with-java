package com.example.shoesecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoesecommerceapp.Fragments.HomeFragment;
import com.example.shoesecommerceapp.databinding.ActivityOrderHistoryBinding;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;
import com.example.shoesecommerceapp.fetchCartProductsRecyclerview.APISettingController;
import com.example.shoesecommerceapp.fetchCartProductsRecyclerview.CartProAdapter;
import com.example.shoesecommerceapp.fetchOrderHistoryRecyclerview.Orderresponemodel;
import com.example.shoesecommerceapp.fetchOrderHistoryRecyclerview.SetAPIWorkingController;
import com.example.shoesecommerceapp.fetchOrderHistoryRecyclerview.orderHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {

    ActivityOrderHistoryBinding binding;

    orderHistoryAdapter orderHistoryAdapter;

    String email;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(OrderHistoryActivity.this);
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(true);

        // Retrieve the user's email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        email = sharedPreferences.getString("email", null);
        //set Layout of best seller
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this,LinearLayoutManager.VERTICAL,false);
        binding.OrderHistoryRecyclerview.setLayoutManager(linearLayoutManager);
        orderHistoryAdapter = new orderHistoryAdapter(new ArrayList<>());
        binding.OrderHistoryRecyclerview.setAdapter(orderHistoryAdapter);
        // Initialize the adapter with an empty list

        processdataBestSeller(email);

    }

    public void processdataBestSeller(String email) {
        dialog.show();
        // Modify the API call to include a query parameter or filter for the email
        Call<List<Orderresponemodel>> call = SetAPIWorkingController.getInstance().getapi().getdata(email);
        call.enqueue(new Callback<List<Orderresponemodel>>() {
            @Override
            public void onResponse(Call<List<Orderresponemodel>> call, Response<List<Orderresponemodel>> response) {

                if (response.isSuccessful()) {
                    List<Orderresponemodel> cartItems = response.body();
                    if (cartItems != null  && !cartItems.isEmpty()) {
                        dialog.dismiss();
                        orderHistoryAdapter.updateData(cartItems);
                    } else {
                        dialog.show();
                        orderHistoryAdapter.updateData(new ArrayList<>());
                        binding.textView54.setVisibility(View.VISIBLE);
                    }
                } else {
                    dialog.dismiss();
//                    binding.scrollView3.setVisibility(View.VISIBLE);
                    Log.e("CartFragment", "Response not successful: " + response.message());
                    orderHistoryAdapter.updateData(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Orderresponemodel>> call, Throwable t) {
//                binding.scrollView3.setVisibility(View.VISIBLE);
                dialog.dismiss();
                Log.e("CartFragment", "API call failed: " + t.getMessage(), t);
                Toast.makeText(OrderHistoryActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                orderHistoryAdapter.updateData(new ArrayList<>());
            }
        });
    }


}