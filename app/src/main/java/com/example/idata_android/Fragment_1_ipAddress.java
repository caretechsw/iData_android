package com.example.idata_android;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

import static android.widget.Toast.LENGTH_SHORT;

public class Fragment_1_ipAddress extends Fragment {

    EditText editText;
    String ipInput;
    Button connectBttn;
    OkHttpClient httpClient;
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1_ip_address, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectBttn = view.findViewById(R.id.Connect);
        editText = view.findViewById(R.id.ip_address);

        httpClient = new OkHttpClient();
        connectBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ipInput = editText.getText().toString().trim();
             Log.i("ipEdittext", ipInput);

             if(TextUtils.isEmpty(ipInput)){
                 ipInput = "192.168.1.20";//default Ip - home
                  }

             String url = "http://"+ipInput+":7070/";

             if(!ipInput.equals("192.168.1.20")){
                bundle.putString("ipAddress", ipInput);
                 }

                  Log.i(TAG, "url : "+ url);
                  Request request = new Request.Builder().url(url).build();

                  httpClient.newCall(request).enqueue(new Callback() {
                      @Override
                      public void onFailure(@NotNull Call call, @NotNull IOException e) {
                          e.printStackTrace();
                          System.out.print("failed");
                      }
                      @Override public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){

                    bundle.putString("url", url);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NavHostFragment.findNavController(Fragment_1_ipAddress.this).navigate(R.id.action_FirstFragmentIpAddress_to_SecondFragmentCrudSelect);
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
        editText.setText(ipInput);
    }

    @Override
    public void onPause() {
        super.onPause();
        getParentFragmentManager().setFragmentResult("requestKey", bundle);
    }

    static String TAG = "Fragment_1_ipAddress";
}