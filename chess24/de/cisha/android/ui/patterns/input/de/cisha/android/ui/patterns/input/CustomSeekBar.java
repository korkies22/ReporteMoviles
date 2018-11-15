/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.SeekBar
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import de.cisha.android.ui.patterns.R;

public class CustomSeekBar
extends SeekBar {
    public CustomSeekBar(Context context) {
        super(context);
        this.init();
    }

    public CustomSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setThumb(this.getContext().getResources().getDrawable(R.drawable.seek_thumb_normal));
        this.setThumbOffset(20);
        this.setProgressDrawable(this.getContext().getResources().getDrawable(R.drawable.seekbar_default_style));
    }
}
