/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 */
package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;

@RequiresApi(value=18)
private static class ActionBarDrawerToggle.JellybeanMr2Delegate
implements ActionBarDrawerToggle.Delegate {
    final Activity mActivity;

    ActionBarDrawerToggle.JellybeanMr2Delegate(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public Context getActionBarThemedContext() {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null) {
            return actionBar.getThemedContext();
        }
        return this.mActivity;
    }

    @Override
    public Drawable getThemeUpIndicator() {
        TypedArray typedArray = this.getActionBarThemedContext().obtainStyledAttributes(null, new int[]{16843531}, 16843470, 0);
        Drawable drawable = typedArray.getDrawable(0);
        typedArray.recycle();
        return drawable;
    }

    @Override
    public boolean isNavigationVisible() {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null && (actionBar.getDisplayOptions() & 4) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void setActionBarDescription(int n) {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null) {
            actionBar.setHomeActionContentDescription(n);
        }
    }

    @Override
    public void setActionBarUpIndicator(Drawable drawable, int n) {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(drawable);
            actionBar.setHomeActionContentDescription(n);
        }
    }
}
