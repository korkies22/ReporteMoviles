/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.transition.ImageViewUtils;
import android.support.transition.MatrixUtils;
import android.support.transition.Transition;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Map;

public class ChangeImageTransform
extends Transition {
    private static final Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY;
    private static final TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR;
    private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
    private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
    private static final String[] sTransitionProperties;

    static {
        sTransitionProperties = new String[]{PROPNAME_MATRIX, PROPNAME_BOUNDS};
        NULL_MATRIX_EVALUATOR = new TypeEvaluator<Matrix>(){

            public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
                return null;
            }
        };
        ANIMATED_TRANSFORM_PROPERTY = new Property<ImageView, Matrix>(Matrix.class, "animatedTransform"){

            public Matrix get(ImageView imageView) {
                return null;
            }

            public void set(ImageView imageView, Matrix matrix) {
                ImageViewUtils.animateTransform(imageView, matrix);
            }
        };
    }

    public ChangeImageTransform() {
    }

    public ChangeImageTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues object) {
        View view = object.view;
        if (view instanceof ImageView) {
            if (view.getVisibility() != 0) {
                return;
            }
            ImageView imageView = (ImageView)view;
            if (imageView.getDrawable() == null) {
                return;
            }
            object = object.values;
            object.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            object.put(PROPNAME_MATRIX, ChangeImageTransform.copyImageMatrix(imageView));
            return;
        }
    }

    private static Matrix centerCropMatrix(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        int n = drawable.getIntrinsicWidth();
        float f = imageView.getWidth();
        float f2 = n;
        float f3 = f / f2;
        n = drawable.getIntrinsicHeight();
        float f4 = imageView.getHeight();
        float f5 = n;
        f3 = Math.max(f3, f4 / f5);
        n = Math.round((f - f2 * f3) / 2.0f);
        int n2 = Math.round((f4 - f5 * f3) / 2.0f);
        imageView = new Matrix();
        imageView.postScale(f3, f3);
        imageView.postTranslate((float)n, (float)n2);
        return imageView;
    }

    private static Matrix copyImageMatrix(ImageView imageView) {
        switch (.$SwitchMap$android$widget$ImageView$ScaleType[imageView.getScaleType().ordinal()]) {
            default: {
                return new Matrix(imageView.getImageMatrix());
            }
            case 2: {
                return ChangeImageTransform.centerCropMatrix(imageView);
            }
            case 1: 
        }
        return ChangeImageTransform.fitXYMatrix(imageView);
    }

    private ObjectAnimator createMatrixAnimator(ImageView imageView, Matrix matrix, Matrix matrix2) {
        return ObjectAnimator.ofObject((Object)imageView, ANIMATED_TRANSFORM_PROPERTY, (TypeEvaluator)new TransitionUtils.MatrixEvaluator(), (Object[])new Matrix[]{matrix, matrix2});
    }

    private ObjectAnimator createNullAnimator(ImageView imageView) {
        return ObjectAnimator.ofObject((Object)imageView, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, (Object[])new Matrix[]{null, null});
    }

    private static Matrix fitXYMatrix(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Matrix matrix = new Matrix();
        matrix.postScale((float)imageView.getWidth() / (float)drawable.getIntrinsicWidth(), (float)imageView.getHeight() / (float)drawable.getIntrinsicHeight());
        return matrix;
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(@NonNull ViewGroup object, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null) {
            if (transitionValues2 == null) {
                return null;
            }
            object = (Rect)transitionValues.values.get(PROPNAME_BOUNDS);
            Rect rect = (Rect)transitionValues2.values.get(PROPNAME_BOUNDS);
            if (object != null) {
                if (rect == null) {
                    return null;
                }
                transitionValues = (Matrix)transitionValues.values.get(PROPNAME_MATRIX);
                Matrix matrix = (Matrix)transitionValues2.values.get(PROPNAME_MATRIX);
                int n = transitionValues == null && matrix == null || transitionValues != null && transitionValues.equals((Object)matrix) ? 1 : 0;
                if (object.equals((Object)rect) && n != 0) {
                    return null;
                }
                transitionValues2 = (ImageView)transitionValues2.view;
                object = transitionValues2.getDrawable();
                n = object.getIntrinsicWidth();
                int n2 = object.getIntrinsicHeight();
                ImageViewUtils.startAnimateTransform((ImageView)transitionValues2);
                if (n != 0 && n2 != 0) {
                    object = transitionValues;
                    if (transitionValues == null) {
                        object = MatrixUtils.IDENTITY_MATRIX;
                    }
                    transitionValues = matrix;
                    if (matrix == null) {
                        transitionValues = MatrixUtils.IDENTITY_MATRIX;
                    }
                    ANIMATED_TRANSFORM_PROPERTY.set((Object)transitionValues2, object);
                    object = this.createMatrixAnimator((ImageView)transitionValues2, (Matrix)object, (Matrix)transitionValues);
                } else {
                    object = this.createNullAnimator((ImageView)transitionValues2);
                }
                ImageViewUtils.reserveEndAnimateTransform((ImageView)transitionValues2, (Animator)object);
                return object;
            }
            return null;
        }
        return null;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

}
