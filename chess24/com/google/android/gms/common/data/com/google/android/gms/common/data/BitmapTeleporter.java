/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.data.zza;
import com.google.android.gms.common.internal.ReflectedParcelable;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BitmapTeleporter
extends com.google.android.gms.common.internal.safeparcel.zza
implements ReflectedParcelable {
    public static final Parcelable.Creator<BitmapTeleporter> CREATOR = new zza();
    final int mVersionCode;
    ParcelFileDescriptor zzSn;
    private Bitmap zzaCg;
    private boolean zzaCh;
    private File zzaCi;
    final int zzanR;

    BitmapTeleporter(int n, ParcelFileDescriptor parcelFileDescriptor, int n2) {
        this.mVersionCode = n;
        this.zzSn = parcelFileDescriptor;
        this.zzanR = n2;
        this.zzaCg = null;
        this.zzaCh = false;
    }

    public BitmapTeleporter(Bitmap bitmap) {
        this.mVersionCode = 1;
        this.zzSn = null;
        this.zzanR = 0;
        this.zzaCg = bitmap;
        this.zzaCh = true;
    }

    private void zza(Closeable closeable) {
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            Log.w((String)"BitmapTeleporter", (String)"Could not close stream", (Throwable)iOException);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private FileOutputStream zzwA() {
        File file;
        if (this.zzaCi == null) {
            throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
        }
        try {
            file = File.createTempFile("teleporter", ".tmp", this.zzaCi);
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Could not create temporary file", iOException);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            this.zzSn = ParcelFileDescriptor.open((File)file, (int)268435456);
            file.delete();
            return fileOutputStream;
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new IllegalStateException("Temporary file is somehow already deleted");
        }
    }

    public void release() {
        if (!this.zzaCh) {
            try {
                this.zzSn.close();
                return;
            }
            catch (IOException iOException) {
                Log.w((String)"BitmapTeleporter", (String)"Could not close PFD", (Throwable)iOException);
            }
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
    public void writeToParcel(Parcel parcel, int n) {
        Throwable throwable2222;
        Object object;
        if (this.zzSn == null) {
            Bitmap bitmap = this.zzaCg;
            object = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
            bitmap.copyPixelsToBuffer((Buffer)object);
            byte[] arrby = object.array();
            object = new DataOutputStream(this.zzwA());
            object.writeInt(arrby.length);
            object.writeInt(bitmap.getWidth());
            object.writeInt(bitmap.getHeight());
            object.writeUTF(bitmap.getConfig().toString());
            object.write(arrby);
            this.zza((Closeable)object);
        }
        zza.zza(this, parcel, n | 1);
        this.zzSn = null;
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                throw new IllegalStateException("Could not write into unlinked file", iOException);
            }
        }
        this.zza((Closeable)object);
        throw throwable2222;
    }

    public void zzd(File file) {
        if (file == null) {
            throw new NullPointerException("Cannot set null temp directory");
        }
        this.zzaCi = file;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Bitmap zzwz() {
        Throwable throwable2222;
        if (this.zzaCh) return this.zzaCg;
        Object object = new DataInputStream((InputStream)new ParcelFileDescriptor.AutoCloseInputStream(this.zzSn));
        byte[] arrby = new byte[object.readInt()];
        int n = object.readInt();
        int n2 = object.readInt();
        Bitmap.Config config = Bitmap.Config.valueOf((String)object.readUTF());
        object.read(arrby);
        this.zza((Closeable)object);
        object = ByteBuffer.wrap(arrby);
        config = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)config);
        config.copyPixelsFromBuffer((Buffer)object);
        this.zzaCg = config;
        this.zzaCh = true;
        return this.zzaCg;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                throw new IllegalStateException("Could not read from parcel file descriptor", iOException);
            }
        }
        this.zza((Closeable)object);
        throw throwable2222;
    }
}
