/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Handler
 */
package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class zzbii {
    public static final Uri CONTENT_URI = Uri.parse((String)"content://com.google.android.gsf.gservices");
    public static final Uri zzbTL = Uri.parse((String)"content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzbTM = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzbTN = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzbTO = new AtomicBoolean();
    static HashMap<String, String> zzbTP;
    private static Object zzbTQ;
    private static boolean zzbTR;
    static String[] zzbTS;

    static {
        zzbTS = new String[0];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static long getLong(ContentResolver object, String string, long l) {
        object = zzbii.getString((ContentResolver)object, string);
        long l2 = l;
        if (object == null) return l2;
        try {
            return Long.parseLong((String)object);
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
    }

    @Deprecated
    public static String getString(ContentResolver contentResolver, String string) {
        return zzbii.zza(contentResolver, string, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String zza(ContentResolver object, String string, String object2) {
        synchronized (zzbii.class) {
            zzbii.zza((ContentResolver)object);
            Object object3 = zzbTQ;
            if (zzbTP.containsKey(string)) {
                object = zzbTP.get(string);
                if (object == null) return object2;
                return object;
            }
            Object object4 = zzbTS;
            int n = ((String[])object4).length;
            int n2 = 0;
            do {
                block16 : {
                    block17 : {
                        Cursor cursor;
                        Throwable throwable2;
                        block18 : {
                            block14 : {
                                block13 : {
                                    block15 : {
                                        if (n2 >= n) break block15;
                                        if (!string.startsWith(object4[n2])) break block16;
                                        if (zzbTR) {
                                            if (!zzbTP.isEmpty()) return object2;
                                        }
                                        break block17;
                                    }
                                    // MONITOREXIT [4, 6] lbl19 : MonitorExitStatement: MONITOREXIT : com.google.android.gms.internal.zzbii.class
                                    cursor = object.query(CONTENT_URI, null, null, new String[]{string}, null);
                                    if (cursor != null) {
                                        if (!cursor.moveToFirst()) break block13;
                                        object = object4 = cursor.getString(1);
                                        if (object4 != null) {
                                            object = object4;
                                            if (object4.equals(object2)) {
                                                object = object2;
                                            }
                                        }
                                        zzbii.zza(object3, string, (String)object);
                                        if (object != null) {
                                            object2 = object;
                                        }
                                        if (cursor == null) return object2;
                                        cursor.close();
                                        return object2;
                                    }
                                }
                                try {
                                    zzbii.zza(object3, string, null);
                                    if (cursor == null) return object2;
                                    break block14;
                                }
                                catch (Throwable throwable2) {}
                                break block18;
                            }
                            cursor.close();
                            return object2;
                        }
                        if (cursor == null) throw throwable2;
                        cursor.close();
                        throw throwable2;
                    }
                    zzbii.zzc((ContentResolver)object, zzbTS);
                    if (!zzbTP.containsKey(string)) return object2;
                    object = zzbTP.get(string);
                    if (object == null) return object2;
                    return object;
                }
                ++n2;
            } while (true);
        }
    }

    public static /* varargs */ Map<String, String> zza(ContentResolver contentResolver, String ... object) {
        contentResolver = contentResolver.query(zzbTL, null, null, (String[])object, null);
        object = new TreeMap();
        if (contentResolver == null) {
            return object;
        }
        try {
            while (contentResolver.moveToNext()) {
                object.put(contentResolver.getString(0), contentResolver.getString(1));
            }
            return object;
        }
        finally {
            contentResolver.close();
        }
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzbTP == null) {
            zzbTO.set(false);
            zzbTP = new HashMap();
            zzbTQ = new Object();
            zzbTR = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new ContentObserver(null){

                public void onChange(boolean bl) {
                    zzbTO.set(true);
                }
            });
            return;
        }
        if (zzbTO.getAndSet(false)) {
            zzbTP.clear();
            zzbTQ = new Object();
            zzbTR = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void zza(Object object, String string, String string2) {
        synchronized (zzbii.class) {
            if (object == zzbTQ) {
                zzbTP.put(string, string2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static /* varargs */ void zzb(ContentResolver contentResolver, String ... arrstring) {
        if (arrstring.length == 0) {
            return;
        }
        synchronized (zzbii.class) {
            block4 : {
                block5 : {
                    block3 : {
                        zzbii.zza(contentResolver);
                        arrstring = zzbii.zzk(arrstring);
                        if (!zzbTR || zzbTP.isEmpty()) break block3;
                        if (arrstring.length == 0) break block4;
                        break block5;
                    }
                    arrstring = zzbTS;
                }
                zzbii.zzc(contentResolver, arrstring);
            }
            return;
        }
    }

    private static void zzc(ContentResolver contentResolver, String[] arrstring) {
        zzbTP.putAll(zzbii.zza(contentResolver, arrstring));
        zzbTR = true;
    }

    private static String[] zzk(String[] arrstring) {
        HashSet<String> hashSet = new HashSet<String>((zzbTS.length + arrstring.length) * 4 / 3 + 1);
        hashSet.addAll(Arrays.asList(zzbTS));
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : arrstring) {
            if (!hashSet.add(string)) continue;
            arrayList.add(string);
        }
        if (arrayList.isEmpty()) {
            return new String[0];
        }
        zzbTS = hashSet.toArray(new String[hashSet.size()]);
        return arrayList.toArray(new String[arrayList.size()]);
    }

}
