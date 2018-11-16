// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.buttons;

import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;

public class CustomButtonPositive extends CustomButton
{
    public CustomButtonPositive(final Context context) {
        super(context);
    }
    
    public CustomButtonPositive(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_positive;
    }
    
    @Override
    protected int getTextColorInactive() {
        return this.getResources().getColor(R.color.custom_button_positive_text_inactive);
    }
}
