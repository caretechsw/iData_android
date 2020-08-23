package hk.com.caretech.clive.idata_android.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.util.Log;
import android.widget.Toast;

public class NetworkChecker {

    public static boolean checkNetWorkStatus(Context context) {
        {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network activeNetwork = connMgr.getActiveNetwork();

            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activeNetwork == null) {
                Toast.makeText(context, "沒有網絡", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return  false;
    }
    static String TAG = NetworkChecker.class.getName();
}
