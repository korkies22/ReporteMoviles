/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.os.SystemClock;
import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzs;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class zzv
implements com.google.android.gms.internal.zzb {
    private final Map<String, zza> zzav = new LinkedHashMap<String, zza>(16, 0.75f, true);
    private long zzaw = 0L;
    private final File zzax;
    private final int zzay;

    public zzv(File file) {
        this(file, 5242880);
    }

    public zzv(File file, int n) {
        this.zzax = file;
        this.zzay = n;
    }

    private void removeEntry(String string) {
        zza zza2 = this.zzav.get(string);
        if (zza2 != null) {
            this.zzaw -= zza2.zzaz;
            this.zzav.remove(string);
        }
    }

    private static int zza(InputStream inputStream) throws IOException {
        int n = inputStream.read();
        if (n == -1) {
            throw new EOFException();
        }
        return n;
    }

    static void zza(OutputStream outputStream, int n) throws IOException {
        outputStream.write(n >> 0 & 255);
        outputStream.write(n >> 8 & 255);
        outputStream.write(n >> 16 & 255);
        outputStream.write(n >> 24 & 255);
    }

    static void zza(OutputStream outputStream, long l) throws IOException {
        outputStream.write((byte)(l >>> 0));
        outputStream.write((byte)(l >>> 8));
        outputStream.write((byte)(l >>> 16));
        outputStream.write((byte)(l >>> 24));
        outputStream.write((byte)(l >>> 32));
        outputStream.write((byte)(l >>> 40));
        outputStream.write((byte)(l >>> 48));
        outputStream.write((byte)(l >>> 56));
    }

    static void zza(OutputStream outputStream, String arrby) throws IOException {
        arrby = arrby.getBytes("UTF-8");
        zzv.zza(outputStream, (long)arrby.length);
        outputStream.write(arrby, 0, arrby.length);
    }

    private void zza(String string, zza zza2) {
        if (!this.zzav.containsKey(string)) {
            this.zzaw += zza2.zzaz;
        } else {
            zza zza3 = this.zzav.get(string);
            this.zzaw += zza2.zzaz - zza3.zzaz;
        }
        this.zzav.put(string, zza2);
    }

    static void zza(Map<String, String> object, OutputStream outputStream) throws IOException {
        if (object != null) {
            zzv.zza(outputStream, object.size());
            for (Map.Entry entry : object.entrySet()) {
                zzv.zza(outputStream, (String)entry.getKey());
                zzv.zza(outputStream, (String)entry.getValue());
            }
        } else {
            zzv.zza(outputStream, 0);
        }
    }

    private static byte[] zza(InputStream object, int n) throws IOException {
        int n2;
        int n3;
        byte[] arrby = new byte[n];
        for (n2 = 0; n2 < n && (n3 = object.read(arrby, n2, n - n2)) != -1; n2 += n3) {
        }
        if (n2 != n) {
            object = new StringBuilder(50);
            object.append("Expected ");
            object.append(n);
            object.append(" bytes, read ");
            object.append(n2);
            object.append(" bytes");
            throw new IOException(object.toString());
        }
        return arrby;
    }

    static int zzb(InputStream inputStream) throws IOException {
        int n = zzv.zza(inputStream);
        int n2 = zzv.zza(inputStream);
        int n3 = zzv.zza(inputStream);
        return zzv.zza(inputStream) << 24 | (n << 0 | 0 | n2 << 8 | n3 << 16);
    }

    static long zzc(InputStream inputStream) throws IOException {
        return 0L | ((long)zzv.zza(inputStream) & 255L) << 0 | ((long)zzv.zza(inputStream) & 255L) << 8 | ((long)zzv.zza(inputStream) & 255L) << 16 | ((long)zzv.zza(inputStream) & 255L) << 24 | ((long)zzv.zza(inputStream) & 255L) << 32 | ((long)zzv.zza(inputStream) & 255L) << 40 | ((long)zzv.zza(inputStream) & 255L) << 48 | ((long)zzv.zza(inputStream) & 255L) << 56;
    }

    private void zzc(int n) {
        long l = this.zzaw;
        long l2 = n;
        if (l + l2 < (long)this.zzay) {
            return;
        }
        if (zzs.DEBUG) {
            zzs.zza("Pruning old cache entries.", new Object[0]);
        }
        long l3 = this.zzaw;
        l = SystemClock.elapsedRealtime();
        Iterator<Map.Entry<String, zza>> iterator = this.zzav.entrySet().iterator();
        n = 0;
        while (iterator.hasNext()) {
            zza zza2 = iterator.next().getValue();
            if (this.zzf(zza2.zzaA).delete()) {
                this.zzaw -= zza2.zzaz;
            } else {
                zzs.zzb("Could not delete cache entry for key=%s, filename=%s", zza2.zzaA, this.zze(zza2.zzaA));
            }
            iterator.remove();
            ++n;
            if ((float)(this.zzaw + l2) >= (float)this.zzay * 0.9f) continue;
            break;
        }
        if (zzs.DEBUG) {
            zzs.zza("pruned %d files, %d bytes, %d ms", n, this.zzaw - l3, SystemClock.elapsedRealtime() - l);
        }
    }

    static String zzd(InputStream inputStream) throws IOException {
        return new String(zzv.zza(inputStream, (int)zzv.zzc(inputStream)), "UTF-8");
    }

    private String zze(String string) {
        int n = string.length() / 2;
        String string2 = String.valueOf(String.valueOf(string.substring(0, n).hashCode()));
        if ((string = String.valueOf(String.valueOf(string.substring(n).hashCode()))).length() != 0) {
            return string2.concat(string);
        }
        return new String(string2);
    }

    static Map<String, String> zze(InputStream inputStream) throws IOException {
        int n = zzv.zzb(inputStream);
        Map<String, String> map = n == 0 ? Collections.emptyMap() : new HashMap(n);
        for (int i = 0; i < n; ++i) {
            map.put(zzv.zzd(inputStream).intern(), zzv.zzd(inputStream).intern());
        }
        return map;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void initialize() {
        // MONITORENTER : this
        bl = this.zzax.exists();
        n = 0;
        if (!bl) {
            if (!this.zzax.mkdirs()) {
                zzs.zzc("Unable to create cache dir %s", new Object[]{this.zzax.getAbsolutePath()});
            }
            // MONITOREXIT : this
            return;
        }
        arrfile = this.zzax.listFiles();
        if (arrfile == null) {
            // MONITOREXIT : this
            return;
        }
        n2 = arrfile.length;
        do lbl-1000: // 2 sources:
        {
            block23 : {
                if (n >= n2) {
                    // MONITOREXIT : this
                    return;
                }
                file = arrfile[n];
                bufferedInputStream = null;
                object2 = null;
                object = new BufferedInputStream(new FileInputStream(file));
                object2 = zza.zzf((InputStream)object);
                object2.zzaz = file.length();
                this.zza(object2.zzaA, (zza)object2);
                object.close();
                catch (Throwable throwable) {
                    object2 = object;
                    object = throwable;
                    break block23;
                }
                catch (IOException iOException) {
                    break block24;
                }
                catch (Throwable throwable) {}
            }
            if (object2 == null) throw object;
            object2.close();
            break;
        } while (true);
        catch (IOException iOException) {
            throw object;
        }
        {
            block25 : {
                block24 : {
                    throw object;
                    catch (IOException iOException) {
                        object = bufferedInputStream;
                    }
                }
                if (file != null) {
                    object2 = object;
                    {
                        file.delete();
                    }
                }
                if (object != null) {
                    object.close();
                }
                break block25;
                catch (IOException iOException) {}
            }
            ++n;
            ** while (true)
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void remove(String string) {
        synchronized (this) {
            boolean bl = this.zzf(string).delete();
            this.removeEntry(string);
            if (!bl) {
                zzs.zzb("Could not delete cache entry for key=%s, filename=%s", string, this.zze(string));
            }
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public zzb.zza zza(String string) {
        zzb zzb2;
        void var1_4;
        block19 : {
            block20 : {
                void var4_11;
                zzb zzb3;
                File file;
                block18 : {
                    // MONITORENTER : this
                    Object object = this.zzav.get(string);
                    if (object == null) {
                        // MONITOREXIT : this
                        return null;
                    }
                    file = this.zzf(string);
                    zzb2 = zzb3 = new zzb(new FileInputStream(file));
                    zza.zzf(zzb3);
                    zzb2 = zzb3;
                    object = object.zzb(zzv.zza(zzb3, (int)(file.length() - (long)zzb3.zzaB)));
                    try {
                        zzb3.close();
                        // MONITOREXIT : this
                        return object;
                    }
                    catch (IOException iOException) {
                        return null;
                    }
                    catch (IOException iOException) {
                        break block18;
                    }
                    catch (Throwable throwable) {
                        zzb2 = null;
                        break block19;
                    }
                    catch (IOException iOException) {
                        zzb3 = null;
                    }
                }
                zzb2 = zzb3;
                zzs.zzb("%s: %s", file.getAbsolutePath(), var4_11.toString());
                zzb2 = zzb3;
                this.remove(string);
                if (zzb3 == null) break block20;
                try {
                    zzb3.close();
                    return null;
                }
                catch (IOException iOException) {
                    return null;
                }
            }
            // MONITOREXIT : this
            return null;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (zzb2 == null) throw var1_4;
        try {
            zzb2.close();
            throw var1_4;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void zza(String string, zzb.zza zza2) {
        synchronized (this) {
            void var2_3;
            this.zzc(var2_3.data.length);
            File file = this.zzf(string);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                zza zza3 = new zza(string, (zzb.zza)var2_3);
                if (!zza3.zza(fileOutputStream)) {
                    fileOutputStream.close();
                    zzs.zzb("Failed to write header for %s", file.getAbsolutePath());
                    throw new IOException();
                }
                fileOutputStream.write(var2_3.data);
                fileOutputStream.close();
                this.zza(string, zza3);
                return;
            }
            catch (IOException iOException) {}
            if (!file.delete()) {
                zzs.zzb("Could not clean up file %s", file.getAbsolutePath());
            }
            return;
        }
    }

    public File zzf(String string) {
        return new File(this.zzax, this.zze(string));
    }

    static class zza {
        public String zza;
        public String zzaA;
        public long zzaz;
        public long zzb;
        public long zzc;
        public long zzd;
        public long zze;
        public Map<String, String> zzf;

        private zza() {
        }

        public zza(String string, zzb.zza zza2) {
            this.zzaA = string;
            this.zzaz = zza2.data.length;
            this.zza = zza2.zza;
            this.zzb = zza2.zzb;
            this.zzc = zza2.zzc;
            this.zzd = zza2.zzd;
            this.zze = zza2.zze;
            this.zzf = zza2.zzf;
        }

        public static zza zzf(InputStream inputStream) throws IOException {
            zza zza2 = new zza();
            if (zzv.zzb(inputStream) != 538247942) {
                throw new IOException();
            }
            zza2.zzaA = zzv.zzd(inputStream);
            zza2.zza = zzv.zzd(inputStream);
            if (zza2.zza.equals("")) {
                zza2.zza = null;
            }
            zza2.zzb = zzv.zzc(inputStream);
            zza2.zzc = zzv.zzc(inputStream);
            zza2.zzd = zzv.zzc(inputStream);
            zza2.zze = zzv.zzc(inputStream);
            zza2.zzf = zzv.zze(inputStream);
            return zza2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean zza(OutputStream outputStream) {
            try {
                zzv.zza(outputStream, 538247942);
                zzv.zza(outputStream, this.zzaA);
                String string = this.zza == null ? "" : this.zza;
                zzv.zza(outputStream, string);
                zzv.zza(outputStream, this.zzb);
                zzv.zza(outputStream, this.zzc);
                zzv.zza(outputStream, this.zzd);
                zzv.zza(outputStream, this.zze);
                zzv.zza(this.zzf, outputStream);
                outputStream.flush();
                return true;
            }
            catch (IOException iOException) {
                zzs.zzb("%s", iOException.toString());
                return false;
            }
        }

        public zzb.zza zzb(byte[] arrby) {
            zzb.zza zza2 = new zzb.zza();
            zza2.data = arrby;
            zza2.zza = this.zza;
            zza2.zzb = this.zzb;
            zza2.zzc = this.zzc;
            zza2.zzd = this.zzd;
            zza2.zze = this.zze;
            zza2.zzf = this.zzf;
            return zza2;
        }
    }

    private static class zzb
    extends FilterInputStream {
        private int zzaB = 0;

        private zzb(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public int read() throws IOException {
            int n = super.read();
            if (n != -1) {
                ++this.zzaB;
            }
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if ((n = super.read(arrby, n, n2)) != -1) {
                this.zzaB += n;
            }
            return n;
        }
    }

}
