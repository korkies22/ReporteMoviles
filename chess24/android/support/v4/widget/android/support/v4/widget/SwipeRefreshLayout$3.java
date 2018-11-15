/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

class SwipeRefreshLayout
extends Animation {
    SwipeRefreshLayout() {
    }

    public void applyTransformation(float f, Transformation transformation) {
        SwipeRefreshLayout.this.setAnimationProgress(1.0f - f);
    }
}
