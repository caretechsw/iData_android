package hk.com.caretech.clive.idata_android;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import hk.com.caretech.clive.idata_android.Utils.ServerUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button previous_activitSetting;
    private EditText editText_inputIp_activitSetting;
    private Button button_confirm_activitySetting;
    private String ipInput;
    private OkHttpClient httpClient;
    private Context context = this;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        previous_activitSetting = findViewById(R.id.previous_activitSetting);
        editText_inputIp_activitSetting = findViewById(R.id.editText_inputIp_activitSetting);
        button_confirm_activitySetting = findViewById(R.id.button_confirm_activitySetting);

        previous_activitSetting.setOnClickListener(this);
        button_confirm_activitySetting.setOnClickListener(this);

         prefs = context.getSharedPreferences(ipPrefs, MODE_PRIVATE);
        editor = prefs.edit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        prefs = context.getSharedPreferences(ipPrefs, MODE_PRIVATE);
        ipInput = prefs.getString(ip, "localhost");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previous_activitSetting:
                onBackPressed();
                break;
            case R.id.button_confirm_activitySetting:
                ipInput = editText_inputIp_activitSetting.getText().toString();
                if(!TextUtils.isEmpty(ipInput)){
                    String baseUrl = ServerUtils.getBaseUrl(ipInput);

                    httpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(baseUrl).build();

                    httpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            editText_inputIp_activitSetting.setError("IP地址無效");
                            editText_inputIp_activitSetting.requestFocus();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                editor.putString(ip, ipInput);
                                editor.apply();
                                Toast.makeText(SettingActivity.this, "地址驗證成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
        }
    }
    }

    static String ip = "ip";
    static String ipPrefs = "ipPrefs";
    static String TAG = SettingActivity.class.getName();
}