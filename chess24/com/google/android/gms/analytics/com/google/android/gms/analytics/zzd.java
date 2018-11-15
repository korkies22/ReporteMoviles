/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 *  android.util.LogPrinter
 */
package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import android.util.LogPrinter;
import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzf;
import com.google.android.gms.analytics.zzi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public final class zzd
implements zzi {
    private static final Uri zzaaR;
    private final LogPrinter zzaaS = new LogPrinter(4, "GA/LogCatTransport");

    static {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("local");
        zzaaR = builder.build();
    }

    @Override
    public void zzb(zze object) {
        Object object2 = new ArrayList<zzf>(object.zzmc());
        Collections.sort(object2, new Comparator<zzf>(this){

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.zza((zzf)object, (zzf)object2);
            }

            public int zza(zzf zzf2, zzf zzf3) {
                return zzf2.getClass().getCanonicalName().compareTo(zzf3.getClass().getCanonicalName());
            }
        });
        object = new StringBuilder();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            String string = ((zzf)object2.next()).toString();
            if (TextUtils.isEmpty((CharSequence)string)) continue;
            if (object.length() != 0) {
                object.append(", ");
            }
            object.append(string);
        }
        this.zzaaS.println(object.toString());
    }

    @Override
    public Uri zzlQ() {
        return zzaaR;
    }

}
