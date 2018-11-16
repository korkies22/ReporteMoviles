// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import android.view.View;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import android.content.Context;

public interface AnalyzeNavigationBarItem
{
    ArrowBottomContainerView createSubmenuView(final Context p0);
    
    View getContentView(final Context p0);
    
    String getEventTrackingName();
    
    MenuBarItem getMenuItem(final Context p0);
    
    void handleClick();
    
    void handleDeselection();
    
    boolean handleLongClick();
}
