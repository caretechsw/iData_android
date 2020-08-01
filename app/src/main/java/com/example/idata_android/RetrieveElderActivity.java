package com.example.idata_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idata_android.Model.Elder;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveElderActivity extends AppCompatActivity {


    Button previous_bttn;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    OkHttpClient httpClient;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_crud_retrive_recyclerview);
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        String url = bundle.getString("finalUrl");
        recyclerView = findViewById(R.id.recyclerview__retrieveElderActivity);
        previous_bttn = findViewById(R.id.previous_retrieveElderActivity);

        setHttpClient(url);


        previous_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void setHttpClient(String url){
        Log.i(TAG, "url:"+url);
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
                    String jsonData = response.body().string();

                    Elder[] elderList =  new Gson().fromJson(jsonData, Elder[].class);

                    mAdapter = new RecyclerViewAdapter_retrieveElder(elderList);

                    //Must call UI thread to change layouts and views
                    RetrieveElderActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "runOnUiThread");
                            recyclerView.setAdapter(mAdapter);
                            layoutManager = new LinearLayoutManager(RetrieveElderActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(layoutManager);
                        }
                    });
                }
            }
        });
    }

    static String TAG ="RetrivalElderActivity";
}
