/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzzt;
import java.io.FileDescriptor;
import java.io.PrintWriter;

private class zzzt.zza
implements GoogleApiClient.OnConnectionFailedListener {
    public final GoogleApiClient.OnConnectionFailedListener zzayA;
    public final int zzayy;
    public final GoogleApiClient zzayz;

    public zzzt.zza(int n, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzayy = n;
        this.zzayz = googleApiClient;
        this.zzayA = onConnectionFailedListener;
        googleApiClient.registerConnectionFailedListener(this);
    }

    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.append(string).append("GoogleApiClient #").print(this.zzayy);
        printWriter.println(":");
        this.zzayz.dump(String.valueOf(string).concat("  "), fileDescriptor, printWriter, arrstring);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        String string = String.valueOf(connectionResult);
        StringBuilder stringBuilder = new StringBuilder(27 + String.valueOf(string).length());
        stringBuilder.append("beginFailureResolution for ");
        stringBuilder.append(string);
        Log.d((String)"AutoManageHelper", (String)stringBuilder.toString());
        zzzt.this.zzb(connectionResult, this.zzayy);
    }

    public void zzuX() {
        this.zzayz.unregisterConnectionFailedListener(this);
        this.zzayz.disconnect();
    }
}
