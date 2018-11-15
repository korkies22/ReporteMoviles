/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class zzse
extends zzru {
    private volatile String zzacg;
    private Future<String> zzadR;

    protected zzse(zzrw zzrw2) {
        super(zzrw2);
    }

    private String zzos() {
        String string;
        String string2 = string = this.zzot();
        try {
            if (!this.zzq(this.zznt().getContext(), string)) {
                string2 = "0";
            }
            return string2;
        }
        catch (Exception exception) {
            this.zze("Error saving clientId file", exception);
            return "0";
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean zzq(Context object, String string) {
        Object object2;
        block18 : {
            block20 : {
                Object var5_14;
                block19 : {
                    Object context;
                    zzac.zzdv(string);
                    zzac.zzdo("ClientId should be saved from worker thread");
                    Object var4_13 = null;
                    var5_14 = null;
                    object2 = context = null;
                    this.zza("Storing clientId", string);
                    object2 = context;
                    object = object.openFileOutput("gaClientId", 0);
                    object.write(string.getBytes());
                    if (object == null) return true;
                    try {
                        object.close();
                        return true;
                    }
                    catch (IOException iOException) {
                        this.zze("Failed to close clientId writing stream", iOException);
                    }
                    return true;
                    catch (Throwable throwable) {
                        object2 = object;
                        object = throwable;
                        break block18;
                    }
                    catch (IOException iOException) {
                        break block19;
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        break block20;
                    }
                    catch (Throwable throwable) {
                        break block18;
                    }
                    catch (IOException iOException) {
                        object = var4_13;
                    }
                }
                object2 = object;
                {
                    this.zze("Error writing to clientId file", string);
                    if (object == null) return false;
                }
                try {
                    object.close();
                    return false;
                }
                catch (IOException iOException) {
                    this.zze("Failed to close clientId writing stream", iOException);
                }
                return false;
                catch (FileNotFoundException fileNotFoundException) {
                    object = var5_14;
                }
            }
            object2 = object;
            {
                this.zze("Error creating clientId file", string);
                if (object == null) return false;
            }
            try {
                object.close();
                return false;
            }
            catch (IOException iOException) {
                this.zze("Failed to close clientId writing stream", iOException);
            }
            return false;
        }
        if (object2 == null) throw object;
        try {
            object2.close();
            throw object;
        }
        catch (IOException iOException) {
            this.zze("Failed to close clientId writing stream", iOException);
        }
        throw object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected String zzY(Context context) {
        FileInputStream fileInputStream;
        block27 : {
            void var1_8;
            FileInputStream fileInputStream2;
            block26 : {
                void var5_20;
                block25 : {
                    int n;
                    byte[] arrby;
                    block24 : {
                        zzac.zzdo("ClientId should be loaded from worker thread");
                        fileInputStream2 = fileInputStream = context.openFileInput("gaClientId");
                        arrby = new byte[36];
                        fileInputStream2 = fileInputStream;
                        n = fileInputStream.read(arrby, 0, 36);
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream.available() <= 0) break block24;
                        fileInputStream2 = fileInputStream;
                        this.zzbR("clientId file seems corrupted, deleting it.");
                        fileInputStream2 = fileInputStream;
                        fileInputStream.close();
                        fileInputStream2 = fileInputStream;
                        context.deleteFile("gaClientId");
                        if (fileInputStream == null) return null;
                        try {
                            fileInputStream.close();
                            return null;
                        }
                        catch (IOException iOException) {
                            this.zze("Failed to close client id reading stream", iOException);
                        }
                        return null;
                    }
                    if (n < 14) {
                        fileInputStream2 = fileInputStream;
                        this.zzbR("clientId file is empty, deleting it.");
                        fileInputStream2 = fileInputStream;
                        fileInputStream.close();
                        fileInputStream2 = fileInputStream;
                        context.deleteFile("gaClientId");
                        if (fileInputStream == null) return null;
                        try {
                            fileInputStream.close();
                            return null;
                        }
                        catch (IOException iOException) {
                            this.zze("Failed to close client id reading stream", iOException);
                        }
                        return null;
                    }
                    fileInputStream2 = fileInputStream;
                    fileInputStream.close();
                    fileInputStream2 = fileInputStream;
                    String string = new String(arrby, 0, n);
                    fileInputStream2 = fileInputStream;
                    this.zza("Read client id from disk", string);
                    if (fileInputStream == null) return string;
                    try {
                        fileInputStream.close();
                        return string;
                    }
                    catch (IOException iOException) {
                        this.zze("Failed to close client id reading stream", iOException);
                    }
                    return string;
                    catch (IOException iOException) {
                        break block25;
                    }
                    catch (Throwable throwable) {
                        fileInputStream2 = null;
                        break block26;
                    }
                    catch (IOException iOException) {
                        fileInputStream = null;
                    }
                }
                fileInputStream2 = fileInputStream;
                this.zze("Error reading client id file, deleting it", var5_20);
                fileInputStream2 = fileInputStream;
                context.deleteFile("gaClientId");
                if (fileInputStream == null) return null;
                try {
                    fileInputStream.close();
                    return null;
                }
                catch (IOException iOException) {
                    this.zze("Failed to close client id reading stream", iOException);
                }
                return null;
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (fileInputStream2 == null) throw var1_8;
            try {
                fileInputStream2.close();
                throw var1_8;
            }
            catch (IOException iOException) {
                this.zze("Failed to close client id reading stream", iOException);
            }
            throw var1_8;
            catch (FileNotFoundException fileNotFoundException) {}
            fileInputStream = null;
            break block27;
            catch (FileNotFoundException fileNotFoundException) {}
        }
        if (fileInputStream == null) return null;
        try {
            fileInputStream.close();
            return null;
        }
        catch (IOException iOException) {
            this.zze("Failed to close client id reading stream", iOException);
        }
        return null;
    }

    @Override
    protected void zzmr() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public String zzop() {
        Object object;
        block7 : {
            this.zznA();
            // MONITORENTER : this
            if (this.zzacg == null) {
                this.zzadR = this.zznt().zzc(new Callable<String>(){

                    @Override
                    public /* synthetic */ Object call() throws Exception {
                        return this.zzou();
                    }

                    public String zzou() throws Exception {
                        return zzse.this.zzor();
                    }
                });
            }
            if ((object = this.zzadR) == null) break block7;
            try {
                this.zzacg = this.zzadR.get();
            }
            catch (ExecutionException executionException) {
                block8 : {
                    this.zze("Failed to load or generate client id", executionException);
                    break block8;
                    catch (InterruptedException interruptedException) {
                        this.zzd("ClientId loading or generation was interrupted", interruptedException);
                    }
                }
                this.zzacg = "0";
            }
            if (this.zzacg == null) {
                this.zzacg = "0";
            }
            this.zza("Loaded clientId", this.zzacg);
            this.zzadR = null;
        }
        object = this.zzacg;
        // MONITOREXIT : this
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String zzoq() {
        synchronized (this) {
            this.zzacg = null;
            this.zzadR = this.zznt().zzc(new Callable<String>(){

                @Override
                public /* synthetic */ Object call() throws Exception {
                    return this.zzou();
                }

                public String zzou() throws Exception {
                    return zzse.this.zzos();
                }
            });
            return this.zzop();
        }
    }

    String zzor() {
        String string;
        String string2 = string = this.zzY(this.zznt().getContext());
        if (string == null) {
            string2 = this.zzos();
        }
        return string2;
    }

    protected String zzot() {
        return UUID.randomUUID().toString().toLowerCase();
    }

}
