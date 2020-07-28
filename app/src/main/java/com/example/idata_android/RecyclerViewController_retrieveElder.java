package com.example.idata_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

class RecyclerViewController_retrieveElder {



    public RecyclerViewController_retrieveElder(View view) {
    }

    class RecyclerViewAdapter_retrieveElder extends RecyclerView.Adapter<RecyclerViewController_retrieveElder.RecyclerViewAdapter_retrieveElder.TheView> {

        JSONArray jsonArray;

        public RecyclerViewAdapter_retrieveElder(JSONArray jsonArray) {
            this.jsonArray = jsonArray;
        }


        @NonNull
        @Override
        public RecyclerViewController_retrieveElder.RecyclerViewAdapter_retrieveElder.TheView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewController_retrieveElder.RecyclerViewAdapter_retrieveElder.TheView(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.fragment_crud_retrive, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewController_retrieveElder.RecyclerViewAdapter_retrieveElder.TheView holder, int position) {
            // holder.itemView.

        }


        @Override
        public int getItemCount() {
            return jsonArray.length();
        }

        class TheView extends RecyclerView.ViewHolder {
            public TheView(@NonNull View itemView) {
                super(itemView);
            }

            public void bindItemList() {
//            itemView.
//                view.product_name_main.text = itemListModel.name
//                view.product_price_main.text = "$" + itemListModel.price.toString()
//                Glide.with(view.context).load(itemListModel.url_forRecyclerview)
//                        .into(view.image_swipe)
            }
        }
    }
}
