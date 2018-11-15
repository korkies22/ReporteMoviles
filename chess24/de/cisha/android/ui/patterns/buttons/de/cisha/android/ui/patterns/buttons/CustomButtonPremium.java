/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package de.cisha.android.ui.patterns.buttons;

import android.content.Context;
import android.util.AttributeSet;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.buttons.CustomButton;

public class CustomButtonPremium
extends CustomButton {
    public CustomButtonPremium(Context context) {
        super(context);
    }

    public CustomButtonPremium(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_premium;
    }

    @Override
    protected int getTextColorActive() {
        return -1;
    }

    @Override
    protected int getTextColorDefault() {
        return -1;
    }

    @Override
    protected int getTextColorInactive() {
        return -1;
    }
}
