// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import android.util.AttributeSet;
import android.content.Context;
import android.widget.LinearLayout;

public class PrivacySettingView extends LinearLayout
{
    public PrivacySettingView(final Context context) {
        super(context);
        this.init();
    }
    
    public PrivacySettingView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
    }
}
