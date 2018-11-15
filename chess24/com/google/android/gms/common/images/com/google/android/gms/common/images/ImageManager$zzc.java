/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.ParcelFileDescriptor
 *  android.util.Log
 */
package com.google.android.gms.common.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.internal.zzc;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

private final class ImageManager.zzc
implements Runnable {
    private final Uri mUri;
    private final ParcelFileDescriptor zzaCS;

    public ImageManager.zzc(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
        this.mUri = uri;
        this.zzaCS = parcelFileDescriptor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        void var3_10;
        zzc.zzdo("LoadBitmapFromDiskRunnable can't be executed in the main thread");
        ParcelFileDescriptor parcelFileDescriptor = this.zzaCS;
        boolean bl = false;
        boolean bl2 = false;
        Object var3_5 = null;
        Object object = null;
        if (parcelFileDescriptor != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor((FileDescriptor)this.zzaCS.getFileDescriptor());
                bl = bl2;
            }
            catch (OutOfMemoryError outOfMemoryError) {
                String string = String.valueOf((Object)this.mUri);
                StringBuilder stringBuilder = new StringBuilder(34 + String.valueOf(string).length());
                stringBuilder.append("OOM while loading bitmap for uri: ");
                stringBuilder.append(string);
                Log.e((String)"ImageManager", (String)stringBuilder.toString(), (Throwable)outOfMemoryError);
                bl = true;
                CountDownLatch countDownLatch = object;
            }
            try {
                this.zzaCS.close();
            }
            catch (IOException iOException) {
                Log.e((String)"ImageManager", (String)"closed failed", (Throwable)iOException);
            }
        }
        object = new CountDownLatch(1);
        ImageManager.this.mHandler.post((Runnable)new ImageManager.zzf(ImageManager.this, this.mUri, (Bitmap)var3_10, bl, (CountDownLatch)object));
        try {
            object.await();
            return;
        }
        catch (InterruptedException interruptedException) {}
        String string = String.valueOf((Object)this.mUri);
        object = new StringBuilder(32 + String.valueOf(string).length());
        object.append("Latch interrupted while posting ");
        object.append(string);
        Log.w((String)"ImageManager", (String)object.toString());
    }
}
