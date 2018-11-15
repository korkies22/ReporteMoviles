/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 *  android.widget.Spinner
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.input.CustomInput;

public class CustomSpinner
extends CustomInput
implements View.OnFocusChangeListener {
    private Spinner _spinner;

    public CustomSpinner(Context context) {
        super(context);
        this.init();
    }

    public CustomSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this._spinner = (Spinner)CustomSpinner.inflate((Context)this.getContext(), (int)R.layout.spinner, null);
        this.setOnFocusChangeListener((View.OnFocusChangeListener)this);
        this.setInputView((View)this._spinner);
    }

    public Spinner getSpinner() {
        return this._spinner;
    }

    public void onFocusChange(View view, boolean bl) {
        this.setHintActive(bl);
    }
}
