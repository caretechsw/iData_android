package hk.com.caretech.clive.idata_android.Server;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

import hk.com.caretech.clive.idata_android.Model.Elder;
import hk.com.caretech.clive.idata_android.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveElderActivity extends AppCompatActivity {


    private Button previous_bttn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private OkHttpClient httpClient;
    private String baseUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elder_table_serverdata_recyclerview);
        Bundle bundle;
        bundle = getIntent().getExtras();
        String adapterUrl = bundle.getString("finalUrl");
        baseUrl = bundle.getString("baseUrl");
        Log.i(TAG, "baseUrl: " + baseUrl);

        recyclerView = findViewById(R.id.recyclerview_elder_table_serverdata_recyclerview);
        previous_bttn = findViewById(R.id.previous_elder_table_serverdata_recyclerview);

        setHttpClient(adapterUrl);

        previous_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setHttpClient(String adapterUrl){
        Log.i(TAG, "url:"+adapterUrl);
        httpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(adapterUrl).build();

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

                    mAdapter = new RecyclerviewAdapter_retrieveElder(elderList, baseUrl);

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

    static String TAG = RetrieveElderActivity.class.getName();
}
