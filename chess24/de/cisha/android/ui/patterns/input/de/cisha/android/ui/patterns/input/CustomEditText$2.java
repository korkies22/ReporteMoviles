/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.widget.EditText
 */
package de.cisha.android.ui.patterns.input;

import android.text.Editable;
import android.widget.EditText;

class CustomEditText
implements Runnable {
    CustomEditText() {
    }

    @Override
    public void run() {
        String string = CustomEditText.this._editText.getText().toString();
        de.cisha.android.ui.patterns.input.CustomEditText customEditText = CustomEditText.this;
        boolean bl = string != null && string.length() > 0;
        customEditText.updateHint(bl);
    }
}
