/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class PrivacySettingView
extends LinearLayout {
    public PrivacySettingView(Context context) {
        super(context);
        this.init();
    }

    public PrivacySettingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
    }
}
