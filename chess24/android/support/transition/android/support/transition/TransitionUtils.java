/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorSet
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.transition.ViewUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

class TransitionUtils {
    private static final int MAX_IMAGE_SIZE = 1048576;

    TransitionUtils() {
    }

    static View copyViewImage(ViewGroup viewGroup, View view, View view2) {
        Matrix matrix = new Matrix();
        matrix.setTranslate((float)(- view2.getScrollX()), (float)(- view2.getScrollY()));
        ViewUtils.transformMatrixToGlobal(view, matrix);
        ViewUtils.transformMatrixToLocal((View)viewGroup, matrix);
        view2 = new RectF(0.0f, 0.0f, (float)view.getWidth(), (float)view.getHeight());
        matrix.mapRect((RectF)view2);
        int n = Math.round(view2.left);
        int n2 = Math.round(view2.top);
        int n3 = Math.round(view2.right);
        int n4 = Math.round(view2.bottom);
        viewGroup = new ImageView(view.getContext());
        viewGroup.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view = TransitionUtils.createViewBitmap(view, matrix, (RectF)view2);
        if (view != null) {
            viewGroup.setImageBitmap((Bitmap)view);
        }
        viewGroup.measure(View.MeasureSpec.makeMeasureSpec((int)(n3 - n), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(n4 - n2), (int)1073741824));
        viewGroup.layout(n, n2, n3, n4);
        return viewGroup;
    }

    private static Bitmap createViewBitmap(View view, Matrix matrix, RectF rectF) {
        int n = Math.round(rectF.width());
        int n2 = Math.round(rectF.height());
        if (n > 0 && n2 > 0) {
            float f = Math.min(1.0f, 1048576.0f / (float)(n * n2));
            n = (int)((float)n * f);
            n2 = (int)((float)n2 * f);
            matrix.postTranslate(- rectF.left, - rectF.top);
            matrix.postScale(f, f);
            rectF = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas((Bitmap)rectF);
            canvas.concat(matrix);
            view.draw(canvas);
            return rectF;
        }
        return null;
    }

    static Animator mergeAnimators(Animator animator, Animator animator2) {
        if (animator == null) {
            return animator2;
        }
        if (animator2 == null) {
            return animator;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{animator, animator2});
        return animatorSet;
    }

    static class MatrixEvaluator
    implements TypeEvaluator<Matrix> {
        final float[] mTempEndValues = new float[9];
        final Matrix mTempMatrix = new Matrix();
        final float[] mTempStartValues = new float[9];

        MatrixEvaluator() {
        }

        public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
            matrix.getValues(this.mTempStartValues);
            matrix2.getValues(this.mTempEndValues);
            for (int i = 0; i < 9; ++i) {
                float f2 = this.mTempEndValues[i];
                float f3 = this.mTempStartValues[i];
                this.mTempEndValues[i] = this.mTempStartValues[i] + (f2 - f3) * f;
            }
            this.mTempMatrix.setValues(this.mTempEndValues);
            return this.mTempMatrix;
        }
    }

}
