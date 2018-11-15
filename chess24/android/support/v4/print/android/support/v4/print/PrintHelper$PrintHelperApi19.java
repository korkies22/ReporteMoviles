/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.ColorMatrix
 *  android.graphics.ColorMatrixColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.pdf.PdfDocument
 *  android.graphics.pdf.PdfDocument$Page
 *  android.graphics.pdf.PdfDocument$PageInfo
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.CancellationSignal$OnCancelListener
 *  android.os.ParcelFileDescriptor
 *  android.print.PageRange
 *  android.print.PrintAttributes
 *  android.print.PrintAttributes$Builder
 *  android.print.PrintAttributes$Margins
 *  android.print.PrintAttributes$MediaSize
 *  android.print.PrintAttributes$Resolution
 *  android.print.PrintDocumentAdapter
 *  android.print.PrintDocumentAdapter$LayoutResultCallback
 *  android.print.PrintDocumentAdapter$WriteResultCallback
 *  android.print.PrintDocumentInfo
 *  android.print.PrintDocumentInfo$Builder
 *  android.print.PrintJob
 *  android.print.PrintManager
 *  android.print.pdf.PrintedPdfDocument
 *  android.util.Log
 */
package android.support.v4.print;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.RequiresApi;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RequiresApi(value=19)
private static class PrintHelper.PrintHelperApi19
implements PrintHelper.PrintHelperVersionImpl {
    private static final String LOG_TAG = "PrintHelperApi19";
    private static final int MAX_PRINT_SIZE = 3500;
    int mColorMode = 2;
    final Context mContext;
    BitmapFactory.Options mDecodeOptions = null;
    protected boolean mIsMinMarginsHandlingCorrect = true;
    private final Object mLock = new Object();
    int mOrientation;
    protected boolean mPrintActivityRespectsOrientation = true;
    int mScaleMode = 2;

    PrintHelper.PrintHelperApi19(Context context) {
        this.mContext = context;
    }

    static /* synthetic */ Bitmap access$100(PrintHelper.PrintHelperApi19 printHelperApi19, Bitmap bitmap, int n) {
        return printHelperApi19.convertBitmapForColorMode(bitmap, n);
    }

    static /* synthetic */ Matrix access$200(PrintHelper.PrintHelperApi19 printHelperApi19, int n, int n2, RectF rectF, int n3) {
        return printHelperApi19.getMatrix(n, n2, rectF, n3);
    }

    private Bitmap convertBitmapForColorMode(Bitmap bitmap, int n) {
        if (n != 1) {
            return bitmap;
        }
        Bitmap bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter((ColorFilter)new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.setBitmap(null);
        return bitmap2;
    }

    private Matrix getMatrix(int n, int n2, RectF rectF, int n3) {
        Matrix matrix = new Matrix();
        float f = rectF.width();
        float f2 = n;
        f = n3 == 2 ? Math.max(f, rectF.height() / (float)n2) : Math.min(f /= f2, rectF.height() / (float)n2);
        matrix.postScale(f, f);
        matrix.postTranslate((rectF.width() - f2 * f) / 2.0f, (rectF.height() - (float)n2 * f) / 2.0f);
        return matrix;
    }

    private static boolean isPortrait(Bitmap bitmap) {
        if (bitmap.getWidth() <= bitmap.getHeight()) {
            return true;
        }
        return false;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Bitmap loadBitmap(Uri object, BitmapFactory.Options options) throws FileNotFoundException {
        block9 : {
            if (object == null) throw new IllegalArgumentException("bad argument to loadBitmap");
            if (this.mContext == null) {
                throw new IllegalArgumentException("bad argument to loadBitmap");
            }
            Object var3_6 = null;
            object = this.mContext.getContentResolver().openInputStream((Uri)object);
            options = BitmapFactory.decodeStream((InputStream)object, null, (BitmapFactory.Options)options);
            if (object == null) return options;
            try {
                object.close();
                return options;
            }
            catch (IOException iOException) {
                Log.w((String)LOG_TAG, (String)"close fail ", (Throwable)iOException);
            }
            return options;
            catch (Throwable throwable) {
                options = object;
                object = throwable;
            }
            break block9;
            catch (Throwable throwable) {
                options = var3_6;
            }
        }
        if (options == null) throw object;
        try {
            options.close();
            throw object;
        }
        catch (IOException iOException) {
            Log.w((String)LOG_TAG, (String)"close fail ", (Throwable)iOException);
        }
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Bitmap loadConstrainedBitmap(Uri object) throws FileNotFoundException {
        BitmapFactory.Options options;
        if (object == null) throw new IllegalArgumentException("bad argument to getScaledBitmap");
        if (this.mContext == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        Object object2 = new BitmapFactory.Options();
        object2.inJustDecodeBounds = true;
        this.loadBitmap((Uri)object, (BitmapFactory.Options)object2);
        int n = object2.outWidth;
        int n2 = object2.outHeight;
        if (n <= 0) return null;
        if (n2 <= 0) {
            return null;
        }
        int n3 = 1;
        for (int i = Math.max((int)n, (int)n2); i > 3500; i >>>= 1, n3 <<= 1) {
        }
        if (n3 <= 0) return null;
        if (Math.min(n, n2) / n3 <= 0) {
            return null;
        }
        object2 = this.mLock;
        synchronized (object2) {
            this.mDecodeOptions = new BitmapFactory.Options();
            this.mDecodeOptions.inMutable = true;
            this.mDecodeOptions.inSampleSize = n3;
            options = this.mDecodeOptions;
        }
        try {
            object2 = this.loadBitmap((Uri)object, options);
            return object2;
        }
        finally {
            object = this.mLock;
            synchronized (object) {
                this.mDecodeOptions = null;
            }
        }
    }

    private void writeBitmap(final PrintAttributes printAttributes, final int n, final Bitmap bitmap, final ParcelFileDescriptor parcelFileDescriptor, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        final PrintAttributes printAttributes2 = this.mIsMinMarginsHandlingCorrect ? printAttributes : this.copyAttributes(printAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
        new AsyncTask<Void, Void, Throwable>(){

            /*
             * Unable to fully structure code
             * Enabled aggressive exception aggregation
             */
            protected /* varargs */ Throwable doInBackground(Void ... var1_1) {
                block28 : {
                    block27 : {
                        block26 : {
                            if (cancellationSignal.isCanceled()) {
                                return null;
                            }
                            var4_6 = new PrintedPdfDocument(PrintHelperApi19.this.mContext, printAttributes2);
                            var3_8 = PrintHelper.PrintHelperApi19.access$100(PrintHelperApi19.this, bitmap, printAttributes2.getColorMode());
                            var2_9 = cancellationSignal.isCanceled();
                            if (!var2_9) break block26;
                            return null;
                        }
                        var5_10 = var4_6.startPage(1);
                        if (PrintHelperApi19.this.mIsMinMarginsHandlingCorrect) {
                            var1_1 = new RectF(var5_10.getInfo().getContentRect());
                        } else {
                            var6_11 = new PrintedPdfDocument(PrintHelperApi19.this.mContext, printAttributes);
                            var7_12 = var6_11.startPage(1);
                            var1_1 = new RectF(var7_12.getInfo().getContentRect());
                            var6_11.finishPage(var7_12);
                            var6_11.close();
                        }
                        var6_11 = PrintHelper.PrintHelperApi19.access$200(PrintHelperApi19.this, var3_8.getWidth(), var3_8.getHeight(), var1_1, n);
                        if (!PrintHelperApi19.this.mIsMinMarginsHandlingCorrect) {
                            var6_11.postTranslate(var1_1.left, var1_1.top);
                            var5_10.getCanvas().clipRect(var1_1);
                        }
                        var5_10.getCanvas().drawBitmap(var3_8, (Matrix)var6_11, null);
                        var4_6.finishPage(var5_10);
                        var2_9 = cancellationSignal.isCanceled();
                        if (!var2_9) break block27;
                        var4_6.close();
                        var1_1 = parcelFileDescriptor;
                        if (var1_1 != null) {
                            parcelFileDescriptor.close();
                        }
lbl34: // 4 sources:
                        do {
                            if (var3_8 != bitmap) {
                                var3_8.recycle();
                            }
                            return null;
                            break;
                        } while (true);
                    }
                    var4_6.writeTo((OutputStream)new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                    try {
                        var4_6.close();
                        var1_1 = parcelFileDescriptor;
                        if (var1_1 != null) {
                            parcelFileDescriptor.close();
                        }
lbl47: // 4 sources:
                        if (var3_8 != bitmap) {
                            var3_8.recycle();
                            return null;
                        }
                        break block28;
                        catch (Throwable var1_2) {
                            var4_6.close();
                            var4_6 = parcelFileDescriptor;
                            if (var4_6 != null) {
                                parcelFileDescriptor.close();
                            }
lbl57: // 4 sources:
                            do {
                                if (var3_8 != bitmap) {
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
                if (cancellationSignal.isCanceled()) {
                    writeResultCallback.onWriteCancelled();
                    return;
                }
                if (throwable == null) {
                    writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                    return;
                }
                Log.e((String)PrintHelper.PrintHelperApi19.LOG_TAG, (String)"Error writing printed content", (Throwable)throwable);
                writeResultCallback.onWriteFailed(null);
            }
        }.execute((Object[])new Void[0]);
    }

    protected PrintAttributes.Builder copyAttributes(PrintAttributes printAttributes) {
        PrintAttributes.Builder builder = new PrintAttributes.Builder().setMediaSize(printAttributes.getMediaSize()).setResolution(printAttributes.getResolution()).setMinMargins(printAttributes.getMinMargins());
        if (printAttributes.getColorMode() != 0) {
            builder.setColorMode(printAttributes.getColorMode());
        }
        return builder;
    }

    @Override
    public int getColorMode() {
        return this.mColorMode;
    }

    @Override
    public int getOrientation() {
        if (this.mOrientation == 0) {
            return 1;
        }
        return this.mOrientation;
    }

    @Override
    public int getScaleMode() {
        return this.mScaleMode;
    }

    @Override
    public void printBitmap(final String string, final Bitmap bitmap, final PrintHelper.OnPrintFinishCallback onPrintFinishCallback) {
        if (bitmap == null) {
            return;
        }
        final int n = this.mScaleMode;
        PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
        PrintAttributes.MediaSize mediaSize = PrintHelper.PrintHelperApi19.isPortrait(bitmap) ? PrintAttributes.MediaSize.UNKNOWN_PORTRAIT : PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
        mediaSize = new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
        printManager.print(string, new PrintDocumentAdapter(){
            private PrintAttributes mAttributes;

            public void onFinish() {
                if (onPrintFinishCallback != null) {
                    onPrintFinishCallback.onFinish();
                }
            }

            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                this.mAttributes = printAttributes2;
                layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(string).setContentType(1).setPageCount(1).build(), printAttributes2.equals((Object)printAttributes) ^ true);
            }

            public void onWrite(PageRange[] arrpageRange, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                PrintHelperApi19.this.writeBitmap(this.mAttributes, n, bitmap, parcelFileDescriptor, cancellationSignal, writeResultCallback);
            }
        }, (PrintAttributes)mediaSize);
    }

    @Override
    public void printBitmap(final String string, Uri object, PrintHelper.OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        object = new PrintDocumentAdapter((Uri)object, onPrintFinishCallback, this.mScaleMode){
            private PrintAttributes mAttributes;
            Bitmap mBitmap = null;
            AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
            final /* synthetic */ PrintHelper.OnPrintFinishCallback val$callback;
            final /* synthetic */ int val$fittingMode;
            final /* synthetic */ Uri val$imageFile;
            {
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
                    layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(string).setContentType(1).setPageCount(1).build(), printAttributes2.equals((Object)printAttributes) ^ true);
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
                            bitmap = new PrintDocumentInfo.Builder(string).setContentType(1).setPageCount(1).build();
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

        };
        onPrintFinishCallback = (PrintManager)this.mContext.getSystemService("print");
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(this.mColorMode);
        if (this.mOrientation != 1 && this.mOrientation != 0) {
            if (this.mOrientation == 2) {
                builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
            }
        } else {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
        }
        onPrintFinishCallback.print(string, (PrintDocumentAdapter)object, builder.build());
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
