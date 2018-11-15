/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package android.support.v4.widget;

import android.support.v4.widget.CircularProgressDrawable;
import android.view.animation.Animation;
import android.view.animation.Transformation;

class SwipeRefreshLayout
extends Animation {
    final /* synthetic */ int val$endingAlpha;
    final /* synthetic */ int val$startingAlpha;

    SwipeRefreshLayout(int n, int n2) {
        this.val$startingAlpha = n;
        this.val$endingAlpha = n2;
    }

    public void applyTransformation(float f, Transformation transformation) {
        SwipeRefreshLayout.this.mProgress.setAlpha((int)((float)this.val$startingAlpha + (float)(this.val$endingAlpha - this.val$startingAlpha) * f));
    }
}
