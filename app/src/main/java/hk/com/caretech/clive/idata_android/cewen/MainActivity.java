package hk.com.caretech.clive.idata_android.cewen;

import android.content.Intent;
import android.database.Cursor;
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

import hk.com.caretech.clive.idata_android.R;
import hk.com.caretech.clive.idata_android.RetrieveLocalTemperatureActivity;
import hk.com.caretech.clive.idata_android.SQLiteDBHelper;
import hk.com.caretech.clive.idata_android.Server.ServerDataActivity;
import hk.com.caretech.clive.idata_android.Synchronization.SyncUtils;
import hk.com.caretech.clive.idata_android.Utils.SyncStatus;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    IMeasureSDK mIMeasureSDK;

    private Button scanButton;

    private SQLiteDBHelper sqlDb;
    private Intent intent;
    private String android_id;
    private EditText editText_inputElderId_main;
    private Button bttn_reentry_main;
    private String inputElderId;
    //private List<TemperatureModel_Local> dataList = new ArrayList<>();
    OkHttpClient httpClient;
    Boolean isIdExist = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sqlDb = new SQLiteDBHelper(this);


        editText_inputElderId_main = findViewById(R.id.editText_inputElderId_main);
        bttn_reentry_main = findViewById(R.id.bttn_reentry_main);
        bttn_reentry_main.setOnClickListener(new View.OnClickListener() {
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

                                        sqlDb.addTemptoLocal(elder_id_int, temp, android_id, System.currentTimeMillis(), SyncStatus.UNSYNCHONISED);

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
                            Toast.makeText(this, "讀取中，請等侯...", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(this, "Reading,please wait...", Toast.LENGTH_SHORT).show();
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



    //This method will check the id if exists over the elder and temp tables
    public boolean elderIdExists(int inputElderId){
        sqlDb = new SQLiteDBHelper(this);
        Cursor cursor_temp = sqlDb.getTempById(inputElderId);
        Cursor cursor_elder = sqlDb.getElderById(inputElderId);
        Log.i(TAG, "cursor_temp size"+cursor_temp.getCount()+ "cursor_elder size"+cursor_elder.getCount());
        if(cursor_temp.getCount() > 0 || cursor_elder.getCount()>0){return true;
        }
//        if (cursor.moveToFirst()) {
//            do {
//                int elder_id = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_ELDER_ID));
//                if(inputElderId==elder_id)
//            } while (cursor.moveToNext());
//        }


//        HttpUrl.Builder httpBuilder = HttpUrl.parse( NetworkUtils.retrieveIdUrl() ).newBuilder();
//
//        HttpUrl finalUrl = httpBuilder.addQueryParameter("id", inputElderId).build();
//
//        final Request request = new Request.Builder().url(finalUrl).build();
//        Log.i(TAG, " finalUrl : "+ finalUrl);
//        httpClient = new OkHttpClient();
//        httpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if(response.isSuccessful()) {
//                    if (response.code() == 200 || response.code() == 201) {
//                        isIdExist = true;
//                        Log.i(TAG, "isIdExist : " + isIdExist);
//                    }Log.i(TAG, "response.code() : " + response.code());
//                }
//
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e){
//                isIdExist = false;
//            }
//        });
        Log.i(TAG, "isIdExist (at return: " + isIdExist);
        sqlDb.close();
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        sqlDb.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIMeasureSDK.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null ) {
            inputElderId = scanResult.getContents();
            if (inputElderId != null && !inputElderId.equals("")) {
                if (elderIdExists(Integer.parseInt(inputElderId))) {
                    editText_inputElderId_main.setText(inputElderId);
                    Log.d(TAG, "onActivityResult: "+inputElderId);
                    Toast.makeText(MainActivity.this,"ID："+inputElderId,Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this,"Successful decoding："+inputElderId,Toast.LENGTH_LONG).show();
                }else {

                    //show dialog to choose yes/no here
                    Toast.makeText(MainActivity.this,"ID不存在",Toast.LENGTH_LONG).show();

                    //if no
                editText_inputElderId_main.setText("");}
            }

//            HttpUrl.Builder httpBuilder = HttpUrl
//                    .parse(NetworkUtils.retrieveIdUrl() ).newBuilder();
//            HttpUrl finalUrl = httpBuilder.addQueryParameter("id", inputElderId).build();
//
//            final Request request = new Request.Builder().url(finalUrl).build();
//            Log.i(TAG, " finalUrl : "+ finalUrl);
//            httpClient = new OkHttpClient();
//            httpClient.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                    if(response.isSuccessful()) {
//                        if (response.code() == 200 || response.code() == 201) {
//                            editText_inputElderId_main.setText(inputElderId);
////                            isIdExist = true;
////                            Log.i(TAG, "isIdExist : " + isIdExist);
//                        }Log.i(TAG, "response.code() : " + response.code());
//                    }
//                }
//                @Override
//                public void onFailure(@NotNull Call call, @NotNull IOException e){
//                    //isIdExist = false;
//                }
//            });

           // Log.d(TAG, "isIdExist: "+isIdExist);

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
                SyncUtils.CreateSyncAccount(MainActivity.this); //place this into oncreate method if would like to do auto-sync when start the app
                SyncUtils.forceRefreshAll(MainActivity.this);
                return true;
            case R.id.action_setting:
                //??

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static String TAG = MainActivity.class.getName();
}
