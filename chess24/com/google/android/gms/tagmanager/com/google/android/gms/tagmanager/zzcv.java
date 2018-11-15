/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  org.json.JSONException
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzbgg;
import com.google.android.gms.internal.zzbgi;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.tagmanager.zzbh;
import com.google.android.gms.tagmanager.zzbn;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcj;
import com.google.android.gms.tagmanager.zzp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

class zzcv
implements zzp.zzf {
    private final Context mContext;
    private final String zzbCS;
    private zzbn<zzbgg.zza> zzbFr;
    private final ExecutorService zzbFy;

    zzcv(Context context, String string) {
        this.mContext = context;
        this.zzbCS = string;
        this.zzbFy = Executors.newSingleThreadExecutor();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private zzbgi.zzc zzL(byte[] object) {
        try {
            zzbgi.zzc zzc2 = zzbgi.zzb(zzai.zzf.zzf((byte[])object));
            if (zzc2 == null) return zzc2;
            zzbo.v("The container was successfully loaded from the resource (using binary file)");
            return zzc2;
        }
        catch (zzbus zzbus2) {}
        zzbo.e("The resource file is corrupted. The container cannot be extracted from the binary file");
        return null;
        catch (zzbgi.zzg zzg2) {}
        zzbo.zzbe("The resource file is invalid. The container from the binary file is invalid");
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private zzbgi.zzc zza(ByteArrayOutputStream object) {
        try {
            return zzbh.zzho(object.toString("UTF-8"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {}
        zzbo.zzbc("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
        return null;
        catch (JSONException jSONException) {}
        zzbo.zzbe("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
        return null;
    }

    private void zzd(zzbgg.zza zza2) throws IllegalArgumentException {
        if (zza2.zzlu == null && zza2.zzbLi == null) {
            throw new IllegalArgumentException("Resource and SupplementedResource are NULL.");
        }
    }

    @Override
    public void release() {
        synchronized (this) {
            this.zzbFy.shutdown();
            return;
        }
    }

    @Override
    public void zzOK() {
        this.zzbFy.execute(new Runnable(){

            @Override
            public void run() {
                zzcv.this.zzPH();
            }
        });
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void zzPH() {
        Throwable throwable2222;
        block15 : {
            FileInputStream fileInputStream;
            block14 : {
                if (this.zzbFr == null) {
                    throw new IllegalStateException("Callback must be set before execute");
                }
                zzbo.v("Attempting to load resource from disk");
                if ((zzcj.zzPz().zzPA() == zzcj.zza.zzbFg || zzcj.zzPz().zzPA() == zzcj.zza.zzbFh) && this.zzbCS.equals(zzcj.zzPz().getContainerId())) {
                    this.zzbFr.zza(zzbn.zza.zzbEG);
                    return;
                }
                fileInputStream = new FileInputStream(this.zzPI());
                {
                    catch (FileNotFoundException fileNotFoundException) {}
                }
                try {
                    try {
                        Object object = new ByteArrayOutputStream();
                        zzbgi.zzc(fileInputStream, (OutputStream)object);
                        object = zzbgg.zza.zzP(object.toByteArray());
                        this.zzd((zzbgg.zza)object);
                        this.zzbFr.onSuccess((zzbgg.zza)object);
                        break block14;
                    }
                    catch (IOException iOException) {}
                    zzbo.zzbc("Failed to find the resource in the disk");
                    this.zzbFr.zza(zzbn.zza.zzbEG);
                    return;
                    this.zzbFr.zza(zzbn.zza.zzbEH);
                    zzbo.zzbe("Failed to read the resource from disk");
                    break block14;
                }
                catch (Throwable throwable2222) {}
                try {
                    fileInputStream.close();
                    throw throwable2222;
                }
                catch (IOException iOException) {}
                catch (IllegalArgumentException illegalArgumentException) {}
                this.zzbFr.zza(zzbn.zza.zzbEH);
                zzbo.zzbe("Failed to read the resource from disk. The resource is inconsistent");
            }
            try {
                fileInputStream.close();
                break block15;
            }
            catch (IOException iOException) {}
            zzbo.zzbe("Error closing stream for reading resource from disk");
        }
        zzbo.v("The Disk resource was successfully read.");
        return;
        zzbo.zzbe("Error closing stream for reading resource from disk");
        throw throwable2222;
    }

    File zzPI() {
        String string = String.valueOf("resource_");
        String string2 = String.valueOf(this.zzbCS);
        string = string2.length() != 0 ? string.concat(string2) : new String(string);
        return new File(this.mContext.getDir("google_tagmanager", 0), string);
    }

    @Override
    public void zza(zzbn<zzbgg.zza> zzbn2) {
        this.zzbFr = zzbn2;
    }

    @Override
    public void zzb(final zzbgg.zza zza2) {
        this.zzbFy.execute(new Runnable(){

            @Override
            public void run() {
                zzcv.this.zzc(zza2);
            }
        });
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    boolean zzc(zzbgg.zza zza2) {
        FileOutputStream fileOutputStream;
        File file = this.zzPI();
        try {
            fileOutputStream = new FileOutputStream(file);
        }
        catch (FileNotFoundException fileNotFoundException) {}
        fileOutputStream.write(zzbut.zzf(zza2));
        try {
            fileOutputStream.close();
            return true;
        }
        catch (IOException iOException) {}
        zzbo.e("Error opening resource file for writing");
        return false;
        zzbo.zzbe("error closing stream for writing resource to disk");
        return true;
        catch (Throwable throwable) {}
        try {
            fileOutputStream.close();
            throw throwable;
        }
        catch (IOException iOException) {}
        catch (IOException iOException) {}
        {
            zzbo.zzbe("Error writing resource to disk. Removing resource from disk.");
            file.delete();
        }
        try {
            fileOutputStream.close();
            return false;
        }
        catch (IOException iOException) {}
        zzbo.zzbe("error closing stream for writing resource to disk");
        return false;
        zzbo.zzbe("error closing stream for writing resource to disk");
        throw throwable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public zzbgi.zzc zzmO(int n) {
        void var2_8;
        block4 : {
            InputStream inputStream;
            try {
                inputStream = this.mContext.getResources().openRawResource(n);
            }
            catch (Resources.NotFoundException notFoundException) {}
            Object object = String.valueOf(this.mContext.getResources().getResourceName(n));
            StringBuilder stringBuilder = new StringBuilder(66 + String.valueOf(object).length());
            stringBuilder.append("Attempting to load a container from the resource ID ");
            stringBuilder.append(n);
            stringBuilder.append(" (");
            stringBuilder.append((String)object);
            stringBuilder.append(")");
            zzbo.v(stringBuilder.toString());
            try {
                object = new ByteArrayOutputStream();
                zzbgi.zzc(inputStream, (OutputStream)object);
                zzbgi.zzc zzc2 = this.zza((ByteArrayOutputStream)object);
                if (zzc2 == null) return this.zzL(object.toByteArray());
                zzbo.v("The container was successfully loaded from the resource (using JSON file format)");
                return zzc2;
            }
            catch (IOException iOException) {}
            StringBuilder stringBuilder2 = new StringBuilder(98);
            stringBuilder2.append("Failed to load the container. No default container resource found with the resource ID ");
            stringBuilder2.append(n);
            String string = stringBuilder2.toString();
            break block4;
            String string2 = String.valueOf(this.mContext.getResources().getResourceName(n));
            object = new StringBuilder(67 + String.valueOf(string2).length());
            object.append("Error reading the default container with resource ID ");
            object.append(n);
            object.append(" (");
            object.append(string2);
            object.append(")");
            String string3 = object.toString();
        }
        zzbo.zzbe((String)var2_8);
        return null;
    }

}
