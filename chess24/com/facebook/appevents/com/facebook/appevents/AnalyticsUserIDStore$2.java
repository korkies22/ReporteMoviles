/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.preference.PreferenceManager
 */
package com.facebook.appevents;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.facebook.FacebookSdk;
import java.util.concurrent.locks.ReentrantReadWriteLock;

static final class AnalyticsUserIDStore
implements Runnable {
    final /* synthetic */ String val$id;

    AnalyticsUserIDStore(String string) {
        this.val$id = string;
    }

    @Override
    public void run() {
        lock.writeLock().lock();
        try {
            userID = this.val$id;
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)FacebookSdk.getApplicationContext()).edit();
            editor.putString(com.facebook.appevents.AnalyticsUserIDStore.ANALYTICS_USER_ID_KEY, userID);
            editor.apply();
            return;
        }
        finally {
            lock.writeLock().unlock();
        }
    }
}
