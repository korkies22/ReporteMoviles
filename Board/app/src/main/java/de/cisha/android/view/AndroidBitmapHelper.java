// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.view;

import android.graphics.Bitmap.Config;
import android.graphics.Bitmap;
import android.content.Context;

public class AndroidBitmapHelper
{
    static {
        System.loadLibrary("AndroidBitmapHelper");
    }
    
    public static Bitmap blurImage(final Context context, Bitmap copy, final int n) {
        Bitmap copy2 = copy;
        if (copy.getConfig() != Bitmap.Config.ARGB_8888) {
            copy2 = copy.copy(Bitmap.Config.ARGB_8888, true);
        }
        copy = copy2.copy(copy2.getConfig(), true);
        blurNative(copy2, copy, n);
        return copy;
    }
    
    private static native void blurNative(final Bitmap p0, final Bitmap p1, final int p2);
}
