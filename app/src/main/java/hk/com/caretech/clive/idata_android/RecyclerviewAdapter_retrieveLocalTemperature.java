package hk.com.caretech.clive.idata_android;

import android.app.Activity;
import android.content.ContentValues;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import hk.com.caretech.clive.idata_android.Model.Elder;
import hk.com.caretech.clive.idata_android.Utils.SyncStatus;


class RecyclerviewAdapter_retrieveLocalTemperature extends RecyclerView.Adapter<RecyclerviewAdapter_retrieveLocalTemperature.TheView> {


    private List<TemperatureModel_Local> tempList;
    private List<Elder> elderList;
    private TextView textView_elderID;
    private TextView textView_temperature;
    private TextView textView_deviceID;
    private TextView textView_timestamp;
    private TextView textView_status;
    private Group group_temptable_local;

    public RecyclerviewAdapter_retrieveLocalTemperature(List<TemperatureModel_Local> tempList, List<Elder> elderList) {
        this.tempList = tempList;
        this.elderList = elderList;
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
        return tempList.size();
    }

    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);
        return position;
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
            textView_status = itemView.findViewById(R.id.textView_status_temptable_local);
            group_temptable_local =  itemView.findViewById(R.id.group_temptable_local);

        }


        public void bindItemList(int position) {
//            if(position==0){
//                Log.i(TAG, "checkSize :" +tempList.size());
//                textView_elderID.setText("ID");
//                textView_temperature.setText("溫度°C");
//               // textView_deviceID.setText("量度器");
//                textView_timestamp.setText("時間(月-日)");
//                textView_status.setText("S");
//            }else if(position>0) {
//                int actualPosition = position - 1; //position 0 has been used to display heading.
//                Log.i(TAG, "position :" + position + " actualPosition : " + actualPosition);
            textView_elderID.setText(Integer.toString(tempList.get(position).getElder_id()));
            textView_temperature.setText(Double.toString(tempList.get(position).getTemperature()));
            textView_timestamp.setText(simplifiedDF.format(tempList.get(position).getTimestamp()));

            if (tempList.get(position).getStatus() == SyncStatus.SYNCHONISED) {
                textView_status.setBackgroundResource(R.drawable.ic_dot_lime);
            } else if (tempList.get(position).getStatus() == SyncStatus.UNSYNCHONISED) {
                textView_status.setBackgroundResource(R.drawable.ic_dot_lightgrey);
            }


            int refIds[] = group_temptable_local.getReferencedIds();
            for (int id : refIds) {
                itemView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TempDetailsDialogFragment mTempDetailsDialogFragment = new TempDetailsDialogFragment(tempList.get(position), elderList);
                        FragmentTransaction ft = ((AppCompatActivity)itemView.getContext()).getSupportFragmentManager().beginTransaction();
                        mTempDetailsDialogFragment.show(ft, ContentValues.TAG);
                    }
                });

            }
        }


            }
        static String TAG = RecyclerviewAdapter_retrieveLocalTemperature.class.getName();
    }





