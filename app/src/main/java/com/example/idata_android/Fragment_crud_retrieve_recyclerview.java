/*package com.example.idata_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idata_android.Model.Elder;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_crud_retrieve_recyclerview extends Fragment implements View.OnClickListener {

    Button previous_bttn;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    OkHttpClient httpClient;
    String url;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crud_retrive_recyclerview, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                url = bundle.getString("finalUrl");
            }
        });

        previous_bttn = view.findViewById(R.id.previous_crud_retrieve_recyclerview);
        recyclerView = view.findViewById(R.id.recyclerview_retrive);

        previous_bttn.setOnClickListener(this);


        httpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                System.out.print("failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String jsonData = response.body().string();

                    Elder[] elderList =  new Gson().fromJson(jsonData, Elder[].class);

                        mAdapter = new RecyclerViewAdapter_retrieveElder(elderList);

                        //Must call UI thread to change layouts and views
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "runOnUiThread");
                                recyclerView.setAdapter(mAdapter);
                                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(layoutManager);
                            }
                        });
                }
            }
        });
        }

    @Override
    public void onStart() {
        super.onStart();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                url = bundle.getString("url");
                // Do something with the result...
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        bundle.putString("url", url);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.previous_crud_retrieve_recyclerview:
                NavHostFragment.findNavController(Fragment_crud_retrieve_recyclerview.this)
                        .navigate(R.id.action_FourthfragmentfragmentCrudRetrieveRecyclerview_to_ThirdfragmentCrudRetrieve);
                break;
    }
}
static String TAG = "Fragment_crud_retrieve_recyclerview";
}*/