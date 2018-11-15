/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 */
package com.crashlytics.android;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ManifestEnabledCheckStrategy;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.FirebaseInfo;

public class CrashlyticsInitProvider
extends ContentProvider {
    private static final String TAG = "CrashlyticsInitProvider";

    public int delete(Uri uri, String string, String[] arrstring) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean onCreate() {
        Context context = this.getContext();
        if (!this.shouldInitializeFabric(context, new FirebaseInfo(), new ManifestEnabledCheckStrategy())) {
            return true;
        }
        try {
            Fabric.with(context, new Crashlytics());
            Fabric.getLogger().i(TAG, "CrashlyticsInitProvider initialization successful");
            return true;
        }
        catch (IllegalStateException illegalStateException) {}
        Fabric.getLogger().i(TAG, "CrashlyticsInitProvider initialization unsuccessful");
        return false;
    }

    public Cursor query(Uri uri, String[] arrstring, String string, String[] arrstring2, String string2) {
        return null;
    }

    boolean shouldInitializeFabric(Context context, FirebaseInfo firebaseInfo, EnabledCheckStrategy enabledCheckStrategy) {
        if (firebaseInfo.isFirebaseCrashlyticsEnabled(context) && enabledCheckStrategy.isCrashlyticsEnabled(context)) {
            return true;
        }
        return false;
    }

    public int update(Uri uri, ContentValues contentValues, String string, String[] arrstring) {
        return 0;
    }

    static interface EnabledCheckStrategy {
        public boolean isCrashlyticsEnabled(Context var1);
    }

}
