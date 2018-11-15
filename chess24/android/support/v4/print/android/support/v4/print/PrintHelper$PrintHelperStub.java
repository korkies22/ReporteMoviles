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

private static final class PrintHelper.PrintHelperStub
implements PrintHelper.PrintHelperVersionImpl {
    int mColorMode = 2;
    int mOrientation = 1;
    int mScaleMode = 2;

    private PrintHelper.PrintHelperStub() {
    }

    @Override
    public int getColorMode() {
        return this.mColorMode;
    }

    @Override
    public int getOrientation() {
        return this.mOrientation;
    }

    @Override
    public int getScaleMode() {
        return this.mScaleMode;
    }

    @Override
    public void printBitmap(String string, Bitmap bitmap, PrintHelper.OnPrintFinishCallback onPrintFinishCallback) {
    }

    @Override
    public void printBitmap(String string, Uri uri, PrintHelper.OnPrintFinishCallback onPrintFinishCallback) {
    }

    @Override
    public void setColorMode(int n) {
        this.mColorMode = n;
    }

    @Override
    public void setOrientation(int n) {
        this.mOrientation = n;
    }

    @Override
    public void setScaleMode(int n) {
        this.mScaleMode = n;
    }
}
