/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package de.cisha.android.board.mainmenu.view;

import android.app.Activity;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.mainmenu.view.MenuItem;

public static interface MenuItem.MenuAction {
    public void performMenuAction(Activity var1, IContentPresenter var2);
}
