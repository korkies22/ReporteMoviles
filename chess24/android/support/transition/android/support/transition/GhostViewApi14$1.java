/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.transition;

import android.graphics.Matrix;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

class GhostViewApi14
implements ViewTreeObserver.OnPreDrawListener {
    GhostViewApi14() {
    }

    public boolean onPreDraw() {
        GhostViewApi14.this.mCurrentMatrix = GhostViewApi14.this.mView.getMatrix();
        ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this);
        if (GhostViewApi14.this.mStartParent != null && GhostViewApi14.this.mStartView != null) {
            GhostViewApi14.this.mStartParent.endViewTransition(GhostViewApi14.this.mStartView);
            ViewCompat.postInvalidateOnAnimation((View)GhostViewApi14.this.mStartParent);
            GhostViewApi14.this.mStartParent = null;
            GhostViewApi14.this.mStartView = null;
        }
        return true;
    }
}
