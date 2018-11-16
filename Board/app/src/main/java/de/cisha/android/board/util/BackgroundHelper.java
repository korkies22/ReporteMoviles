// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.util;

import android.graphics.drawable.Drawable;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class BackgroundHelper
{
    public static void fixBackgroundRepeat(final View view) {
        final Drawable background = view.getBackground();
        if (background != null && background instanceof BitmapDrawable) {
            final BitmapDrawable bitmapDrawable = (BitmapDrawable)background;
            bitmapDrawable.mutate();
            bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }
    }
}
