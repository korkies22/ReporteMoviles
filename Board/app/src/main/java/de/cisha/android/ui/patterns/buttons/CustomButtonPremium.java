// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.buttons;

import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;

public class CustomButtonPremium extends CustomButton
{
    public CustomButtonPremium(final Context context) {
        super(context);
    }
    
    public CustomButtonPremium(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_premium;
    }
    
    @Override
    protected int getTextColorActive() {
        return -1;
    }
    
    @Override
    protected int getTextColorDefault() {
        return -1;
    }
    
    @Override
    protected int getTextColorInactive() {
        return -1;
    }
}
