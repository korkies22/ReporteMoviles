/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 */
package de.cisha.android.board.analyze.navigationbaritems;

import android.content.Context;
import android.view.View;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

public interface AnalyzeNavigationBarItem {
    public ArrowBottomContainerView createSubmenuView(Context var1);

    public View getContentView(Context var1);

    public String getEventTrackingName();

    public MenuBarItem getMenuItem(Context var1);

    public void handleClick();

    public void handleDeselection();

    public boolean handleLongClick();
}
