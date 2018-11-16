// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.buttons;

import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;

public class CustomButtonPremiumSmall extends CustomButtonPremium
{
    public CustomButtonPremiumSmall(final Context context) {
        super(context);
    }
    
    public CustomButtonPremiumSmall(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.button_orange_small_selector;
    }
    
    @Override
    protected int getTextSizeResDimenId() {
        return R.dimen.custom_button_neutral_small_text_size;
    }
}
