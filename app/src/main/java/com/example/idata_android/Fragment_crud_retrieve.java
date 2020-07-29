package com.example.idata_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_crud_retrieve extends Fragment implements View.OnClickListener {

    String baseUrl;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                setBaseUrl(bundle.getString("baseUrl"));
                // Do something with the result...
            }
        });
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crud_retrive, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        view.findViewById(R.id.previous_crud_retrieve).setOnClickListener(this);
        view.findViewById(R.id.retrieveAllsummit_bttn).setOnClickListener(this);
            }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.previous_crud_retrieve:
                NavHostFragment.findNavController(Fragment_crud_retrieve.this)
                        .navigate(R.id.action_fragment_crud_retrieve_to_SecondFragment);
                break;

            case R.id.retrieveAllsummit_bttn:

                    break;


    }
}

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}