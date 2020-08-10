package hk.com.caretech.clive.idata_android.Server;

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

import hk.com.caretech.clive.idata_android.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_server_first extends Fragment {

    private EditText editText_ip;
    private String ipInput;
    private Button bttn_connect;
    private OkHttpClient httpClient;
    private Bundle bundle = new Bundle();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.serverdata_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText_ip = view.findViewById(R.id.edittext_ipAddress_server_data);
        bttn_connect = view.findViewById(R.id.bttn_connect_server_data);

        view.findViewById(R.id.previous_serverdata_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().onBackPressed();

            }
        });


        httpClient = new OkHttpClient();



        bttn_connect.setOnClickListener(new View.OnClickListener() {
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
                                    NavHostFragment.findNavController(Fragment_server_first.this).navigate(R.id.action_FirstFragmentIpAddress_to_SecondFragmentCrudSelect);
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
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

    static String TAG = "Fragment_server_first";
}