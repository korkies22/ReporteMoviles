/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package android.support.v4.print;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.v4.print.PrintHelper;

@RequiresApi(value=24)
private static class PrintHelper.PrintHelperApi24
extends PrintHelper.PrintHelperApi23 {
    PrintHelper.PrintHelperApi24(Context context) {
        super(context);
        this.mIsMinMarginsHandlingCorrect = true;
        this.mPrintActivityRespectsOrientation = true;
    }
}
