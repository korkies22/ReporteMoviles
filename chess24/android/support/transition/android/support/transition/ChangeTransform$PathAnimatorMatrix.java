/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.view.View
 */
package android.support.transition;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.transition.ChangeTransform;
import android.support.transition.ViewUtils;
import android.view.View;

private static class ChangeTransform.PathAnimatorMatrix {
    private final Matrix mMatrix = new Matrix();
    private float mTranslationX;
    private float mTranslationY;
    private final float[] mValues;
    private final View mView;

    ChangeTransform.PathAnimatorMatrix(View view, float[] arrf) {
        this.mView = view;
        this.mValues = (float[])arrf.clone();
        this.mTranslationX = this.mValues[2];
        this.mTranslationY = this.mValues[5];
        this.setAnimationMatrix();
    }

    private void setAnimationMatrix() {
        this.mValues[2] = this.mTranslationX;
        this.mValues[5] = this.mTranslationY;
        this.mMatrix.setValues(this.mValues);
        ViewUtils.setAnimationMatrix(this.mView, this.mMatrix);
    }

    Matrix getMatrix() {
        return this.mMatrix;
    }

    void setTranslation(PointF pointF) {
        this.mTranslationX = pointF.x;
        this.mTranslationY = pointF.y;
        this.setAnimationMatrix();
    }

    void setValues(float[] arrf) {
        System.arraycopy(arrf, 0, this.mValues, 0, arrf.length);
        this.setAnimationMatrix();
    }
}
