package com.example.shoesecommerceapp.fetchCartProductsRecyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesecommerceapp.PurchaseProductsActivity;
import com.example.shoesecommerceapp.R;
import com.example.shoesecommerceapp.SeeProductsActivity;
import com.example.shoesecommerceapp.databinding.SampleBestsellerRecyclerBinding;
import com.example.shoesecommerceapp.databinding.SampleFavoriteLayoutBinding;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.bestsellerAdapter;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CartProAdapter extends RecyclerView.Adapter<CartProAdapter.viewHolder>{
    private List<responsemodel> data;

    Context context;
    String imageUrl;

    public CartProAdapter(List<responsemodel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void updateData(List<responsemodel> newData) {
        data.clear(); // Clear the existing data
        if (newData != null) {
            data.addAll(newData); // Add new data if not null
        }
        notifyDataSetChanged(); // Notify adapter of dataset changes
    }


    @NonNull
    @Override
    public CartProAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_favorite_layout, parent, false);
        return new CartProAdapter.viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CartProAdapter.viewHolder holder, int position) {
        responsemodel item = data.get(position);

        holder.binding.FavoriteProductName.setText(item.getName());
        holder.binding.FavoriteProductPrice.setText(item.getPrice());
        holder.binding.FavoriteProductDescription.setText(item.getDescription());
        holder.binding.FavoriteProductID.setText(item.getProductid());
        Glide.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.imageimage) // Set a placeholder image while loading
                .error(R.drawable.imageimage) // Set an error placeholder
                .into(holder.binding.imageView20);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PurchaseProductsActivity.class);
                intent.putExtra("Name", holder.binding.FavoriteProductName.getText().toString());
                intent.putExtra("Price", holder.binding.FavoriteProductPrice.getText().toString());
                intent.putExtra("Description", holder.binding.FavoriteProductDescription.getText().toString());
                intent.putExtra("ProductID", holder.binding.FavoriteProductID.getText().toString());
                intent.putExtra("ImageURL", item.getImage());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SampleFavoriteLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleFavoriteLayoutBinding.bind(itemView);
        }
    }
}
