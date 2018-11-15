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
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;

public abstract class AbstractNavigationBarItem
implements AnalyzeNavigationBarItem {
    @Override
    public ArrowBottomContainerView createSubmenuView(Context context) {
        return null;
    }

    @Override
    public View getContentView(Context context) {
        return null;
    }

    @Override
    public String getEventTrackingName() {
        return null;
    }

    @Override
    public void handleDeselection() {
    }

    @Override
    public boolean handleLongClick() {
        return false;
    }
}
