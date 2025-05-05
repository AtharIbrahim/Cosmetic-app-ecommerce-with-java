package com.example.shoesecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoesecommerceapp.databinding.ActivityPurchaseProductsBinding;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PurchaseProductsActivity extends AppCompatActivity {
    ActivityPurchaseProductsBinding binding;

    private static final String Favorite_URL = "http://192.168.169.98/ShoesAppECommerce/FavoriteProductsSave.php";
    private static final String OrderHistory_URL = "http://192.168.169.98/ShoesAppECommerce/orderhistory.php";
    private static final String DeleteCart_URL = "http://192.168.169.98/ShoesAppECommerce/DeleteCartProduct.php";
    private static final String UpdateCart_URL = "http://192.168.169.98/ShoesAppECommerce/updateorderhistory.php";
    private OkHttpClient client = new OkHttpClient();

    String userEmail;
    String ProductID;

    ProgressDialog progressDialog;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(PurchaseProductsActivity.this);
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);

        progressDialog = new ProgressDialog(PurchaseProductsActivity.this);
        progressDialog.setTitle("Purchasing....");
        // Retrieve the user's email from SharedPreferences
        SharedPreferences sharedPreferences = PurchaseProductsActivity.this.getSharedPreferences("user_prefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", null);

        String Name = getIntent().getStringExtra("Name");
        String Price = getIntent().getStringExtra("Price");
        String Description = getIntent().getStringExtra("Description");
        String ImageURL = getIntent().getStringExtra("ImageURL");
         ProductID = getIntent().getStringExtra("ProductID");


        binding.SeeProductName.setText(Name);
        binding.SeeProductPrice.setText(Price);
        binding.SeeProductDescription.setText(Description);
        Glide.with(PurchaseProductsActivity.this)
                .load(ImageURL)
                .placeholder(R.drawable.imageimage) // Set a placeholder image while loading
                .error(R.drawable.imageimage) // Set an error placeholder
                .into(binding.PurchaseProductImage);

        binding.SeeProductHeartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(userEmail, ProductID);
            }
        });
        binding.PurchaseCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String trackingId = generateTrackingID();
                saveCartData(userEmail, ProductID, Name, Price, Description, trackingId, "Pending");
            }
        });
        binding.PurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.show();
                String trackingId = generateTrackingID();
                saveCartData(userEmail, ProductID, Name, Price, Description, trackingId, "Pending");
            }
        });
    }


    // Generate a unique tracking ID
    private String generateTrackingID() {
        return UUID.randomUUID().toString();
    }


    //Save Favorte Data
    public void saveData(String email, String product){
        RequestBody formdata = new FormBody.Builder()
                .add("email", email)
                .add("productid", product)
                .build();

        Request request  =new Request.Builder().url(Favorite_URL).post(formdata).build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                dialog.dismiss();
                Toast.makeText(PurchaseProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String resp =  response.body().string();
                if(response.isSuccessful()){
                    PurchaseProductsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dialog.dismiss();
                                Toast.makeText(PurchaseProductsActivity.this, "Product Added to favorite", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                dialog.dismiss();
                                Toast.makeText(PurchaseProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    //Save Order history Data
    public void saveCartData(String email, String product, String name, String price, String description, String trackingid, String status){
        RequestBody formdata = new FormBody.Builder()
                .add("email", email)
                .add("productid", product)
                .add("name", name)
                .add("price", price)
                .add("description", description)
                .add("trackingid", trackingid)
                .add("status", status)
                .build();

        Request request  =new Request.Builder().url(OrderHistory_URL).post(formdata).build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                dialog.dismiss();
                Toast.makeText(PurchaseProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String resp =  response.body().string();
                if(response.isSuccessful()){
                    PurchaseProductsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dialog.dismiss();
                                deleteCartItem(userEmail, ProductID);
                                updateOrderStatus(userEmail, ProductID);
                            }
                            catch (Exception e){
                                dialog.dismiss();
                                Toast.makeText(PurchaseProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }



    // Delete item from user's cart
    public void deleteCartItem(String email, String productid) {
        RequestBody formdata = new FormBody.Builder()
                .add("email", email)
                .add("productid", productid)
                .build();

        Request request = new Request.Builder()
                .url(DeleteCart_URL)
                .post(formdata)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                dialog.dismiss();
                Toast.makeText(PurchaseProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String resp = response.body().string();
                if (response.isSuccessful()) {
                    PurchaseProductsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dialog.dismiss();
                            } catch (Exception e) {
                                dialog.dismiss();
                                Toast.makeText(PurchaseProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }



    // Update order status after successful purchase
    public void updateOrderStatus(String email, String productid) {
        RequestBody formdata = new FormBody.Builder()
                .add("email", email)
                .add("productid", productid)
                .build();

        Request request = new Request.Builder()
                .url(UpdateCart_URL)
                .post(formdata)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                dialog.dismiss();
                Toast.makeText(PurchaseProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String resp = response.body().string();
                if (response.isSuccessful()) {
                    PurchaseProductsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dialog.dismiss();
                                Intent intent = new Intent(PurchaseProductsActivity.this, PurchasedSuccessfullActivityScreen.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                dialog.dismiss();
                                Toast.makeText(PurchaseProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}