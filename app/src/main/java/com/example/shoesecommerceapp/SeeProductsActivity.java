package com.example.shoesecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoesecommerceapp.databinding.ActivitySeeProductsBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SeeProductsActivity extends AppCompatActivity {
    ActivitySeeProductsBinding binding;

    private static final String Favorite_URL = "http://192.168.169.98/ShoesAppECommerce/FavoriteProductsSave.php";
    private static final String Cart_URL = "http://192.168.169.98/ShoesAppECommerce/CartProductsSave.php";
    private OkHttpClient client = new OkHttpClient();

    String userEmail;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(SeeProductsActivity.this);
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);

        // Retrieve the user's email from SharedPreferences
        SharedPreferences sharedPreferences = SeeProductsActivity.this.getSharedPreferences("user_prefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", null);

        String Name = getIntent().getStringExtra("Name");
        String Price = getIntent().getStringExtra("Price");
        String Description = getIntent().getStringExtra("Description");
        String ProductID = getIntent().getStringExtra("ProductID");
        String Quantity = getIntent().getStringExtra("Quantity");
        String ImageURL = getIntent().getStringExtra("IMAGE_URL");


        if(Quantity.length()>=2){
            binding.textView58.setText("IN STOCK");
        }
        else{
            binding.textView58.setText("OUT OF STOCK");
        }
        binding.SeeProductName.setText(Name);
        binding.SeeProductPrice.setText(Price);
        binding.SeeProductDescription.setText(Description);
        Glide.with(SeeProductsActivity.this).load(ImageURL).placeholder(R.drawable.imageimage) // Set a placeholder image while loading
                .error(R.drawable.imageimage) // Set an error placeholder
                .into(binding.SeeProductImage);
        
        binding.SeeProductHeartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(userEmail, ProductID);
            }
        });
        binding.SeeProductAddCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.textView58.getText().toString().equals("IN STOCK")) {
                    saveCartData(userEmail, ProductID, Name, Price, Description, ImageURL);
                }
            }
        });
        binding.AddToCartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.textView58.getText().toString().equals("IN STOCK")) {
                    saveCartData(userEmail, ProductID, Name, Price, Description, ImageURL);
                }
            }
        });


    }

    //Save Favorte Data
    public void saveData(String email, String product){
        dialog.show();
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
                Toast.makeText(SeeProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String resp =  response.body().string();
                if(response.isSuccessful()){
                    SeeProductsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dialog.dismiss();
                                Toast.makeText(SeeProductsActivity.this, "Product Added To Favorite....", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                dialog.dismiss();
                                Toast.makeText(SeeProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    //Save Cart Data
    public void saveCartData(String email, String product, String name, String price, String description, String image){
        dialog.show();
        RequestBody formdata = new FormBody.Builder()
                .add("email", email)
                .add("productid", product)
                .add("name", name)
                .add("price", price)
                .add("description", description)
                .add("image", image)
                .build();

        Request request  =new Request.Builder().url(Cart_URL).post(formdata).build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                dialog.dismiss();
                Toast.makeText(SeeProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String resp =  response.body().string();
                if(response.isSuccessful()){
                    SeeProductsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dialog.dismiss();
                                Intent intent = new Intent(SeeProductsActivity.this, AddedCartActivity.class);
                                startActivity(intent);
                            }
                            catch (Exception e){
                                dialog.dismiss();
                                Toast.makeText(SeeProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}