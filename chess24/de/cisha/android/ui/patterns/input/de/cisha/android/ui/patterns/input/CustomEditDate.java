/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import de.cisha.android.ui.patterns.input.CustomEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomEditDate
extends CustomEditText {
    private Date _date;

    public CustomEditDate(Context context) {
        super(context);
        this.init();
    }

    public CustomEditDate(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.getEditText().setInputType(0);
    }

    public Date getDate() {
        return this._date;
    }

    public void setDate(Date date) {
        this._date = date;
        if (date != null) {
            this.setText(SimpleDateFormat.getDateInstance(3, Locale.getDefault()).format(date));
            return;
        }
        this.setText("");
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        if (this.getEditText() != null) {
            this.getEditText().setOnClickListener(onClickListener);
        }
    }
}
