package com.example.idata_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idata_android.Model.Elder;
import com.example.idata_android.Model.Temperature;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import okhttp3.Callback;
import okhttp3.Request;


class RecyclerViewAdapter_retrieveTemperature extends RecyclerView.Adapter<RecyclerViewAdapter_retrieveTemperature.TheView> {


    Temperature[] tempList;
    TextView textView_temperatureId;
    TextView textView_temperature;
    TextView textView_timestamp;


    public RecyclerViewAdapter_retrieveTemperature(Temperature[] tempList) {
        this.tempList = tempList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter_retrieveTemperature.TheView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewAdapter_retrieveTemperature.TheView(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.temperature_table, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_retrieveTemperature.TheView holder, int position) {
        holder.bindItemList(position);

    }

    @Override
    public int getItemCount() {
        return tempList.length+1;
    }

    class TheView extends RecyclerView.ViewHolder {
        public TheView(@NonNull View itemView) {
            super(itemView);
            textView_temperatureId = itemView.findViewById(R.id.textView_temperatureId);
            textView_temperature = itemView.findViewById(R.id.textView_temperature);
            textView_timestamp = itemView.findViewById(R.id.textView_timestamp);

        }
        public void bindItemList(int position) {
            if(position==0){
                textView_temperatureId.setText("Temp ID");
                textView_temperature.setText("Temp(°C)");
                textView_timestamp.setText("Date and Time");
            }else if(position>0){
                textView_temperatureId.setText(Integer.toString(tempList[position-1].getTemperatureID()));
                textView_temperature.setText(Float.toString(tempList[position-1].getTemperature()));
                textView_timestamp.setText(tempList[position-1].getTimestamp().toString());
            }
            }
        }
    }





