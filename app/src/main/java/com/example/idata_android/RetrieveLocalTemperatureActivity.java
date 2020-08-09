package com.example.idata_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idata_android.Model.Temperature;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveLocalTemperatureActivity extends AppCompatActivity {


    Button previous_bttn;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Temperature> tempList = new ArrayList<>();
    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;
    //database helper object
    private SQLiteDBHelper sqlDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_table_local_recyclerview);
        sqlDb = new SQLiteDBHelper(this);
//        Bundle bundle;
//        bundle = getIntent().getExtras();
//        String url = bundle.getString("tempUrl");

//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                loadTemp();
//            }
//        };

        recyclerView = findViewById(R.id.recyclerview_temp_table_local_recyclerview);
        previous_bttn = findViewById(R.id.previous_temp_table_local_recyclerview);
        loadTemp();

        previous_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void loadTemp() {
        tempList.clear();
        Cursor cursor = sqlDb.getTemps();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (cursor.moveToFirst()) {
            do {
                Temperature temp = new Temperature(
                        cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TEMP_ID_LOCAL)),
                        cursor.getDouble(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TEMP)),
                        cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_ELDER_ID)),
                        Timestamp.valueOf(df.format(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TIMESTAMP))));
                tempList.add(temp);
            } while (cursor.moveToNext());
        }
        initAdapter();
    }

    public void initAdapter(){
        mAdapter = new RecyclerviewAdapter_retrieveLocalTemperature(tempList);
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(RetrieveLocalTemperatureActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    static String TAG ="RetrieveLocalTemperatureActivity";
}
