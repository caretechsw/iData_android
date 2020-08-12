package hk.com.caretech.clive.idata_android.Synchronization;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.idatachina.imeasuresdk.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hk.com.caretech.clive.idata_android.Model.Elder;
import hk.com.caretech.clive.idata_android.SQLiteDBHelper;
import hk.com.caretech.clive.idata_android.TemperatureModel_Local;
import hk.com.caretech.clive.idata_android.Utils.ServerUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private ContentResolver contentResolver;
    private OkHttpClient httpClient;
    private SQLiteDBHelper sqldb;
    private List<TemperatureModel_Local> localDataList = new ArrayList<>();
    private Context context;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
        this.context = context;
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        contentResolver = context.getContentResolver();
    }



    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        //*****************Connecting to a server

        //Downloading and uploading data -requests the data, downloads , inserts it in the provider.

        Cursor c = sqldb.getData();

        sqldb = new SQLiteDBHelper(context);


        //Handling data conflicts or determining how current the data is
        // Clean up. -close connections



        httpClient = new OkHttpClient();



        final Request request = new Request.Builder().url(ServerUtils.addElderUrl).build();

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

                }
            }
        });
    }

    static String TAG = SyncAdapter.class.getName();
}
