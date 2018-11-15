/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package android.support.design.internal;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;

class ScrimInsetsFrameLayout
implements OnApplyWindowInsetsListener {
    ScrimInsetsFrameLayout() {
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
        if (ScrimInsetsFrameLayout.this.mInsets == null) {
            ScrimInsetsFrameLayout.this.mInsets = new Rect();
        }
        ScrimInsetsFrameLayout.this.mInsets.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
        ScrimInsetsFrameLayout.this.onInsetsChanged(windowInsetsCompat);
        object = ScrimInsetsFrameLayout.this;
        boolean bl = !windowInsetsCompat.hasSystemWindowInsets() || ScrimInsetsFrameLayout.this.mInsetForeground == null;
        object.setWillNotDraw(bl);
        ViewCompat.postInvalidateOnAnimation((View)ScrimInsetsFrameLayout.this);
        return windowInsetsCompat.consumeSystemWindowInsets();
    }
}
