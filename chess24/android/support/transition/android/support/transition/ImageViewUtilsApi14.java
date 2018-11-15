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
import android.support.annotation.RequiresApi;
import android.support.transition.ImageViewUtilsImpl;
import android.support.transition.MatrixUtils;
import android.support.transition.R;
import android.widget.ImageView;

@RequiresApi(value=14)
class ImageViewUtilsApi14
implements ImageViewUtilsImpl {
    ImageViewUtilsApi14() {
    }

    @Override
    public void animateTransform(ImageView imageView, Matrix matrix) {
        imageView.setImageMatrix(matrix);
    }

    @Override
    public void reserveEndAnimateTransform(final ImageView imageView, Animator animator) {
        animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                ImageView.ScaleType scaleType = (ImageView.ScaleType)imageView.getTag(R.id.save_scale_type);
                imageView.setScaleType(scaleType);
                imageView.setTag(R.id.save_scale_type, null);
                if (scaleType == ImageView.ScaleType.MATRIX) {
                    imageView.setImageMatrix((Matrix)imageView.getTag(R.id.save_image_matrix));
                    imageView.setTag(R.id.save_image_matrix, null);
                }
                animator.removeListener((Animator.AnimatorListener)this);
            }
        });
    }

    @Override
    public void startAnimateTransform(ImageView imageView) {
        ImageView.ScaleType scaleType = imageView.getScaleType();
        imageView.setTag(R.id.save_scale_type, (Object)scaleType);
        if (scaleType == ImageView.ScaleType.MATRIX) {
            imageView.setTag(R.id.save_image_matrix, (Object)imageView.getImageMatrix());
        } else {
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
        }
        imageView.setImageMatrix(MatrixUtils.IDENTITY_MATRIX);
    }

}
