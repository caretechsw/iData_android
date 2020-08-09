package com.example.idata_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idata_android.Model.Temperature;

import java.text.SimpleDateFormat;


class RecyclerviewAdapter_retrieveTemperature extends RecyclerView.Adapter<RecyclerviewAdapter_retrieveTemperature.TheView> {


    Temperature[] tempList;
    TextView textView_temperatureId;
    TextView textView_temperature;
    TextView textView_timestamp;


    public RecyclerviewAdapter_retrieveTemperature(Temperature[] tempList) {
        this.tempList = tempList;
    }


    @NonNull
    @Override
    public RecyclerviewAdapter_retrieveTemperature.TheView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerviewAdapter_retrieveTemperature.TheView(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.temp_table_server, parent, false));
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
            textView_temperatureId = itemView.findViewById(R.id.textView_tempId_temp_table_server);
            textView_temperature = itemView.findViewById(R.id.textView_temp_temp_table_server);
            textView_timestamp = itemView.findViewById(R.id.textView_timestamp_temp_table_server);


        }
        public void bindItemList(int position) {
            if(position==0){
                textView_temperatureId.setText("Temp ID");
                textView_temperature.setText("Temp(°C)");
                textView_timestamp.setText("Date and Time");
            }else if(position>0){
                textView_temperatureId.setText(Integer.toString(tempList[position-1].getTemperature_id()));
                textView_temperature.setText(Double.toString(tempList[position-1].getTemperature()));
                textView_timestamp.setText(df.format(tempList[position-1].getTimestamp()));
            }
            }
        }
    }





