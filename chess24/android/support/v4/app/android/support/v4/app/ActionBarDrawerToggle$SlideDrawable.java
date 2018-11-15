/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.Window
 */
package android.support.v4.app;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.Window;

private class ActionBarDrawerToggle.SlideDrawable
extends InsetDrawable
implements Drawable.Callback {
    private final boolean mHasMirroring;
    private float mOffset;
    private float mPosition;
    private final Rect mTmpRect;

    ActionBarDrawerToggle.SlideDrawable(Drawable drawable) {
        boolean bl = false;
        super(drawable, 0);
        if (Build.VERSION.SDK_INT > 18) {
            bl = true;
        }
        this.mHasMirroring = bl;
        this.mTmpRect = new Rect();
    }

    public void draw(@NonNull Canvas canvas) {
        this.copyBounds(this.mTmpRect);
        canvas.save();
        int n = ViewCompat.getLayoutDirection(ActionBarDrawerToggle.this.mActivity.getWindow().getDecorView());
        int n2 = 1;
        n = n == 1 ? 1 : 0;
        if (n != 0) {
            n2 = -1;
        }
        int n3 = this.mTmpRect.width();
        float f = - this.mOffset;
        float f2 = n3;
        canvas.translate(f * f2 * this.mPosition * (float)n2, 0.0f);
        if (n != 0 && !this.mHasMirroring) {
            canvas.translate(f2, 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        super.draw(canvas);
        canvas.restore();
    }

    public float getPosition() {
        return this.mPosition;
    }

    public void setOffset(float f) {
        this.mOffset = f;
        this.invalidateSelf();
    }

    public void setPosition(float f) {
        this.mPosition = f;
        this.invalidateSelf();
    }
}
