/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrv;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzsq;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzta;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class zzsx
extends zzru {
    private static String zzafr = "3";
    private static String zzafs = "01VDIWEA?";
    private static zzsx zzaft;

    public zzsx(zzrw zzrw2) {
        super(zzrw2);
    }

    public static zzsx zzpw() {
        return zzaft;
    }

    public void zza(int n, String string, Object object, Object object2, Object object3) {
        String string2 = zzsq.zzaek.get();
        if (Log.isLoggable((String)string2, (int)n)) {
            Log.println((int)n, (String)string2, (String)zzsx.zzc(string, object, object2, object3));
        }
        if (n >= 5) {
            this.zzb(n, string, object, object2, object3);
        }
    }

    public void zza(zzst object, String string) {
        String string2 = string;
        if (string == null) {
            string2 = "no reason provided";
        }
        object = object != null ? object.toString() : "no hit data";
        string = String.valueOf(string2);
        string = string.length() != 0 ? "Discarding hit. ".concat(string) : new String("Discarding hit. ");
        this.zzd(string, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzb(int n, String object, Object object2, Object object3, Object object4) {
        synchronized (this) {
            void var4_4;
            Object object5;
            void var5_5;
            zzac.zzw(object);
            int n2 = n;
            if (n < 0) {
                n2 = 0;
            }
            n = n2;
            if (n2 >= zzafs.length()) {
                n = zzafs.length() - 1;
            }
            char c = this.zzns().zzow() ? (char)'C' : 'c';
            String string = zzafr;
            char c2 = zzafs.charAt(n);
            String string2 = zzrv.VERSION;
            object = String.valueOf(zzsx.zzc((String)object, this.zzm(object5), this.zzm(var4_4), this.zzm(var5_5)));
            object5 = new StringBuilder(3 + String.valueOf(string).length() + String.valueOf(string2).length() + String.valueOf(object).length());
            object5.append(string);
            object5.append(c2);
            object5.append(c);
            object5.append(string2);
            object5.append(":");
            object5.append((String)object);
            object = object5 = object5.toString();
            if (object5.length() > 1024) {
                object = object5.substring(0, 1024);
            }
            if ((object5 = this.zznp().zznF()) != null) {
                object5.zzpJ().zzcb((String)object);
            }
            return;
        }
    }

    public void zzg(Map<String, String> object, String charSequence) {
        String string = charSequence;
        if (charSequence == null) {
            string = "no reason provided";
        }
        if (object != null) {
            charSequence = new StringBuilder();
            for (Map.Entry entry : object.entrySet()) {
                if (charSequence.length() > 0) {
                    charSequence.append(',');
                }
                charSequence.append((String)entry.getKey());
                charSequence.append('=');
                charSequence.append((String)entry.getValue());
            }
            object = charSequence.toString();
        } else {
            object = "no hit data";
        }
        charSequence = String.valueOf(string);
        charSequence = charSequence.length() != 0 ? "Discarding hit. ".concat((String)charSequence) : new String("Discarding hit. ");
        this.zzd((String)charSequence, object);
    }

    protected String zzm(Object object) {
        if (object == null) {
            return null;
        }
        Object object2 = object;
        if (object instanceof Integer) {
            object2 = new Long(((Integer)object).intValue());
        }
        if (object2 instanceof Long) {
            Serializable serializable = (Long)object2;
            if (Math.abs(serializable.longValue()) < 100L) {
                return String.valueOf(object2);
            }
            object = String.valueOf(object2).charAt(0) == '-' ? "-" : "";
            object2 = String.valueOf(Math.abs(serializable.longValue()));
            serializable = new StringBuilder();
            serializable.append((String)object);
            serializable.append(Math.round(Math.pow(10.0, object2.length() - 1)));
            serializable.append("...");
            serializable.append((String)object);
            serializable.append(Math.round(Math.pow(10.0, object2.length()) - 1.0));
            return serializable.toString();
        }
        if (object2 instanceof Boolean) {
            return String.valueOf(object2);
        }
        if (object2 instanceof Throwable) {
            return object2.getClass().getCanonicalName();
        }
        return "-";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void zzmr() {
        synchronized (zzsx.class) {
            zzaft = this;
            return;
        }
    }
}
