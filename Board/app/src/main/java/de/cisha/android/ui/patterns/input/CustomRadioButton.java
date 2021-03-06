// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.input;

import android.os.Build.VERSION;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.RadioButton;

public class CustomRadioButton extends RadioButton
{
    public CustomRadioButton(final Context context) {
        super(context);
        this.init();
    }
    
    public CustomRadioButton(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setButtonDrawable(this.getResources().getDrawable(R.drawable.radio_button_statelist));
        this.setBackgroundResource(R.drawable.checkbox_background_statelist);
        this.setPadding(this.getPaddingLeft(), 0, 0, 0);
        this.setTextColor(this.getResources().getColor(R.color.check_box_colorstatelist));
    }
    
    public int getCompoundPaddingLeft() {
        final int n = (int)(10.0f * this.getResources().getDisplayMetrics().density + 0.5f);
        if (Build.VERSION.SDK_INT < 17) {
            return super.getCompoundPaddingLeft() - n;
        }
        return super.getCompoundPaddingLeft() + n;
    }
}
