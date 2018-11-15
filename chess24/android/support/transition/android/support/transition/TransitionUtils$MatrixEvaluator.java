/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 *  android.graphics.Matrix
 */
package android.support.transition;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;
import android.support.transition.TransitionUtils;

static class TransitionUtils.MatrixEvaluator
implements TypeEvaluator<Matrix> {
    final float[] mTempEndValues = new float[9];
    final Matrix mTempMatrix = new Matrix();
    final float[] mTempStartValues = new float[9];

    TransitionUtils.MatrixEvaluator() {
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
