/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.SystemClock
 */
package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzabv;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

private final class ImageManager.zzf
implements Runnable {
    private final Bitmap mBitmap;
    private final Uri mUri;
    private boolean zzaCU;
    private final CountDownLatch zzth;

    public ImageManager.zzf(Uri uri, Bitmap bitmap, boolean bl, CountDownLatch countDownLatch) {
        this.mUri = uri;
        this.mBitmap = bitmap;
        this.zzaCU = bl;
        this.zzth = countDownLatch;
    }

    private void zza(ImageManager.ImageReceiver object, boolean bl) {
        object = ((ImageManager.ImageReceiver)((Object)object)).zzaCQ;
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            zza zza2 = (zza)object.get(i);
            if (bl) {
                zza2.zza(ImageManager.this.mContext, this.mBitmap, false);
            } else {
                ImageManager.this.zzaCP.put(this.mUri, SystemClock.elapsedRealtime());
                zza2.zza(ImageManager.this.mContext, ImageManager.this.zzaCM, false);
            }
            if (zza2 instanceof zza.zzc) continue;
            ImageManager.this.zzaCN.remove(zza2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Object object;
        zzc.zzdn("OnBitmapLoadedRunnable must be executed in the main thread");
        boolean bl = this.mBitmap != null;
        if (ImageManager.this.zzaCL != null) {
            if (this.zzaCU) {
                ImageManager.this.zzaCL.evictAll();
                System.gc();
                this.zzaCU = false;
                ImageManager.this.mHandler.post((Runnable)this);
                return;
            }
            if (bl) {
                ImageManager.this.zzaCL.put(new zza.zza(this.mUri), this.mBitmap);
            }
        }
        if ((object = (ImageManager.ImageReceiver)((Object)ImageManager.this.zzaCO.remove((Object)this.mUri))) != null) {
            this.zza((ImageManager.ImageReceiver)((Object)object), bl);
        }
        this.zzth.countDown();
        object = zzaCG;
        synchronized (object) {
            zzaCH.remove((Object)this.mUri);
            return;
        }
    }
}
