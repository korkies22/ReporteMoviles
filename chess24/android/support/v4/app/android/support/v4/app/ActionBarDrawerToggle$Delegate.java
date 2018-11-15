/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package android.support.v4.app;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActionBarDrawerToggle;

@Deprecated
public static interface ActionBarDrawerToggle.Delegate {
    @Nullable
    public Drawable getThemeUpIndicator();

    public void setActionBarDescription(@StringRes int var1);

    public void setActionBarUpIndicator(Drawable var1, @StringRes int var2);
}
