/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 */
package android.support.v4.view;

import android.graphics.Rect;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;

class ViewPager
implements OnApplyWindowInsetsListener {
    private final Rect mTempRect = new Rect();

    ViewPager() {
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
        if ((object = ViewCompat.onApplyWindowInsets((View)object, windowInsetsCompat)).isConsumed()) {
            return object;
        }
        windowInsetsCompat = this.mTempRect;
        windowInsetsCompat.left = object.getSystemWindowInsetLeft();
        windowInsetsCompat.top = object.getSystemWindowInsetTop();
        windowInsetsCompat.right = object.getSystemWindowInsetRight();
        windowInsetsCompat.bottom = object.getSystemWindowInsetBottom();
        int n = ViewPager.this.getChildCount();
        for (int i = 0; i < n; ++i) {
            WindowInsetsCompat windowInsetsCompat2 = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), (WindowInsetsCompat)object);
            windowInsetsCompat.left = Math.min(windowInsetsCompat2.getSystemWindowInsetLeft(), windowInsetsCompat.left);
            windowInsetsCompat.top = Math.min(windowInsetsCompat2.getSystemWindowInsetTop(), windowInsetsCompat.top);
            windowInsetsCompat.right = Math.min(windowInsetsCompat2.getSystemWindowInsetRight(), windowInsetsCompat.right);
            windowInsetsCompat.bottom = Math.min(windowInsetsCompat2.getSystemWindowInsetBottom(), windowInsetsCompat.bottom);
        }
        return object.replaceSystemWindowInsets(windowInsetsCompat.left, windowInsetsCompat.top, windowInsetsCompat.right, windowInsetsCompat.bottom);
    }
}
