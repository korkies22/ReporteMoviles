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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegateImplBase;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;

private class AppCompatDelegateImplBase.ActionBarDrawableToggleImpl
implements ActionBarDrawerToggle.Delegate {
    AppCompatDelegateImplBase.ActionBarDrawableToggleImpl() {
    }

    @Override
    public Context getActionBarThemedContext() {
        return AppCompatDelegateImplBase.this.getActionBarThemedContext();
    }

    @Override
    public Drawable getThemeUpIndicator() {
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.getActionBarThemedContext(), null, new int[]{R.attr.homeAsUpIndicator});
        Drawable drawable = tintTypedArray.getDrawable(0);
        tintTypedArray.recycle();
        return drawable;
    }

    @Override
    public boolean isNavigationVisible() {
        ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
        if (actionBar != null && (actionBar.getDisplayOptions() & 4) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void setActionBarDescription(int n) {
        ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeActionContentDescription(n);
        }
    }

    @Override
    public void setActionBarUpIndicator(Drawable drawable, int n) {
        ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(drawable);
            actionBar.setHomeActionContentDescription(n);
        }
    }
}
