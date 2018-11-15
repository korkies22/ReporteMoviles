/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 */
package android.support.v4.print;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.print.PrintHelper;
import java.io.FileNotFoundException;

static interface PrintHelper.PrintHelperVersionImpl {
    public int getColorMode();

    public int getOrientation();

    public int getScaleMode();

    public void printBitmap(String var1, Bitmap var2, PrintHelper.OnPrintFinishCallback var3);

    public void printBitmap(String var1, Uri var2, PrintHelper.OnPrintFinishCallback var3) throws FileNotFoundException;

    public void setColorMode(int var1);

    public void setOrientation(int var1);

    public void setScaleMode(int var1);
}
