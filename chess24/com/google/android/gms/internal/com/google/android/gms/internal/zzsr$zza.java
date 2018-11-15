/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsh;
import com.google.android.gms.internal.zzsi;
import com.google.android.gms.internal.zzsr;
import com.google.android.gms.internal.zzss;
import com.google.android.gms.internal.zzsx;

private static class zzsr.zza
implements zzsi.zza<zzss> {
    private final zzrw zzacN;
    private final zzss zzafb;

    public zzsr.zza(zzrw zzrw2) {
        this.zzacN = zzrw2;
        this.zzafb = new zzss();
    }

    @Override
    public void zzd(String string, int n) {
        if ("ga_dispatchPeriod".equals(string)) {
            this.zzafb.zzafd = n;
            return;
        }
        this.zzacN.zznr().zzd("Int xml configuration name not recognized", string);
    }

    @Override
    public void zze(String string, boolean bl) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Override
    public void zzo(String string, String string2) {
    }

    @Override
    public /* synthetic */ zzsh zzov() {
        return this.zzpf();
    }

    @Override
    public void zzp(String string, String string2) {
        if ("ga_appName".equals(string)) {
            this.zzafb.zzabK = string2;
            return;
        }
        if ("ga_appVersion".equals(string)) {
            this.zzafb.zzabL = string2;
            return;
        }
        if ("ga_logLevel".equals(string)) {
            this.zzafb.zzafc = string2;
            return;
        }
        this.zzacN.zznr().zzd("String xml configuration name not recognized", string);
    }

    public zzss zzpf() {
        return this.zzafb;
    }
}
