/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 */
package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarDrawerToggleHoneycomb;

private static class ActionBarDrawerToggle.IcsDelegate
implements ActionBarDrawerToggle.Delegate {
    final Activity mActivity;
    ActionBarDrawerToggleHoneycomb.SetIndicatorInfo mSetIndicatorInfo;

    ActionBarDrawerToggle.IcsDelegate(Activity activity) {
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
        return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(this.mActivity);
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
        this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, n);
    }

    @Override
    public void setActionBarUpIndicator(Drawable drawable, int n) {
        ActionBar actionBar = this.mActivity.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, drawable, n);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }
}
