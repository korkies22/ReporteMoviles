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
import de.cisha.android.ui.patterns.buttons.CustomButtonAlternative;

public class CustomButtonAlternativeSmall
extends CustomButtonAlternative {
    public CustomButtonAlternativeSmall(Context context) {
        super(context);
    }

    public CustomButtonAlternativeSmall(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_alternative_small;
    }

    @Override
    protected int getTextSizeResDimenId() {
        return R.dimen.custom_button_neutral_small_text_size;
    }
}
