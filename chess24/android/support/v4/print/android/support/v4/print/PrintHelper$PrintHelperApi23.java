/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.print.PrintAttributes
 *  android.print.PrintAttributes$Builder
 */
package android.support.v4.print;

import android.content.Context;
import android.print.PrintAttributes;
import android.support.annotation.RequiresApi;
import android.support.v4.print.PrintHelper;

@RequiresApi(value=23)
private static class PrintHelper.PrintHelperApi23
extends PrintHelper.PrintHelperApi20 {
    PrintHelper.PrintHelperApi23(Context context) {
        super(context);
        this.mIsMinMarginsHandlingCorrect = false;
    }

    @Override
    protected PrintAttributes.Builder copyAttributes(PrintAttributes printAttributes) {
        PrintAttributes.Builder builder = super.copyAttributes(printAttributes);
        if (printAttributes.getDuplexMode() != 0) {
            builder.setDuplexMode(printAttributes.getDuplexMode());
        }
        return builder;
    }
}
