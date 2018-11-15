/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Matrix
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.CancellationSignal
 *  android.os.CancellationSignal$OnCancelListener
 *  android.print.PrintAttributes
 *  android.print.PrintAttributes$MediaSize
 *  android.print.PrintDocumentAdapter
 *  android.print.PrintDocumentAdapter$LayoutResultCallback
 *  android.print.PrintDocumentInfo
 *  android.print.PrintDocumentInfo$Builder
 */
package android.support.v4.print;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.support.v4.print.PrintHelper;
import java.io.FileNotFoundException;

class PrintHelper.PrintHelperApi19
extends AsyncTask<Uri, Boolean, Bitmap> {
    final /* synthetic */ CancellationSignal val$cancellationSignal;
    final /* synthetic */ PrintDocumentAdapter.LayoutResultCallback val$layoutResultCallback;
    final /* synthetic */ PrintAttributes val$newPrintAttributes;
    final /* synthetic */ PrintAttributes val$oldPrintAttributes;

    PrintHelper.PrintHelperApi19(CancellationSignal cancellationSignal, PrintAttributes printAttributes, PrintAttributes printAttributes2, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback) {
        this.val$cancellationSignal = cancellationSignal;
        this.val$newPrintAttributes = printAttributes;
        this.val$oldPrintAttributes = printAttributes2;
        this.val$layoutResultCallback = layoutResultCallback;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected /* varargs */ Bitmap doInBackground(Uri ... bitmap) {
        try {
            return 3.this.this$0.loadConstrainedBitmap(3.this.val$imageFile);
        }
        catch (FileNotFoundException fileNotFoundException) {
            return null;
        }
    }

    protected void onCancelled(Bitmap bitmap) {
        this.val$layoutResultCallback.onLayoutCancelled();
        3.this.mLoadBitmap = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onPostExecute(Bitmap bitmap) {
        Bitmap bitmap2;
        block10 : {
            PrintAttributes.MediaSize mediaSize;
            block11 : {
                super.onPostExecute((Object)bitmap);
                bitmap2 = bitmap;
                if (bitmap == null) break block10;
                if (!3.this.this$0.mPrintActivityRespectsOrientation) break block11;
                bitmap2 = bitmap;
                if (3.this.this$0.mOrientation != 0) break block10;
            }
            synchronized (this) {
                mediaSize = 3.this.mAttributes.getMediaSize();
            }
            bitmap2 = bitmap;
            if (mediaSize != null) {
                bitmap2 = bitmap;
                if (mediaSize.isPortrait() != PrintHelper.PrintHelperApi19.isPortrait(bitmap)) {
                    bitmap2 = new Matrix();
                    bitmap2.postRotate(90.0f);
                    bitmap2 = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)bitmap.getWidth(), (int)bitmap.getHeight(), (Matrix)bitmap2, (boolean)true);
                }
            }
        }
        3.this.mBitmap = bitmap2;
        if (bitmap2 != null) {
            bitmap = new PrintDocumentInfo.Builder(3.this.val$jobName).setContentType(1).setPageCount(1).build();
            boolean bl = this.val$newPrintAttributes.equals((Object)this.val$oldPrintAttributes);
            this.val$layoutResultCallback.onLayoutFinished((PrintDocumentInfo)bitmap, true ^ bl);
        } else {
            this.val$layoutResultCallback.onLayoutFailed(null);
        }
        3.this.mLoadBitmap = null;
    }

    protected void onPreExecute() {
        this.val$cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){

            public void onCancel() {
                3.this.cancelLoad();
                1.this.cancel(false);
            }
        });
    }

}
