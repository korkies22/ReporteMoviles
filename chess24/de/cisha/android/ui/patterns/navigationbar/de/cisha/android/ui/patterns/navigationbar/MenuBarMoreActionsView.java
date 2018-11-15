/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package de.cisha.android.ui.patterns.navigationbar;

import android.content.Context;
import android.util.AttributeSet;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;

public class MenuBarMoreActionsView
extends ArrowBottomContainerView {
    public MenuBarMoreActionsView(Context context) {
        super(context);
        this.init();
    }

    public MenuBarMoreActionsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(0);
    }
}
