/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.google.android.gms.common.util.zzs;

public final class zzabt
extends Drawable
implements Drawable.Callback {
    private int mFrom;
    private boolean zzaCZ = true;
    private int zzaDe = 0;
    private int zzaDf;
    private int zzaDg = 255;
    private int zzaDh;
    private int zzaDi = 0;
    private boolean zzaDj;
    private zzb zzaDk;
    private Drawable zzaDl;
    private Drawable zzaDm;
    private boolean zzaDn;
    private boolean zzaDo;
    private boolean zzaDp;
    private int zzaDq;
    private long zzaed;

    public zzabt(Drawable object, Drawable object2) {
        this(null);
        Drawable drawable = object;
        if (object == null) {
            drawable = zzaDr;
        }
        this.zzaDl = drawable;
        drawable.setCallback((Drawable.Callback)this);
        object = this.zzaDk;
        int n = object.zzaDt;
        object.zzaDt = drawable.getChangingConfigurations() | n;
        object = object2;
        if (object2 == null) {
            object = zzaDr;
        }
        this.zzaDm = object;
        object.setCallback((Drawable.Callback)this);
        object2 = this.zzaDk;
        n = object2.zzaDt;
        object2.zzaDt = object.getChangingConfigurations() | n;
    }

    zzabt(zzb zzb2) {
        this.zzaDk = new zzb(zzb2);
    }

    public boolean canConstantState() {
        if (!this.zzaDn) {
            boolean bl = this.zzaDl.getConstantState() != null && this.zzaDm.getConstantState() != null;
            this.zzaDo = bl;
            this.zzaDn = true;
        }
        return this.zzaDo;
    }

    public void draw(Canvas canvas) {
        int n = this.zzaDe;
        int n2 = 1;
        int n3 = 1;
        switch (n) {
            default: {
                break;
            }
            case 2: {
                if (this.zzaed < 0L) break;
                float f = (float)(SystemClock.uptimeMillis() - this.zzaed) / (float)this.zzaDh;
                n2 = f >= 1.0f ? n3 : 0;
                if (n2 != 0) {
                    this.zzaDe = 0;
                }
                f = Math.min(f, 1.0f);
                this.zzaDi = (int)(0.0f + (float)(this.zzaDf - 0) * f);
                break;
            }
            case 1: {
                this.zzaed = SystemClock.uptimeMillis();
                this.zzaDe = 2;
                n2 = 0;
            }
        }
        n3 = this.zzaDi;
        boolean bl = this.zzaCZ;
        Drawable drawable = this.zzaDl;
        Drawable drawable2 = this.zzaDm;
        if (n2 != 0) {
            if (!bl || n3 == 0) {
                drawable.draw(canvas);
            }
            if (n3 == this.zzaDg) {
                drawable2.setAlpha(this.zzaDg);
                drawable2.draw(canvas);
            }
            return;
        }
        if (bl) {
            drawable.setAlpha(this.zzaDg - n3);
        }
        drawable.draw(canvas);
        if (bl) {
            drawable.setAlpha(this.zzaDg);
        }
        if (n3 > 0) {
            drawable2.setAlpha(n3);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zzaDg);
        }
        this.invalidateSelf();
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.zzaDk.mChangingConfigurations | this.zzaDk.zzaDt;
    }

    public Drawable.ConstantState getConstantState() {
        if (this.canConstantState()) {
            this.zzaDk.mChangingConfigurations = this.getChangingConfigurations();
            return this.zzaDk;
        }
        return null;
    }

    public int getIntrinsicHeight() {
        return Math.max(this.zzaDl.getIntrinsicHeight(), this.zzaDm.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.zzaDl.getIntrinsicWidth(), this.zzaDm.getIntrinsicWidth());
    }

    public int getOpacity() {
        if (!this.zzaDp) {
            this.zzaDq = Drawable.resolveOpacity((int)this.zzaDl.getOpacity(), (int)this.zzaDm.getOpacity());
            this.zzaDp = true;
        }
        return this.zzaDq;
    }

    @TargetApi(value=11)
    public void invalidateDrawable(Drawable drawable) {
        if (zzs.zzyx() && (drawable = this.getCallback()) != null) {
            drawable.invalidateDrawable((Drawable)this);
        }
    }

    public Drawable mutate() {
        if (!this.zzaDj && super.mutate() == this) {
            if (!this.canConstantState()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.zzaDl.mutate();
            this.zzaDm.mutate();
            this.zzaDj = true;
        }
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        this.zzaDl.setBounds(rect);
        this.zzaDm.setBounds(rect);
    }

    @TargetApi(value=11)
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l) {
        if (zzs.zzyx() && (drawable = this.getCallback()) != null) {
            drawable.scheduleDrawable((Drawable)this, runnable, l);
        }
    }

    public void setAlpha(int n) {
        if (this.zzaDi == this.zzaDg) {
            this.zzaDi = n;
        }
        this.zzaDg = n;
        this.invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.zzaDl.setColorFilter(colorFilter);
        this.zzaDm.setColorFilter(colorFilter);
    }

    public void startTransition(int n) {
        this.mFrom = 0;
        this.zzaDf = this.zzaDg;
        this.zzaDi = 0;
        this.zzaDh = n;
        this.zzaDe = 1;
        this.invalidateSelf();
    }

    @TargetApi(value=11)
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        if (zzs.zzyx() && (drawable = this.getCallback()) != null) {
            drawable.unscheduleDrawable((Drawable)this, runnable);
        }
    }

    public Drawable zzwM() {
        return this.zzaDm;
    }

    private static final class zza
    extends Drawable {
        private static final zza zzaDr = new zza();
        private static final zza$zza zzaDs = new zza$zza();

        private zza() {
        }

        public void draw(Canvas canvas) {
        }

        public Drawable.ConstantState getConstantState() {
            return zzaDs;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int n) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    private static final class zza$zza
    extends Drawable.ConstantState {
        private zza$zza() {
        }

        public int getChangingConfigurations() {
            return 0;
        }

        public Drawable newDrawable() {
            return zzaDr;
        }
    }

    static final class zzb
    extends Drawable.ConstantState {
        int mChangingConfigurations;
        int zzaDt;

        zzb(zzb zzb2) {
            if (zzb2 != null) {
                this.mChangingConfigurations = zzb2.mChangingConfigurations;
                this.zzaDt = zzb2.zzaDt;
            }
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        public Drawable newDrawable() {
            return new zzabt(this);
        }
    }

}
