/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.widget.EditText
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import de.cisha.android.ui.patterns.input.CustomEditText;

public class CustomEditPassword
extends CustomEditText {
    public CustomEditPassword(Context context) {
        super(context);
        this.init();
    }

    public CustomEditPassword(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.getEditText().setInputType(129);
        this.getEditText().setTypeface(Typeface.DEFAULT);
    }
}
