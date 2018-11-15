/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.graphics.Matrix
 *  android.util.Log
 *  android.widget.ImageView
 */
package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.support.annotation.RequiresApi;
import android.support.transition.ImageViewUtilsImpl;
import android.util.Log;
import android.widget.ImageView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(value=21)
class ImageViewUtilsApi21
implements ImageViewUtilsImpl {
    private static final String TAG = "ImageViewUtilsApi21";
    private static Method sAnimateTransformMethod;
    private static boolean sAnimateTransformMethodFetched;

    ImageViewUtilsApi21() {
    }

    private void fetchAnimateTransformMethod() {
        if (!sAnimateTransformMethodFetched) {
            try {
                sAnimateTransformMethod = ImageView.class.getDeclaredMethod("animateTransform", Matrix.class);
                sAnimateTransformMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Failed to retrieve animateTransform method", (Throwable)noSuchMethodException);
            }
            sAnimateTransformMethodFetched = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void animateTransform(ImageView imageView, Matrix matrix) {
        this.fetchAnimateTransformMethod();
        if (sAnimateTransformMethod == null) return;
        try {
            sAnimateTransformMethod.invoke((Object)imageView, new Object[]{matrix});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }

    @Override
    public void reserveEndAnimateTransform(ImageView imageView, Animator animator) {
    }

    @Override
    public void startAnimateTransform(ImageView imageView) {
    }
}
