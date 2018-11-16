// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.input;

import android.view.View.OnClickListener;
import java.text.DateFormat;
import java.util.Locale;
import android.util.AttributeSet;
import android.content.Context;
import java.util.Date;

public class CustomEditDate extends CustomEditText
{
    private Date _date;
    
    public CustomEditDate(final Context context) {
        super(context);
        this.init();
    }
    
    public CustomEditDate(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.getEditText().setInputType(0);
    }
    
    public Date getDate() {
        return this._date;
    }
    
    public void setDate(final Date date) {
        this._date = date;
        if (date != null) {
            this.setText(DateFormat.getDateInstance(3, Locale.getDefault()).format(date));
            return;
        }
        this.setText("");
    }
    
    public void setOnClickListener(final View.OnClickListener view.OnClickListener) {
        super.setOnClickListener(view.OnClickListener);
        if (this.getEditText() != null) {
            this.getEditText().setOnClickListener(view.OnClickListener);
        }
    }
}
