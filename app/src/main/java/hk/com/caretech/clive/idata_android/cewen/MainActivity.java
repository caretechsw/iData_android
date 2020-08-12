package hk.com.caretech.clive.idata_android.cewen;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.idatachina.imeasuresdk.IMeasureSDK;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hk.com.caretech.clive.idata_android.R;
import hk.com.caretech.clive.idata_android.RetrieveLocalTemperatureActivity;
import hk.com.caretech.clive.idata_android.SQLiteDBHelper;
import hk.com.caretech.clive.idata_android.Server.ServerDataActivity;
import hk.com.caretech.clive.idata_android.Synchronization.SyncUtils;
import hk.com.caretech.clive.idata_android.TemperatureModel_Local;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    IMeasureSDK mIMeasureSDK;

    private Button scanButton;

    private SQLiteDBHelper sqlDb;
    private Intent intent;
    private static String addToServerUrl = "http://192.168.1.208/temp/add";
    private String android_id;
    private EditText editText_inputElderId_main;
    private Button bttn_Logout_main;
    private String inputElderId;
    private List<TemperatureModel_Local> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SyncUtils.CreateSyncAccount(this);

        editText_inputElderId_main = findViewById(R.id.editText_inputElderId_main);
        bttn_Logout_main = findViewById(R.id.bttn_Logout_main);
        bttn_Logout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {logout();}
        });

        scanButton = (Button)findViewById(R.id.scanButton) ;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

        //Please pay attention to the life cycle of the incoming Context. At the end of the life cycle of the Context, please call mIMeasureSDK.close ()
        mIMeasureSDK = new IMeasureSDK(getBaseContext());
        mIMeasureSDK.init(initCallback);

        android_id = Settings.System.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (android_id != null) {
            Log.i(TAG, "android_id : "+android_id);
        }

        editText_inputElderId_main.requestFocus();
    }



