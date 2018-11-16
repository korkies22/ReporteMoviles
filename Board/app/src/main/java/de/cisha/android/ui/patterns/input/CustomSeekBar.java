// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.input;

import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.SeekBar;

public class CustomSeekBar extends SeekBar
{
    public CustomSeekBar(final Context context) {
        super(context);
        this.init();
    }
    
    public CustomSeekBar(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setThumb(this.getContext().getResources().getDrawable(R.drawable.seek_thumb_normal));
        this.setThumbOffset(20);
        this.setProgressDrawable(this.getContext().getResources().getDrawable(R.drawable.seekbar_default_style));
    }
}
