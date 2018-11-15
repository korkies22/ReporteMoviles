/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.MenuItem
 */
package android.support.v7.view;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public static interface ActionMode.Callback {
    public boolean onActionItemClicked(ActionMode var1, MenuItem var2);

    public boolean onCreateActionMode(ActionMode var1, Menu var2);

    public void onDestroyActionMode(ActionMode var1);

    public boolean onPrepareActionMode(ActionMode var1, Menu var2);
}
