package hk.com.caretech.clive.idata_android;

import android.content.BroadcastReceiver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hk.com.caretech.clive.idata_android.Model.Elder;
import hk.com.caretech.clive.idata_android.Utils.SyncStatus;

public class RetrieveLocalTemperatureActivity extends AppCompatActivity {


    private Button previous_bttn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TemperatureModel_Local> tempList = new ArrayList<>();
    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;
    //database helper object
    private SQLiteDBHelper sqlDb;
    private TextView textView_countUnsyncData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temptable_local_recyclerview);
        sqlDb = new SQLiteDBHelper(this);

        recyclerView = findViewById(R.id.recyclerview_temptable_local_recyclerview);
        previous_bttn = findViewById(R.id.previous_temptable_local_recyclerview);
        textView_countUnsyncData =findViewById(R.id.textView_countUnsyncData_temptable_local_recyclerview);

        loadLocalData();

        previous_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void loadLocalData() {
        int countUnsyncStatus=0;
        tempList.clear();
        Cursor tempCursor = sqlDb.getTemp();
        if (tempCursor.moveToFirst()) {
            do {
                TemperatureModel_Local temp = new TemperatureModel_Local(
                        tempCursor.getInt(tempCursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_ELDER_ID)),
                        tempCursor.getFloat(tempCursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_TEMP)),
                        tempCursor.getString(tempCursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_DEVICE_ID)),
                        tempCursor.getLong(tempCursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_TIMESTAMP)),
                        tempCursor.getInt(tempCursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_STATUS)));
                tempList.add(temp);

                if (tempCursor.getInt(tempCursor.getColumnIndex(SQLiteDBHelper.TEMP_COLUMN_STATUS)) ==
                        SyncStatus.UNSYNCHONISED) {
                    countUnsyncStatus++;
                }
            } while (tempCursor.moveToNext());
        }


        textView_countUnsyncData.setText("未上載資料: "+countUnsyncStatus);
        initAdapter();
    }

    public void initAdapter(){
        mAdapter = new RecyclerviewAdapter_retrieveLocalTemperature(tempList);
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(RetrieveLocalTemperatureActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    static String TAG = RetrieveLocalTemperatureActivity.class.getName();
}
