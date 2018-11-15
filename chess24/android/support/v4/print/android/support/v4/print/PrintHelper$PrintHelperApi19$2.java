/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.pdf.PdfDocument
 *  android.graphics.pdf.PdfDocument$Page
 *  android.graphics.pdf.PdfDocument$PageInfo
 *  android.os.AsyncTask
 *  android.os.CancellationSignal
 *  android.os.ParcelFileDescriptor
 *  android.print.PageRange
 *  android.print.PrintAttributes
 *  android.print.PrintDocumentAdapter
 *  android.print.PrintDocumentAdapter$WriteResultCallback
 *  android.print.pdf.PrintedPdfDocument
 *  android.util.Log
 */
package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class PrintHelper.PrintHelperApi19
extends AsyncTask<Void, Void, Throwable> {
    final /* synthetic */ PrintAttributes val$attributes;
    final /* synthetic */ Bitmap val$bitmap;
    final /* synthetic */ CancellationSignal val$cancellationSignal;
    final /* synthetic */ ParcelFileDescriptor val$fileDescriptor;
    final /* synthetic */ int val$fittingMode;
    final /* synthetic */ PrintAttributes val$pdfAttributes;
    final /* synthetic */ PrintDocumentAdapter.WriteResultCallback val$writeResultCallback;

    PrintHelper.PrintHelperApi19(CancellationSignal cancellationSignal, PrintAttributes printAttributes, Bitmap bitmap, PrintAttributes printAttributes2, int n, ParcelFileDescriptor parcelFileDescriptor, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        this.val$cancellationSignal = cancellationSignal;
        this.val$pdfAttributes = printAttributes;
        this.val$bitmap = bitmap;
        this.val$attributes = printAttributes2;
        this.val$fittingMode = n;
        this.val$fileDescriptor = parcelFileDescriptor;
        this.val$writeResultCallback = writeResultCallback;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    protected /* varargs */ Throwable doInBackground(Void ... var1_1) {
        block28 : {
            block27 : {
                block26 : {
                    if (this.val$cancellationSignal.isCanceled()) {
                        return null;
                    }
                    var4_6 = new PrintedPdfDocument(PrintHelperApi19.this.mContext, this.val$pdfAttributes);
                    var3_8 = PrintHelper.PrintHelperApi19.access$100(PrintHelperApi19.this, this.val$bitmap, this.val$pdfAttributes.getColorMode());
                    var2_9 = this.val$cancellationSignal.isCanceled();
                    if (!var2_9) break block26;
                    return null;
                }
                var5_10 = var4_6.startPage(1);
                if (PrintHelperApi19.this.mIsMinMarginsHandlingCorrect) {
                    var1_1 = new RectF(var5_10.getInfo().getContentRect());
                } else {
                    var6_11 = new PrintedPdfDocument(PrintHelperApi19.this.mContext, this.val$attributes);
                    var7_12 = var6_11.startPage(1);
                    var1_1 = new RectF(var7_12.getInfo().getContentRect());
                    var6_11.finishPage(var7_12);
                    var6_11.close();
                }
                var6_11 = PrintHelper.PrintHelperApi19.access$200(PrintHelperApi19.this, var3_8.getWidth(), var3_8.getHeight(), var1_1, this.val$fittingMode);
                if (!PrintHelperApi19.this.mIsMinMarginsHandlingCorrect) {
                    var6_11.postTranslate(var1_1.left, var1_1.top);
                    var5_10.getCanvas().clipRect(var1_1);
                }
                var5_10.getCanvas().drawBitmap(var3_8, (Matrix)var6_11, null);
                var4_6.finishPage(var5_10);
                var2_9 = this.val$cancellationSignal.isCanceled();
                if (!var2_9) break block27;
                var4_6.close();
                var1_1 = this.val$fileDescriptor;
                if (var1_1 != null) {
                    this.val$fileDescriptor.close();
                }
lbl34: // 4 sources:
                do {
                    if (var3_8 != this.val$bitmap) {
                        var3_8.recycle();
                    }
                    return null;
                    break;
                } while (true);
            }
            var4_6.writeTo((OutputStream)new FileOutputStream(this.val$fileDescriptor.getFileDescriptor()));
            try {
                var4_6.close();
                var1_1 = this.val$fileDescriptor;
                if (var1_1 != null) {
                    this.val$fileDescriptor.close();
                }
lbl47: // 4 sources:
                if (var3_8 != this.val$bitmap) {
                    var3_8.recycle();
                    return null;
                }
                break block28;
                catch (Throwable var1_2) {
                    var4_6.close();
                    var4_6 = this.val$fileDescriptor;
                    if (var4_6 != null) {
                        this.val$fileDescriptor.close();
                    }
lbl57: // 4 sources:
                    do {
                        if (var3_8 != this.val$bitmap) {
                            var3_8.recycle();
                        }
                        throw var1_2;
                        break;
                    } while (true);
                }
            }
            catch (Throwable var1_3) {
                return var1_3;
            }
            {
                catch (IOException var1_4) {
                    ** continue;
                }
            }
            {
                catch (IOException var1_5) {
                    ** GOTO lbl47
                }
                {
                    catch (IOException var4_7) {
                        ** continue;
                    }
                }
            }
        }
        return null;
    }

    protected void onPostExecute(Throwable throwable) {
        if (this.val$cancellationSignal.isCanceled()) {
            this.val$writeResultCallback.onWriteCancelled();
            return;
        }
        if (throwable == null) {
            this.val$writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
            return;
        }
        Log.e((String)PrintHelper.PrintHelperApi19.LOG_TAG, (String)"Error writing printed content", (Throwable)throwable);
        this.val$writeResultCallback.onWriteFailed(null);
    }
}