//    public void showImageSlideDialog(itemList: ItemInfo_Firebase_Model) {
//        val mProductImageSlideFragment = ProductImageSlideDialogFragment(itemList)
//        mProductImageSlideFragment.show(
//                (view.context as CategoryActivity).getSupportFragmentManager(),
//                ""
//                )
//    }

    public void logout(){
        editText_inputElderId_main.setText(null);
        inputElderId = null;
    }

    private IMeasureSDK.InitCallback initCallback = new IMeasureSDK.InitCallback() {
        @Override
        public void success() {
            Log.d(TAG, "success:Power on successfully");
            //Toast.makeText(getBaseContext(), "Power on successfully", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failed(int code, String msg) {
            Log.d(TAG, "failed: Power on failed,"+msg);
            //Toast.makeText(getBaseContext(), "Power on failed[" + msg + "]", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void disconnect() {
            //Toast.makeText(getBaseContext(), "Service disconnect", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "disconnect:Service disconnect");
            mIMeasureSDK.reconect();
        }
    };


    public void scan() {
        IntentIntegrator integrator = new IntentIntegrator(this); 
        //to set the barcode type to be scanned. ONE_D_CODE_TYPES: 1d barcode, QR_CODE_TYPES:2D barcode
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setCaptureActivity(ScanActivity.class); //set to enable the Activity of camera
        integrator.setPrompt("Please scan the barcode"); //Tip text at the bottom, set to "" can be blank
        integrator.setCameraId(0); //Front camera or rear camera
        integrator.setBeepEnabled(true); //"Beep" sound after successful scanning. Enabled by default.
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    //Temperature measurement status
    private volatile int readStatus = notReading;
    private final static  int reading = 1;
    private final static  int notReading = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: "+keyCode);
        if ( keyCode == 600 || keyCode == 601 || keyCode == 602) {
            inputElderId = editText_inputElderId_main.getText().toString();
            if(!TextUtils.isEmpty(inputElderId)) {
                if( keyCode == 600) {
                        if (notReading == readStatus) {
                        readStatus = reading;
                        mIMeasureSDK.read(new IMeasureSDK.TemperatureCallback() {
                            @Override
                            public void success(final double temp) {
                                readStatus = notReading;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        int elder_id_int = Integer.valueOf(inputElderId);

                                        saveDataToLocalStorage(elder_id_int, temp, android_id, 0);

                                        Toast.makeText(MainActivity.this, "Temperature：" + temp, Toast.LENGTH_SHORT).show();

//                                temp = edTemp.getText().toString();
//                                if (!TextUtils.isEmpty(elderID) && !TextUtils.isEmpty(temp)) {
//                                    saveTempToLocalStorage(Double.parseDouble(temp), Integer.parseInt(elderID), android_id, 0);

                                        Log.d(TAG, "success: " + temp);
                                    }
                                });
                            }

                            @Override
                            public void failed(int code, final String msg) {
                                readStatus = notReading;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Failed[" + msg + "]", Toast.LENGTH_SHORT).show(); //Temperature measurement failed
                                        Log.d(TAG, "run: Failed," + msg);
                                    }
                                });

                            }
                        });
                    }else {
                            Toast.makeText(this, "Reading,please wait...", Toast.LENGTH_SHORT).show();
                        }
                }else if(keyCode == 601 || keyCode == 602){
                    scan();
                }
            }
            else{scan();}
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIMeasureSDK.close();
        sqlDb.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            inputElderId = scanResult.getContents();
            editText_inputElderId_main.setText(inputElderId);
            Log.d(TAG, "onActivityResult: "+inputElderId);
            Toast.makeText(MainActivity.this,"Successful decoding："+inputElderId,Toast.LENGTH_LONG).show();
        }
    }

    public void loadLocalData() {
        dataList.clear();
        Cursor cursor = sqlDb.getData();
        if (cursor.moveToFirst()) {
            do {
                TemperatureModel_Local temp = new TemperatureModel_Local(
                        cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_ELDER_ID)),
                        cursor.getDouble(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TEMP)),
                        cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_DEVICE_ID)),
                        cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TIMESTAMP)),
                        cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_STATUS)));
                dataList.add(temp);
            } while (cursor.moveToNext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_readLocalData:
                intent = new Intent(this, RetrieveLocalTemperatureActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_readServerData:
                intent = new Intent(this, ServerDataActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_DataSync:
                //make handler to prevent user from clicking frequently
                //showing the last time synchonised with server
                //check connection
                SyncUtils.forceRefreshAll(this);
                return true;
            case R.id.action_setting:
                //??

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


/********************* storing features related methods***************************

    //
    /**saving Temperature to local storage
     *
     * @param temp
     * @param elder_id
     * @param device_id
     * @param status
     * The status has two posible values:
     *      * 1 means the name is synced with the server
     *      * 0 means the name is not synced with the server
     *      * as normal, 0 should be stored first
     */
    private void saveDataToLocalStorage(int elder_id,double temp, String device_id, int status) {
        sqlDb = new SQLiteDBHelper(this);
        sqlDb.addData(elder_id,temp, device_id, status);
//        Name n = new Name(name, status);
//        names.add(n);
//        refreshList();
    }




        /*
         * this method is saving the name to ther server
         * */
        private void saveDataToServer() {
      //      loadLocalData();
//            OkHttpHandler okHttpHandler = new OkHttpHandler();
//            if(dataList.size()>0){
//            okHttpHandler.execute(addToServerUrl);}
        }


//    public class OkHttpHandler extends AsyncTask {
//        OkHttpClient client = new OkHttpClient();
//        String temperature;
//        String elder_id;
//        String device_id;
//        String timestamp;
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//
//            FormBody.Builder formBody = new FormBody();
//
//            for(ArrayList<TemperatureModel_Local> d : dataList){
//                RequestBody formBody = new FormBody.Builder()
//                        .add("temperature", temperature)
//                        .add("elder_id", elder_id)
//                        .add("device_id", device_id)
//                        .add("timestamp", timestamp)
//                        .build();// dynamically add more parameter like this:
//                 }
//
//                Request request = new Request.Builder()
//                        .url(objects[0].toString())
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
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            //sqlDb.updateDataStatus()
//        }
//    }


    static String TAG = MainActivity.class.getName();
}
