// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.buttons;

import de.cisha.android.ui.patterns.R;
import android.graphics.Color;
import android.util.AttributeSet;
import android.content.Context;

public class CustomButtonNeutral extends CustomButton
{
    public CustomButtonNeutral(final Context context) {
        super(context);
        this.init();
    }
    
    public CustomButtonNeutral(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setShadowLayer(1.0f, 0.0f, 1.0f, Color.argb(255, 255, 255, 255));
    }
    
    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_neutral;
    }
    
    @Override
    protected int getTextColorActive() {
        return this.getResources().getColor(R.color.custom_button_neutral_text_active);
    }
    
    @Override
    protected int getTextColorDefault() {
        return this.getResources().getColor(R.color.custom_button_neutral_text_passive);
    }
    
    @Override
    protected int getTextColorInactive() {
        return this.getResources().getColor(R.color.custom_button_neutral_text_inactive);
    }
}
