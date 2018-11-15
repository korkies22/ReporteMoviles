/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.CollapsibleActionView
 *  android.view.View
 *  android.widget.FrameLayout
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.view.View;
import android.widget.FrameLayout;

static class MenuItemWrapperICS.CollapsibleActionViewWrapper
extends FrameLayout
implements CollapsibleActionView {
    final android.view.CollapsibleActionView mWrappedView;

    MenuItemWrapperICS.CollapsibleActionViewWrapper(View view) {
        super(view.getContext());
        this.mWrappedView = (android.view.CollapsibleActionView)view;
        this.addView(view);
    }

    View getWrappedView() {
        return (View)this.mWrappedView;
    }

    @Override
    public void onActionViewCollapsed() {
        this.mWrappedView.onActionViewCollapsed();
    }

    @Override
    public void onActionViewExpanded() {
        this.mWrappedView.onActionViewExpanded();
    }
}
