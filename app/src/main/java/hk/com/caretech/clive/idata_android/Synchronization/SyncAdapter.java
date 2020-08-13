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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hk.com.caretech.clive.idata_android.SQLiteDBHelper;
import hk.com.caretech.clive.idata_android.TemperatureModel_Local;
import hk.com.caretech.clive.idata_android.Utils.NetworkUtils;
import hk.com.caretech.clive.idata_android.Utils.SyncStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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


    public void init(Context context){
        Cursor c = sqldb.getData();
        sqldb = new SQLiteDBHelper(context);
        httpClient = new OkHttpClient();
    }



    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        //*****************Connecting to a server

        sqldb = new SQLiteDBHelper(context);
        httpClient = new OkHttpClient();

        Cursor cursor = getlocalData();

        uploadAllDataToServer(cursor);
        //Downloading and uploading data -requests the data, downloads , inserts it in the provider.

        //Handling data conflicts or determining how current the data is
        // Clean up. -close connections

//        final Request request = new Request.Builder().url(ServerUtils.retrieveElderUrl).build();
//
//        httpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                e.printStackTrace();
//                System.out.print("failed");
//            }
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonData = response.body().string();
//
//                    Elder[] elderList =  new Gson().fromJson(jsonData, Elder[].class);
//
//                }
//            }
//        });

        sqldb.close();
    }

    public Cursor getlocalData(){
        return sqldb.getData();
    }

    /*
     * this method is uploading all the data to server
     * */
    private void uploadAllDataToServer(Cursor cursor) {


        OkHttpClient client = new OkHttpClient();
        SimpleDateFormat primarykeyDF = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat fullDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (cursor.moveToFirst()) {
            do {
                String elder_id = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_ELDER_ID));
                String temperature = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TEMP));
                String timestamp = fullDF.format(cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TIMESTAMP)));
                String timestamp_primaryKey = primarykeyDF.format(cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TIMESTAMP)));
                String device_id = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_DEVICE_ID));
                int status = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_STATUS));

                Log.i(TAG, "cursor :" + cursor.getPosition());
                Log.i(TAG, "timestamp :" + timestamp);
                Log.i(TAG, "device_id :" + device_id);
                Log.i(TAG, "status :" + status);

                String dev_timestamp = device_id + "_" + timestamp_primaryKey; //surrogate key
                Log.i(TAG, "dev_timestamp :" + dev_timestamp);

                if (status == SyncStatus.UNSYNCHONISED) {

                    RequestBody formBody = new FormBody.Builder()
                            .add("dev_timestamp", dev_timestamp)
                            .add("elder_id", elder_id)
                            .add("temperature", temperature)
                            .add("timestamp", timestamp)
                            .build();// dynamically add more parameter like this:

                    Request request = new Request.Builder()
                            .url(NetworkUtils.addTempUrl("192.168.1.20"))
                            .post(formBody)
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                if (response.code() == 200 || response.code() == 201) {
                                    sqldb.updateDataStatusToSync(device_id, timestamp);
                                }
                                Log.i(TAG, "Status code :" + response.code());
                                //  }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
                while (cursor.moveToNext()) ;
        }
}

    static String TAG = SyncAdapter.class.getName();
}
