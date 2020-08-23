package hk.com.caretech.clive.idata_android.Synchronization;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.content.Context.ACCOUNT_SERVICE;

public class
SyncUtils {
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "hk.com.caretech.clive.idata_android";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.caretech.app.account";
    // The account name
    public static final String ACCOUNT = "Caretech";

    ContentResolver mResolver;
    static Account acc;


    /**Create a new placeholder account for the sync adapter*/
    public static void CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

             acc = new Account(ACCOUNT, ACCOUNT_TYPE);

            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(acc, AUTHORITY, 1);

            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(acc, AUTHORITY, true);

            // A schedule for automatic synchronization.
            //ContentResolver.addPeriodicSync(acc, AUTHORITY, new Bundle(), TimeUnit.HOURS.toSeconds(1)); //one hour
            Log.i(TAG, "Account added successfully!");
            // newAccount = true;
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
    }

    private static Account getAccount(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(SyncUtils.ACCOUNT_TYPE);
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    /**
     * Note that SYNC_EXTRAS_MANUAL will cause an immediate sync, without any optimization to
     * preserve battery life. If you know new data is available (perhaps via a GCM notification),
     * but the user is not actively waiting for that data, you should omit this flag; this will give
     * the OS additional freedom in scheduling your sync request.
     */
    public static void forceRefreshAll(Context context, String ip) {
        Bundle bundle = new Bundle();
        // Disable sync backoff and ignore sync preferences.
        // In other words...perform sync NOW!
        bundle.putString("ip", ip);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                getAccount(context),        // Sync account
                AUTHORITY,          // Content authority
                bundle);         // Extras
    }


    static String TAG = SyncUtils.class.getName();
}
