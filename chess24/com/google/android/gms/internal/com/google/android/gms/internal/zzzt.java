/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.SparseArray
 */
package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzaav;
import com.google.android.gms.internal.zzaaw;
import com.google.android.gms.internal.zzaax;
import com.google.android.gms.internal.zzzw;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzzt
extends zzzw {
    private final SparseArray<zza> zzayx = new SparseArray();

    private zzzt(zzaax zzaax2) {
        super(zzaax2);
        this.zzaBs.zza("AutoManageHelper", this);
    }

    public static zzzt zza(zzaav object) {
        zzzt zzzt2 = (object = zzzt.zzc((zzaav)object)).zza("AutoManageHelper", zzzt.class);
        if (zzzt2 != null) {
            return zzzt2;
        }
        return new zzzt((zzaax)object);
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        for (int i = 0; i < this.zzayx.size(); ++i) {
            ((zza)this.zzayx.valueAt(i)).dump(string, fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean bl = this.mStarted;
        String string = String.valueOf(this.zzayx);
        StringBuilder stringBuilder = new StringBuilder(14 + String.valueOf(string).length());
        stringBuilder.append("onStart ");
        stringBuilder.append(bl);
        stringBuilder.append(" ");
        stringBuilder.append(string);
        Log.d((String)"AutoManageHelper", (String)stringBuilder.toString());
        if (!this.zzayG) {
            for (int i = 0; i < this.zzayx.size(); ++i) {
                ((zza)this.zzayx.valueAt((int)i)).zzayz.connect();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (int i = 0; i < this.zzayx.size(); ++i) {
            ((zza)this.zzayx.valueAt((int)i)).zzayz.disconnect();
        }
    }

    public void zza(int n, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener object) {
        zzac.zzb(googleApiClient, (Object)"GoogleApiClient instance cannot be null");
        boolean bl = this.zzayx.indexOfKey(n) < 0;
        StringBuilder stringBuilder = new StringBuilder(54);
        stringBuilder.append("Already managing a GoogleApiClient with id ");
        stringBuilder.append(n);
        zzac.zza(bl, (Object)stringBuilder.toString());
        bl = this.mStarted;
        boolean bl2 = this.zzayG;
        stringBuilder = new StringBuilder(54);
        stringBuilder.append("starting AutoManage for client ");
        stringBuilder.append(n);
        stringBuilder.append(" ");
        stringBuilder.append(bl);
        stringBuilder.append(" ");
        stringBuilder.append(bl2);
        Log.d((String)"AutoManageHelper", (String)stringBuilder.toString());
        object = new zza(n, googleApiClient, (GoogleApiClient.OnConnectionFailedListener)object);
        this.zzayx.put(n, object);
        if (this.mStarted && !this.zzayG) {
            object = String.valueOf(googleApiClient);
            stringBuilder = new StringBuilder(11 + String.valueOf(object).length());
            stringBuilder.append("connecting ");
            stringBuilder.append((String)object);
            Log.d((String)"AutoManageHelper", (String)stringBuilder.toString());
            googleApiClient.connect();
        }
    }

    @Override
    protected void zza(ConnectionResult connectionResult, int n) {
        Log.w((String)"AutoManageHelper", (String)"Unresolved error while connecting client. Stopping auto-manage.");
        if (n < 0) {
            Log.wtf((String)"AutoManageHelper", (String)"AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", (Throwable)new Exception());
            return;
        }
        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = (zza)this.zzayx.get(n);
        if (onConnectionFailedListener != null) {
            this.zzcu(n);
            onConnectionFailedListener = onConnectionFailedListener.zzayA;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    public void zzcu(int n) {
        zza zza2 = (zza)this.zzayx.get(n);
        this.zzayx.remove(n);
        if (zza2 != null) {
            zza2.zzuX();
        }
    }

    @Override
    protected void zzuW() {
        for (int i = 0; i < this.zzayx.size(); ++i) {
            ((zza)this.zzayx.valueAt((int)i)).zzayz.connect();
        }
    }

    private class zza
    implements GoogleApiClient.OnConnectionFailedListener {
        public final GoogleApiClient.OnConnectionFailedListener zzayA;
        public final int zzayy;
        public final GoogleApiClient zzayz;

        public zza(int n, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
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

}
