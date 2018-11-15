/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 */
package de.cisha.android.ui.patterns.buttons;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.buttons.CustomButton;

public class CustomButtonPositive
extends CustomButton {
    public CustomButtonPositive(Context context) {
        super(context);
    }

    public CustomButtonPositive(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_positive;
    }

    @Override
    protected int getTextColorInactive() {
        return this.getResources().getColor(R.color.custom_button_positive_text_inactive);
    }
}
