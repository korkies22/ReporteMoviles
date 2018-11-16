// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.input;

import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.Spinner;
import android.view.View.OnFocusChangeListener;

public class CustomSpinner extends CustomInput implements View.OnFocusChangeListener
{
    private Spinner _spinner;
    
    public CustomSpinner(final Context context) {
        super(context);
        this.init();
    }
    
    public CustomSpinner(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this._spinner = (Spinner)inflate(this.getContext(), R.layout.spinner, (ViewGroup)null);
        this.setOnFocusChangeListener((View.OnFocusChangeListener)this);
        this.setInputView((View)this._spinner);
    }
    
    public Spinner getSpinner() {
        return this._spinner;
    }
    
    public void onFocusChange(final View view, final boolean hintActive) {
        this.setHintActive(hintActive);
    }
}
