/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ActionProvider
 *  android.view.ActionProvider$VisibilityListener
 *  android.view.MenuItem
 *  android.view.View
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.support.v7.view.menu.MenuItemWrapperJB;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

class MenuItemWrapperJB.ActionProviderWrapperJB
extends MenuItemWrapperICS.ActionProviderWrapper
implements ActionProvider.VisibilityListener {
    ActionProvider.VisibilityListener mListener;

    public MenuItemWrapperJB.ActionProviderWrapperJB(Context context, android.view.ActionProvider actionProvider) {
        super(MenuItemWrapperJB.this, context, actionProvider);
    }

    @Override
    public boolean isVisible() {
        return this.mInner.isVisible();
    }

    public void onActionProviderVisibilityChanged(boolean bl) {
        if (this.mListener != null) {
            this.mListener.onActionProviderVisibilityChanged(bl);
        }
    }

    @Override
    public View onCreateActionView(MenuItem menuItem) {
        return this.mInner.onCreateActionView(menuItem);
    }

    @Override
    public boolean overridesItemVisibility() {
        return this.mInner.overridesItemVisibility();
    }

    @Override
    public void refreshVisibility() {
        this.mInner.refreshVisibility();
    }

    @Override
    public void setVisibilityListener(ActionProvider.VisibilityListener object) {
        this.mListener = object;
        android.view.ActionProvider actionProvider = this.mInner;
        object = object != null ? this : null;
        actionProvider.setVisibilityListener((ActionProvider.VisibilityListener)object);
    }
}
