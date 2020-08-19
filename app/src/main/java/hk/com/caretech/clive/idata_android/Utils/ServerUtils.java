package hk.com.caretech.clive.idata_android.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hk.com.caretech.clive.idata_android.SettingActivity;
import okhttp3.OkHttpClient;

public class ServerUtils  {



    //    prefs = context.getSharedPreferences(ipPrefs, MODE_PRIVATE);
//    ipInput = prefs.getString(ip, "localhost");

   // public static String ip =  "192.168.1.20"; //home
    //public static String ip =  "192.168.1.208"; //company

    //ad3cc65c16594cae company device_id
    //57a914a4a73fe5b8 home device


    public static String getBaseUrl(String ip){
        return "http://"+ ip +":7070/";
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
        return "http://"+ip+":7070/elder";
    }

    public static String  retrieveTempUrl(String ip){
        return "http://"+ip+":7070/temp";
    }

    public static String addElderUrl(String ip){
        return "http://"+ip+":7070/elder/add";
    }

    public static String addTempUrl(String ip){
        return "http://"+ip+":7070/temp/add";
    }

    public static String addTemp_abnormalUrl(String ip){
        return "http://"+ip+":7070/temp_abnormal/add";
    }

    public static String retrieveIdUrl(String ip){
        return "http://"+ ip +":7070/elder/id";
    }



   // public static final String retrieveElderUrl = "http://"+ip+":7070/elder";
   // public static final String retrieveTempUrl = "http://"+ip+":7070/temp";
    //public static final String addElderUrl = "http://"+ip+":7070/elder/add";
    //public static final String addTempUrl = "http://"+ip+":7070/temp/add";
    //public static final String addTemp_abnormalUrl = "http://"+ip+":7070/temp_abnormal/add";





    static final String TAG = ServerUtils.class.getName();
}
