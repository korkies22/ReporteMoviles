/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.util.AttributeSet
 */
package de.cisha.android.ui.patterns.buttons;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.buttons.CustomButton;

public class CustomButtonNeutral
extends CustomButton {
    public CustomButtonNeutral(Context context) {
        super(context);
        this.init();
    }

    public CustomButtonNeutral(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setShadowLayer(1.0f, 0.0f, 1.0f, Color.argb((int)255, (int)255, (int)255, (int)255));
    }

    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_neutral;
    }

    @Override
    protected int getTextColorActive() {
        return this.getResources().getColor(R.color.custom_button_neutral_text_active);
    }

    @Override
    protected int getTextColorDefault() {
        return this.getResources().getColor(R.color.custom_button_neutral_text_passive);
    }

    @Override
    protected int getTextColorInactive() {
        return this.getResources().getColor(R.color.custom_button_neutral_text_inactive);
    }
}
