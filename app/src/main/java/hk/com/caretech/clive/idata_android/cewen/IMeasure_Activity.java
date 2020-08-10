package hk.com.caretech.clive.idata_android.cewen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.idatachina.imeasuresdk.IMeasureSDK;

import hk.com.caretech.clive.idata_android.R;

public class IMeasure_Activity extends AppCompatActivity {

    IMeasureSDK mIMeasureSDK;

    private Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imeasure);

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

    private final String TAG="cewen";

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
            if (notReading==readStatus){
                readStatus = reading;
                mIMeasureSDK.read(new IMeasureSDK.TemperatureCallback() {
                    @Override
                    public void success(final double temp) {
                        readStatus = notReading;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(IMeasure_Activity.this,"Temperature："+temp,Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "success: "+temp);
                            }
                        });
                    }

                    @Override
                    public void failed(int code, final  String msg) {
                        readStatus = notReading;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "Failed[" + msg + "]", Toast.LENGTH_SHORT).show(); //Temperature measurement failed
                                Log.d(TAG, "run: Failed,"+msg);
                            }
                        });

                    }
                });
            }else{
                Toast.makeText(this,"Reading,please wait...",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        if (scanResult != null) {
            String result = scanResult.getContents();
            Log.d(TAG, "onActivityResult: "+result);
            Toast.makeText(IMeasure_Activity.this,"Successful decoding："+result,Toast.LENGTH_LONG).show();
        }
    }
}
