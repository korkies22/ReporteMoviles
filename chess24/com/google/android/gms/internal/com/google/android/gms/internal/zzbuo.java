/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuv;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class zzbuo<M extends zzbun<M>, T> {
    public final int tag;
    protected final int type;
    protected final Class<T> zzciF;
    protected final boolean zzcrY;

    private zzbuo(int n, Class<T> class_, int n2, boolean bl) {
        this.type = n;
        this.zzciF = class_;
        this.tag = n2;
        this.zzcrY = bl;
    }

    public static <M extends zzbun<M>, T extends zzbut> zzbuo<M, T> zza(int n, Class<T> class_, long l) {
        return new zzbuo<M, T>(n, class_, (int)l, false);
    }

    private T zzaa(List<zzbuv> list) {
        int n;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        int n2 = 0;
        for (n = 0; n < list.size(); ++n) {
            zzbuv zzbuv2 = list.get(n);
            if (zzbuv2.zzcsh.length == 0) continue;
            this.zza(zzbuv2, arrayList);
        }
        int n3 = arrayList.size();
        if (n3 == 0) {
            return null;
        }
        list = this.zzciF.cast(Array.newInstance(this.zzciF.getComponentType(), n3));
        for (n = n2; n < n3; ++n) {
            Array.set(list, n, arrayList.get(n));
        }
        return (T)list;
    }

    private T zzab(List<zzbuv> object) {
        if (object.isEmpty()) {
            return null;
        }
        object = object.get(object.size() - 1);
        return this.zzciF.cast(this.zzaM(zzbul.zzad(object.zzcsh)));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zzbuo)) {
            return false;
        }
        object = (zzbuo)object;
        if (this.type == object.type && this.zzciF == object.zzciF && this.tag == object.tag && this.zzcrY == object.zzcrY) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    final T zzZ(List<zzbuv> list) {
        if (list == null) {
            return null;
        }
        if (this.zzcrY) {
            return this.zzaa(list);
        }
        return this.zzab(list);
    }

    protected void zza(zzbuv zzbuv2, List<Object> list) {
        list.add(this.zzaM(zzbul.zzad(zzbuv2.zzcsh)));
    }

    void zza(Object object, zzbum zzbum2) throws IOException {
        if (this.zzcrY) {
            this.zzc(object, zzbum2);
            return;
        }
        this.zzb(object, zzbum2);
    }

    /*
     * Exception decompiling
     */
    protected Object zzaM(zzbul var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:367)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
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

    int zzaR(Object object) {
        if (this.zzcrY) {
            return this.zzaS(object);
        }
        return this.zzaT(object);
    }

    protected int zzaS(Object object) {
        int n = Array.getLength(object);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = n2;
            if (Array.get(object, i) != null) {
                n3 = n2 + this.zzaT(Array.get(object, i));
            }
            n2 = n3;
        }
        return n2;
    }

    protected int zzaT(Object object) {
        int n = zzbuw.zzqB(this.tag);
        switch (this.type) {
            default: {
                n = this.type;
                object = new StringBuilder(24);
                object.append("Unknown type ");
                object.append(n);
                throw new IllegalArgumentException(object.toString());
            }
            case 11: {
                return zzbum.zzc(n, (zzbut)object);
            }
            case 10: 
        }
        return zzbum.zzb(n, (zzbut)object);
    }

    /*
     * Exception decompiling
     */
    protected void zzb(Object var1_1, zzbum var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:367)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
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

    protected void zzc(Object object, zzbum zzbum2) {
        int n = Array.getLength(object);
        for (int i = 0; i < n; ++i) {
            Object object2 = Array.get(object, i);
            if (object2 == null) continue;
            this.zzb(object2, zzbum2);
        }
    }
}
