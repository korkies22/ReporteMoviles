/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcelable
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.ImageButton
 */
package android.support.v7.widget;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;

private class Toolbar.ExpandedActionViewMenuPresenter
implements MenuPresenter {
    MenuItemImpl mCurrentExpandedItem;
    MenuBuilder mMenu;

    Toolbar.ExpandedActionViewMenuPresenter() {
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
            ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewCollapsed();
        }
        Toolbar.this.removeView(Toolbar.this.mExpandedActionView);
        Toolbar.this.removeView((View)Toolbar.this.mCollapseButtonView);
        Toolbar.this.mExpandedActionView = null;
        Toolbar.this.addChildrenForExpandedActionView();
        this.mCurrentExpandedItem = null;
        Toolbar.this.requestLayout();
        menuItemImpl.setActionViewExpanded(false);
        return true;
    }

    @Override
    public boolean expandItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
        Toolbar.this.ensureCollapseButtonView();
        object = Toolbar.this.mCollapseButtonView.getParent();
        if (object != Toolbar.this) {
            if (object instanceof ViewGroup) {
                ((ViewGroup)object).removeView((View)Toolbar.this.mCollapseButtonView);
            }
            Toolbar.this.addView((View)Toolbar.this.mCollapseButtonView);
        }
        Toolbar.this.mExpandedActionView = menuItemImpl.getActionView();
        this.mCurrentExpandedItem = menuItemImpl;
        object = Toolbar.this.mExpandedActionView.getParent();
        if (object != Toolbar.this) {
            if (object instanceof ViewGroup) {
                ((ViewGroup)object).removeView(Toolbar.this.mExpandedActionView);
            }
            object = Toolbar.this.generateDefaultLayoutParams();
            object.gravity = 8388611 | Toolbar.this.mButtonGravity & 112;
            object.mViewType = 2;
            Toolbar.this.mExpandedActionView.setLayoutParams((ViewGroup.LayoutParams)object);
            Toolbar.this.addView(Toolbar.this.mExpandedActionView);
        }
        Toolbar.this.removeChildrenForExpandedActionView();
        Toolbar.this.requestLayout();
        menuItemImpl.setActionViewExpanded(true);
        if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
            ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewExpanded();
        }
        return true;
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public MenuView getMenuView(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        if (this.mMenu != null && this.mCurrentExpandedItem != null) {
            this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
        }
        this.mMenu = menuBuilder;
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return null;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
    }

    @Override
    public void updateMenuView(boolean bl) {
        if (this.mCurrentExpandedItem != null) {
            boolean bl2;
            MenuBuilder menuBuilder = this.mMenu;
            boolean bl3 = bl2 = false;
            if (menuBuilder != null) {
                int n = this.mMenu.size();
                int n2 = 0;
                do {
                    bl3 = bl2;
                    if (n2 >= n) break;
                    if (this.mMenu.getItem(n2) == this.mCurrentExpandedItem) {
                        bl3 = true;
                        break;
                    }
                    ++n2;
                } while (true);
            }
            if (!bl3) {
                this.collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
            }
        }
    }
}
