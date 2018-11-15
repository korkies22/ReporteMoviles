/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzabt;
import com.google.android.gms.internal.zzabu;
import java.lang.ref.WeakReference;

public static final class zza.zzb
extends zza {
    private WeakReference<ImageView> zzaDc;

    public zza.zzb(ImageView imageView, int n) {
        super(null, n);
        zzc.zzt((Object)imageView);
        this.zzaDc = new WeakReference<ImageView>(imageView);
    }

    public zza.zzb(ImageView imageView, Uri uri) {
        super(uri, 0);
        zzc.zzt((Object)imageView);
        this.zzaDc = new WeakReference<ImageView>(imageView);
    }

    private void zza(ImageView object, Drawable object2, boolean bl, boolean bl2, boolean bl3) {
        int n = 0;
        boolean bl4 = !bl2 && !bl3;
        if (bl4 && object instanceof zzabu) {
            int n2 = ((zzabu)((Object)object)).zzwO();
            if (this.zzaCX != 0 && n2 == this.zzaCX) {
                return;
            }
        }
        bl = this.zzc(bl, bl2);
        Drawable drawable = object2;
        if (bl) {
            drawable = this.zza(object.getDrawable(), (Drawable)object2);
        }
        object.setImageDrawable(drawable);
        if (object instanceof zzabu) {
            object2 = (zzabu)((Object)object);
            object = bl3 ? this.zzaCV.uri : null;
            object2.zzr((Uri)object);
            if (bl4) {
                n = this.zzaCX;
            }
            object2.zzcK(n);
        }
        if (bl) {
            ((zzabt)drawable).startTransition(250);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof zza.zzb)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        zza.zzb zzb2 = (zza.zzb)object;
        object = (ImageView)this.zzaDc.get();
        zzb2 = (ImageView)zzb2.zzaDc.get();
        if (zzb2 != null && object != null && zzaa.equal(zzb2, object)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    @Override
    protected void zza(Drawable drawable, boolean bl, boolean bl2, boolean bl3) {
        ImageView imageView = (ImageView)this.zzaDc.get();
        if (imageView != null) {
            this.zza(imageView, drawable, bl, bl2, bl3);
        }
    }
}
