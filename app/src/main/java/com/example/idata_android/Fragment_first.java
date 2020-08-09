package com.example.idata_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_first extends Fragment implements View.OnClickListener {

    private EditText editText_ip;
    private String ipInput;
    private Button connectBttn;
    private OkHttpClient httpClient;
    private Bundle bundle = new Bundle();
    private EditText edElder;
    private EditText edTemp;
    private String elderID;
    private String temp;
    //database helper object
    private SQLiteDBHelper sqlDb;
    private Context context;

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
        connectBttn = view.findViewById(R.id.Connect_firstpage);
        editText_ip = view.findViewById(R.id.ip_address_firstpage);
        edElder = view.findViewById(R.id.editText_inputElderId_firstpage);
        view.findViewById(R.id.bttn_summitElderid_firstpage).setOnClickListener(this);
        edTemp =  view.findViewById(R.id.editText_inputTemp_firstpage);
        view.findViewById(R.id.bttn2_summitTemp_firstpage).setOnClickListener(this);
        view.findViewById(R.id.bttn3_readLocalData_firstpage).setOnClickListener(this);
        edElder.requestFocus();

        httpClient = new OkHttpClient();
        connectBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ipInput = editText_ip.getText().toString().trim();
             Log.i("ipEdittext", ipInput);



             if(TextUtils.isEmpty(ipInput)){
                 ipInput = "192.168.1.20";//default Ip - Clive's home
                  }

             String baseUrl = "http://"+ipInput+":7070/";

            // if(!ipInput.equals("192.168.1.20")){
                bundle.putString("ipAddress", ipInput);
                // }

                  Log.i(TAG, "baseUrl : "+ baseUrl);
                  Request request = new Request.Builder().url(baseUrl).build();


                  httpClient.newCall(request).enqueue(new Callback() {
                      @Override
                      public void onFailure(@NotNull Call call, @NotNull IOException e) {
                          e.printStackTrace();
                          System.out.print("failed");
                      }
                      @Override public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){

                    bundle.putString("baseUrl", baseUrl);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NavHostFragment.findNavController(Fragment_first.this).navigate(R.id.action_FirstFragmentIpAddress_to_SecondFragmentCrudSelect);
                        }
                    });
                }
            }
        });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                ipInput = bundle.getString("ipAddress");
            }
        });
        editText_ip.setText(ipInput);
    }

    @Override
    public void onPause() {
        super.onPause();
        getParentFragmentManager().setFragmentResult("requestKey", bundle);
    }

    //saving Temperature to local storage
    private void saveTempToLocalStorage(Double temperature, int elder_id) {
        sqlDb = new SQLiteDBHelper(context);
        sqlDb.addTemp(temperature, elder_id);
//        Name n = new Name(name, status);
//        names.add(n);
//        refreshList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bttn_summitElderid_firstpage:
                elderID = edElder.getText().toString();
                if (!TextUtils.isEmpty(elderID)) {
                    edTemp.requestFocus();
                }

                break;

            case R.id.bttn2_summitTemp_firstpage:
                temp = edTemp.getText().toString();
                if (!TextUtils.isEmpty(elderID) && !TextUtils.isEmpty(temp)) {
                    saveTempToLocalStorage(Double.parseDouble(temp), Integer.parseInt(elderID));
                    edTemp.requestFocus();
                }
                break;

            case R.id.bttn3_readLocalData_firstpage:
                Intent intent;
                intent = new Intent(getActivity(), RetrieveLocalTemperatureActivity.class);
                startActivity(intent);
                break;
        }
    }
    static String TAG = "Fragment_first";
}