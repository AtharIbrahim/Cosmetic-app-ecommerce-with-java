package com.example.shoesecommerceapp.fetchBestsellerRecyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesecommerceapp.R;
import com.example.shoesecommerceapp.SeeProductsActivity;
import com.example.shoesecommerceapp.databinding.SampleBestsellerRecyclerBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class bestsellerAdapter extends RecyclerView.Adapter<bestsellerAdapter.viewHolder>{
    private List<responsemodel> data;
    Context context;

    public bestsellerAdapter(List<responsemodel> data, Context context) {
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
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_bestseller_recycler, parent, false);
        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        responsemodel item = data.get(position);

        // Log item details for debugging
        Log.d("bestsellerAdapter", "Item: " + new Gson().toJson(item));

        if (context != null && item.getImage() != null && !item.getImage().isEmpty()) {
            String baseUrl = "http://192.168.169.98/ShoesAppECommerce/images/";
            String imageUrl = baseUrl + item.getImage();

            // Log the URL to verify it's correct
            Log.d("bestsellerAdapter", "Loading image URL: " + imageUrl);

            // Check if the view is attached to the window before loading the image
            holder.binding.shoesimage.post(() -> {
                if (holder.binding.shoesimage.isAttachedToWindow()) {
                    Glide.with(context)
                            .load(imageUrl)
                            .placeholder(R.drawable.imageimage) // Set a placeholder image while loading
                            .error(R.drawable.imageimage) // Set an error placeholder
                            .into(holder.binding.shoesimage);
                } else {
                    // If not attached, set a placeholder or error image
                    holder.binding.shoesimage.setImageResource(R.drawable.imageimage);
                }
            });
        } else {
            // If context is null or image URL is empty, set a placeholder or error image
            Log.e("bestsellerAdapter", "Context is null or image URL is empty");
            holder.binding.shoesimage.setImageResource(R.drawable.app_icon);
        }

        holder.binding.Shoesname.setText(item.getName());
        holder.binding.shoesPrice.setText(item.getPrice());
        holder.binding.ShoesDescription.setText(item.getDescription());
        holder.binding.ShoesProductID.setText(item.getProductid());
        holder.binding.ShoesProductQuantity.setText(item.getQuantity());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SeeProductsActivity.class);
            intent.putExtra("Name", item.getName());
            intent.putExtra("Price", item.getPrice());
            intent.putExtra("Description", item.getDescription());
            intent.putExtra("ProductID", item.getProductid());
            intent.putExtra("Quantity", item.getQuantity());
            intent.putExtra("IMAGE_URL", "http://192.168.169.98/ShoesAppECommerce/images/" + item.getImage());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SampleBestsellerRecyclerBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleBestsellerRecyclerBinding.bind(itemView);
        }
    }
}
