/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 */
package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzc;
import java.lang.ref.WeakReference;

public static final class zza.zzc
extends zza {
    private WeakReference<ImageManager.OnImageLoadedListener> zzaDd;

    public zza.zzc(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        zzc.zzt(onImageLoadedListener);
        this.zzaDd = new WeakReference<ImageManager.OnImageLoadedListener>(onImageLoadedListener);
    }

    public boolean equals(Object object) {
        if (!(object instanceof zza.zzc)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        object = (zza.zzc)object;
        ImageManager.OnImageLoadedListener onImageLoadedListener = (ImageManager.OnImageLoadedListener)this.zzaDd.get();
        ImageManager.OnImageLoadedListener onImageLoadedListener2 = (ImageManager.OnImageLoadedListener)object.zzaDd.get();
        if (onImageLoadedListener2 != null && onImageLoadedListener != null && zzaa.equal(onImageLoadedListener2, onImageLoadedListener) && zzaa.equal(object.zzaCV, this.zzaCV)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaCV);
    }

    @Override
    protected void zza(Drawable drawable, boolean bl, boolean bl2, boolean bl3) {
        ImageManager.OnImageLoadedListener onImageLoadedListener;
        if (!bl2 && (onImageLoadedListener = (ImageManager.OnImageLoadedListener)this.zzaDd.get()) != null) {
            onImageLoadedListener.onImageLoaded(this.zzaCV.uri, drawable, bl3);
        }
    }
}
