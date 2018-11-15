/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 */
package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class zzo {
    public static long zza(InputStream inputStream, OutputStream outputStream) throws IOException {
        return zzo.zza(inputStream, outputStream, false);
    }

    public static long zza(InputStream inputStream, OutputStream outputStream, boolean bl) throws IOException {
        return zzo.zza(inputStream, outputStream, bl, 1024);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static long zza(InputStream inputStream, OutputStream outputStream, boolean bl, int n) throws IOException {
        long l;
        block4 : {
            byte[] arrby = new byte[n];
            l = 0L;
            try {
                int n2;
                while ((n2 = inputStream.read(arrby, 0, n)) != -1) {
                    long l2 = n2;
                    outputStream.write(arrby, 0, n2);
                    l += l2;
                }
                if (!bl) break block4;
            }
            catch (Throwable throwable) {
                if (bl) {
                    zzo.zzb(inputStream);
                    zzo.zzb(outputStream);
                }
                throw throwable;
            }
            zzo.zzb(inputStream);
            zzo.zzb(outputStream);
        }
        return l;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void zza(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) return;
        try {
            parcelFileDescriptor.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static byte[] zza(InputStream inputStream, boolean bl) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        zzo.zza(inputStream, byteArrayOutputStream, bl);
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void zzb(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static byte[] zzk(InputStream inputStream) throws IOException {
        return zzo.zza(inputStream, true);
    }
}
