/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.graphics.Matrix
 *  android.view.View
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Matrix;
import android.support.transition.ChangeTransform;
import android.support.transition.R;
import android.support.transition.ViewUtils;
import android.view.View;

class ChangeTransform
extends AnimatorListenerAdapter {
    private boolean mIsCanceled;
    private Matrix mTempMatrix = new Matrix();
    final /* synthetic */ Matrix val$finalEndMatrix;
    final /* synthetic */ boolean val$handleParentChange;
    final /* synthetic */ ChangeTransform.PathAnimatorMatrix val$pathAnimatorMatrix;
    final /* synthetic */ ChangeTransform.Transforms val$transforms;
    final /* synthetic */ View val$view;

    ChangeTransform(boolean bl, Matrix matrix, View view, ChangeTransform.Transforms transforms, ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix) {
        this.val$handleParentChange = bl;
        this.val$finalEndMatrix = matrix;
        this.val$view = view;
        this.val$transforms = transforms;
        this.val$pathAnimatorMatrix = pathAnimatorMatrix;
    }

    private void setCurrentMatrix(Matrix matrix) {
        this.mTempMatrix.set(matrix);
        this.val$view.setTag(R.id.transition_transform, (Object)this.mTempMatrix);
        this.val$transforms.restore(this.val$view);
    }

    public void onAnimationCancel(Animator animator) {
        this.mIsCanceled = true;
    }

    public void onAnimationEnd(Animator animator) {
        if (!this.mIsCanceled) {
            if (this.val$handleParentChange && ChangeTransform.this.mUseOverlay) {
                this.setCurrentMatrix(this.val$finalEndMatrix);
            } else {
                this.val$view.setTag(R.id.transition_transform, null);
                this.val$view.setTag(R.id.parent_matrix, null);
            }
        }
        ViewUtils.setAnimationMatrix(this.val$view, null);
        this.val$transforms.restore(this.val$view);
    }

    public void onAnimationPause(Animator animator) {
        this.setCurrentMatrix(this.val$pathAnimatorMatrix.getMatrix());
    }

    public void onAnimationResume(Animator animator) {
        android.support.transition.ChangeTransform.setIdentityTransforms(this.val$view);
    }
}
