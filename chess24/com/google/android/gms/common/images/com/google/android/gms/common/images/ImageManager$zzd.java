/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.SystemClock
 */
package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzabv;
import java.util.HashSet;
import java.util.Map;

private final class ImageManager.zzd
implements Runnable {
    private final zza zzaCT;

    public ImageManager.zzd(zza zza2) {
        this.zzaCT = zza2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        zzc.zzdn("LoadImageRunnable must be executed on the main thread");
        Object object = (ImageManager.ImageReceiver)((Object)ImageManager.this.zzaCN.get(this.zzaCT));
        if (object != null) {
            ImageManager.this.zzaCN.remove(this.zzaCT);
            object.zzc(this.zzaCT);
        }
        zza.zza zza2 = this.zzaCT.zzaCV;
        if (zza2.uri == null) {
            this.zzaCT.zza(ImageManager.this.mContext, ImageManager.this.zzaCM, true);
            return;
        }
        object = ImageManager.this.zza(zza2);
        if (object != null) {
            this.zzaCT.zza(ImageManager.this.mContext, (Bitmap)object, true);
            return;
        }
        object = (Long)ImageManager.this.zzaCP.get((Object)zza2.uri);
        if (object != null) {
            if (SystemClock.elapsedRealtime() - object.longValue() < 3600000L) {
                this.zzaCT.zza(ImageManager.this.mContext, ImageManager.this.zzaCM, true);
                return;
            }
            ImageManager.this.zzaCP.remove((Object)zza2.uri);
        }
        this.zzaCT.zza(ImageManager.this.mContext, ImageManager.this.zzaCM);
        Object object2 = (ImageManager.ImageReceiver)((Object)ImageManager.this.zzaCO.get((Object)zza2.uri));
        object = object2;
        if (object2 == null) {
            object = new ImageManager.ImageReceiver(ImageManager.this, zza2.uri);
            ImageManager.this.zzaCO.put(zza2.uri, object);
        }
        object.zzb(this.zzaCT);
        if (!(this.zzaCT instanceof zza.zzc)) {
            ImageManager.this.zzaCN.put(this.zzaCT, object);
        }
        object2 = zzaCG;
        synchronized (object2) {
            if (!zzaCH.contains((Object)zza2.uri)) {
                zzaCH.add(zza2.uri);
                object.zzwL();
            }
            return;
        }
    }
}
