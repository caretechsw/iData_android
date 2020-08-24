package hk.com.caretech.clive.idata_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import hk.com.caretech.clive.idata_android.Utils.NetworkChecker;
import hk.com.caretech.clive.idata_android.Utils.ServerUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.KeyEvent.KEYCODE_DEL;
import static android.view.KeyEvent.KEYCODE_NUM;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private Button previous_activitySetting;
    private TextView textView_currentIp_activitySetting;
    private EditText editText_inputIp_activitySetting;
    private EditText editText2_inputIp_activitySetting;
    private EditText editText3_inputIp_activitySetting;
    private EditText editText4_inputIp_activitySetting;
    private Button button_confirm_activitySetting;
    private String fullIpInput;
    private String ipInput1;
    private String ipInput2;
    private String ipInput3;
    private String ipInput4;

    private OkHttpClient httpClient;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public static String ip = "ip";
    public static String ipPrefs = "ipPrefs";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        NetworkChecker.checkNetWorkStatus(this);

        previous_activitySetting = findViewById(R.id.previous_activitySetting);
        textView_currentIp_activitySetting = findViewById(R.id.textView_currentIp_activitySetting);
        editText_inputIp_activitySetting = findViewById(R.id.editText_inputIp_activitySetting);
        editText2_inputIp_activitySetting = findViewById(R.id.editText2_inputIp_activitySetting);
        editText3_inputIp_activitySetting = findViewById(R.id.editText3_inputIp_activitySetting);
        editText4_inputIp_activitySetting = findViewById(R.id.editText4_inputIp_activitySetting);
        button_confirm_activitySetting = findViewById(R.id.button_confirm_activitySetting);

        editText_inputIp_activitySetting.setOnKeyListener(this);
        editText2_inputIp_activitySetting.setOnKeyListener(this);
        editText3_inputIp_activitySetting.setOnKeyListener(this);

        previous_activitySetting.setOnClickListener(this);
        button_confirm_activitySetting.setOnClickListener(this);

         prefs = this.getSharedPreferences(ipPrefs, MODE_PRIVATE);

        editor = prefs.edit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        prefs = this.getSharedPreferences(ipPrefs, MODE_PRIVATE);
        fullIpInput = prefs.getString(ip, "沒有IP地址");
        if(!TextUtils.isEmpty(fullIpInput)){
        textView_currentIp_activitySetting.setText(fullIpInput);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previous_activitySetting:
                onBackPressed();
                break;
            case R.id.button_confirm_activitySetting:
                NetworkChecker.checkNetWorkStatus(this);
                ipInput1 = editText_inputIp_activitySetting.getText().toString();
                ipInput2 = editText2_inputIp_activitySetting.getText().toString();
                ipInput3 = editText3_inputIp_activitySetting.getText().toString();
                ipInput4 = editText4_inputIp_activitySetting.getText().toString();



                if(!TextUtils.isEmpty(ipInput1) && !TextUtils.isEmpty(ipInput2)
                && !TextUtils.isEmpty(ipInput3) && !TextUtils.isEmpty(ipInput4)){
//                    if(!NetworkChecker.checkNetWorkStatus(this)){
//                        break;
//                    }

                    fullIpInput = ipInput1+"."+ipInput2+"."+ipInput3+"."+ipInput4;


                    String baseUrl = ServerUtils.getBaseUrl(fullIpInput);

                    httpClient = new OkHttpClient();
                    final Request request = new Request.Builder().url(baseUrl).build();

                    httpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SettingActivity.this,"IP地址無效", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    editText_inputIp_activitySetting.setError("重新輸入");
                                    editText_inputIp_activitySetting.requestFocus();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                editor.putString(ip, fullIpInput);
                                editor.apply();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SettingActivity.this, "地址驗證成功", Toast.LENGTH_SHORT).show();
                                        textView_currentIp_activitySetting.setText(fullIpInput);
                                    }
                                });
                        }
                    }
                });

                break;
        }
    }
    }
    static String TAG = SettingActivity.class.getName();

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        switch (view.getId()){
            case R.id.editText_inputIp_activitySetting:
                if(keyEvent.getKeyCode()==KEYCODE_DEL){break;}
                if (editText_inputIp_activitySetting.length() == 3) {
                    editText2_inputIp_activitySetting.requestFocus();
                }
                break;
            case R.id.editText2_inputIp_activitySetting:
                if(keyEvent.getKeyCode()==KEYCODE_DEL){break;}
                if (editText2_inputIp_activitySetting.length() == 3) {
                    editText3_inputIp_activitySetting.requestFocus();
                }
                break;
            case R.id.editText3_inputIp_activitySetting:
                if(keyEvent.getKeyCode()==KEYCODE_DEL){break;}
                if (editText3_inputIp_activitySetting.length() == 3) {
                    editText4_inputIp_activitySetting.requestFocus();
                }
                break;
        }


        return false;
    }
}
