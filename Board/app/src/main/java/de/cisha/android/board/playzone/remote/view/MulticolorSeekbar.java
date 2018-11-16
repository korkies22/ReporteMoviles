// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.SeekBar;

public class MulticolorSeekbar extends SeekBar
{
    public MulticolorSeekbar(final Context context) {
        super(context);
        this.init();
    }
    
    public MulticolorSeekbar(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setProgressDrawable((Drawable)new MultiColorSeekbarDrawable());
        this.setThumb(this.getContext().getResources().getDrawable(2131231546));
    }
}
