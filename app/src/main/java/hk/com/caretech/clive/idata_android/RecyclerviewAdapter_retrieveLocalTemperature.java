package hk.com.caretech.clive.idata_android;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.List;


class RecyclerviewAdapter_retrieveLocalTemperature extends RecyclerView.Adapter<RecyclerviewAdapter_retrieveLocalTemperature.TheView> {


    private List<TemperatureModel_Local> tempList;

    private TextView textView_elderID;
    private TextView textView_temperature;
    private TextView textView_deviceID;
    private TextView textView_timestamp;


    public RecyclerviewAdapter_retrieveLocalTemperature(List<TemperatureModel_Local> tempList) {
        this.tempList = tempList;
    }

    @NonNull
    @Override
    public RecyclerviewAdapter_retrieveLocalTemperature.TheView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerviewAdapter_retrieveLocalTemperature.TheView(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.temptable_local, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter_retrieveLocalTemperature.TheView holder, int position) {
        holder.bindItemList(position);

    }

    @Override
    public int getItemCount() {
        return tempList.size()+1;
    }

    class TheView extends RecyclerView.ViewHolder {

        //SimpleDateFormat fullDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simplifiedDF = new SimpleDateFormat("MM-dd HH:mm");

        public TheView(@NonNull View itemView) {
            super(itemView);
            textView_elderID = itemView.findViewById(R.id.textView_elderID_temptable_local);
            textView_temperature = itemView.findViewById(R.id.textView_temp_temptable_local);
           // textView_deviceID = itemView.findViewById(R.id.textView_deviceID_temptable_local);
            textView_timestamp = itemView.findViewById(R.id.textView_timestamp_temptable_local);

        }

        public void bindItemList(int position) {
            if(position==0){
                Log.i(TAG, "checkSize :" +tempList.size());
                textView_elderID.setText("ID");
                textView_temperature.setText("溫度(°C)");
               // textView_deviceID.setText("量度器");
                textView_timestamp.setText("時間(月-日)  ");
            }else if(position>0){
                Log.i(TAG, "position :" +position);
                textView_elderID.setText((tempList.get(position-1).getElder_id()));
                textView_temperature.setText(Double.toString(tempList.get(position-1).getTemperature()));
             //   textView_deviceID.setText(tempList.get(position-1).getDevice_id());
                textView_timestamp.setText(simplifiedDF.format(tempList.get(position-1).getTimestamp()));
            }
            }
        }
        static String TAG = "RecyclerviewAdapter_retrieveLocalTemperature";
    }





