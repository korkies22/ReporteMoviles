/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.widget.RadioButton
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RadioButton;
import de.cisha.android.ui.patterns.R;

public class CustomRadioButton
extends RadioButton {
    public CustomRadioButton(Context context) {
        super(context);
        this.init();
    }

    public CustomRadioButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setButtonDrawable(this.getResources().getDrawable(R.drawable.radio_button_statelist));
        this.setBackgroundResource(R.drawable.checkbox_background_statelist);
        this.setPadding(this.getPaddingLeft(), 0, 0, 0);
        this.setTextColor(this.getResources().getColor(R.color.check_box_colorstatelist));
    }

    public int getCompoundPaddingLeft() {
        int n = (int)(10.0f * this.getResources().getDisplayMetrics().density + 0.5f);
        if (Build.VERSION.SDK_INT < 17) {
            return super.getCompoundPaddingLeft() - n;
        }
        return super.getCompoundPaddingLeft() + n;
    }
}
