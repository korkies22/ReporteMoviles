/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class zzs {
    public static boolean DEBUG = false;
    public static String TAG = "Volley";

    static {
        DEBUG = Log.isLoggable((String)TAG, (int)2);
    }

    public static /* varargs */ void zza(String string, Object ... arrobject) {
        if (DEBUG) {
            Log.v((String)TAG, (String)zzs.zzd(string, arrobject));
        }
    }

    public static /* varargs */ void zza(Throwable throwable, String string, Object ... arrobject) {
        Log.e((String)TAG, (String)zzs.zzd(string, arrobject), (Throwable)throwable);
    }

    public static /* varargs */ void zzb(String string, Object ... arrobject) {
        Log.d((String)TAG, (String)zzs.zzd(string, arrobject));
    }

    public static /* varargs */ void zzc(String string, Object ... arrobject) {
        Log.e((String)TAG, (String)zzs.zzd(string, arrobject));
    }

    private static /* varargs */ String zzd(String string, Object ... object) {
        if (object != null) {
            string = String.format(Locale.US, string, (Object[])object);
        }
        Object object2 = new Throwable().fillInStackTrace().getStackTrace();
        String string2 = "<unknown>";
        int n = 2;
        do {
            object = string2;
            if (n >= ((StackTraceElement[])object2).length) break;
            if (!object2[n].getClass().equals(zzs.class)) {
                object = object2[n].getClassName();
                object = object.substring(object.lastIndexOf(46) + 1);
                object = object.substring(object.lastIndexOf(36) + 1);
                string2 = String.valueOf(object2[n].getMethodName());
                object2 = new StringBuilder(String.valueOf(object).length() + 1 + String.valueOf(string2).length());
                object2.append((String)object);
                object2.append(".");
                object2.append(string2);
                object = object2.toString();
                break;
            }
            ++n;
        } while (true);
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), object, string);
    }

    static class zza {
        public static final boolean zzai = zzs.DEBUG;
        private final List<zza$zza> zzaj = new ArrayList<zza$zza>();
        private boolean zzak = false;

        zza() {
        }

        private long getTotalDuration() {
            if (this.zzaj.size() == 0) {
                return 0L;
            }
            long l = this.zzaj.get((int)0).time;
            return this.zzaj.get((int)(this.zzaj.size() - 1)).time - l;
        }

        protected void finalize() throws Throwable {
            if (!this.zzak) {
                this.zzd("Request on the loose");
                zzs.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        public void zza(String string, long l) {
            synchronized (this) {
                if (this.zzak) {
                    throw new IllegalStateException("Marker added to finished log");
                }
                this.zzaj.add(new zza$zza(string, l, SystemClock.elapsedRealtime()));
                return;
            }
        }

        public void zzd(String object) {
            synchronized (this) {
                long l;
                block5 : {
                    this.zzak = true;
                    l = this.getTotalDuration();
                    if (l > 0L) break block5;
                    return;
                }
                long l2 = this.zzaj.get((int)0).time;
                zzs.zzb("(%-4d ms) %s", l, object);
                for (zza$zza zza$zza2 : this.zzaj) {
                    l = zza$zza2.time;
                    zzs.zzb("(+%-4d) [%2d] %s", l - l2, zza$zza2.zzal, zza$zza2.name);
                    l2 = l;
                }
                return;
            }
        }
    }

    private static class zza$zza {
        public final String name;
        public final long time;
        public final long zzal;

        public zza$zza(String string, long l, long l2) {
            this.name = string;
            this.zzal = l;
            this.time = l2;
        }
    }

}
