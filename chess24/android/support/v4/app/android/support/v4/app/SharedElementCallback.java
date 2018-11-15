/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package android.support.v4.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import java.util.List;
import java.util.Map;

public abstract class SharedElementCallback {
    private static final String BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap";
    private static final String BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix";
    private static final String BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType";
    private static int MAX_IMAGE_SIZE = 1048576;
    private Matrix mTempMatrix;

    private static Bitmap createDrawableBitmap(Drawable drawable) {
        int n = drawable.getIntrinsicWidth();
        int n2 = drawable.getIntrinsicHeight();
        if (n > 0 && n2 > 0) {
            float f = Math.min(1.0f, (float)MAX_IMAGE_SIZE / (float)(n * n2));
            if (drawable instanceof BitmapDrawable && f == 1.0f) {
                return ((BitmapDrawable)drawable).getBitmap();
            }
            n = (int)((float)n * f);
            n2 = (int)((float)n2 * f);
            Bitmap bitmap = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Rect rect = drawable.getBounds();
            int n3 = rect.left;
            int n4 = rect.top;
            int n5 = rect.right;
            int n6 = rect.bottom;
            drawable.setBounds(0, 0, n, n2);
            drawable.draw(canvas);
            drawable.setBounds(n3, n4, n5, n6);
            return bitmap;
        }
        return null;
    }

    public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF arrf) {
        ImageView imageView;
        Drawable drawable;
        if (view instanceof ImageView) {
            imageView = (ImageView)view;
            drawable = imageView.getDrawable();
            Drawable drawable2 = imageView.getBackground();
            if (drawable != null && drawable2 == null && (drawable = SharedElementCallback.createDrawableBitmap(drawable)) != null) {
                view = new Bundle();
                view.putParcelable(BUNDLE_SNAPSHOT_BITMAP, (Parcelable)drawable);
                view.putString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE, imageView.getScaleType().toString());
                if (imageView.getScaleType() == ImageView.ScaleType.MATRIX) {
                    matrix = imageView.getImageMatrix();
                    arrf = new float[9];
                    matrix.getValues(arrf);
                    view.putFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX, arrf);
                }
                return view;
            }
        }
        int n = Math.round(arrf.width());
        int n2 = Math.round(arrf.height());
        drawable = null;
        imageView = drawable;
        if (n > 0) {
            imageView = drawable;
            if (n2 > 0) {
                float f = Math.min(1.0f, (float)MAX_IMAGE_SIZE / (float)(n * n2));
                n = (int)((float)n * f);
                n2 = (int)((float)n2 * f);
                if (this.mTempMatrix == null) {
                    this.mTempMatrix = new Matrix();
                }
                this.mTempMatrix.set(matrix);
                this.mTempMatrix.postTranslate(- arrf.left, - arrf.top);
                this.mTempMatrix.postScale(f, f);
                imageView = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                matrix = new Canvas((Bitmap)imageView);
                matrix.concat(this.mTempMatrix);
                view.draw((Canvas)matrix);
            }
        }
        return imageView;
    }

    public View onCreateSnapshotView(Context context, Parcelable arrf) {
        boolean bl = arrf instanceof Bundle;
        Bitmap bitmap = null;
        if (bl) {
            bitmap = (Bitmap)(arrf = (Bundle)arrf).getParcelable(BUNDLE_SNAPSHOT_BITMAP);
            if (bitmap == null) {
                return null;
            }
            context = new ImageView(context);
            context.setImageBitmap(bitmap);
            context.setScaleType(ImageView.ScaleType.valueOf((String)arrf.getString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE)));
            bitmap = context;
            if (context.getScaleType() == ImageView.ScaleType.MATRIX) {
                arrf = arrf.getFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX);
                bitmap = new Matrix();
                bitmap.setValues(arrf);
                context.setImageMatrix((Matrix)bitmap);
                return context;
            }
        } else if (arrf instanceof Bitmap) {
            arrf = (Bitmap)arrf;
            bitmap = new ImageView(context);
            bitmap.setImageBitmap((Bitmap)arrf);
        }
        return bitmap;
    }

    public void onMapSharedElements(List<String> list, Map<String, View> map) {
    }

    public void onRejectSharedElements(List<View> list) {
    }

    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
    }

    public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
    }

    public void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListener onSharedElementsReadyListener) {
        onSharedElementsReadyListener.onSharedElementsReady();
    }

    public static interface OnSharedElementsReadyListener {
        public void onSharedElementsReady();
    }

}
