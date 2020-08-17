//package hk.com.caretech.clive.idata_android;
//
//import android.content.BroadcastReceiver;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import hk.com.caretech.clive.idata_android.Model.Elder;
//
//public class RetrieveLocalElderActivity extends AppCompatActivity {
//
//
//    private Button previous_bttn;
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private List<Elder> elderDataList = new ArrayList<>();
//    //database helper object
//    private SQLiteDBHelper sqlDb;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.eldertable_local_recyclerview);
//        sqlDb = new SQLiteDBHelper(this);
//
//        recyclerView = findViewById(R.id.recyclerview_eldertable_local_recyclerview);
//        previous_bttn = findViewById(R.id.previous_eldertable_local_recyclerview);
//
//        loadLocalElder();
//
//        previous_bttn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//    }
//
//    public void loadLocalElder() {
//        elderDataList.clear();
//        Cursor cursor = sqlDb.getElder();
//        if (cursor.moveToFirst()) {
//            do {
//                Elder elder = new Elder(
//                        cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.ELDER_COLUMN_ID)),
//                        cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.ELDER_COLUMN_NAME)),
//                        cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.ELDER_COLUMN_BED_NO)));
//                        elderDataList.add(elder);
//
//            } while (cursor.moveToNext());
//        }
//        initAdapter();
//    }
//
//    public void initAdapter(){
//        mAdapter = new RecyclerviewAdapter_retrieveLocalElder(elderDataList);
//        recyclerView.setAdapter(mAdapter);
//        layoutManager = new LinearLayoutManager(RetrieveLocalElderActivity.this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);
//    }
//
//
//    static String TAG = RetrieveLocalElderActivity.class.getName();
//}
