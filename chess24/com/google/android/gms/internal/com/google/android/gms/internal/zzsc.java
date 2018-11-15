/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteException
 *  android.text.TextUtils
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzf;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzacx;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.internal.zzrf;
import com.google.android.gms.internal.zzri;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzrx;
import com.google.android.gms.internal.zzry;
import com.google.android.gms.internal.zzrz;
import com.google.android.gms.internal.zzsa;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzsl;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzso;
import com.google.android.gms.internal.zzsq;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zzsy;
import com.google.android.gms.internal.zzsz;
import com.google.android.gms.internal.zzta;
import com.google.android.gms.internal.zztb;
import com.google.android.gms.internal.zztc;
import com.google.android.gms.internal.zztd;
import com.google.android.gms.internal.zztg;
import com.google.android.gms.internal.zzth;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class zzsc
extends zzru {
    private boolean mStarted;
    private final zzsz zzadA;
    private final zzsy zzadB;
    private final zzrz zzadC;
    private long zzadD;
    private final zzsl zzadE;
    private final zzsl zzadF;
    private final zztd zzadG;
    private long zzadH;
    private boolean zzadI;
    private final zzsa zzadz;

    protected zzsc(zzrw zzrw2, zzrx zzrx2) {
        super(zzrw2);
        zzac.zzw(zzrx2);
        this.zzadD = Long.MIN_VALUE;
        this.zzadB = zzrx2.zzk(zzrw2);
        this.zzadz = zzrx2.zzm(zzrw2);
        this.zzadA = zzrx2.zzn(zzrw2);
        this.zzadC = zzrx2.zzo(zzrw2);
        this.zzadG = new zztd(this.zznq());
        this.zzadE = new zzsl(zzrw2){

            @Override
            public void run() {
                zzsc.this.zzoa();
            }
        };
        this.zzadF = new zzsl(zzrw2){

            @Override
            public void run() {
                zzsc.this.zzob();
            }
        };
    }

    private void zza(zzry zzry2, zzrf zzrf2) {
        zzac.zzw(zzry2);
        zzac.zzw(zzrf2);
        Object object = new zza(this.zznp());
        object.zzbn(zzry2.zznJ());
        object.enableAdvertisingIdCollection(zzry2.zznK());
        object = object.zzlN();
        zzrn zzrn2 = object.zzb(zzrn.class);
        zzrn2.zzbD("data");
        zzrn2.zzS(true);
        object.zza(zzrf2);
        zzri zzri2 = object.zzb(zzri.class);
        zzre zzre2 = object.zzb(zzre.class);
        for (Map.Entry<String, String> entry : zzry2.zzfz().entrySet()) {
            String string = entry.getKey();
            String object2 = entry.getValue();
            if ("an".equals(string)) {
                zzre2.setAppName(object2);
                continue;
            }
            if ("av".equals(string)) {
                zzre2.setAppVersion(object2);
                continue;
            }
            if ("aid".equals(string)) {
                zzre2.setAppId(object2);
                continue;
            }
            if ("aiid".equals(string)) {
                zzre2.setAppInstallerId(object2);
                continue;
            }
            if ("uid".equals(string)) {
                zzrn2.setUserId(object2);
                continue;
            }
            zzri2.set(string, object2);
        }
        this.zzb("Sending installation campaign to", zzry2.zznJ(), zzrf2);
        object.zzp(this.zznv().zzpE());
        object.zzmf();
    }

    private boolean zzbV(String string) {
        if (zzacx.zzaQ(this.getContext()).checkCallingOrSelfPermission(string) == 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zznY() {
        void var1_3;
        this.zzmq();
        Context context = this.zznp().getContext();
        if (!zztb.zzT(context)) {
            this.zzbR("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zztc.zzU(context)) {
            this.zzbS("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzT(context)) {
            String string = "CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.";
        } else {
            if (CampaignTrackingService.zzU(context)) {
                return;
            }
            String string = "CampaignTrackingService is not registered or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.";
        }
        this.zzbR((String)var1_3);
    }

    private void zzoa() {
        this.zzb(new zzso(){

            @Override
            public void zzf(Throwable throwable) {
                zzsc.this.zzog();
            }
        });
    }

    private void zzob() {
        try {
            this.zzadz.zznS();
            this.zzog();
        }
        catch (SQLiteException sQLiteException) {
            this.zzd("Failed to delete stale hits", (Object)sQLiteException);
        }
        this.zzadF.zzx(86400000L);
    }

    private boolean zzoh() {
        boolean bl = this.zzadI;
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (this.zzon() > 0L) {
            bl2 = true;
        }
        return bl2;
    }

    private void zzoi() {
        long l;
        zzsn zzsn2 = this.zznu();
        if (!zzsn2.zzpd()) {
            return;
        }
        if (!zzsn2.zzcv() && (l = this.zznT()) != 0L && Math.abs(this.zznq().currentTimeMillis() - l) <= this.zzns().zzoF()) {
            this.zza("Dispatch alarm scheduled (ms)", this.zzns().zzoE());
            zzsn2.schedule();
        }
    }

    private void zzoj() {
        this.zzoi();
        long l = this.zzon();
        long l2 = this.zznv().zzpG();
        if (l2 == 0L || (l2 = l - Math.abs(this.zznq().currentTimeMillis() - l2)) <= 0L) {
            l2 = Math.min(this.zzns().zzoC(), l);
        }
        this.zza("Dispatch scheduled (ms)", l2);
        if (this.zzadE.zzcv()) {
            l2 = Math.max(1L, l2 + this.zzadE.zzpa());
            this.zzadE.zzy(l2);
            return;
        }
        this.zzadE.zzx(l2);
    }

    private void zzok() {
        this.zzol();
        this.zzom();
    }

    private void zzol() {
        if (this.zzadE.zzcv()) {
            this.zzbO("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzadE.cancel();
    }

    private void zzom() {
        zzsn zzsn2 = this.zznu();
        if (zzsn2.zzcv()) {
            zzsn2.cancel();
        }
    }

    protected void onServiceConnected() {
        this.zzmq();
        this.zzod();
    }

    void start() {
        this.zznA();
        zzac.zza(this.mStarted ^ true, (Object)"Analytics backend already started");
        this.mStarted = true;
        this.zznt().zzg(new Runnable(){

            @Override
            public void run() {
                zzsc.this.zznZ();
            }
        });
    }

    public void zzV(boolean bl) {
        this.zzog();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long zza(zzry zzry2, boolean bl) {
        Throwable throwable2222;
        zzac.zzw(zzry2);
        this.zznA();
        this.zzmq();
        this.zzadz.beginTransaction();
        this.zzadz.zza(zzry2.zznI(), zzry2.zzlX());
        long l = this.zzadz.zza(zzry2.zznI(), zzry2.zzlX(), zzry2.zznJ());
        if (!bl) {
            zzry2.zzr(l);
        } else {
            zzry2.zzr(l + 1L);
        }
        this.zzadz.zzb(zzry2);
        this.zzadz.setTransactionSuccessful();
        try {
            this.zzadz.endTransaction();
            return l;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Failed to end transaction", (Object)sQLiteException);
            return l;
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLiteException sQLiteException) {}
            {
                this.zze("Failed to update Analytics property", (Object)sQLiteException);
            }
            try {
                this.zzadz.endTransaction();
                return -1L;
            }
            catch (SQLiteException sQLiteException) {
                this.zze("Failed to end transaction", (Object)sQLiteException);
            }
            return -1L;
        }
        try {
            this.zzadz.endTransaction();
            throw throwable2222;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Failed to end transaction", (Object)sQLiteException);
        }
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(zzso zzso2, long l) {
        zzh.zzmq();
        this.zznA();
        long l2 = this.zznv().zzpG();
        l2 = l2 != 0L ? Math.abs(this.zznq().currentTimeMillis() - l2) : -1L;
        this.zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", l2);
        this.zzoc();
        try {
            this.zzoe();
            this.zznv().zzpH();
            this.zzog();
            if (zzso2 != null) {
                zzso2.zzf(null);
            }
            if (this.zzadH == l) return;
            this.zzadB.zzpz();
            return;
        }
        catch (Throwable throwable) {
            this.zze("Local dispatch failed", throwable);
            this.zznv().zzpH();
            this.zzog();
            if (zzso2 == null) return;
            zzso2.zzf(throwable);
        }
    }

    public void zza(zzst zzst2) {
        zzac.zzw(zzst2);
        zzh.zzmq();
        this.zznA();
        if (this.zzadI) {
            this.zzbP("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            this.zza("Delivering hit", zzst2);
        }
        zzst2 = this.zzf(zzst2);
        this.zzoc();
        if (this.zzadC.zzb(zzst2)) {
            this.zzbP("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        try {
            this.zzadz.zzc(zzst2);
            this.zzog();
            return;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Delivery failed to save hit to a database", (Object)sQLiteException);
            this.zznr().zza(zzst2, "deliver: failed to insert hit to database");
            return;
        }
    }

    public void zzb(zzso zzso2) {
        this.zza(zzso2, this.zzadH);
    }

    public void zzbW(String object) {
        zzac.zzdv((String)object);
        this.zzmq();
        zzrf zzrf2 = zztg.zza(this.zznr(), (String)object);
        if (zzrf2 == null) {
            this.zzd("Parsing failed. Ignoring invalid campaign data", object);
            return;
        }
        String string = this.zznv().zzpI();
        if (object.equals(string)) {
            this.zzbR("Ignoring duplicate install campaign");
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)string)) {
            this.zzd("Ignoring multiple install campaigns. original, new", string, object);
            return;
        }
        this.zznv().zzca((String)object);
        if (this.zznv().zzpF().zzz(this.zzns().zzoY())) {
            this.zzd("Campaign received too late, ignoring", zzrf2);
            return;
        }
        this.zzb("Received installation campaign", zzrf2);
        object = this.zzadz.zzv(0L).iterator();
        while (object.hasNext()) {
            this.zza((zzry)object.next(), zzrf2);
        }
    }

    protected void zzc(zzry zzry2) {
        this.zzmq();
        this.zzb("Sending first hit to property", zzry2.zznJ());
        if (this.zznv().zzpF().zzz(this.zzns().zzoY())) {
            return;
        }
        Object object = this.zznv().zzpI();
        if (TextUtils.isEmpty((CharSequence)object)) {
            return;
        }
        object = zztg.zza(this.zznr(), (String)object);
        this.zzb("Found relevant installation campaign", object);
        this.zza(zzry2, (zzrf)object);
    }

    zzst zzf(zzst zzst2) {
        if (!TextUtils.isEmpty((CharSequence)zzst2.zzpu())) {
            return zzst2;
        }
        Object object = this.zznv().zzpJ().zzpM();
        if (object == null) {
            return zzst2;
        }
        HashMap<String, String> hashMap = (Long)object.second;
        object = (String)object.first;
        hashMap = String.valueOf(hashMap);
        StringBuilder stringBuilder = new StringBuilder(1 + String.valueOf(hashMap).length() + String.valueOf(object).length());
        stringBuilder.append((String)((Object)hashMap));
        stringBuilder.append(":");
        stringBuilder.append((String)object);
        object = stringBuilder.toString();
        hashMap = new HashMap<String, String>(zzst2.zzfz());
        hashMap.put("_m", (String)object);
        return zzst.zza(this, zzst2, hashMap);
    }

    @Override
    protected void zzmr() {
        this.zzadz.initialize();
        this.zzadA.initialize();
        this.zzadC.initialize();
    }

    public long zznT() {
        zzh.zzmq();
        this.zznA();
        try {
            long l = this.zzadz.zznT();
            return l;
        }
        catch (SQLiteException sQLiteException) {
            this.zze("Failed to get min/max hit times from local store", (Object)sQLiteException);
            return 0L;
        }
    }

    protected void zznZ() {
        this.zznA();
        this.zznY();
        this.zznv().zzpE();
        if (!this.zzbV("android.permission.ACCESS_NETWORK_STATE")) {
            this.zzbS("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            this.zzoo();
        }
        if (!this.zzbV("android.permission.INTERNET")) {
            this.zzbS("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            this.zzoo();
        }
        if (zztc.zzU(this.getContext())) {
            this.zzbO("AnalyticsService registered in the app manifest and enabled");
        } else {
            this.zzbR("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzadI && !this.zzadz.isEmpty()) {
            this.zzoc();
        }
        this.zzog();
    }

    public void zznj() {
        zzh.zzmq();
        this.zznA();
        this.zzbO("Delete all hits from local store");
        try {
            this.zzadz.zznQ();
            this.zzadz.zznR();
            this.zzog();
        }
        catch (SQLiteException sQLiteException) {
            this.zzd("Failed to delete hits from store", (Object)sQLiteException);
        }
        this.zzoc();
        if (this.zzadC.zznM()) {
            this.zzbO("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    public void zznm() {
        zzh.zzmq();
        this.zznA();
        this.zzbO("Service disconnected");
    }

    void zzno() {
        this.zzmq();
        this.zzadH = this.zznq().currentTimeMillis();
    }

    protected void zzoc() {
        if (this.zzadI) {
            return;
        }
        if (!this.zzns().zzox()) {
            return;
        }
        if (this.zzadC.isConnected()) {
            return;
        }
        long l = this.zzns().zzoS();
        if (this.zzadG.zzz(l)) {
            this.zzadG.start();
            this.zzbO("Connecting to service");
            if (this.zzadC.connect()) {
                this.zzbO("Connected to service");
                this.zzadG.clear();
                this.onServiceConnected();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzod() {
        zzh.zzmq();
        this.zznA();
        if (!this.zzns().zzox()) {
            this.zzbR("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zzadC.isConnected()) {
            this.zzbO("Service not connected");
            return;
        }
        if (this.zzadz.isEmpty()) {
            return;
        }
        this.zzbO("Dispatching local hits to device AnalyticsService");
        block4 : do {
            List<zzst> list;
            try {
                list = this.zzadz.zzt(this.zzns().zzoG());
                if (list.isEmpty()) {
                    this.zzog();
                    return;
                }
            }
            catch (SQLiteException sQLiteException) {
                this.zze("Failed to read hits from store", (Object)sQLiteException);
                this.zzok();
                return;
            }
            do {
                if (list.isEmpty()) continue block4;
                zzst zzst2 = list.get(0);
                if (!this.zzadC.zzb(zzst2)) {
                    this.zzog();
                    return;
                }
                list.remove(zzst2);
                try {
                    this.zzadz.zzu(zzst2.zzpp());
                }
                catch (SQLiteException sQLiteException) {
                    this.zze("Failed to remove hit that was send for delivery", (Object)sQLiteException);
                    this.zzok();
                    return;
                }
            } while (true);
            break;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    protected boolean zzoe() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK], 3[TRYBLOCK]], but top level block is 26[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    public void zzof() {
        zzh.zzmq();
        this.zznA();
        this.zzbP("Sync dispatching local hits");
        long l = this.zzadH;
        this.zzoc();
        try {
            this.zzoe();
            this.zznv().zzpH();
            this.zzog();
            if (this.zzadH != l) {
                this.zzadB.zzpz();
                return;
            }
        }
        catch (Throwable throwable) {
            this.zze("Sync local dispatch failed", throwable);
            this.zzog();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void zzog() {
        boolean bl;
        this.zznp().zzmq();
        this.zznA();
        if (!this.zzoh() || this.zzadz.isEmpty()) {
            this.zzadB.unregister();
            this.zzok();
            return;
        }
        if (!zzsq.zzaeR.get().booleanValue()) {
            this.zzadB.zzpx();
            bl = this.zzadB.isConnected();
        } else {
            bl = true;
        }
        if (bl) {
            this.zzoj();
            return;
        }
        this.zzok();
        this.zzoi();
    }

    public long zzon() {
        if (this.zzadD != Long.MIN_VALUE) {
            return this.zzadD;
        }
        long l = this.zzns().zzoD();
        if (this.zzma().zzpk()) {
            l = (long)this.zzma().zzqb() * 1000L;
        }
        return l;
    }

    public void zzoo() {
        this.zznA();
        this.zzmq();
        this.zzadI = true;
        this.zzadC.disconnect();
        this.zzog();
    }

    public void zzw(long l) {
        zzh.zzmq();
        this.zznA();
        long l2 = l;
        if (l < 0L) {
            l2 = 0L;
        }
        this.zzadD = l2;
        this.zzog();
    }

}
