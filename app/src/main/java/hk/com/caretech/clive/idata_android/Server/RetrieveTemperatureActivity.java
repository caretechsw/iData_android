package hk.com.caretech.clive.idata_android.Server;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import hk.com.caretech.clive.idata_android.Model.Temperature;
import hk.com.caretech.clive.idata_android.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveTemperatureActivity extends AppCompatActivity {


    private Button previous_bttn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private OkHttpClient httpClient;
    private TextView textView_elderID;
    private TextView textView_elderName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temptable_serverdata_recyclerview);
        Bundle bundle;
        bundle = getIntent().getExtras();
        String url = bundle.getString("tempUrl");
        String elder_id = bundle.getString("elder_id");
        String elder_name = bundle.getString("elder_name");

        recyclerView = findViewById(R.id.recyclerview_temp_table_server_recyclerview);
        previous_bttn = findViewById(R.id.previous_temp_table_server_recyclerview);
        textView_elderID = findViewById(R.id.textView_showElderID_temp_table_server_recyclerview);
        textView_elderName = findViewById(R.id.textView_showElderName_temp_table_server_recyclerview);
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

                    mAdapter = new RecyclerviewAdapter_retrieveTemperature(tempList);

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

    static String TAG = RetrieveTemperatureActivity.class.getName();
}
