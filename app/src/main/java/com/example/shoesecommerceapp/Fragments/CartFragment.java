package com.example.shoesecommerceapp.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoesecommerceapp.R;
import com.example.shoesecommerceapp.databinding.FragmentCartBinding;
import com.example.shoesecommerceapp.databinding.FragmentHomeBinding;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.apicontroller;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.bestsellerAdapter;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;
import com.example.shoesecommerceapp.fetchCartProductsRecyclerview.APISettingController;
import com.example.shoesecommerceapp.fetchCartProductsRecyclerview.CartProAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    FragmentCartBinding binding;
    CartProAdapter adapter;
    String email;

    Dialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dialog = new Dialog(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);

        // Retrieve the user's email from SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_prefs", MODE_PRIVATE);
        email = sharedPreferences.getString("email", null);

        //set Layout of best seller
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        binding.CartProductRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new CartProAdapter(new ArrayList<>(), getContext());
        binding.CartProductRecyclerview.setAdapter(adapter);
        // Initialize the adapter with an empty list

        processdataBestSeller(email); // Pass the email to the method
        return view;
    }

    public void processdataBestSeller(String email) {
        dialog.show();
        // Modify the API call to include a query parameter or filter for the email
        Call<List<responsemodel>> call = APISettingController.getInstance().getapi().getdata(email);
        call.enqueue(new Callback<List<responsemodel>>() {
            @Override
            public void onResponse(Call<List<responsemodel>> call, Response<List<responsemodel>> response) {

                if (response.isSuccessful()) {
                    List<responsemodel> cartItems = response.body();
                    if (cartItems != null  && !cartItems.isEmpty()) {
                        dialog.dismiss();
                        adapter.updateData(cartItems);
                    } else {
                        adapter.updateData(new ArrayList<>());
                        dialog.dismiss();
                        binding.scrollView3.setVisibility(View.VISIBLE);
                    }
                } else {
                    dialog.show();
//                    binding.scrollView3.setVisibility(View.VISIBLE);
                    Log.e("CartFragment", "Response not successful: " + response.message());
                    adapter.updateData(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<responsemodel>> call, Throwable t) {
//                binding.scrollView3.setVisibility(View.VISIBLE);
                dialog.dismiss();
                Log.e("CartFragment", "API call failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.updateData(new ArrayList<>());
            }
        });
    }


}