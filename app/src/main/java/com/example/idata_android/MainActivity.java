package com.example.idata_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.idata_android.Model.Temperature;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    //url to be call for adding data to server
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

    }

    /*
     * this method is saving the name to ther server
     * */
    private void saveTempToServer() {
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class OkHttpHandler extends AsyncTask {
        OkHttpClient client = new OkHttpClient();
        @Override
        protected Object doInBackground(Object[] objects) {

//                String url_elder = "http://192.168.1.20:7070/temp";
//                RequestBody formBody = new FormBody.Builder()
//                        .add("temperature", temperature)
//                        .add("elder_id", elder_id).build();// dynamically add more parameter like this:
//
//                Request request = new Request.Builder()
//                        .url(url_elder + "/add")
//                        .post(formBody)
//                        .build();
//
//                Call call = client.newCall(request);
//
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                    }
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                        Toast.makeText(MainActivity.this, "Data synchonized", Toast.LENGTH_SHORT).show();
//                    }
//                });
            return null;
        }
    }
}