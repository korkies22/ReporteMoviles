/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.view.View
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.WindowInsets
 */
package android.support.v4.widget;

import android.annotation.TargetApi;
import android.view.View;
import android.view.WindowInsets;

class DrawerLayout
implements View.OnApplyWindowInsetsListener {
    DrawerLayout() {
    }

    @TargetApi(value=21)
    public WindowInsets onApplyWindowInsets(View object, WindowInsets windowInsets) {
        object = (android.support.v4.widget.DrawerLayout)((Object)object);
        boolean bl = windowInsets.getSystemWindowInsetTop() > 0;
        object.setChildInsets((Object)windowInsets, bl);
        return windowInsets.consumeSystemWindowInsets();
    }
}
