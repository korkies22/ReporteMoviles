/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Matrix
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.CancellationSignal$OnCancelListener
 *  android.os.ParcelFileDescriptor
 *  android.print.PageRange
 *  android.print.PrintAttributes
 *  android.print.PrintAttributes$MediaSize
 *  android.print.PrintDocumentAdapter
 *  android.print.PrintDocumentAdapter$LayoutResultCallback
 *  android.print.PrintDocumentAdapter$WriteResultCallback
 *  android.print.PrintDocumentInfo
 *  android.print.PrintDocumentInfo$Builder
 */
package android.support.v4.print;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.support.v4.print.PrintHelper;
import java.io.FileNotFoundException;

class PrintHelper.PrintHelperApi19
extends PrintDocumentAdapter {
    private PrintAttributes mAttributes;
    Bitmap mBitmap = null;
    AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
    final /* synthetic */ PrintHelper.OnPrintFinishCallback val$callback;
    final /* synthetic */ int val$fittingMode;
    final /* synthetic */ Uri val$imageFile;
    final /* synthetic */ String val$jobName;

    PrintHelper.PrintHelperApi19(String string, Uri uri, PrintHelper.OnPrintFinishCallback onPrintFinishCallback, int n) {
        this.val$jobName = string;
        this.val$imageFile = uri;
        this.val$callback = onPrintFinishCallback;
        this.val$fittingMode = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cancelLoad() {
        Object object = PrintHelperApi19.this.mLock;
        synchronized (object) {
            if (PrintHelperApi19.this.mDecodeOptions != null) {
                PrintHelperApi19.this.mDecodeOptions.requestCancelDecode();
                PrintHelperApi19.this.mDecodeOptions = null;
            }
            return;
        }
    }

    public void onFinish() {
        super.onFinish();
        this.cancelLoad();
        if (this.mLoadBitmap != null) {
            this.mLoadBitmap.cancel(true);
        }
        if (this.val$callback != null) {
            this.val$callback.onFinish();
        }
        if (this.mBitmap != null) {
            this.mBitmap.recycle();
            this.mBitmap = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onLayout(final PrintAttributes printAttributes, final PrintAttributes printAttributes2, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
        // MONITORENTER : this
        this.mAttributes = printAttributes2;
        // MONITOREXIT : this
        if (cancellationSignal.isCanceled()) {
            layoutResultCallback.onLayoutCancelled();
            return;
        }
        if (this.mBitmap != null) {
            layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.val$jobName).setContentType(1).setPageCount(1).build(), printAttributes2.equals((Object)printAttributes) ^ true);
            return;
        }
        this.mLoadBitmap = new AsyncTask<Uri, Boolean, Bitmap>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            protected /* varargs */ Bitmap doInBackground(Uri ... bitmap) {
                try {
                    return PrintHelperApi19.this.loadConstrainedBitmap(3.this.val$imageFile);
                }
                catch (FileNotFoundException fileNotFoundException) {
                    return null;
                }
            }

            protected void onCancelled(Bitmap bitmap) {
                layoutResultCallback.onLayoutCancelled();
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
                        if (!PrintHelperApi19.this.mPrintActivityRespectsOrientation) break block11;
                        bitmap2 = bitmap;
                        if (PrintHelperApi19.this.mOrientation != 0) break block10;
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
                    boolean bl = printAttributes2.equals((Object)printAttributes);
                    layoutResultCallback.onLayoutFinished((PrintDocumentInfo)bitmap, true ^ bl);
                } else {
                    layoutResultCallback.onLayoutFailed(null);
                }
                3.this.mLoadBitmap = null;
            }

            protected void onPreExecute() {
                cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){

                    public void onCancel() {
                        3.this.cancelLoad();
                        1.this.cancel(false);
                    }
                });
            }

        }.execute((Object[])new Uri[0]);
    }

    public void onWrite(PageRange[] arrpageRange, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        PrintHelperApi19.this.writeBitmap(this.mAttributes, this.val$fittingMode, this.mBitmap, parcelFileDescriptor, cancellationSignal, writeResultCallback);
    }

}
