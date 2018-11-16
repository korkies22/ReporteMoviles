// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import android.view.View;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import android.content.Context;

public abstract class AbstractNavigationBarItem implements AnalyzeNavigationBarItem
{
    @Override
    public ArrowBottomContainerView createSubmenuView(final Context context) {
        return null;
    }
    
    @Override
    public View getContentView(final Context context) {
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
