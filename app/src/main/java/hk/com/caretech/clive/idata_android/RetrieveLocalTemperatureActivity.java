package hk.com.caretech.clive.idata_android;

import android.content.BroadcastReceiver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RetrieveLocalTemperatureActivity extends AppCompatActivity {


    private Button previous_bttn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<TemperatureModel_Local> tempList = new ArrayList<>();
    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;
    //database helper object
    private SQLiteDBHelper sqlDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temptable_local_recyclerview);
        sqlDb = new SQLiteDBHelper(this);
//        Bundle bundle;
//        bundle = getIntent().getExtras();
//        String url = bundle.getString("tempUrl");

//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                loadTemp();
//            }
//        };

        recyclerView = findViewById(R.id.recyclerview_temp_table_local_recyclerview);
        previous_bttn = findViewById(R.id.previous_temp_table_local_recyclerview);
        loadLocalData();

        previous_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void loadLocalData() {
        tempList.clear();
        Cursor cursor = sqlDb.getTemps();
        if (cursor.moveToFirst()) {
            do {
                TemperatureModel_Local temp = new TemperatureModel_Local(
                        cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_ELDER_ID)),
                        cursor.getDouble(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TEMP)),
                        cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_DEVICE_ID)),
                        cursor.getLong(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_TIMESTAMP)),
                        cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_STATUS)));

                tempList.add(temp);
            } while (cursor.moveToNext());
        }
        initAdapter();
    }

    public void initAdapter(){
        mAdapter = new RecyclerviewAdapter_retrieveLocalTemperature(tempList);
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(RetrieveLocalTemperatureActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    static String TAG = "RetrieveLocalTemperatureActivity";
}
