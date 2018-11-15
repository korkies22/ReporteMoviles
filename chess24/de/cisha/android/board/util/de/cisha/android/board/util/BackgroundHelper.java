/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package de.cisha.android.board.util;

import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class BackgroundHelper {
    private BackgroundHelper() {
    }

    public static void fixBackgroundRepeat(View view) {
        if ((view = view.getBackground()) != null && view instanceof BitmapDrawable) {
            view = (BitmapDrawable)view;
            view.mutate();
            view.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }
    }
}
