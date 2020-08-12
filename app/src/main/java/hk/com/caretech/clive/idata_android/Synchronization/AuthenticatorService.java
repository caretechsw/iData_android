package hk.com.caretech.clive.idata_android.Synchronization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class AuthenticatorService extends Service {

    private StubAuthenticator mStubAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mStubAuthenticator = new StubAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStubAuthenticator.getIBinder();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service destroyed");
    }

    static String TAG = StubAuthenticator.class.getName();
}
