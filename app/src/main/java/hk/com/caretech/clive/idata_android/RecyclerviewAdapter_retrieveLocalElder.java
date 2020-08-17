//package hk.com.caretech.clive.idata_android;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import hk.com.caretech.clive.idata_android.Model.Elder;
//
//class RecyclerviewAdapter_retrieveLocalElder extends RecyclerView.Adapter<RecyclerviewAdapter_retrieveLocalElder.TheView> {
//
//    private List<Elder> elderList;
//
//    private TextView textView_elderID;
//    private TextView textView_temperature;
//    private TextView textView_timestamp;
//    private TextView textView_status;
//
//    public RecyclerviewAdapter_retrieveLocalElder(List<Elder> elderList) {
//        this.elderList = elderList;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerviewAdapter_retrieveLocalElder.TheView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new RecyclerviewAdapter_retrieveLocalElder.TheView(
//                LayoutInflater.from(parent.getContext()).inflate(
//                        R.layout.eldertable_local, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerviewAdapter_retrieveLocalElder.TheView holder, int position) {
//        holder.bindItemList(position);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return elderList.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//       // return super.getItemViewType(position);
//        return position;
//    }
//
//    class TheView extends RecyclerView.ViewHolder {
//
//        //SimpleDateFormat fullDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat simplifiedDF = new SimpleDateFormat("MM-dd HH:mm");
//
//        public TheView(@NonNull View itemView) {
//            super(itemView);
//            textView_elderID = itemView.findViewById(R.id.textView_elderID_temptable_local);
//            textView_temperature = itemView.findViewById(R.id.textView_temp_temptable_local);
//           // textView_deviceID = itemView.findViewById(R.id.textView_deviceID_temptable_local);
//            textView_timestamp = itemView.findViewById(R.id.textView_timestamp_temptable_local);
//            textView_status = itemView.findViewById(R.id.textView_status_temptable_local);
//
//        }
//
//        public void bindItemList(int position) {
//                textView_elderID.setText(Integer.toString(elderList.get(position).getId()));
//                textView_temperature.setText(elderList.get(position).getName());
//                textView_timestamp.setText(Integer.toString(elderList.get(position).getBed_no()));
//            }
//            }
//        static String TAG = RecyclerviewAdapter_retrieveLocalElder.class.getName();
//    }
//
//
//
//
//
