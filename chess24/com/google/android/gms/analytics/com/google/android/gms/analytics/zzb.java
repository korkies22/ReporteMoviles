/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzc;
import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzf;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.internal.zzrf;
import com.google.android.gms.internal.zzrg;
import com.google.android.gms.internal.zzrh;
import com.google.android.gms.internal.zzri;
import com.google.android.gms.internal.zzrj;
import com.google.android.gms.internal.zzrk;
import com.google.android.gms.internal.zzrl;
import com.google.android.gms.internal.zzrm;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzro;
import com.google.android.gms.internal.zzrp;
import com.google.android.gms.internal.zzrq;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzrv;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzry;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztg;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class zzb
extends zzrt
implements zzi {
    private static DecimalFormat zzaaq;
    private final zzrw zzaam;
    private final String zzaar;
    private final Uri zzaas;
    private final boolean zzaat;
    private final boolean zzaau;

    public zzb(zzrw zzrw2, String string) {
        this(zzrw2, string, true, false);
    }

    public zzb(zzrw zzrw2, String string, boolean bl, boolean bl2) {
        super(zzrw2);
        zzac.zzdv(string);
        this.zzaam = zzrw2;
        this.zzaar = string;
        this.zzaat = bl;
        this.zzaau = bl2;
        this.zzaas = zzb.zzbp(this.zzaar);
    }

    private static String zzQ(Map<String, String> object) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : object.entrySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append((String)entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String)entry.getValue());
        }
        return stringBuilder.toString();
    }

    private static void zza(Map<String, String> map, String string, double d) {
        if (d != 0.0) {
            map.put(string, zzb.zzb(d));
        }
    }

    private static void zza(Map<String, String> map, String string, int n, int n2) {
        if (n > 0 && n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder(23);
            stringBuilder.append(n);
            stringBuilder.append("x");
            stringBuilder.append(n2);
            map.put(string, stringBuilder.toString());
        }
    }

    private static void zza(Map<String, String> map, String string, boolean bl) {
        if (bl) {
            map.put(string, "1");
        }
    }

    static String zzb(double d) {
        if (zzaaq == null) {
            zzaaq = new DecimalFormat("0.######");
        }
        return zzaaq.format(d);
    }

    private static void zzb(Map<String, String> map, String string, String string2) {
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            map.put(string, string2);
        }
    }

    static Uri zzbp(String string) {
        zzac.zzdv(string);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(string);
        return builder.build();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Map<String, String> zzc(zze object) {
        Object object2;
        Map.Entry<Integer, Double> entry;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Object object3 = object.zza(zzri.class);
        if (object3 != null) {
            object3 = object3.zzmI().entrySet().iterator();
            while (object3.hasNext()) {
                entry = (Map.Entry)object3.next();
                object2 = zzb.zzi(entry.getValue());
                if (object2 == null) continue;
                hashMap.put((String)entry.getKey(), (String)object2);
            }
        }
        if ((object3 = object.zza(zzrn.class)) != null) {
            zzb.zzb(hashMap, "t", object3.zzmT());
            zzb.zzb(hashMap, "cid", object3.zzlX());
            zzb.zzb(hashMap, "uid", object3.getUserId());
            zzb.zzb(hashMap, "sc", object3.zzmW());
            zzb.zza(hashMap, "sf", object3.zzmY());
            zzb.zza(hashMap, "ni", object3.zzmX());
            zzb.zzb(hashMap, "adid", object3.zzmU());
            zzb.zza(hashMap, "ate", object3.zzmV());
        }
        if ((object3 = object.zza(zzro.class)) != null) {
            zzb.zzb(hashMap, "cd", object3.zzna());
            zzb.zza(hashMap, "a", object3.zznb());
            zzb.zzb(hashMap, "dr", object3.zznc());
        }
        if ((object3 = object.zza(zzrl.class)) != null) {
            zzb.zzb(hashMap, "ec", object3.getCategory());
            zzb.zzb(hashMap, "ea", object3.getAction());
            zzb.zzb(hashMap, "el", object3.getLabel());
            zzb.zza(hashMap, "ev", object3.getValue());
        }
        if ((object3 = object.zza(zzrf.class)) != null) {
            zzb.zzb(hashMap, "cn", object3.getName());
            zzb.zzb(hashMap, "cs", object3.getSource());
            zzb.zzb(hashMap, "cm", object3.zzmA());
            zzb.zzb(hashMap, "ck", object3.zzmB());
            zzb.zzb(hashMap, "cc", object3.getContent());
            zzb.zzb(hashMap, "ci", object3.getId());
            zzb.zzb(hashMap, "anid", object3.zzmC());
            zzb.zzb(hashMap, "gclid", object3.zzmD());
            zzb.zzb(hashMap, "dclid", object3.zzmE());
            zzb.zzb(hashMap, "aclid", object3.zzmF());
        }
        if ((object3 = object.zza(zzrm.class)) != null) {
            zzb.zzb(hashMap, "exd", object3.getDescription());
            zzb.zza(hashMap, "exf", object3.zzmS());
        }
        if ((object3 = object.zza(zzrp.class)) != null) {
            zzb.zzb(hashMap, "sn", object3.zznd());
            zzb.zzb(hashMap, "sa", object3.getAction());
            zzb.zzb(hashMap, "st", object3.getTarget());
        }
        if ((object3 = object.zza(zzrq.class)) != null) {
            zzb.zzb(hashMap, "utv", object3.zzne());
            zzb.zza(hashMap, "utt", object3.getTimeInMillis());
            zzb.zzb(hashMap, "utc", object3.getCategory());
            zzb.zzb(hashMap, "utl", object3.getLabel());
        }
        if ((object3 = object.zza(zzrg.class)) != null) {
            object3 = object3.zzmG().entrySet().iterator();
            while (object3.hasNext()) {
                entry = (Map.Entry)object3.next();
                object2 = zzc.zzam((Integer)entry.getKey());
                if (TextUtils.isEmpty((CharSequence)object2)) continue;
                hashMap.put((String)object2, (String)((Object)entry.getValue()));
            }
        }
        if ((object3 = object.zza(zzrh.class)) != null) {
            object3 = object3.zzmH().entrySet().iterator();
            while (object3.hasNext()) {
                entry = object3.next();
                object2 = zzc.zzao(entry.getKey());
                if (TextUtils.isEmpty((CharSequence)object2)) continue;
                hashMap.put((String)object2, zzb.zzb(entry.getValue()));
            }
        }
        if ((entry = object.zza(zzrk.class)) != null) {
            object3 = entry.zzmO();
            if (object3 != null) {
                for (Map.Entry entry2 : object3.build().entrySet()) {
                    object3 = ((String)entry2.getKey()).startsWith("&") ? ((String)entry2.getKey()).substring(1) : (String)entry2.getKey();
                    hashMap.put((String)object3, (String)entry2.getValue());
                }
            }
            object3 = entry.zzmR().iterator();
            int n = 1;
            while (object3.hasNext()) {
                hashMap.putAll(object3.next().zzbL(zzc.zzas(n)));
                ++n;
            }
            object3 = entry.zzmP().iterator();
            n = 1;
            while (object3.hasNext()) {
                hashMap.putAll(((Product)object3.next()).zzbL(zzc.zzaq(n)));
                ++n;
            }
            entry = entry.zzmQ().entrySet().iterator();
            n = 1;
            while (entry.hasNext()) {
                object2 = (Map.Entry)entry.next();
                object3 = (List)object2.getValue();
                String string = zzc.zzav(n);
                Iterator iterator = object3.iterator();
                int n2 = 1;
                while (iterator.hasNext()) {
                    Product product = (Product)iterator.next();
                    object3 = String.valueOf(string);
                    String string2 = String.valueOf(zzc.zzat(n2));
                    object3 = string2.length() != 0 ? object3.concat(string2) : new String((String)object3);
                    hashMap.putAll(product.zzbL((String)object3));
                    ++n2;
                }
                if (!TextUtils.isEmpty((CharSequence)((CharSequence)object2.getKey()))) {
                    object3 = String.valueOf(string);
                    String string3 = String.valueOf("nm");
                    object3 = string3.length() != 0 ? object3.concat(string3) : new String((String)object3);
                    hashMap.put((String)object3, (String)object2.getKey());
                }
                ++n;
            }
        }
        if ((object3 = object.zza(zzrj.class)) != null) {
            zzb.zzb(hashMap, "ul", object3.getLanguage());
            zzb.zza(hashMap, "sd", object3.zzmJ());
            zzb.zza(hashMap, "sr", object3.zzmK(), object3.zzmL());
            zzb.zza(hashMap, "vp", object3.zzmM(), object3.zzmN());
        }
        if ((object = object.zza(zzre.class)) != null) {
            zzb.zzb(hashMap, "an", object.zzmx());
            zzb.zzb(hashMap, "aid", object.zzjI());
            zzb.zzb(hashMap, "aiid", object.zzmz());
            zzb.zzb(hashMap, "av", object.zzmy());
        }
        return hashMap;
    }

    private static String zzi(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            if (!TextUtils.isEmpty((CharSequence)(object = (String)object))) {
                return object;
            }
            return null;
        }
        if (object instanceof Double) {
            if ((object = (Double)object).doubleValue() != 0.0) {
                return zzb.zzb(object.doubleValue());
            }
            return null;
        }
        if (object instanceof Boolean) {
            if (object != Boolean.FALSE) {
                return "1";
            }
            return null;
        }
        return String.valueOf(object);
    }

    @Override
    public void zzb(zze object) {
        zzac.zzw(object);
        zzac.zzb(object.zzmg(), (Object)"Can't deliver not submitted measurement");
        zzac.zzdo("deliver should be called on worker thread");
        Object object2 = object.zzmb();
        Object object3 = object2.zzb(zzrn.class);
        if (TextUtils.isEmpty((CharSequence)object3.zzmT())) {
            this.zznr().zzg(zzb.zzc((zze)object2), "Ignoring measurement without type");
            return;
        }
        if (TextUtils.isEmpty((CharSequence)object3.zzlX())) {
            this.zznr().zzg(zzb.zzc((zze)object2), "Ignoring measurement without client id");
            return;
        }
        if (this.zzaam.zznE().getAppOptOut()) {
            return;
        }
        double d = object3.zzmY();
        if (zztg.zza(d, object3.zzlX())) {
            this.zzb("Sampling enabled. Hit sampled out. sampling rate", d);
            return;
        }
        object2 = zzb.zzc((zze)object2);
        object2.put("v", "1");
        object2.put("_v", zzrv.zzacP);
        object2.put("tid", this.zzaar);
        if (this.zzaam.zznE().isDryRunEnabled()) {
            this.zzc("Dry run is enabled. GoogleAnalytics would have sent", zzb.zzQ((Map<String, String>)object2));
            return;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        zztg.zzc(hashMap, "uid", object3.getUserId());
        zzre zzre2 = object.zza(zzre.class);
        if (zzre2 != null) {
            zztg.zzc(hashMap, "an", zzre2.zzmx());
            zztg.zzc(hashMap, "aid", zzre2.zzjI());
            zztg.zzc(hashMap, "av", zzre2.zzmy());
            zztg.zzc(hashMap, "aiid", zzre2.zzmz());
        }
        object3 = new zzry(0L, object3.zzlX(), this.zzaar, TextUtils.isEmpty((CharSequence)object3.zzmU()) ^ true, 0L, hashMap);
        object2.put("_s", String.valueOf(this.zzlZ().zza((zzry)object3)));
        object = new zzst(this.zznr(), (Map<String, String>)object2, object.zzme(), true);
        this.zzlZ().zza((zzst)object);
    }

    @Override
    public Uri zzlQ() {
        return this.zzaas;
    }
}
