// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.input;

import android.text.Editable;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.EditText;
import android.view.View.OnFocusChangeListener;
import android.text.TextWatcher;

public class CustomEditText extends CustomInput implements TextWatcher, View.OnFocusChangeListener
{
    private EditText _editText;
    
    public CustomEditText(final Context context) {
        super(context);
        this.initialize();
    }
    
    public CustomEditText(final Context context, final AttributeSet set) {
        super(context, set);
        this.initialize();
    }
    
    private void initialize() {
        (this._editText = new EditText(this.getContext()) {
            public void setInputType(final int inputType) {
                this.removeTextChangedListener((TextWatcher)CustomEditText.this);
                super.setInputType(inputType);
                this.addTextChangedListener((TextWatcher)CustomEditText.this);
            }
        }).setHint((CharSequence)this.getHintString());
        this._editText.setOnFocusChangeListener((View.OnFocusChangeListener)this);
        this._editText.addTextChangedListener((TextWatcher)this);
        this.setInputView((View)this._editText);
        this.showHint(false);
    }
    
    public void afterTextChanged(final Editable editable) {
    }
    
    public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
    }
    
    public EditText getEditText() {
        return this._editText;
    }
    
    public void onFocusChange(final View view, final boolean hintActive) {
        this.setHintActive(hintActive);
    }
    
    public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
        this._editText.post((Runnable)new Runnable() {
            @Override
            public void run() {
                final String string = CustomEditText.this._editText.getText().toString();
                CustomEditText.this.updateHint(string != null && string.length() > 0);
            }
        });
    }
    
    @Override
    public void setHint(final String s) {
        super.setHint(s);
        if (this._editText != null) {
            this._editText.setHint((CharSequence)s);
        }
    }
    
    public void setText(final CharSequence text) {
        this._editText.setOnFocusChangeListener((View.OnFocusChangeListener)null);
        this._editText.removeTextChangedListener((TextWatcher)this);
        this._editText.setText(text);
        this._editText.addTextChangedListener((TextWatcher)this);
        this._editText.setOnFocusChangeListener((View.OnFocusChangeListener)this);
        this.showHint(text != null && !"".equals(text));
    }
}
