/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 */
package android.support.v4.view;

import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;

static class ViewPropertyAnimatorCompat.ViewPropertyAnimatorListenerApi14
implements ViewPropertyAnimatorListener {
    boolean mAnimEndCalled;
    ViewPropertyAnimatorCompat mVpa;

    ViewPropertyAnimatorCompat.ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        this.mVpa = viewPropertyAnimatorCompat;
    }

    @Override
    public void onAnimationCancel(View view) {
        Object object = view.getTag(2113929216);
        object = object instanceof ViewPropertyAnimatorListener ? (ViewPropertyAnimatorListener)object : null;
        if (object != null) {
            object.onAnimationCancel(view);
        }
    }

    @Override
    public void onAnimationEnd(View view) {
        int n = this.mVpa.mOldLayerType;
        ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
        if (n > -1) {
            view.setLayerType(this.mVpa.mOldLayerType, null);
            this.mVpa.mOldLayerType = -1;
        }
        if (Build.VERSION.SDK_INT >= 16 || !this.mAnimEndCalled) {
            Object object;
            if (this.mVpa.mEndAction != null) {
                object = this.mVpa.mEndAction;
                this.mVpa.mEndAction = null;
                object.run();
            }
            if ((object = view.getTag(2113929216)) instanceof ViewPropertyAnimatorListener) {
                viewPropertyAnimatorListener = (ViewPropertyAnimatorListener)object;
            }
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationEnd(view);
            }
            this.mAnimEndCalled = true;
        }
    }

    @Override
    public void onAnimationStart(View view) {
        Object object;
        this.mAnimEndCalled = false;
        int n = this.mVpa.mOldLayerType;
        ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
        if (n > -1) {
            view.setLayerType(2, null);
        }
        if (this.mVpa.mStartAction != null) {
            object = this.mVpa.mStartAction;
            this.mVpa.mStartAction = null;
            object.run();
        }
        if ((object = view.getTag(2113929216)) instanceof ViewPropertyAnimatorListener) {
            viewPropertyAnimatorListener = (ViewPropertyAnimatorListener)object;
        }
        if (viewPropertyAnimatorListener != null) {
            viewPropertyAnimatorListener.onAnimationStart(view);
        }
    }
}
