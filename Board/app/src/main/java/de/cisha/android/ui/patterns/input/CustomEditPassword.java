// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.input;

import android.graphics.Typeface;
import android.util.AttributeSet;
import android.content.Context;

public class CustomEditPassword extends CustomEditText
{
    public CustomEditPassword(final Context context) {
        super(context);
        this.init();
    }
    
    public CustomEditPassword(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.getEditText().setInputType(129);
        this.getEditText().setTypeface(Typeface.DEFAULT);
    }
}
