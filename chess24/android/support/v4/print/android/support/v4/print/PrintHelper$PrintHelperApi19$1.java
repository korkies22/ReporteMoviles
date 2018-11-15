/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.ParcelFileDescriptor
 *  android.print.PageRange
 *  android.print.PrintAttributes
 *  android.print.PrintDocumentAdapter
 *  android.print.PrintDocumentAdapter$LayoutResultCallback
 *  android.print.PrintDocumentAdapter$WriteResultCallback
 *  android.print.PrintDocumentInfo
 *  android.print.PrintDocumentInfo$Builder
 */
package android.support.v4.print;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.support.v4.print.PrintHelper;

class PrintHelper.PrintHelperApi19
extends PrintDocumentAdapter {
    private PrintAttributes mAttributes;
    final /* synthetic */ Bitmap val$bitmap;
    final /* synthetic */ PrintHelper.OnPrintFinishCallback val$callback;
    final /* synthetic */ int val$fittingMode;
    final /* synthetic */ String val$jobName;

    PrintHelper.PrintHelperApi19(String string, int n, Bitmap bitmap, PrintHelper.OnPrintFinishCallback onPrintFinishCallback) {
        this.val$jobName = string;
        this.val$fittingMode = n;
        this.val$bitmap = bitmap;
        this.val$callback = onPrintFinishCallback;
    }

    public void onFinish() {
        if (this.val$callback != null) {
            this.val$callback.onFinish();
        }
    }

    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
        this.mAttributes = printAttributes2;
        layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.val$jobName).setContentType(1).setPageCount(1).build(), printAttributes2.equals((Object)printAttributes) ^ true);
    }

    public void onWrite(PageRange[] arrpageRange, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        PrintHelperApi19.this.writeBitmap(this.mAttributes, this.val$fittingMode, this.val$bitmap, parcelFileDescriptor, cancellationSignal, writeResultCallback);
    }
}
