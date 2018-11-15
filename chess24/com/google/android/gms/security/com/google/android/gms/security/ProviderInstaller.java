/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.util.Log
 */
package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final zzc zzbCa = zzc.zzuz();
    private static Method zzbCb;
    private static final Object zztU;

    static {
        zztU = new Object();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void installIfNeeded(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzac.zzb(context, (Object)"Context must not be null");
        zzbCa.zzam(context);
        context = zze.getRemoteContext(context);
        if (context == null) {
            Log.e((String)"ProviderInstaller", (String)"Failed to get remote context");
            throw new GooglePlayServicesNotAvailableException(8);
        }
        Object object = zztU;
        synchronized (object) {
            try {
                try {
                    if (zzbCb == null) {
                        ProviderInstaller.zzbz(context);
                    }
                    zzbCb.invoke(null, new Object[]{context});
                    return;
                }
                catch (Exception exception) {
                    String string = String.valueOf(exception.getMessage());
                    string = string.length() != 0 ? "Failed to install provider: ".concat(string) : new String("Failed to install provider: ");
                    Log.e((String)"ProviderInstaller", (String)string);
                    throw new GooglePlayServicesNotAvailableException(8);
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public static void installIfNeededAsync(Context context, final ProviderInstallListener providerInstallListener) {
        zzac.zzb(context, (Object)"Context must not be null");
        zzac.zzb(providerInstallListener, (Object)"Listener must not be null");
        zzac.zzdn("Must be called on the UI thread");
        new AsyncTask<Void, Void, Integer>(){

            protected /* synthetic */ Object doInBackground(Object[] arrobject) {
                return this.zzc((Void[])arrobject);
            }

            protected /* synthetic */ void onPostExecute(Object object) {
                this.zzg((Integer)object);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            protected /* varargs */ Integer zzc(Void ... arrvoid) {
                int n;
                try {
                    ProviderInstaller.installIfNeeded(Context.this);
                    n = 0;
                }
                catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                    n = googlePlayServicesNotAvailableException.errorCode;
                    return n;
                }
                catch (GooglePlayServicesRepairableException googlePlayServicesRepairableException) {
                    n = googlePlayServicesRepairableException.getConnectionStatusCode();
                    return n;
                }
                do {
                    return n;
                    break;
                } while (true);
            }

            protected void zzg(Integer n) {
                if (n == 0) {
                    providerInstallListener.onProviderInstalled();
                    return;
                }
                Intent intent = zzbCa.zzb(Context.this, n, "pi");
                providerInstallListener.onProviderInstallFailed(n, intent);
            }
        }.execute((Object[])new Void[0]);
    }

    private static void zzbz(Context context) throws ClassNotFoundException, NoSuchMethodException {
        zzbCb = context.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", Context.class);
    }

    public static interface ProviderInstallListener {
        public void onProviderInstallFailed(int var1, Intent var2);

        public void onProviderInstalled();
    }

}
