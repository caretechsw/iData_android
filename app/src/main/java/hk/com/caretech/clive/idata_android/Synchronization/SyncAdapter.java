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

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hk.com.caretech.clive.idata_android.Model.Elder;
import hk.com.caretech.clive.idata_android.Model.Temperature;
import hk.com.caretech.clive.idata_android.SQLiteDBHelper;
import hk.com.caretech.clive.idata_android.TemperatureModel_Local;
import hk.com.caretech.clive.idata_android.Utils.ServerUtils;
import hk.com.caretech.clive.idata_android.Utils.SyncStatus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
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


//    public void init(Context context){
//        Cursor c = sqldb.getData();
//        sqldb = new SQLiteDBHelper(context);
//        httpClient = new OkHttpClient();
//    }



    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        //*****************Connecting to a server

        sqldb = new SQLiteDBHelper(context);
        httpClient = new OkHttpClient();


        Cursor cursor = sqldb.getTemp();
        getElderDBFromServer();
        getTempDBFromServer();

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

        cursor.close();
        closeDB();
//        Looper.prepare();
//        Toast.makeText(context, "同步完成" , Toast.LENGTH_SHORT).show();
//        Looper.loop();
    }

    public void closeDB(){
        try {
            Thread.sleep(500);//prevent the database closing too quickly
            sqldb.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void getElderDBFromServer() {
        final Request request = new Request.Builder().url(ServerUtils.retrieveElderUrl()).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    Elder[] elderDataList = new Gson().fromJson(jsonData, Elder[].class);
                    for(Elder t : elderDataList){
                        sqldb.addEldertoLocal(t.getId(), t.getName(), t.getBed_no());
                    }
                    Log.i(TAG, "getElderDBFromServer finished");
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }

    public void getTempDBFromServer(){
       // Log.i(TAG, "url:"+url);
        //httpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(ServerUtils.retrieveTempUrl()).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    Temperature[] dataList = new Gson().fromJson(jsonData, Temperature[].class);
                    //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    for(Temperature t : dataList){
                        String dev_timestamp =  t.getDev_timestamp();
                        long unix_timestamp = Long.parseLong(dev_timestamp.substring(dev_timestamp.indexOf('_')+1));
                           // long unixTime = df.parse(df.format(t.getTimestamp())).getTime();
                            sqldb.addTemptoLocal(t.getElder_id(), t.getTemperature(), t.getDevice_id(),
                                    unix_timestamp, SyncStatus.SYNCHONISED);
                            Log.i(TAG, "unix_timestamp : " + unix_timestamp);

                    }
                    Log.i(TAG, "getTempDBFromServer finished");

                }
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                System.out.print("failed");
            }
            });
    }


    /*
     * this method is uploading all the data to server
     * */
    private void uploadAllDataToServer(Cursor cursor) {
        //SimpleDateFormat primarykeyDF = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat fullDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (cursor.moveToFirst()) {
            do {
                int elder_id = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_ELDER_ID));
                String temperature = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_TEMP));
                String timestamp = fullDF.format(cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_TIMESTAMP)));
                Long timestamp_primaryKey = cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_TIMESTAMP));
                String device_id = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_DEVICE_ID));
                int status = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_STATUS));

                String dev_timestamp = device_id + "_" + timestamp_primaryKey.toString(); //surrogate key

                Log.i(TAG, "cursor :" + cursor.getPosition());
                Log.i(TAG, "timestamp :" + timestamp);
                Log.i(TAG, "device_id :" + device_id);
                Log.i(TAG, "status :" + status);
                Log.i(TAG, "dev_timestamp :" + dev_timestamp);

                Cursor cursor2 = sqldb.getElderById(elder_id);

                RequestBody formBody = new FormBody.Builder()
                        .add("dev_timestamp", dev_timestamp)
                        .add("elder_id", Integer.toString(elder_id))
                        .add("temperature", temperature)
                        .add("timestamp", timestamp)
                        .add("device_id", device_id)
                        .build();// dynamically add more parameter like this:

                if (cursor2.getCount()>0) {
                    // if (status == SyncStatus.UNSYNCHONISED) {
                    Request request = new Request.Builder()
                            .url(ServerUtils.addTempUrl())
                            .post(formBody)
                            .build();

                    Call call = httpClient.newCall(request);
                    call.enqueue(new Callback() {

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                if (response.code() == 200 || response.code() == 201) {
                                    Log.i(TAG, "added to temp table");

                                    //Note! Even the foreign key -elder_id is not exist in a database, the status
                                    //won't update but the the status code will be still fallen in 2xx
                                    sqldb.updateDataStatusToSync(device_id, timestamp_primaryKey);
                                }
                                Log.i(TAG, "Status code :" + response.code());
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                            Log.i(TAG, "onFailure :");
                        }
                    });
                }
                else {
                    Request request = new Request.Builder()
                            //add data to temperature_adnormal table
                            .url(ServerUtils.addTempUrl_abnormal())
                            .post(formBody)
                            .build();

                    Call call = httpClient.newCall(request);
                    call.enqueue(new Callback() {

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                if (response.code() == 200 || response.code() == 201) {
                                    //Note! Even the foreign key -elder_id is not exist in a database, the status
                                    //won't update but the the status code will be still fallen in 2xx
                                    sqldb.updateDataStatusToSync(device_id, timestamp_primaryKey);
                                    Log.i(TAG, "added to temp_abnormal table");
                                }
                                Log.i(TAG, "Status code :" + response.code());
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                            Log.i(TAG, "onFailure :");
                        }
                    });
                }
            }
                while (cursor.moveToNext()) ;
        }
}

    static String TAG = SyncAdapter.class.getName();
}
