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

    EditText ed;

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
        Button connectBttn = view.findViewById(R.id.Connect);
         ed = view.findViewById(R.id.ip_address);

        connectBttn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               String ipInput = ed.getText().toString().trim();
                                               Log.i("ipEdittext", ipInput);

                                               final String baseUrl = "http://"+ipInput+":7070/elder";
                                               Log.i("baseUrl", baseUrl);
                                               Request request = new Request.Builder().url(baseUrl).build();
                                               OkHttpClient httpClient = new OkHttpClient();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                System.out.print("failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Bundle result = new Bundle();
                    String baseUrlforTransfer = baseUrl;
                    result.putString("baseUrl", baseUrlforTransfer);
                    getParentFragmentManager().setFragmentResult("requestKey", result);


                    NavHostFragment.findNavController(Fragment_1_ipAddress.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);

                    String myResponse = response.body().string();

                }
            }
        });
                                          }

});
    }
}