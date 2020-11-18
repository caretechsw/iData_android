package hk.com.caretech.clive.idata_android.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hk.com.caretech.clive.idata_android.SettingActivity;
import okhttp3.OkHttpClient;

public class ServerUtils  {




    public static String getBaseUrl(String ip, String port){
        return "http://"+ ip +":"+port+"/";
    }





//    //elder table
//    public static String ELDER_COLUMN_ID = "id";
//    public static String ELDER_COLUMN_NAME = "name";
//    public static String ELDER_COLUMN_BED_NO = "bed_no";
//
//    //temperature table
//    public static String TEMP_COLUMN_DEV_TIMESTAMP_ = "dev_timestamp";
//    public static String TEMP_COLUMN_ELDER_ID = "elder_id";
//    public static String TEMP_COLUMN_TEMP = "temperature";
//    public static String TEMP_COLUMN_TIMESTAMP = "timestamp";
//    public static String TEMP_COLUMN_STATUS = "status";


 public static String retrieveElderUrl(String ip){
        return "http://"+ip+"/elder";
    }

    public static String  retrieveTempUrl(String ip){
        return "http://"+ip+"/temp";
    }

    public static String addElderUrl(String ip){
        return "http://"+ip+"/elder/add";
    }

    public static String addTempUrl(String ip){
        return "http://"+ip+"/temp/add";
    }

    public static String addTemp_abnormalUrl(String ip){
        return "http://"+ip+"/tempab/add";
    }

    public static String retrieveIdUrl(String ip){
        return "http://"+ ip +"/elder/id";
    }

    static final String TAG = ServerUtils.class.getName();
}
