/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ServiceInfo
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.internal.zzrf;
import com.google.android.gms.internal.zzsx;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class zztg {
    private static final char[] zzafT = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String zzW(boolean bl) {
        if (bl) {
            return "1";
        }
        return "0";
    }

    public static double zza(String string, double d) {
        if (string == null) {
            return d;
        }
        try {
            double d2 = Double.parseDouble(string);
            return d2;
        }
        catch (NumberFormatException numberFormatException) {
            return d;
        }
    }

    public static zzrf zza(zzsx object, String object2) {
        zzac.zzw(object);
        if (TextUtils.isEmpty((CharSequence)object2)) {
            return null;
        }
        new HashMap();
        try {
            object2 = String.valueOf(object2);
            object2 = object2.length() != 0 ? "?".concat((String)object2) : new String("?");
            object2 = zzn.zza(new URI((String)object2), "UTF-8");
            object = new zzrf();
            object.zzbt((String)object2.get("utm_content"));
            object.zzbr((String)object2.get("utm_medium"));
            object.setName((String)object2.get("utm_campaign"));
            object.zzbq((String)object2.get("utm_source"));
            object.zzbs((String)object2.get("utm_term"));
            object.zzbu((String)object2.get("utm_id"));
            object.zzbv((String)object2.get("anid"));
            object.zzbw((String)object2.get("gclid"));
            object.zzbx((String)object2.get("dclid"));
            object.zzby((String)object2.get("aclid"));
            return object;
        }
        catch (URISyntaxException uRISyntaxException) {
            object.zzd("No valid campaign data found", uRISyntaxException);
            return null;
        }
    }

    public static String zza(Locale locale) {
        if (locale == null) {
            return null;
        }
        String string = locale.getLanguage();
        if (TextUtils.isEmpty((CharSequence)string)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string.toLowerCase());
        if (!TextUtils.isEmpty((CharSequence)locale.getCountry())) {
            stringBuilder.append("-");
            stringBuilder.append(locale.getCountry().toLowerCase());
        }
        return stringBuilder.toString();
    }

    public static void zza(Map<String, String> map, String string, Map<String, String> map2) {
        zztg.zzc(map, string, map2.get(string));
    }

    public static boolean zza(double d, String string) {
        if (d > 0.0) {
            if (d >= 100.0) {
                return false;
            }
            if ((double)(zztg.zzch(string) % 10000) >= d * 100.0) {
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean zza(Context context, String string, boolean bl) {
        block5 : {
            try {
                context = context.getPackageManager().getReceiverInfo(new ComponentName(context, string), 2);
                if (context == null) break block5;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return false;
            }
            if (!context.enabled) break block5;
            if (!bl) return true;
            bl = context.exported;
            if (!bl) break block5;
            return true;
        }
        do {
            return false;
            break;
        } while (true);
    }

    public static void zzb(Map<String, String> map, String string, boolean bl) {
        if (!map.containsKey(string)) {
            String string2 = bl ? "1" : "0";
            map.put(string, string2);
        }
    }

    public static void zzc(Map<String, String> map, String string, String string2) {
        if (string2 != null && !map.containsKey(string)) {
            map.put(string, string2);
        }
    }

    public static Map<String, String> zzcd(String string) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] arrstring = string.split("&");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            String[] arrstring2 = arrstring[i].split("=", 3);
            Object var3_5 = null;
            if (arrstring2.length > 1) {
                String string2 = arrstring2[0];
                string = TextUtils.isEmpty((CharSequence)arrstring2[1]) ? null : arrstring2[1];
                hashMap.put(string2, string);
                if (arrstring2.length != 3 || TextUtils.isEmpty((CharSequence)arrstring2[1]) || hashMap.containsKey(arrstring2[1])) continue;
                string2 = arrstring2[1];
                string = TextUtils.isEmpty((CharSequence)arrstring2[2]) ? var3_5 : arrstring2[2];
                hashMap.put(string2, string);
                continue;
            }
            if (arrstring2.length != 1 || arrstring2[0].length() == 0) continue;
            hashMap.put(arrstring2[0], null);
        }
        return hashMap;
    }

    public static long zzce(String string) {
        if (string == null) {
            return 0L;
        }
        try {
            long l = Long.parseLong(string);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return 0L;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String zzcf(String object) {
        Object object2;
        if (TextUtils.isEmpty(object)) {
            return null;
        }
        String[] arrstring = object;
        if (object.contains((CharSequence)"?")) {
            object2 = object.split("[\\?]");
            arrstring = object;
            if (((String[])object2).length > 1) {
                arrstring = object2[1];
            }
        }
        if (arrstring.contains("%3D")) {
            object = URLDecoder.decode(arrstring, "UTF-8");
        } else {
            object = arrstring;
            if (!arrstring.contains("=")) {
                return null;
            }
        }
        object = zztg.zzcd((String)((Object)object));
        arrstring = new String[11];
        int i = 0;
        arrstring[0] = "dclid";
        arrstring[1] = "utm_source";
        arrstring[2] = "gclid";
        arrstring[3] = "aclid";
        arrstring[4] = "utm_campaign";
        arrstring[5] = "utm_medium";
        arrstring[6] = "utm_term";
        arrstring[7] = "utm_content";
        arrstring[8] = "utm_id";
        arrstring[9] = "anid";
        arrstring[10] = "gmob_t";
        object2 = new StringBuilder();
        while (i < 11) {
            if (!TextUtils.isEmpty((CharSequence)object.get(arrstring[i]))) {
                if (object2.length() > 0) {
                    object2.append("&");
                }
                object2.append(arrstring[i]);
                object2.append("=");
                object2.append(object.get(arrstring[i]));
            }
            ++i;
        }
        return object2.toString();
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MessageDigest zzcg(String string) {
        int n = 0;
        do {
            if (n >= 2) {
                return null;
            }
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(string);
                if (messageDigest != null) {
                    return messageDigest;
                }
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
            ++n;
        } while (true);
    }

    public static int zzch(String string) {
        boolean bl = TextUtils.isEmpty((CharSequence)string);
        int n = 1;
        if (!bl) {
            int n2 = string.length() - 1;
            int n3 = 0;
            do {
                n = n3;
                if (n2 < 0) break;
                n = string.charAt(n2);
                n = (n3 << 6 & 268435455) + n + (n << 14);
                int n4 = 266338304 & n;
                n3 = n;
                if (n4 != 0) {
                    n3 = n4 >> 21 ^ n;
                }
                --n2;
            } while (true);
        }
        return n;
    }

    public static boolean zzci(String string) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return true;
        }
        return string.startsWith("http:") ^ true;
    }

    public static void zzd(Map<String, String> map, String string, String string2) {
        if (string2 != null && TextUtils.isEmpty((CharSequence)map.get(string))) {
            map.put(string, string2);
        }
    }

    public static boolean zzg(String string, boolean bl) {
        if (string != null) {
            if (!(string.equalsIgnoreCase("true") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("1"))) {
                if (string.equalsIgnoreCase("false") || string.equalsIgnoreCase("no") || string.equalsIgnoreCase("0")) {
                    return false;
                }
            } else {
                return true;
            }
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean zzr(Context context, String string) {
        block4 : {
            try {
                context = context.getPackageManager().getServiceInfo(new ComponentName(context, string), 4);
                if (context == null) break block4;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return false;
            }
            boolean bl = context.enabled;
            if (!bl) break block4;
            return true;
        }
        do {
            return false;
            break;
        } while (true);
    }
}
