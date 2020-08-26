package hk.com.caretech.clive.idata_android;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.com.caretech.clive.idata_android.Model.Elder;
import hk.com.caretech.clive.idata_android.Utils.SyncStatus;

public class TempDetailsDialogFragment extends DialogFragment {

    private TextView textView2_id_dialogfragment;
    private TextView textView2_name_dialogfragment;
    private TextView textView2_bedno_dialogfragment;
    private TextView textView2_deviceID_dialogfragment;
    private TextView textView2_temperature_dialogfragment;
    private TextView textView2_timestamp_dialogfragment;
    private TextView textView2_status_dialogfragment;

    private TextView textView3_id_dialogfragment;
    private TextView textView3_name_dialogfragment;
    private TextView textView3_bedno_dialogfragment;
    private TextView textView3_deviceID_dialogfragment;
    private TextView textView3_temperature_dialogfragment;
    private TextView textView3_timestamp_dialogfragment;
    private TextView textView3_status_dialogfragment;


    private Map<String, Object> map = new HashMap<>();
    private SQLiteDBHelper sqlDB;
    TemperatureModel_Local tempList;


    public TempDetailsDialogFragment(TemperatureModel_Local tempList) {
        this.tempList = tempList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dialogfragment_tempdetails, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView2_id_dialogfragment = view.findViewById(R.id.textView2_id_dialogfragment);
        textView2_name_dialogfragment = view.findViewById(R.id.textView2_name_dialogfragment);
        textView2_bedno_dialogfragment = view.findViewById(R.id.textView2_bedno_dialogfragment);
        textView2_deviceID_dialogfragment = view.findViewById(R.id.textView2_deviceID_dialogfragment);
        textView2_temperature_dialogfragment = view.findViewById(R.id.textView2_temperature_dialogfragment);
        textView2_timestamp_dialogfragment = view.findViewById(R.id.textView2_timestamp_dialogfragment);
        textView2_status_dialogfragment = view.findViewById(R.id.textView2_status_dialogfragment);

        textView3_id_dialogfragment = view.findViewById(R.id.textView3_id_dialogfragment);
        textView3_name_dialogfragment = view.findViewById(R.id.textView3_name_dialogfragment);
        textView3_bedno_dialogfragment = view.findViewById(R.id.textView3_bedno_dialogfragment);
        textView3_deviceID_dialogfragment = view.findViewById(R.id.textView3_deviceID_dialogfragment);
        textView3_temperature_dialogfragment = view.findViewById(R.id.textView3_temperature_dialogfragment);
        textView3_timestamp_dialogfragment = view.findViewById(R.id.textView3_timestamp_dialogfragment);
        textView3_status_dialogfragment = view.findViewById(R.id.textView3_status_dialogfragment);

        textView3_id_dialogfragment.setText(": ");
        textView3_name_dialogfragment.setText(": ");
        textView3_bedno_dialogfragment.setText(": ");
        textView3_deviceID_dialogfragment.setText(": ");
        textView3_temperature_dialogfragment.setText(": ");
        textView3_timestamp_dialogfragment.setText(": ");
        textView3_status_dialogfragment.setText(": ");

        sqlDB = new SQLiteDBHelper(view.getContext());

        Cursor c = sqlDB.getElderById(tempList.getElder_id());

        int elder_id = tempList.getElder_id();
        String device_id = tempList.getDevice_id();
        float temperature = tempList.getTemperature();
        long unixTimestamp = tempList.getTimestamp();
        int status = tempList.getStatus();

        SimpleDateFormat fullDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        textView2_id_dialogfragment.setText("" + elder_id);
        textView2_deviceID_dialogfragment.setText("" + device_id);
        textView2_temperature_dialogfragment.setText("" + temperature);
        textView2_timestamp_dialogfragment.setText(fullDF.format(unixTimestamp));

        if(status==SyncStatus.UNSYNCHONISED){
        textView2_status_dialogfragment.setText(" 未同步");}
        else if(status==SyncStatus.SYNCHONISED){
            textView2_status_dialogfragment.setText(" 已同步");
        }

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                textView2_name_dialogfragment.setText(c.getString(c.getColumnIndex(SQLiteDBHelper.ELDER_COLUMN_NAME)));
                textView2_bedno_dialogfragment.setText(c.getString(c.getColumnIndex(SQLiteDBHelper.ELDER_COLUMN_BED_NO)));
            } else {
                textView2_name_dialogfragment.setText("未有資料");
                textView2_bedno_dialogfragment.setText("未有資料");
            }
        }
    }
}

