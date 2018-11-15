/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.tagmanager;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzbgg;
import com.google.android.gms.internal.zzbgh;
import com.google.android.gms.internal.zzbgi;
import com.google.android.gms.internal.zzzx;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.zzbm;
import com.google.android.gms.tagmanager.zzbn;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcj;
import com.google.android.gms.tagmanager.zzcl;
import com.google.android.gms.tagmanager.zzcu;
import com.google.android.gms.tagmanager.zzcv;
import com.google.android.gms.tagmanager.zzo;
import com.google.android.gms.tagmanager.zzq;
import com.google.android.gms.tagmanager.zzt;

public class zzp
extends zzzx<ContainerHolder> {
    private final Context mContext;
    private final String zzbCS;
    private long zzbCX;
    private final TagManager zzbDe;
    private final zzd zzbDh;
    private final zzcl zzbDi;
    private final int zzbDj;
    private final zzq zzbDk;
    private zzf zzbDl;
    private zzbgh zzbDm;
    private volatile zzo zzbDn;
    private volatile boolean zzbDo;
    private zzai.zzj zzbDp;
    private String zzbDq;
    private zze zzbDr;
    private zza zzbDs;
    private final Looper zzrx;
    private final com.google.android.gms.common.util.zze zzuI;

    zzp(Context context, TagManager tagManager, Looper looper, String string, int n, zzf zzf2, zze zze2, zzbgh zzbgh2, com.google.android.gms.common.util.zze zze3, zzcl zzcl2, zzq zzq2) {
        Looper looper2 = looper == null ? Looper.getMainLooper() : looper;
        super(looper2);
        this.mContext = context;
        this.zzbDe = tagManager;
        context = looper;
        if (looper == null) {
            context = Looper.getMainLooper();
        }
        this.zzrx = context;
        this.zzbCS = string;
        this.zzbDj = n;
        this.zzbDl = zzf2;
        this.zzbDr = zze2;
        this.zzbDm = zzbgh2;
        this.zzbDh = new zzd();
        this.zzbDp = new zzai.zzj();
        this.zzuI = zze3;
        this.zzbDi = zzcl2;
        this.zzbDk = zzq2;
        if (this.zzOI()) {
            this.zzgZ(zzcj.zzPz().zzPB());
        }
    }

    public zzp(Context context, TagManager tagManager, Looper looper, String string, int n, zzt zzt2) {
        this(context, tagManager, looper, string, n, new zzcv(context, string), new zzcu(context, string, zzt2), new zzbgh(context), zzh.zzyv(), new zzbm(1, 5, 900000L, 5000L, "refreshing", zzh.zzyv()), new zzq(context, string));
        this.zzbDm.zzij(zzt2.zzOQ());
    }

    private boolean zzOI() {
        zzcj zzcj2 = zzcj.zzPz();
        if ((zzcj2.zzPA() == zzcj.zza.zzbFg || zzcj2.zzPA() == zzcj.zza.zzbFh) && this.zzbCS.equals(zzcj2.getContainerId())) {
            return true;
        }
        return false;
    }

    private void zza(zzai.zzj zzj2) {
        synchronized (this) {
            if (this.zzbDl != null) {
                zzbgg.zza zza2 = new zzbgg.zza();
                zza2.zzbLh = this.zzbCX;
                zza2.zzlu = new zzai.zzf();
                zza2.zzbLi = zzj2;
                this.zzbDl.zzb(zza2);
            }
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void zza(zzai.zzj var1_1, long var2_3, boolean var4_4) {
        block7 : {
            // MONITORENTER : this
            if (!var4_4) ** GOTO lbl5
            var4_4 = this.zzbDo;
lbl5: // 2 sources:
            if (!this.isReady() || (var7_5 = this.zzbDn) != null) break block7;
            return;
        }
        this.zzbDp = var1_1;
        this.zzbCX = var2_3;
        var5_6 = this.zzbDk.zzOL();
        this.zzav(Math.max(0L, Math.min(var5_6, this.zzbCX + var5_6 - this.zzuI.currentTimeMillis())));
        var1_1 = new Container(this.mContext, this.zzbDe.getDataLayer(), this.zzbCS, var2_3, (zzai.zzj)var1_1);
        if (this.zzbDn == null) {
            this.zzbDn = new zzo(this.zzbDe, this.zzrx, (Container)var1_1, this.zzbDh);
        } else {
            this.zzbDn.zza((Container)var1_1);
        }
        if (!this.isReady() && this.zzbDs.zzb((Container)var1_1)) {
            this.zzb(this.zzbDn);
        }
        // MONITOREXIT : this
    }

    private void zzaL(final boolean bl) {
        this.zzbDl.zza(new zzb());
        this.zzbDr.zza(new zzc());
        zzbgi.zzc zzc2 = this.zzbDl.zzmO(this.zzbDj);
        if (zzc2 != null) {
            this.zzbDn = new zzo(this.zzbDe, this.zzrx, new Container(this.mContext, this.zzbDe.getDataLayer(), this.zzbCS, 0L, zzc2), this.zzbDh);
        }
        this.zzbDs = new zza(){
            private Long zzbDu;

            private long zzOJ() {
                if (this.zzbDu == null) {
                    this.zzbDu = zzp.this.zzbDk.zzOL();
                }
                return this.zzbDu;
            }

            @Override
            public boolean zzb(Container container) {
                if (bl) {
                    if (container.getLastRefreshTime() + this.zzOJ() >= zzp.this.zzuI.currentTimeMillis()) {
                        return true;
                    }
                    return false;
                }
                return container.isDefault() ^ true;
            }
        };
        if (this.zzOI()) {
            this.zzbDr.zzf(0L, "");
            return;
        }
        this.zzbDl.zzOK();
    }

    private void zzav(long l) {
        synchronized (this) {
            if (this.zzbDr == null) {
                zzbo.zzbe("Refresh requested, but no network load scheduler.");
                return;
            }
            this.zzbDr.zzf(l, this.zzbDp.zzlv);
            return;
        }
    }

    static /* synthetic */ zzai.zzj zzf(zzp zzp2) {
        return zzp2.zzbDp;
    }

    String zzOC() {
        synchronized (this) {
            String string = this.zzbDq;
            return string;
        }
    }

    public void zzOF() {
        Object object = this.zzbDl.zzmO(this.zzbDj);
        if (object != null) {
            object = new Container(this.mContext, this.zzbDe.getDataLayer(), this.zzbCS, 0L, (zzbgi.zzc)object);
            this.zzb(new zzo(this.zzbDe, this.zzrx, (Container)object, new zzo.zza(){

                @Override
                public String zzOC() {
                    return zzp.this.zzOC();
                }

                @Override
                public void zzOE() {
                    zzbo.zzbe("Refresh ignored: container loaded as default only.");
                }

                @Override
                public void zzgZ(String string) {
                    zzp.this.zzgZ(string);
                }
            }));
        } else {
            zzbo.e("Default was requested, but no default container was found");
            this.zzb(this.zzbK(new Status(10, "Default was requested, but no default container was found", null)));
        }
        this.zzbDr = null;
        this.zzbDl = null;
    }

    public void zzOG() {
        this.zzaL(false);
    }

    public void zzOH() {
        this.zzaL(true);
    }

    protected ContainerHolder zzbK(Status status) {
        if (this.zzbDn != null) {
            return this.zzbDn;
        }
        if (status == Status.zzayk) {
            zzbo.e("timer expired: setting result to failure");
        }
        return new zzo(status);
    }

    @Override
    protected /* synthetic */ Result zzc(Status status) {
        return this.zzbK(status);
    }

    void zzgZ(String string) {
        synchronized (this) {
            this.zzbDq = string;
            if (this.zzbDr != null) {
                this.zzbDr.zzhc(string);
            }
            return;
        }
    }

    static interface zza {
        public boolean zzb(Container var1);
    }

    private class zzb
    implements zzbn<zzbgg.zza> {
        private zzb() {
        }

        @Override
        public /* synthetic */ void onSuccess(Object object) {
            this.zza((zzbgg.zza)object);
        }

        public void zza(zzbgg.zza zza2) {
            zzai.zzj zzj2;
            if (zza2.zzbLi != null) {
                zzj2 = zza2.zzbLi;
            } else {
                zzai.zzf zzf2 = zza2.zzlu;
                zzj2 = new zzai.zzj();
                zzj2.zzlu = zzf2;
                zzj2.zzlt = null;
                zzj2.zzlv = zzf2.version;
            }
            zzp.this.zza(zzj2, zza2.zzbLh, true);
        }

        @Override
        public void zza(zzbn.zza zza2) {
            if (!zzp.this.zzbDo) {
                zzp.this.zzav(0L);
            }
        }
    }

    private class zzc
    implements zzbn<zzai.zzj> {
        private zzc() {
        }

        @Override
        public /* synthetic */ void onSuccess(Object object) {
            this.zzb((zzai.zzj)object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void zza(zzbn.zza object) {
            if (object == zzbn.zza.zzbEJ) {
                zzp.this.zzbDk.zzON();
            }
            zzp zzp2 = zzp.this;
            synchronized (zzp2) {
                if (!zzp.this.isReady()) {
                    ContainerHolder containerHolder;
                    if (zzp.this.zzbDn != null) {
                        object = zzp.this;
                        containerHolder = zzp.this.zzbDn;
                    } else {
                        object = zzp.this;
                        containerHolder = zzp.this.zzbK(Status.zzayk);
                    }
                    object.zzb(containerHolder);
                }
            }
            long l = zzp.this.zzbDk.zzOM();
            zzp.this.zzav(l);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void zzb(zzai.zzj zzj2) {
            zzp.this.zzbDk.zzOO();
            zzp zzp2 = zzp.this;
            synchronized (zzp2) {
                if (zzj2.zzlu == null) {
                    if (zzp.zzf((zzp)zzp.this).zzlu == null) {
                        zzbo.e("Current resource is null; network resource is also null");
                        long l = zzp.this.zzbDk.zzOM();
                        zzp.this.zzav(l);
                        return;
                    }
                    zzj2.zzlu = zzp.zzf((zzp)zzp.this).zzlu;
                }
                zzp.this.zza(zzj2, zzp.this.zzuI.currentTimeMillis(), false);
                long l = zzp.this.zzbCX;
                StringBuilder stringBuilder = new StringBuilder(58);
                stringBuilder.append("setting refresh time to current time: ");
                stringBuilder.append(l);
                zzbo.v(stringBuilder.toString());
                if (!zzp.this.zzOI()) {
                    zzp.this.zza(zzj2);
                }
                return;
            }
        }
    }

    private class zzd
    implements zzo.zza {
        private zzd() {
        }

        @Override
        public String zzOC() {
            return zzp.this.zzOC();
        }

        @Override
        public void zzOE() {
            if (zzp.this.zzbDi.zzpv()) {
                zzp.this.zzav(0L);
            }
        }

        @Override
        public void zzgZ(String string) {
            zzp.this.zzgZ(string);
        }
    }

    static interface zze
    extends Releasable {
        public void zza(zzbn<zzai.zzj> var1);

        public void zzf(long var1, String var3);

        public void zzhc(String var1);
    }

    static interface zzf
    extends Releasable {
        public void zzOK();

        public void zza(zzbn<zzbgg.zza> var1);

        public void zzb(zzbgg.zza var1);

        public zzbgi.zzc zzmO(int var1);
    }

}
