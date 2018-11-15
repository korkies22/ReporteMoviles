/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzz;

@Deprecated
public final class zzaas {
    private static zzaas zzaBn;
    private static final Object zztU;
    private final String zzVQ;
    private final Status zzaBo;
    private final boolean zzaBp;
    private final boolean zzaBq;

    static {
        zztU = new Object();
    }

    zzaas(Context context) {
        Object object = context.getResources();
        int n = object.getIdentifier("google_app_measurement_enable", "integer", object.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        boolean bl = true;
        boolean bl2 = true;
        if (n != 0) {
            if (object.getInteger(n) == 0) {
                bl2 = false;
            }
            this.zzaBq = bl2 ^ true;
        } else {
            this.zzaBq = false;
            bl2 = bl;
        }
        this.zzaBp = bl2;
        String string2 = zzz.zzaD(context);
        object = string2;
        if (string2 == null) {
            object = new zzam(context).getString("google_app_id");
        }
        if (TextUtils.isEmpty((CharSequence)object)) {
            this.zzaBo = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.zzVQ = null;
            return;
        }
        this.zzVQ = object;
        this.zzaBo = Status.zzayh;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Status zzay(Context object) {
        zzac.zzb(object, (Object)"Context must not be null.");
        Object object2 = zztU;
        synchronized (object2) {
            if (zzaBn != null) return zzaas.zzaBn.zzaBo;
            zzaBn = new zzaas((Context)object);
            return zzaas.zzaBn.zzaBo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static zzaas zzdc(String object) {
        Object object2 = zztU;
        synchronized (object2) {
            if (zzaBn != null) return zzaBn;
            StringBuilder stringBuilder = new StringBuilder(34 + String.valueOf(object).length());
            stringBuilder.append("Initialize must be called before ");
            stringBuilder.append((String)object);
            stringBuilder.append(".");
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public static String zzwj() {
        return zzaas.zzdc((String)"getGoogleAppId").zzVQ;
    }

    public static boolean zzwk() {
        return zzaas.zzdc((String)"isMeasurementExplicitlyDisabled").zzaBq;
    }
}
