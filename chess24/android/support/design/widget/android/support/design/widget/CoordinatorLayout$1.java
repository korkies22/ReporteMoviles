/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;

class CoordinatorLayout
implements OnApplyWindowInsetsListener {
    CoordinatorLayout() {
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return CoordinatorLayout.this.setWindowInsets(windowInsetsCompat);
    }
}
