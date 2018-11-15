/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsf;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzsl;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zztd;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class zzrz
extends zzru {
    private final zza zzadk;
    private zzsu zzadl;
    private final zzsl zzadm;
    private zztd zzadn;

    protected zzrz(zzrw zzrw2) {
        super(zzrw2);
        this.zzadn = new zztd(zzrw2.zznq());
        this.zzadk = new zza();
        this.zzadm = new zzsl(zzrw2){

            @Override
            public void run() {
                zzrz.this.zznO();
            }
        };
    }

    private void onDisconnect() {
        this.zzlZ().zznm();
    }

    private void onServiceDisconnected(ComponentName componentName) {
        this.zzmq();
        if (this.zzadl != null) {
            this.zzadl = null;
            this.zza("Disconnected from device AnalyticsService", (Object)componentName);
            this.onDisconnect();
        }
    }

    private void zza(zzsu zzsu2) {
        this.zzmq();
        this.zzadl = zzsu2;
        this.zznN();
        this.zzlZ().onServiceConnected();
    }

    private void zznN() {
        this.zzadn.start();
        this.zzadm.zzx(this.zzns().zzoQ());
    }

    private void zznO() {
        this.zzmq();
        if (!this.isConnected()) {
            return;
        }
        this.zzbO("Inactivity, disconnecting from device AnalyticsService");
        this.disconnect();
    }

    public boolean connect() {
        this.zzmq();
        this.zznA();
        if (this.zzadl != null) {
            return true;
        }
        zzsu zzsu2 = this.zzadk.zznP();
        if (zzsu2 != null) {
            this.zzadl = zzsu2;
            this.zznN();
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disconnect() {
        this.zzmq();
        this.zznA();
        try {
            com.google.android.gms.common.stats.zza.zzyc().zza(this.getContext(), this.zzadk);
        }
        catch (IllegalArgumentException | IllegalStateException runtimeException) {}
        if (this.zzadl != null) {
            this.zzadl = null;
            this.onDisconnect();
        }
    }

    public boolean isConnected() {
        this.zzmq();
        this.zznA();
        if (this.zzadl != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean zzb(zzst zzst2) {
        zzac.zzw(zzst2);
        this.zzmq();
        this.zznA();
        zzsu zzsu2 = this.zzadl;
        if (zzsu2 == null) {
            return false;
        }
        String string = zzst2.zzps() ? this.zzns().zzoJ() : this.zzns().zzoK();
        List<zzsf> list = Collections.emptyList();
        try {
            zzsu2.zza(zzst2.zzfz(), zzst2.zzpq(), string, list);
            this.zznN();
            return true;
        }
        catch (RemoteException remoteException) {}
        this.zzbO("Failed to send hits to AnalyticsService");
        return false;
    }

    @Override
    protected void zzmr() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean zznM() {
        this.zzmq();
        this.zznA();
        zzsu zzsu2 = this.zzadl;
        if (zzsu2 == null) {
            return false;
        }
        try {
            zzsu2.zznj();
            this.zznN();
            return true;
        }
        catch (RemoteException remoteException) {}
        this.zzbO("Failed to clear hits from AnalyticsService");
        return false;
    }

    protected class zza
    implements ServiceConnection {
        private volatile zzsu zzadp;
        private volatile boolean zzadq;

        protected zza() {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void onServiceConnected(ComponentName var1_1, IBinder var2_5) {
            block21 : {
                block19 : {
                    block20 : {
                        block17 : {
                            block18 : {
                                zzac.zzdn("AnalyticsServiceConnection.onServiceConnected");
                                // MONITORENTER : this
                                if (var2_5 == null) {
                                    zzrz.this.zzbS("Service connected with null binder");
                                    this.notifyAll();
                                    return;
                                }
                                var1_1 /* !! */  = null;
                                var3_7 = null;
                                try {
                                    ** try [egrp 3[TRYBLOCK] [2 : 35->58)] { 
lbl13: // 1 sources:
                                    ** GOTO lbl16
                                }
                                catch (Throwable var1_2) {}
                                {
                                    break block17;
lbl16: // 1 sources:
                                    var4_8 = var2_5.getInterfaceDescriptor();
                                    if (!"com.google.android.gms.analytics.internal.IAnalyticsService".equals(var4_8)) break block18;
                                    var1_1 /* !! */  = zzsu.zza.zzam(var2_5);
                                }
                                try {
                                    zzrz.this.zzbO("Bound to IAnalyticsService interface");
                                    break block19;
                                }
                                catch (RemoteException var2_6) {}
                            }
                            try {
                                zzrz.this.zze("Got binder with a wrong descriptor", var4_8);
                                break block19;
                            }
lbl27: // 2 sources:
                            catch (RemoteException var1_3) {
                                var1_1 /* !! */  = var3_7;
                                break block20;
                            }
                        }
                        this.notifyAll();
                        throw var1_2;
                    }
                    zzrz.this.zzbS("Service connect failed to get IAnalyticsService");
                }
                if (var1_1 /* !! */  == null) {
                    com.google.android.gms.common.stats.zza.zzyc().zza(zzrz.this.getContext(), zzrz.zza(zzrz.this));
                }
                if (!this.zzadq) {
                    zzrz.this.zzbR("onServiceConnected received after the timeout limit");
                    zzrz.this.zznt().zzg(new Runnable((zzsu)var1_1 /* !! */ ){
                        final /* synthetic */ zzsu zzadr;
                        {
                            this.zzadr = zzsu2;
                        }

                        @Override
                        public void run() {
                            if (!zzrz.this.isConnected()) {
                                zzrz.this.zzbP("Connected to service after a timeout");
                                zzrz.this.zza(this.zzadr);
                            }
                        }
                    });
                } else {
                    this.zzadp = var1_1 /* !! */ ;
                }
                break block21;
                catch (IllegalArgumentException var1_4) {}
            }
            this.notifyAll();
            // MONITOREXIT : this
        }

        public void onServiceDisconnected(final ComponentName componentName) {
            zzac.zzdn("AnalyticsServiceConnection.onServiceDisconnected");
            zzrz.this.zznt().zzg(new Runnable(){

                @Override
                public void run() {
                    zzrz.this.onServiceDisconnected(componentName);
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public zzsu zznP() {
            zzrz.this.zzmq();
            Object object = new Intent("com.google.android.gms.analytics.service.START");
            object.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.analytics.service.AnalyticsService"));
            Context context = zzrz.this.getContext();
            object.putExtra("app_package_name", context.getPackageName());
            com.google.android.gms.common.stats.zza zza2 = com.google.android.gms.common.stats.zza.zzyc();
            synchronized (this) {
                block6 : {
                    this.zzadp = null;
                    this.zzadq = true;
                    boolean bl = zza2.zza(context, (Intent)object, zzrz.this.zzadk, 129);
                    zzrz.this.zza("Bind to service requested", bl);
                    if (!bl) {
                        this.zzadq = false;
                        return null;
                    }
                    try {
                        this.wait(zzrz.this.zzns().zzoR());
                        break block6;
                    }
                    catch (InterruptedException interruptedException) {}
                    zzrz.this.zzbR("Wait for service connect was interrupted");
                }
                this.zzadq = false;
                object = this.zzadp;
                this.zzadp = null;
                if (object == null) {
                    zzrz.this.zzbS("Successfully bound to service but never got onServiceConnected callback");
                }
                return object;
            }
        }

    }

}
