package hk.com.caretech.clive.idata_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.List;

import hk.com.caretech.clive.idata_android.Model.Temperature;


class RecyclerviewAdapter_retrieveLocalTemperature extends RecyclerView.Adapter<RecyclerviewAdapter_retrieveLocalTemperature.TheView> {


    List<TemperatureModel_Local> tempList;
    TextView textView_tempId_local;
    TextView textView_temperature;
    TextView textView_timestamp;


    public RecyclerviewAdapter_retrieveLocalTemperature(List<TemperatureModel_Local> tempList) {
        this.tempList = tempList;
    }

    @NonNull
    @Override
    public RecyclerviewAdapter_retrieveLocalTemperature.TheView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerviewAdapter_retrieveLocalTemperature.TheView(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.temp_table_local, parent, false));
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

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public TheView(@NonNull View itemView) {
            super(itemView);
            textView_tempId_local = itemView.findViewById(R.id.textView_tempId_temp_table_local);
            textView_temperature = itemView.findViewById(R.id.textView_tempId_temp_table_local);
            textView_timestamp = itemView.findViewById(R.id.textView_timestamp_temp_table_local);
        }

        public void bindItemList(int position) {
            if(position==0){
               // textView_tempId_local.setText("Temp ID");
                textView_temperature.setText("Temp(°C)");
                textView_timestamp.setText("Date and Time");
            }else if(position>0){
              //  textView_tempId_local.setText(Integer.toString(tempList.get(position-1).getTemperature_id_local()));
                textView_temperature.setText(Double.toString(tempList.get(position-1).getTemperature()));
                textView_timestamp.setText(df.format(tempList.get(position-1).getTimestamp()));
            }
            }
        }
    }





