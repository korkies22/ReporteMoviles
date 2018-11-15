/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.app;

import android.view.View;

class ActionBarDrawerToggle
implements View.OnClickListener {
    ActionBarDrawerToggle() {
    }

    public void onClick(View view) {
        if (ActionBarDrawerToggle.this.mDrawerIndicatorEnabled) {
            ActionBarDrawerToggle.this.toggle();
            return;
        }
        if (ActionBarDrawerToggle.this.mToolbarNavigationClickListener != null) {
            ActionBarDrawerToggle.this.mToolbarNavigationClickListener.onClick(view);
        }
    }
}
