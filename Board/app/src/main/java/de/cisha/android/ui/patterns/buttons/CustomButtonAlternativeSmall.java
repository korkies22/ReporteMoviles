// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.buttons;

import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;

public class CustomButtonAlternativeSmall extends CustomButtonAlternative
{
    public CustomButtonAlternativeSmall(final Context context) {
        super(context);
    }
    
    public CustomButtonAlternativeSmall(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    @Override
    protected int getBackgroundResourceId() {
        return R.drawable.custom_button_alternative_small;
    }
    
    @Override
    protected int getTextSizeResDimenId() {
        return R.dimen.custom_button_neutral_small_text_size;
    }
}
