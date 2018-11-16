// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.buttons;

import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;

public class CustomButtonAlternative extends CustomButton
{
    public CustomButtonAlternative(final Context context) {
        super(context);
    }
    
    public CustomButtonAlternative(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_alternative;
    }
    
    @Override
    protected int getTextColorInactive() {
        return this.getResources().getColor(R.color.custom_button_alternative_text_inactive);
    }
}
