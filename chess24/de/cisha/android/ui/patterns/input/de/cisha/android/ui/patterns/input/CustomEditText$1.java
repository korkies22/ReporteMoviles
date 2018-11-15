/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextWatcher
 *  android.widget.EditText
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.text.TextWatcher;
import android.widget.EditText;

class CustomEditText
extends EditText {
    CustomEditText(Context context) {
        super(context);
    }

    public void setInputType(int n) {
        this.removeTextChangedListener((TextWatcher)CustomEditText.this);
        super.setInputType(n);
        this.addTextChangedListener((TextWatcher)CustomEditText.this);
    }
}
