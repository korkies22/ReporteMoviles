/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.SharedPreferences
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import com.google.android.gms.internal.zzcn;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AdvertisingIdClient {
    private final Context mContext;
    com.google.android.gms.common.zza zzse;
    zzcn zzsf;
    boolean zzsg;
    Object zzsh = new Object();
    zza zzsi;
    final long zzsj;

    public AdvertisingIdClient(Context context) {
        this(context, 30000L, false);
    }

    public AdvertisingIdClient(Context context, long l, boolean bl) {
        zzac.zzw(context);
        Context context2 = context;
        if (bl && (context2 = context.getApplicationContext()) == null) {
            context2 = context;
        }
        this.mContext = context2;
        this.zzsg = false;
        this.zzsj = l;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Info getAdvertisingIdInfo(Context object) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        float f;
        Throwable throwable2222;
        Object object2;
        boolean bl;
        block9 : {
            block10 : {
                block8 : {
                    f = 0.0f;
                    object2 = zze.getRemoteContext((Context)object);
                    if (object2 == null) break block8;
                    object2 = object2.getSharedPreferences("google_ads_flags", 1);
                    bl = object2.getBoolean("gads:ad_id_app_context:enabled", false);
                    try {
                        float f2;
                        f = f2 = object2.getFloat("gads:ad_id_app_context:ping_ratio", 0.0f);
                        break block9;
                    }
                    catch (Exception exception) {
                        break block10;
                    }
                }
                bl = false;
                break block9;
                catch (Exception exception) {
                    bl = false;
                }
            }
            Log.w((String)"AdvertisingIdClient", (String)"Error while reading from SharedPreferences ", (Throwable)object2);
        }
        object = new AdvertisingIdClient((Context)object, -1L, bl);
        object.zze(false);
        object2 = object.getInfo();
        AdvertisingIdClient.super.zza((Info)object2, bl, f, null);
        object.finish();
        return object2;
        {
            catch (Throwable throwable2222) {
            }
            catch (Throwable throwable3) {}
            {
                AdvertisingIdClient.super.zza(null, bl, f, throwable3);
                object.finish();
                return null;
            }
        }
        object.finish();
        throw throwable2222;
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean bl) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static zzcn zza(Context object, com.google.android.gms.common.zza zza2) throws IOException {
        try {
            void var1_4;
            return zzcn.zza.zzf(var1_4.zza(10000L, TimeUnit.MILLISECONDS));
        }
        catch (Throwable throwable) {
            throw new IOException(throwable);
        }
        catch (InterruptedException interruptedException) {
            throw new IOException("Interrupted exception");
        }
    }

    private void zza(Info info, boolean bl, float f, Throwable throwable) {
        if (Math.random() > (double)f) {
            return;
        }
        new Thread(this, this.zza(info, bl, throwable).toString()){
            final /* synthetic */ String zzsk;
            {
                this.zzsk = string;
            }

            @Override
            public void run() {
                new com.google.android.gms.ads.identifier.zza().zzu(this.zzsk);
            }
        }.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzbx() {
        Object object = this.zzsh;
        synchronized (object) {
            if (this.zzsi != null) {
                this.zzsi.cancel();
                try {
                    this.zzsi.join();
                }
                catch (InterruptedException interruptedException) {}
            }
            if (this.zzsj > 0L) {
                this.zzsi = new zza(this, this.zzsj);
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static com.google.android.gms.common.zza zzf(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
        int n = zzc.zzuz().isGooglePlayServicesAvailable(context);
        if (n != 0 && n != 2) {
            throw new IOException("Google Play services not available");
        }
        com.google.android.gms.common.zza zza2 = new com.google.android.gms.common.zza();
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        try {
            boolean bl = com.google.android.gms.common.stats.zza.zzyc().zza(context, intent, zza2, 1);
            if (!bl) throw new IOException("Connection failure");
            return zza2;
        }
        catch (Throwable throwable) {
            throw new IOException(throwable);
        }
    }

    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void finish() {
        zzac.zzdo("Calling this from your main thread can lead to deadlock");
        // MONITORENTER : this
        if (this.mContext == null) {
            // MONITOREXIT : this
            return;
        }
        var1_1 = this.zzse;
        if (var1_1 == null) {
            return;
        }
        try {
            if (this.zzsg) {
                com.google.android.gms.common.stats.zza.zzyc().zza(this.mContext, this.zzse);
            }
        }
        catch (Throwable var1_2) {}
        ** GOTO lbl-1000
        catch (IllegalArgumentException var1_4) {}
lbl-1000: // 2 sources:
        {
            Log.i((String)"AdvertisingIdClient", (String)"AdvertisingIdClient unbindService failed.", (Throwable)var1_3);
        }
        this.zzsg = false;
        this.zzsf = null;
        this.zzse = null;
        // MONITOREXIT : this
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Info getInfo() throws IOException {
        zzac.zzdo("Calling this from your main thread can lead to deadlock");
        // MONITORENTER : this
        if (!this.zzsg) {
            var1_1 = this.zzsh;
            // MONITORENTER : var1_1
            if (this.zzsi == null) throw new IOException("AdvertisingIdClient is not connected.");
            if (!this.zzsi.zzby()) {
                throw new IOException("AdvertisingIdClient is not connected.");
            }
            // MONITOREXIT : var1_1
            try {
                this.zze(false);
                ** if (this.zzsg) goto lbl-1000
            }
            catch (Exception var1_2) {
                throw new IOException("AdvertisingIdClient cannot reconnect.", var1_2);
            }
lbl-1000: // 1 sources:
            {
                throw new IOException("AdvertisingIdClient cannot reconnect.");
            }
lbl-1000: // 1 sources:
            {
            }
        }
        zzac.zzw(this.zzse);
        zzac.zzw(this.zzsf);
        try {
            var1_1 = new Info(this.zzsf.getId(), this.zzsf.zzf(true));
            // MONITOREXIT : this
        }
        catch (RemoteException var1_3) {
            Log.i((String)"AdvertisingIdClient", (String)"GMS remote exception ", (Throwable)var1_3);
            throw new IOException("Remote exception");
        }
        this.zzbx();
        return var1_1;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        this.zze(true);
    }

    Uri zza(Info info, boolean bl, Throwable object) {
        Bundle bundle = new Bundle();
        String string2 = bl ? "1" : "0";
        bundle.putString("app_context", string2);
        if (info != null) {
            string2 = info.isLimitAdTrackingEnabled() ? "1" : "0";
            bundle.putString("limit_ad_tracking", string2);
        }
        if (info != null && info.getId() != null) {
            bundle.putString("ad_id_size", Integer.toString(info.getId().length()));
        }
        if (object != null) {
            bundle.putString("error", object.getClass().getName());
        }
        info = Uri.parse((String)"https://pagead2.googlesyndication.com/pagead/gen_204?id=gmob-apps").buildUpon();
        for (String string2 : bundle.keySet()) {
            info.appendQueryParameter(string2, bundle.getString(string2));
        }
        return info.build();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void zze(boolean bl) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzac.zzdo("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzsg) {
                this.finish();
            }
            this.zzse = AdvertisingIdClient.zzf(this.mContext);
            this.zzsf = AdvertisingIdClient.zza(this.mContext, this.zzse);
            this.zzsg = true;
            if (bl) {
                this.zzbx();
            }
            return;
        }
    }

    public static final class Info {
        private final String zzsp;
        private final boolean zzsq;

        public Info(String string, boolean bl) {
            this.zzsp = string;
            this.zzsq = bl;
        }

        public String getId() {
            return this.zzsp;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzsq;
        }

        public String toString() {
            String string = this.zzsp;
            boolean bl = this.zzsq;
            StringBuilder stringBuilder = new StringBuilder(7 + String.valueOf(string).length());
            stringBuilder.append("{");
            stringBuilder.append(string);
            stringBuilder.append("}");
            stringBuilder.append(bl);
            return stringBuilder.toString();
        }
    }

    static class zza
    extends Thread {
        private WeakReference<AdvertisingIdClient> zzsl;
        private long zzsm;
        CountDownLatch zzsn;
        boolean zzso;

        public zza(AdvertisingIdClient advertisingIdClient, long l) {
            this.zzsl = new WeakReference<AdvertisingIdClient>(advertisingIdClient);
            this.zzsm = l;
            this.zzsn = new CountDownLatch(1);
            this.zzso = false;
            this.start();
        }

        private void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient)this.zzsl.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzso = true;
            }
        }

        public void cancel() {
            this.zzsn.countDown();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            try {
                if (this.zzsn.await(this.zzsm, TimeUnit.MILLISECONDS)) return;
                {
                    this.disconnect();
                    return;
                }
            }
            catch (InterruptedException interruptedException) {}
            this.disconnect();
        }

        public boolean zzby() {
            return this.zzso;
        }
    }

}
