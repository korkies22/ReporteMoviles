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
import android.support.v7.widget.Toolbar;

static class ActionBarDrawerToggle.ToolbarCompatDelegate
implements ActionBarDrawerToggle.Delegate {
    final CharSequence mDefaultContentDescription;
    final Drawable mDefaultUpIndicator;
    final Toolbar mToolbar;

    ActionBarDrawerToggle.ToolbarCompatDelegate(Toolbar toolbar) {
        this.mToolbar = toolbar;
        this.mDefaultUpIndicator = toolbar.getNavigationIcon();
        this.mDefaultContentDescription = toolbar.getNavigationContentDescription();
    }

    @Override
    public Context getActionBarThemedContext() {
        return this.mToolbar.getContext();
    }

    @Override
    public Drawable getThemeUpIndicator() {
        return this.mDefaultUpIndicator;
    }

    @Override
    public boolean isNavigationVisible() {
        return true;
    }

    @Override
    public void setActionBarDescription(@StringRes int n) {
        if (n == 0) {
            this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
            return;
        }
        this.mToolbar.setNavigationContentDescription(n);
    }

    @Override
    public void setActionBarUpIndicator(Drawable drawable, @StringRes int n) {
        this.mToolbar.setNavigationIcon(drawable);
        this.setActionBarDescription(n);
    }
}
