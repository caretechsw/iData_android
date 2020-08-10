package hk.com.caretech.clive.idata_android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import hk.com.caretech.clive.idata_android.Server.ServerDataActivity;
import okhttp3.OkHttpClient;

public class Fragment_first extends Fragment implements View.OnClickListener {


    private EditText editText_inputElderId;
    private EditText edTemp;
    private String elderID;
    private String temp;
    //database helper object
    private SQLiteDBHelper sqlDb;
    private Context context;
    private Intent intent;
    private String android_id;
    private String url;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firstpage, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();

        editText_inputElderId = view.findViewById(R.id.editText_inputElderId_firstpage);
        view.findViewById(R.id.bttn_summitElderid_firstpage).setOnClickListener(this);
        edTemp =  view.findViewById(R.id.editText_inputTemp_firstpage);
        view.findViewById(R.id.bttn2_summitTemp_firstpage).setOnClickListener(this);
        view.findViewById(R.id.bttn3_readLocalData_firstpage).setOnClickListener(this);
        view.findViewById(R.id.bttn4_readServerData_firstpage).setOnClickListener(this);

        android_id = Settings.System.getString(view.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (android_id != null) {
            Log.i(TAG, android_id);
        }


        editText_inputElderId.requestFocus();



    }


    //saving Temperature to local storage

    /**
     *
     * @param temp
     * @param elder_id
     * @param device_id
     * @param status
     * The status has two posible values:
     *      * 1 means the name is synced with the server
     *      * 0 means the name is not synced with the server
     *      * in this case, 0 is stored by default
     */
    private void saveTempToLocalStorage(double temp, int elder_id, String device_id, int status) {
        sqlDb = new SQLiteDBHelper(context);
        sqlDb.addTemp(temp, elder_id, device_id, status);
//        Name n = new Name(name, status);
//        names.add(n);
//        refreshList();
    }

    /*
     * this method is saving the name to ther server
     * */
    private void saveDataToServer () {
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(url);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bttn_summitElderid_firstpage:
                elderID = editText_inputElderId.getText().toString();
                if (!TextUtils.isEmpty(elderID)) {
                    edTemp.requestFocus();
                }

                break;

            case R.id.bttn2_summitTemp_firstpage:
                temp = edTemp.getText().toString();
                if (!TextUtils.isEmpty(elderID) && !TextUtils.isEmpty(temp)) {
                    saveTempToLocalStorage(Double.parseDouble(temp), Integer.parseInt(elderID), android_id, 0);
                    edTemp.requestFocus();
                }
                break;

            case R.id.bttn3_readLocalData_firstpage:
                intent = new Intent(getActivity(), RetrieveLocalTemperatureActivity.class);
                startActivity(intent);
            break;

            case R.id.bttn4_readServerData_firstpage:
                intent = new Intent(getActivity(), ServerDataActivity.class);
                startActivity(intent);
                break;

        }
    }

    public class OkHttpHandler extends AsyncTask {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected Object doInBackground(Object[] objects) {

//                String url_elder = "http://192.168.1.20:7070/temp";
//                RequestBody formBody = new FormBody.Builder()
//                        .add("temperature", temperature)
//                        .add("elder_id", elder_id).build();// dynamically add more parameter like this:
//
//                Request request = new Request.Builder()
//                        .url(url_elder + "/add")
//                        .post(formBody)
//                        .build();
//
//                Call call = client.newCall(request);
//
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                    }
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                        Toast.makeText(MainActivity.this, "Data synchonized", Toast.LENGTH_SHORT).show();
//                    }
//                });
            return null;
        }
    }





    static String TAG = "Fragment_first";
}