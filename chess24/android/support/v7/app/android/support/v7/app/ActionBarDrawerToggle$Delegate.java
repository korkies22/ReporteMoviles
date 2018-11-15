/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 */
package android.support.v7.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBarDrawerToggle;

public static interface ActionBarDrawerToggle.Delegate {
    public Context getActionBarThemedContext();

    public Drawable getThemeUpIndicator();

    public boolean isNavigationVisible();

    public void setActionBarDescription(@StringRes int var1);

    public void setActionBarUpIndicator(Drawable var1, @StringRes int var2);
}
