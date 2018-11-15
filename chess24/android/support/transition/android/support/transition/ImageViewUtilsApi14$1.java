/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.graphics.Matrix
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Matrix;
import android.support.transition.R;
import android.widget.ImageView;

class ImageViewUtilsApi14
extends AnimatorListenerAdapter {
    final /* synthetic */ ImageView val$view;

    ImageViewUtilsApi14(ImageView imageView) {
        this.val$view = imageView;
    }

    public void onAnimationEnd(Animator animator) {
        ImageView.ScaleType scaleType = (ImageView.ScaleType)this.val$view.getTag(R.id.save_scale_type);
        this.val$view.setScaleType(scaleType);
        this.val$view.setTag(R.id.save_scale_type, null);
        if (scaleType == ImageView.ScaleType.MATRIX) {
            this.val$view.setImageMatrix((Matrix)this.val$view.getTag(R.id.save_image_matrix));
            this.val$view.setTag(R.id.save_image_matrix, null);
        }
        animator.removeListener((Animator.AnimatorListener)this);
    }
}
