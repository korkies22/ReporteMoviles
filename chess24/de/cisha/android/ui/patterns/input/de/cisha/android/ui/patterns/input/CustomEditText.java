/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 *  android.widget.EditText
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import de.cisha.android.ui.patterns.input.CustomInput;

public class CustomEditText
extends CustomInput
implements TextWatcher,
View.OnFocusChangeListener {
    private EditText _editText;

    public CustomEditText(Context context) {
        super(context);
        this.initialize();
    }

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialize();
    }

    private void initialize() {
        this._editText = new EditText(this.getContext()){

            public void setInputType(int n) {
                this.removeTextChangedListener((TextWatcher)CustomEditText.this);
                super.setInputType(n);
                this.addTextChangedListener((TextWatcher)CustomEditText.this);
            }
        };
        this._editText.setHint((CharSequence)this.getHintString());
        this._editText.setOnFocusChangeListener((View.OnFocusChangeListener)this);
        this._editText.addTextChangedListener((TextWatcher)this);
        this.setInputView((View)this._editText);
        this.showHint(false);
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    public EditText getEditText() {
        return this._editText;
    }

    public void onFocusChange(View view, boolean bl) {
        this.setHintActive(bl);
    }

    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        this._editText.post(new Runnable(){

            @Override
            public void run() {
                String string = CustomEditText.this._editText.getText().toString();
                CustomEditText customEditText = CustomEditText.this;
                boolean bl = string != null && string.length() > 0;
                customEditText.updateHint(bl);
            }
        });
    }

    @Override
    public void setHint(String string) {
        super.setHint(string);
        if (this._editText != null) {
            this._editText.setHint((CharSequence)string);
        }
    }

    public void setText(CharSequence charSequence) {
        this._editText.setOnFocusChangeListener(null);
        this._editText.removeTextChangedListener((TextWatcher)this);
        this._editText.setText(charSequence);
        this._editText.addTextChangedListener((TextWatcher)this);
        this._editText.setOnFocusChangeListener((View.OnFocusChangeListener)this);
        boolean bl = charSequence != null && !"".equals(charSequence);
        this.showHint(bl);
    }

}
