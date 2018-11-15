/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.text.TextWatcher
 */
package android.support.design.widget;

import android.text.Editable;
import android.text.TextWatcher;

class TextInputLayout
implements TextWatcher {
    TextInputLayout() {
    }

    public void afterTextChanged(Editable editable) {
        TextInputLayout.this.updateLabelState(TextInputLayout.this.mRestoringSavedState ^ true);
        if (TextInputLayout.this.mCounterEnabled) {
            TextInputLayout.this.updateCounter(editable.length());
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }
}
