/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.KeyEvent
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.HeaderViewListAdapter
 *  android.widget.ListAdapter
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.DropDownListView;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public static class MenuPopupWindow.MenuDropDownListView
extends DropDownListView {
    final int mAdvanceKey;
    private MenuItemHoverListener mHoverListener;
    private MenuItem mHoveredMenuItem;
    final int mRetreatKey;

    public MenuPopupWindow.MenuDropDownListView(Context context, boolean bl) {
        super(context, bl);
        context = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= 17 && 1 == context.getLayoutDirection()) {
            this.mAdvanceKey = 21;
            this.mRetreatKey = 22;
            return;
        }
        this.mAdvanceKey = 22;
        this.mRetreatKey = 21;
    }

    public void clearSelection() {
        this.setSelection(-1);
    }

    @Override
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (this.mHoverListener != null) {
            MenuItem menuItem;
            int n;
            Object object = this.getAdapter();
            if (object instanceof HeaderViewListAdapter) {
                object = (HeaderViewListAdapter)object;
                n = object.getHeadersCount();
                object = (MenuAdapter)object.getWrappedAdapter();
            } else {
                n = 0;
                object = (MenuAdapter)((Object)object);
            }
            MenuItem menuItem2 = menuItem = null;
            if (motionEvent.getAction() != 10) {
                int n2 = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
                menuItem2 = menuItem;
                if (n2 != -1) {
                    n = n2 - n;
                    menuItem2 = menuItem;
                    if (n >= 0) {
                        menuItem2 = menuItem;
                        if (n < object.getCount()) {
                            menuItem2 = object.getItem(n);
                        }
                    }
                }
            }
            if ((menuItem = this.mHoveredMenuItem) != menuItem2) {
                object = object.getAdapterMenu();
                if (menuItem != null) {
                    this.mHoverListener.onItemHoverExit((MenuBuilder)object, menuItem);
                }
                this.mHoveredMenuItem = menuItem2;
                if (menuItem2 != null) {
                    this.mHoverListener.onItemHoverEnter((MenuBuilder)object, menuItem2);
                }
            }
        }
        return super.onHoverEvent(motionEvent);
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        ListMenuItemView listMenuItemView = (ListMenuItemView)this.getSelectedView();
        if (listMenuItemView != null && n == this.mAdvanceKey) {
            if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                this.performItemClick((View)listMenuItemView, this.getSelectedItemPosition(), this.getSelectedItemId());
            }
            return true;
        }
        if (listMenuItemView != null && n == this.mRetreatKey) {
            this.setSelection(-1);
            ((MenuAdapter)this.getAdapter()).getAdapterMenu().close(false);
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    public void setHoverListener(MenuItemHoverListener menuItemHoverListener) {
        this.mHoverListener = menuItemHoverListener;
    }
}
