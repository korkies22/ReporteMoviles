/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.tagmanager.zzbn;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcj;
import com.google.android.gms.tagmanager.zzt;

class zzct
implements Runnable {
    private final Context mContext;
    private final String zzbCS;
    private volatile String zzbDq;
    private final zzbgm zzbFp;
    private final String zzbFq;
    private zzbn<zzai.zzj> zzbFr;
    private volatile zzt zzbFs;
    private volatile String zzbFt;

    zzct(Context object, String string, zzbgm zzbgm2, zzt zzt2) {
        this.mContext = object;
        this.zzbFp = zzbgm2;
        this.zzbCS = string;
        this.zzbFs = zzt2;
        object = String.valueOf("/r?id=");
        string = String.valueOf(string);
        object = string.length() != 0 ? object.concat(string) : new String((String)object);
        this.zzbFq = object;
        this.zzbDq = this.zzbFq;
        this.zzbFt = null;
    }

    public zzct(Context context, String string, zzt zzt2) {
        this(context, string, new zzbgm(), zzt2);
    }

    private boolean zzPC() {
        NetworkInfo networkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        zzbo.v("...no network connectivity");
        return false;
    }

    /*
     * Exception decompiling
     */
    private void zzPD() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 8[CATCHBLOCK]
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

    @Override
    public void run() {
        if (this.zzbFr == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.zzPD();
    }

    String zzPE() {
        String string = String.valueOf(this.zzbFs.zzOQ());
        String string2 = this.zzbDq;
        String string3 = String.valueOf("&v=a65833898");
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string).length() + 0 + String.valueOf(string2).length() + String.valueOf(string3).length());
        stringBuilder.append(string);
        stringBuilder.append(string2);
        stringBuilder.append(string3);
        string = string2 = stringBuilder.toString();
        if (this.zzbFt != null) {
            string = string2;
            if (!this.zzbFt.trim().equals("")) {
                string = String.valueOf(string2);
                string2 = String.valueOf("&pv=");
                string3 = this.zzbFt;
                stringBuilder = new StringBuilder(0 + String.valueOf(string).length() + String.valueOf(string2).length() + String.valueOf(string3).length());
                stringBuilder.append(string);
                stringBuilder.append(string2);
                stringBuilder.append(string3);
                string = stringBuilder.toString();
            }
        }
        string2 = string;
        if (zzcj.zzPz().zzPA().equals((Object)zzcj.zza.zzbFh)) {
            string = String.valueOf(string);
            string2 = String.valueOf("&gtm_debug=x");
            if (string2.length() != 0) {
                return string.concat(string2);
            }
            string2 = new String(string);
        }
        return string2;
    }

    void zza(zzbn<zzai.zzj> zzbn2) {
        this.zzbFr = zzbn2;
    }

    /*
     * Enabled aggressive block sorting
     */
    void zzhc(String string) {
        if (string == null) {
            string = this.zzbFq;
        } else {
            String string2 = String.valueOf(string);
            string2 = string2.length() != 0 ? "Setting CTFE URL path: ".concat(string2) : new String("Setting CTFE URL path: ");
            zzbo.zzbc(string2);
        }
        this.zzbDq = string;
    }

    void zzhr(String string) {
        String string2 = String.valueOf(string);
        string2 = string2.length() != 0 ? "Setting previous container version: ".concat(string2) : new String("Setting previous container version: ");
        zzbo.zzbc(string2);
        this.zzbFt = string;
    }
}
