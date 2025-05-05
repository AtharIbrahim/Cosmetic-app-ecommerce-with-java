package com.example.shoesecommerceapp.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shoesecommerceapp.EditProfile;
import com.example.shoesecommerceapp.LoginActivity;
import com.example.shoesecommerceapp.OrderHistoryActivity;
import com.example.shoesecommerceapp.R;
import com.example.shoesecommerceapp.SignUpActivity;
import com.example.shoesecommerceapp.VerifyEmailAddress;
import com.example.shoesecommerceapp.VerifyPhoneNumber;
import com.example.shoesecommerceapp.databinding.FragmentAccountBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;

    private static final String LOGOUT_URL = "http://192.168.169.98/ShoesAppECommerce/login.php";
    private static final String DELETE_ACCOUNT_URL = "http://192.168.169.98/ShoesAppECommerce/delete_account.php";

    private OkHttpClient client = new OkHttpClient();

    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    Dialog dialog;
    Dialog dialogDe;
    Dialog Loading;
    Button cancelBtn, LogoutBtn, cancelDe, DeleteBtn;
    String userEmail;
    String verifyEmail, verifyPhone;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Logging out!");
        dialog = new Dialog(getContext());
        dialogDe = new Dialog(getContext());
        Loading = new Dialog(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        // Retrieve the user's email from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", null);

        fetchUserData();

        // Set the content view of the dialog before finding the buttons
        dialog.setContentView(R.layout.custom_logout_alert_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialog.setCancelable(false);

        // Find the buttons after setting the content view
        cancelBtn = dialog.findViewById(R.id.logoutCancel);
        LogoutBtn = dialog.findViewById(R.id.LogoutAccept);

        // Set the content view of the dialog before finding the buttons
        dialogDe.setContentView(R.layout.custom_delete_account_alert_dialog);
        dialogDe.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogDe.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        dialogDe.setCancelable(false);

        // Find the buttons after setting the content view
        cancelDe = dialogDe.findViewById(R.id.DeleteCancel);
        DeleteBtn = dialogDe.findViewById(R.id.DeleteAccept);

        // Set the content view of the dialog before finding the buttons
        Loading.setContentView(R.layout.custom_loading_bar);
        Loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Loading.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_logout_alert_background));
        Loading.setCancelable(false);

        binding.AccountLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        binding.AccountInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                getContext().startActivity(intent);
            }
        });

        binding.OrderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        binding.VerifyEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VerifyEmailAddress.class);
                intent.putExtra("VerifyEmail", verifyEmail);
                getContext().startActivity(intent);
            }
        });
        binding.constraintLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogDe.show();
            }
        });

        binding.VerifyPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VerifyPhoneNumber.class);
                intent.putExtra("VerifyPhone", verifyPhone);
                getContext().startActivity(intent);
            }
        });




        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cancelDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDe.dismiss();
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.show();

                Request request = new Request.Builder()
                        .url(LOGOUT_URL)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Loading.dismiss();
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), "Logout failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.remove("email");
                                        editor.apply();

                                        Loading.dismiss();
                                        dialog.dismiss();
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                            }
                        } else {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Loading.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "Logout failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserAccount(userEmail);
            }
        });

        return view;
    }



    //Read data Start
    private void fetchUserData() {
        Loading.show();
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Email", userEmail)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.169.98/ShoesAppECommerce/retrive.php?action=readUser")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() -> {
                    Loading.dismiss();
                    Toast.makeText(getActivity(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject user = new JSONObject(responseData);
                        if (user.has("error")) {
                            getActivity().runOnUiThread(() -> {
                                Loading.dismiss();
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            getActivity().runOnUiThread(() -> {
                                try {
                                    Loading.dismiss();
                                    binding.UsernameOnAccount.setText(user.getString("Name"));
                                    binding.UseremailOnAccount.setText(user.getString("Email"));
                                    verifyEmail = user.getString("VerifyEmail");
                                    verifyPhone = user.getString("VerifyPhone");
                                    if ("null".equals(verifyEmail)) { // Use "null".equals(verifyEmail) for string comparison
//                                        binding.textView35.setTextColor(getResources().getColor(R.color.RED));
                                        binding.imageView16.setColorFilter(getResources().getColor(R.color.RED));
//                                        binding.imageView17.setColorFilter(getResources().getColor(R.color.RED));
                                        binding.linearLayout3.setBackgroundResource(R.drawable.account_boarder_red);
                                        binding.SuccessfulVerifyEmail.setVisibility(View.GONE);
                                        binding.EmailVerifciation.setText(verifyEmail);
                                    } else {
                                        binding.SuccessfulVerifyEmail.setVisibility(View.VISIBLE);
                                        binding.EmailVerifciation.setText(verifyEmail);
                                    }
                                    if ("null".equals(verifyPhone)) { // Use "null".equals(verifyEmail) for string comparison
//                                        binding.textView35.setTextColor(getResources().getColor(R.color.RED));
                                        binding.imageView18.setColorFilter(getResources().getColor(R.color.RED));
//                                        binding.imageView17.setColorFilter(getResources().getColor(R.color.RED));
                                        binding.linearLayout4.setBackgroundResource(R.drawable.account_boarder_red);
//                                        binding.SuccessfulVerifyEmail.setVisibility(View.GONE);
                                        binding.EmailVerifciation.setText(verifyPhone);
                                    } else {
//                                        binding.SuccessfulVerifyEmail.setVisibility(View.VISIBLE);
                                        binding.EmailVerifciation.setText(verifyPhone);
                                    }
                                    // Assuming you have a field for phone number
                                    // Load the user image if there's an URL field for it
                                    // Glide.with(EditProfile.this).load(user.getString("ImageURL")).into(binding.UserImageEdit);
                                    // Assuming you have a field for phone number
                                    // Load the user image if there's an URL field for it
                                    // Glide.with(EditProfile.this).load(user.getString("ImageURL")).into(binding.UserImageEdit);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    //Read data End




    //Delete Account Strat
    private void deleteUserAccount(String email) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Email", email)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.169.98/ShoesAppECommerce/delete_account.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() -> {
                    Loading.dismiss();
                    dialogDe.dismiss();
                    Toast.makeText(getActivity(), "Failed to delete user account", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("email");
                        editor.apply();
                        // Account deleted successfully, navigate to login or other appropriate screen
                        Loading.dismiss();
                        dialogDe.dismiss();
                        Toast.makeText(getActivity(), "Account deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), SignUpActivity.class);
                        startActivity(intent);
                        getActivity().finishAffinity();

                        // You might want to clear the shared preferences and navigate to login activity
                        // SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                        // sharedPreferences.edit().clear().apply();
                        // startActivity(new Intent(EditProfile.this, LoginActivity.class));
                        // finish(); // Optional, depending on your navigation flow
                    } else {
                        Loading.dismiss();
                        dialogDe.dismiss();
                        Toast.makeText(getActivity(), "Failed to delete user account", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
