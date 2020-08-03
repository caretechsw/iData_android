package com.example.idata_android;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idata_android.Model.Elder;

import okhttp3.HttpUrl;


class RecyclerViewAdapter_retrieveElder extends RecyclerView.Adapter<RecyclerViewAdapter_retrieveElder.TheView> {


    Elder[] elderList;
    TextView textView_id;
    TextView textView_name;
    TextView textView_bed_no;
    Group group;
    String baseUrl;

    public RecyclerViewAdapter_retrieveElder(Elder[] elderList, String baseUrl) {
        this.elderList = elderList;
        this.baseUrl = baseUrl;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter_retrieveElder.TheView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewAdapter_retrieveElder.TheView(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.elder_table, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_retrieveElder.TheView holder, int position) {
        holder.bindItemList(position);
    }

    @Override
    public int getItemCount() {
        return elderList.length+1;
    }

    class TheView extends RecyclerView.ViewHolder {
        public TheView(@NonNull View itemView) {
            super(itemView);
            textView_id = itemView.findViewById(R.id.textView_id);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_bed_no = itemView.findViewById(R.id.textView_bed_no);
            group = itemView.findViewById(R.id.group);

        }
        public void bindItemList(int position) {
            if(position==0){
                textView_id.setText("ID");
                textView_name.setText("Name");
                textView_bed_no.setText("Bed No");
            }else if(position>0){
            textView_id.setText(Integer.toString(elderList[position-1].getId()));
            textView_name.setText(elderList[position-1].getName());
            textView_bed_no.setText(Integer.toString(elderList[position-1].getBed_no()));
            }

            int refIds[] = group.getReferencedIds();
            for (int id : refIds) {
                itemView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent;
                        intent = new Intent(itemView.getContext(), RetrieveTemperatureActivity.class);

                        String searchTemp = baseUrl+"temp/elder_id";
                        Log.i(TAG, searchTemp);
                        HttpUrl.Builder httpBuilder = HttpUrl.parse(searchTemp).newBuilder();
                            HttpUrl tempUrl = httpBuilder.addQueryParameter("elder_id", Integer.toString(elderList[position-1].getId())).build();

                        intent.putExtra("tempUrl", tempUrl.toString());
                        intent.putExtra("elder_id", Integer.toString(elderList[position-1].getId()));
                        intent.putExtra("elder_name", elderList[position-1].getName());
                        Log.i(TAG, tempUrl.toString());
                        itemView.getContext().startActivity(intent);
                    }
                });
            }
//                Glide.with(view.context).load(itemListModel.url_forRecyclerview)
//                        .into(view.image_swipe)
            }


        }
        static String TAG="RecyclerViewAdapter_retrieveElder";
    }





