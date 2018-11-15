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
package de.cisha.android.board.playzone.remote.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import de.cisha.android.board.playzone.remote.view.MultiColorSeekbarDrawable;

public class MulticolorSeekbar
extends SeekBar {
    public MulticolorSeekbar(Context context) {
        super(context);
        this.init();
    }

    public MulticolorSeekbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setProgressDrawable((Drawable)new MultiColorSeekbarDrawable());
        this.setThumb(this.getContext().getResources().getDrawable(2131231546));
    }
}
