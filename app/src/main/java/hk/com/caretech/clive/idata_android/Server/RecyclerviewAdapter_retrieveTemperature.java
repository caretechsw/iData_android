package hk.com.caretech.clive.idata_android.Server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import hk.com.caretech.clive.idata_android.Model.Temperature;
import hk.com.caretech.clive.idata_android.R;


class RecyclerviewAdapter_retrieveTemperature extends RecyclerView.Adapter<RecyclerviewAdapter_retrieveTemperature.TheView> {


    private Temperature[] tempList;
    private TextView textView_deviceID;
    private TextView textView_temperature;
    private TextView textView_timestamp;


    public RecyclerviewAdapter_retrieveTemperature(Temperature[] tempList) {
        this.tempList = tempList;
    }


    @NonNull
    @Override
    public RecyclerviewAdapter_retrieveTemperature.TheView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerviewAdapter_retrieveTemperature.TheView(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.temptable_serverdata, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter_retrieveTemperature.TheView holder, int position) {
        holder.bindItemList(position);

    }

    @Override
    public int getItemCount() {
        return tempList.length+1;
    }

    class TheView extends RecyclerView.ViewHolder {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public TheView(@NonNull View itemView) {
            super(itemView);
            textView_temperature = itemView.findViewById(R.id.textView_temp_temptable_serverdata);
            textView_deviceID = itemView.findViewById(R.id.textView_deviceID_temptable_serverdata);
            textView_timestamp = itemView.findViewById(R.id.textView_timestamp_temptable_serverdata);


        }
        public void bindItemList(int position) {
            if(position==0){
                textView_temperature.setText("溫盛(°C)");
                textView_deviceID.setText("量度器");
                textView_timestamp.setText("時間");
            }else if(position>0){
                textView_temperature.setText(Double.toString(tempList[position-1].getTemperature()));
                textView_deviceID.setText((tempList[position-1].getDevice_id()));
                textView_timestamp.setText(df.format(tempList[position-1].getTimestamp()));
            }
            }
        }

        static String TAG = RecyclerviewAdapter_retrieveTemperature.class.getName();
    }





