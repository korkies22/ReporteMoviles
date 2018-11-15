/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.preference.PreferenceManager
 *  android.util.Log
 */
package com.facebook.appevents;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.AppEventUtility;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class AnalyticsUserIDStore {
    private static final String ANALYTICS_USER_ID_KEY = "com.facebook.appevents.AnalyticsUserIDStore.userID";
    private static final String TAG = "AnalyticsUserIDStore";
    private static volatile boolean initialized = false;
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static String userID;

    AnalyticsUserIDStore() {
    }

    public static String getUserID() {
        if (!initialized) {
            Log.w((String)TAG, (String)"initStore should have been called before calling setUserID");
            AnalyticsUserIDStore.initAndWait();
        }
        lock.readLock().lock();
        try {
            String string = userID;
            return string;
        }
        finally {
            lock.readLock().unlock();
        }
    }

    private static void initAndWait() {
        block5 : {
            if (initialized) {
                return;
            }
            lock.writeLock().lock();
            boolean bl = initialized;
            if (!bl) break block5;
            lock.writeLock().unlock();
            return;
        }
        try {
            userID = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext()).getString(ANALYTICS_USER_ID_KEY, null);
            initialized = true;
            return;
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public static void initStore() {
        if (initialized) {
            return;
        }
        AppEventsLogger.getAnalyticsExecutor().execute(new Runnable(){

            @Override
            public void run() {
                AnalyticsUserIDStore.initAndWait();
            }
        });
    }

    public static void setUserID(final String string) {
        AppEventUtility.assertIsNotMainThread();
        if (!initialized) {
            Log.w((String)TAG, (String)"initStore should have been called before calling setUserID");
            AnalyticsUserIDStore.initAndWait();
        }
        AppEventsLogger.getAnalyticsExecutor().execute(new Runnable(){

            @Override
            public void run() {
                lock.writeLock().lock();
                try {
                    userID = string;
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext()).edit();
                    editor.putString(AnalyticsUserIDStore.ANALYTICS_USER_ID_KEY, userID);
                    editor.apply();
                    return;
                }
                finally {
                    lock.writeLock().unlock();
                }
            }
        });
    }

}
