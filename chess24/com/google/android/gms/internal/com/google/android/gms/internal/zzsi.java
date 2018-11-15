/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.XmlResourceParser
 *  android.text.TextUtils
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsh;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

abstract class zzsi<T extends zzsh>
extends zzrt {
    zza<T> zzadX;

    public zzsi(zzrw zzrw2, zza<T> zza2) {
        super(zzrw2);
        this.zzadX = zza2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private T zza(XmlResourceParser var1_1) {
        try {
            var1_1.next();
            var2_3 = var1_1.getEventType();
            while (var2_3 != 1) {
                block9 : {
                    block11 : {
                        block10 : {
                            if (var1_1.getEventType() != 2) break block9;
                            var4_5 = var1_1.getName().toLowerCase();
                            if (!var4_5.equals("screenname")) break block10;
                            var4_5 = var1_1.getAttributeValue(null, "name");
                            var5_8 = var1_1.nextText().trim();
                            if (!TextUtils.isEmpty((CharSequence)var4_5) && !TextUtils.isEmpty((CharSequence)var5_8)) {
                                this.zzadX.zzo(var4_5, var5_8);
                            }
                            break block9;
                        }
                        if (!var4_5.equals("string")) break block11;
                        var4_5 = var1_1.getAttributeValue(null, "name");
                        var5_8 = var1_1.nextText().trim();
                        if (!TextUtils.isEmpty((CharSequence)var4_5) && var5_8 != null) {
                            this.zzadX.zzp(var4_5, var5_8);
                        }
                        break block9;
                    }
                    if (!var4_5.equals("bool")) ** GOTO lbl32
                    var4_5 = var1_1.getAttributeValue(null, "name");
                    var5_8 = var1_1.nextText().trim();
                    if (TextUtils.isEmpty((CharSequence)var4_5) || (var3_4 = TextUtils.isEmpty((CharSequence)var5_8))) break block9;
                    try {
                        var3_4 = Boolean.parseBoolean(var5_8);
                        this.zzadX.zze(var4_5, var3_4);
                    }
                    catch (NumberFormatException var4_6) {
                        block12 : {
                            var6_9 = "Error parsing bool configuration value";
                            break block12;
lbl32: // 1 sources:
                            if (!var4_5.equals("integer")) break block9;
                            var4_5 = var1_1.getAttributeValue(null, "name");
                            var5_8 = var1_1.nextText().trim();
                            if (TextUtils.isEmpty((CharSequence)var4_5) || (var3_4 = TextUtils.isEmpty((CharSequence)var5_8))) break block9;
                            try {
                                var2_3 = Integer.parseInt(var5_8);
                                this.zzadX.zzd(var4_5, var2_3);
                            }
                            catch (NumberFormatException var4_7) {
                                var6_9 = "Error parsing int configuration value";
                            }
                        }
                        this.zzc(var6_9, var5_8, var4_5);
                    }
                }
                var2_3 = var1_1.next();
            }
            return this.zzadX.zzov();
        }
        catch (IOException | XmlPullParserException var1_2) {
            this.zze("Error parsing tracker configuration file", var1_2);
        }
        return this.zzadX.zzov();
    }

    public T zzaG(int n) {
        T t;
        try {
            t = this.zza(this.zznp().zznC().getResources().getXml(n));
        }
        catch (Resources.NotFoundException notFoundException) {
            this.zzd("inflate() called with unknown resourceId", (Object)notFoundException);
            return null;
        }
        return t;
    }

    public static interface zza<U extends zzsh> {
        public void zzd(String var1, int var2);

        public void zze(String var1, boolean var2);

        public void zzo(String var1, String var2);

        public U zzov();

        public void zzp(String var1, String var2);
    }

}
