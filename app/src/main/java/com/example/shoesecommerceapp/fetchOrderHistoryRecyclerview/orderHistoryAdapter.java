package com.example.shoesecommerceapp.fetchOrderHistoryRecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesecommerceapp.R;
import com.example.shoesecommerceapp.databinding.SampleOrderHistoryLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class orderHistoryAdapter extends RecyclerView.Adapter<orderHistoryAdapter.viewHolder>{
private List<Orderresponemodel> data;

public orderHistoryAdapter(List<Orderresponemodel> data) {
        this.data = data != null ? data : new ArrayList<>(); // Ensure data is not null
        }

public void updateData(List<Orderresponemodel> newData) {
        data.clear(); // Clear the existing data
        if (newData != null) {
        data.addAll(newData); // Add new data if not null
        }
        notifyDataSetChanged(); // Notify adapter of dataset changes
        }


@NonNull
@Override
public orderHistoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_order_history_layout, parent, false);
        return new orderHistoryAdapter.viewHolder(view);
        }
@Override
public void onBindViewHolder(@NonNull orderHistoryAdapter.viewHolder holder, int position) {
        holder.binding.OrderHistoryPrice.setText(data.get(position).getPrice());
        holder.binding.OrderHistoryTrackingID.setText(data.get(position).getTrackingid());
        holder.binding.OrderHistoryStatus.setText(data.get(position).getStatus());
        holder.binding.OrderHistoryTimeStamp.setText(data.get(position).getTimestamp());

        }

@Override
public int getItemCount() {
        return data.size();
        }

public class viewHolder extends RecyclerView.ViewHolder {
    SampleOrderHistoryLayoutBinding binding;
    public viewHolder(@NonNull View itemView) {
        super(itemView);
        binding = SampleOrderHistoryLayoutBinding.bind(itemView);
    }
}
}
