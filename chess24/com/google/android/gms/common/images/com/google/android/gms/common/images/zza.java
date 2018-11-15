/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzabt;
import com.google.android.gms.internal.zzabu;
import com.google.android.gms.internal.zzabv;
import java.lang.ref.WeakReference;

public abstract class zza {
    final zza zzaCV;
    protected int zzaCW = 0;
    protected int zzaCX = 0;
    protected boolean zzaCY = false;
    private boolean zzaCZ = true;
    private boolean zzaDa = false;
    private boolean zzaDb = true;

    public zza(Uri uri, int n) {
        this.zzaCV = new zza(uri);
        this.zzaCX = n;
    }

    private Drawable zza(Context context, zzabv zzabv2, int n) {
        return context.getResources().getDrawable(n);
    }

    protected zzabt zza(Drawable drawable, Drawable drawable2) {
        Drawable drawable3;
        if (drawable != null) {
            drawable3 = drawable;
            if (drawable instanceof zzabt) {
                drawable3 = ((zzabt)drawable).zzwM();
            }
        } else {
            drawable3 = null;
        }
        return new zzabt(drawable3, drawable2);
    }

    void zza(Context context, Bitmap bitmap, boolean bl) {
        com.google.android.gms.common.internal.zzc.zzt((Object)bitmap);
        this.zza((Drawable)new BitmapDrawable(context.getResources(), bitmap), bl, false, true);
    }

    void zza(Context context, zzabv zzabv2) {
        if (this.zzaDb) {
            this.zza(null, false, true, false);
        }
    }

    void zza(Context object, zzabv zzabv2, boolean bl) {
        object = this.zzaCX != 0 ? this.zza((Context)object, zzabv2, this.zzaCX) : null;
        this.zza((Drawable)object, bl, false, false);
    }

    protected abstract void zza(Drawable var1, boolean var2, boolean var3, boolean var4);

    protected boolean zzc(boolean bl, boolean bl2) {
        if (this.zzaCZ && !bl2 && !bl) {
            return true;
        }
        return false;
    }

    public void zzcI(int n) {
        this.zzaCX = n;
    }

    static final class zza {
        public final Uri uri;

        public zza(Uri uri) {
            this.uri = uri;
        }

        public boolean equals(Object object) {
            if (!(object instanceof zza)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            return zzaa.equal((Object)((zza)object).uri, (Object)this.uri);
        }

        public int hashCode() {
            return zzaa.hashCode(new Object[]{this.uri});
        }
    }

    public static final class zzb
    extends zza {
        private WeakReference<ImageView> zzaDc;

        public zzb(ImageView imageView, int n) {
            super(null, n);
            com.google.android.gms.common.internal.zzc.zzt((Object)imageView);
            this.zzaDc = new WeakReference<ImageView>(imageView);
        }

        public zzb(ImageView imageView, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzc.zzt((Object)imageView);
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
            if (!(object instanceof zzb)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            zzb zzb2 = (zzb)object;
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

    public static final class zzc
    extends zza {
        private WeakReference<ImageManager.OnImageLoadedListener> zzaDd;

        public zzc(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzc.zzt(onImageLoadedListener);
            this.zzaDd = new WeakReference<ImageManager.OnImageLoadedListener>(onImageLoadedListener);
        }

        public boolean equals(Object object) {
            if (!(object instanceof zzc)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (zzc)object;
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

}
