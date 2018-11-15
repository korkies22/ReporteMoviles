/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.tagmanager.zzau;
import com.google.android.gms.tagmanager.zzaw;
import com.google.android.gms.tagmanager.zzax;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzbt;
import com.google.android.gms.tagmanager.zzcg;
import com.google.android.gms.tagmanager.zzdb;

class zzdc
extends zzdb {
    private static final Object zzbFZ = new Object();
    private static zzdc zzbGl;
    private boolean connected = true;
    private Context zzbGa;
    private zzaw zzbGb;
    private volatile zzau zzbGc;
    private int zzbGd = 1800000;
    private boolean zzbGe = true;
    private boolean zzbGf = false;
    private boolean zzbGg = true;
    private zzax zzbGh = new zzax(){

        @Override
        public void zzaM(boolean bl) {
            zzdc.this.zze(bl, zzdc.this.connected);
        }
    };
    private zza zzbGi;
    private zzbt zzbGj;
    private boolean zzbGk = false;

    private zzdc() {
    }

    private boolean isPowerSaveMode() {
        if (!this.zzbGk && this.connected && this.zzbGd > 0) {
            return false;
        }
        return true;
    }

    public static zzdc zzPT() {
        if (zzbGl == null) {
            zzbGl = new zzdc();
        }
        return zzbGl;
    }

    private void zzPU() {
        this.zzbGj = new zzbt(this);
        this.zzbGj.zzbJ(this.zzbGa);
    }

    private void zzPV() {
        this.zzbGi = new zzb();
        if (this.zzbGd > 0) {
            this.zzbGi.zzx(this.zzbGd);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzog() {
        String string;
        if (this.isPowerSaveMode()) {
            this.zzbGi.cancel();
            string = "PowerSaveMode initiated.";
        } else {
            this.zzbGi.zzx(this.zzbGd);
            string = "PowerSaveMode terminated.";
        }
        zzbo.v(string);
    }

    @Override
    public void dispatch() {
        synchronized (this) {
            if (!this.zzbGf) {
                zzbo.v("Dispatch call queued. Dispatch will run once initialization is complete.");
                this.zzbGe = true;
                return;
            }
            this.zzbGc.zzp(new Runnable(){

                @Override
                public void run() {
                    zzdc.this.zzbGb.dispatch();
                }
            });
            return;
        }
    }

    zzaw zzPW() {
        synchronized (this) {
            if (this.zzbGb == null) {
                if (this.zzbGa == null) {
                    throw new IllegalStateException("Cant get a store unless we have a context");
                }
                this.zzbGb = new zzcg(this.zzbGh, this.zzbGa);
            }
            if (this.zzbGi == null) {
                this.zzPV();
            }
            this.zzbGf = true;
            if (this.zzbGe) {
                this.dispatch();
                this.zzbGe = false;
            }
            if (this.zzbGj == null && this.zzbGg) {
                this.zzPU();
            }
            zzaw zzaw2 = this.zzbGb;
            return zzaw2;
        }
    }

    void zza(Context context, zzau zzau2) {
        synchronized (this) {
            block5 : {
                Context context2 = this.zzbGa;
                if (context2 == null) break block5;
                return;
            }
            this.zzbGa = context.getApplicationContext();
            if (this.zzbGc == null) {
                this.zzbGc = zzau2;
            }
            return;
        }
    }

    @Override
    public void zzaN(boolean bl) {
        synchronized (this) {
            this.zze(this.zzbGk, bl);
            return;
        }
    }

    void zze(boolean bl, boolean bl2) {
        synchronized (this) {
            block4 : {
                boolean bl3 = this.isPowerSaveMode();
                this.zzbGk = bl;
                this.connected = bl2;
                bl = this.isPowerSaveMode();
                if (bl != bl3) break block4;
                return;
            }
            this.zzog();
            return;
        }
    }

    @Override
    public void zznn() {
        synchronized (this) {
            if (!this.isPowerSaveMode()) {
                this.zzbGi.zzPY();
            }
            return;
        }
    }

    public static interface zza {
        public void cancel();

        public void zzPY();

        public void zzx(long var1);
    }

    private class zzb
    implements zza {
        private Handler handler;

        private zzb() {
            this.handler = new Handler(zzdc.this.zzbGa.getMainLooper(), new Handler.Callback(){

                public boolean handleMessage(Message message) {
                    if (1 == message.what && zzbFZ.equals(message.obj)) {
                        zzdc.this.dispatch();
                        if (!zzdc.this.isPowerSaveMode()) {
                            zzb.this.zzx(zzdc.this.zzbGd);
                        }
                    }
                    return true;
                }
            });
        }

        private Message obtainMessage() {
            return this.handler.obtainMessage(1, zzbFZ);
        }

        @Override
        public void cancel() {
            this.handler.removeMessages(1, zzbFZ);
        }

        @Override
        public void zzPY() {
            this.handler.removeMessages(1, zzbFZ);
            this.handler.sendMessage(this.obtainMessage());
        }

        @Override
        public void zzx(long l) {
            this.handler.removeMessages(1, zzbFZ);
            this.handler.sendMessageDelayed(this.obtainMessage(), l);
        }

    }

}
