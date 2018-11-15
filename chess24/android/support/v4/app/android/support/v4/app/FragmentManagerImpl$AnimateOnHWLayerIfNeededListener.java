/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package android.support.v4.app;

import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Animation;

private static class FragmentManagerImpl.AnimateOnHWLayerIfNeededListener
extends FragmentManagerImpl.AnimationListenerWrapper {
    View mView;

    FragmentManagerImpl.AnimateOnHWLayerIfNeededListener(View view, Animation.AnimationListener animationListener) {
        super(animationListener, null);
        this.mView = view;
    }

    @CallSuper
    @Override
    public void onAnimationEnd(Animation animation) {
        if (!ViewCompat.isAttachedToWindow(this.mView) && Build.VERSION.SDK_INT < 24) {
            this.mView.setLayerType(0, null);
        } else {
            this.mView.post(new Runnable(){

                @Override
                public void run() {
                    AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null);
                }
            });
        }
        super.onAnimationEnd(animation);
    }

}
