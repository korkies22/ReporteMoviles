// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.navigationbar;

import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;

public class MenuBarMoreActionsView extends ArrowBottomContainerView
{
    public MenuBarMoreActionsView(final Context context) {
        super(context);
        this.init();
    }
    
    public MenuBarMoreActionsView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(0);
    }
}
