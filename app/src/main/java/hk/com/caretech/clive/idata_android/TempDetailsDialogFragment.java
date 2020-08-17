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

import java.text.SimpleDateFormat;
import java.util.List;

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
    private TemperatureModel_Local tempList;
    private List<Elder> elderList;
    private SQLiteDBHelper sqlDB;


    public TempDetailsDialogFragment(TemperatureModel_Local tempList, List<Elder> elderList) {
        this.tempList = tempList;
        this.elderList = elderList;
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
        sqlDB = new SQLiteDBHelper(view.getContext());
        textView2_id_dialogfragment = view.findViewById(R.id.textView2_id_dialogfragment);
        textView2_name_dialogfragment = view.findViewById(R.id.textView2_name_dialogfragment);
        textView2_bedno_dialogfragment = view.findViewById(R.id.textView2_bedno_dialogfragment);
        textView2_deviceID_dialogfragment = view.findViewById(R.id.textView2_deviceID_dialogfragment);
        textView2_temperature_dialogfragment = view.findViewById(R.id.textView2_temperature_dialogfragment);
        textView2_timestamp_dialogfragment = view.findViewById(R.id.textView2_timestamp_dialogfragment);
        textView2_status_dialogfragment = view.findViewById(R.id.textView2_status_dialogfragment);

        SimpleDateFormat fullDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Cursor c = sqlDB.getElderById(tempList.getElder_id());
        textView2_id_dialogfragment.append(" "+tempList.getElder_id());
        textView2_deviceID_dialogfragment.append(" "+tempList.getDevice_id());
        textView2_temperature_dialogfragment.append(" "+tempList.getTemperature());
        textView2_timestamp_dialogfragment.append(" "+fullDF.format(tempList.getTimestamp()));
        textView2_status_dialogfragment.append(" "+tempList.getStatus());

        if(c.getCount() >0){
            if (c.moveToFirst()) {
                    textView2_name_dialogfragment.append(" "+c.getString(c.getColumnIndex(SQLiteDBHelper.ELDER_COLUMN_NAME)));
                    textView2_bedno_dialogfragment.append(" "+c.getInt(c.getColumnIndex(SQLiteDBHelper.ELDER_COLUMN_BED_NO)));
                }
        }else{
            textView2_name_dialogfragment.append(" 未有資料");
            textView2_bedno_dialogfragment.append(" 未有資料");}
    }
    }
