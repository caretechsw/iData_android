//package hk.com.caretech.clive.idata_android.Server;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentResultListener;
//import androidx.navigation.fragment.NavHostFragment;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//
//import hk.com.caretech.clive.idata_android.R;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class Fragment_crud_create_tempppppp extends Fragment implements View.OnClickListener {
//
//    private String url_temp;
//    private String baseUrl;
//    private EditText inputTemp;
//    private EditText inputElderId;
//
//    @Override
//    public View onCreateView(
//            LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState
//    ) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_crud_create_temppppppp, container, false);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
//                baseUrl = bundle.getString("baseUrl");
//                if(url_temp==null&&baseUrl!=null){
//                    url_temp = baseUrl+"temp";}
//                Log.i(TAG, "url_temp :"+ url_temp);
//                Log.i(TAG, "baseUrl :"+ baseUrl);
//            }});
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Bundle bundle = new Bundle();
//        bundle.putString("baseUrl", baseUrl);
//        getParentFragmentManager().setFragmentResult("requestKey", bundle);
//    }
//
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        view.findViewById(R.id.previous_crud_create_temp).setOnClickListener(this);
//        inputTemp = view.findViewById(R.id.editText_inputTemp_crud_create_temp);
//        inputElderId = view.findViewById(R.id.editText_inputElderId_crud_create_temp);
//        view.findViewById(R.id.bttn_addTemp_crud_create_temp).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.previous_crud_create_temp:
//                NavHostFragment.findNavController(Fragment_crud_create_tempppppp.this)
//                        .navigate(R.id.action_fragment_crud_create_temp_to_fragment_crud_create);
//                break;
//
//            case R.id.bttn_addTemp_crud_create_temp:
//
//                String temp = inputTemp.getText().toString();
//                String elderId = inputElderId.getText().toString();
//
//                OkHttpClient client = new OkHttpClient();
//
//                if (!TextUtils.isEmpty(temp) && !TextUtils.isEmpty(elderId)) {
//                    Log.i(TAG, "1 :"+ baseUrl);
//                    RequestBody formBody = new FormBody.Builder()
//                            .add("temperature", temp)
//                            .add("elder_id", elderId).build();// dynamically add more parameter like this:
//
//                    Log.i(TAG, "2:"+ baseUrl);
//                    Request request = new Request.Builder()
//                            .url(url_temp + "/add")
//                            .post(formBody)
//                            .build();
//                    Log.i(TAG, "3:"+ baseUrl);
//
//                    Call call = client.newCall(request);
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                            Log.i(TAG, "4:" + baseUrl);
//                        }
//
//                        @Override
//                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                            if(response.isSuccessful()) {
//
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
//                                        Log.i(TAG, "5:" + baseUrl);
//                                    }
//                                });
//
//                            }
//
//                        }
//                    });
//                    break;
//                }
//        }
//    }
//
// static String TAG = Fragment_crud_create_tempppppp..class.getName();
//}