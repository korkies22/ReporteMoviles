/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Process
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 */
package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import com.google.android.gms.common.util.zzo;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class zzt {
    private static String zzaGZ;
    private static final int zzaHa;

    static {
        zzaHa = Process.myPid();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static String zzdk(int var0) {
        block10 : {
            var3_1 = null;
            if (var0 <= 0) {
                return null;
            }
            var2_2 = StrictMode.allowThreadDiskReads();
            var1_8 = new StringBuilder(25);
            var1_8.append("/proc/");
            var1_8.append(var0);
            var1_8.append("/cmdline");
            var1_8 = new BufferedReader(new FileReader(var1_8.toString()));
            try {
                StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)var2_2);
                var2_3 = var1_8.readLine().trim();
            }
            catch (Throwable var2_4) {
                ** GOTO lbl25
            }
            zzo.zzb((Closeable)var1_8);
            return var2_3;
            catch (Throwable var1_9) {
                try {
                    StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)var2_2);
                    throw var1_9;
                }
                catch (Throwable var2_5) {
                    var1_8 = var3_1;
                }
lbl25: // 2 sources:
                zzo.zzb(var1_8);
                throw var2_6;
                catch (IOException var1_10) {}
            }
            var1_8 = null;
            break block10;
            catch (IOException var2_7) {}
        }
        zzo.zzb(var1_8);
        return null;
    }

    public static String zzyK() {
        if (zzaGZ == null) {
            zzaGZ = zzt.zzdk(zzaHa);
        }
        return zzaGZ;
    }
}
