/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.text.TextWatcher
 */
package android.support.v7.widget;

import android.text.Editable;
import android.text.TextWatcher;

class SearchView
implements TextWatcher {
    SearchView() {
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        SearchView.this.onTextChanged(charSequence);
    }
}
