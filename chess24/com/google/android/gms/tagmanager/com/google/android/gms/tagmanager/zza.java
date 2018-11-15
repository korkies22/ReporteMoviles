/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Process
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.tagmanager.zzbo;
import java.io.IOException;

public class zza {
    private static Object zzbCG = new Object();
    private static zza zzbCH;
    private volatile boolean mClosed = false;
    private final Context mContext;
    private final Thread zzWx;
    private volatile AdvertisingIdClient.Info zzacA;
    private volatile long zzbCA = 900000L;
    private volatile long zzbCB = 30000L;
    private volatile long zzbCC;
    private volatile long zzbCD;
    private final Object zzbCE = new Object();
    private zza zzbCF = new zza(){

        @Override
        public AdvertisingIdClient.Info zzOv() {
            void var1_7;
            String string;
            try {
                AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(zza.this.mContext);
                return info;
            }
            catch (Exception exception) {
                string = "Unknown exception. Could not get the Advertising Id Info.";
            }
            catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                string = "GooglePlayServicesNotAvailableException getting Advertising Id Info";
            }
            catch (IOException iOException) {
                string = "IOException getting Ad Id Info";
            }
            catch (GooglePlayServicesRepairableException googlePlayServicesRepairableException) {
                string = "GooglePlayServicesRepairableException getting Advertising Id Info";
            }
            catch (IllegalStateException illegalStateException) {
                string = "IllegalStateException getting Advertising Id Info";
            }
            zzbo.zzc(string, (Throwable)var1_7);
            return null;
        }
    };
    private final zze zzuI;

    private zza(Context context) {
        this(context, null, zzh.zzyv());
    }

    public zza(Context context, zza zza2, zze zze2) {
        this.zzuI = zze2;
        zze2 = context;
        if (context != null) {
            zze2 = context.getApplicationContext();
        }
        this.mContext = zze2;
        if (zza2 != null) {
            this.zzbCF = zza2;
        }
        this.zzbCC = this.zzuI.currentTimeMillis();
        this.zzWx = new Thread(new Runnable(){

            @Override
            public void run() {
                zza.this.zzOu();
            }
        });
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void zzOr() {
        // MONITORENTER : this
        this.zzOs();
        this.wait(500L);
        return;
        catch (InterruptedException interruptedException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzOs() {
        if (this.zzuI.currentTimeMillis() - this.zzbCC > this.zzbCB) {
            Object object = this.zzbCE;
            synchronized (object) {
                this.zzbCE.notify();
            }
            this.zzbCC = this.zzuI.currentTimeMillis();
            return;
        }
    }

    private void zzOt() {
        if (this.zzuI.currentTimeMillis() - this.zzbCD > 3600000L) {
            this.zzacA = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void zzOu() {
        Process.setThreadPriority((int)10);
        do {
            boolean bl = this.mClosed;
            Object object = this.zzbCF.zzOv();
            if (object != null) {
                this.zzacA = object;
                this.zzbCD = this.zzuI.currentTimeMillis();
                zzbo.zzbd("Obtained fresh AdvertisingId info from GmsCore.");
            }
            // MONITORENTER : this
            this.notifyAll();
            // MONITOREXIT : this
            try {
                object = this.zzbCE;
                // MONITORENTER : object
            }
            catch (InterruptedException interruptedException) {}
            this.zzbCE.wait(this.zzbCA);
            // MONITOREXIT : object
            continue;
            zzbo.zzbd("sleep interrupted in AdvertiserDataPoller thread; continuing");
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zza zzbA(Context context) {
        if (zzbCH != null) return zzbCH;
        Object object = zzbCG;
        synchronized (object) {
            if (zzbCH != null) return zzbCH;
            zzbCH = new zza(context);
            zzbCH.start();
            return zzbCH;
        }
    }

    public boolean isLimitAdTrackingEnabled() {
        if (this.zzacA == null) {
            this.zzOr();
        } else {
            this.zzOs();
        }
        this.zzOt();
        if (this.zzacA == null) {
            return true;
        }
        return this.zzacA.isLimitAdTrackingEnabled();
    }

    public void start() {
        this.zzWx.start();
    }

    public String zzOq() {
        if (this.zzacA == null) {
            this.zzOr();
        } else {
            this.zzOs();
        }
        this.zzOt();
        if (this.zzacA == null) {
            return null;
        }
        return this.zzacA.getId();
    }

    public static interface zza {
        public AdvertisingIdClient.Info zzOv();
    }

}
