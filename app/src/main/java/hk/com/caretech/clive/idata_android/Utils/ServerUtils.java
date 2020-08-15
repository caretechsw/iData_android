package hk.com.caretech.clive.idata_android.Utils;

public class ServerUtils {

    //elder table
    public static String ELDER_COLUMN_ID = "id";
    public static String ELDER_COLUMN_NAME = "name";
    public static String ELDER_COLUMN_BED_NO = "bed_no";

    //temperature table
    public static String TEMP_COLUMN_DEV_TIMESTAMP_ = "dev_timestamp";
    public static String TEMP_COLUMN_ELDER_ID = "elder_id";
    public static String TEMP_COLUMN_TEMP = "temperature";
    public static String TEMP_COLUMN_TIMESTAMP = "timestamp";
    public static String TEMP_COLUMN_STATUS = "status";





    public static String ip(){
        return "192.168.1.20";
    }

    public static String baseUrl(){
        return "http://"+ ip() +":7070/";
    }

    public static String retrieveElderUrl(){
        String url  = "http://"+ ip() +":7070/elder";
        return url;
    }

    public static String retrieveTempUrl(){
        String url  = "http://"+ ip() +":7070/temp";
        return url;
    }

    public static String retrieveIdUrl(){
        String url  = "http://"+ ip() +":7070/elder/id";
        return url;
    }


    public static String addElderUrl(){
        String url  = "http://"+ ip() +":7070/elder/add";
        return url;
    }

    public static String addTempUrl(){
        String url  = "http://"+ ip() +":7070/temp/add";
        return url;
    }

    public static String addTempUrl_abnormal(){
        String url  = "http://"+ ip() +":7070/temp_abnormal/add";
        return url;
    }




    public static final String retrieveElderUrl = "http://192.126.1.208:7070/elder";
    public static final String retrieveTempUrl = "http://192.126.1.208:7070/temp";
    public static final String addElderUrl = "http://192.126.1.208:7070/elder/add";
    public static final String addTempUrl = "http://192.126.1.208:7070/temp/add";

    static final String TAG = ServerUtils.class.getName();
}
