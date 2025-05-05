package com.example.shoesecommerceapp.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.shoesecommerceapp.Adapters.titleadapter;
import com.example.shoesecommerceapp.EditProfile;
import com.example.shoesecommerceapp.EyesProduct.EyeProductController;
import com.example.shoesecommerceapp.EyesProduct.EyebestsellerController;
import com.example.shoesecommerceapp.EyesProduct.EyefeatureproductController;
import com.example.shoesecommerceapp.FaceProducts.FaceBestserllerController;
import com.example.shoesecommerceapp.FaceProducts.FaceFeatureController;
import com.example.shoesecommerceapp.FaceProducts.FaceProductController;
import com.example.shoesecommerceapp.FacemaskProduct.FacemaskProductController;
import com.example.shoesecommerceapp.FacemaskProduct.FacemaskbestsellerController;
import com.example.shoesecommerceapp.FacemaskProduct.FacemaskfeatureproductController;
import com.example.shoesecommerceapp.FacialProduct.FacialProductController;
import com.example.shoesecommerceapp.FacialProduct.FacialbestsellerController;
import com.example.shoesecommerceapp.FacialProduct.FacialfeatureproductController;
import com.example.shoesecommerceapp.Models.titlemodel;
import com.example.shoesecommerceapp.MoisturizerProduct.moisturizerbestsellerController;
import com.example.shoesecommerceapp.MoisturizerProduct.moisturizerfeatureproductController;
import com.example.shoesecommerceapp.MoisturizerProduct.moisturizerproductController;
import com.example.shoesecommerceapp.NailProducts.nailAPIController;
import com.example.shoesecommerceapp.NailProducts.nailbestsellerController;
import com.example.shoesecommerceapp.OrderHistoryActivity;
import com.example.shoesecommerceapp.R;
import com.example.shoesecommerceapp.SkinCareProduct.SkincareController;
import com.example.shoesecommerceapp.SkinCareProduct.SkincarebestsellerController;
import com.example.shoesecommerceapp.SkinCareProduct.skincarefeatureproductController;
import com.example.shoesecommerceapp.SunscreenProducts.SunscreenbestsellerController;
import com.example.shoesecommerceapp.SunscreenProducts.SunscreenfeatureController;
import com.example.shoesecommerceapp.SunscreenProducts.SunscreenproductController;
import com.example.shoesecommerceapp.databinding.FragmentHomeBinding;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.apicontroller;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.bestsellerAdapter;
import com.example.shoesecommerceapp.fetchBestsellerRecyclerview.responsemodel;
import com.example.shoesecommerceapp.fetchLipstickRecyclerview.lipstickAPIController;
import com.example.shoesecommerceapp.fetchLipstickRecyclerview.lipstickbestsellerController;
import com.example.shoesecommerceapp.fetchLipstickRecyclerview.lipstickfeatureproductController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumeAPIController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumebestsellerController;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproduct;
import com.example.shoesecommerceapp.fetchPerfumesRecyclerview.perfumefeatureproductController;
import com.example.shoesecommerceapp.fetchallproductsRecyclerview.AllProductsAPIController;
import com.example.shoesecommerceapp.fetchfeatureProductsRecyclerview.featureAPIcontroller;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    bestsellerAdapter adapter;

    bestsellerAdapter adapterFeature;

    bestsellerAdapter adapterAllProducts;


    RecyclerView.LayoutManager layoutManager;
    List<titlemodel> list;
    Call<List<responsemodel>> call;

    Dialog dialog;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Assuming you have a Toolbar in your layout
        setHasOptionsMenu(true); // Ensure tha
        dialog = new Dialog(getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Setup toolbar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(binding.toolbar8);
            activity.getSupportActionBar().setTitle("");
            setHasOptionsMenu(true); // Tell the fragment that it has options menu
        }
        return binding.getRoot();
    }
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Make custom dialog for loading bar start
        dialog.setContentView(R.layout.custom_loading_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);
        //Make custom dialog for loading bar end

        //set Layout of best seller Start
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.bestsellerRecyclerview2.setLayoutManager(linearLayoutManager);
        adapter = new bestsellerAdapter(new ArrayList<>(), getContext());
        binding.bestsellerRecyclerview2.setAdapter(adapter);
        //set Layout of best seller End

        //set layout of feature products Start
        LinearLayoutManager linearLayoutManagerFeature = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.featureProductsRecyclerview.setLayoutManager(linearLayoutManagerFeature);
        adapterFeature = new bestsellerAdapter(new ArrayList<>(), getContext());
        binding.featureProductsRecyclerview.setAdapter(adapterFeature);
        //set layout of feature products StaEndrt


        //set layout of all products Start
        layoutManager = new GridLayoutManager(getContext(), 2);
        binding.AllProductsRecyclerview.setLayoutManager(layoutManager);
        adapterAllProducts = new bestsellerAdapter(new ArrayList<>(), getContext());
        binding.AllProductsRecyclerview.setAdapter(adapterAllProducts);
        //set layout of all products End

        //Set types in product type recycler view Start
        list = new ArrayList<titlemodel>();
        list.add(new titlemodel("All", R.drawable.allproduct_type));
        list.add(new titlemodel("Lipstick", R.drawable.lipstick_type));
        list.add(new titlemodel("Perfume", R.drawable.perfume_type));
        list.add(new titlemodel("Nail Polish", R.drawable.nail_type));
        list.add(new titlemodel("Face Base", R.drawable.facebase_type));
        list.add(new titlemodel("Eye Care", R.drawable.eyecare_type));
        list.add(new titlemodel("Skin Care", R.drawable.skincare_type));
        list.add(new titlemodel("Moisturizer", R.drawable.moisturizer_type));
        list.add(new titlemodel("Facial", R.drawable.facebase_type));
        list.add(new titlemodel("Face Mask", R.drawable.facemask_type));
        list.add(new titlemodel("Sun Screen", R.drawable.sunscreen_type));
        //Set Content as horizontal
        LinearLayoutManager linearLayoutManagerType = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.TypeRecyclerview.setLayoutManager(linearLayoutManagerType);

        titleadapter titleAdapter = new titleadapter(getContext(), list, new titleadapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                titlemodel clickedItem = list.get(position);
                String itemName = clickedItem.getTitle();
                switch (itemName) {
                    case "All":
                        processdataBestSeller();
                        processdataAllProducts();
                        processdataFeatureData();
                        break;
                    case "Lipstick":
                        processdataLipstick();
                        processdataLipstickbestseller();
                        processdataLipstickfeatureproduct();
                        break;
                    case "Perfume":
                        processdataPerfumes();
                        processdataPerfumesbestseller();
                        processdataPerfumesfeatureproduct();
                        break;
                    case "Nail Polish":
                        nailproduct();
                        nailbestseller();
                        nailfestureproduct();
                        break;
                    case "Face Base":
                        faceproduct();
                        facebestseller();
                        facefeatureproduct();
                        break;
                    case "Eye Care":
                        eyeproduct();
                        eyebestseller();
                        eyefeatureproduct();
                        break;
                    case "Skin Care":
                        skincareproduct();
                        skincarebestseller();
                        skincarefeatureproduct();
                        break;
                    case "Moisturizer":
                        moisturizerproduct();
                        moisturizerbestseller();
                        moisturizerfeatureproduct();
                        break;
                    case "Facial":
                        facialproduct();
                        facialbestseller();
                        facialfeatureproduct();
                        break;
                    case "Face Mask":
                        facemaskproduct();
                        facemaskbestseller();
                        facemaskfeatureproduct();
                        break;
                    case "Sun Screen":
                        sunscreenproduct();
                        sunscreenbestseller();
                        sunscreenfeatureproduct();
                        break;
                    default:
                        break;
                }
            }
        });
        //Set default selection is must be ALL Type
        titleAdapter.setSelectedItem(0);
        if(titleAdapter.setSelectedItem(0)){
            processdataBestSeller();
            processdataAllProducts();
            processdataFeatureData();
        }
        binding.TypeRecyclerview.setAdapter(titleAdapter);
        //Set types in product type recycler view End

    }

    //Get Content from API and set as (All) Product Type Start
    public void processdataBestSeller() {
        call = apicontroller.getInstance().getapi().getdata();
        bestseller();
    }
    public void processdataFeatureData() {
        call = featureAPIcontroller.getInstance().getapi().getdata();
        featureproduct();
    }
    public void processdataAllProducts() {
        call = AllProductsAPIController.getInstance().getapi().getdata();
        allproducts();
    }
    //Get Content from API and set as (All) Product Type End

    //Get Content from API and set as (Perfume) Product Type Start
    public void processdataPerfumes() {
        call = perfumeAPIController.getInstance().getapi().getdata();
        allproducts();
    }
    public void processdataPerfumesbestseller() {
        call = perfumebestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void processdataPerfumesfeatureproduct() {
        call = perfumefeatureproductController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Perfume) Product Type End

    //Get Content from API and set as (Lipstick) Product Type Start
    public void processdataLipstick() {
        call = lipstickAPIController.getInstance().getapi().getdata();
        allproducts();
    }
    public void processdataLipstickbestseller() {
        call = lipstickbestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void processdataLipstickfeatureproduct() {
        call = lipstickfeatureproductController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Lipstick) Product Type End

    //Get Content from API and set as (Nails) Product Type Start
    public void nailproduct() {
        call = nailAPIController.getInstance().getapi().getdata();
        allproducts();
    }
    public void nailbestseller() {
        call = nailbestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void nailfestureproduct() {
        call = lipstickfeatureproductController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Nails) Product Type End

    //Get Content from API and set as (Face) Product Type Start
    public void faceproduct() {
        call = FaceProductController.getInstance().getapi().getdata();
        allproducts();
    }
    public void facebestseller() {
        call = FaceBestserllerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void facefeatureproduct() {
        call = FaceFeatureController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Face) Product Type End

    //Get Content from API and set as (Eyes) Product Type Start
    public void eyeproduct() {
        call = EyeProductController.getInstance().getapi().getdata();
        allproducts();
    }
    public void eyebestseller() {
        call = EyebestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void eyefeatureproduct() {
        call = EyefeatureproductController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Eyes) Product Type End

    //Get Content from API and set as (Skin Care) Product Type Start
    public void skincareproduct() {
        call = SkincareController.getInstance().getapi().getdata();
        allproducts();
    }
    public void skincarebestseller() {
        call = SkincarebestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void skincarefeatureproduct() {
        call = skincarefeatureproductController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Skin Care) Product Type End

    //Get Content from API and set as (Moisturizer) Product Type Start
    public void moisturizerproduct() {
        call = moisturizerproductController.getInstance().getapi().getdata();
        allproducts();
    }
    public void moisturizerbestseller() {
        call = moisturizerbestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void moisturizerfeatureproduct() {
        call = moisturizerfeatureproductController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Moisturizer) Product Type End

    //Get Content from API and set as (Facial) Product Type Start
    public void facialproduct() {
        call = FacialProductController.getInstance().getapi().getdata();
        allproducts();
    }
    public void facialbestseller() {
        call = FacialbestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void facialfeatureproduct() {
        call = FacialfeatureproductController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Facial) Product Type End

    //Get Content from API and set as (Face Mask) Product Type Start
    public void facemaskproduct() {
        call = FacemaskProductController.getInstance().getapi().getdata();
        allproducts();
    }
    public void facemaskbestseller() {
        call = FacemaskbestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void facemaskfeatureproduct() {
        call = FacemaskfeatureproductController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Face Mask) Product Type End

    //Get Content from API and set as (Sun Screen) Product Type Start
    public void sunscreenproduct() {
        call = SunscreenproductController.getInstance().getapi().getdata();
        allproducts();
    }
    public void sunscreenbestseller() {
        call = SunscreenbestsellerController.getInstance().getapi().getdata();
        bestseller();
    }
    public void sunscreenfeatureproduct() {
        call = SunscreenfeatureController.getInstance().getapi().getdata();
        featureproduct();
    }
    //Get Content from API and set as (Sun Screen) Product Type End

    //All Best Sellers Function
    public void bestseller(){
        dialog.show();
        call.enqueue(new Callback<List<responsemodel>>() {
            @Override
            public void onResponse(Call<List<responsemodel>> call, Response<List<responsemodel>> response) {
                if (response.isSuccessful()) {
                    List<responsemodel> data = response.body();
                    if (data != null) {
                        // Log the entire response
                        dialog.dismiss();
                        Log.d("API Response", new Gson().toJson(data));
                        adapter.updateData(data);
                    } else {
                        dialog.show();
                        adapter.updateData(new ArrayList<>());
                    }
                } else {
                    dialog.dismiss();
                    // Handle unsuccessful response
                    Log.e("HomeFragment", "Response not successful: " + response.message());
                    adapter.updateData(new ArrayList<>());
                }
            }
            @Override
            public void onFailure(Call<List<responsemodel>> call, Throwable t) {
                // Handle failure
                dialog.dismiss();
                Log.e("HomeFragment", "API call failed: " + t.getMessage(), t);
//                Toast.makeText(getContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.updateData(new ArrayList<>());
            }
        });
    }
    //All Feature Product Function
    public void featureproduct(){
        dialog.show();
        call.enqueue(new Callback<List<responsemodel>>() {
            @Override
            public void onResponse(Call<List<responsemodel>> call, Response<List<responsemodel>> response) {
                if (response.isSuccessful()) {
                    List<responsemodel> data = response.body();
                    if (data != null) {
                        dialog.dismiss();
                        adapterFeature.updateData(data);
                    } else {
                        dialog.show();
                        adapterFeature.updateData(new ArrayList<>());
                    }
                } else {
                    dialog.dismiss();
                    // Handle unsuccessful response
                    Log.e("HomeFragment", "Response not successful: " + response.message());
                    adapterFeature.updateData(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<responsemodel>> call, Throwable t) {
                // Handle failure
                dialog.dismiss();
                Log.e("HomeFragment", "API call failed: " + t.getMessage(), t);
//                Toast.makeText(getContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                adapterFeature.updateData(new ArrayList<>());
            }
        });
    }
    //All Products Display Functions
    public void allproducts(){
        dialog.show();
        call.enqueue(new Callback<List<responsemodel>>() {
            @Override
            public void onResponse(Call<List<responsemodel>> call, Response<List<responsemodel>> response) {
                if (response.isSuccessful()) {
                    List<responsemodel> data = response.body();
                    if (data != null) {
                        dialog.dismiss();
                        adapterAllProducts.updateData(data);
                    } else {
                        dialog.show();
                        adapterAllProducts.updateData(new ArrayList<>());
                    }
                } else {
                    dialog.dismiss();
                    // Handle unsuccessful response
                    Log.e("HomeFragment", "Response not successful: " + response.message());
                    adapterAllProducts.updateData(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<responsemodel>> call, Throwable t) {
                // Handle failure
                dialog.dismiss();
                Log.e("HomeFragment", "API call failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                adapterAllProducts.updateData(new ArrayList<>());
            }
        });
    }


    // Inflate the menu resource file
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        CharSequence title = item.getTitle();

        if ("Cart".equals(title)) {
           Intent intent = new Intent(getContext(), OrderHistoryActivity.class);
           startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
