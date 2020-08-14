package hk.com.caretech.clive.idata_android.Utils;

public class NetworkUtils {



    public static String retrieveElderUrl(String ip){
        String url  = "http://"+ ip +":7070/elder";
        return url;
    }

    public static String retrieveTempUrl(String ip){
        String url  = "http://"+ ip +":7070/temp";
        return url;
    }

    public static String addElderUrl(String ip){
        String url  = "http://"+ ip +":7070/elder/add";
        return url;
    }

    public static String addTempUrl(String ip){
        String url  = "http://"+ ip +":7070/temp/add";
        return url;
    }




    public static final String retrieveElderUrl = "http://192.126.1.208:7070/elder";
    public static final String retrieveTempUrl = "http://192.126.1.208:7070/temp";
    public static final String addElderUrl = "http://192.126.1.208:7070/elder/add";
    public static final String addTempUrl = "http://192.126.1.208:7070/temp/add";

    static final String TAG = NetworkUtils.class.getName();
}
