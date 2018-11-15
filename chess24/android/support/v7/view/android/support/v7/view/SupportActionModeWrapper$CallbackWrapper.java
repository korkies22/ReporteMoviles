/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Menu
 *  android.view.MenuItem
 */
package android.support.v7.view;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportActionModeWrapper;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public static class SupportActionModeWrapper.CallbackWrapper
implements ActionMode.Callback {
    final ArrayList<SupportActionModeWrapper> mActionModes;
    final Context mContext;
    final SimpleArrayMap<Menu, Menu> mMenus;
    final ActionMode.Callback mWrappedCallback;

    public SupportActionModeWrapper.CallbackWrapper(Context context, ActionMode.Callback callback) {
        this.mContext = context;
        this.mWrappedCallback = callback;
        this.mActionModes = new ArrayList();
        this.mMenus = new SimpleArrayMap();
    }

    private Menu getMenuWrapper(Menu menu) {
        Menu menu2;
        Menu menu3 = menu2 = this.mMenus.get((Object)menu);
        if (menu2 == null) {
            menu3 = MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu)menu);
            this.mMenus.put(menu, menu3);
        }
        return menu3;
    }

    public android.view.ActionMode getActionModeWrapper(ActionMode object) {
        int n = this.mActionModes.size();
        for (int i = 0; i < n; ++i) {
            SupportActionModeWrapper supportActionModeWrapper = this.mActionModes.get(i);
            if (supportActionModeWrapper == null || supportActionModeWrapper.mWrappedObject != object) continue;
            return supportActionModeWrapper;
        }
        object = new SupportActionModeWrapper(this.mContext, (ActionMode)object);
        this.mActionModes.add((SupportActionModeWrapper)((Object)object));
        return object;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return this.mWrappedCallback.onActionItemClicked(this.getActionModeWrapper(actionMode), MenuWrapperFactory.wrapSupportMenuItem(this.mContext, (SupportMenuItem)menuItem));
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return this.mWrappedCallback.onCreateActionMode(this.getActionModeWrapper(actionMode), this.getMenuWrapper(menu));
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.mWrappedCallback.onDestroyActionMode(this.getActionModeWrapper(actionMode));
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return this.mWrappedCallback.onPrepareActionMode(this.getActionModeWrapper(actionMode), this.getMenuWrapper(menu));
    }
}
