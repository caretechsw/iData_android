package hk.com.caretech.clive.idata_android;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.idatachina.imeasuresdk.IMeasureSDK;

import hk.com.caretech.clive.idata_android.cewen.IMeasure_Activity;


public class MainActivity extends AppCompatActivity {

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: "+keyCode);
        if ( keyCode == 600 || keyCode == 601 || keyCode == 602) {
           // if (notReading==readStatus){
              //  readStatus = reading;
                mIMeasureSDK.read(new IMeasureSDK.TemperatureCallback() {
                    @Override
                    public void success(final double temp) {
                        //readStatus = notReading;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               // Toast.makeText(IMeasure_Activity.this,"Temperature："+temp,Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "success: "+temp);
                            }
                        });
                    }

                    @Override
                    public void failed(int code, final  String msg) {
                      //  readStatus = notReading;
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





//        @Override
//        public boolean onCreateOptionsMenu (Menu menu){
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.menu_main, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected (MenuItem item){
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//
//            //noinspection SimplifiableIfStatement
//            if (id == R.id.action_settings) {
//                return true;
//            }
//
//            return super.onOptionsItemSelected(item);
//        }






    static String TAG = "MainActivity";
}