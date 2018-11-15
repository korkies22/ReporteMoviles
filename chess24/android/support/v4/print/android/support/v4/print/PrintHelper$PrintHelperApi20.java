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

@RequiresApi(value=20)
private static class PrintHelper.PrintHelperApi20
extends PrintHelper.PrintHelperApi19 {
    PrintHelper.PrintHelperApi20(Context context) {
        super(context);
        this.mPrintActivityRespectsOrientation = false;
    }
}
