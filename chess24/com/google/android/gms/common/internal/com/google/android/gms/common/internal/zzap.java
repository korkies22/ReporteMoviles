/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

public class zzap {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String zza(String charSequence, String string, Context object, AttributeSet object2, boolean bl, boolean bl2, String string2) {
        void var0_3;
        void var6_11;
        void var1_5;
        void var5_10;
        Object object3;
        if (object3 == null) {
            Object var0_1 = null;
        } else {
            String string3 = object3.getAttributeValue(charSequence, (String)var1_5);
        }
        object3 = var0_3;
        if (var0_3 != null) {
            object3 = var0_3;
            if (var0_3.startsWith("@string/")) {
                void var4_9;
                object3 = var0_3;
                if (var4_9 != false) {
                    Object object4;
                    block10 : {
                        String string4 = var0_3.substring("@string/".length());
                        String string5 = object4.getPackageName();
                        object3 = new TypedValue();
                        try {
                            object4 = object4.getResources();
                            StringBuilder stringBuilder = new StringBuilder(8 + String.valueOf(string5).length() + String.valueOf(string4).length());
                            stringBuilder.append(string5);
                            stringBuilder.append(":string/");
                            stringBuilder.append(string4);
                            object4.getValue(stringBuilder.toString(), (TypedValue)object3, true);
                            break block10;
                        }
                        catch (Resources.NotFoundException notFoundException) {}
                        object4 = new StringBuilder(30 + String.valueOf(var1_5).length() + String.valueOf(var0_3).length());
                        object4.append("Could not find resource for ");
                        object4.append((String)var1_5);
                        object4.append(": ");
                        object4.append((String)var0_3);
                        Log.w((String)var6_11, (String)object4.toString());
                    }
                    if (object3.string != null) {
                        object3 = object3.string.toString();
                    } else {
                        object4 = String.valueOf(object3);
                        object3 = new StringBuilder(28 + String.valueOf(var1_5).length() + String.valueOf(object4).length());
                        object3.append("Resource ");
                        object3.append((String)var1_5);
                        object3.append(" was not a string: ");
                        object3.append((String)object4);
                        Log.w((String)var6_11, (String)object3.toString());
                        object3 = var0_3;
                    }
                }
            }
        }
        if (var5_10 != false && object3 == null) {
            StringBuilder stringBuilder = new StringBuilder(33 + String.valueOf(var1_5).length());
            stringBuilder.append("Required XML attribute \"");
            stringBuilder.append((String)var1_5);
            stringBuilder.append("\" missing");
            Log.w((String)var6_11, (String)stringBuilder.toString());
        }
        return object3;
    }
}
