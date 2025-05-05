package com.example.shoesecommerceapp.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.RouteListingPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesecommerceapp.Models.titlemodel;
import com.example.shoesecommerceapp.R;
import com.example.shoesecommerceapp.SeeProductsActivity;
import com.example.shoesecommerceapp.databinding.SampleBestsellerRecyclerBinding;
import com.example.shoesecommerceapp.databinding.SampleHomeTypeBinding;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.bestsellerAdapter;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;

import java.util.ArrayList;
import java.util.List;

public class titleadapter extends RecyclerView.Adapter<titleadapter.viewHolder> {

    Context context;
    List<titlemodel> list;
    OnItemClickListener listener;
    int selectedItem = 0; // Default to the first item (position 0)

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public titleadapter(Context context, List<titlemodel> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_home_type, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        titlemodel currentItem = list.get(position);
        holder.binding.textView55.setText(currentItem.getTitle());
        holder.binding.imageView24.setImageResource(currentItem.getImage());

        // Set the text style to bold if the item is selected
        holder.binding.textView55.setTypeface(null, position == selectedItem ? Typeface.BOLD : Typeface.NORMAL);

        // Set click listener on item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    // Update the selected item position
                    selectedItem = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    listener.onItemClick(selectedItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SampleHomeTypeBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleHomeTypeBinding.bind(itemView);
        }
    }

    // Method to select an item programmatically
    public boolean setSelectedItem(int position) {
        if (position >= 0 && position < list.size()) {
            selectedItem = position;
            notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
