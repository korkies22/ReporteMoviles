/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapShader
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.AnimationDrawable
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.ClipDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.RoundRectShape
 *  android.graphics.drawable.shapes.Shape
 *  android.util.AttributeSet
 *  android.widget.ProgressBar
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.graphics.drawable.WrappedDrawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

class AppCompatProgressBarHelper {
    private static final int[] TINT_ATTRS = new int[]{16843067, 16843068};
    private Bitmap mSampleTile;
    private final ProgressBar mView;

    AppCompatProgressBarHelper(ProgressBar progressBar) {
        this.mView = progressBar;
    }

    private Shape getDrawableShape() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    private Drawable tileify(Drawable drawable, boolean bl) {
        if (drawable instanceof WrappedDrawable) {
            WrappedDrawable wrappedDrawable = (WrappedDrawable)drawable;
            Drawable drawable2 = wrappedDrawable.getWrappedDrawable();
            if (drawable2 != null) {
                wrappedDrawable.setWrappedDrawable(this.tileify(drawable2, bl));
                return drawable;
            }
        } else {
            if (drawable instanceof LayerDrawable) {
                int n;
                drawable = (LayerDrawable)drawable;
                int n2 = drawable.getNumberOfLayers();
                LayerDrawable layerDrawable = new Drawable[n2];
                int n3 = 0;
                for (n = 0; n < n2; ++n) {
                    int n4 = drawable.getId(n);
                    Drawable drawable3 = drawable.getDrawable(n);
                    bl = n4 == 16908301 || n4 == 16908303;
                    layerDrawable[n] = this.tileify(drawable3, bl);
                }
                layerDrawable = new LayerDrawable((Drawable[])layerDrawable);
                for (n = n3; n < n2; ++n) {
                    layerDrawable.setId(n, drawable.getId(n));
                }
                return layerDrawable;
            }
            if (drawable instanceof BitmapDrawable) {
                drawable = (BitmapDrawable)drawable;
                Bitmap bitmap = drawable.getBitmap();
                if (this.mSampleTile == null) {
                    this.mSampleTile = bitmap;
                }
                ShapeDrawable shapeDrawable = new ShapeDrawable(this.getDrawableShape());
                bitmap = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
                shapeDrawable.getPaint().setShader((Shader)bitmap);
                shapeDrawable.getPaint().setColorFilter(drawable.getPaint().getColorFilter());
                if (bl) {
                    return new ClipDrawable((Drawable)shapeDrawable, 3, 1);
                }
                return shapeDrawable;
            }
        }
        return drawable;
    }

    private Drawable tileifyIndeterminate(Drawable drawable) {
        Drawable drawable2 = drawable;
        if (drawable instanceof AnimationDrawable) {
            drawable = (AnimationDrawable)drawable;
            int n = drawable.getNumberOfFrames();
            drawable2 = new AnimationDrawable();
            drawable2.setOneShot(drawable.isOneShot());
            for (int i = 0; i < n; ++i) {
                Drawable drawable3 = this.tileify(drawable.getFrame(i), true);
                drawable3.setLevel(10000);
                drawable2.addFrame(drawable3, drawable.getDuration(i));
            }
            drawable2.setLevel(10000);
        }
        return drawable2;
    }

    Bitmap getSampleTime() {
        return this.mSampleTile;
    }

    void loadFromAttributes(AttributeSet object, int n) {
        object = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), (AttributeSet)object, TINT_ATTRS, n, 0);
        Drawable drawable = object.getDrawableIfKnown(0);
        if (drawable != null) {
            this.mView.setIndeterminateDrawable(this.tileifyIndeterminate(drawable));
        }
        if ((drawable = object.getDrawableIfKnown(1)) != null) {
            this.mView.setProgressDrawable(this.tileify(drawable, false));
        }
        object.recycle();
    }
}
