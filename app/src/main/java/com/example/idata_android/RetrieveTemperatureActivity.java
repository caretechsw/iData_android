package com.example.idata_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idata_android.Model.Elder;
import com.example.idata_android.Model.Temperature;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveTemperatureActivity extends AppCompatActivity {


    Button previous_bttn;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    OkHttpClient httpClient;
    TextView textView_elderID;
    TextView textView_elderName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_table_recyclerview);
        Bundle bundle;
        bundle = getIntent().getExtras();
        String url = bundle.getString("tempUrl");
        String elder_id = bundle.getString("elder_id");
        String elder_name = bundle.getString("elder_name");

        recyclerView = findViewById(R.id.recyclerview_retrieveTemperatureActivity);
        previous_bttn = findViewById(R.id.previous_retrieveTemperatureActivity);
        textView_elderID = findViewById(R.id.textView_retrieveTemperatureActivity_showElderID);
        textView_elderName = findViewById(R.id.textView_retrieveTemperatureActivity_showElderName);
        textView_elderID.setText("Elder ID: "+ elder_id);
        textView_elderName.setText("Name: "+ elder_name);

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

                    Temperature[] tempList =  new Gson().fromJson(jsonData, Temperature[].class);

                    mAdapter = new RecyclerViewAdapter_retrieveTemperature(tempList);

                    //Must call UI thread to change layouts and views
                    RetrieveTemperatureActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "runOnUiThread");
                            recyclerView.setAdapter(mAdapter);
                            layoutManager = new LinearLayoutManager(RetrieveTemperatureActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(layoutManager);
                        }
                    });
                }
            }
        });
    }

    static String TAG ="RetrieveTemperatureActivity";
}
