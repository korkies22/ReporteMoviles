/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v7.app;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.RestrictTo;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.DecorToolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class WindowDecorActionBar.ActionModeImpl
extends ActionMode
implements MenuBuilder.Callback {
    private final Context mActionModeContext;
    private ActionMode.Callback mCallback;
    private WeakReference<View> mCustomView;
    private final MenuBuilder mMenu;

    public WindowDecorActionBar.ActionModeImpl(Context context, ActionMode.Callback callback) {
        this.mActionModeContext = context;
        this.mCallback = callback;
        this.mMenu = new MenuBuilder(context).setDefaultShowAsAction(1);
        this.mMenu.setCallback(this);
    }

    public boolean dispatchOnCreate() {
        this.mMenu.stopDispatchingItemsChanged();
        try {
            boolean bl = this.mCallback.onCreateActionMode(this, this.mMenu);
            return bl;
        }
        finally {
            this.mMenu.startDispatchingItemsChanged();
        }
    }

    @Override
    public void finish() {
        if (WindowDecorActionBar.this.mActionMode != this) {
            return;
        }
        if (!WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false)) {
            WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
            WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
        } else {
            this.mCallback.onDestroyActionMode(this);
        }
        this.mCallback = null;
        WindowDecorActionBar.this.animateToMode(false);
        WindowDecorActionBar.this.mContextView.closeMode();
        WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
        WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
        WindowDecorActionBar.this.mActionMode = null;
    }

    @Override
    public View getCustomView() {
        if (this.mCustomView != null) {
            return (View)this.mCustomView.get();
        }
        return null;
    }

    @Override
    public Menu getMenu() {
        return this.mMenu;
    }

    @Override
    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.mActionModeContext);
    }

    @Override
    public CharSequence getSubtitle() {
        return WindowDecorActionBar.this.mContextView.getSubtitle();
    }

    @Override
    public CharSequence getTitle() {
        return WindowDecorActionBar.this.mContextView.getTitle();
    }

    @Override
    public void invalidate() {
        if (WindowDecorActionBar.this.mActionMode != this) {
            return;
        }
        this.mMenu.stopDispatchingItemsChanged();
        try {
            this.mCallback.onPrepareActionMode(this, this.mMenu);
            return;
        }
        finally {
            this.mMenu.startDispatchingItemsChanged();
        }
    }

    @Override
    public boolean isTitleOptional() {
        return WindowDecorActionBar.this.mContextView.isTitleOptional();
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
    }

    public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        if (this.mCallback != null) {
            return this.mCallback.onActionItemClicked(this, menuItem);
        }
        return false;
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        if (this.mCallback == null) {
            return;
        }
        this.invalidate();
        WindowDecorActionBar.this.mContextView.showOverflowMenu();
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (this.mCallback == null) {
            return false;
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show();
        return true;
    }

    @Override
    public void setCustomView(View view) {
        WindowDecorActionBar.this.mContextView.setCustomView(view);
        this.mCustomView = new WeakReference<View>(view);
    }

    @Override
    public void setSubtitle(int n) {
        this.setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(n));
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
        WindowDecorActionBar.this.mContextView.setSubtitle(charSequence);
    }

    @Override
    public void setTitle(int n) {
        this.setTitle(WindowDecorActionBar.this.mContext.getResources().getString(n));
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        WindowDecorActionBar.this.mContextView.setTitle(charSequence);
    }

    @Override
    public void setTitleOptionalHint(boolean bl) {
        super.setTitleOptionalHint(bl);
        WindowDecorActionBar.this.mContextView.setTitleOptional(bl);
    }
}
