package hk.com.caretech.clive.idata_android.Server;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import hk.com.caretech.clive.idata_android.R;
import hk.com.caretech.clive.idata_android.SettingActivity;
import hk.com.caretech.clive.idata_android.Utils.ServerUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_crud_create_elder extends Fragment implements View.OnClickListener {

    private EditText inputName;
    private EditText inputBedNo;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;
    private String ip;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crud_create_elder, container, false);
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
//                baseUrl = bundle.getString("baseUrl");
//                if(url_elder==null&&baseUrl!=null){
//                    url_elder = baseUrl+"elder";}
//                Log.i(TAG, "url_elder :"+ url_elder);
//                Log.i(TAG, "baseUrl :"+ baseUrl);
//            }});
//    }

    @Override
    public void onStart() {
        super.onStart();
        prefs = context.getSharedPreferences(SettingActivity.ipPrefs, MODE_PRIVATE);
        ip = prefs.getString(SettingActivity.ip, "");
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Bundle bundle = new Bundle();
//        bundle.putString("baseUrl", baseUrl);
//        getParentFragmentManager().setFragmentResult("requestKey", bundle);
//    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        context = view.getContext();
        view.findViewById(R.id.previous_crud_create_elder).setOnClickListener(this);
        inputName = view.findViewById(R.id.editText_inputName_crud_create_elder);
        inputBedNo = view.findViewById(R.id.editText_inputBedNo_crud_create_elder);
        view.findViewById(R.id.bttn_addElder_crud_create_elder).setOnClickListener(this);
        prefs = view.getContext().getSharedPreferences(SettingActivity.ipPrefs, MODE_PRIVATE);
        editor = prefs.edit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous_crud_create_elder:
                NavHostFragment.findNavController(Fragment_crud_create_elder.this)
                        .navigate(R.id.action_fragment_crud_create_elder_to_SecondFragmentCrudSelect);
                break;

            case R.id.bttn_addElder_crud_create_elder:

                String elderName = inputName.getText().toString();
                String bedNo = inputBedNo.getText().toString();

                OkHttpClient client = new OkHttpClient();

                if (!TextUtils.isEmpty(elderName) && !TextUtils.isEmpty(bedNo)) {
                    //Log.i(TAG, "1 :"+ baseUrl);
                    RequestBody formBody = new FormBody.Builder()
                            .add("name", elderName)
                            .add("bed_no", bedNo).build();// dynamically add more parameter like this:
                    //Log.i(TAG, "2 :"+ baseUrl);
                    Request request = new Request.Builder()
                            .url(ServerUtils.addElderUrl(ip))
                            .post(formBody)
                            .build();
                   //Log.i(TAG, "3 :"+ baseUrl);

                    Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
                              //  Log.i(TAG, "4 :"+ baseUrl);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                     //   Log.i(TAG, "5:" + baseUrl);
                                    }
                                });
                            }
                        }
                    });
                    break;
                }
        }
    }

 static String TAG = Fragment_crud_create_elder.class.getName();
}